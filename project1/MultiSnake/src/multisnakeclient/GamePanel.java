/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JPanel;
import multisnakeglobal.*;

/**
 *
 * @author Benedikt
 */
public class GamePanel extends JPanel implements Observer{

    private MainFrame parentFrame;
    private ImageProceedingData currentImage;
    private static final int BOARDER = 1;
    private int parcelLength;
    
    public GamePanel(MainFrame parent) {
        currentImage=null;
        this.parentFrame=parent;
        parcelLength=parent.getOptions().getMaxParcelLength();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(!(o instanceof INetworkClient))
        {
            throw new IllegalArgumentException();
        }
        INetworkClient network=(INetworkClient)o;
        IGameData data=network.getGameData();
        
        currentImage=new ImageProceedingData(data.getDimensions().getX(), data.getDimensions().getY());
        
        for (ISnake snake: data.getSnakes())
        {
            int color=parentFrame.getOptions().getCOLORPOOL()[snake.getID()];
            currentImage.addRectangleFromSnake(snake, new Color(color));
        }
        parentFrame.drawContent();
        parcelLength=Math.min(parentFrame.getOptions().getMaxParcelLength(),(int)this.getSize().getWidth()/data.getDimensions().getX());
        parcelLength=Math.min(parcelLength,(int)this.getSize().getHeight()/data.getDimensions().getY());
    }
    
    /**
         * Custom painting codes on this JPanel
         */
        @Override
        public void paintComponent(Graphics g) {
            if(currentImage!=null)
            {
            super.paintComponent(g);
            // paint background
            g.setColor(Color.WHITE);
            g.clearRect(0, 0, parcelLength * currentImage.getNumOfXFields(), parcelLength * currentImage.getNumOfYFields());
            // initiate some help variables
            RectangleData currentSnake;
            int firstRectX = 0;
            int firstRectY = 0;
            multisnakeglobal.Point currentCoords;
            // until no data left, iteration over snakes
            while (!currentImage.isEmpty()) {
                // read data of next snake in line
                currentSnake = currentImage.getNextRectangleData();
                // read coords of the current snake
                currentCoords = currentSnake.getNextCoord();
                // save the first coords
                firstRectX = (currentCoords.getX()) * parcelLength;
                firstRectY = (currentCoords.getY()) * parcelLength;
                // set color
                g.setColor(Color.WHITE);
                // fill first tile of the snake on board including a boarder
                g.fillRect(firstRectX, firstRectY, parcelLength, parcelLength);
                g.setColor(currentSnake.getColor());
                g.fillRect(firstRectX + BOARDER, firstRectY + BOARDER, parcelLength - 2 * BOARDER, parcelLength - 2 * BOARDER);
                // iterate over remaining coords of the snaketiles
                currentCoords = currentSnake.getNextCoord();
                while ((currentCoords.getX() != -1) && (currentCoords.getY() != -1)) {
                    // copy the first tile of the snake to the new tile position
                    g.copyArea(firstRectX, firstRectY, parcelLength, parcelLength, currentCoords.getX() * parcelLength - firstRectX, currentCoords.getY() * parcelLength - firstRectY);
                    currentCoords = currentSnake.getNextCoord();
                }
                // add number to head of the snake
                g.setColor(brightness(currentSnake.getColor()) < 130 ? Color.WHITE : Color.BLACK);
                g.drawString(currentSnake.getNumber(), firstRectX, firstRectY + parcelLength);
            }
        }
    }
        
    // calculates the brightness of a color (between 0..255)
    public int brightness(Color c) {
        return (int) Math.sqrt(
                c.getRed() * c.getRed() * .241
                + c.getGreen() * c.getGreen() * .691
                + c.getBlue() * c.getBlue() * .068);
    }
}
