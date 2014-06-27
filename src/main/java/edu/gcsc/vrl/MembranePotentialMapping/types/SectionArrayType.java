/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.types;

import edu.gcsc.vrl.userdata.types.UserDataArrayBaseType;
import eu.mihosoft.vrl.annotation.TypeInfo;

/**
 *
 * @author stephan
 */
@TypeInfo(type = SectionType[].class, input = true, output = false, style = "array")
public class SectionArrayType extends UserDataArrayBaseType {
	private static final long serialVersionUID = 1L;
	
	public SectionArrayType() {
			  
	}
}
