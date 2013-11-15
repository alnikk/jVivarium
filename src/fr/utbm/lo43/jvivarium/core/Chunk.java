/**
 * 
 */
package fr.utbm.lo43.jvivarium.core;

import org.w3c.dom.NodeList;

/**
 * A chunk is a part of the map.
 * It's described by a bounding box
 * (coordinates, and size) and
 * field type
 * 
 * @author Alexandre Guyon
 */
public class Chunk extends BoundingBox
{
	//*********************** Variables *****************************/
	
	/**
	 * The enumerate field type of the chunk
	 */
	private FieldType type;

	//************************ Constructors *************************/
	
	/**
	 * Allow to create a chunk by giving position size and field type
	 * @param position The position of the chunk in the map
	 * @param size The size of the Chunk
	 * @param type The field type of the chunk
	 * @throws NegativeSizeException When the size given is negative
	 */
	public Chunk(Coordinates position, Coordinates size, FieldType type) throws NegativeSizeException
	{
		super(position, size);
		this.type = type;
	}
	
	/**
	 * Constructs a chunk object with xml node
	 * @param l (NodeList) xml node list
	 * @throws NegativeSizeException Bad size in xml
	 */
	public Chunk(NodeList l) throws NegativeSizeException
	{
		super(new Coordinates(0,0), new Coordinates(0,0));
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
					this.reSize(new Coordinates(x,y));
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
					this.moveTo(new Coordinates(x,y));
					break;
				case "type":
					NodeList type = l.item(i).getChildNodes();
					switch(type.item(0).getNodeValue())
					{
						case "GRASS":
							this.type = FieldType.GRASS;
						case "ROCK":
							this.type = FieldType.ROCK;
						case "WATER":
							this.type = FieldType.WATER;
					}
					break;
			}
		}
	}
	
	//*************************** Getters & Setters *************************/
	
	@Override
	public String toString()
	{
		return "Chunk -> Field Type : " + this.type.toString() + "\n" + super.toString();
	}
}