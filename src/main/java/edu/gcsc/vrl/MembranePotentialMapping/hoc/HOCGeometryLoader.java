/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;


/**
 * @brief geometry loader
 * @author stephan
 */
@ComponentInfo(name="HOCGeometryLoader", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCGeometryLoader extends HOCCommand implements Serializable {
    private static final long serialVersionUID = 1L;


    public HOCGeometryLoader() {
	    
    }
    /**
     * 
     * @brief set's the HOC interpreter instance
     * @param transformator the hoc interpreter
     */
    @Override
    public void set_transformator(
	    @ParamInfo(name="HOC Interpreter")
	    I_Transformator transformator) {
	    super.set_transformator(transformator);
    }

    /**
     * @brief get's the HOC interpreter instance
     * @return the hoc interpreter
     */
    @SuppressWarnings("all")
    @OutputInfo(name="HOC Interpreter")
    @Override
    public I_Transformator get_transformator() {
	    return super.get_transformator();
    }

    /**
     * @brief loads a hoc geometry into the given interpreter
     * @param hoc_file 
     */
    public void load_geometry(
	@ParamInfo(name="Load", style="load-dialog")
	File hoc_file
    ) {
	    if ( ! (m_transformator == null) ) { 
		    System.err.println(hoc_file.toString());
		    m_transformator.load_geom(hoc_file.toString());
	    } else {
          	eu.mihosoft.vrl.system.VMessage.info("HOC interpreter not initialized", "Check if Clamp instance has ingoing connection of a HOC interpreter (I_Transformator).");
	    }
    }
	
}
