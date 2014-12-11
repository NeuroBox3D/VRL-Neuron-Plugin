/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import eu.mihosoft.vrl.annotation.ComponentInfo;
import java.io.Serializable;
import eu.mihosoft.vrl.system.VMessage;

/**
 * @brief single electrode voltage clamp
 * @author stephan
 */
@ComponentInfo(name = "SEClamp", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class SEClamp extends HOCCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief ctor
	 */
	public SEClamp() {

	}

	/**
	 * @brief single electrode clamp
	 * @todo implement
	 * @return
	 */
	public boolean clamp() {
		VMessage.error("SEClamp", "Single Electrode Clamp (SEClamp) currently not implemented.");
		return false;
	}
}
