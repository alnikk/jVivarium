package fr.utbm.lo43.jvivarium.core;

import java.util.Iterator;

public final class Bowser extends Entity
{
	//********************** Constants ********************
	/**
	 * The image file for Bowser
	 */
	public final static String IMG = "./res/entity/bowser1.png";
	
	/**
	 * The max Vision of a Bowser entity
	 */
	private final static int VISION = 200;
	
	/**
	 * The attack point of a Bowser Entity
	 */
	private final static int ATTACK = 15;
	
	/**
	 * The move of a Bowser entity
	 */
	private final static int MOVE = 3;
	
	//************************* Attributes ****************
	/**
	 * If browser kidnap Peach
	 */
	private Peach kidnaped = null;
	
	//**************************** Constructor ****************
	public Bowser(BoundingBox area)
	{
		super(area);
		this.attPoints = ATTACK;
		this.vision = VISION;
		this.move = MOVE;
	}

	public void finalize()
	{
		if(kidnaped != null)
			Map.getMap().add(kidnaped);
	}
	
	//****************************** Methods ********************
	
	@Override
	public void life()
	{
		// Allow to act only one time
		boolean act = false;
		Entity e;
		
		if(kidnaped == null)
		{
			for(Iterator<Entity> itp = Map.getMap().searchEntity(Peach.class).iterator(); itp.hasNext();)
			{
				e = itp.next();
				
				if(this.overlapping(e) != null)
				{
					this.kidnap((Peach) e);
					act = true;
					break;
				}
			}
		}
		
		if(!act)
		{
			for(Iterator<Entity> itp = Map.getMap().searchEntity(Mario.class).iterator(); itp.hasNext();)
			{
				e = itp.next();
				
				if(this.overlapping(e) != null)
				{
					this.attack(e);
					act = true;
					break;
				}
			}
		}
		
		if(!act)
			this.move();
	}
	
	private void move()
	{
		Element e = Map.getMap().searchNearest(this, Peach.class);
		
		if(e != null)
			this.goNearTo(e,this.move);
	}
	
	private void attack(Entity e)
	{
		// Bowser lose life points, he lose more life points than Mario
		e.setLifePoints(getLifePoints() - this.attPoints);
		
		// TODO In the main loop I think... (turn by turn effect will be better, and so order in the list no matter)
		if(e.getLifePoints() == 0)
			Map.getMap().remove(e);
	}
	
	private void kidnap(Peach p)
	{
		if(kidnaped == null)
		{
			// if Bowser has not already kidnaped Peach
			kidnaped = p;
			// we remove her of the map
			Map.getMap().remove(p);
		}
	}
}
