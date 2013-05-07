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
    private ArrayList<Label> players;

    public GamePanel(MainFrame parent) {
        super();
        this.parentFrame = parent;
        boardPanel = new BoardPanel(parentFrame,this);
        rightPanel = new JPanel();
        //rightPanel.setBackground(Color.black);
        //boardPanel.setPreferredSize(boardPanel.getPreferredSize());
        this.setLayout(new BorderLayout(0, 0));
        addRightPanelComponents();
        this.add(rightPanel, BorderLayout.CENTER);
        this.add(boardPanel, BorderLayout.LINE_START);
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

    private void addRightPanelComponents() {
        rightPanel.setLayout(new GridBagLayout());
        players = new ArrayList<Label>();

        //Adds the first Label
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.LINE_START;
        c.gridx = 0;
        c.gridy = 0;
        Label newLabel = new Label();
        players.add(newLabel);
        rightPanel.add(newLabel, c);
    }

    private void updateRightPanel(IPlayer network) {
        int ownID = network.getId();
        IGameData data = network.getGameData();
        int ind = 1;
        int maxPriority = 0;
        for (ISnake s : data.getSnakes()) {
            if (s.getPriority() > maxPriority) {
                maxPriority = s.getPriority();
            }
        }
        int maxPriorityLength = (maxPriority + "").length();
        for (ISnake s : data.getSnakes()) {
            String pri = s.getPriority() + "";
            for (int i = pri.length(); i < maxPriorityLength; i++) {
                pri = " " + pri;
            }
            pri += " ";
            Label l;
            if (s.getID() == ownID) {
                l = players.get(0);
            } else {
                if (players.size() <= ind) {
                    //Add new label
                    GridBagConstraints c = new GridBagConstraints();
                    c.anchor = GridBagConstraints.LINE_START;
                    c.gridx = 0;
                    c.gridy = ind;
                    l = new Label();
                    players.add(l);
                    rightPanel.add(l, c);
                } else {
                    l = players.get(ind);
                }
                ind++;
            }
            l.setText(pri + s.getName());
            l.setVisible(true);
        }
        for (int i = ind; i < players.size(); i++) {
            players.get(ind).setVisible(false);
        }
    }

    private class BoardPanel extends JPanel {

        private ImageProceedingData currentImage;
        private MainFrame parentFrame;
        private GamePanel parentPanel;
        private int parcelLength;
        private static final int BOARDER = 1;
        public BoardPanel(MainFrame parent, GamePanel parentPanel) {
            super();
            currentImage = null;
            parentFrame = parent;
            this.parentPanel=parentPanel;
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

            int ownID = network.getId();
            int nextColorInd = 0;
            for (ISnake snake : data.getSnakes()) {
                int color;
                if (snake.getID() == ownID) {
                    color = parentFrame.getOptions().getCOLORPOOL()[parentFrame.getOptions().getOwnColorInd()];
                } else {
                    if (nextColorInd == parentFrame.getOptions().getOwnColorInd()) {
                        nextColorInd = (nextColorInd + 1) % parentFrame.getOptions().getCOLORPOOL().length;
                    }
                    color = parentFrame.getOptions().getCOLORPOOL()[nextColorInd];
                }
                currentImage.addRectangleFromSnake(snake, new Color(color));
            }
            parcelLength = Math.min(parentFrame.getOptions()
                    .getMaxParcelLength(), ((parentPanel.getSize().width-100) - 2)
                    / data.getDimensions().getX());
            parcelLength = Math.min(parcelLength, (int) ((parentPanel.getSize().height) - 2) / data.getDimensions().getY()); 
            int boardSizeX=parcelLength*data.getDimensions().getX()+2;
            int boardSizeY=parcelLength*data.getDimensions().getY()+2;
            this.setPreferredSize(new Dimension(boardSizeX,boardSizeY));
         }

        /**
         * Custom painting codes on this JPanel
         */
        @Override
        public void paintComponent(Graphics g) {
            if (currentImage != null) {
                int boardCornerX = 1;
                int boardCornerY = 1;
                super.paintComponent(g);
                // paint background
                g.setColor(Color.BLACK);
                g.drawRect(0, 0,
                        parcelLength * currentImage.getNumOfXFields() + 1,
                        parcelLength * currentImage.getNumOfYFields() + 1);
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
                    firstRectX = (currentCoords.getX()) * parcelLength + boardCornerX;
                    firstRectY = (currentCoords.getY()) * parcelLength + boardCornerY;
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
                    while (currentCoords != null) {
                        // copy the first tile of the snake to the new tile
                        // position
                        g.copyArea(firstRectX, firstRectY, parcelLength,
                                parcelLength, currentCoords.getX()
                                * parcelLength - firstRectX + boardCornerX,
                                currentCoords.getY() * parcelLength
                                - firstRectY + boardCornerY);
                        currentCoords = currentSnake.getNextCoord();
                    }
                    // add number to head of the snake
                    g.setColor(brightness(currentSnake.getColor()) < 130 ? Color.WHITE
                            : Color.BLACK);
                    Font f = new Font("SansSerif", Font.PLAIN, ((parcelLength - 2 * BOARDER) * 3) / 4);
                    g.setFont(f);
                    String s = currentSnake.getNumber();
                    determineFontSize(parcelLength - 2, parcelLength - 2, g, s);
                    FontMetrics fm = g.getFontMetrics();
                    int x = (parcelLength - fm.stringWidth(s)) / 2 + firstRectX;
                    int y = (parcelLength - fm.getDescent() + fm.getAscent()) / 2 + firstRectY;
                    g.drawString(s, x, y);
                }
            }
        }

        private void determineFontSize(int pX, int pY, Graphics g, String s) {
            FontMetrics fm = g.getFontMetrics();
            Font f = g.getFont();
            while (fm.stringWidth(s) < pX && fm.getHeight() < pY) {
                f = f.deriveFont(f.getSize2D() + 0.5f);
                fm = g.getFontMetrics(f);
            }
            while (fm.stringWidth(s) > pX || fm.getHeight() > pY) {
                f = f.deriveFont(f.getSize2D() - 0.5f);
                fm = g.getFontMetrics(f);
            }
        }
    }
}
