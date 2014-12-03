/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Section;
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author stephan
 */
@ComponentInfo(name="VClamp", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class VClamp extends HOCCommand implements Serializable {
    private static final long serialVersionUID = 1L;

	/**
	 * @brief default ctor
	 */
	public VClamp() {
		
	}


    /**
     * @brief VClamp
     * 
     * @param stimDur
     * @param stimDel
     * @param stimAmp
     * @param stimLoc
     * @param hoc_file
     * @param sectionTest 
     */
    public void clamp(
	@ParamGroupInfo(group="VClamp|false; Stimulation|false")
    	@ParamInfo(name="stimDur", style="default") double stimDur,
	@ParamGroupInfo(group="VClamp|false; Stimulation|false")
	@ParamInfo(name="stimDel") double stimDel,
	@ParamGroupInfo(group="VClamp|false; Stimulation|false")
	@ParamInfo(name="stimAmp") double stimAmp,
	@ParamGroupInfo(group="VClamp|false; Stimulation|false")
	@ParamInfo(name="stimLoc") double stimLoc,
		
	@ParamGroupInfo(group="VClamp|true; Geometry|false")
	@ParamInfo(name="Load", style="hoc-load-dialog", options="hoc_tag=\"gridFile\"") File hoc_file,
	    
	@ParamGroupInfo(group="VClamp|false; Geometry|false")
	@ParamInfo(name="Sections", style="default", options="hoc_tag=\"gridFile\"") Section sectionTest
    ) {
	     if ( ! (m_transformator == null) ) {
		    if (! (sectionTest == null)) {
		    	for (int i = 0; i < sectionTest.get_names().size(); i++) {
			    System.err.println(sectionTest.get_names().get(i));
			    m_transformator.execute_hoc_stmt("objectvar stim" + i);
			    m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stim"+i + "= new VClamp(" + stimLoc + ")");
			}
		    }
	    } else {
          	eu.mihosoft.vrl.system.VMessage.info("HOC interpreter not initialized", "Check if Clamp instance has ingoing connection of a HOC interpreter (I_Transformator).");
	    }
    }
}
