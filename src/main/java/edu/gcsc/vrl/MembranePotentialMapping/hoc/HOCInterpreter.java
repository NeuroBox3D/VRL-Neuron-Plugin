/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.ug.api.AlgebraType;
import edu.gcsc.vrl.ug.api.F_InitUG;
import edu.gcsc.vrl.ug.api.I_Transformator3dCPU1;
import edu.gcsc.vrl.ug.api.Transformator3dCPU1;
import eu.mihosoft.vrl.annotation.ComponentInfo;

/**
 *
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
@ComponentInfo(name = "HOCInterpreter", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCInterpreter {
	private final I_Transformator3dCPU1 transformator = new Transformator3dCPU1();
	
	public I_Transformator3dCPU1 get_interpreter() {
		F_InitUG.invoke(3, new AlgebraType("CPU", 1));
		return new Transformator3dCPU1();
	}
}
