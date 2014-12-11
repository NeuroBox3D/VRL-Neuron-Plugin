/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.types;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Clamp;
import edu.gcsc.vrl.userdata.types.UserDataArrayBaseType;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;

/**
 * @brief clamp array types
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
	 * @return
	 */
	@Override
	public String getValueAsCode() {
		return "\"" + VLangUtils.addEscapesToCode(getValue().toString()) + "\"";
	}
}
