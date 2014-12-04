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
 * @brief two electrode voltage clamp
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
	
    @Override
    public void set_transformator(
	    @ParamInfo(name="HOC Interpreter")
	    I_Transformator transformator) {
	    super.set_transformator(transformator);
    }
    
    @Override
    @OutputInfo(name="HOC Interpreter")
    public I_Transformator get_transformator() {
	    return super.get_transformator();
    }


    /**
     * @brief VClamp
     * 
     * @param stimDur1
     * @param stimDur2
     * @param stimDur3
     * @param stimDel
     * @param stimAmp1
     * @param stimAmp2
     * @param stimAmp3
     * @param stimGain
     * @param tau1
     * @param tau2
     * @param rstim
     * @param stimLoc
     * @param hoc_file
     * @param sectionTest 
     */
    public void clamp(
	@ParamGroupInfo(group="VClamp|false; Stimulation Duration|false")
    	@ParamInfo(name="stimDur1", style="default") 
	double stimDur1,
	@ParamGroupInfo(group="VClamp|false; Stimulation Duration|false")
    	@ParamInfo(name="stimDur2", style="default") 
	double stimDur2,
	@ParamGroupInfo(group="VClamp|false; Stimulation Duration|false")
    	@ParamInfo(name="stimDur3", style="default") 
	double stimDur3,
	
	@ParamGroupInfo(group="VClamp|false; Stimulation Deletion|false")
	@ParamInfo(name="stimDel") 
	double stimDel,
	
	@ParamGroupInfo(group="VClamp|false; Stimulation Amplitude|false")
	@ParamInfo(name="stimAmp1") 
	double stimAmp1,
	@ParamGroupInfo(group="VClamp|false; Stimulation Amplitude|false")
	@ParamInfo(name="stimAmp2") 
	double stimAmp2,
	@ParamGroupInfo(group="VClamp|false; Stimulation Amplitude|false")
	@ParamInfo(name="stimAmp3") 
	double stimAmp3,
	
	@ParamGroupInfo(group="VClamp|false; Stimulation Gain|false")
	@ParamInfo(name="stimGain") 
	double stimGain,
	
	@ParamGroupInfo(group="VClamp|false; Time constants|false")
	@ParamInfo(name="tau1") 
	double tau1,
	@ParamGroupInfo(group="VClamp|false; Time constants|false")
	@ParamInfo(name="tau2") 
	double tau2,
	
	@ParamGroupInfo(group="VClamp|false; Stimulation r|false")
	@ParamInfo(name="rstim")
	double rstim,
	
	@ParamGroupInfo(group="VClamp|false; Stimulation Location|false")
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
			    m_transformator.execute_hoc_stmt("access " + sectionTest.get_names().get(i));
			    m_transformator.execute_hoc_stmt("objectvar stimv_" + i);
			    m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stimv_"+i + "= new VClamp(" + stimLoc + ")");
			    m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " insert hh");
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".dur(0) =" + stimDur1);
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".dur(1) =" + stimDur2);
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".dur(2) =" + stimDur3);
			    
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".amp(0) =" + stimAmp1);
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".amp(1) =" + stimAmp2);
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".amp(2) =" + stimAmp3);
			    
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".gain = " + stimGain);
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".tau1 = " + tau1);
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".tau2 = " + tau2);
			    m_transformator.execute_hoc_stmt("stimv_" + i +  ".rstim = " + rstim);
			}
		    }
	    } else {
          	eu.mihosoft.vrl.system.VMessage.info("HOC interpreter not initialized", "Check if VClamp instance has ingoing connection of a HOC interpreter (I_Transformator).");
	    }
    }
}
