package fr.utbm.lo43.jvivarium.core;

import java.util.Random;

public final class Mario extends Entity
{
	/**
	 * The image file for Mario
	 */
	public final static String IMG = "./res/entity/mario1.png";
	
	public Mario(BoundingBox area)
	{
		super(area);
	}

	@Override
	public void life()
	{
		this.move();
		
		// if Mario is on an Obj, he will eat it
		if(Map.getMap().getObjAt(this.getArea().getPosition()) != null)
			this.eat();
	}
	
	/**
	 * This Mario object will move on the map
	 */
	private void move()
	{
		Coordinates max = Map.getMap().getMaxMap();
		BoundingBox b = this.getArea();
		int x,y;
		
		// Check valid coordinates
		do
		{
			// Choice of coordinates
			x = (int) Math.round(Math.random()*20);
			if(Math.random() < 0.5)
				x = -x;
			
			y = (int) Math.round(Math.random()*20);
			if(Math.random() < 0.5)
				y = -y;
			
			// test
			try
			{
				b = this.getArea().translate(new Coordinates(x, y));
			}
			catch (NegativeSizeException e)
			{
				e.printStackTrace();
			}
		}while((b.getPosition().getX() < 0 
				|| b.getPosition().getX() + b.getSize().getX() > max.getX())
				|| (b.getPosition().getY() < 0
				|| b.getPosition().getY() + b.getSize().getY() > max.getY()));
		
		// Set new position
		this.setArea(b);	
	}
	
	/**
	 * Mario will eat something on the map
	 */
	private void eat()
	{
		// if the object is a MUSHROOM, we regenerate the life points of Mario
		if(Map.getMap().getObjAt(this.getArea().getPosition()).getType() == ObjectType.MUSHROOM)
			this.setLife(getMaxLife());
		
		//Mario eat this object, so we can remove it from the map
		Map.getMap().remove(Map.getMap().getObjAt(this.getArea().getPosition()));
	}
	

	
	/**
	 * Mario will attack Bowser
	 */
	private void attack()
	{
		
	}

}
