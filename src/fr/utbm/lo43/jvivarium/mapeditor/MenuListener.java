/**
 * 
 */
package fr.utbm.lo43.jvivarium.mapeditor;

import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.Entity;
import fr.utbm.lo43.jvivarium.core.Obj;

/**
 * Listener for send event
 * from menu to editor
 * @author Alexandre Guyon
 */
public interface MenuListener
{
	/**
	 * Add a chunk to the editor view
	 * @param c The chunk to add
	 */
	public void addChunk(Chunk c);
	
	/**
	 * Add an entity to the editor view
	 * @param e The entity to add
	 */
	public void addEntity(Entity e);
	
	/**
	 * Add an object to the editor view
	 * @param o The object to add
	 */
	public void addObject(Obj o);
	
	/**
	 * Save the map
	 */
	public void saveMap();
}
