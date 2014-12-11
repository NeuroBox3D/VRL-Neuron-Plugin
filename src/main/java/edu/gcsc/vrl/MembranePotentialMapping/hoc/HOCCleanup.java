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
@ComponentInfo(name = "HOCCleanup", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCCleanup extends HOCCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * @brief ctor
	 */
	public HOCCleanup() {

	}

	/**
	 * @brief sets the transformator
	 * @param transformator 
	 */
	@Override
	public void set_transformator(
		@ParamInfo(name = "HOC Interpreter", typeName="The NEURON interpreter") I_Transformator transformator) {
		super.set_transformator(transformator);
	}

	/**
	 * @brief gets the transformator
	 * @return transformator
	 */
	@Override
	@OutputInfo(name = "HOC Interpreter", typeName="The NEURON interpreter")
	public I_Transformator get_transformator() {
		return super.get_transformator();
	}

	/**
	 * @brief cleans the HOC interpreter up, i. e. by deleting all sections
	 * and points. all other variables are also deleted, especially also the
	 * clamps are deleted implicit, since no section is refering to it
	 * anymore.
	 *
	 * we reset also the tstart, tstop, dt value to its defaults, i. e. 0
	 */
	public void cleanup() {
		if (!(m_transformator == null)) {
			m_transformator.execute_hoc_stmt("forall pt3dclear()");
			m_transformator.execute_hoc_stmt("forall delete_section()");
			m_transformator.execute_hoc_stmt("tstart = 0");
			m_transformator.execute_hoc_stmt("tstop = 0");
			m_transformator.execute_hoc_stmt("dt = 0");

		} else {
			error(this);
		}
	}
}
