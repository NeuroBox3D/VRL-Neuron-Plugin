/// package's name
package edu.gcsc.vrl.MembranePotentialMapping.types;

/// imports
import edu.gcsc.vrl.MembranePotentialMapping.userdata.LoadHOCFileObservable;
import eu.mihosoft.vrl.annotation.TypeInfo;
import eu.mihosoft.vrl.dialogs.FileDialogManager;
import eu.mihosoft.vrl.io.VFileFilter;
import eu.mihosoft.vrl.lang.VLangUtils;
import eu.mihosoft.vrl.reflection.LayoutType;
import eu.mihosoft.vrl.reflection.TypeRepresentationBase;
import eu.mihosoft.vrl.reflection.VisualCanvas;
import eu.mihosoft.vrl.visual.MessageType;
import eu.mihosoft.vrl.visual.VBoxLayout;
import eu.mihosoft.vrl.visual.VTextField;
import groovy.lang.Script;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @brief use this as SectionLoaderFromHocFile
 * @author stephan
 */
@TypeInfo(type = File.class, input = true, output = false, style = "hoc-load-dialog")
public class LoadHOCFileType extends TypeRepresentationBase {

	private static final long serialVersionUID = 1L;

	/// the text field to display
	private VTextField input;
	/// filter to restrict to ugx file
	private VFileFilter fileFilter = new VFileFilter();
	/// the current hoc_tag
	private String hoc_tag = null;

	/**
	 * Constructor.
	 */
	@Override
	public void addedToMethodRepresentation() {
		super.addedToMethodRepresentation();

		// register at observable
		notifyLoadHOCFileObservable();
	}

	public LoadHOCFileType() {

		eu.mihosoft.vrl.system.VMessage.info("MPM Plugin status", "LoadHOCFileType instantiated!");

		// create a Box and set it as layout
		VBoxLayout layout = new VBoxLayout(this, VBoxLayout.Y_AXIS);
		setLayout(layout);
		setLayoutType(LayoutType.STATIC);

		// set the name label
		nameLabel.setText("File Name (*.hoc):");
		nameLabel.setAlignmentX(0.0f);
		add(nameLabel);

		// elements are horizontally aligned
		Box horizBox = Box.createHorizontalBox();
		horizBox.setAlignmentX(LEFT_ALIGNMENT);
		add(horizBox);

		// create input field
		input = new VTextField(this, "");
		input.setHorizontalAlignment(JTextField.RIGHT);
		int height = (int) this.input.getMinimumSize().getHeight();
		input.setSize(new Dimension(120, height));
		input.setMinimumSize(new Dimension(120, height));
		input.setMaximumSize(new Dimension(120, height));
		input.setPreferredSize(new Dimension(120, height));
		input.setEditable(true);
		input.setAlignmentY(0.5f);
		input.setAlignmentX(0.0f);
		input.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setViewValue(new File(input.getText()));
			}
		});
		horizBox.add(input);

		// hide connector, since no external data allowed
		setHideConnector(true);

		// set to ugx ending only
		ArrayList<String> endings = new ArrayList<String>();
		endings.add("hoc");
		fileFilter.setAcceptedEndings(endings);
		fileFilter.setDescription("*.hoc");

		// create a file manager
		final FileDialogManager fileManager = new FileDialogManager();

		// create a load button
		JButton button = new JButton("...");
		button.setMaximumSize(new Dimension(50, button.getMinimumSize().height));
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File directory = null;
				if (getViewValueWithoutValidation() != null) {
					directory = new File(getViewValueWithoutValidation().toString());
					if (!directory.isDirectory()) {
						directory = directory.getParentFile();
					}
				}

				File file = fileManager.getLoadFile(getMainCanvas(),
					directory, fileFilter, false);

				if (file != null) {
					setViewValue(file);
				}
			}
		});

		horizBox.add(button);
	}

	@Override
	public void setViewValue(Object o) {
		if (o instanceof File) {
			input.setText(((File) o).getAbsolutePath());
			input.setCaretPosition(input.getText().length());
			input.setToolTipText(input.getText());
			input.setHorizontalAlignment(JTextField.RIGHT);
			//TODO: reset width according to file name
		}
		//  Here we inform the Singleton, that the file has been scheduled
		notifyLoadHOCFileObservable();
	}

	@Override
	public Object getViewValue() {
		return new File(input.getText());
	}

	@Override
	public void emptyView() {
		input.setText("");
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void evaluationRequest(Script script) {
		super.evaluationRequest(script);

		Object property = null;

		if (getValueOptions() != null) {

			if (getValueOptions().contains("hoc_tag")) {
				property = script.getProperty("hoc_tag");
			}

			if (property != null) {
				hoc_tag = (String) property;
			}
		}

		if (hoc_tag == null) {
			getMainCanvas().getMessageBox().addMessage("Invalid ParamInfo option",
				"ParamInfo for hoc-subset-selection requires hoc_tag in options",
				getConnector(), MessageType.ERROR);
		} else {
			getMainCanvas().getMessageBox().addMessage("ParamInfo specified correctly", "hoc_tag was: " + hoc_tag, MessageType.INFO);
		}

	}

	protected void notifyLoadHOCFileObservable() {
		eu.mihosoft.vrl.system.VMessage.info("Clamp", "notifyloadhocfileobaservable called!");
		File file = new File(input.getText());
		int id = this.getParentMethod().getParentObject().getObjectID();
		Object o = ((VisualCanvas) getMainCanvas()).getInspector().getObject(id);
		int windowID = 0;

		//  Here we inform the Singleton, that the file has been scheduled
		if (!file.getAbsolutePath().isEmpty() && file.isFile()) {
			String msg = LoadHOCFileObservable.getInstance().setSelectedFile(file, hoc_tag, o, windowID);
			if (!msg.isEmpty() && !getMainCanvas().isLoadingSession()) {
				getMainCanvas().getMessageBox().addMessage("Invalid hoc-File",
					msg, getConnector(), MessageType.ERROR);
			}

		} else {
			LoadHOCFileObservable.getInstance().setInvalidFile(hoc_tag, o, windowID);
			if (!input.getText().isEmpty() && !getMainCanvas().isLoadingSession()) {
				getMainCanvas().getMessageBox().addMessage("Invalid hoc-File",
					"Specified filename invalid: " + file.toString(),
					getConnector(), MessageType.ERROR);
			}
		}
	}

	/**
	 * @brief for code generation
	 * @return
	 */
	@Override
	public String getValueAsCode() {
		return "\""
			+ VLangUtils.addEscapesToCode(getValue().toString()) + "\"";
	}
}
