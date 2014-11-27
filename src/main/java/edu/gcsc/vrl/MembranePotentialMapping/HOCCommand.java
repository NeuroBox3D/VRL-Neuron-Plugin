/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.visual.VTextField;
import java.io.Serializable;

/**
 *
 * @author stephan
 */
@ComponentInfo(name="HOCCommand", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCCommand implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * @brief default ctor
	 */
	public HOCCommand() {
			  
	}
	
	/**
	 * @brief command str
	 * @param cmd_str hoc code to be executed
	 * @return 
	 * TODO: proper implementation here necessary (execute_hoc_stmt should be made synchronized anyway here!)
	 */
	public boolean cmd_str(
		@ParamInfo(name="Command String", style="code") String cmd_str
		) {
			Double ret = 0.;
			synchronized(this) {
				ret = HOCInterpreter.getInstance().getTransformator().execute_hoc_stmt(cmd_str);
			}
			return true;
	}
}
