package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.utbm.lo43.jvivarium.core.BoundingBox;
import fr.utbm.lo43.jvivarium.core.Bowser;
import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.Coordinates;
import fr.utbm.lo43.jvivarium.core.FieldType;
import fr.utbm.lo43.jvivarium.core.Mario;
import fr.utbm.lo43.jvivarium.core.NegativeSizeException;
import fr.utbm.lo43.jvivarium.core.Obj;
import fr.utbm.lo43.jvivarium.core.ObjectType;
import fr.utbm.lo43.jvivarium.core.Peach;

public class MainMenuPanel extends JPanel implements Runnable
{
	/**
	 * Serialize number
	 */
	private static final long serialVersionUID = 1L;
	
	//****************************** Constant ********************

	/**
	 * Display's FPS of the panel
	 */
	private static int FPS = 1;
	
	/**
	 * The X size of the Panel
	 */
	public static int XPANEL = 100;
	
	/**
	 * The Y size of the Panel
	 */
	public static int YPANEL = 400;
	
	/**
	 * X size of a button
	 */
	private final int XBUTTON = 93;
	
	/**
	 * Y size of a button
	 */
	private final int YBUTTON = 20;
	
	/**
	 * X spacing
	 */
	private final int XSPACING = 3;
	
	/**
	 * Y spacing
	 */
	private final int YSPACING = 3;
	
	/**
	 * The default X size of each Chunk
	 */
	private final int XCHUNK = 20;
	
	/**
	 * The default Y size of each Chunk
	 */
	private final int YCHUNK = 20;
	
	//****************************** Variable ********************
	
	/**
	 * Menu Listener for sending events to the editor
	 */
	private MenuListener mListener;
	
	/**
	 * List for stocking JButtons drew
	 */
	private List<Component> lButtons = new LinkedList<Component>();
	
	/**
	 * Enumerate the state of the menu
	 */
	private enum mState {MENU, CHUNK, OBJECT, ENTITY};
	private mState etat;
	
	//*************************** Constructors *******************
	
	/**
	 * Constructor of the class.
	 * Draw the menu Panel
	 */
	public MainMenuPanel(MenuListener mListener)
	{
		this.mListener = mListener;
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.setBackground(new Color(100, 100, 100)); // Backgroud color
		this.setBorder(BorderFactory.createEmptyBorder(XSPACING, YSPACING, XSPACING, YSPACING)); // Border of the panel
		
		this.drawMenu();
	}
	
	//************************ Menu methods *******************
	
	/**
	 * Main menu of the editor
	 * Choice chunks, entity or objects.
	 * Insert a save button too
	 */
	private void drawMenu()
	{
		if(this.etat != mState.MENU)
		{			
			this.cleanList();
			this.etat = mState.MENU;
			
			
			
			
					MainMenuPanel.this.drawObjects();

			
			
			// Add all button to the view with space between each one
			Component c;
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			

		}
	}
	
	
	/**
	 * Draw the objects present in the game
	 */
	private void drawObjects()
	{
		if(this.etat != mState.OBJECT)
		{
			this.etat = mState.OBJECT;
			this.cleanList();
			
			BufferedImage img = null; // Used for load image
			Image i; // Scaled image
			
			// Mushroom
			try
			{
				img = ImageIO.read(new File(Obj.MUSHROOM));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton mush = new JButton(new ImageIcon(i));
			mush.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			mush.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			mush.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
			mush.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MainMenuPanel.this.mListener.addObject(
								new Obj(
										new BoundingBox(
												new Coordinates(0, 0), 
												new Coordinates(XCHUNK, YCHUNK)),
										ObjectType.MUSHROOM));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
					
				}
			});
			
			// Star
			try
			{
				img = ImageIO.read(new File(Obj.STAR));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton star = new JButton(new ImageIcon(i));
			star.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			star.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			star.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
			star.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MainMenuPanel.this.mListener.addObject(
								new Obj(
										new BoundingBox(
												new Coordinates(0, 0), 
												new Coordinates(XCHUNK, YCHUNK)),
										ObjectType.STAR));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
					MainMenuPanel.this.mListener.saveMap();		
				}
			});
			
			// Add all JButtons with spacing
			Component c;
			this.add(mush); this.lButtons.add(mush);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(star); this.lButtons.add(star);
		}
	}
	
	/**
	 * Used to clean component in the list.
	 * First remove it from the view and create a new list
	 */
	private void cleanList()
	{
		Component bt;
		for(Iterator<Component> it = this.lButtons.iterator(); it.hasNext();)
		{
			bt = it.next();
			this.remove(bt);
		}
		
		this.lButtons = new LinkedList<Component>();
	}

	//******************* Runnable **********************
	
	@Override
	public void run()
	{
		while(true)
		{
			try
			{
				Thread.sleep(1000/FPS);
				this.repaint();
				this.updateUI();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
