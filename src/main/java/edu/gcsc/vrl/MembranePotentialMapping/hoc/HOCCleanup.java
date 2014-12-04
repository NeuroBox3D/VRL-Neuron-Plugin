/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.Serializable;

/**
 * @brief cleanups the environment (the interpreter)
 * @author stephan
 */
@ComponentInfo(name="HOCCleanup", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCCleanup extends HOCCommand implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @brief ctor
	 */
	public HOCCleanup() {
		
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
     * @brief purge
     */
    public void cleanup() {
	if (! (m_transformator == null)) {
		m_transformator.purge();
	} else {
          	eu.mihosoft.vrl.system.VMessage.info("HOC interpreter not initialized", "Check if Clamp instance has ingoing connection of a HOC interpreter (I_Transformator).");
	}
    }
}
