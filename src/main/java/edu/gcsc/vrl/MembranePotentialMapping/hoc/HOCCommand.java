/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.system.VMessage;

/**
 * @brief abstract base class for all HOC commands respectively NEURON
 * components
 * @author stephan
 */
public abstract class HOCCommand {

	@SuppressWarnings("ProtectedField")
	protected I_Transformator m_transformator;

	/**
	 *
	 * @brief sets the HOC interpreter instance
	 * @param transformator the hoc interpreter
	 */
	public void set_transformator(
		I_Transformator transformator) {
		m_transformator = transformator;
	}

	/**
	 * @brief gets the HOC interpreter instance
	 * @return transformator
	 */
	@SuppressWarnings("all")
	public I_Transformator get_transformator() {
		return m_transformator;
	}

	/**
	 * @brief prints out an error message on the canvas
	 * @param component
	 * @return 
	 */
	protected boolean error(HOCCommand component) {
		VMessage.info("HOC interpreter not initialized",
			"Check if " + component.getClass().getSimpleName() + " instance has ingoing connection "
			+ "of a HOC interpreter (I_Transformator).");
		return false;
	}
}
