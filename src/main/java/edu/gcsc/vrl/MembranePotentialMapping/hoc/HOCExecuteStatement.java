/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.HOCInterpreter;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.Serializable;

/**
 * @brief HOC command class
 * @author stephan
 */
@ComponentInfo(name="HOCExecuteStatement", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCExecuteStatement extends HOCCommand implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * @brief default ctor
	 */
	public HOCExecuteStatement() {
	}
	
	public boolean cmd_str(
		@ParamInfo(name="Command String", style="code") String cmd_str
		) {
		if (! (m_transformator == null)) {
			return (HOCInterpreter.getInstance().getTransformator().execute_hoc_stmt(cmd_str) == 0);
		} else {
          	eu.mihosoft.vrl.system.VMessage.info("HOC interpreter not initialized", "Check if Clamp instance has ingoing connection of a HOC interpreter (I_Transformator).");
			return false;
		}
	}
}
