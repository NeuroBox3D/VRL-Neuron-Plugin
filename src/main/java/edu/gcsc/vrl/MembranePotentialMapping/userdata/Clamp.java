/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.userdata;

import edu.gcsc.vrl.MembranePotentialMapping.types.SectionType;
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;

/**
 * @ Clamps, i. e. Point Processes from NEURON
 * @author stephan
 */

@ComponentInfo(name="Clamp", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class Clamp implements Serializable {
    private static final long serialVersionUID = 1L;
    private I_Transformator m_transformator = null;
	
    /**
     * @brief adds a point process to the given hoc interpreter
     * 
     * @param stimType type, i. e. IClamp or VClamp
     * @param stimDur duration [ms]
     * @param stimAmp amplitude [nA]
     * @param stimDel duration of deletion in [ms]
     * @param hoc_file the hoc file to be loaded
     * @param sectionTest test the type representation
     * @param stimLoc
     */
    public void point_process(
	@ParamGroupInfo(group="Point Process|false; Stimulation|false")
	@ParamInfo(name="Type", style="selection", options="value=[\"IClamp\", \"VClamp\"]") String stimType,
	@ParamGroupInfo(group="Point Process|false; Stimulation|false")
    	@ParamInfo(name="stimDur", style="default") double stimDur,
	@ParamGroupInfo(group="Point Process|false; Stimulation|false")
	@ParamInfo(name="stimDel") double stimDel,
	@ParamGroupInfo(group="Point Process|false; Stimulation|false")
	@ParamInfo(name="stimAmp") double stimAmp,
	@ParamGroupInfo(group="Point Process|false; Stimulation|false")
	@ParamInfo(name="stimLoc") double stimLoc,
		
	/*@ParamGroupInfo(group="Point Process|false; Location|false")
	@ParamInfo(name="Section") String stimSection,
	@ParamGroupInfo(group="Point Process|false; Location|false")
	@ParamInfo(name="Position") String stimPosition,*/

	@ParamGroupInfo(group="Point Process|true; Geometry|false")
	@ParamInfo(name="Load", style="hoc-load-dialog", options="hoc_tag=\"gridFile\"") File hoc_file,
	    
	@ParamGroupInfo(group="Point Process|false; Geometry|false")
	@ParamInfo(name="Section", style="default", options="hoc_tag=\"gridFile\"") Section sectionTest
	    
    ) {
	    if ( ! (m_transformator == null) ) {
		    for (int i = 0; i < sectionTest.get_names().size(); i++) {
			    System.err.println(sectionTest.get_names().get(i));
			    m_transformator.execute_hoc_stmt("objectvar stim" + i);
			    if (stimType.equals("VClamp")) {
				    m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stim"+i + "= new VClamp(" + stimLoc + ")");
			    } else if (stimType.equals("IClamp")) {
				    m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stim"+i + "= new IClamp(" + stimLoc + ")");
			    } else {
				    
			    }
		    }
		    /// then execute transformator statement
		// TODO: implement method stub
		// m_transformator.execute_hoc_stmt("");
	    } else {
          	eu.mihosoft.vrl.system.VMessage.info("HOC interpreter not initialized", "Check if Clamp instance has ingoing connection of a HOC interpreter (I_Transformator).");
	    }
    }

    /**
     * @brief default ctor
     */
    public Clamp() {
	    
    }

    /**
     * 
     * @brief set's the HOC interpreter instance
     * @param transformator the hoc interpreter
     */
    public void set_transformator(
	    @ParamInfo(name="HOC Interpreter")
	    I_Transformator transformator) {
	    m_transformator = transformator;
    }

    /**
     * @brief get's the HOC interpreter instance
     * @return the hoc interpreter
     */
    @SuppressWarnings("all")
    private I_Transformator get_transformator() {
	    return m_transformator;
    }
}
