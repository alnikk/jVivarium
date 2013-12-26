package fr.utbm.lo43.jvivarium.core;

import java.util.LinkedList;
import java.util.List;

/**
 * Singleton Map.
 * Centralize all informations about the game. 
 * @author Alexandre Guyon
 */
public class Map
{
	/**
	 * The singleton
	 */
	private static Map singleton = null;
	
	/**
	 * The private constructors
	 */
	private Map(){}
	
	/**
	 * Static method for return the singleton.
	 * @return The singleton map
	 */
	public static synchronized Map getMap() 
	{ 
		if(singleton == null)
			singleton = new Map();
		return singleton;
	}
	
	/**
	 * List of all chunk in the map
	 */
	private List<Chunk> lChunk = new LinkedList<Chunk>();
	
	/**
	 * List of all entity in the map
	 */
	private List<Entity> lEntity = new LinkedList<Entity>();
	
	/**
	 * List of all objects in the map
	 */
	private List<Obj> lObj = new LinkedList<Obj>();
	
	/**
	 * Add chunk,entity, or object in the map
	 * @param o (Chunk,Entity,Obj) The Object to add in the map
	 */
	public void add(Object o)
	{
		if(o instanceof Chunk)
			this.lChunk.add((Chunk) o);
		if(o instanceof Entity)
			this.lEntity.add((Entity) o);
		if(o instanceof Obj)
			this.lObj.add((Obj) o);
		// TODO Exception
	}
	
	/**
	 * Remove chunk,entity, or object of the map
	 * @param o (Chunk,Entity,Obj) The Object to remove of the map
	 */
	public void remove(Object o)
	{
		if(o instanceof Chunk)
			this.lChunk.remove((Chunk) o);
		if(o instanceof Entity)
			this.lEntity.remove((Entity) o);
		if(o instanceof Obj)
			this.lObj.remove((Obj) o);
		// TODO Exception
	}
	
	/**
	 * Set the list of chunks
	 * @param l The list of chunks
	 */
	public void setChunks(List<Chunk> l)
	{
		this.lChunk = l;
	}
	
	/**
	 * Get the list of chunks
	 * @return The list of Chunks
	 */
	public List<Chunk> getChunks()
	{
		return this.lChunk;
	}
	
	/**
	 * Set the list of entity
	 * @param l The list of entity
	 */
	public void setEntitys(List<Entity> l)
	{
		this.lEntity = l;
	}
	
	/**
	 * Get the list of entity
	 * @return The list of entity
	 */
	public List<Entity> getEntitys()
	{
		return this.lEntity;
	}
	
	/**
	 * Set the list of objects
	 * @param l The list of objects
	 */
	public void setObjects(List<Obj> l)
	{
		this.lObj = l;
	}
	
	/**
	 * Get the list of objects
	 * @return The list of objects
	 */
	public List<Obj> getObjects()
	{
		return this.lObj;
	}
}
