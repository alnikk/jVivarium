/**
 * 
 */
package fr.utbm.lo43.jvivarium.testunit;

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
import fr.utbm.lo43.jvivarium.core.XMLLoader;
import fr.utbm.lo43.jvivarium.mapeditor.MapEditor;
import fr.utbm.lo43.jvivarium.view.MainFrame;

/**
 * Main class of the package
 * @author Alexandre Guyon
 */
public class JVivarium
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		XMLLoader xml = new XMLLoader();
		xml.startParse();
		
		MainFrame m = new MainFrame();
		
		
		// Create entity
		Map map = Map.getMap();
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
		
		List<Entity> l = map.getEntitys();
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
