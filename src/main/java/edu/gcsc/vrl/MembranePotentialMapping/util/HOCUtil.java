/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.util;

/// imports
import edu.gcsc.vrl.ug.api.I_Transformator;

/**
 *
 * @author stephan
 */
public final class HOCUtil {
	private HOCUtil() {
		
	}
	
	/**
	 * @brief checks if transformator is available
	 * @param transformator
	 * @return 
	 */
	public static boolean isInterpreterAvailable(I_Transformator transformator) {
		 if ( ! (transformator == null) ) {
			 return true;
		 } else {
          		eu.mihosoft.vrl.system.VMessage.info("HOC interpreter not initialized", "Check if Clamp instance has ingoing connection of a HOC interpreter (I_Transformator).");
			 return false;
		 }
	}
}
