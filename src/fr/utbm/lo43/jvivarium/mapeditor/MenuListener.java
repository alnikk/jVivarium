/**
 * 
 */
package fr.utbm.lo43.jvivarium.mapeditor;

import fr.utbm.lo43.jvivarium.core.Chunk;

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
}
