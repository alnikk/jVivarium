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
	 * Time between each loop of the controller
	 */
	private final static int TIME_SLEEP = 100;
	
	/**
	 * Main frame of the game
	 */
	private MainFrame mFrame;
	
	/**
	 * Instance of the map
	 */
	private Map map = Map.getMap();
	
	
	public JVivarium()
	{
		XMLLoader xml = new XMLLoader();
		Thread xmlT = new Thread(xml);
		xmlT.start();
		
		// View
		this.mFrame = new MainFrame();
		this.mFrame.loading();
		
		try
		{
			xmlT.join();
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		
		this.mFrame.start();
		
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
		while(true)
		{
			this.callEntity();
			
			try
			{
				Thread.sleep(TIME_SLEEP);
			}
			catch (InterruptedException e1)
			{
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * Call life on each entity
	 */
	private void callEntity()
	{
		List<Entity> l = this.map.getEntitys();
		Entity e;
		
		for(Iterator<Entity> it = l.iterator(); it.hasNext();)
		{
			e = it.next();
			e.life();
		}
	}
}
