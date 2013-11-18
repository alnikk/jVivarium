/**
 * 
 */
package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.EventListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.Coordinates;

/**
 * Display the map in a JFrame
 * 
 * @author Alexandre Guyon
 */
public class EditorPanel extends JPanel implements Runnable, MouseListener, MouseMotionListener, MenuListener
{
	/**
	 * Display's FPS of the panel
	 */
	private final int FPS = 35;
	
	/**
	 * List of chunks to display
	 */
	private List<Chunk> lChunk;
	
	
		//**** Listeners ****
	/**
	 * If the user is moving chunk
	 */
	private Chunk drag = null;
	
	/**
	 * Coordinates used for the chunk stay at the same distance from cursor
	 */
	private Coordinates followCursor = null;
	
	//****************************** Constructors *******************
	
	/**
	 * Constructors of the class.
	 * Load the map into the object and configure the frame
	 * @param list List of the map's chunks
	 */
	public EditorPanel(List<Chunk> list)
	{
		this.lChunk = list;
		int x=0,y=0;
		
		for (Iterator<Chunk> it = lChunk.iterator(); it.hasNext();)
		{
			Chunk c = it.next();
			
			if(c.getArea().getPosition().getX() + c.getArea().getSize().getX() > x)
				x = c.getArea().getPosition().getX() + c.getArea().getSize().getX();
			if(c.getArea().getPosition().getY() + c.getArea().getSize().getY() > y)
				y = c.getArea().getPosition().getY() + c.getArea().getSize().getY();
		}
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
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
		
		for (Iterator<Chunk> it = lChunk.iterator(); it.hasNext();)
		{
			Chunk c = it.next();
			// FIXME Add a paint method in chunk class
			g.setColor(new Color(0, 0, 0));
			g.drawRect(c.getArea().getPosition().getX(), c.getArea().getPosition().getY(), c.getArea().getSize().getX(), c.getArea().getSize().getY());
			switch(c.getFieldType())
			{
				case GRASS:
					g.setColor(new Color(0, 255, 0));
					break;
				case WATER:
					g.setColor(new Color(0, 0, 255));
					break;
				case ROCK:
					g.setColor(new Color(125, 125, 125));
					break;
			}
			g.fillRect(c.getArea().getPosition().getX() + 1, c.getArea().getPosition().getY() + 1, c.getArea().getSize().getX() - 1, c.getArea().getSize().getY() - 1);
		}
	}
	
	//**************************** MouseListener *********************
	
		@Override
		public void mouseClicked(MouseEvent e)
		{
			if(this.drag == null)
			{
				for (Iterator<Chunk> it = lChunk.iterator(); it.hasNext() && true;)
				{
					Chunk c = it.next();
					
					if(c.pointIn(new Coordinates(e.getX(), e.getY())))
					{
						followCursor = new Coordinates(
								e.getX() - c.getArea().getPosition().getX(), 
								e.getY() - c.getArea().getPosition().getY());
						this.drag = c;
					}
				}
			}
			else
				this.drag = null;
		}

		@Override
		public void mouseEntered(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e)
		{
			// TODO Auto-generated method stub
			
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
			if(this.drag != null)
			{
				this.drag.setArea(
						this.drag.getArea()
									.moveTo(new Coordinates(
											e.getX() - followCursor.getX(),
											e.getY() - followCursor.getY())));
			}
		}

		//****************************** Menu Listener ******************
		
		@Override
		public void addChunk(Chunk c)
		{
			this.lChunk.add(c);
		}
	
	
}
