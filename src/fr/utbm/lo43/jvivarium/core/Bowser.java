package fr.utbm.lo43.jvivarium.core;

public final class Bowser extends Entity
{
	/**
	 * The image file for Bowser
	 */
	public final static String IMG = "./res/entity/bowser1.png";
	
	/**
	 * If browser kidnap Peach
	 */
	private Peach kidnaped = null;
	
	public Bowser(BoundingBox area)
	{
		super(area);
	}

	@Override
	public void life()
	{
		this.move();
	}
	
	private void move()
	{
		Coordinates max = Map.getMaxMap();
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
	
	private void eat()
	{
		
	}
	
	private void attack()
	{
		
	}
	
	private void kidnap()
	{
		
	}
	

}
