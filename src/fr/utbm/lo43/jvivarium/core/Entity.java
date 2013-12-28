package fr.utbm.lo43.jvivarium.core;


public abstract class Entity extends Element
{
	/**
	 * The maximum life of an entity
	 */
	private final int MAX_LIFE = 100;
	
	/**
	 * The life points of the entity. When 0 the entity is dead
	 */
	private int lifePoints = 100;
	
	/**
	 * Constructors of the class.
	 * @param area (BoundingBox) Set the position and the size of this entity
	 */
	public Entity(BoundingBox area)
	{
		super(area);
	}
	
	/**
	 * The life method is called by the main of the game.
	 * This method is the IA  of the entity
	 */
	public abstract void life();

	public String getLife(){
		return Integer.toString(lifePoints);	
	}
		
	}