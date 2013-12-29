package fr.utbm.lo43.jvivarium.core;

import java.util.Random;

public final class Peach extends Entity
{
	/**
	 * The image file for Peach
	 */
	public final static String IMG = "./res/entity/peach1.png";
	
	/**
	 * Probability of having a baby, ex: 8 mean 1 chance out of 8
	 */
	public final int proBaby = 8;
	
	public Peach(BoundingBox area)
	{
		super(area);
	}

	@Override
	public void life()
	{
		this.runAway();
		
		// if Mario and a Peach are at the same place, they will reproduce
		if(Map.getMap().getEntityAt(this.getArea().getPosition()) != null && Map.getMap().getEntityAt(this.getArea().getPosition()) instanceof Peach)
			this.reproduce();
	}
	
	private void runAway()
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
	 * Peach will reproduce herself with Mario
	 */
	private void reproduce()
	{
		Random r = new Random();
		//will the baby be a Mario or a Peach ?
		try
		{
			if(r.nextInt(proBaby) == 3)
			{
				if(Math.round(Math.random()) == 1)
					Map.getMap().add(new Mario(this.getArea()));
				else
					Map.getMap().add(new Peach(this.getArea()));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
