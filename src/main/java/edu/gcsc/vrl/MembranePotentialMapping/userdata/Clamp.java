/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.userdata;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.hoc.HOCCommand;
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.system.VMessage;
import java.io.File;
import java.io.Serializable;

/**
 * @brief clamps, i. e. Point Processes from NEURON, currently VClamp and IClamp
 * selectable
 * @author stephan
 */
@ComponentInfo(name = "Clamp", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class Clamp extends HOCCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief ctor
	 */
	public Clamp() {

	}

	/**
	 * @brief sets the tranformator
	 * @param transformator
	 */
	@Override
	public void set_transformator(
		@ParamInfo(name = "HOC Interpreter", typeName="The NEURON interpreter") I_Transformator transformator) {
		super.set_transformator(transformator);
	}

	/**
	 * @brief gets the transformator
	 * @return
	 */
	@Override
	@OutputInfo(name = "HOC Interpreter", typeName="The NEURON interpreter")
	public I_Transformator get_transformator() {
		return m_transformator;
	}

	/**
	 * @brief adds a point process to the given hoc interpreter
	 * @todo implement the clamp selection correctly
	 *
	 * @param stimType type, i. e. IClamp or VClamp
	 * @param stimDur duration [ms]
	 * @param stimAmp amplitude [nA]
	 * @param stimDel duration of deletion in [ms]
	 * @param hoc_file the hoc file to be loaded
	 * @param sectionTest test the type representation
	 * @param stimLoc
	 * @return
	 */
	public boolean point_process(
		@ParamGroupInfo(group = "Point Process|false; Stimulation|false")
		@ParamInfo(name = "Type", typeName="Type of the stimulating clamp", style = "selection", options = "value=[\"IClamp\", \"VClamp\"]") String stimType,
		@ParamGroupInfo(group = "Point Process|false; Stimulation|false")
		@ParamInfo(name = "stimDur [ms]", typeName="Duration of stimulation", style = "default") double stimDur,
		@ParamGroupInfo(group = "Point Process|false; Stimulation|false")
		@ParamInfo(name = "stimDel [ms]", typeName="Delay until stimulation") double stimDel,
		@ParamGroupInfo(group = "Point Process|false; Stimulation|false")
		@ParamInfo(name = "stimAmp [ms]", typeName="Stimulation amplitude") double stimAmp,
		@ParamGroupInfo(group = "Point Process|false; Stimulation|false")
		@ParamInfo(name = "stimLoc", typeName="Location of stimulation electrode, typically 0.5 is used and refers to the center of the desired compartment") double stimLoc,
		@ParamGroupInfo(group = "Point Process|true; Geometry|false")
		@ParamInfo(name = "Load", typeName="Load any hoc geometry", style = "hoc-load-dialog", options = "hoc_tag=\"gridFile\"") File hoc_file,
		@ParamGroupInfo(group = "Point Process|false; Geometry|false")
		@ParamInfo(name = "Sections", typeName="The compartment of the multi-compartmental model loaded", style = "default", options = "hoc_tag=\"gridFile\"") Section sectionTest
	) {
		boolean success = true;
		if (!(m_transformator == null)) {
			if (!(sectionTest == null)) {
				for (int i = 0; i < sectionTest.get_names().size(); i++) {
					System.err.println(sectionTest.get_names().get(i));
					boolean st1 = (m_transformator.execute_hoc_stmt("objectvar stim" + i) == 0);
					boolean st2 = true;
					if (stimType.equals("VClamp")) {
						st2 = (m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stim" + i + "= new VClamp(" + stimLoc + ")") == 0);
					} else if (stimType.equals("IClamp")) {
						st2 = (m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stim" + i + "= new IClamp(" + stimLoc + ")") == 0);
					} else if (stimType.equals("SEClamp")) {
						st2 = (m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stim" + i + "= new SEClamp(" + stimLoc + ")") == 0);
					} else {
						VMessage.info("Clamp", "Selection must be VClamp, IClamp or SEClamp.");
					}
					success = (success && st1 && st2);
				}
			}
		} else {
			return error(this);
		}
		return success;
	}

}
