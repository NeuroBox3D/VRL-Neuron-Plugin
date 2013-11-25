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

@ComponentInfo(name="Mapper", category="NeuronPlugin/Tools", description="Vm2uG")
public class Mapper implements Serializable {
        private static final long serialVersionUID=1L;
	private double vm;
	private Interpreter interpreter;
 	// TODO: private Vm2uG instance here

	/**
	 * @brief primitive ctor
	 * @param path 
	 */
	public Mapper(
		@ParamInfo(name="path", style="default", options="value=Path to timestep basedir") String path
		) { 
		// TODO: create Vm2uG insstance
	}


	/**
	 * @brief hoc ctro
	 * @param interpreter 
	 */
	public Mapper(
		@ParamInfo(name="interprter", style="default") Interpreter interpreter
		) {
		this.interpreter = interpreter;
	}
	
	public void init(
		@ParamInfo(name="interpreter", style="default") Interpreter interpreter
		) {
		if (interpreter == null) {
			this.interpreter = interpreter;
		} else {
			System.out.println("Interpreter already presenet in Mapper!");
		}
	}
	/**
	 * @brief default ctor
	 */
	public Mapper() {
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param t
	 * @return 
	 */
	public double mapping(
		@ParamInfo(name="x coordinate", options="value=0") double x,
		@ParamInfo(name="y coordiante", options="value=0") double y,
		@ParamInfo(name="z coordiante", options="value=0") double z,
		@ParamInfo(name="time", options="value=0") double t
		) {
		return invoke(x, y, z, t);		
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param t
	 * @return 
	 */
	private double invoke(double x, double y, double z, double t) { 
		return this.vm;
	}
	
}
