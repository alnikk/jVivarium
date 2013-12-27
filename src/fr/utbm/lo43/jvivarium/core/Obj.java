package fr.utbm.lo43.jvivarium.core;

/**
 * Objects for help (or not ^^) entity
 * @author Alexandre Guyon
 */
public final class Obj extends Element
{
	/**
	 * Type of this object
	 */
	private ObjectType type;
	
	/**
	 * Contructors of the class.
	 * Define the type of this object.
	 * @param t The type of object you want to create
	 */
	public Obj(BoundingBox b, ObjectType t)
	{
		super(b);
		this.setType(t);
	}

	/**
	 * Get the type of object
	 * @return the type of the object
	 */
	public ObjectType getType()
	{
		return type;
	}

	/**
	 * Ste the type of this object
	 * @param type the type of object to set
	 */
	public void setType(ObjectType type)
	{
		this.type = type;
	}
}
