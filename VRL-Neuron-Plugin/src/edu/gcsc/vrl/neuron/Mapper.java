/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.neuron;

import edu.gcsc.vrl.ug.api.AlgebraType;
import edu.gcsc.vrl.ug.api.F_InitUG;
import edu.gcsc.vrl.ug.api.I_AlgebraType;
import edu.gcsc.vrl.userdata.UserDataTuple;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.Serializable;

/**
 *
 * @author stephangrein
 */

@ComponentInfo(name="Mapper", category="NeuronPlugin/Tools", description="Vm2uG")
public class Mapper implements Serializable {
        private static final long serialVersionUID=1L;
	private double vm;
	private Interpreter interpreter;
 	// TODO: private Vm2uG instance here

	/**
	 * @brief primitive ctor
	 * @param path 
	 */
	public Mapper(
		@ParamInfo(name="path", style="default", options="value=Path to timestep basedir") String path
		) { 
		// TODO: create Vm2uG insstance
	}


	/**
	 * @brief hoc ctro
	 * @param interpreter 
	 */
	public Mapper(
		@ParamInfo(name="interprter", style="default") Interpreter interpreter
		) {
		this.interpreter = interpreter;
	}
	

	
	public void init(
		@ParamInfo(name="interpreter", style="default") Interpreter interpreter,
		UserDataTuple[] convDiffData,
        	@ParamGroupInfo(group="Physical Parameter")
        	@ParamInfo(name="Dirichlet Boundary", style="array", options="tag=\"TheFile\"; minArraySize=0; type=\"s|n:Subset, Value\"")
        	UserDataTuple[] dirichletBndValue,
        	@ParamGroupInfo(group="Physical Parameter")
       		@ParamInfo(name="Neumann Boundary  ", style="array", options="tag=\"TheFile\"; minArraySize=0; type=\"s|n:Subset, Value\"")
        	UserDataTuple[] neumannBndValue
		) {
		if (interpreter == null) {
			this.interpreter = interpreter;
			I_AlgebraType algebraType = new AlgebraType("CPU", 1);
			 F_InitUG.invoke(3, algebraType);
		} else {
			System.out.println("Interpreter already presenet in Mapper!");
		}
	}
	/**
	 * @brief default ctor
	 */
	public Mapper() {
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param t
	 * @return 
	 */
	public double mapping(
		@ParamInfo(name="x coordinate", options="value=0") double x,
		@ParamInfo(name="y coordiante", options="value=0") double y,
		@ParamInfo(name="z coordiante", options="value=0") double z,
		@ParamInfo(name="time", options="value=0") double t
		) {
		return invoke(x, y, z, t);		
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param t
	 * @return 
	 */
	private double invoke(double x, double y, double z, double t) { 
		return this.vm;
	}
	
}
