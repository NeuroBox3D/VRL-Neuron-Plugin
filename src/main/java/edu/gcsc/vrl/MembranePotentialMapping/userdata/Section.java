/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.userdata;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author stephan
 */
public class Section implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<String> names;
	
	public Section() {
	}	
	
	public void set_names(List<String> list) {
		this.names = list;
	}
	
	public List<String> get_names() {
		return this.names;
	}
	
}
