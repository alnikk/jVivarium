package fr.utbm.lo43.jvivarium.core;

import java.util.Iterator;
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
	private static List<Chunk> lChunk = new LinkedList<Chunk>();
	
	/**
	 * List of all entity in the map
	 */
	private static List<Entity> lEntity = new LinkedList<Entity>();
	
	/**
	 * List of all objects in the map
	 */
	private static List<Obj> lObj = new LinkedList<Obj>();
	
	/**
	 * Add chunk,entity, or object in the map
	 * @param o (Chunk,Entity,Obj) The Object to add in the map
	 */
	public static void add(Object o)
	{
		if(o instanceof Chunk)
			lChunk.add((Chunk) o);
		if(o instanceof Entity)
			lEntity.add((Entity) o);
		if(o instanceof Obj)
			lObj.add((Obj) o);
		// TODO Exception
	}
	
	/**
	 * Remove chunk,entity, or object of the map
	 * @param o (Chunk,Entity,Obj) The Object to remove of the map
	 */
	public static void remove(Object o)
	{
		if(o instanceof Chunk)
			lChunk.remove((Chunk) o);
		if(o instanceof Entity)
			lEntity.remove((Entity) o);
		if(o instanceof Obj)
			lObj.remove((Obj) o);
		// TODO Exception
	}
	
	/**
	 * Set the list of chunks
	 * @param l The list of chunks
	 */
	public static void setChunks(List<Chunk> l)
	{
		lChunk = l;
	}
	
	/**
	 * Get the list of chunks
	 * @return The list of Chunks
	 */
	public static List<Chunk> getChunks()
	{
		return lChunk;
	}
	
	/**
	 * Set the list of entity
	 * @param l The list of entity
	 */
	public static void setEntitys(List<Entity> l)
	{
		lEntity = l;
	}
	
	/**
	 * Get the list of entity
	 * @return The list of entity
	 */
	public static List<Entity> getEntitys()
	{
		return lEntity;
	}
	
	/**
	 * Set the list of objects
	 * @param l The list of objects
	 */
	public static void setObjects(List<Obj> l)
	{
		lObj = l;
	}
	
	/**
	 * Get the list of objects
	 * @return The list of objects
	 */
	public static List<Obj> getObjects()
	{
		return lObj;
	}
	
	/**
	 * Return the max coordinates of the map
	 * @return (Coordinates) The max coordinates of the map
	 */
	public static Coordinates getMaxMap()
	{
		Chunk c;
		int x=0,y=0;
		
		for(Iterator<Chunk> it = lChunk.iterator(); it.hasNext();)
		{
			c = it.next();
			
			if((c.getArea().getPosition().getX() + c.getArea().getSize().getX()) > x)
				x = c.getArea().getPosition().getX() + c.getArea().getSize().getX();
			if((c.getArea().getPosition().getY() + c.getArea().getSize().getY()) > y)
				y = c.getArea().getPosition().getY() + c.getArea().getSize().getY();
		}
		
		return new Coordinates(x, y);
	}
	
	/**
	 * Return the chunk at the indicate coordinates
	 * @param c The coordinates to get the chunk
	 * @return The chunk
	 */
	public static Chunk getChunkAt(Coordinates p)
	{
		Chunk c;
		int x=0,y=0;
		
		for(Iterator<Chunk> it = lChunk.iterator(); it.hasNext();)
		{
			c = it.next();
			
			if((c.getArea().getPosition().getX() + c.getArea().getSize().getX()) > p.getX()
					&& c.getArea().getPosition().getX() < p.getX()
					&& (c.getArea().getPosition().getY() + c.getArea().getSize().getY()) > p.getY()
					&& c.getArea().getPosition().getY() < p.getY())
				return c;
		}
		return null;
	}
	
	public static Entity getEntityAt(Coordinates p)
	{
		Entity e;
		int x=0,y=0;
		
		for(Iterator<Entity> it = lEntity.iterator(); it.hasNext();)
		{
			e = it.next();
			
			if((e.getArea().getPosition().getX() + e.getArea().getSize().getX()) > p.getX()
					&& e.getArea().getPosition().getX() < p.getX()
					&& (e.getArea().getPosition().getY() + e.getArea().getSize().getY()) > p.getY()
					&& e.getArea().getPosition().getY() < p.getY())
				return e;
		}
		return null;
	}
	
	public static Obj getObjAt(Coordinates p)
	{
		Obj o;
		int x=0,y=0;
		
		for(Iterator<Obj> it = lObj.iterator(); it.hasNext();)
		{
			o = it.next();
			
			if((o.getArea().getPosition().getX() + o.getArea().getSize().getX()) > p.getX()
					&& o.getArea().getPosition().getX() < p.getX()
					&& (o.getArea().getPosition().getY() + o.getArea().getSize().getY()) > p.getY()
					&& o.getArea().getPosition().getY() < p.getY())
				return o;
		}
		return null;
	}
}
