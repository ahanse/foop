/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.*;
import java.util.*;
import javax.swing.JButton;
import javax.swing.JPanel;
import multisnakeglobal.*;
import multisnakeglobal.Point;

/**
 *
 * @author Benedikt
 */
public class GamePanel extends JPanel implements Observer {

    private MainFrame parentFrame;
    private BoardPanel boardPanel;
    private ArrayList<Label> players;
    private RightPanel rightPanel;
    //private int Zaehler;

    public GamePanel(MainFrame parent) {
        super();
        this.parentFrame = parent;
        boardPanel = new BoardPanel(parentFrame, this);

        //rightPanel.setBackground(Color.black);
        //boardPanel.setPreferredSize(boardPanel.getPreferredSize());
        this.setLayout(new BorderLayout(0, 0));
        rightPanel = new RightPanel();
        rightPanel.addRightPanelComponents();
        this.add(rightPanel, BorderLayout.CENTER);
        this.add(boardPanel, BorderLayout.LINE_START);
        //Zaehler = 0;

    }

    @Override
    public void update(Observable o, Object arg) {
        if (!(o instanceof IPlayer)) {
            throw new IllegalArgumentException();
        }
        int[] cols = calculateColors((IPlayer) o);
        rightPanel.updateRightPanel((IPlayer) o, cols);
        boardPanel.update((IPlayer) o, cols);
        parentFrame.drawContent();
        //Zaehler++;
        //System.out.println(Zaehler + "");
    }

    int[] calculateColors(IPlayer network) {
        int ownID = network.getId();
        int nextColorInd = 0;
        java.util.List<ISnake> snakes = network.getGameData().getSnakes();
        int[] erg = new int[snakes.size()];
        for (int i = 0; i < snakes.size(); i++) {
            if (snakes.get(i).getID() == ownID) {
                erg[i] = parentFrame.getOptions().getCOLORPOOL()[parentFrame.getOptions().getOwnColorInd()];
            } else {
                if (nextColorInd == parentFrame.getOptions().getOwnColorInd()) {
                    nextColorInd = (nextColorInd + 1) % parentFrame.getOptions().getCOLORPOOL().length;
                }
                erg[i] = parentFrame.getOptions().getCOLORPOOL()[nextColorInd];
                nextColorInd = (nextColorInd + 1) % parentFrame.getOptions().getCOLORPOOL().length;
            }
        }
        return erg;
    }

    // calculates the brightness of a color (between 0..255)
    private int brightness(Color c) {
        return (int) Math.sqrt(c.getRed() * c.getRed() * .241
                + c.getGreen() * c.getGreen() * .691 + c.getBlue()
                * c.getBlue() * .068);
    }

    private class BoardPanel extends JPanel {

        private ImageProceedingData currentImage;
        private MainFrame parentFrame;
        private GamePanel parentPanel;
        private int parcelLength;
        private static final int BOARDER = 1;
        private Label lblBoardCaption;
        private Label lblBoardInformation;

        public BoardPanel(MainFrame parent, GamePanel parentPanel) {
            super();
            currentImage = null;
            parentFrame = parent;
            this.parentPanel = parentPanel;
            parcelLength = parent.getOptions().getMaxParcelLength();
            this.setLayout(new GridBagLayout());

            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.CENTER;
            c.gridx = 0;
            c.gridy = 0;
            lblBoardCaption = new Label();
            lblBoardCaption.setFont(new Font("SansSerif", Font.BOLD, 20));
            lblBoardCaption.setText("");
            this.add(lblBoardCaption, c);

            c.gridy = 1;
            lblBoardInformation = new Label();
            lblBoardInformation.setFont(new Font("SansSerif", Font.PLAIN, 18));
            lblBoardInformation.setText("");
            this.add(lblBoardInformation, c);
        }

