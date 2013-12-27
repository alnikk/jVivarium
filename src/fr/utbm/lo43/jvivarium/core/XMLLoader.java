/**
 * 
 */
package fr.utbm.lo43.jvivarium.core;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * This class is used for load the map
 * and others objects describes in the XML, 
 * into the memory (And reverse too)
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
	 * DOM document
	 */
	private Document doc;
	
	/**
	 * The map singleton
	 */
	private Map map = Map.getMap();
	
	//******************* Constructor ********************/
	
	/**
	 * Constructor of the class.
	 * Used for initialize the XML parser
	 */
	public XMLLoader()
	{		
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			File xml = new File(FILENAME);
			doc = builder.parse(xml);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	//********************* Parse *******************************************
	
	/**
	 * Begin the parsing of xml data
	 */
	public void startParse()
	{
		NodeList list = doc.getDocumentElement().getChildNodes();
		int i;
		
		for(i = 0 ; i < list.getLength() ; i++)
		{
			switch (list.item(i).getNodeName())
			{
				case "map":
					this.parseMap(list.item(i).getChildNodes());
					break;
				case "entity":
					this.parseEntity(list.item(i).getChildNodes());
				case "obj":
					this.parseObj(list.item(i).getChildNodes());
				default:
					break;
			}
		}
	}

	/**
	 * Parse the map XML
	 * @param l	(NodeList) The node which contains chunks
	 */
	private void parseMap(NodeList l)
	{
		int i;		
		
		// Add chunks to the list
		for(i = 0 ; i < l.getLength() ; i++)
		{
			if(l.item(i).getNodeName() == "chunk")
			{
				try
				{
					this.map.add(new Chunk(l.item(i).getChildNodes()));
				}
				catch (NegativeSizeException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Parse the espece XML
	 * @param l	(NodeList) The node which contains espece
	 */
	private void parseEntity(NodeList l)
	{
		int i, j;
		int x = 0, y = 0;
		Coordinates sizeE = null, positionE = null;
		
		// Add entities to the list
		for(i = 0 ; i < l.getLength() ; i++)
		{
			// Get the size, position, and type
			switch(l.item(i).getNodeName())
			{
				case "size":
					NodeList size = l.item(i).getChildNodes();
					for(j = 0 ; j < size.getLength() ; j++)
					{
						switch(size.item(j).getNodeName())
						{
							case "x":
								x = Integer.parseInt(size.item(j).getChildNodes().item(0).getNodeValue());
								break;
							case "y":
								y = Integer.parseInt(size.item(j).getChildNodes().item(0).getNodeValue());
								break;
						}
					}
					sizeE = new Coordinates(x,y);
					break;
					
				case "position":
					NodeList position = l.item(i).getChildNodes();
					for(j = 0 ; j < position.getLength() ; j++)
					{
						switch(position.item(j).getNodeName())
						{
							case "x":
								x = Integer.parseInt(position.item(j).getChildNodes().item(0).getNodeValue());
								break;
							case "y":
								y = Integer.parseInt(position.item(j).getChildNodes().item(0).getNodeValue());
								break;
						}
					}
					positionE = new Coordinates(x,y);
					break;
					
				case "type":
					NodeList type = l.item(i).getChildNodes();
					
					try
					{
						switch(type.item(0).getNodeValue())
						{
							case "MARIO":
								this.map.add(new Mario(new BoundingBox(positionE, sizeE)));
								break;
							case "PEACH":
								this.map.add(new Peach(new BoundingBox(positionE, sizeE)));
								break;
							case "BOWSER":
								this.map.add(new Bowser(new BoundingBox(positionE, sizeE)));
								break;
						}
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
			}
		}
	}

	/**
	 * Parse the objects XML
	 * @param l	(NodeList) The node which contains objects
	 */	
	private void parseObj(NodeList l)
	{
		int i, j;
		int x = 0, y = 0;
		Coordinates sizeO = null, positionO = null;
		
		// Add objects to the list
		for(i = 0 ; i < l.getLength() ; i++)
		{
			// Get the size, position, and type
			switch(l.item(i).getNodeName())
			{
				case "size":
					NodeList size = l.item(i).getChildNodes();
					for(j = 0 ; j < size.getLength() ; j++)
					{
						switch(size.item(j).getNodeName())
						{
							case "x":
								x = Integer.parseInt(size.item(j).getChildNodes().item(0).getNodeValue());
								break;
							case "y":
								y = Integer.parseInt(size.item(j).getChildNodes().item(0).getNodeValue());
								break;
						}
					}
					sizeO = new Coordinates(x,y);
					break;
								
					case "position":
						NodeList position = l.item(i).getChildNodes();
						for(j = 0 ; j < position.getLength() ; j++)
						{
							switch(position.item(j).getNodeName())
							{
								case "x":
									x = Integer.parseInt(position.item(j).getChildNodes().item(0).getNodeValue());
									break;
								case "y":
									y = Integer.parseInt(position.item(j).getChildNodes().item(0).getNodeValue());
									break;
							}
						}
						positionO = new Coordinates(x,y);
						break;
								
					case "type":
						NodeList type = l.item(i).getChildNodes();
						
						try
						{			
							switch(type.item(0).getNodeValue())
							{
									case "MUSHROOM":
									this.map.add(new Obj(new BoundingBox(positionO, sizeO), ObjectType.MUSHROOM));
									break;
								case "STAR":
									this.map.add(new Obj(new BoundingBox(positionO, sizeO), ObjectType.STAR));
									break;
							}
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						break;
			}
		}
	}
	
	//******************* Save **********************************
	
	/**
	 * Save chunks to XML
	 */
	public void saveChunks()
	{
		this.saveChunks(this.map.getChunks());
	}
	
	/**
	 * Save chunks to XML
	 * @param lChunk List of chunk to save
	 */
	public void saveChunks(List<Chunk> lChunk)
	{		
		NodeList list = doc.getDocumentElement().getChildNodes();
		Chunk c;
		Node n, old;
		int i;
		
		
		for(i = 0 ; i < list.getLength() ; i++)
		{
			if(list.item(i).getNodeName() == "map")
			{
				// Remove all chunks
				n = list.item(i).getFirstChild();
				while(n != null)
				{
					old = n;
					n = n.getNextSibling();
					
					if(old != null && n != null)
						n.getParentNode().removeChild(old);
				}
				
				//Add new ones
				for(Iterator<Chunk> it = lChunk.iterator(); it.hasNext();)
				{
					c = it.next();
					
					c.saveXML(this.doc, list.item(i));
				}
			}
		}
		
		// Save		
		Transformer transformer;
		try
		{
			transformer = TransformerFactory.newInstance().newTransformer();
			Result output = new StreamResult(new File(FILENAME));
			Source input = new DOMSource(this.doc);
			
			transformer.transform(input, output);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
