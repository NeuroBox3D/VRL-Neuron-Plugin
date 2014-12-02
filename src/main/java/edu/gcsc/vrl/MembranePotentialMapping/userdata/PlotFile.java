/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.userdata;

/// imports
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.math.Trajectory;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @brief PlotFile (compatible to JDK 6)
 * @author stephan
 * @note adapted by PlotFile by MB & MS
 */
@ComponentInfo(name="PlotFile", category="Custom")
public class PlotFile implements java.io.Serializable {
    private static final long serialVersionUID=1L;

    public Trajectory plot(
        @ParamInfo(name="Label", style="default", options="") String label,
        @ParamInfo(name="", style="load-dialog", options="") File input,
	@ParamInfo(name="Delimiter (RE)", options="value=\\s") String delimiter,
        @ParamInfo(name="First Column", style="default", options="") int first,
        @ParamInfo(name="Second Column", style="default", options="") int second){

        Trajectory trajectory = new Trajectory();
        trajectory.setLabel(label);

	try {
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		while ((line = br.readLine()) != null) {
			if (!line.trim().startsWith("#") && !line.trim().isEmpty()) {
				String[] numbers = line.split(delimiter);
				double t = Double.parseDouble(numbers[first]);
				double value = Double.parseDouble(numbers[second]);
				trajectory.add(t, value);
			}
		}
	br.close();
	} catch (FileNotFoundException e) {
		System.err.println(e);
	} catch (IOException e) {
		System.err.println(e);
	}
	
        return trajectory;
    }

}