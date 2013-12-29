package fr.utbm.lo43.jvivarium.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

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
	 * Map singleton
	 */
	private Map map = Map.getMap();
	
		//******* Image ********
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
	 * Image Mario
	 */
	private BufferedImage iMario;
	
	/**
	 * Image Peach
	 */
	private BufferedImage iPeach;
	
	/**
	 * Image Bowser
	 */
	private BufferedImage iBowser;
	
	/**
	 * Image Mushroom
	 */
	private BufferedImage iMushroom;
	
	/**
	 * Image Star
	 */
	private BufferedImage iStar;

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
			
			this.iMario = ImageIO.read(new File(Mario.IMG));
			this.iPeach = ImageIO.read(new File(Peach.IMG));
			this.iBowser = ImageIO.read(new File(Bowser.IMG));
			
			this.iMushroom = ImageIO.read(new File(Obj.MUSHROOM));
			this.iStar = ImageIO.read(new File(Obj.STAR));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g)
	{		
		super.paint(g);
		
		BufferedImage img = null;
		Chunk c;
		Obj o;
		Entity entity;
		
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
			catch(Exception e)
			{
				// TODO Better display
				break;
			}
		}
		
		// Draw all entity
		for(Iterator<Entity> it = this.map.getEntitys().iterator(); it.hasNext();)
		{
			entity = it.next();
			
			if(entity instanceof Mario)
				img = this.iMario;
			if(entity instanceof Peach)
				img = this.iPeach;
			if(entity instanceof Bowser)
				img = this.iBowser;
			
			if(img != null)
			{
				g.drawImage(img, 
						entity.getArea().getPosition().getX(), 
						entity.getArea().getPosition().getY(), 
						entity.getArea().getSize().getX(), 
						entity.getArea().getSize().getY(), null);
			}
		}
		
		// Draw all objects
		for(Iterator<Obj> ito = this.map.getObjects().iterator(); ito.hasNext();)
		{
			o = ito.next();
			
			
			if(o.getType() == ObjectType.MUSHROOM)
				img = this.iMushroom;
			if(o.getType() == ObjectType.STAR)
				img = this.iStar;
			
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
