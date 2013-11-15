/**
 * 
 */
package fr.utbm.lo43.jvivarium.core;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * This class is used for load the map
 * and others objects describes in the XML, 
 * into the memory.
 * 
 * @author Alexandre Guyon
 */
public class XMLLoader
{
	//******************* Constant ********************/
	
	/**
	 * This constant point to the XML file
	 */
	private static final String FILENAME = "./res/map.xml";
	
	//******************* Variable ********************/
	
	/**
	 * XML Reader for parsing
	 */
	private XMLHandler xmlH;
	
	/**
	 * Used to read the xml file
	 */
	private XMLReader xr;
	
	//******************* Constructor ********************/
	
	/**
	 * Constructor of the class.
	 * Used for initialize the XML parser
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public XMLLoader()
	{		
		try
		{
			xr = XMLReaderFactory.createXMLReader();
			xmlH = new XMLHandler();
			xr.setContentHandler(xmlH);
			xr.setErrorHandler(xmlH);
		} 
		catch (SAXException e)
		{
			e.printStackTrace();
		}
		
	}
	
	
	//******************* Methods ********************/
	
	public void startParse()
	{
		try
		{
			xr.parse(FILENAME);
		} catch (IOException e)
		{
			e.printStackTrace();
		} catch (SAXException e)
		{
			e.printStackTrace();
		}
	}
	
	//******************* Getters & Setters ********************/
	
	
	
	//************************* private class ********************/
	
	private class XMLHandler extends DefaultHandler
	{
		//********** Variables *************/
		
		private List<BoundingBox> lChunk;
		
		private Coordinates size;
		
		private Coordinates pos;
		
		//********** Constructor ***********/
		
		public XMLHandler()
		{
			lChunk = new LinkedList();
			size = null;
			pos =null;
		}
		
		//*********** Methods *************/
		
		public void startDocument() throws SAXException
		{
			System.out.println("Begin parsing :");
		}
		
		public void endDocument() throws SAXException
		{
			System.out.println("End parsing !");
		}
		
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException
		{
			System.out.println("Begin URI : " + uri + " localName : " + localName +
										" qName : " + qName + " attr : " + attributes);
			switch(localName)
			{
				case "size":
					size = new Coordinates(0, 0);
					break;
				case "position":
					pos = new Coordinates(0, 0);
					break;
				case "x":
					//if(size == new Coordinates(0, 0))
						//size = new Coordinates(, y)
					break;
				case "y":
					break;
			}
		}
		
		public void endElement(String uri, String localName, String qName)
				throws SAXException
		{
			System.out.println("End URI : " + uri + " localName : " + localName +
					" qName : " + qName);
		}
		
		public void characters (char ch[], int start, int length)
	   {

	   }
	}
}
