package org.tools.ui;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.tools.dicom.DicomDictionary;
import org.tools.dicom.DicomTag;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class implements the search panel by dicom name
 * It derives from ActionListener and KeyListener interface to handle the search when user types
 * @author Fabien Rica
 */
public class TextSearchPanel extends JPanel implements ActionListener, KeyListener {

	private static final long serialVersionUID = 1L;

	public TextSearchPanel() {

		initComponents();
		buildTextSearchPanel();
	}
	
	public void actionPerformed(ActionEvent e) {

		updateComponentContents(e);
	}
	
	public void keyPressed(KeyEvent e) {
		// not used		
	}

	public void keyReleased(KeyEvent e) {

		updateComponentContents(e);
	}

	public void keyTyped(KeyEvent e) {
		// not used
	}
		
	/**
	 * Update components content when user interacts with them
	 * @param e
	 */
	private void updateComponentContents(AWTEvent e) {
		
		if (e.getSource() == textField){
						
			try {
			
				// clean anyway
				textDicomName.setText("");
				comboResults.removeAllItems();
				
				String textEntered = textField.getText();
				if (textEntered.isEmpty()) {
					fieldTagVM.setText("");
					fieldTagVR.setText("");
					return;
				}
				
				// to handle a possible issue with the regexp in 
				// DicomDictionary.getTagListbyValue
				if (textEntered.contains("(") || textEntered.contains(")")) {
					fieldTagVM.setText("");
					fieldTagVR.setText("");
					return;
				}
				
				List<String> tagList = DicomDictionary.getInstance().getTagListbyValue(textField.getText());
				final int listSize = tagList.size();
				for (int i = 0; i<listSize; i++)
					comboResults.addItem(tagList.get(i));
				
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
		}
		
		if (e.getSource() == comboResults){

			try {
				do {
					
					final int nComboElements = comboResults.getItemCount();
					if (nComboElements == 0)
						break;
					
					String sSelectedValue = comboResults.getSelectedItem().toString();
					if (sSelectedValue.isEmpty())
						break;
						
					String sValue = DicomDictionary.getInstance().getUniqueValuebyTag(sSelectedValue);
					if (sValue.equals("Not found")) {
						fieldTagVM.setText("");
						fieldTagVR.setText("");
						break;
					}
										
					DicomTag dicomTag = DicomDictionary.getInstance().getDicomTagByCode(sSelectedValue);
					if (dicomTag == null){
						textDicomName.setText("");
						fieldTagVM.setText("");
						fieldTagVR.setText("");
					}

					textDicomName.setText(dicomTag.sName);
					fieldTagVM.setText(dicomTag.sMultiplicity);
					fieldTagVR.setText(dicomTag.sRepresentation);
					
				} while (false);
				
			} catch (IOException e1) {
				
				e1.printStackTrace();
			}
			
		}
	}
	
	/**
	 * build the layout 
	 */ 
	private void buildTextSearchPanel(){
		
		FormLayout layout =  new FormLayout(
				"right:pref, 3dlu, pref:grow", 			// columns
	    		"p, 3dlu, p, 3dlu, p, 9dlu, p, 3dlu, p, 6dlu, p, 3dlu, p");	// rows);
		
		// Specify that columns 1 & 5 as well as 3 & 7 have equal widths.
        layout.setRowGroups(new int[][]{{2, 4, 6, 9}, {3, 7}});
        
        buildTextSearchPanelComponents(layout);
	}

	/** 
	 * add the GUI components to the layout
	 * @param layout
	 */
	private void buildTextSearchPanelComponents(FormLayout layout){
		
		ApplicationLanguage lang = ApplicationLanguage.getInstance();
		
		PanelBuilder builder = new PanelBuilder(layout, this);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		
		builder.addSeparator(lang.getText("TagName"), cc.xyw(1, 1, 3));
		builder.addLabel(lang.getText("Name"), cc.xy(1, 3));
		
		builder.add(textField, cc.xy(3, 3));
		
		builder.addSeparator(lang.getText("DICOMTag"), cc.xyw(1, 5, 3));
		builder.addLabel(lang.getText("Tag"), cc.xy(1, 7));
				
		builder.add(comboResults, cc.xy(3, 7));
		builder.add(textDicomName, cc.xy(3, 9));
		
		builder.addLabel("VM", cc.xy(1, 11));
		builder.add(fieldTagVM, cc.xy(3, 11));
		
		builder.addLabel("VR", cc.xy(1, 13));
		builder.add(fieldTagVR, cc.xy(3, 13));
	}
	
	/** 
	 * initialize GUI components
	 */
	private void initComponents() {
		
		textField = new JTextField();
		textField.addActionListener(this);
		textField.addKeyListener(this);
		
		comboResults = new JComboBox();
		comboResults.setEditable(false);
		comboResults.addActionListener(this);
		
		textDicomName = new JTextField();
		textDicomName.setBorder(null);
		textDicomName.setEditable(false);
		
		fieldTagVM = new JTextField();
		fieldTagVM.setEditable(false);
		
		fieldTagVR = new JTextField();
		fieldTagVR.setEditable(false);
	}

	private JTextField 	textField;
	private JComboBox 	comboResults;
	private JTextField	textDicomName;
	private JTextField 	fieldTagVM;
	private JTextField 	fieldTagVR;

}
