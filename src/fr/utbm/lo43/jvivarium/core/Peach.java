package fr.utbm.lo43.jvivarium.core;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public final class Peach extends Entity
{
	//******************************** Constant ********************
	/**
	 * The image file for Peach
	 */
	public final static String IMG = "./res/entity/peach1.png";
	
	/**
	 * Probability of having a baby, ex: 8 mean 1 chance out of 8
	 */
	private final static int PRO_BABY = 8;
	
	/**
	 * Vision of Peach (in pixel)
	 */
	private final static int VISION = 100;
	
	/**
	 * Max moving peach
	 */
	private final static int MOVE = 2;
	
	//********************************* Attributes ********************
	
	
	//******************************* Constructor ******************
	public Peach(BoundingBox area)
	{
		super(area);
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
					this.runAway((Bowser) e);
					act = true;
				}
			}
		}
		
		// if Mario and a Peach are at the same place, they will reproduce
		if(!act)
		{
			if(Map.getMap().getEntityAt(this.getArea().getPosition()) != null 
					&& Map.getMap().getEntityAt(this.getArea().getPosition()) instanceof Peach)
			{
				this.reproduce();
				act = true;
			}
		}
		
		// If no action, move on preferred chunk
		if(!act)
			this.move();
	}
	
	private void runAway(Bowser bowser)
	{
		Coordinates max = Map.getMap().getMaxMap();
		BoundingBox b = this.getArea();
		int x,y;
		
		// Avoid Bowser
		if(bowser.getArea().getPosition().getX() < this.getArea().getPosition().getX())
			x = MOVE;
		else if(bowser.getArea().getPosition().getX() == this.getArea().getPosition().getX())
			x = 0;
		else
			x = -MOVE;
		
		if(bowser.getArea().getPosition().getY() < this.getArea().getPosition().getY())
			y = -MOVE;
		else if(bowser.getArea().getPosition().getY() == this.getArea().getPosition().getY())
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
	
	/**
	 * Move Peach on its chunk
	 * or try to go on Mario
	 */
	private void move()
	{
		Chunk c;
		boolean findChunk = false;
		
		// Search its Chunks
		for(Iterator<Chunk> it = Map.getMap().scanChunk(this, VISION).iterator(); it.hasNext();)
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
			Entity e;
			int x,y;
			BoundingBox b = null;
			Coordinates max = Map.getMap().getMaxMap();
			
			for(Iterator<Entity> it = Map.getMap().getEntitys().iterator();it.hasNext();)
			{
				e = it.next();
				
				if(e instanceof Mario)
				{
					if(e.getArea().getPosition().getX() < this.getArea().getPosition().getX())
						x = -MOVE;
					else if(e.getArea().getPosition().getX() == this.getArea().getPosition().getX())
						x = 0;
					else
						x = MOVE;
					
					if(e.getArea().getPosition().getY() < this.getArea().getPosition().getY())
						y = -MOVE;
					else if(e.getArea().getPosition().getY() == this.getArea().getPosition().getY())
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
					catch (NegativeSizeException e1)
					{
						e1.printStackTrace();
					}					
					break;
				}
			}
		}
		else
		{
			// Try to go on the more near pink chunk
			Chunk n, cRes = null;
			List<Chunk> res = new LinkedList<Chunk>();
			double module1 = VISION*2,module2 = VISION*2,module3 = VISION*2,module4 = VISION*2; // Mooooochhheee
			double moduleRes = VISION*2, mod;
			int x1,x2,y1,y2,x,y;
			BoundingBox b = null;
			Coordinates max = Map.getMap().getMaxMap();
			
			int xe = this.getArea().getPosition().getX() + (this.getArea().getSize().getX()/2);
			int ye = this.getArea().getPosition().getY() + (this.getArea().getSize().getY()/2);
			
			// Search of all pink chunk
			for(Iterator<Chunk> it = Map.getMap().getChunks().iterator(); it.hasNext();)
			{
				n = it.next();
				
				if(n.getFieldType() == FieldType.PINK_BRICK)
					res.add(n);
			}
			
			// Search more near pink chunk
			for(Iterator<Chunk> i = res.iterator();i.hasNext();)
			{
				n = i.next();
				
				x1 = n.getArea().getPosition().getX() - xe;
				x2 = n.getArea().getPosition().getX() + n.getArea().getSize().getX() - xe;
				y1 = n.getArea().getPosition().getY() - ye;
				y2 = n.getArea().getPosition().getY() + n.getArea().getSize().getY() - ye;
				
				module1 = Math.sqrt(Math.pow(x1, 2)+Math.pow(y1, 2));
				module2 = Math.sqrt(Math.pow(x1, 2)+Math.pow(y2, 2));
				module3 = Math.sqrt(Math.pow(x2, 2)+Math.pow(y1, 2));
				module4 = Math.sqrt(Math.pow(x2, 2)+Math.pow(y2, 2));
				
				if(cRes != null)
				{
					mod = Math.min(Math.min(module1, module2), Math.min(module3, module4));
					if(moduleRes > mod)
						moduleRes = mod;
				}
				else
				{
					cRes = n;
					moduleRes = Math.min(Math.min(module1, module2), Math.min(module3, module4));
				}
			}
			
			if(cRes.getArea().getPosition().getX() < this.getArea().getPosition().getX())
				x = -MOVE;
			else if(cRes.getArea().getPosition().getX() == this.getArea().getPosition().getX())
				x = 0;
			else
				x = MOVE;
			
			if(cRes.getArea().getPosition().getY() < this.getArea().getPosition().getY())
				y = -MOVE;
			else if(cRes.getArea().getPosition().getY() == this.getArea().getPosition().getY())
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
			catch (NegativeSizeException e1)
			{
				e1.printStackTrace();
			}
		}
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
			if(r.nextInt(PRO_BABY) == 3)
			{
				if(Math.round(Math.random()) < 0.5)
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
