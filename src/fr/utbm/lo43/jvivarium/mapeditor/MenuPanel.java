package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
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
	 * List of all various chunk (in the menu)
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
		
		JLabel lbMenu = new JLabel("Chunk");
		this.add(lbMenu);
		
		// List each type of chunk
		JPanel t = new JPanel();
		t.setPreferredSize(new Dimension(XPANEL, 5*30));
		t.setBackground(new Color(100, 100, 100));
		this.add(t);
		try
		{
			this.lVChunk.add(new Chunk(new BoundingBox(
					new Coordinates((XPANEL/2) - (XCHUNK/2), (YCHUNK + 10) * 1), 
					new Coordinates(XCHUNK, YCHUNK)), 
					FieldType.FIRE));
			this.lVChunk.add(new Chunk(new BoundingBox(
					new Coordinates((XPANEL/2) - (XCHUNK/2), (YCHUNK + 10) * 2), 
					new Coordinates(XCHUNK, YCHUNK)), 
					FieldType.CASTLE));
			this.lVChunk.add(new Chunk(new BoundingBox(
					new Coordinates((XPANEL/2) - (XCHUNK/2), (YCHUNK + 10) * 3), 
					new Coordinates(XCHUNK, YCHUNK)), 
					FieldType.BRICK));
			this.lVChunk.add(new Chunk(new BoundingBox(
					new Coordinates((XPANEL/2) - (XCHUNK/2), (YCHUNK + 10) * 4), 
					new Coordinates(XCHUNK, YCHUNK)), 
					FieldType.PIPE));
			this.lVChunk.add(new Chunk(new BoundingBox(
					new Coordinates((XPANEL/2) - (XCHUNK/2), (YCHUNK + 10) * 5), 
					new Coordinates(XCHUNK, YCHUNK)), 
					FieldType.PINK_BRICK));
		}
		catch(NegativeSizeException e)
		{
			System.out.println(e);
		}
		
		// Save button
		JButton bt_save = new JButton("Save");
		bt_save.addActionListener(new ActionListener()
		{
			@Override // On click
			public void actionPerformed(ActionEvent arg0)
			{
				MenuPanel.this.mListener.saveMap();				
			}
		});
		this.add(bt_save);
		
		// Handle mouse
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
			c.paint(g);
		}
	}

	//******************* Runnable **********************
	
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(1000/FPS);
			this.repaint();
		}
		catch (InterruptedException e)
		{
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
}
