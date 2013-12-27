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
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

import fr.utbm.lo43.jvivarium.core.BoundingBox;
import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.Coordinates;
import fr.utbm.lo43.jvivarium.core.Element;
import fr.utbm.lo43.jvivarium.core.Entity;
import fr.utbm.lo43.jvivarium.core.Map;
import fr.utbm.lo43.jvivarium.core.XMLLoader;

/**
 * Display the map in a JFrame
 * 
 * @author Alexandre Guyon
 */
public class EditorPanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, MenuListener, KeyListener
{	
	/**
	 * Serialize number
	 */
	private static final long serialVersionUID = 1L;

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
		
		int x=0,y=0;
		
		// Set the size of the panel in functions the most far chunk 
		for (Iterator<Chunk> it = this.map.getChunks().iterator(); it.hasNext();)
		{
			Chunk c = it.next();
			
			if(c.getArea().getPosition().getX() + c.getArea().getSize().getX() > x)
				x = c.getArea().getPosition().getX() + c.getArea().getSize().getX();
			if(c.getArea().getPosition().getY() + c.getArea().getSize().getY() > y)
				y = c.getArea().getPosition().getY() + c.getArea().getSize().getY();
		}
		this.setSize(x, y);
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
		
		// Draw all chunks
		List<Chunk> l = this.map.getChunks();
		for (Iterator<Chunk> it = l.iterator(); it.hasNext();)
		{
			Chunk c = it.next();
			c.paint(g);
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
		public void saveMap()
		{
			XMLLoader xml = new XMLLoader();
			xml.saveChunks(this.map.getChunks());
			// TODO Save Entity
			// TODO Save Objects
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
