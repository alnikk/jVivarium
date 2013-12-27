package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.utbm.lo43.jvivarium.core.BoundingBox;
import fr.utbm.lo43.jvivarium.core.Chunk;
import fr.utbm.lo43.jvivarium.core.Coordinates;
import fr.utbm.lo43.jvivarium.core.FieldType;
import fr.utbm.lo43.jvivarium.core.NegativeSizeException;

public class MenuPanel extends JPanel implements Runnable
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
	public MenuPanel(MenuListener mListener)
	{
		this.mListener = mListener;
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
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
			
			// Chunk button
			JButton bt_chunk = new JButton("Chunks");
			bt_chunk.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			bt_chunk.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			bt_chunk.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			bt_chunk.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					MenuPanel.this.drawChunks();
				}
			});
			
			// Entity button
			JButton bt_entity = new JButton("Entity");
			bt_entity.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			bt_entity.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			bt_entity.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			bt_entity.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					MenuPanel.this.drawEntity();
				}
			});
			
			// Objects button
			JButton bt_object = new JButton("Objects");
			bt_object.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			bt_object.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			bt_object.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			bt_object.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					MenuPanel.this.drawObjects();
				}
			});
			
			// Save button
			JButton bt_save = new JButton("Save");
			bt_save.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			bt_save.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			bt_save.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			bt_save.addActionListener(new ActionListener()
			{
				@Override // On click
				public void actionPerformed(ActionEvent arg0)
				{
					MenuPanel.this.mListener.saveMap();				
				}
			});
			
			
			// Add all button to the view with space between each one
			Component c;
			this.add(bt_chunk); this.lButtons.add(bt_chunk);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(bt_entity); this.lButtons.add(bt_entity);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(bt_object); this.lButtons.add(bt_object);
			c = Box.createRigidArea(new Dimension(1, YSPACING*10));
			this.add(c); this.lButtons.add(c);
			this.add(bt_save); this.lButtons.add(bt_save);
		}
	}
	
	/**
	 * Menu Chunks.
	 * List of all chunks icons in JButton.
	 */
	private void drawChunks()
	{
		if(this.etat != mState.CHUNK)
		{
			// Clean and set
			this.cleanList();
			this.etat = mState.CHUNK;
			
			// Fire
			JButton fire = new JButton(new ImageIcon(Chunk.FIRE));
			fire.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			fire.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			fire.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			fire.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MenuPanel.this.mListener.addChunk(
								new Chunk(new BoundingBox(
										new Coordinates(0, 0), 
										new Coordinates(XCHUNK, YCHUNK)), 
										FieldType.FIRE));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			
			// Castle
			JButton castle = new JButton(new ImageIcon(Chunk.CASTLE));
			castle.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			castle.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			castle.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			castle.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MenuPanel.this.mListener.addChunk(
								new Chunk(new BoundingBox(
										new Coordinates(0, 0), 
										new Coordinates(XCHUNK, YCHUNK)), 
										FieldType.CASTLE));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			// Brick
			JButton brick = new JButton(new ImageIcon(Chunk.BRICK));
			brick.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			brick.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			brick.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			brick.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MenuPanel.this.mListener.addChunk(
								new Chunk(new BoundingBox(
										new Coordinates(0, 0), 
										new Coordinates(XCHUNK, YCHUNK)), 
										FieldType.BRICK));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			// Pipe
			JButton pipe = new JButton(new ImageIcon(Chunk.PIPE));
			pipe.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			pipe.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			pipe.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			pipe.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MenuPanel.this.mListener.addChunk(
								new Chunk(new BoundingBox(
										new Coordinates(0, 0), 
										new Coordinates(XCHUNK, YCHUNK)), 
										FieldType.PIPE));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
				}
			});

			// Pink Brick
			JButton pink_brick = new JButton(new ImageIcon(Chunk.PINK_BRICK));
			pink_brick.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			pink_brick.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			pink_brick.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			pink_brick.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MenuPanel.this.mListener.addChunk(
								new Chunk(new BoundingBox(
										new Coordinates(0, 0), 
										new Coordinates(XCHUNK, YCHUNK)), 
										FieldType.PINK_BRICK));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			// Back Button
			JButton back = new JButton("Back");
			back.setPreferredSize(new Dimension(XBUTTON,YBUTTON));
			back.setMaximumSize(new Dimension(XBUTTON,YBUTTON));
			back.setMinimumSize(new Dimension(XBUTTON,YBUTTON));
			back.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					MenuPanel.this.drawMenu();
				}
			});
			
			// Add all JButtons with spacing
			Component c;
			this.add(fire); this.lButtons.add(fire);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(castle); this.lButtons.add(castle);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(brick); this.lButtons.add(brick);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(pipe); this.lButtons.add(pipe);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(pink_brick); this.lButtons.add(pink_brick);
			c = Box.createRigidArea(new Dimension(1, YSPACING*10));
			this.add(c); this.lButtons.add(c);
			this.add(back); this.lButtons.add(back);
		}
	}
	
	/**
	 * Draw all the entity with their own icons =)
	 */
	private void drawEntity()
	{
		if(this.etat != mState.ENTITY)
		{
			this.etat = mState.CHUNK;
			this.cleanList();
			
			// TODO Add button entity
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
			
			// TODO Add button objects
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
