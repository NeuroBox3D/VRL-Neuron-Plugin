/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.userdata;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.hoc.HOCCommand;
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;

/**
 * @brief clamps, i. e. Point Processes from NEURON, currently VClamp and IClamp selectable
 * @author stephan
 */
@ComponentInfo(name="Clamp", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class Clamp extends HOCCommand implements Serializable {
    private static final long serialVersionUID = 1L;
    
 	 /**
     * @brief default ctor
     */
    public Clamp() {
	    
    }
	
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
		
	@ParamGroupInfo(group="Point Process|true; Geometry|false")
	@ParamInfo(name="Load", style="hoc-load-dialog", options="hoc_tag=\"gridFile\"") File hoc_file,
	    
	@ParamGroupInfo(group="Point Process|false; Geometry|false")
	@ParamInfo(name="Sections", style="default", options="hoc_tag=\"gridFile\"") Section sectionTest
	    
    ) {
	    if ( ! (m_transformator == null) ) {
		    if (! (sectionTest == null)) {
		    	for (int i = 0; i < sectionTest.get_names().size(); i++) {
			    System.err.println(sectionTest.get_names().get(i));
			    m_transformator.execute_hoc_stmt("objectvar stim" + i);
			    if (stimType.equals("VClamp")) {
				    m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stim"+i + "= new VClamp(" + stimLoc + ")");
			    } else if (stimType.equals("IClamp")) {
				    m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stim"+i + "= new IClamp(" + stimLoc + ")");
			    } else {
				   System.err.println("hoc statement could not be executed."); 
			    }
			}
		    }
	    } else {
          	eu.mihosoft.vrl.system.VMessage.info("HOC interpreter not initialized", "Check if Clamp instance has ingoing connection of a HOC interpreter (I_Transformator).");
	    }
    }

   
  }
