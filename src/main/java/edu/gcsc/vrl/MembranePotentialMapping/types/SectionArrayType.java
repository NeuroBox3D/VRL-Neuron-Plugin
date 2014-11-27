/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.types;

import edu.gcsc.vrl.MembranePotentialMapping.userdata.Section;
import edu.gcsc.vrl.userdata.types.UserDataArrayBaseType;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;

/**
 *
 * @author stephan
 */
@TypeInfo(type = Section[].class, input = true, output = false, style = "array")
public class SectionArrayType extends UserDataArrayBaseType {
	private static final long serialVersionUID = 1L;
	
	public SectionArrayType() {
        	setValueName("Section Array");
    }

    /**
     * Evaluates the contract, e.g., checks for correct data type or range
     * condition.
     */
    @Override
    protected void evaluateContract()
    {
        // deactivated contract evaluation to prevent multiple error messages
        // due to null array

        if (value == null) invalidateValue();
    }
    
	    @Override
    public String getValueAsCode() {
        return "\""
                + VLangUtils.addEscapesToCode(getValue().toString()) + "\"";
    }
    
}
