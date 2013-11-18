package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.utbm.lo43.jvivarium.core.BoundingBox;
import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.Coordinates;
import fr.utbm.lo43.jvivarium.core.FieldType;
import fr.utbm.lo43.jvivarium.core.NegativeSizeException;

public class MenuPanel extends JPanel implements Runnable, MouseListener
{
	//****************************** Constant ********************
	
	/**
	 * The X size of the Panel
	 */
	private final int XPANEL = 50;
	
	/**
	 * The Y size of the Panel
	 */
	private final int YPANEL = 200;
	
	/**
	 * The X size of each Chunk
	 */
	private final int XCHUNK = 20;
	
	/**
	 * The Y size of each Chunk
	 */
	private final int YCHUNK = 20;
	
	//****************************** Variable ********************
	
	/**
	 * Display's FPS of the panel
	 */
	private final int FPS = 35;
	
	/**
	 * List of all various chunk
	 */
	private List<Chunk> lVChunk = new LinkedList<Chunk>();
	
	/**
	 * Menu Listener for passing events
	 */
	private MenuListener mListener;
	
	//*************************** Constructors *******************
	
	/**
	 * Constructor of the class.
	 * Draw the menu Panel
	 */
	public MenuPanel(MenuListener mListener)
	{
		this.mListener = mListener;
		this.setBackground(new Color(100, 100, 100));
		this.setSize(XPANEL, YPANEL);
		
		JLabel lbMenu = new JLabel("Menu");
		this.add(lbMenu);
		
		// List each type of chunk
		try
		{
			this.lVChunk.add(new Chunk(new BoundingBox(
					new Coordinates((XPANEL/2) - (XCHUNK/2), 20), 
					new Coordinates(XCHUNK, YCHUNK)), 
					FieldType.GRASS));
			this.lVChunk.add(new Chunk(new BoundingBox(
					new Coordinates((XPANEL/2) - (XCHUNK/2), 50), 
					new Coordinates(XCHUNK, YCHUNK)), 
					FieldType.ROCK));
			this.lVChunk.add(new Chunk(new BoundingBox(
					new Coordinates((XPANEL/2) - (XCHUNK/2), 80), 
					new Coordinates(XCHUNK, YCHUNK)), 
					FieldType.WATER));
		}
		catch(NegativeSizeException e)
		{
			System.out.println(e);
		}
		
		this.addMouseListener(this);
	}
	
	//************************ JPanel Override ****************
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		for (Iterator<Chunk> it = lVChunk.iterator(); it.hasNext();)
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

	//******************* Runnable **********************
	
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(1000);
			this.repaint();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//********************* MouseListener ********************
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		for (Iterator<Chunk> it = this.lVChunk.iterator(); it.hasNext() && true;)
		{
			Chunk c = it.next();
			
			if(c.pointIn(new Coordinates(e.getX(), e.getY())))
			{
				this.mListener.addChunk(c.clone());
			}
		}
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}
}
