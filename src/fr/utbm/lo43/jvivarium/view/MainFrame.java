package fr.utbm.lo43.jvivarium.view;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.w3c.dom.NodeList;

import fr.utbm.lo43.jvivarium.core.BoundingBox;
import fr.utbm.lo43.jvivarium.core.Bowser;
import fr.utbm.lo43.jvivarium.core.Coordinates;
import fr.utbm.lo43.jvivarium.core.Entity;
import fr.utbm.lo43.jvivarium.core.Map;
import fr.utbm.lo43.jvivarium.core.Mario;
import fr.utbm.lo43.jvivarium.core.NegativeSizeException;
import fr.utbm.lo43.jvivarium.core.Peach;
import fr.utbm.lo43.jvivarium.mapeditor.EditorPanel;
import fr.utbm.lo43.jvivarium.mapeditor.MainEditorPanel;
import fr.utbm.lo43.jvivarium.mapeditor.MainMenuPanel;
import fr.utbm.lo43.jvivarium.mapeditor.MenuListener;
import fr.utbm.lo43.jvivarium.mapeditor.MenuPanel;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Game Panel
	 */
	private GamePanel gameP = new GamePanel();
	/**
	 * The loading JLabel
	 */
	private JLabel load = new JLabel("Loading");
	public static JLabel win = new JLabel("Victory, Mario saved the princess !");
	
	public static JLabel loose = new JLabel("Defeat, Bowser kidnaped Peach !");

	
	/**
	 * Initialize the main window
	 * and add the panel of the game
	 * to it
	 */
	public MainFrame()
	{
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		this.setVisible(true);
	}
	
	/**
	 * Create panels thread
	 * for the view and activate
	 * the window
	 */
	public void start() 
	{
		
		
			// Menu
		MainMenuPanel menu;
		menu = new MainMenuPanel(new MainEditorPanel());
		
		menu.setBounds(0, 0, this.gameP.getWidth(),this.gameP.getHeight());
		
			// Resize JFrame
		int y;
		
		if(menu.getSize().height > this.gameP.getSize().height)
			y = menu.getSize().height;
		else
			y = this.gameP.getSize().height;
		
		this.setSize(menu.getSize().width + this.gameP.getSize().width, y);
		this.add(menu);
		this.remove(load);
		this.add(this.gameP);
		this.add(win);
		win.setVisible(false);
		this.add(loose);
		loose.setVisible(false);
		this.setSize(
				new Dimension(((int) this.gameP.getSize().getWidth()),
						(int)(this.gameP.getSize().getHeight()+menu.getHeight() +50 + this.getInsets().top)));
		
		Thread game = new Thread(this.gameP);
		game.start();
		
		//test if game is won or lost
		
		
	}
	
	/**
	 * Loading view
	 */
	public void loading()
	{		
		this.setSize(100,70);
		this.add(load);
	}

}
