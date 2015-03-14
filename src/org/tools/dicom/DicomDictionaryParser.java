package org.tools.dicom;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.tools.application.DicomBuddy;
import org.xml.sax.SAXException;

/**
 * This class implements a simple dicom data dictionnary parser
 * based on dcm4che data dictionary dtd
 * @author Fabien Rica
 */
@SuppressWarnings("deprecation")
public class DicomDictionaryParser {

	public DicomDictionaryParser() throws SAXException, IOException, ParserConfigurationException {
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser parser = factory.newSAXParser();

		InputStream xmlStream = getClass().getResourceAsStream("/xml/dictionary.xml");
		
		if (xmlStream != null) {
			DicomDataDictionaryHandler handler = new DicomDataDictionaryHandler();
			parser.parse(xmlStream, handler);
			
			dictionary = handler.getDictionary();
			
	    } else
	    	System.err.println("Couldn't load dictionary!");
	}
	
	private List<DicomTag> dictionary;
	
	public static void main(String s[ ]) throws IOException, SAXException, ParserConfigurationException {
		 
		new DicomDictionaryParser();
	 }

	public List<DicomTag> getDictionary() {
		return dictionary;
	}
	
}
