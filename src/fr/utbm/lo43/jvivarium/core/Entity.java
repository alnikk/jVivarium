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
	 * Number of life points lose per attack
	 */
	private int attPoints = 5;
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

	public synchronized int getLife(){
		return lifePoints;	
	}
		
	
	public synchronized void setLife(int lp){
		lifePoints=lp;
	}
	
	public synchronized int getAttPoints(){
		return attPoints;
	}
	
	public synchronized void setAttPoints(int ap){
		attPoints = ap;
	}
	
	public synchronized int getMaxLife(){
		return MAX_LIFE;
	}
	}