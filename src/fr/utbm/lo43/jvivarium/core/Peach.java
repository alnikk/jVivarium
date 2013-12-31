package fr.utbm.lo43.jvivarium.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public final class Peach extends Entity
{
	//******************************** Constant ********************
	/**
	 * The image file for Peach
	 */
	public final static String IMG = "./res/entity/peach1.png";
	
	/**
	 * Probability of having a baby (0 <= p <= 1)
	 */
	private final static double PRO_BABY = 0.01;
	
	/**
	 * Probability of having Mario (< 0.5) or Peach (> 0.5) (0 <= p <= 1)
	 */
	private final static double PRO_MarioPeach = 0.5;
	
	/**
	 * The amount of damage that Peach do when she attacks
	 */
	private final static int ATT_POINTS = 0;
	
	/**
	 * Vision of Peach (in pixel)
	 */
	private final static int VISION = 40;
	
	/**
	 * Max moving peach
	 */
	private final static int MOVE = 2;
	
	//********************************* Attributes ********************
	
	
	//******************************* Constructor ******************
	public Peach(BoundingBox area)
	{
		super(area);
		this.attPoints = ATT_POINTS;
	}

	//****************************** Methods ************************
	
	@Override
	public void life()
	{
		// Allow the entity to do only one action
		boolean act = false;
		
		if(!act)
		{
			// If bowser is next to peach, run away
			List<Entity> le = Map.getMap().scanEntity(this, VISION);
			Entity e;
			
			for(Iterator<Entity> it = le.iterator(); it.hasNext();)
			{
				e = it.next();
				
				if(e instanceof Bowser)
				{
					this.runAwayFrom((Element) e, MOVE);
					act = true;
				}
			}
		}
		
		// if Mario and a Peach are at the same place, they will reproduce
		if(!act)
		{
			Entity eMario;

			for(Iterator<Entity> it = Map.getMap().getEntitys().iterator(); it.hasNext();)
			{
				eMario = it.next();
				
				// Search Mario
				if(eMario instanceof Mario)
				{
					// If Mario touch Peach
					if(eMario.overlapping(this) != null)
					{
						this.reproduce();
						act = true;
						break;
					}
				}
			}
		}
		
		// If no action, move on preferred chunk
		if(!act)
			this.move();
	}
	
	/**
	 * Move Peach on its chunk
	 * or try to go on Mario
	 */
	private void move()
	{
		Chunk c;
		boolean findChunk = false;
		
		// Search its Chunks
		for(Iterator<Chunk> it = Map.getMap().scan(this, VISION).iterator(); it.hasNext();)
		{
			c = it.next();
			
			if(c.getFieldType() == FieldType.PINK_BRICK)
			{
				findChunk = true;
				break;
			}
		}
		
		// If Peach see its chunks
		if(findChunk)
		{
			//	Find and approach Mario
			this.goNearTo(Map.getMap().searchNearest(this, Mario.class),MOVE);
			System.out.println((Mario) Map.getMap().searchNearest(this, Mario.class));
		}
		else
			this.goNearTo(Map.getMap().searchNearest(this, FieldType.PINK_BRICK),MOVE);
	}
	
	/**
	 * Peach will reproduce herself with Mario
	 */
	private void reproduce()
	{
		//will the baby be a Mario or a Peach ?
		try
		{
			if(Math.random() < PRO_BABY)
			{
				if(Math.random() < PRO_MarioPeach)
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
