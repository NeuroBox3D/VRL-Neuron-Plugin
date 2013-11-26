/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.neuron;

import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import java.io.Serializable;

/**
 *
 * @author stephangrein
 */
@TypeInfo(type=Interpreter.class, input = false, output = true, style="default")
public class InterpreterType extends TypeRepresentationBase implements Serializable {
        private static final long serialVersionUID=1L;
	public InterpreterType() {
	}
}