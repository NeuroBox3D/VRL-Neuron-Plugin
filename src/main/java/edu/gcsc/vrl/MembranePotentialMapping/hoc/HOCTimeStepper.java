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
@ComponentInfo(name="HOCTimeStepper", category="/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCTimeStepper extends HOCCommand implements Serializable {
	private static final long serialVersionUID = 1L;
	

	/**
	 * @brief default ctor
	 */
	public HOCTimeStepper() {
		
	}
	
  @Override
    public void set_transformator(
	    @ParamInfo(name="HOC Interpreter")
	    I_Transformator transformator) {
	    super.set_transformator(transformator);
    }
    
    @Override
    @OutputInfo(name="HOC Interpreter")
    public I_Transformator get_transformator() {
	    return super.get_transformator();
    }
    
    public void step(
	@ParamGroupInfo(group="TimeStepper|true; Output|false")
        @ParamInfo(name="Write potentials")
	boolean generateOutput,

	@ParamGroupInfo(group="TimeStepper|true; Output|false")
	@ParamInfo(name="Output folder", style="save-folder-dialog")
	String folder,

	@ParamGroupInfo(group="TimeStepper|true; Setup|false")
	@ParamInfo(name="Start time [s]")
	double t_start,
	
	@ParamGroupInfo(group="TimeStepper|true; Setup|false")
	@ParamInfo(name="End time [s]")
	double t_end,
	
	@ParamGroupInfo(group="TimeStepper|true; Setup|false")
	@ParamInfo(name="Initial potential [mV[")
	double finit,
	
	@ParamGroupInfo(group="TimeStepper|true; Setup|false")
	@ParamInfo(name="dt [ms]")
	double dt,
	    
	@ParamGroupInfo(group="TimeStepper|true; Geometry|false")
	@ParamInfo(name="Load", style="hoc-load-dialog", options="hoc_tag=\"gridFile\"") 
	File hoc_file,
	    
	@ParamGroupInfo(group="TimeStepper|false; Geometry|false")
	@ParamInfo(name="Sections", style="default", options="hoc_tag=\"gridFile\"") 
	Section sectionTest
	) {
	    
 if ( ! (m_transformator == null) ) {
	 m_transformator.setup_hoc(t_start, t_end, dt, finit);
		    if (! (sectionTest == null)) {
			for (int j = 0; j < ((t_end - t_start) / dt) - 1; j++) {
		   		m_transformator.fadvance();
		    		for (int i = 0; i < sectionTest.get_names().size(); i++) {
					@SuppressWarnings("UnusedAssignment")
					BufferedWriter out = null;
					try {
 		   			FileWriter fstream = new FileWriter(folder + "/" + sectionTest.get_names().get(i) + ".csv", true); 
 				   	out = new BufferedWriter(fstream);
					m_transformator.execute_hoc_stmt("access " + sectionTest.get_names().get(i));
			   		out.write("\n" + m_transformator.get_t() + ", " + m_transformator.get_hoc_variable("v"));
			   		out.close();
				} catch (IOException e) {
 		   		System.err.println("Error: " + e.getMessage());
				}
			}
		}
	    } else {
          	eu.mihosoft.vrl.system.VMessage.info("HOC interpreter not initialized", "Check if Clamp instance has ingoing connection of a HOC interpreter (I_Transformator).");
	}
 }
    }
  }