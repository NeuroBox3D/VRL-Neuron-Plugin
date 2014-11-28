/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.userdata;

/// imports
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @brief represents a section in a hoc compartmental model
 * @author stephan grein <stephan.grein@gcsc.uni-frankfurt.de>
 */
public class Section implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<String> names = new ArrayList<String>();
	
	/**
	 * @brief default ctor
	 */
	public Section() {
	}	
	
	/**
	 * @brief get all section names
	 * @param list 
	 */
	@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
	public void set_names(List<String> list) {
		this.names = list;
	}
	
	/**
	 * @brief get all sectio names
	 * @return
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public List<String> get_names() {
		return this.names;
	}
	
}
