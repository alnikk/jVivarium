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
import fr.utbm.lo43.jvivarium.core.ObjectType;
import fr.utbm.lo43.jvivarium.core.Peach;
import fr.utbm.lo43.jvivarium.core.Obj;
import fr.utbm.lo43.jvivarium.view.MainFrame;

/**
 * Main class of the project.
 * Used to control entity and verify them.
 * Run first the main frame, and begin to parse XML.
 * Next it run the model (core)
 * @author Alexandre Guyon
 */
public final class JVivarium
{
	//**************************** Constants *******************
	/**
	 * Time between each loop of the controller
	 */
	private final static int TIME_SLEEP = 100;
	
	//*************************** Attributes *******************
	/**
	 * Main frame of the game
	 */
	private MainFrame mFrame;
	
	/**
	 * Instance of the map
	 */
	private Map map = Map.getMap();
	
	//************************** Constructor ****************
	public JVivarium()
	{
		// XML Thread
		XMLLoader xml = new XMLLoader();
		Thread xmlT = new Thread(xml);
		xmlT.start();
		
		// Run view
		this.mFrame = new MainFrame();
		this.mFrame.loading();
		
		try
		{
			// Wait for end XML parsing to start the view
			xmlT.join();
			this.mFrame.start();
		}
		catch (InterruptedException e1)
		{
			e1.printStackTrace();
		}
		
		// TODO Entity will be loaded with the map
		// Create entity
		try
		{
			map.add(new Obj(new BoundingBox(new Coordinates(20, 300), new Coordinates(20, 20)), ObjectType.MUSHROOM));
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
		// Main of the game
		while(true)
		{
			List<Entity> l = Map.getMap().getEntitys();
			boolean won = true;
			boolean lost = true; 
			for(Iterator<Entity> it = l.iterator(); it.hasNext();)
			{
				Entity e = it.next();
				if (e instanceof Bowser)
					won=false;
				else
					if(e instanceof Peach || e instanceof Mario)
						lost=false;
			
			}
			if (won){	
				MainFrame.win.setVisible(true);
			}
			else
				if(lost){
					MainFrame.loose.setVisible(true);

				}
			
			// Call the life method on each entity
			this.callEntity();
			
			// Wait for slow the model
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
	 * Call life method on each entity.
	 * It will make entity act.
	 */
	private void callEntity()
	{
		/* Get an array of entity for take a snapshot of the model
		 * and not being distract by adding or removing entity of
		 * this list.
		 */
		Object[] l = this.map.getEntitys().toArray();
		int i;
		
		for(i=0;i < l.length ; i++)
		{
			((Entity)l[i]).life();
		}
	}
}
