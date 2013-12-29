/**
 * 
 */
package fr.utbm.lo43.jvivarium.controller;

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

import fr.utbm.lo43.jvivarium.core.BoundingBox;
import fr.utbm.lo43.jvivarium.core.Bowser;
import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.Coordinates;
import fr.utbm.lo43.jvivarium.core.Entity;
import fr.utbm.lo43.jvivarium.core.Map;
import fr.utbm.lo43.jvivarium.core.Mario;
import fr.utbm.lo43.jvivarium.core.NegativeSizeException;
import fr.utbm.lo43.jvivarium.core.Obj;
import fr.utbm.lo43.jvivarium.core.ObjectType;
import fr.utbm.lo43.jvivarium.core.Peach;

/**
 * This class is used for load the map
 * and others objects describes in the XML, 
 * into the memory (And reverse too)
 * 
 * @author Alexandre Guyon
 */
public class XMLLoader implements Runnable
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
	
	@Override
	public void run()
	{
		this.startParse();
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
		int i, j, life = 0, lifeE=100;
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
				case "life":
					NodeList getLife = l.item(i).getChildNodes();
					for(j = 0 ; j < getLife.getLength() ; j++)
					{
						switch(getLife.item(j).getNodeName())
						{
							case "life":
								life = Integer.parseInt(getLife.item(j).getChildNodes().item(0).getNodeValue());
								break;
						}
					}
					lifeE=life;
					break;
					
				case "espece":
					NodeList type = l.item(i).getChildNodes();
					
					try
					{
						switch(type.item(0).getNodeValue())
						{
							case "MARIO":
								Mario m = new Mario(new BoundingBox(positionE, sizeE));
								m.setLife(lifeE);
								this.map.add(m);
								break;
							case "PEACH":
								Peach p = new Peach(new BoundingBox(positionE, sizeE));
								p.setLife(lifeE);
								
								this.map.add(p);
								break;
							case "BOWSER":
								Bowser b = new Bowser(new BoundingBox(positionE, sizeE));
								b.setLife(lifeE);
								this.map.add(b);
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
	
	
	
	/**
	 * Save entities to XML
	 */
	public void saveEntitys()
	{
		this.saveEntitys(this.map.getEntitys());
	}
	
	/**
	 * Save entities to XML
	 * @param lEntity List of entities to save
	 */
	public void saveEntitys(List<Entity> lEntity)
	{		
		NodeList list = doc.getDocumentElement().getChildNodes();
		Entity el;
		Node n, old;
		int i,life;
		
		
		for(i = 0 ; i < list.getLength() ; i++)
		{
			if(list.item(i).getNodeName() == "entity")
			{
				// Remove all entities
				n = list.item(i).getFirstChild();
				while(n != null)
				{
					old = n;
					n = n.getNextSibling();
					
					if(old != null && n != null)
						n.getParentNode().removeChild(old);
				}
				
				//Add new ones
				for(Iterator<Entity> it = lEntity.iterator(); it.hasNext();)
				{
					el = it.next();
					Node no;
					Node temp=list.item(i);
					Node size,pos,lifeNode,type,entity;
					String espece;
					entity=temp;
						entity.appendChild(this.doc.createElement("size"));
						temp = temp.getLastChild();
						
						while(temp.getNodeName() != "size")
							temp = temp.getNextSibling();
						
						//si trouvé
						if(temp.getNodeName() == "size")
						{	
							size=temp;
							no = this.doc.createElement("x");
							no.setTextContent(el.getArea().getSize().getX() + "");
							size.appendChild(no);
							
							no = this.doc.createElement("y");
							no.setTextContent(el.getArea().getSize().getY() + "");
							size.appendChild(no);
						}
						else
							System.out.println("pb saveXML in entity !");
						// Position 
						entity.appendChild(this.doc.createElement("position"));
						
						while(temp.getNodeName() != "position")
							temp = temp.getNextSibling();
						if(temp.getNodeName() == "position")
						{
							pos=temp;
							no = this.doc.createElement("x");
							no.setTextContent(el.getArea().getPosition().getX() + "");
							pos.appendChild(no);
							
							no = this.doc.createElement("y");
							no.setTextContent(el.getArea().getPosition().getY() + "");
							pos.appendChild(no);
						}
						else
							System.out.println("pb saveXML in entity !");
						// Espece
						entity.appendChild(this.doc.createElement("espece"));
						
						while(temp.getNodeName() != "espece")
							temp = temp.getNextSibling();
						if(temp.getNodeName() == "espece")
						{
							type=temp;
							
							if( el instanceof Mario) {
							    espece="Mario";
							  }
							  else if( el instanceof Peach) {
							    espece="Peach";
							  }
							  else 
								  espece="Bowser";
							
							type.setTextContent(espece);
						}
						else
							System.out.println("pb saveXML in entity !");
						//Life Points
						entity.appendChild(this.doc.createElement("life"));
						
						while(temp.getNodeName() != "life")
							temp = temp.getNextSibling();
						if(temp.getNodeName() == "life")
						{
							lifeNode=temp;
							life= el.getLife();
							temp.setTextContent(Integer.toString(life));
						}
						else
							System.out.println("pb saveXML in entity !");
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
	
	
	/**
	 * Save objects to XML
	 */
	public void saveObjects()
	{
		this.saveObjects(this.map.getObjects());
	}
	
	/**
	 * Save object to XML
	 * @param lObj List of Obj to save
	 */
	public void saveObjects(List<Obj> lObj)
	{		
		NodeList list = doc.getDocumentElement().getChildNodes();
		Obj o;
		Node n, old,size,pos,obj,type;
		int i;
		
		
		for(i = 0 ; i < list.getLength() ; i++)
		{
			if(list.item(i).getNodeName() == "obj")
			{
				
				// Remove all chunks
				n = list.item(i).getFirstChild();
				obj=n;
				while(n != null)
				{
					old = n;
					n = n.getNextSibling();
					
					if(old != null && n != null)
						n.getParentNode().removeChild(old);
				}
				
				//Add new ones
				for(Iterator<Obj> it = lObj.iterator(); it.hasNext();)
				{
					o = it.next();
					Node no;
					Node temp=list.item(i);
					String objType;	
					obj=temp;
						obj.appendChild(this.doc.createElement("size"));

						
						temp = temp.getLastChild();
						while(temp.getNodeName() != "size")
							temp = temp.getNextSibling();
						if(temp.getNodeName() == "size")
						{		
							size=temp;
							no = this.doc.createElement("x");
							no.setTextContent(o.getArea().getSize().getX() + "");
							size.appendChild(no);
							
							no = this.doc.createElement("y");
							no.setTextContent(o.getArea().getSize().getY() + "");
							size.appendChild(no);
						}
						else
							System.out.println("pb saveXML in entity !");
						// Position 
						obj.appendChild(this.doc.createElement("position"));
						while(temp.getNodeName() != "position")
							temp = temp.getNextSibling();
						if(temp.getNodeName() == "position")
						{
							pos=temp;
							
							no = this.doc.createElement("x");
							no.setTextContent(o.getArea().getPosition().getX() + "");
							pos.appendChild(no);
							
							no = this.doc.createElement("y");
							no.setTextContent(o.getArea().getPosition().getY() + "");
							pos.appendChild(no);
						}
						else
							System.out.println("pb saveXML in entity !");
						// Type
						
						obj.appendChild(this.doc.createElement("objType"));
						while(temp.getNodeName() != "objType")
							temp = temp.getNextSibling();
						if(temp.getNodeName() == "objType")
						{		
							type=temp;
							if( o.getType().equals("STAR")) {
							    objType="STAR";
							  }
							else 
								objType="MUSHROOM";
							type.setTextContent(objType);
						}
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

