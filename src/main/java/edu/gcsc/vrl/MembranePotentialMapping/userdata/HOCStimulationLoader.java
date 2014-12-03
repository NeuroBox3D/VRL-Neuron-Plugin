/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.userdata;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;


/**
 *
 * @author stephan
 */
	
@ComponentInfo(name="HOCStimulationLoader", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCStimulationLoader implements Serializable {
    private static final long serialVersionUID = 1L;
    private I_Transformator m_transformator = null;


    public HOCStimulationLoader() {
	    
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
    public I_Transformator get_transformator() {
	    return m_transformator;
    }

    /**
     * @brief loads a hoc geometry into the given interpreter
     * @param hoc_file 
     */
    public void load_stimulation(
	@ParamInfo(name="Load", style="hoc-load-dialog", options="hoc_tag=\"gridFile\"") 
	File hoc_file
    ) {
	    if ( ! (m_transformator == null) ) { 
		    m_transformator.load_geom(hoc_file.getAbsolutePath());
	    }
    }
	
}

