/**
 * 
 */
package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.FieldType;

/**
 * Display the map in a JFrame
 * 
 * @author Alexandre Guyon
 */
public class SwingDisplay extends JFrame
{
	/**
	 * List of chunks to display
	 */
	private List<Chunk> lChunk;
	
	/**
	 * The x size of the window in pixel
	 */
	private int x = 0;
	
	/**
	 * The y size of the window in pixel
	 */
	private int y = 0;
	
	public SwingDisplay(List<Chunk> list)
	{
		this.lChunk = list;
		
		for (Iterator<Chunk> it = lChunk.iterator(); it.hasNext();)
		{
			Chunk c = it.next();
			
			if(c.getArea().getPosition().getX() + c.getArea().getSize().getX() > this.x)
				x = c.getArea().getPosition().getX() + c.getArea().getSize().getX();
			if(c.getArea().getPosition().getY() + c.getArea().getSize().getY() > this.x)
				y = c.getArea().getPosition().getY() + c.getArea().getSize().getY();
		}
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(this.x, this.y);
		this.setResizable(true);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		for (Iterator<Chunk> it = lChunk.iterator(); it.hasNext();)
		{
			Chunk c = it.next();
			
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
}
