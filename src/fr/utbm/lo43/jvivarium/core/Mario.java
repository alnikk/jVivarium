package fr.utbm.lo43.jvivarium.core;

import java.util.Iterator;

public final class Mario extends Entity
{
	//******************************** Constant ***************************
	
	/**
	 * The image file for Mario
	 */
	public final static String IMG = "./res/entity/mario1.png";
	
	/**
	 * The max Vision of a Mario entity
	 */
	private final static int VISION = 10;
	
	/**
	 * The attack point of a Mario Entity
	 */
	private final static int ATTACK = 10;
	
	/**
	 * The move of a Mario entity
	 */
	private final static int MOVE = 3;
	
	//********************************* Attributes ************************
	
	
	
	//********************************* Constructor ************************
	
	public Mario(BoundingBox area)
	{
		super(area);
		this.attPoints = ATTACK;
		this.vision = VISION;
		this.move = MOVE;
	}
	

	//********************************* Methods *****************************
	
	@Override
	public void life()
	{
		// Allow the Mario to only executes one action
		boolean act = false;
		Entity b = null;
		Obj o = null;
		
		// If Mario and Bowser touch them-self, attack
		for(Iterator<Entity> itb = Map.getMap().searchEntity(Bowser.class).iterator(); itb.hasNext();)
		{
			b = itb.next();
			
			// If they are overlapping
			if(this.overlapping(b) != null)
			{
				this.attack((Bowser) b);
				act = true;
				break;
			}
		}
		
		// if Mario is on an Obj, he will eat it
		if(!act)
		{
			for(Iterator<Obj> ito = Map.getMap().getObjects().iterator(); ito.hasNext();)
			{
				o = ito.next();
				
				if(this.overlapping(o) != null)
				{
					this.eat(o);
					act = true;
					break;
				}
			}
		}
		
		if(!act)
			this.move();
	}
	
	/**
	 * Mario will attack Bowser
	 */
	private void attack(Bowser b)
	{
		// Bowser lose life points, he lose more life points than Mario
		b.setLifePoints(b.getLifePoints() - this.attPoints);
		
		// TODO In the main loop I think... (turn by turn effect will be better, and so order in the list no matter)
		if(b.getLifePoints() <= 0)
			Map.getMap().remove(b);
	}
	
	/**
	 * Mario will eat something on the map
	 */
	private void eat(Obj o)
	{
		// if the object is a MUSHROOM, we regenerate the life points of Mario
		switch(o.getType())
		{
			case MUSHROOM:
				this.setLifePoints(this.MAX_LIFE);
				break;
			case STAR:
				System.out.println("Mario eats a star");
				break;
		}
		
		//Mario eat this object, so we can remove it from the map
		Map.getMap().remove(o);
	}
	
	/**
	 * This Mario object will move on the map
	 */
	private void move()
	{		
		Entity p = Map.getMap().searchNearest(this, Peach.class);
		Entity b = Map.getMap().searchNearest(this, Bowser.class);
		Element e = this;
		Obj o = Map.getMap().searchNearest(e, ObjectType.MUSHROOM);
		
		if(o!=null)
			this.goNearTo(o, this.move);
		else{
			if(p == null)
				this.goNearTo(b, this.move);
		else if(b == null)
			//if (!((Peach) p).getChild())	
				this.goNearTo(p, this.move);
		else
		{
			e = Map.compNear(this, b, p);
			if(e != null)
				this.goNearTo(e, this.move);
			else // Bowser for combativity
				this.goNearTo(b, this.move);
			}
		}
	}
}
