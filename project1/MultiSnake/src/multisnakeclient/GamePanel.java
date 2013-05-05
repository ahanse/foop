/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.util.*;
import javax.swing.JPanel;
import multisnakeglobal.*;

/**
 * 
 * @author Benedikt
 */
public class GamePanel extends JPanel implements Observer {

	private MainFrame parentFrame;
	private BoardPanel boardPanel;
	private JPanel rightPanel;
	private ArrayList<Label> Players;

	public GamePanel(MainFrame parent) {
		super();
		this.parentFrame = parent;
		boardPanel = new BoardPanel(parentFrame);
		rightPanel = new JPanel();
		//rightPanel.setBackground(Color.black);
		//boardPanel.setPreferredSize(boardPanel.getPreferredSize());
		this.setLayout(new BorderLayout(0,0));
		this.add(boardPanel, BorderLayout.CENTER);
		this.rightPanel.setPreferredSize(new Dimension(100,10));
		this.rightPanel.setBackground(Color.yellow);
		addRightPanelComponents();
		this.add(rightPanel, BorderLayout.LINE_END);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof IPlayer)) {
			throw new IllegalArgumentException();
		}
		boardPanel.update((IPlayer) o);
		updateRightPanel((IPlayer) o);
		parentFrame.drawContent();
	}
	
	private void addRightPanelComponents()
	{
		rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor=GridBagConstraints.LINE_START;
		c.gridx=0;
		parentFrame.getOptions();
		Players=new ArrayList<Label>();
		for(int i=0;i<parentFrame.getOptions().getMAXPLAYER();i++)
		{
			Label newLabel=new Label();
			Players.add(newLabel);
			c.gridy=i;
			rightPanel.add(newLabel,c);
		}
	}
	
	private void updateRightPanel(IPlayer network)
	{
		int ownID=network.getId();
		IGameData data=network.getGameData();
		int ind=1;
		for(ISnake s:data.getSnakes())
		{
			String pre="";
			if(s.getPriority()<10)
				pre=" ";
			pre+=s.getPriority();
			pre+=" ";
			Label l;
			if(s.getID()==ownID)
			{
				l=Players.get(0);
			}
			else
			{
				l=Players.get(ind);
				ind++;
			}
			l.setText(pre+s.getName());
			l.setVisible(true);
		}
		for(int i=ind;i<10;i++)
		{
			Players.get(ind).setVisible(false);
		}
	}

	private class BoardPanel extends JPanel {
		private ImageProceedingData currentImage;
		private MainFrame parentFrame;
		private int parcelLength;
		private static final int BOARDER = 1;

		public BoardPanel(MainFrame parent) {
			super();
			currentImage = null;
			parentFrame = parent;
			parcelLength = parent.getOptions().getMaxParcelLength();
		}

		// calculates the brightness of a color (between 0..255)
		private int brightness(Color c) {
			return (int) Math.sqrt(c.getRed() * c.getRed() * .241
					+ c.getGreen() * c.getGreen() * .691 + c.getBlue()
					* c.getBlue() * .068);
		}

		public void update(IPlayer network) {
			IGameData data = network.getGameData();

			currentImage = new ImageProceedingData(data.getDimensions().getX(),
					data.getDimensions().getY());

			for (ISnake snake : data.getSnakes()) {
				int color = parentFrame.getOptions().getCOLORPOOL()[snake
						.getID()];
				currentImage.addRectangleFromSnake(snake, new Color(color));
			}
			parcelLength = Math.min(parentFrame.getOptions()
					.getMaxParcelLength(), ((int) this.getSize().getWidth()-2)
					/ data.getDimensions().getX());
			parcelLength = Math.min(parcelLength, (int) (this.getSize()
					.getHeight()-2) / data.getDimensions().getY());
		}

		/**
		 * Custom painting codes on this JPanel
		 */
		@Override
		public void paintComponent(Graphics g) {
			if (currentImage != null) {
                                int boardCornerX=1;
                                int boardCornerY=1;
				super.paintComponent(g);
				// paint background
				g.setColor(Color.BLACK);
				g.drawRect(0, 0,
						parcelLength * currentImage.getNumOfXFields()+1,
						parcelLength * currentImage.getNumOfYFields()+1);
                                g.setColor(Color.WHITE);
				g.fillRect(1, 1,
						parcelLength * currentImage.getNumOfXFields(),
						parcelLength * currentImage.getNumOfYFields());
				// initiate some help variables
				RectangleData currentSnake;
				int firstRectX = 0;
				int firstRectY = 0;
				Point currentCoords;
				// until no data left, iteration over snakes
				while (!currentImage.isEmpty()) {
					// read data of next snake in line
					currentSnake = currentImage.getNextRectangleData();
					// read coords of the current snake
					currentCoords = currentSnake.getNextCoord();
					// save the first coords
					firstRectX = (currentCoords.getX()) * parcelLength+boardCornerX;
					firstRectY = (currentCoords.getY()) * parcelLength+boardCornerY;
					// set color
					g.setColor(Color.WHITE);
					// fill first tile of the snake on board including a boarder
					g.fillRect(firstRectX, firstRectY, parcelLength,
							parcelLength);
					g.setColor(currentSnake.getColor());
					g.fillRect(firstRectX + BOARDER, firstRectY + BOARDER,
							parcelLength - 2 * BOARDER, parcelLength - 2
									* BOARDER);
					// iterate over remaining coords of the snaketiles
					currentCoords = currentSnake.getNextCoord();
					while ((currentCoords.getX() != -1)
							&& (currentCoords.getY() != -1)) {
						// copy the first tile of the snake to the new tile
						// position
						g.copyArea(firstRectX, firstRectY, parcelLength,
								parcelLength, currentCoords.getX()
										* parcelLength - firstRectX+boardCornerX,
								currentCoords.getY() * parcelLength
										- firstRectY+boardCornerY);
						currentCoords = currentSnake.getNextCoord();
					}
					// add number to head of the snake
					g.setColor(brightness(currentSnake.getColor()) < 130 ? Color.WHITE
							: Color.BLACK);
                                        Font f=new Font("SansSerif", Font.PLAIN, ((parcelLength-2*BOARDER)*3)/4);
                                        g.setFont(f);
                                        String s=currentSnake.getNumber();
                                        determineFontSize(parcelLength-2,parcelLength-2,g,s);
                                        FontMetrics fm = g.getFontMetrics();
                                        int x=(parcelLength-fm.stringWidth(s))/2+firstRectX;
                                        int y=(parcelLength-fm.getDescent()+fm.getAscent())/2+firstRectY;
					g.drawString(s, x, y);
				}
			}
		}
                
                private void determineFontSize(int pX, int pY, Graphics g,String s)
                {
                    FontMetrics fm=g.getFontMetrics();
                    Font f=g.getFont();
                    while(fm.stringWidth(s)<pX && fm.getHeight()<pY)
                    {
                        f=f.deriveFont(f.getSize2D()+0.5f);
                        fm=g.getFontMetrics(f);
                    }
                    while(fm.stringWidth(s)>pX || fm.getHeight()>pY)
                    {
                        f=f.deriveFont(f.getSize2D()-0.5f);
                        fm=g.getFontMetrics(f);
                    }
                }
	}
}
