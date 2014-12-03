/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/// package's name
package edu.gcsc.vrl.MembranePotentialMapping;

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
			 return false;
		 }
	}
}
