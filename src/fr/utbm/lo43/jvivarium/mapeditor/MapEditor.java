/**
 * 
 */
package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

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
	 * Editor's panel
	 */
	private EditorPanel editor;
	
	/**
	 * Menu's Panel
	 */
	private MenuPanel menu;
	
			//************ [List Entity] ************/
	
	/**
	 * List of the map's chunk
	 */
	private List<Chunk> lChunk;
	
	
	//*************************** Construcor ***********************/
	
	public MapEditor()
	{
		super();
		
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		
		//**** XML ****
		xml.startParse();
		this.lChunk = xml.getChunks();
		
		//**** View ****
		// Menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menuFile = new JMenu("File");
		JMenuItem test = new JMenuItem("test");
		
		menuFile.add(test);
		menuBar.add(menuFile);
		this.setJMenuBar(menuBar);
		
		// Editor
		this.editor = new EditorPanel(this.lChunk);
		this.add(this.editor);
		this.editor.setBounds(50, 0, 400, 300);
		this.setFocusable(true);
		this.addKeyListener(editor);
		
		// Menu
		this.menu = new MenuPanel(this.editor);
		this.add(menu);
		this.menu.setBounds(0, 0, 50, 300);
		
		// Resize JFrame
		int y= 0;
		
		if(this.menu.getSize().height > this.editor.getSize().height)
			y = this.menu.getSize().height;
		else
			y = this.editor.getSize().height;
		
		this.setSize(this.menu.getSize().width + this.editor.getSize().width, y);
		
		// Add a listener to resize window;
		this.addComponentListener(new ComponentListener()
		{			
			@Override
			public void componentShown(ComponentEvent e)
			{
				
			}
			
			@Override
			public void componentResized(ComponentEvent e)
			{
				Dimension d = e.getComponent().getSize();
				int xEditor = 0 , yEditor=0;
				
				// Width
				if(d.width <= menu.getSize().width)
				{
					xEditor = 0;
				}
				else
				{
					xEditor = d.width - menu.getSize().width;
				}
				
				// Height
				yEditor = d.height;
				
				// Set
				editor.setSize(xEditor, yEditor);
			}
			
			@Override
			public void componentMoved(ComponentEvent e)
			{
				
			}
			
			@Override
			public void componentHidden(ComponentEvent e)
			{
				
			}
		});
	}
	//***************************** Methods *************************/
	
	public void start()
	{
		// Menu
		Thread me = new Thread(this.menu);
		me.start();
		
		// Editor
		Thread ed = new Thread(this.editor);
		ed.start();
		
		this.setVisible(true);
	}
}
