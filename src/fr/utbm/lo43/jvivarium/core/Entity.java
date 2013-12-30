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
	public void goNearTo(Entity ent)
	{
		Coordinates max = Map.getMap().getMaxMap();
		BoundingBox b = this.getArea();
		int x,y;
		int move=2;
		
		// Avoid Bowser
		if(ent.getArea().getPosition().getX() < this.getArea().getPosition().getX())
			x = -move;
		else if(ent.getArea().getPosition().getX() == this.getArea().getPosition().getX())
			x = 0;
		else
			x = move;
		
		if(ent.getArea().getPosition().getY() < this.getArea().getPosition().getY())
			y =move;
		else if(ent.getArea().getPosition().getY() == this.getArea().getPosition().getY())
			y = 0;
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
		catch (NegativeSizeException e)
		{
			e.printStackTrace();
		}
	}
	public void goNearTo(Obj o)
	{
		int MOVE=2;
		Coordinates max = Map.getMap().getMaxMap();
		BoundingBox b = this.getArea();
		int x,y;
		
		if(o.getArea().getPosition().getX() < this.getArea().getPosition().getX())
			x = -MOVE;
		else if(o.getArea().getPosition().getX() == this.getArea().getPosition().getX())
			x = 0;
		else
			x = MOVE;
		
		if(o.getArea().getPosition().getY() < this.getArea().getPosition().getY())
			y =MOVE;
		else if(o.getArea().getPosition().getY() == this.getArea().getPosition().getY())
			y = 0;
		else
			y = -MOVE;
			
		
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
		catch (NegativeSizeException e)
		{
			e.printStackTrace();
		}
	}
	public void runAway(Entity ent)
	{
		Coordinates max = Map.getMap().getMaxMap();
		BoundingBox b = this.getArea();
		int x,y;
		int MOVE=2;
		
		if(ent.getArea().getPosition().getX() < this.getArea().getPosition().getX())
			x = MOVE;
		else if(ent.getArea().getPosition().getX() == this.getArea().getPosition().getX())
			x = 0;
		else
			x = -MOVE;
		
		if(ent.getArea().getPosition().getY() < this.getArea().getPosition().getY())
			y = -MOVE;
		else if(ent.getArea().getPosition().getY() == this.getArea().getPosition().getY())
			y = 0;
		else
			y = MOVE;
			
		
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
		catch (NegativeSizeException e)
		{
			e.printStackTrace();
		}
	}
	}