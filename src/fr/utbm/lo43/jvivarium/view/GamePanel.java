package fr.utbm.lo43.jvivarium.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.utbm.lo43.jvivarium.core.Bowser;
import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.Coordinates;
import fr.utbm.lo43.jvivarium.core.Element;
import fr.utbm.lo43.jvivarium.core.Entity;
import fr.utbm.lo43.jvivarium.core.Map;
import fr.utbm.lo43.jvivarium.core.Mario;
import fr.utbm.lo43.jvivarium.core.Obj;
import fr.utbm.lo43.jvivarium.core.ObjectType;
import fr.utbm.lo43.jvivarium.core.Peach;

public class GamePanel extends JPanel implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Map of the game
	 */
	private Map map = Map.getMap();

	/**
	 * Constructor of the class.
	 * Initialize the view panel
	 * of the game
	 */
	public GamePanel()
	{
		this.setBackground(new Color(0, 0, 0));
		
		Coordinates c = Map.getMaxMap();
		
		this.setSize(c.getX(), c.getY());
		this.setPreferredSize(new Dimension(c.getX(), c.getY()));
		this.setMaximumSize(new Dimension(c.getX(), c.getY()));
		this.setMinimumSize(new Dimension(c.getX(), c.getY()));
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
	// Draw all chunks
		List<Chunk> lc = this.map.getChunks();
		for(Iterator<Chunk> it = lc.iterator(); it.hasNext();)
		{
			Chunk c = it.next();
			c.paint(g);
		}
		
		// Draw all entity
		List<Entity> le = this.map.getEntitys();
		for(Iterator<Entity> it = le.iterator(); it.hasNext();)
		{
			Entity e = it.next();
			
			BufferedImage img = null;
			if(e instanceof Mario)
			{
				try
				{
					img = ImageIO.read(new File(Mario.IMG));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			if(e instanceof Peach)
			{
				try
				{
					img = ImageIO.read(new File(Peach.IMG));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			if(e instanceof Bowser)
			{
				try
				{
					img = ImageIO.read(new File(Bowser.IMG));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			if(img != null)
			{
				g.drawImage(img, 
						e.getArea().getPosition().getX(), 
						e.getArea().getPosition().getY(), 
						e.getArea().getSize().getX(), 
						e.getArea().getSize().getY(), null);
			}
		}
		
		// Draw all objects
		List<Obj> lo = this.map.getObjects();
		for(Iterator<Obj> it = lo.iterator(); it.hasNext();)
		{
			Obj o = it.next();
			
			BufferedImage img = null;
			
			if(o.getType() == ObjectType.MUSHROOM)
			{
				try
				{
					img = ImageIO.read(new File(Obj.MUSHROOM));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			if(o.getType() == ObjectType.STAR)
			{
				try
				{
					img = ImageIO.read(new File(Obj.STAR));
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
			if(img != null)
			{
				g.drawImage(img, 
						o.getArea().getPosition().getX(), 
						o.getArea().getPosition().getY(), 
						o.getArea().getSize().getX(), 
						o.getArea().getSize().getY(), null);
			}
		}
	}

	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(100);
				this.repaint();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
