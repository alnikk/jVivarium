package fr.utbm.lo43.jvivarium.core;


public abstract class Entity extends Element
{
	//************************ Constants **********************
	/**
	 * The maximum life of an entity
	 */
	protected final int MAX_LIFE = 100;
	
	//*************************** Attributes ********************
	/**
	 * The life points of the entity. When 0 the entity is dead
	 */
	protected int lifePoints = 100;
	
	/**
	 * Number of life points lose per attack
	 */
	protected int attPoints;
	
	/**
	 * The vision of the entity
	 */
	protected int vision;
	
	/**
	 * The max move in one action
	 */
	protected int move;
	
	//********************************* Construtor ****************
	/**
	 * Constructors of the class.
	 * @param area (BoundingBox) Set the position and the size of this entity
	 */
	public Entity(BoundingBox area)
	{
		super(area);
		this.lifePoints = MAX_LIFE;
	}
	
	
	//******************************* Methods *************************
	
	/**
	 * The life method is called by the main of the game.
	 * This method is the IA  of the entity
	 */
	public abstract void life();
	
	/**
	 * Get the life points of the entity
	 * @return The life point of the entity
	 */
	public synchronized int getLifePoints()
	{
		return lifePoints;
	}

	/**
	 * Set the life points of the entity
	 * @param lifePoints The life points to set
	 */
	protected synchronized void setLifePoints(int lifePoints)
	{
		this.lifePoints = lifePoints;
	}

	/**
	 * Go near to an element
	 * @param e The element to approach
	 * @param move The distance to move
	 */
	protected void goNearTo(Element e, int move)
	{
		Coordinates max = Map.getMap().getMaxMap();
		BoundingBox b = this.getArea();
		int x,y;
		
		if(e.getArea().getPosition().getX() < this.getArea().getPosition().getX())
			x = -move;
		else if(e.getArea().getPosition().getX() == this.getArea().getPosition().getX())
			x = 0;
		else
			x = move;
		
		if(e.getArea().getPosition().getY() < this.getArea().getPosition().getY())
			y = -move;
		else if(e.getArea().getPosition().getY() == this.getArea().getPosition().getY())
			y = 0;
		else
			y = move;
			
		
		// test
		try
		{
			b = this.getArea().translate(new Coordinates(x, y));
			
			// Set new position if valid coordinates
			if((b.getPosition().getX() > 0 
					&& b.getPosition().getX() + b.getSize().getX() < max.getX())
					&& (b.getPosition().getY() > 0
					&& b.getPosition().getY() + b.getSize().getY() < max.getY()))
				this.setArea(b);
		}
		catch (NegativeSizeException e1)
		{
			e1.printStackTrace();
		}
	}
	
	/**
	 * Run away from an element
	 * @param e The element to avoid
	 * @param move The move to do
	 */
	protected void runAwayFrom(Element e, int move)
	{
		Coordinates max = Map.getMap().getMaxMap();
		BoundingBox b = this.getArea();
		int x,y;
		
		// Avoid Element
		if(e.getArea().getPosition().getX() < this.getArea().getPosition().getX())
			x = move;
		else if(e.getArea().getPosition().getX() == this.getArea().getPosition().getX())
			x = 0;
		else
			x = -move;
		
		if(e.getArea().getPosition().getY() < this.getArea().getPosition().getY())
			y = move;
		else if(e.getArea().getPosition().getY() == this.getArea().getPosition().getY())
			if(x==0)y = move;
			else y=0;
		else
			y = -move;
			
		
		// test
		try
		{
			b = this.getArea().translate(new Coordinates(x, y));
			
			// Set new position if valid coordinates
			if((b.getPosition().getX() > 0 
					&& b.getPosition().getX() + b.getSize().getX() < max.getX())
					&& (b.getPosition().getY() > 0
					&& b.getPosition().getY() + b.getSize().getY() < max.getY()))
				this.setArea(b);
		}
		catch (NegativeSizeException e1)
		{
			e1.printStackTrace();
		}
	}
}