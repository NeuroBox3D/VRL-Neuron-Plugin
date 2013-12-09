/**
 * Script to perform a Advection-Diffusion Problem
 * 
 * Author: M. Hoffer, A. Vogel
 */

package edu.gcsc.vrl.neuron;

import edu.gcsc.vrl.ug.api.I_ApproximationSpace;
import edu.gcsc.vrl.ug.api.I_UserNumber;
import edu.gcsc.vrl.ug.api.I_UserVector;
import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.ug.api.*;
import edu.gcsc.vrl.userdata.*;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;

@ComponentInfo(name="AdvecetionDiffusion", category="Custom")
public class AdvecetionDiffusion implements java.io.Serializable {
    private static final long serialVersionUID=1L;


   //   elemTypes = [I_DomainDiscretization.class, I_ApproximationSpace.class, I_UserNumber[].class, I_VTKOutput.class]
 //     elemNames = ["Problem-Setup", "Approximation-Space", "StartValues", "Output-Data"],
    
    @OutputInfo(
    style="multi-out",
      elemTypes = {I_DomainDiscretization.class, I_ApproximationSpace.class, I_UserNumber[].class, I_VTKOutput.class},
      elemNames = {"Problem-Setup", "Approximation-Space", "StartValues", "Output-Data"})
    public Object[] invoke(
        @ParamGroupInfo(group="Domain")
        @ParamInfo(name="Grid:", style="ugx-load-dialog", options="tag=\"TheFile\"")
        java.io.File file,
        @ParamGroupInfo(group="Physical Parameter|true|Parameter")
        @ParamInfo(name="Inner Parameters", style="array", options="tag=\"TheFile\"; minArraySize=1; type=\"s|mvnn:Subset, Diffusion, Velocity, Source, Reaction Rate\"")
        UserDataTuple[] convDiffData,
        @ParamGroupInfo(group="Physical Parameter")
        @ParamInfo(name="Dirichlet Boundary", style="array", options="tag=\"TheFile\"; minArraySize=0; type=\"s|n:Subset, Value\"")
        UserDataTuple[] dirichletBndValue,
        @ParamGroupInfo(group="Physical Parameter")
        @ParamInfo(name="Neumann Boundary  ", style="array", options="tag=\"TheFile\"; minArraySize=0; type=\"s|n:Subset, Value\"")
        UserDataTuple[] neumannBndValue,

        @ParamGroupInfo(group="Start Value|false|Start Values")
        @ParamInfo(name="Start Value", style="default", options="tag=\"TheFile\"")
        I_UserNumber startValue,

        @ParamGroupInfo(group="Discretization Setup|false|Discretization Parameters")
        @ParamInfo(name="Discretization Type", style="selection", options="value=[\"Finite Element\", \"Finite Volume\"]")
        String discTypeName,
        @ParamGroupInfo(group="Discretization Setup")
        @ParamInfo(name="Upwind Type", style="selection", options="value=[\"Full\",\"Partial\", \"None\"]")
        String upwindTypeName
        )
        {
        String fileName = file.getAbsoluteFile().getAbsolutePath();

        int dim = convDiffData[0].getMatrixData(1).const__get_dim();
        
        //-- Init UG for dimension and algebra
        F_InitUG.invoke(dim,new AlgebraType("CPU", 1));

        //--------------------------------------------------------------------------------
        //-- Domain Setup
        //--------------------------------------------------------------------------------
        I_Domain dom = new Domain();
        F_LoadDomain.invoke(dom, fileName);
      
        // -- Create, Load, Refine and Distribute Domain
        I_ApproximationSpace approxSpace = new ApproximationSpace(dom);
        String fctName = "c";
        approxSpace.add_fct(fctName, "Lagrange",1);

        //--------------------------------------------------------------------------------
        //-- FV Disc setup
        //--------------------------------------------------------------------------------
        I_DomainDiscretization domainDisc = new DomainDiscretization(approxSpace);

        I_MGSubsetHandler sh = dom.subset_handler();

        String innerSubset = "";
        for (int i = 0; i < convDiffData.length; i++) { 
            String innerSS = convDiffData[i].getSubset(0);

            if("".equals(innerSubset)) {innerSubset = innerSubset + innerSS;}
            else {innerSubset = innerSubset + "," + innerSS;}

	    I_ConvectionDiffusionBase elemDisc = null;
 	    I_IConvectionShapes upwinding = null;
	    
	    if ("Full".equals(upwindTypeName)) {
		upwinding = new FullUpwind3d();	    
	    } else if ("Partial".equals(upwindTypeName)) {
		upwinding = new PartialUpwind3d();
	    } else if ("None".equals(upwindTypeName)) {
		upwinding = new NoUpwind3d();
	    } else {
 	        errorExit("Cannot find specified upwinding type: " + upwindTypeName);
	    }

	    
	    if ("Finite Elemente".equals(discTypeName)) {
		    elemDisc = new ConvectionDiffusionFE3d(fctName, innerSS);
	    } else if ("Finite Volumen".equals(discTypeName)) {
		    elemDisc = new ConvectionDiffusionFV13d(fctName, innerSS);
		    ((I_ConvectionDiffusionFV13d) elemDisc).set_upwind(upwinding);
	    } else {
		    errorExit("Cannot find specified discretization type: " + discTypeName);
	    }

	    
	    I_UserNumber massScale = convDiffData[i].getNumberData(5);
            I_UserNumber reactionRate = convDiffData[i].getNumberData(4);
            I_UserNumber source = convDiffData[i].getNumberData(3);
            I_UserVector velocity = convDiffData[i].getVectorData(2);
            I_UserMatrix diffusion = convDiffData[i].getMatrixData(1);

            elemDisc.set_diffusion((I_CplUserMatrix) diffusion);
            elemDisc.set_velocity((I_CplUserVector) velocity);
            elemDisc.set_source((I_CplUserNumber) source);
            elemDisc.set_reaction_rate((I_CplUserNumber) reactionRate);
            elemDisc.set_mass_scale((I_CplUserNumber) massScale);
            domainDisc.add(elemDisc);
            }

        I_DirichletBoundary dirichletBND = new DirichletBoundary();
        for (int i = 0; i < dirichletBndValue.length; i++) { 
  
            String bndSS = dirichletBndValue[i].getSubset(0);
 
            dirichletBND.add(dirichletBndValue[i].getNumberData(1), fctName, bndSS);
        }
        domainDisc.add(dirichletBND);

        I_ConvectionDiffusionBase base;
	
      // I_NeumannBoundary neumannBND = new NeumannBoundary(innerSubset);
	NeumannBoundaryBase neumannBND = null;
        for (int i = 0; i < neumannBndValue.length; i++) { 
           String bndSS = neumannBndValue[i].getSubset(0);
          neumannBND.add((I_CplUserNumber) neumannBndValue[i].getNumberData(1), fctName, bndSS);
        }
        domainDisc.add(neumannBND);

        I_UserNumber[] startVal = new I_UserNumber[1];
        startVal[0] = startValue;

        I_VTKOutput out = new VTKOutput();
        out.select_nodal(fctName, fctName);
	
	Object[] obj = new Object[] { domainDisc, approxSpace, startVal, out};
	return obj;
    }

    private void errorExit(String s){
        eu.mihosoft.vrl.system.VMessage.exception("Setup Error in AdvectionDiffusion: ", s);
	 }}