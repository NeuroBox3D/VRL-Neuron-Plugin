/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.util;

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
    private static final int COLUMNS = 2;

    public Trajectory plot(
        @ParamInfo(name="Label", style="default", options="") String label,
        @ParamInfo(name="", style="load-dialog", options="") File input,
	@ParamInfo(name="Delimiter (RE)") String delimiter,
        @ParamInfo(name="First Column", style="default", options="value=0") int first,
        @ParamInfo(name="Second Column", style="default", options="value=1") int second){

        Trajectory trajectory = new Trajectory();
        trajectory.setLabel(label);

	try {
		BufferedReader br = new BufferedReader(new FileReader(input));
		String line;
		while ((line = br.readLine()) != null) {
			if (!line.trim().startsWith("#") && !line.trim().isEmpty()) {
				String[] numbers = line.split(delimiter);
				if (numbers.length == COLUMNS) {
					try {
						double t = Double.parseDouble(numbers[first]);
						double value = Double.parseDouble(numbers[second]);
						trajectory.add(t, value);
					} catch (NumberFormatException e) {
						System.err.println("Malformed line in input file: " + input.getAbsolutePath() + ". Skipping this entry because of error: " + e.toString());
					}
				}
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