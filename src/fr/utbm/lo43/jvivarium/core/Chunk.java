/**
 * 
 */
package fr.utbm.lo43.jvivarium.core;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * A chunk is a part of the map.
 * It's described by a bounding box
 * (coordinates, and size) and
 * field type
 * 
 * @author Alexandre Guyon
 */
public class Chunk extends Element
{
	//*********************** Constants *****************************/
	
	/**
	 * The fire sprite
	 */
	public final static String FIRE = "./res/chunks/fire.png"; 
	
	/**
	 * The castle sprite
	 */
	public final static String CASTLE = "./res/chunks/castle.png";
	
	/**
	 * The brick sprite
	 */
	public final static String BRICK = "./res/chunks/brick.png";
	
	/**
	 * The pipe sprite
	 */
	public final static String PIPE = "./res/chunks/pipe.jpg";
	
	/**
	 * The pink brick sprite
	 */
	public final static String PINK_BRICK = "./res/chunks/pink_brick.jpg";
	
	//*********************** Variables *****************************/
	
	/**
	 * The enumerate field type of the chunk
	 */
	private FieldType type;

	//************************ Constructors *************************/
	
	/**
	 * Allow to create a chunk by giving position size and field type
	 * @param b The bounding box of the chunk
	 * @param type The field type of the chunk
	 * @throws NegativeSizeException When the size given is negative
	 */
	public Chunk(BoundingBox b, FieldType type)
	{
		super(b);
		this.type = type;
	}
	
	/**
	 * Constructs a chunk object with xml node
	 * @param l (NodeList) xml node list pointing to chunk node
	 * @throws NegativeSizeException Bad size in xml
	 */
	public Chunk(NodeList l) throws NegativeSizeException
	{
		super(null);
		this.importChunk(l);
	}
	
	//******************************* Methods ***********************/
	
	/**
	 * Set the value of the current chunk
	 * @param l (NodeList) xml node list
	 * @throws NegativeSizeException Bas size in xml
	 */
	private void importChunk(NodeList l) throws NegativeSizeException
	{
		// Begin
		int i,j;
		int x = 0, y = 0;
		Coordinates positionC = null, sizeC = null;
		
		// For all the element of the chunk node
		for(i = 0; i < l.getLength() ; i++)
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
					sizeC = new Coordinates(x,y);
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
					positionC = new Coordinates(x,y);
					break;
				case "type":
					NodeList type = l.item(i).getChildNodes();
					switch(type.item(0).getNodeValue())
					{
						case "FIRE":
							this.type = FieldType.FIRE;
							break;
						case "CASTLE":
							this.type = FieldType.CASTLE;
							break;
						case "BRICK":
							this.type = FieldType.BRICK;
							break;
						case "PIPE":
							this.type = FieldType.PIPE;
							break;
						case "PINK_BRICK":
							this.type = FieldType.PINK_BRICK;
							break;
					}
					break;
			}
		}
		this.setArea(new BoundingBox(positionC, sizeC));
	}
	
	//*************************** Getters & Setters *************************/
	
	public FieldType getFieldType()
	{
		return this.type;
	}
	
	public void saveXML(Document doc, Node n)
	{
		Node chunk, size, pos, type;
		Node e;
		n.appendChild(doc.createElement("chunk"));
		n = n.getLastChild();
		
		// Recherche de chunk
		while(n.getNodeName() != "chunk")
			n = n.getPreviousSibling();
		
		// Si trouvé
		if(n.getNodeName() == "chunk")
		{
			chunk = n;
			
			// Size
			chunk.appendChild(doc.createElement("size"));
			
			n = chunk.getFirstChild();
			while(n.getNodeName() != "size")
				n = n.getNextSibling();
			if(n.getNodeName() == "size")
			{
				size = n;
				
				e = doc.createElement("x");
				e.setTextContent(this.getArea().getSize().getX() + "");
				size.appendChild(e);
				
				e = doc.createElement("y");
				e.setTextContent(this.getArea().getSize().getY() + "");
				size.appendChild(e);
			}
			else
				System.out.println("pb saveXML in Chunk.java !");
			// Position 
			chunk.appendChild(doc.createElement("position"));
			
			while(n.getNodeName() != "position")
				n = n.getNextSibling();
			if(n.getNodeName() == "position")
			{
				pos = n;
				
				e = doc.createElement("x");
				e.setTextContent(this.getArea().getPosition().getX() + "");
				pos.appendChild(e);
				
				e = doc.createElement("y");
				e.setTextContent(this.getArea().getPosition().getY() + "");
				pos.appendChild(e);
			}
			else
				System.out.println("pb saveXML in Chunk.java !");
			// Type
			chunk.appendChild(doc.createElement("type"));
			
			while(n.getNodeName() != "type")
				n = n.getNextSibling();
			if(n.getNodeName() == "type")
			{
				type = n;
				type.setTextContent(this.getFieldType().toString());
			}
			else
				System.out.println("pb saveXML in Chunk.java !");
		}
		else // Si chunk pas trouvé
			System.out.println("pb saveXML in Chunk.java !");
	}
	
	public Chunk clone()
	{
		return new Chunk(this.getArea(), this.type);
	}
	
	@Override
	public String toString()
	{
		return "Chunk -> Field Type : " + this.type.toString() + "\n" + super.toString();
	}
}