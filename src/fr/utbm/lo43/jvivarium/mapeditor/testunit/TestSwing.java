package fr.utbm.lo43.jvivarium.mapeditor.testunit;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;

import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.XMLLoader;
import fr.utbm.lo43.jvivarium.mapeditor.SwingDisplay;

public class TestSwing extends JFrame
{
	
	public static void main(String[] args)
	{
		XMLLoader xml = new XMLLoader();
		SwingDisplay s;
		List<Chunk> l;
		
		xml.startParse();
		l = xml.getChunks();
		
		s = new SwingDisplay(l);
		s.setVisible(true);
	}
}
