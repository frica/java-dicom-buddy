package org.tools.dicom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 * This singleton implements a very simple dicom data dictionnary
 * Today based on the Dicom Buddy data dictionary.
 * @author Fabien Rica
 */
@SuppressWarnings("deprecation")
public class DicomDictionary {


	   public 	static DicomDictionary getInstance() throws IOException {
		   
	       if (null == instance) {
	           instance = new DicomDictionary();
	       }
	       return instance;
	   }
	   
	   /**
	    * Get the unique name of the tag given the tag code
	    */
	   public String getUniqueValuebyTag(String Tag){
		   
		   if (!Tag.equalsIgnoreCase("Not Found"))
			   Tag = TagFormatter.getUnformattedTagCode(Tag);
		   
		   String name = "";
		   final int nSize = dicomDictionary.size();
		   for (int i = 0; i < nSize; i++)
		   {
			   DicomTag tag = dicomDictionary.get(i);
			   if (tag.sCode.equalsIgnoreCase(Tag))
			   {
				   name = tag.sName;
				   break;
			   }
		   }

		   if (name.isEmpty())
			   name = "Not found";

		   return name;

		}
	   	
	   	public DicomTag getDicomTagByCode(String sCode){
		   
	   		if (sCode.length() != 11)
	   			return null;
	   		
	   		if (!sCode.startsWith("(") || !sCode.endsWith(")"))
	   			return null;
	   			
	   		sCode = TagFormatter.getUnformattedTagCode(sCode);
	
			final int nSize = dicomDictionary.size();
			for (int i = 0; i < nSize; i++)
			{
				DicomTag tag = dicomDictionary.get(i);
				if (tag.sCode.equalsIgnoreCase(sCode))
					return tag;
			}
				
			return null;
		}
	   
	   /**
	    * Get the dicom tag in the dicom dictionary
	    */
	   public DicomTag getTag(int i) {
			
			return dicomDictionary.get(i);
		}
	   
	   /**
	    * return the size of the dicom dictionary
	    */
	   public int getSize(){
		   
		   	return dicomDictionary.size();
		}
	   
	   /**
	    * Get the unique tag code given the tag name
	    */
	   public String getUniqueTagbyValue(String Value){
			
			String TagCode = "";
			final int nSize = dicomDictionary.size();
			for (int i = 0; i < nSize; i++)
			{
				DicomTag tag = dicomDictionary.get(i);
				if (tag.sName.equalsIgnoreCase(Value))
				{
					TagCode = TagFormatter.formatDicomTagCode(tag.sCode);
					break;
				}
			}
			
			if (TagCode.isEmpty())
				TagCode = "Not found";
			
			return TagCode;
	   }
	   
	   /**
	    * Get a tag code list given a partial tag name
	    */
	   public List<String> getTagListbyValue(String Value){
			
			List<String> TagCodes = new ArrayList<String>();
			
			final int nSize = dicomDictionary.size();
			for (int i = 0; i < nSize; i++)
			{
				DicomTag tag = dicomDictionary.get(i);
				
				// check if it contains the value without taking care of case
				// mandatory to use regexp to do this apparently
				if (tag.sName.matches("(?i).*"+ Value +".*"))
					TagCodes.add(TagFormatter.formatDicomTagCode(tag.sCode));
			}
			
			if (TagCodes.isEmpty())
				TagCodes.add("Not found");
			
			return TagCodes;
	   }
	   
	   private DicomDictionary() throws IOException {
		   
		   try {
			   DicomDictionaryParser dicomDictionaryParser = new DicomDictionaryParser();
			   dicomDictionary = dicomDictionaryParser.getDictionary();
			   
			   System.out.println("Dictionnary is now loaded");
			   
		   } catch (SAXException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   } catch (ParserConfigurationException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
	   }
	   
	   /** unique instance  */
	   private static DicomDictionary 	instance;
	   private List<DicomTag> 			dicomDictionary;
}
