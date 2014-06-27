/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.types;

import edu.gcsc.vrl.MembranePotentialMapping.HOCFileInfo;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.LoadHOCFileObservable;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.LoadHOCFileObserver;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import java.io.Serializable;

/**
 *
 * @author stephan
 */
@TypeInfo(type=SectionType.class, input = true, output = false, style="default")
public class SectionType extends TypeRepresentationBase implements Serializable, LoadHOCFileObserver {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @brief default ctor
	 */
	public SectionType() {
		
	}

	/**
	 * @brief to be implemented
	 */
	 @Override
    	public void addedToMethodRepresentation() {
 	       super.addedToMethodRepresentation();
	       
			 // TODO: init view etc 
			if (true) 
        {
            int id = this.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
            LoadHOCFileObservable.getInstance().addObserver(this);
        }

		}
        
	
	/**
	 * @brief update method
    * @param data the observable
	 */
	@Override
	public void update(HOCFileInfo data) {
		// do something with the HocFileObject passed here then -> i. e. update JTextField for selection!! in this class to be used ...
	}
}

