/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.hoc;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;


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
	    I_Transformator transformator) {
	    m_transformator = transformator;
    }

    /**
     * @brief get's the HOC interpreter instance
     * @return the hoc interpreter
     */
    @SuppressWarnings("all")
    public I_Transformator get_transformator() {
	    return m_transformator;
    }
}
