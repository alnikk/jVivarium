/**
 * 
 */
package fr.utbm.lo43.jvivarium.core;

import java.io.IOException;

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
public class XMLLoader extends DefaultHandler
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
	private DefaultHandler xmlH;
	
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
			xmlH = new XMLLoader();
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
	
	
	
	//*******************************************************
	
	// FIXME Private class ?
	
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
									" qName : " + qName + " attributes : " + attributes);
	}
	
	public void endElement(String uri, String localName, String qName)
			throws SAXException
	{
		System.out.println("End URI : " + uri + " localName : " + localName +
				" qName : " + qName);
	}
}
