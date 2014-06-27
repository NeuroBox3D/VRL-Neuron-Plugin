/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.userdata;

import edu.gcsc.vrl.MembranePotentialMapping.HOCFileInfo;
import edu.gcsc.vrl.ug.api.I_Transformator;
import edu.gcsc.vrl.ug.api.Transformator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * SINGLETON INSTANCE, also I_Transformator MUST BE SINGLETON for safety reasons...
 * @author stephan
 */
public class LoadHOCFileObservable {
	private class HOCFileTag  {
      public Collection<LoadHOCFileObserver> observers = new HashSet<LoadHOCFileObserver>();
      public HOCFileInfo data = null;
	}
	
	/**
	 * if multiple file loaders exist they are distinguished by the hoc_tag 
	 * @param hoc_tag
	 * @param id
	 * @param object
	 */
	private class Identifier {
		  private String hoc_tag = null;
		  private int id = 0; 
		  private Object object = null;
		   /**
			* 
			* @param hoc_tag
			* @param id
			* @param object 
			*/
		  public Identifier(String hoc_tag_, int id_, Object object_) {
					 hoc_tag = hoc_tag_;
					 id = id_;
					 object = object_;
		  }
		  
		  
	}

	  // TODO: get the tag for the file then create a HOCFileInfo object
	private HOCFileTag get_hoc_tag() {
		return new HOCFileTag();
	}
	
	// add an observer and update the observer!
    public synchronized void addObserver(LoadHOCFileObserver obs) {
				// get the tag then set info below
				get_hoc_tag();
		// get by an identifier the observer instance and pass a HOCFileInfo object here
		  obs.update(new HOCFileInfo());
    }
	
	// stores the file infos (i. e. sections etc)
	private transient HashMap<Identifier, HOCFileTag> hoc_tags = new HashMap<Identifier, HOCFileTag>();
	
	// store observers
	private ArrayList<LoadHOCFileObserver> observers;
	
   // singleton instance
   private static volatile LoadHOCFileObservable instance = null;
	
	// transformator instance (should be a singleton from cpp side TODO)
	private static final I_Transformator transformator = new Transformator();
	
   // store hoc sections
   private ArrayList<String> hoc_sections;
   
   /**
    * @brief private ctor for singleton
    */
   private LoadHOCFileObservable() {
	
   }
   
	/**
	 * @brief returns the singleton instance
	 * @return instance
	 */
	public static LoadHOCFileObservable getInstance() {
		if (instance == null) {
  		 	synchronized (LoadHOCFileObservable.class) {
				if (instance == null) {
					instance = new LoadHOCFileObservable();
				}
			}
		}	   
		return instance;
  	 }
}