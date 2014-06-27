/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.types;

import edu.gcsc.vrl.MembranePotentialMapping.userdata.Clamp;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import java.io.Serializable;

/**
 *
 * @author sgrein
 */
@TypeInfo(type = Clamp.class, input = true, output = false, style="default")
public class ClampType extends TypeRepresentationBase implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * @brief default ctor
     */
    public ClampType() {
	    
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
