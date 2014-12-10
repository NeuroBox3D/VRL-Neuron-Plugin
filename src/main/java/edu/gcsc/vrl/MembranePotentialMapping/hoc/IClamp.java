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
 * @brief one electrode current clamp
 * @author stephan
 */
@ComponentInfo(name = "IClamp", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class IClamp extends HOCCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief default ctor
	 */
	public IClamp() {

	}

	@Override
	public void set_transformator(
		@ParamInfo(name = "HOC Interpreter", typeName="The NEURON interpreter") I_Transformator transformator) {
		super.set_transformator(transformator);
	}

	@Override
	@OutputInfo(name = "HOC Interpreter", typeName="The NEURON interpreter")
	public I_Transformator get_transformator() {
		return super.get_transformator();
	}

	/**
	 * @brief IClamp
	 * @param stimDur
	 * @param stimDel
	 * @param stimAmp
	 * @param stimLoc
	 * @param hoc_file
	 * @param sectionTest
	 * @return 
	 */
	public boolean clamp(
		@ParamGroupInfo(group = "IClamp|false; Stimulation|false")
		@ParamInfo(name = "stimDur [ms]", typeName="Duration of stimulation", style = "default") double stimDur,
		@ParamGroupInfo(group = "IClamp|false; Stimulation|false")
		@ParamInfo(name = "stimDel [ms]", typeName="Delay until stimulation") double stimDel,
		@ParamGroupInfo(group = "IClamp|false; Stimulation|false")
		@ParamInfo(name = "stimAmp [nA]", typeName="Amplitude of stimulation") double stimAmp,
		@ParamGroupInfo(group = "IClamp|false; Stimulation|false")
		@ParamInfo(name = "stimLoc", typeName="Location of stimulation electrode, typically 0.5 is used and refers to the center of the desired compartment", options = "value=0.5") double stimLoc,
		@ParamGroupInfo(group = "IClamp|true; Geometry|false")
		@ParamInfo(name = "Load", typeName="Load any hoc geometry", style = "hoc-load-dialog", options = "hoc_tag=\"gridFile\"") File hoc_file,
		@ParamGroupInfo(group = "IClamp|false; Geometry|false")
		@ParamInfo(name = "Sections", typeName="The compartment of the multi-compartmental model loaded", style = "default", options = "hoc_tag=\"gridFile\"") Section sectionTest
	) {
		boolean success = true;
		if (!(m_transformator == null)) {
			if (!(sectionTest == null)) {
				for (int i = 0; i < sectionTest.get_names().size(); i++) {
					boolean st1 = (m_transformator.execute_hoc_stmt("access " + sectionTest.get_names().get(i)) == 0);
					boolean st2 = (m_transformator.execute_hoc_stmt("objectvar stimi_" + i) == 0);
					boolean st3 = (m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stimi_" + i + "= new IClamp(" + stimLoc + ")") == 0);
					boolean st4 = (m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " insert hh") == 0);
					boolean st5 = (m_transformator.execute_hoc_stmt("stimi_" + i + ".amp = " + stimAmp) == 0);
					boolean st6 = (m_transformator.execute_hoc_stmt("stimi_" + i + ".dur = " + stimDur) == 0);
					success = (success && st1 && st2 && st3 && st4 && st5 && st6);
				}
			}
		} else {
			return error(this);
		}
		return success;
	}
}
