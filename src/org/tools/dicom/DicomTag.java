package org.tools.dicom;

/**
 * This class implements a very simple dicom tag object
 * @author Fabien Rica
 */  
public class DicomTag {

	public DicomTag (String code, String name, String multiplicity, String representation) {
		
		sCode 			= code;
		sName	 		= name;
		sMultiplicity 	= multiplicity;
		sRepresentation = representation;
	}
	
	public String sCode;
	public String sName;
	public String sMultiplicity;
	public String sRepresentation;
	
	public void setTagName(String name) {
		this.sName = name;
	}
}
