package org.tools.dicom;

import java.util.LinkedList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * This class implements the dicom data dictionnary xml handler
 * based on dcm4che data dictionary dtd
 * @author Fabien Rica
 */
public class DicomDataDictionaryHandler extends DefaultHandler {

	public DicomDataDictionaryHandler() {
		
		super();
	}
	
    public List<DicomTag> getDictionary() {
		return dictionary;
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {

		 String test = new String(ch,start,length);
		 if(buffer != null) 
			 buffer.append(test);
	}

	@Override
	public void endDocument() throws SAXException {

//		 System.out.println("Fin du parsing");
//         System.out.println("Resultats du parsing");
//         for(DicomTag p : dictionary){
//        	 System.out.println(p.sCode + " " + p.sName + " " + p.sMultiplicity + " " + p.sRepresentation);
//        }
	}

	@Override
	public void endElement(String uri, String localName, String name)
			throws SAXException {

		if(name.equals("dictionary")){
			
			inDictionary = false;
		
		}else if(name.equals("element")){
			
			tag.setTagName(buffer.toString());
			dictionary.add(tag);
			
			tag = null;
			inElement = false;
			
		}else{

			throw new SAXException("Balise "+name+" inconnue.");
		}   
	}

	@Override
	/**
	 * start parsing
	 */
	public void startDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String name,
			Attributes attributes) throws SAXException {

		if(name.equals("dictionary")){
			
			dictionary = new LinkedList<DicomTag>();
			inDictionary = true;
			
		}else if(name.equals("element")){
			
			try{
				
				String tagCode 	= attributes.getValue("tag");
				String tagVR 	= attributes.getValue("vr");
				String tagVM 	= attributes.getValue("vm");
				
				tag = new DicomTag(tagCode, "", tagVM, tagVR);
				
				buffer = new StringBuffer();
				
				inElement = true;
				
			}catch(Exception e){

				throw new SAXException(e);
			}
		}
	}

	private List<DicomTag> dictionary;
    private DicomTag tag;
    private StringBuffer buffer;
    
    private boolean inDictionary 	= false;
    private boolean inElement 		= false;
	   
}
