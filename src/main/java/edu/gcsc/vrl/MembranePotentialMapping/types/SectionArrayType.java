/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.types;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Section;
import edu.gcsc.vrl.userdata.types.UserDataArrayBaseType;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;
import eu.mihosoft.vrl.types.ArrayBaseType;

/**
 * @brief a section array type
 * @author stephan
 */
@TypeInfo(type = Section[].class, input = true, output = false, style = "array")
public class SectionArrayType extends ArrayBaseType {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief default ctor
	 */
	public SectionArrayType() {
		setValueName("Section Array");
	}

	/**
	 * @brief Evaluates the contract, e.g., checks for correct data type or
	 * range condition.
	 */
	@Override
	protected void evaluateContract() {
        // deactivated contract evaluation to prevent multiple error messages
		// due to null array
		if (value == null) {
			invalidateValue();
		}
	}

	/**
	 * @brief for code generation
	 * @return
	 */
	@Override
	public String getValueAsCode() {
		return "\""
			+ VLangUtils.addEscapesToCode(getValue().toString()) + "\"";
	}

}
