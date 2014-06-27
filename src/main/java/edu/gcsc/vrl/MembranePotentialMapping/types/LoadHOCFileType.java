/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.types;

import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import java.io.File;

/**
 *
 * @brief use this as SectionLoaderFromHocFile
 * @author stephan
 */

@TypeInfo(type = File.class, input = true, output = false, style = "default")
public class LoadHOCFileType extends TypeRepresentationBase {
	 private static final long serialVersionUID = 1L;
	 
	 @Override
    public void addedToMethodRepresentation() {
        super.addedToMethodRepresentation();

		  // register at observable
        notifyLoadHOCFileObservable();
    }	
	 
	 protected void notifyLoadHOCFileObservable() {
				// TODO: implement method stub
	 }
}
