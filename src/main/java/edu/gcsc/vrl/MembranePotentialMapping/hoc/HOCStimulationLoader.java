/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;

/**
 * @brief stimulation loader
 * @author stephan
 */
@ComponentInfo(name = "HOCStimulationLoader", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCStimulationLoader extends HOCCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief ctor
	 */
	public HOCStimulationLoader() {

	}

	/**
	 *
	 * @brief sets the HOC interpreter instance
	 * @param transformator the hoc interpreter
	 */
	@Override
	public void set_transformator(
		@ParamInfo(name = "HOC Interpreter", typeName="The NEURON interpreter") I_Transformator transformator) {
		super.set_transformator(transformator);
	}

	/**
	 * @brief gets the HOC interpreter instance
	 * @return the hoc interpreter
	 */
	@SuppressWarnings("all")
	@Override
	@OutputInfo(name = "HOC Interpreter", typeName="The NEURON interpreter")
	public I_Transformator get_transformator() {
		return super.get_transformator();
	}

	/**
	 * @brief loads a hoc geometry into the given interpreter
	 * @param hoc_file
	 * @return
	 */
	@MethodInfo(valueName = "Success", valueTypeName="Success")
	public boolean load_stimulation(
		@ParamInfo(name = "Load", typeName="Any stimulation protocol in a hoc file", style = "load-dialog") File hoc_file
	) {
		if (!(m_transformator == null)) {
			m_transformator.load_stim(hoc_file.toString());
			return true;
		} else {
			return error(this);
		}
	}
}
