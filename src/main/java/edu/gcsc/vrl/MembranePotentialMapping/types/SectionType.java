/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.gcsc.vrl.MembranePotentialMapping.types;

import edu.gcsc.vrl.MembranePotentialMapping.HOCFileInfo;
import edu.gcsc.vrl.MembranePotentialMapping.HOCInterpreter;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.LoadHOCFileObservable;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.LoadHOCFileObserver;
import edu.gcsc.vrl.MembranePotentialMapping.userdata.Section;
import edu.gcsc.vrl.ug.api.UGXFileInfo;
import edu.gcsc.vrl.userdata.FunctionDefinition;
import edu.gcsc.vrl.userdata.FunctionDefinitionObservable;
import edu.gcsc.vrl.userdata.LoadUGXFileObservable;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.lang.VLangUtils;
import eu.mihosoft.vrl.reflection.LayoutType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.visual.MessageType;
import eu.mihosoft.vrl.visual.VBoxLayout;
import eu.mihosoft.vrl.visual.VTextField;
import groovy.lang.Script;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Box;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
	private String hoc_tag = null;
	
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
        hocFileName = new VTextField("-- no hoc file selected --");
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
        	eu.mihosoft.vrl.system.VMessage.info("VTextField", "key was released");
						  
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

		    // subset selection list
        subsetListModel = new DefaultListModel();
        subsetList = new JList(subsetListModel);
        subsetList.setSelectionModel(new DefaultListSelectionModel() {
            private static final long serialVersionUID = 1L;
            boolean gestureStarted = false;

	            @Override
                public void setSelectionInterval(int index0, int index1)
                {
                    if(!gestureStarted)
                        if (isSelectedIndex(index0)) super.removeSelectionInterval(index0, index1);
                        else super.addSelectionInterval(index0, index1);
                    gestureStarted = true;
                }

                @Override
                public void setValueIsAdjusting(boolean isAdjusting)
                {
                    if (isAdjusting == false) gestureStarted = false;
                }
        });
        subsetList.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(subsetList);
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

			 if (hoc_tag == null) {
						System.err.println("Hoc_tag was null in addedToMethodRepresentation!");
			 }
	       
			if (hoc_tag != null)  {
            int id = this.getParentMethod().getParentObject().getObjectID();
            Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
            int windowID = 0;
				System.err.println("registering an observer");
            LoadHOCFileObservable.getInstance().addObserver(this, hoc_tag, o, windowID);
        }

		}

	  /**
		* @brief what does this actually do?
		*/
	 @Override
    protected void evaluateContract() {
        if (isValidValue()) super.evaluateContract();
    }
		
  /**
    * Requests evaluation of the value options that are usually specified in
    * {@link eu.mihosoft.vrl.annotation.ParamInfo}.
    */
    @Override
    protected void evaluationRequest(Script script) {
        super.evaluationRequest(script);

		  Object property = null;
        // read the hoc_tag
        if (getValueOptions() != null) {
					 if (getValueOptions().contains("hoc_tag")) {
								property = script.getProperty("hoc_tag");
					 }
		  }
            if (property != null) {
						  hoc_tag = (String) property;
				}


        if (hoc_tag == null) {
            getMainCanvas().getMessageBox().addMessage("Invalid ParamInfo option",
                    "ParamInfo for hoc-subset-selection requires hoc_tag in options",
                    getConnector(), MessageType.ERROR);
        }
    }
        
	 @Override
	 /**
	  * @brief dispose resources on graphical user interface destruction
	  */
    public void dispose() {
        if (hoc_tag != null) LoadHOCFileObservable.getInstance().deleteObserver(this);
	 }
	 
	
	 /**
	  * 
	  * @param info file info for hoc 
	  */
    @Override
    public void update(HOCFileInfo info) {
        // adjust data for new FileInfo

				System.err.println("update called!!! update(HOCFileInfo info)");
				if (info == null) {
						  System.err.println("info is null!");
				}
        adjustView(info);
	 }
    
 @Override
    public void setViewValue(Object o) {
				hocFileName.setText("SetViewValue");
	 }
	 
    
	 /**
	  * 
	  * @param info file info for hoc
	  */
	@SuppressWarnings("unchecked")
    private void adjustView(HOCFileInfo info) {
				System.err.println("within adjustView function!");
				hocFileName.setText("test test test");
        // adjust displayed subset list
        if (info != null) {
            if (!(info instanceof HOCFileInfo)) {
						  throw new RuntimeException("HOCFileInfo was not found / constructed, i. e. instance is not HOCFileInfo");
				}
            
				// clear all elements
            subsetListModel.removeAllElements();
				System.err.println("if part of adjustview");
				HOCInterpreter.getInstance().getTransformator().execute_hoc_stmt("a = 10");
				hocFileName.setText("-- sections need to be extracted --");
			
				// TODO: get the sections with I_Transformator instance here 
				// TODO: additionally we want maybe the registration of a global hoc_tag instead of a window based one!
			
        } else {
            subsetListModel.removeAllElements();
				System.err.println("else part of adjustview");
            subsetListModel.addElement("-- adjustview no hoc file selected --");
				hocFileName.setText("-- adjustview no hoc file selected --");
        }
    }
	
	 @Override
	 /**
	  * @brief necessary for code generation
	  * @return the escaped code
	  */
    public String getValueAsCode() {
        return "\""
                + VLangUtils.addEscapesToCode(getValue().toString()) + "\"";
    }
}

