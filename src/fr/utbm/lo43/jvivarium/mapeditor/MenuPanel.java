package fr.utbm.lo43.jvivarium.mapeditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel implements Runnable
{
	public MenuPanel()
	{
		this.setBackground(new Color(100, 100, 100));
		this.setSize(50, 200);
		//this.setPreferredSize(new Dimension(50, 200));
		
		JLabel lbMenu = new JLabel("Menu");
		this.add(lbMenu);
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		g.setColor(new Color(0, 255, 0));
		g.fillRect(0, 50, 20, 20);
		
		g.setColor(new Color(0, 0, 255));
		g.fillRect(0, 80, 20, 20);
		
		g.setColor(new Color(125, 125, 125));
		g.fillRect(0, 110, 20, 20);
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(1000);
			this.repaint();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
