package fr.utbm.lo43.jvivarium.controller;

import java.util.Iterator;
import java.util.List;

import fr.utbm.lo43.jvivarium.core.BoundingBox;
import fr.utbm.lo43.jvivarium.core.Bowser;
import fr.utbm.lo43.jvivarium.core.Coordinates;
import fr.utbm.lo43.jvivarium.core.Entity;
import fr.utbm.lo43.jvivarium.core.Map;
import fr.utbm.lo43.jvivarium.core.Mario;
import fr.utbm.lo43.jvivarium.core.NegativeSizeException;
import fr.utbm.lo43.jvivarium.core.Peach;
import fr.utbm.lo43.jvivarium.view.MainFrame;

/**
 * Main class of the project.
 * Used to controlle entity and verify them
 * @author Alexandre Guyon
 */
public final class JVivarium
{
	/**
	 * Main frame of the game
	 */
	MainFrame mFrame = new MainFrame();
	
	/**
	 * Instance of the map
	 */
	Map map = Map.getMap();
	
	
	public JVivarium()
	{
		XMLLoader xml = new XMLLoader();
		xml.startParse();
		
		// Create entity
		try
		{
			map.add(new Mario(new BoundingBox(new Coordinates(60, 200), new Coordinates(20, 20))));
			map.add(new Peach(new BoundingBox(new Coordinates(20, 300), new Coordinates(20, 20))));
			map.add(new Bowser(new BoundingBox(new Coordinates(40, 0), new Coordinates(20, 20))));
		}
		catch (NegativeSizeException e)
		{
			e.printStackTrace();
		}
	}
	
	public void start()
	{
		List<Entity> l = this.map.getEntitys();
		Entity e;
		
		while(true)
		{
			for(Iterator<Entity> it = l.iterator(); it.hasNext();)
			{
				e = it.next();
				e.life();
			}
			
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		}
	}
}
