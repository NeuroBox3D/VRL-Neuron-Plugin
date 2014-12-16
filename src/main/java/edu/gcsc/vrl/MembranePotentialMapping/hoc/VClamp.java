/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Section;
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;

/**
 * @brief two electrode voltage clamp
 * @author stephan
 */
@ComponentInfo(name = "VClamp", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class VClamp extends HOCCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief ctor
	 */
	public VClamp() {

	}

	/**
	 * @brief sets the transformator
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
	public I_Transformator get_transformator() {
		return super.get_transformator();
	}

	/**
	 * @brief voltage clamp
	 *
	 * @param stimDur1
	 * @param stimDur2
	 * @param stimDur3
	 * @param stimDel
	 * @param stimAmp1
	 * @param stimAmp2
	 * @param stimAmp3
	 * @param stimGain
	 * @param tau1
	 * @param tau2
	 * @param rstim
	 * @param stimLoc
	 * @param hoc_file
	 * @param sectionTest
	 * @return
	 */
	@MethodInfo(valueName = "Success", valueTypeName="Success")
	public boolean clamp(
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Stimulation Duration|false")
		@ParamInfo(name = "stimDur1 [ms]", typeName="Duration of stimulation", style = "default") double stimDur1,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Stimulation Duration|false")
		@ParamInfo(name = "stimDur2 [ms]", typeName="Duration of stimulation", style = "default") double stimDur2,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Stimulation Duration|false")
		@ParamInfo(name = "stimDur3 [ms]", typeName="Duration of stimulation", style = "default") double stimDur3,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Stimulation Deletion|false")
		@ParamInfo(name = "stimDel [ms]", typeName="Delay until stimulation") double stimDel,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Stimulation Amplitude|false")
		@ParamInfo(name = "stimAmp1 [nA]", typeName="Amplitude of stimulation") double stimAmp1,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Stimulation Amplitude|false")
		@ParamInfo(name = "stimAmp2 [nA]", typeName="Amplitude of stimulation") double stimAmp2,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Stimulation Amplitude|false")
		@ParamInfo(name = "stimAmp3 [nA]", typeName="Amplitude of stimulation") double stimAmp3,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Stimulation Gain|false")
		@ParamInfo(name = "stimGain [nA]", typeName="Gain of stimulation") double stimGain,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Time constants|false")
		@ParamInfo(name = "tau1 [1/ms]", typeName="Time constant of stimulation") double tau1,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Time constants|false")
		@ParamInfo(name = "tau2 [1/ms]", typeName="Time constant of stimulation") double tau2,
		@ParamGroupInfo(group = "VClamp|false; Stimulation r|false")
		@ParamInfo(name = "rstim", typeName="Parameter r of stimulation") double rstim,
		@ParamGroupInfo(group = "VClamp|false|Two electrode voltage clamp; Stimulation Location|false")
		@ParamInfo(name = "stimLoc", typeName="Location of stimulation electrode, typically 0.5 is used and refers to the center of the desired compartment") double stimLoc,
		@ParamGroupInfo(group = "VClamp|true|Geometry; Geometry|false")
		@ParamInfo(name = "Load", typeName="Load any hoc geometry", style = "hoc-load-dialog", options = "hoc_tag=\"gridFile\"") File hoc_file,
		@ParamGroupInfo(group = "VClamp|false|Geometry; Geometry|false")
		@ParamInfo(name = "Sections", typeName="The compartments in the multi-compartmental model loaded", style = "default", options = "hoc_tag=\"gridFile\"") Section sectionTest
	) {
		boolean success = true;
		if (!(m_transformator == null)) {
			if (!(sectionTest == null)) {
				for (int i = 0; i < sectionTest.get_names().size(); i++) {
					System.err.println(sectionTest.get_names().get(i));
					boolean st1 = (m_transformator.execute_hoc_stmt("access " + sectionTest.get_names().get(i)) == 0);
					boolean st2 = (m_transformator.execute_hoc_stmt("objectvar stimv_" + i) == 0);
					boolean st3 = (m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " stimv_" + i + "= new VClamp(" + stimLoc + ")") == 0);
					boolean st4 = (m_transformator.execute_hoc_stmt(sectionTest.get_names().get(i) + " insert hh") == 0);
					boolean st5 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".dur(0) =" + stimDur1) == 0);
					boolean st6 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".dur(1) =" + stimDur2) == 0);
					boolean st7 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".dur(2) =" + stimDur3) == 0);

					boolean st8 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".amp(0) =" + stimAmp1) == 0);
					boolean st9 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".amp(1) =" + stimAmp2) == 0);
					boolean st10 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".amp(2) =" + stimAmp3) == 0);

					boolean st11 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".gain = " + stimGain) == 0);
					boolean st12 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".tau1 = " + tau1) == 0);
					boolean st13 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".tau2 = " + tau2) == 0);
					boolean st14 = (m_transformator.execute_hoc_stmt("stimv_" + i + ".rstim = " + rstim) == 0);
					success = (success && st1 && st2 && st3 && st4 && st5 && st6 && st7 && st8 && st9 && st10 && st11 && st12 && st13 && st14);
				}
			}
		} else {
			return error(this);
		}
		return success;
	}
}
