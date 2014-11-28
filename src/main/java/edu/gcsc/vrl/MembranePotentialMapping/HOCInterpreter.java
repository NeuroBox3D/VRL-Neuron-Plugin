/// package's name
package edu.gcsc.vrl.MembranePotentialMapping;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;
import edu.gcsc.vrl.ug.api.Transformator;

/**
 * @brief access to hoc interpreter, provided as singleton
 * @author stephan
 */
public class HOCInterpreter {
   // private singleton instance
   private static volatile HOCInterpreter instance = null;
	private final static I_Transformator transformator = new Transformator();
	
	/**
	 * @brief private ctor for singleton
	 */
	private HOCInterpreter() {
		
	}

	/**
	 * @brief get singleton instance or create if necessary
	 * @see this implementation makes use of a non-draconian synchronization
	 * @return instance
	 */
	public static HOCInterpreter getInstance() {
		if (instance == null) {
			synchronized (HOCInterpreter.class) {
				if (instance == null) {
			   	instance = new HOCInterpreter();
				}
			}
		}
	  	return instance;
	}

	/**
	 * @brief get transformator instance
	 * @return transformator
	 */
	public synchronized I_Transformator getTransformator() {
		return transformator;		  
	}
}
