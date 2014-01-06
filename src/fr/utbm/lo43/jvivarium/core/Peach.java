package fr.utbm.lo43.jvivarium.core;

import java.util.Iterator;
import java.util.List;

// TODO Add time between reproduce


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
	private final static double PRO_BABY = 0.5;
	
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
	private final static int VISION = 10;
	
	/**
	 * Max moving peach
	 */
	private final static int MOVE = 2;
	
	//********************************* Attributes ********************
	private boolean child = false;
	private int timer=0;
	
	//******************************* Constructor ******************
	public Peach(BoundingBox area)
	{
		super(area);
		this.attPoints = ATT_POINTS;
		this.vision = VISION;
		this.move = MOVE;
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
			List<Entity> le = Map.getMap().scanEntity(this, this.vision);
			Entity e;
			
			for(Iterator<Entity> it = le.iterator(); it.hasNext();)
			{
				e = it.next();
				
				if(e instanceof Bowser)
				{
					this.runAwayFrom((Element) e, this.move);
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
		for(Iterator<Chunk> it = Map.getMap().scan(this, this.vision).iterator(); it.hasNext();)
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
			if(Map.getMap().searchNearest(this, Mario.class)!=null){
			//	Find and approach Mario
				if(!this.getChild()){
					this.goNearTo(Map.getMap().searchNearest(this, Mario.class),this.move);
				}
				else
					this.runAwayFrom(Map.getMap().searchNearest(this, Mario.class),this.move);
			}
		}
		else
			this.goNearTo(Map.getMap().searchNearest(this, FieldType.PINK_BRICK),this.move);
	}
	
	/**
	 * Peach will reproduce herself with Mario
	 */
	public boolean getChild(){return this.child;}
	public void setChild(boolean ch){this.child=ch;}
	private void reproduce()
	{
		timer=timer+1;
		if(timer==100){
			this.setChild(false);
			timer=0;
		}
		int randomx,randomy;
		//will the baby be a Mario or a Peach ?
		randomx = (int) (Math.random()*(Map.getMap().getMaxMap().getX()));
		randomy = (int) (Math.random()*(Map.getMap().getMaxMap().getY()));
		try
		
		{
			if(Math.random() < PRO_BABY)
			{
				// TODO Create new entity on another place than the existing peach
				if(Math.random() < PRO_MarioPeach)
					Map.getMap().add(new Mario(new BoundingBox(new Coordinates(randomx,randomy),new Coordinates(20,20))));
				else
					Map.getMap().add(new Peach(new BoundingBox(new Coordinates(randomx,randomy),new Coordinates(20,20))));
				this.setChild(true);
				this.runAwayFrom(Map.getMap().searchNearest(this, Mario.class),this.move);;
			}
		}
		
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
