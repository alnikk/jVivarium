package fr.utbm.lo43.jvivarium.core;

/**
 * Objects for help (or not ^^) entity
 * @author Alexandre Guyon
 */
public final class Obj
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
	public Obj(ObjectType t)
	{
		this.type = t;
	}
}
