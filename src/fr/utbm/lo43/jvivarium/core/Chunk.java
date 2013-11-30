/**
 * 
 */
package fr.utbm.lo43.jvivarium.core;

import java.awt.Color;
import java.awt.Graphics;

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
		super(b, Type.CHUNK);
		this.type = type;
	}
	
	/**
	 * Constructs a chunk object with xml node
	 * @param l (NodeList) xml node list
	 * @throws NegativeSizeException Bad size in xml
	 */
	public Chunk(NodeList l) throws NegativeSizeException
	{
		super(null, null);
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
						case "GRASS":
							this.type = FieldType.GRASS;
							break;
						case "ROCK":
							this.type = FieldType.ROCK;
							break;
						case "WATER":
							this.type = FieldType.WATER;
							break;
					}
					break;
			}
		}
		this.setArea(new BoundingBox(positionC, sizeC));
	}
	
	public void paint(Graphics g)
	{
		g.setColor(new Color(0, 0, 0));
		g.drawRect(this.getArea().getPosition().getX(), this.getArea().getPosition().getY(), this.getArea().getSize().getX(), this.getArea().getSize().getY());
		switch(this.getFieldType())
		{
			case GRASS:
				g.setColor(new Color(0, 255, 0));
				break;
			case WATER:
				g.setColor(new Color(0, 0, 255));
				break;
			case ROCK:
				g.setColor(new Color(125, 125, 125));
				break;
		}
		g.fillRect(this.getArea().getPosition().getX() + 1, this.getArea().getPosition().getY() + 1, this.getArea().getSize().getX() - 1, this.getArea().getSize().getY() - 1);
	}
	
	//*************************** Getters & Setters *************************/
	
	public FieldType getFieldType()
	{
		return this.type;
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