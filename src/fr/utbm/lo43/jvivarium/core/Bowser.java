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
		
		// if Bowser is on an Obj, he will eat it
		if(Map.getMap().getObjAt(this.getArea().getPosition()) != null)
			this.eat();
	}
	
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
	
	private void eat()
	{
		//Bowser eat this object, so we can remove it from the map
		Map.getMap().remove(Map.getMap().getObjAt(this.getArea().getPosition()));
	}
	
	private void attack()
	{
		
	}
	
	private void kidnap()
	{
		
	}
	

}
