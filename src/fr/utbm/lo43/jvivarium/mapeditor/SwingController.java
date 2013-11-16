/**
 * 
 */
package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.util.List;

import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.Coordinates;

/**
 * This class allow the user to control the
 * map editor, that is to say, move chunk,
 * create chunk etc
 * 
 * @author Alexandre Guyon
 */
public class SwingController implements MouseListener, MouseMotionListener
{	
	//**************************** Variables *************************
	
	/**
	 * If the user is moving chunk
	 */
	private Chunk drag = null;
	
	/**
	 * List of the map's chunks
	 */
	private List<Chunk> lChunk;
	
	/**
	 * Coordinates used for the chunk stay at the same distance from cursor
	 */
	private Coordinates followCursor = null;
	
	//**************************** Constructors *******************
	
	/**
	 * Constructors of the class.
	 * Initialize map elements for user can control its
	 * @param lChunk List of the map's chunks
	 */
	public SwingController(List<Chunk> lChunk)
	{
		this.lChunk = lChunk;
	}
	
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

	@Override
	public void mouseDragged(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
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
	
}
