/**
 * 
 */
package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JFrame;

import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.XMLLoader;

/**
 * Master class of the map editor.
 * @author Alexandre Guyon
 */
public class MapEditor extends JFrame
{
	//*************************** Variable **********************/
	/**
	 * XML parser for read/write the map file
	 */
	private XMLLoader xml = new XMLLoader();
	
			//************* [View] ******************/
	/**
	 * Layout of the JFrame
	 */
	private BorderLayout layout;
	
	/**
	 * Editor's panel
	 */
	private SwingDisplayEditor editor;
	
	/**
	 * Editor's controller
	 */
	private SwingControllerEditor mouseEditor;
	
			//************ [List Entity] ************/
	
	/**
	 * List of the map's chunk
	 */
	private List<Chunk> lChunk;
	
	
	//*************************** Construcor ***********************/
	
	public MapEditor()
	{
		super();
		
		// JFramethis.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(400, 400));
		
		// XML
		xml.startParse();
		this.lChunk = xml.getChunks();
		
		// Editor
		this.mouseEditor = new SwingControllerEditor(this.lChunk);
		this.editor = new SwingDisplayEditor(this.lChunk, this.mouseEditor);
		
		// Add to JFrame
		this.editor.setBounds(0, 0, 300, 300);
		this.add(this.editor);
	}
	
	//***************************** Methods *************************/
	
	public void start()
	{
		// Editor
		Thread ed = new Thread(this.editor);
		ed.start();
		
		this.setVisible(true);
	}
}
