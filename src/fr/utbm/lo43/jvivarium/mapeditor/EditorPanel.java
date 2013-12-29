/**
 * 
 */
package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import fr.utbm.lo43.jvivarium.controller.XMLLoader;
import fr.utbm.lo43.jvivarium.core.BoundingBox;
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

/**
 * Display the map in a JFrame
 * 
 * @author Alexandre Guyon
 */
public class EditorPanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, MenuListener, KeyListener
{	
	//******************************* Constants *************************
	/**
	 * Serialize number
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Size of the blank around the dragging element
	 */
	private static final int SIZE_SELECTED = 1;
	
	//****************************** Variable **********************
	/**
	 * Display's FPS of the panel
	 */
	private final int FPS = 35;
	
	/**
	 * Map singleton
	 */
	private Map map = Map.getMap();
	
	
		//**** Listeners ****
	/**
	 * If the user is moving chunk
	 */
	private Element drag = null;
	
	/**
	 * The old position of the chunk
	 */
	private BoundingBox oldPosition = null;
	
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
	
	//****************************** Constructors *******************
	
	/**
	 * Constructors of the class.
	 * Load the map into the object and configure the frame
	 * @param list List of the map's chunks
	 */
	public EditorPanel()
	{		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setBackground(new Color(0, 0, 0));
		
		// Set the size of the panel in functions the most far chunk 
		Coordinates max = map.getMaxMap();
		this.setSize(max.getX(), max.getY());
		
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
	
	//******************************** run ***************************/
	
	@Override
	public void run()
	{
		while(true)
		{
			this.repaint();
			try
			{
				Thread.sleep(1000/FPS);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	//***************************** Methods *************************
	
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
		
		//***** Draw in last the dragging chunk
		if(this.drag != null)
		{
				// Get the good picture
			if(this.drag instanceof Chunk)
			{
				switch (((Chunk) this.drag).getFieldType())
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
			}
			if(this.drag instanceof Mario)
				img = this.iMario;
			if(this.drag instanceof Peach)
				img = this.iPeach;
			if(this.drag instanceof Bowser)
				img = this.iBowser;
			if(this.drag instanceof Obj)
			{
				if(((Obj)this.drag).getType() == ObjectType.MUSHROOM)
					img = this.iMushroom;
				if(((Obj)this.drag).getType() == ObjectType.STAR)
					img = this.iStar;
			}
			// Add a rectangle around it
			g.setColor(new Color(255,255,255));
			g.fillRect(this.drag.getArea().getPosition().getX() - SIZE_SELECTED, 
					this.drag.getArea().getPosition().getY() - SIZE_SELECTED,
					this.drag.getArea().getSize().getX() + (SIZE_SELECTED*2),
					this.drag.getArea().getSize().getY() + (SIZE_SELECTED*2));
			
			// Draw it
			g.drawImage(img, 
					this.drag.getArea().getPosition().getX(), 
					this.drag.getArea().getPosition().getY(), 
					this.drag.getArea().getSize().getX(), 
					this.drag.getArea().getSize().getY(), null);
		}
	}
	
	//**************************** MouseListener *********************
	
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if(this.drag == null)
			{
				for (Iterator<Chunk> it = this.map.getChunks().iterator(); it.hasNext();)
				{
					Chunk c = it.next();
					
					if(c.pointIn(new Coordinates(e.getX(), e.getY())))
					{
						this.drag = c;
						this.oldPosition = c.getArea();
					}
				}
			}
			else
				this.drag = null;
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			
		}

		@Override
		public void mousePressed(MouseEvent e)
		{

		}

		@Override
		public void mouseReleased(MouseEvent e)
		{
			
		}
		
		//****************************** MouseMotionListener *******************

		@Override
		public void mouseDragged(MouseEvent e)
		{
			
		}

		@Override
		public void mouseMoved(MouseEvent e)
		{
			int x = 0, y = 0;
			if(this.drag != null)
			{
				int XCHUNK = this.drag.getArea().getSize().getX();
				int YCHUNK = this.drag.getArea().getSize().getY();
				
				if((e.getX() % XCHUNK) >= XCHUNK)
					x = e.getX() + (XCHUNK - (e.getX() % XCHUNK));
				else
					x = e.getX() - (e.getX() % XCHUNK);
				
				if((e.getY() % YCHUNK) >= YCHUNK)
					y = e.getY() + (YCHUNK - (e.getX() % YCHUNK));
				else
					y = e.getY() - ((e.getY() % YCHUNK));
				
				this.drag.setArea(
						this.drag.getArea()
							.moveTo(new Coordinates(x, y)));				
			}
		}

		//****************************** Menu Listener ******************
		
		@Override
		public void addChunk(Chunk c)
		{
			this.drag = c;
			this.map.add(c);
		}
		
		@Override
		public void addEntity(Entity e)
		{
			this.drag = e;
			this.map.add(e);
		}
		
		@Override
		public void addObject(Obj o)
		{
			this.drag = o;
			this.map.add(o);
		}
		
		@Override
		public void saveMap()
		{
			XMLLoader xml = new XMLLoader();
			xml.saveChunks(this.map.getChunks());
			xml.saveEntitys(this.map.getEntitys());
			xml.saveObjects(this.map.getObjects());

		}
		
		
		//*************************** Key Listener ************************	
		
		@Override
		public void keyPressed(KeyEvent arg0)
		{
			switch(arg0.getKeyCode())
			{
				case KeyEvent.VK_S: // Save map
					saveMap();
					break;
				case KeyEvent.VK_ESCAPE: // Cancel current move
					if(this.drag != null)
					{
						this.drag.setArea(this.oldPosition);
						this.drag = null;
					}
					break;
				case KeyEvent.VK_DELETE: // Delete chunk
					if(this.drag != null)
					{
						this.map.remove(this.drag);
					}
					break;
				default:
					System.out.println(arg0.getKeyChar() + " isn't handled");
			}
		}

		@Override
		public void keyReleased(KeyEvent arg0)
		{
		}

		@Override
		public void keyTyped(KeyEvent arg0)
		{
			
		}	
}
