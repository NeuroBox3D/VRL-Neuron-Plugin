/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.neuron;

import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.Serializable;

/**
 *
 * @author stephangrein
 */

@ComponentInfo(name="HOC", category="NeuronPlugin/Tools", description="Enable HOC interpreter (NEURON functionality to UG/VRL)")
public class HOC implements Serializable {
        private static final long serialVersionUID=1L;
	// TODO: initialize HOC instance
	private Interpreter interpreter;
	
	/**
	 * @brief ctor
	 * @param cmd initial command
	 */
	public HOC
		(
		@ParamInfo(name="initial HOC command") String cmd
		) {
		// TODO: setup hoc instance
		interpreter = new Interpreter();
	}

	/**
	 * @brief default ctor
	 */
	public HOC() {
	}
	
	public void init() {
		if (interpreter == null) {
			this.interpreter = new Interpreter();
		} else {
			System.out.println("Interpreter instance already present for current HOC instance!");
		}
		
	}
	

	public Interpreter get_hoc_interpreter() {
		return this.interpreter;
		// TODO: return hoc instance
	}
	
	
}