        public void update(IPlayer network, int[] cols) {
            IGameData data = network.getGameData();

            switch (data.getStatus()) {
                case WAITINGFORPLAYERS:
                    try {
                        lblBoardInformation.setText("Your own local network adress: " + InetAddress.getLocalHost().getHostAddress());
                    } catch (Exception e) {
                        lblBoardInformation.setText("Couldn't find out own IP-adress.");
                    }
                    lblBoardInformation.setBackground(this.getBackground());
                    lblBoardInformation.setVisible(true);
                    lblBoardCaption.setText("Waiting for players");
                    lblBoardCaption.setBackground(this.getBackground());
                    lblBoardCaption.setVisible(true);
                    currentImage = null;
                    break;
                case TIMETOFIGHT:
                    lblBoardCaption.setText("Get Ready!");
                    lblBoardCaption.setBackground(Color.WHITE);
                    lblBoardCaption.setVisible(true);
                    lblBoardInformation.setBackground(Color.WHITE);
                    lblBoardInformation.setText("The game is starting");
                    lblBoardInformation.setVisible(true);
                    calculateImage(network, cols);
                    break;
                case RUNNING:
                    lblBoardInformation.setVisible(false);
                    lblBoardCaption.setVisible(false);
                    calculateImage(network, cols);
                    break;
                case FINISHED:
                    lblBoardInformation.setVisible(false);
                    lblBoardCaption.setVisible(false);
                    calculateImage(network, cols);
                    break;
                default:
                    break;

            }
        }

        private void calculateImage(IPlayer network, int[] cols) {
            IGameData data = network.getGameData();
            int i = 0;
            currentImage = new ImageProceedingData(data.getDimensions().getX(),
                    data.getDimensions().getY());
            for (ISnake snake : data.getSnakes()) {
                if (snake.getHead() != null) {
                    currentImage.addRectangleFromSnake(snake, new Color(cols[i]));
                }
                i++;
            }
            parcelLength = Math.min(parentFrame.getOptions()
                    .getMaxParcelLength(), ((parentPanel.getSize().width - 100) - 2)
                    / data.getDimensions().getX());
            parcelLength = Math.min(parcelLength, (int) ((parentPanel.getSize().height) - 2) / data.getDimensions().getY());
            int boardSizeX = parcelLength * data.getDimensions().getX() + 2;
            int boardSizeY = parcelLength * data.getDimensions().getY() + 2;
            this.setPreferredSize(new Dimension(boardSizeX, boardSizeY));
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
                Point currentCoords = new Point(0, 0);
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

    private class RightPanel extends JPanel implements ActionListener {

        private JPanel playerListPanel;
        private JButton backButton;

        public RightPanel() {
            this.setLayout(new BorderLayout());
            JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));

            //Button go back to MainMenu
            backButton = new JButton("Back to Main Menu");
            backButton.setActionCommand("Back");
            backButton.setVisible(false);

            btnPnl.add(backButton);
            backButton.addActionListener(this);

            playerListPanel = new JPanel();

            this.add(playerListPanel, BorderLayout.CENTER);
            this.add(btnPnl, BorderLayout.PAGE_END);
        }

        public void addRightPanelComponents() {
            playerListPanel.setLayout(new GridBagLayout());
            players = new ArrayList<Label>();

            //Adds the first Label
            GridBagConstraints c = new GridBagConstraints();
            c.anchor = GridBagConstraints.LINE_START;
            c.gridx = 0;
            c.gridy = 0;
            Label newLabel = new Label();
            players.add(newLabel);
            playerListPanel.add(newLabel, c);
        }

        private Integer[] sortSnakes(final java.util.List<ISnake> snakes) {
            Integer[] idx = new Integer[snakes.size()];
            for (int i = 0; i < snakes.size(); i++) {
                idx[i] = i;
            }
            Arrays.sort(idx, new Comparator<Integer>() {
                @Override
                public int compare(final Integer o1, final Integer o2) {
                    return Integer.compare(getPoints(snakes.get(o2)), getPoints(snakes.get(o1)));
                }
            });
            return idx;
        }

