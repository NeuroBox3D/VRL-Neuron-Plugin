/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports

import edu.gcsc.vrl.ug.api.I_Transformator;
import eu.mihosoft.vrl.annotation.OutputInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;


/**
 * @brief base class for hoc commands (subclass this)
 * @author stephan
 */
public abstract class HOCCommand {
    @SuppressWarnings("ProtectedField")
    protected I_Transformator m_transformator;

    /**
     * 
     * @brief set's the HOC interpreter instance
     * @param transformator the hoc interpreter
     */
    public void set_transformator(
	    @ParamInfo(name="HOC Interpreter")
	    I_Transformator transformator) {
	    m_transformator = transformator;
    }

    /**
     * @brief get's the HOC interpreter instance
     * @return the hoc interpreter
     */
    @SuppressWarnings("all")
    @OutputInfo(name="HOC Interpreter")
    public I_Transformator get_transformator() {
	    return m_transformator;
    }
}
