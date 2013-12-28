package fr.utbm.lo43.jvivarium.view;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MainFrame extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The Game Panel
	 */
	private GamePanel gameP;
	
	public MainFrame()
	{
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		
		this.gameP = new GamePanel();
		this.add(this.gameP);
		
		this.setSize(this.gameP.getSize());
		
		this.start();
	}
	
	private void start()
	{
		Thread game = new Thread(this.gameP);
		game.start();
		
		this.setVisible(true);
	}

}
