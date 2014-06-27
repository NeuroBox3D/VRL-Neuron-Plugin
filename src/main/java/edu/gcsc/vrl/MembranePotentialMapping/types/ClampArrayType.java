/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.types;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Clamp;
import edu.gcsc.vrl.userdata.types.UserDataArrayBaseType;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;

/**
 *
 * @author sgrein
 */
@TypeInfo(type = Clamp[].class, input = true, output = false, style = "array")
public class ClampArrayType extends UserDataArrayBaseType {
    private static final long serialVersionUID = 1L;
    
	/**
	 * @brief default ctor
	 */
	public ClampArrayType() {

	}
	
	/**
	 * @brief necessary for code generation
	 * @return code as String with escapes
	 */
	@Override
	public String getValueAsCode() {
 	       return "\"" + VLangUtils.addEscapesToCode(getValue().toString()) + "\"";
	}
}
