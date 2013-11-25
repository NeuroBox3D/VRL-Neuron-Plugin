/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.gcsc.vrl.neuron;

import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;


/**
 *
 * @author stephangrein
 */
@TypeInfo(type=Interpreter.class, input = false, output = true, style="default")
public class InterpreterType extends TypeRepresentationBase {
	public InterpreterType() {
		
	}
@Override()
    public String getValueAsCode() {
        String result = "null";

        Object o = getValue();

        if (o != null) {
            result = o.toString();
        }

        return result;
    }
	
}
