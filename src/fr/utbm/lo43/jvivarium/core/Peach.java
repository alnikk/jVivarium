package fr.utbm.lo43.jvivarium.core;

public final class Peach extends Entity
{
	/**
	 * The image file for Peach
	 */
	public final static String IMG = "./res/entity/peach1.png";
	
	public Peach(BoundingBox area)
	{
		super(area);
	}

	@Override
	public void life()
	{
		this.runAway();
	}
	
	private void runAway()
	{
		int x = (int) Math.round(Math.random()*100);
		int y = (int) Math.round(Math.random()*100);
		System.out.println(x +":"+ y);
		
		if(x < 0)
			x=-x;
		if(y> 0)
			y=-y;
		
		try
		{
			this.setArea(this.getArea().translate(new Coordinates(x, y)));
		}
		catch (NegativeSizeException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void eat()
	{
		
	}
	
	private void reproduce()
	{
		
	}

}
