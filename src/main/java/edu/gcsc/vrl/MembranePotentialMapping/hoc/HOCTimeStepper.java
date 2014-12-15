/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Section;
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamGroupInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @author stephan
 */
@ComponentInfo(name = "HOCTimeStepper", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCTimeStepper extends HOCCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief ctor
	 */
	public HOCTimeStepper() {

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
	 * @return transformator
	 */
	@Override
	@OutputInfo(name = "HOC Interpreter", typeName="The NEURON interpreter")
	public I_Transformator get_transformator() {
		return super.get_transformator();
	}

	/**
	 * @brief simulate a number of timesteps with NEURON
	 * @param generateOutput
	 * @param folder
	 * @param t_start
	 * @param t_end
	 * @param finit
	 * @param dt
	 * @param hoc_file
	 * @param sectionTest
	 * @return
	 */
	public boolean step(
		@ParamGroupInfo(group = "TimeStepper|true; Output|false")
		@ParamInfo(name = "Write potentials", typeName="Indicate if we should write membrane potentials to files") boolean generateOutput,
		@ParamGroupInfo(group = "TimeStepper|true; Output|false")
		@ParamInfo(name = "Output folder",  typeName="Output folder for membrane potentials", style = "save-folder-dialog") String folder,
		@ParamGroupInfo(group = "TimeStepper|true; Setup|false")
		@ParamInfo(name = "Start time [s]", typeName="The simulation starting time", style="slider", options="value=1;min=0;max=1000;step=0.1") double t_start,
		@ParamGroupInfo(group = "TimeStepper|true; Setup|false")
		@ParamInfo(name = "End time [s]", typeName="The simulation end time", style="slider", options="value=100;min=0;max=10000;step=1") double t_end,
		@ParamGroupInfo(group = "TimeStepper|true; Setup|false")
		@ParamInfo(name = "Initial potential [mV]", typeName="The initial potential for all compartments", style="slider", options="value=-65; min=-75;max=100;step=0.1") double finit,
		@ParamGroupInfo(group = "TimeStepper|true; Setup|false")
		@ParamInfo(name = "dt [ms]", typeName="The timestep width", style="slider", options="value=0;min=0;max=2;step=0.0001") double dt,
		@ParamGroupInfo(group = "TimeStepper|true; Geometry|false")
		@ParamInfo(name = "Load", typeName="Load any hoc geometry file", style = "hoc-load-dialog", options = "hoc_tag=\"gridFile\"") File hoc_file,
		@ParamGroupInfo(group = "TimeStepper|false; Geometry|false")
		@ParamInfo(name = "Sections", typeName="The compartments in the multi-compartmental model we loaded", style = "default", options = "hoc_tag=\"gridFile\"") Section sectionTest
	) {

		boolean success = true;
		if (!(m_transformator == null)) {
			if (((int) Math.signum(dt)) == -1) {
				eu.mihosoft.vrl.system.VMessage.info("Negative timestep", "Make sure you want a negative timestep. The timestep was: " + dt);
			}
			boolean st1 = (m_transformator.execute_hoc_stmt("tstart = " + t_start) == 0);
			boolean st2 = (m_transformator.execute_hoc_stmt("tstop = " + t_end) == 0);
			boolean st3 = (m_transformator.execute_hoc_stmt("dt = " + dt) == 0);
			boolean st4 = (m_transformator.execute_hoc_stmt("finitialize(" + finit + ")") == 0);
			success = (success && st1 && st2 && st3 && st4);
			if (!(sectionTest == null)) {
				for (int j = 0; j < ((t_end - t_start) / dt) - 1; j++) {
					boolean st5 = m_transformator.fadvance();
					success = (success && st5);
					for (int i = 0; i < sectionTest.get_names().size(); i++) {
						@SuppressWarnings("UnusedAssignment")
						BufferedWriter out = null;
						try {
							FileWriter fstream = new FileWriter(folder + "/" + sectionTest.get_names().get(i) + ".csv", true);
							out = new BufferedWriter(fstream);
							boolean st6 = (m_transformator.execute_hoc_stmt("access " + sectionTest.get_names().get(i)) == 0);
							success = (success && st6);
							out.write("\n" + m_transformator.get_t() + ", " + m_transformator.get_hoc_variable("v"));
							out.close();
						} catch (IOException e) {
							System.err.println("Error: " + e.getMessage());
						}
					}
				}
			} else {
				return error(this);
			}
		}
		return success;
	}
}
