/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.types;

import edu.gcsc.vrl.MembranePotentialMapping.HOCFileInfo;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.LoadHOCFileObservable;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.LoadHOCFileObserver;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Section;
import edu.gcsc.vrl.userdata.FunctionDefinition;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;
import eu.mihosoft.vrl.reflection.LayoutType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.visual.MessageType;
import eu.mihosoft.vrl.visual.VBoxLayout;
import eu.mihosoft.vrl.visual.VTextField;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author stephan
 */
@TypeInfo(type=Section.class, input = true, output = false, style="default")
public class SectionType extends TypeRepresentationBase implements Serializable, LoadHOCFileObserver {
	private static final long serialVersionUID = 1L;
   protected JList subsetList = null;
   DefaultListModel subsetListModel = null;
	protected VTextField hocFileName = null;
	private Section section = null;
	
	public void init() {
          	eu.mihosoft.vrl.system.VMessage.info("SectionType", "init was called");
		 // create a VBoxLayout and set it as layout
        VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);
        setLayout(layout);
        setLayoutType(LayoutType.STATIC);
        
        nameLabel.setAlignmentX(LEFT_ALIGNMENT);
        add(nameLabel);

        // elements are horizontally aligned
        Box horizBox = Box.createHorizontalBox();
        horizBox.setAlignmentX(LEFT_ALIGNMENT);
        //add(horizBox);
		   // function naming field
        hocFileName = new VTextField("Geometry sections");
        hocFileName.setAlignmentX(Component.LEFT_ALIGNMENT);
        hocFileName.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e) {}
            
            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e)
            {
                if (subsetList.getSelectedIndices() != null && hocFileName.getText() != null)
                {
                    // construct selectedValuesList by hand since 
                    // getSelectedValuesList() depends on 1.7 and may raise an exception
                    List<String> selSubsets = new ArrayList<String>();
                    //storeCustomParamData();
                }
            }
        });
		  add(hocFileName);
	}
	
	/**
	 * @brief default ctor
	 */
	public SectionType() {
			// hide connector 
        setHideConnector(true);
        section = new Section();
		  System.err.println("SectionType ctor called!");
		
	}

	/**
	 * @brief to be implemented
	 */
	 @Override
    	public void addedToMethodRepresentation() {
 	       super.addedToMethodRepresentation();
			 init();
	       
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
	    @Override
    public String getValueAsCode() {
        return "\""
                + VLangUtils.addEscapesToCode(getValue().toString()) + "\"";
    }
}

