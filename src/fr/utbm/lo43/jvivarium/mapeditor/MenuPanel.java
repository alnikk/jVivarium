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
			
			BufferedImage img = null; // Used for load image
			Image i; // Scaled image
			
			// Fire
			try
			{
				img = ImageIO.read(new File(Chunk.FIRE));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton fire = new JButton(new ImageIcon(i));
			fire.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			fire.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			fire.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
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
			try
			{
				img = ImageIO.read(new File(Chunk.CASTLE));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton castle = new JButton(new ImageIcon(i));
			castle.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			castle.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			castle.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
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
			try
			{
				img = ImageIO.read(new File(Chunk.BRICK));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton brick = new JButton(new ImageIcon(i));
			brick.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			brick.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			brick.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
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
			try
			{
				img = ImageIO.read(new File(Chunk.PIPE));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton pipe = new JButton(new ImageIcon(i));
			pipe.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			pipe.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			pipe.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
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
										new Coordinates(XCHUNK, XBUTTON)), 
										FieldType.PIPE));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
				}
			});

			// Pink Brick
			try
			{
				img = ImageIO.read(new File(Chunk.PINK_BRICK));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton pink_brick = new JButton(new ImageIcon(i));
			pink_brick.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			pink_brick.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			pink_brick.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
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
			
			BufferedImage img = null; // Used for load image
			Image i; // Scaled image
			
			// Mario
			try
			{
				img = ImageIO.read(new File(Mario.IMG));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton mario = new JButton(new ImageIcon(i));
			mario.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			mario.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			mario.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
			mario.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MenuPanel.this.mListener.addEntity(
								new Mario(new BoundingBox(
										new Coordinates(0, 0), 
										new Coordinates(XCHUNK, YCHUNK))));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			// Peach
			try
			{
				img = ImageIO.read(new File(Peach.IMG));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton peach = new JButton(new ImageIcon(i));
			peach.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			peach.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			peach.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
			peach.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MenuPanel.this.mListener.addEntity(
								new Peach(new BoundingBox(
										new Coordinates(0, 0), 
										new Coordinates(XCHUNK, YCHUNK))));
					}
					catch (NegativeSizeException e)
					{
						e.printStackTrace();
					}
				}
			});
			
			// Bowser
			try
			{
				img = ImageIO.read(new File(Bowser.IMG));
			}
			catch (IOException e1)
			{
				e1.printStackTrace();
			}
			i = img.getScaledInstance(XBUTTON, XBUTTON/2, Image.SCALE_FAST);
			JButton bowser = new JButton(new ImageIcon(i));
			bowser.setPreferredSize(new Dimension(XBUTTON,XBUTTON/2));
			bowser.setMaximumSize(new Dimension(XBUTTON,XBUTTON/2));
			bowser.setMinimumSize(new Dimension(XBUTTON,XBUTTON/2));
			bowser.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent arg0)
				{
					try
					{
						MenuPanel.this.mListener.addEntity(
								new Bowser(new BoundingBox(
										new Coordinates(0, 0), 
										new Coordinates(XCHUNK, YCHUNK))));
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
			this.add(mario); this.lButtons.add(mario);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(peach); this.lButtons.add(peach);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(bowser); this.lButtons.add(bowser);
			c = Box.createRigidArea(new Dimension(1, YSPACING*10));
			this.add(c); this.lButtons.add(c);
			this.add(back); this.lButtons.add(back);			
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
						MenuPanel.this.mListener.addObject(
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
						MenuPanel.this.mListener.addObject(
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
			this.add(mush); this.lButtons.add(mush);
			c = Box.createRigidArea(new Dimension(1, YSPACING));
			this.add(c); this.lButtons.add(c);
			this.add(star); this.lButtons.add(star);
			c = Box.createRigidArea(new Dimension(1, YSPACING*10));
			this.add(c); this.lButtons.add(c);
			this.add(back); this.lButtons.add(back);	
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
