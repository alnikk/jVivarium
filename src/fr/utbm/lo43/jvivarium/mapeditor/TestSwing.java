package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.XMLLoader;

public class TestSwing extends JFrame
{
	private static List<Chunk> l;
	
	public static void main(String[] args)
	{
		XMLLoader xml = new XMLLoader();
		xml.startParse();
		l = xml.getChunks();
		
		TestSwing t = new TestSwing();
		t.setSize(400, 400);
		t.setVisible(true);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		for (Iterator it = l.iterator(); it.hasNext();)
		{
			Chunk c = (Chunk) it.next();
			g.drawRect(c.getArea().getPosition().getX(), c.getArea().getPosition().getY(), c.getArea().getSize().getX(), c.getArea().getSize().getY());
			System.out.println(c.toString());
		}
	}
}
