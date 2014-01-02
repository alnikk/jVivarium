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
	public synchronized void add(Object o)
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
	public synchronized void remove(Object o)
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
	public synchronized void setChunks(List<Chunk> l)
	{
		lChunk = l;
	}
	
	/**
	 * Get the list of chunks
	 * @return The list of Chunks
	 */
	public List<Chunk> getChunks()
	{
		return lChunk;
	}
	
	/**
	 * Set the list of entity
	 * @param l The list of entity
	 */
	public synchronized void setEntitys(List<Entity> l)
	{
		lEntity = l;
	}
	
	/**
	 * Get the list of entity
	 * @return The list of entity
	 */
	public List<Entity> getEntitys()
	{
		return lEntity;
	}
	
	/**
	 * Set the list of objects
	 * @param l The list of objects
	 */
	public synchronized void setObjects(List<Obj> l)
	{
		lObj = l;
	}
	
	/**
	 * Get the list of objects
	 * @return The list of objects
	 */
	public List<Obj> getObjects()
	{
		return lObj;
	}
	
	/**
	 * Return the max coordinates of the map
	 * @return (Coordinates) The max coordinates of the map
	 */
	public synchronized Coordinates getMaxMap()
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
	 * Return the list of all entity of a type
	 * @param o The class to search in entity list
	 * @return The list of entity of a type
	 */
	public List<Entity> searchEntity(Class<?> o)
	{
		List<Entity> l = new LinkedList<Entity>();
		Entity e;	
		
		for(Iterator<Entity> it = this.lEntity.iterator(); it.hasNext();)
		{
			e = it.next();
			
			if (o.isInstance(e))
				l.add(e);
		}
		
		return l;
	}
	
	/**
	 * Return the chunk at the indicate coordinates
	 * @param c The coordinates to get the chunk
	 * @return The chunk
	 */
	public synchronized Chunk getChunkAt(Coordinates p)
	{
		Chunk c;
		
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
	public synchronized Entity getEntityAt(Coordinates p)
	{
		Entity e;
		
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
	public synchronized Obj getObjAt(Coordinates p)
	{
		Obj o;
		
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
	
	// TODO Add generics
	/**
	 * Return a list of chunks in the radius distance
	 * @param e Around which entity look up 
	 * @param radius The distance to look
	 * @return A list of chunks in the radius
	 */
	public synchronized List<Chunk> scan(Entity e, int radius)
	{
		List<Chunk> res = new LinkedList<Chunk>();
		Chunk c;
		
		double xe = e.getArea().getPosition().getX() + (e.getArea().getSize().getX() / 2);
		double ye = e.getArea().getPosition().getY() + (e.getArea().getSize().getY() / 2);
		double xc1,yc1, xc2, yc2, module1, module2, module3, module4; 
		
		for(Iterator<Chunk> it = lChunk.iterator(); it.hasNext();)
		{
			c = it.next();
			
			xc1 = c.getArea().getPosition().getX() - xe;
			yc1 = c.getArea().getPosition().getY() - ye;
			xc2 = (c.getArea().getPosition().getX() + c.getArea().getSize().getX()) - xe;
			yc2 = (c.getArea().getPosition().getY() + c.getArea().getSize().getX()) - ye;
			
			module1 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc1, 2));
			module2 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc2, 2));
			module3 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc1, 2));
			module4 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc2, 2));
			
			if(module1 < radius
					|| module2 < radius
					|| module3 < radius
					|| module4 < radius)
				res.add(c);
		}
		
		return res;
	}
	public synchronized List<Entity> scanEntity(Entity e, int radius)
	{
		List<Entity> res = new LinkedList<Entity>();
		Entity c;
		
		double xe = e.getArea().getPosition().getX() + (e.getArea().getSize().getX() / 2);
		double ye = e.getArea().getPosition().getY() + (e.getArea().getSize().getY() / 2);
		double xc1,yc1, xc2, yc2, module1, module2, module3, module4; 
		
		for(Iterator<Entity> it = lEntity.iterator(); it.hasNext();)
		{
			c = it.next();
			
			xc1 = c.getArea().getPosition().getX() - xe;
			yc1 = c.getArea().getPosition().getY() - ye;
			xc2 = (c.getArea().getPosition().getX() + c.getArea().getSize().getX()) - xe;
			yc2 = (c.getArea().getPosition().getY() + c.getArea().getSize().getX()) - ye;
			
			module1 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc1, 2));
			module2 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc2, 2));
			module3 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc1, 2));
			module4 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc2, 2));
			
			if(module1 < radius
					|| module2 < radius
					|| module3 < radius
					|| module4 < radius)
				res.add(c);
		}
		
		return res;
	}
	public synchronized List<Obj> scanObject(Entity e, int radius)
	{
		List<Obj> res = new LinkedList<Obj>();
		Obj c;
		
		double xe = e.getArea().getPosition().getX() + (e.getArea().getSize().getX() / 2);
		double ye = e.getArea().getPosition().getY() + (e.getArea().getSize().getY() / 2);
		double xc1,yc1, xc2, yc2, module1, module2, module3, module4; 
		
		for(Iterator<Obj> it = lObj.iterator(); it.hasNext();)
		{
			c = it.next();
			
			xc1 = c.getArea().getPosition().getX() - xe;
			yc1 = c.getArea().getPosition().getY() - ye;
			xc2 = (c.getArea().getPosition().getX() + c.getArea().getSize().getX()) - xe;
			yc2 = (c.getArea().getPosition().getY() + c.getArea().getSize().getX()) - ye;
			
			module1 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc1, 2));
			module2 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc2, 2));
			module3 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc1, 2));
			module4 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc2, 2));
			
			if(module1 < radius
					|| module2 < radius
					|| module3 < radius
					|| module4 < radius)
				res.add(c);
		}
		
		return res;
	}
	
	/**
	 * Search the nearest object of an element
	 * @param e The element
	 * @param t The object's type to search
	 * @return The nearest object
	 */
	public synchronized Obj searchNearest(Element e, ObjectType t)
	{
		Obj c;
		Obj res = null;
		
		double distance=Math.max(this.getMaxMap().getX(), this.getMaxMap().getY());
		double xe = e.getArea().getPosition().getX() + (e.getArea().getSize().getX() / 2);
		double ye = e.getArea().getPosition().getY() + (e.getArea().getSize().getY() / 2);
		
		double xc1,yc1, xc2, yc2, module1, module2, module3, module4, mod; 
		
		for(Iterator<Obj> it = lObj.iterator(); it.hasNext();)
		{
			c = it.next();
			
			if(c.getType() == t)
			{
				xc1 = c.getArea().getPosition().getX() - xe;
				yc1 = c.getArea().getPosition().getY() - ye;
				xc2 = (c.getArea().getPosition().getX() + c.getArea().getSize().getX()) - xe;
				yc2 = (c.getArea().getPosition().getY() + c.getArea().getSize().getX()) - ye;
				
				module1 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc1, 2));
				module2 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc2, 2));
				module3 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc1, 2));
				module4 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc2, 2));
				
					
				if(res != null)
				{
					mod = Math.min(Math.min(module1, module2), Math.min(module3, module4));
					if(distance > mod)
					{
						distance = mod;
						res = c;
						break;
					}
				}
				else
				{
					res = c;
					distance = Math.min(Math.min(module1, module2), Math.min(module3, module4));
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Search the nearest entity from the element
	 * @param e The element
	 * @param t Class to search int entity List
	 * @return The nearest entity of class t
	 */
	public synchronized Entity searchNearest(Element e, Class<?> t)
	{
		Entity c;
		Entity res = null;
		
		double distance=Math.max(this.getMaxMap().getX(), this.getMaxMap().getY());
		double xe = e.getArea().getPosition().getX() + (e.getArea().getSize().getX() / 2);
		double ye = e.getArea().getPosition().getY() + (e.getArea().getSize().getY() / 2);
		
		double xc1,yc1, xc2, yc2, module1, module2, module3, module4, mod; 
		
		for(Iterator<Entity> it = lEntity.iterator(); it.hasNext();)
		{
			c = it.next();
			
			if(t.isInstance(c))
			{
				xc1 = c.getArea().getPosition().getX() - xe;
				yc1 = c.getArea().getPosition().getY() - ye;
				xc2 = (c.getArea().getPosition().getX() + c.getArea().getSize().getX()) - xe;
				yc2 = (c.getArea().getPosition().getY() + c.getArea().getSize().getX()) - ye;
				
				module1 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc1, 2));
				module2 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc2, 2));
				module3 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc1, 2));
				module4 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc2, 2));
				
					
				if(res != null)
				{
					mod = Math.min(Math.min(module1, module2), Math.min(module3, module4));
					if(distance > mod)
					{
						distance = mod;
						res = c;
						break;
					}
				}
				else
				{
					res = c;
					distance = Math.min(Math.min(module1, module2), Math.min(module3, module4));
				}
			}
		}
		
		return res;
	}
	
	public synchronized Chunk searchNearest(Element e, FieldType t)
	{
		Chunk c;
		Chunk res = null;
		
		double distance=Math.max(this.getMaxMap().getX(), this.getMaxMap().getY());
		double xe = e.getArea().getPosition().getX() + (e.getArea().getSize().getX() / 2);
		double ye = e.getArea().getPosition().getY() + (e.getArea().getSize().getY() / 2);
		
		double xc1,yc1, xc2, yc2, module1, module2, module3, module4, mod; 
		
		for(Iterator<Chunk> it = lChunk.iterator(); it.hasNext();)
		{
			c = it.next();
			
			if(c.getFieldType() == t)
			{
				xc1 = c.getArea().getPosition().getX() - xe;
				yc1 = c.getArea().getPosition().getY() - ye;
				xc2 = (c.getArea().getPosition().getX() + c.getArea().getSize().getX()) - xe;
				yc2 = (c.getArea().getPosition().getY() + c.getArea().getSize().getX()) - ye;
				
				module1 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc1, 2));
				module2 = Math.sqrt(Math.pow(xc1, 2)+Math.pow(yc2, 2));
				module3 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc1, 2));
				module4 = Math.sqrt(Math.pow(xc2, 2)+Math.pow(yc2, 2));
				
					
				if(res != null)
				{
					mod = Math.min(Math.min(module1, module2), Math.min(module3, module4));
					if(distance > mod)
					{
						distance = mod;
						res = c;
						break;
					}
				}
				else
				{
					res = c;
					distance = Math.min(Math.min(module1, module2), Math.min(module3, module4));
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Return the nearest element (between two) of an other element
	 * @param comp The element (Base, middle)
	 * @param e1 The first element to compare
	 * @param e2 The second element to compare
	 * @return The nearest element (null when equals)
	 */
	public static Element compNear(Element comp, Element e1, Element e2)
	{
		Element res = null;
		double xe = comp.getArea().getPosition().getX() + (comp.getArea().getSize().getX() / 2);
		double ye = comp.getArea().getPosition().getY() + (comp.getArea().getSize().getY() / 2);
		double x1,y1, x2, y2, module1, module2, module3, module4, distance1, distance2; 
			
		
		// Calc e1
		x1 = e1.getArea().getPosition().getX() - xe;
		y1 = e1.getArea().getPosition().getY() - ye;
		x2 = (e1.getArea().getPosition().getX() + e1.getArea().getSize().getX()) - xe;
		y2 = (e1.getArea().getPosition().getY() + e1.getArea().getSize().getX()) - ye;
		
		module1 = Math.sqrt(Math.pow(x1, 2)+Math.pow(y1, 2));
		module2 = Math.sqrt(Math.pow(x1, 2)+Math.pow(y2, 2));
		module3 = Math.sqrt(Math.pow(x2, 2)+Math.pow(y1, 2));
		module4 = Math.sqrt(Math.pow(x2, 2)+Math.pow(y2, 2));

		distance1 = Math.min(Math.min(module1, module2), Math.min(module3, module4));
		
		// Calc e2
		
		x1 = e2.getArea().getPosition().getX() - xe;
		y1 = e2.getArea().getPosition().getY() - ye;
		x2 = (e2.getArea().getPosition().getX() + e2.getArea().getSize().getX()) - xe;
		y2 = (e2.getArea().getPosition().getY() + e2.getArea().getSize().getX()) - ye;
		
		module1 = Math.sqrt(Math.pow(x1, 2)+Math.pow(y1, 2));
		module2 = Math.sqrt(Math.pow(x1, 2)+Math.pow(y2, 2));
		module3 = Math.sqrt(Math.pow(x2, 2)+Math.pow(y1, 2));
		module4 = Math.sqrt(Math.pow(x2, 2)+Math.pow(y2, 2));
		
		distance2 = Math.min(Math.min(module1, module2), Math.min(module3, module4));
		
		
		// Comp
		if(distance1 < distance2)
			res = e1;
		if(distance1 > distance2)
			res = e2;
			
		return res;
	}
	
	// TODO Class for calculates module of elements
}
