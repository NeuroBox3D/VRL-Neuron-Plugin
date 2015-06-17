/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.Serializable;

/**
 * @brief HOC command class
 * @author stephan
 */
@ComponentInfo(name = "HOCExecuteStatement", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCExecuteStatement extends HOCCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief ctor
	 */
	public HOCExecuteStatement() {
	}

	@Override
	public void set_transformator(
		@ParamInfo(name = "HOC Interpreter", typeName="The NEURON interpreter") I_Transformator transformator) {
		super.set_transformator(transformator);
		
	}

	@Override
	@MethodInfo(valueName = "HOC Interpreter",
		    valueTypeName="The NEURON interpreter")
	public I_Transformator get_transformator() {
		return super.get_transformator();
	}

	/**
	 * @brief executes a HOC command string
	 * @param cmd_str
	 * @return
	 */
	@MethodInfo(valueName = "Success", valueTypeName="Success")
	public boolean cmd_str(
		@ParamInfo(name = "Command String", typeName="Any valid HOC code", style = "code") String cmd_str
	) {
		if (!(m_transformator == null)) {
			return (m_transformator.execute_hoc_stmt(cmd_str) == 0);
		} else {
			return error(this);
		}
	}
}
