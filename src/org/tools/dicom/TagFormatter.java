package org.tools.dicom;

/**
 * This static class allows to get the tag with parenthesis and comma like in th enorm
 * or to get the raw representation of the tag code like it is in the dictionary
 * @author Fabien Rica
 */
public class TagFormatter {

	/** 
	 * get the dicom code formatted like in the standard 
	 * (0000,0000)
	 */
	static public String formatDicomTagCode(String code) {
		
		String firstpart =  code.substring(0, 4);
		String secondpart = code.substring(4);
		return "(" + firstpart + "," + secondpart + ")";
	}

	/** 
	 * get the dicom code not formatted
	 * 00000000
	 */
	static public String getUnformattedTagCode(String formattedTag) {
		String firstpart =  formattedTag.substring(1, 5);
		String secondpart = formattedTag.substring(6, 10);
		return firstpart + secondpart;
	}

}