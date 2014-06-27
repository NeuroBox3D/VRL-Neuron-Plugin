/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.types;

import edu.gcsc.vrl.MembranePotentialMapping.userdata.Section;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.SectionObservable;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.SectionObserver;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import java.io.Serializable;

/**
 *
 * @author stephan
 */
@TypeInfo(type=Section.class, input = true, output = false, style="default")
public class SectionType extends TypeRepresentationBase implements Serializable, SectionObserver {
	private static final long serialVersionUID = 1L;
	
	/**
	 * @brief default ctor
	 */
	public SectionType() {
		
	}
	
	/**
	 * @brief update method
	 */
	@Override
	public void update(SectionObservable data) {
		
	}
}

