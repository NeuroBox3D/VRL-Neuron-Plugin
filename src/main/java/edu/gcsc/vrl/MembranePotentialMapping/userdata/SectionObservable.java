/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.userdata;

/**
 *
 * @author stephan
 */
public class SectionObservable {
   // singleton instance
   private static volatile SectionObservable instance = null;
   
   /**
    * @brief private ctor for singleton
    */
   private SectionObservable() {
	
   }
		
	/**
	 * @brief returns the singleton instance
	 * @return instance
	 */
	public static SectionObservable getInstance() {
		if (instance == null) {
	   	synchronized (SectionObservable.class) {
				if (instance == null) {
					instance = new SectionObservable();
				}
			}
		}	   
		return instance;
   }
}