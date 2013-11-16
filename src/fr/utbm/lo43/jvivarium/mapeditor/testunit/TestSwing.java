package fr.utbm.lo43.jvivarium.mapeditor.testunit;

import java.util.List;

import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.XMLLoader;
import fr.utbm.lo43.jvivarium.mapeditor.SwingController;
import fr.utbm.lo43.jvivarium.mapeditor.SwingDisplay;

public class TestSwing
{
	
	public static void main(String[] args)
	{
		XMLLoader xml = new XMLLoader();
		Thread display;
		SwingDisplay s;
		List<Chunk> l;
		
		xml.startParse();
		l = xml.getChunks();
		
		SwingController c = new SwingController(l);
		s = new SwingDisplay(l,c);
		s.setVisible(true);
		display = new Thread(s);
		display.start();
	}
}
