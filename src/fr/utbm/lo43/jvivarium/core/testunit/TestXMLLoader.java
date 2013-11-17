/**
 * 
 */
package fr.utbm.lo43.jvivarium.core.testunit;

import java.util.Iterator;
import java.util.List;

import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.XMLLoader;

/**
 * Class for testing the XML parser.
 * 
 * @author Alexandre Guyon
 */
public class TestXMLLoader
{

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		XMLLoader xml = new XMLLoader();
		xml.startParse();
		List<Chunk> l = xml.getChunks();
		
		for (Iterator<Chunk> it = l.iterator(); it.hasNext();)
		{
			Chunk c = (Chunk) it.next();
			System.out.println(c.toString());
		}
	}

}
