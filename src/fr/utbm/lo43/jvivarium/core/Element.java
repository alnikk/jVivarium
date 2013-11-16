package fr.utbm.lo43.jvivarium.core;

import fr.utbm.lo43.jvivarium.core.BoundingBox;
import fr.utbm.lo43.jvivarium.core.Element;
import fr.utbm.lo43.jvivarium.core.Type;

/**
 * The element Class is the basic class of 
 * all drowable elements on the grid.<br/>
 * It defines all stuff needed for position 
 * and collision.
 * 
 * @author Alexandre Guyon
 */
public abstract class Element
{
	/**
	 * It defines the type of the element.
	 */
	private Type type;
	
	/**
	 * Area of the grid filled by the element
	 */
	private BoundingBox boundingBox;

	// ************* Constructor ***************
	/**
	 * This constructor sets the area of the grid 
	 * filled by the element .<br/>
	 * It defines the BoundingBox of the element.
	 * 
	 * @param area The BoundingBox of the element to create.
	 * @param t The type of the element
	 */
	public Element(BoundingBox area, Type t)
	{
		super();
		this.boundingBox = area;
		this.setType(t);
	}

	// ************* Methods **************
	/**
	 * Calculate the overlapping of two 
	 * Elements' Object. The compute is 
	 * made through bounding box. The first 
	 * element to compare is the current 
	 * element and the second one is given 
	 * as argument.
	 * 
	 * @param e The element to compute the 
	 * 			overlapping with.
	 * @return (BoundingBox) the intersection 
	 * 			of both elements if it exists, 
	 * 			<tt>null</tt> otherwise
	 */
	public BoundingBox overlapping(Element e)
	{
		return this.boundingBox.intersection(e.boundingBox);
	}
	
	/**
	 * Method to check if a point is contained in this bounding box.
	 * 
	 * @param point Coordinates of the point to check.
	 * @return <tt>true</tt> if the point is in the area,
	 * 		   <tt>false</tt> otherwise.
	 */
	public boolean pointIn(Coordinates c)
	{
		return this.boundingBox.pointIn(c);
	}
	

	// ************* Getters and Setters ***************

	/**
	 * This method returns the bounding box of the element
	 * 
	 * @return return the bounding box of the element
	 */
	public BoundingBox getArea()
	{
		return this.boundingBox;
	}

	/**
	 * This method allows to modify the location 
	 * and size (BoundingBox) of the element.
	 * 
	 * @param area bounding box of the element
	 */
	public void setArea(BoundingBox area)
	{
		this.boundingBox = area;
	}

	/**
	 * Return the type of the element.
	 * @return the type
	 */
	public Type getType()
	{
		return this.type;
	}

	/**
	 * Set the type of the element.
	 * @param type the type to set
	 */
	public void setType(Type type)
	{
		this.type = type;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Element [ position=" + this.getArea().getPosition() + ", taille=" + this.getArea().getSize() + "]"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$//$NON-NLS-4$
	}
}
