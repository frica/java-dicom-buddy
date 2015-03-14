package org.tools.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.tools.application.DicomBuddy;
import org.tools.dicom.DicomDictionary;
import org.tools.dicom.DicomTag;
import org.tools.dicom.TagFormatter;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * This class implements the search panel by dicom tag
 * It implements ActionListener for the jpanel to be able to generate actionperformed event
 * @author Fabien Rica
 */
@SuppressWarnings("deprecation")
public class TagSearchPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public TagSearchPanel () throws IOException {
		
		initComponents();
		buildTagSearchPanel();
	}

	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == tagNumberList){

			try {
				String tagEntered = tagNumberList.getSelectedItem().toString();
				DicomTag dicomTag = DicomDictionary.getInstance().getDicomTagByCode(tagEntered);
				
				if (dicomTag != null) {
					tagMeaning.setText(dicomTag.sName);
					fieldTagVM.setText(dicomTag.sMultiplicity);
					fieldTagVR.setText(dicomTag.sRepresentation);
				} else {
					System.out.println("Tag " + tagEntered + " not found");
					tagMeaning.setText("Unknown tag");
					fieldTagVM.setText("");
					fieldTagVR.setText("");
				}
				
			} catch (IOException e1) {
				e1.printStackTrace();
			}		
		}
	}
	
	private JComboBox tagNumberList;
	private JTextField tagMeaning;
	private JTextField fieldTagVM;
	private JTextField fieldTagVR;
	
	/**
	 * build the layout 
	 */ 
	private void buildTagSearchPanel(){
		
		FormLayout layout =  new FormLayout(
				"right:pref, 3dlu, pref:grow", 			// columns
	    		"p, 3dlu, p, 3dlu, p, 9dlu, p, 3dlu, p, 3dlu, p, 3dlu, p");	// rows);
		
		// Specify that columns xxx as well as xxx have equal widths.
        layout.setRowGroups(new int[][]{{2, 4, 6, 9}, {3, 7}});
        
        buildTagSearchPanelComponents(layout);
	}
	
	/** 
	 * initialize GUI components
	 */
	private void initComponents() throws IOException {
		
		tagNumberList = new JComboBox();
		tagNumberList.setEditable(true);
		
		String sDefaultValueForCombo = "";
		tagNumberList.setSelectedItem(sDefaultValueForCombo);
		
		final int nTagCount = DicomDictionary.getInstance().getSize();
		for ( int i = 0; i < nTagCount; i++ )
		{
			DicomTag tag = DicomDictionary.getInstance().getTag(i);
			tagNumberList.addItem(TagFormatter.formatDicomTagCode(tag.sCode));
		}
		
		tagNumberList.addActionListener(this);
		
		tagMeaning = new JTextField();
		tagMeaning.setEditable(false);
		
		fieldTagVM = new JTextField();
		//fieldTagVM.setBorder(null);
		fieldTagVM.setEditable(false);
		
		fieldTagVR = new JTextField();
		//fieldTagVR.setBorder(null);
		fieldTagVR.setEditable(false);
	}

	/** 
	 * add the GUI components to the layout
	 * @param layout
	 */
	private void buildTagSearchPanelComponents(FormLayout layout){
		
		ApplicationLanguage lang = ApplicationLanguage.getInstance();
		
		PanelBuilder builder = new PanelBuilder(layout, this);
		builder.setDefaultDialogBorder();
		CellConstraints cc = new CellConstraints();
		
		builder.addSeparator(lang.getText("DICOMTag"), cc.xyw(1, 1, 3));
		builder.addLabel(lang.getText("Value"), cc.xy(1, 3));
		
		builder.add(tagNumberList, cc.xy(3, 3));
		
		builder.addSeparator(lang.getText("Details"), cc.xyw(1, 5, 3));
		builder.addLabel(lang.getText("Meaning"), cc.xy(1, 7));
		
		builder.add(tagMeaning, cc.xy(3, 7));
		
		builder.addLabel("VM", cc.xy(1, 9));
		builder.add(fieldTagVM, cc.xy(3, 9));
		
		builder.addLabel("VR", cc.xy(1, 11));
		builder.add(fieldTagVR, cc.xy(3, 11));
	}
}


