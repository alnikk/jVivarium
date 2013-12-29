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
	 * Image Chunk Fire
	 */
	private BufferedImage iCFire;
	
	/**
	 * Image Chunk Castle
	 */
	private BufferedImage iCCastle;
	
	/**
	 * Image Chunk Brick
	 */
	private BufferedImage iCBrick;
	
	/**
	 * Image Chunk Pipe
	 */
	private BufferedImage iCPipe;
	
	/**
	 * Image Chunk Pink Brick
	 */
	private BufferedImage iCPinkBrick;

	/**
	 * Constructor of the class.
	 * Initialize the view panel
	 * of the game
	 */
	public GamePanel()
	{
		this.setBackground(new Color(0, 0, 0));
		
		Coordinates c = map.getMaxMap();
		
		this.setSize(c.getX(), c.getY());
		this.setPreferredSize(new Dimension(c.getX(), c.getY()));
		this.setMaximumSize(new Dimension(c.getX(), c.getY()));
		this.setMinimumSize(new Dimension(c.getX(), c.getY()));
		
		// Initialize Images
		try
		{
			this.iCCastle = ImageIO.read(new File(Chunk.CASTLE));
			this.iCFire = ImageIO.read(new File(Chunk.FIRE));
			this.iCPinkBrick = ImageIO.read(new File(Chunk.PINK_BRICK));
			this.iCPipe = ImageIO.read(new File(Chunk.PIPE));
			this.iCBrick = ImageIO.read(new File(Chunk.BRICK));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g)
	{
		Entity e;
		
		super.paint(g);
		
		// Draw all chunks
		BufferedImage img = null;
		Chunk c;
		
		// Draw all chunks
		for(Iterator<Chunk> it = this.map.getChunks().iterator(); it.hasNext();)
		{
			try
			{
				c = it.next();
				
				switch (c.getFieldType())
				{
					case BRICK:
						img = iCBrick;
						break;
					case CASTLE:
						img = iCCastle;
						break;
					case FIRE:
						img = iCFire;
						break;
					case PINK_BRICK:
						img = iCPinkBrick;
						break;
					case PIPE:
						img = iCPipe;
						break;
				}
				
				g.drawImage(img, c.getArea().getPosition().getX(),
						c.getArea().getPosition().getY(), 
						c.getArea().getSize().getX(), 
						c.getArea().getSize().getY(), null);
			}
			catch(Exception ec)
			{
				// TODO Better display
				break;
			}
		}
		
		// Draw all entity
		for(Iterator<Entity> ite = this.map.getEntitys().iterator(); ite.hasNext();)
		{
			try{
			e = ite.next();
			}catch(Exception exc)
			{
				break;
			}
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
		for(Iterator<Obj> ito = this.map.getObjects().iterator(); ito.hasNext();)
		{
			Obj o = ito.next();
			
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