        public void updateRightPanel(IPlayer network, int[] cols) {

            int ownID = network.getId();
            IGameData data = network.getGameData();
            if (data.getStatus() == GameState.FINISHED) {
                backButton.setVisible(true);
                Integer[] idx = sortSnakes(data.getSnakes());
                Label l = players.get(0);
                l.setBackground(null);
                l.setText("Results");
                l.setFont(new Font("SansSerif", Font.BOLD, 20));
                l.setForeground(Color.black);
                int maxPlaceSize=0;
                int maxPointSize=0;
                for (int i=0;i<idx.length;i++) {
                    if(((i+1)+"").length()>maxPlaceSize)
                    {
                        maxPlaceSize=((i+1)+"").length();
                    }
                    if ((getPoints(data.getSnakes().get(i))+"").length() > maxPointSize) {
                        maxPointSize = (getPoints(data.getSnakes().get(i))+"").length();
                    }
                }
                for (int i = 0; i < idx.length; i++) {
                    if (players.size() <= i + 1) {
                        //Add new label
                        GridBagConstraints c = new GridBagConstraints();
                        c.anchor = GridBagConstraints.LINE_START;
                        c.gridx = 0;
                        c.gridy = i + 1;
                        l = new Label();
                        players.add(l);
                        playerListPanel.add(l, c);
                    } else {
                        l = players.get(i + 1);
                    }
                    Color c = new Color(cols[idx[i]]);
                    l.setBackground(c);
                    l.setForeground(brightness(c) < 130 ? Color.WHITE
                            : Color.BLACK);
                    String t=(i+1)+".";
                    for(int j=t.length();j<=maxPlaceSize;j++)
                    {
                        t+="  ";
                    }
                    t+=" | ";
                    String tmp=getPoints(data.getSnakes().get(idx[i]))+"";
                    for(int j=tmp.length();j<maxPointSize;j++)
                    {
                        t+="  ";
                    }
                    t+=tmp+" | "+data.getSnakes().get(idx[i]).getName();
                    l.setText(t);
                    l.setVisible(true);
                }
                for (int i = idx.length + 2; i < players.size(); i++) {
                    players.get(i).setVisible(false);
                }
            } else {
                int ind = 1;
                int maxPriority = 0;
                int maxNextPriority = 0;
                int maxNickSize = 0;
                for (ISnake s : data.getSnakes()) {
                    if (s.getPriority() > maxPriority) {
                        maxPriority = s.getPriority();
                    }
                    if (s.getNextPriority() > maxNextPriority) {
                        maxNextPriority = s.getNextPriority();
                    }
                    if (s.getName().length() > maxNickSize) {
                        maxNickSize = s.getName().length();
                    }
                }
                int maxPriorityLength = (maxPriority + "").length();
                int maxNextPriorityLength = (maxNextPriority + "").length();
                int j = 0;
                for (ISnake s : data.getSnakes()) {
                    String pri = s.getPriority() + "";
                    for (int i = pri.length(); i < maxPriorityLength; i++) {
                        pri = " " + pri;
                    }
                    pri += " -> ";
                    String nextPri = s.getNextPriority() + "";
                    for (int i = nextPri.length(); i < maxNextPriorityLength; i++) {
                        nextPri = " " + nextPri;
                    }
                    pri += nextPri + " ";
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
                            playerListPanel.add(l, c);
                        } else {
                            l = players.get(ind);
                        }
                        ind++;
                    }

                    Color col = new Color(cols[j]);
                    l.setBackground(col);
                    l.setForeground(brightness(col) < 130 ? Color.WHITE
                            : Color.BLACK);
                    pri = pri + s.getName();
                    if (s.isDead()) {
                        pri += " (+)";
                    }
                    l.setText(pri);
                    l.setVisible(true);
                    j++;
                }
                for (int i = ind; i < players.size(); i++) {
                    players.get(i).setVisible(false);
                }
            }
        }

        private int getPoints(ISnake s) {
            if (s.isDead()) {
                return 0;
            } else {
                return s.getCoordinates().size();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String com = e.getActionCommand();
            if (com.equals("")) {
            } else if (com.equals("Back")) {
                parentFrame.drawPanel("MainMenu");
            } else {
            }
        }
    }
}
