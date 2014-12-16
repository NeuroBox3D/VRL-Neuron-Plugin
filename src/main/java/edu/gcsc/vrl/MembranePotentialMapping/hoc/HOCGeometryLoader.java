/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import java.io.Serializable;

/**
 * @brief geometry loader
 * @author stephan
 */
@ComponentInfo(name = "HOCGeometryLoader", category = "/UG4/VRL-Plugins/Neuro/MembranePotentialMapping/")
public class HOCGeometryLoader extends HOCCommand implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief ctor
	 */
	public HOCGeometryLoader() {

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
	@MethodInfo(valueName = "HOC Interpreter",
		    valueTypeName="The NEURON interpreter")
	public I_Transformator get_transformator() {
		return super.get_transformator();
	}

	/**
	 * @brief loads a hoc geometry into the given interpreter
	 * @param hoc_file
	 * @return
	 */
	@MethodInfo(valueName = "Success", valueTypeName="Success")
	public boolean load_geometry(
		@ParamInfo(name = "Load", style = "load-dialog") File hoc_file
	) {
		if (!(m_transformator == null)) {
			System.err.println(hoc_file.toString());
			m_transformator.load_geom(hoc_file.toString());
			return true;
		} else {
			return error(this);
		}
	}

}
