/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Section;
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;

/**
 * @brief inserts membrane mechanisms
 * @author stephan
 */
@ComponentInfo(name = "HOCMembraneMechanismInserter", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCMembraneMechanismInserter extends HOCCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief ctor
	 */
	public HOCMembraneMechanismInserter() {

	}

	/**
	 * @brief sets the transformator
	 * @param transformator
	 */
	@Override
	public void set_transformator(
		@ParamInfo(name = "HOC Interpreter") I_Transformator transformator) {
		super.set_transformator(transformator);
	}

	/**
	 * @brief gets the transformator
	 * @return transformator
	 */
	@Override
	@OutputInfo(name = "HOC Interpreter")
	public I_Transformator get_transformator() {
		return super.get_transformator();
	}

	/**
	 * @brief inserts a channel / membrane mechanism i. e. pas or hh
	 * @param hoc_file
	 * @param sectionTest
	 * @param mechanism
	 * @return
	 */
	public boolean membrane_mechanism(
		@ParamGroupInfo(group = "Membrane mechanism|true|Inserts membrane mechanisms for compartments; Mechanism|false")
		@ParamInfo(name = "Mechanism", typeName="Any of the common plasma membrane mechanisms", style = "selection", options = "value=[\"pas\", \"hh\"]") String mechanism,
		@ParamGroupInfo(group = "Membrane mechanism|true|Inserts membrane mechanisms for compartments; Geometry|false")
		@ParamInfo(name = "Load", typeName="Any hoc file can be loaded", style = "hoc-load-dialog", options = "hoc_tag=\"gridFile\"") File hoc_file,
		@ParamGroupInfo(group = "Membranche mechanism|false|Inserts membrane mechanisms for compartments; Geometry|false")
		@ParamInfo(name = "Sections", typeName="Compartments of the multi-compartmental model we loaded", style = "default", options = "hoc_tag=\"gridFile\"") Section sectionTest
	) {
		boolean success = true;
		if (!(m_transformator == null)) {
			if (!(sectionTest == null)) {
				for (int i = 0; i < sectionTest.get_names().size(); i++) {
					boolean st1 = (m_transformator.execute_hoc_stmt("access " + sectionTest.get_names().get(i)) == 0);
					boolean st2 = (m_transformator.execute_hoc_stmt("insert " + mechanism) == 0);
					success = (success && st1 && st2);
				}
			}
		} else {
			return error(this);
		}
	return success;
	}
}
