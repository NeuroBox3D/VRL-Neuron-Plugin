/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import eu.mihosoft.vrl.annotation.ComponentInfo;
import java.io.Serializable;

/**
 * @brief single electrode voltage clamp
 * @author stephan
 */
@ComponentInfo(name="SEClamp", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class SEClamp extends HOCCommand implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @brief ctor
	 */
	public SEClamp() {
		
	}
	
	public void clamp() {
		/**
		 * @todo implement
		 */
		System.err.println("Currently not implemented!");
	}
}
