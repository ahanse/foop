/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import multisnakeclient.GameModel.*;

/**
 *
 * @author max
 */

/* this class will be part of a MVC design. The view in which the gui will be 
 * constructed */
public class GameView extends JFrame {

    private GameModel model;
    private Dimension defaultWindowSize;
    private ArrayList<JButton> menuButtonList;
    private JButton backToMenuButton;
    private JPanel contentPanel;

    public GameView(GameModel model, int width, int height) {
        this.model = model;
        defaultWindowSize = new Dimension(width, height);
        this.setTitle("MultiSnake Client GUI Test");
        this.setSize(defaultWindowSize);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        //this.pack();
        contentPanel = new JPanel();

        // init ALL buttons
        menuButtonList = new ArrayList<JButton>();
        menuButtonList.add(new JButton("Start New Server"));
        menuButtonList.add(new JButton("Join Game"));
        menuButtonList.add(new JButton("Options"));
        menuButtonList.add(new JButton("Help"));
        menuButtonList.add(new JButton("Exit"));
        menuButtonList.add(new JButton("Draw image"));
        backToMenuButton = new JButton("Back to Menu");

        viewMainMenu();
    }

    public void reset() {
        this.getContentPane().removeAll();
    }

    public void drawContent() {
        //this.revalidate();
        this.repaint();
    }

    // shows the main menu in frame
    public final void viewMainMenu() {
        // reset framecontent
        reset();
        // create a panel
        contentPanel = new JPanel();
        contentPanel.setSize(defaultWindowSize);
        //place buttons on menupanel
        int numberOfButtons = menuButtonList.size();
        contentPanel.setLayout(null);
        int width = 150;
        int height = 30;
        double y = ((defaultWindowSize.getHeight() - 100) / (2 * numberOfButtons)) - height + 50;
        for (JButton button : menuButtonList) {
            double x = (defaultWindowSize.getWidth() - width) / 2;
            button.setBounds((int) x, (int) y, width, height);
            contentPanel.add(button);
            y += (defaultWindowSize.getHeight() - 100) / numberOfButtons;
        }
        this.add(contentPanel);
        // redraw frame
        drawContent();
    }

    // shows the join game screen in frame
    public final void viewNewServer() {
        // reset framecontent
        reset();
        // create a panel
        contentPanel = new JPanel();
        // insert back to menu button
        insertBackButton("Back To Menu", 150, 30);
        // do smthing with panel
        contentPanel.add(new JLabel("New Server Screen"));

        this.add(contentPanel);
        // redraw frame
        drawContent();
    }

    // shows the join game screen in frame
    public final void viewJoinGame() {
        // reset framecontent
        reset();
        // create a panel
        contentPanel = new JPanel();
        // insert back to menu button
        insertBackButton("Back To Menu", 150, 30);
        // do smthing with panel
        contentPanel.add(new JLabel("Join Game Screen"));

        this.add(contentPanel);
        // redraw frame
        drawContent();
    }

    // shows the options in frame
    public final void viewOptions() {
        // reset framecontent
        reset();
        // create a panel
        contentPanel = new JPanel();
        // insert back to menu button
        insertBackButton("Back To Menu", 150, 30);
        // do smthing with panel
        contentPanel.add(new JLabel("Options Screen"));

        this.add(contentPanel);
        // redraw frame
        drawContent();
    }

    // shows the HelpScreen in Frame
    public final void viewHelp() {
        // reset framecontent
        reset();
        // create a panel
        contentPanel = new JPanel();
        contentPanel.setSize(defaultWindowSize);
        JLabel label = new JLabel("Help Screen");
        contentPanel.add(label);
        String text = "Auf einer rechteckigen Anordnung von Feldern befinden sich Schlangen in mehreren unterschiedlichen Farben, deren Köpfe sich bei jedem Spielzug (nach einem vorgegebenen Takt) um ein Feld nach links, rechts, oben oder unten bewegen. Der Schlangenkörper (von dem jeder Teil genauso wie der Kopf ein Feld belegt) folgt der Bewegungslinie des Schlangenkopfs. Jeder Mitspieler steuert die Bewegungsrichtung des Kopfs seiner Schlange, die anderen Schlangen werden vom Computer gesteuert. Treffen unterschiedlich gefärbte Schlangenköpfe aufeinander, frisst die Schlange mit höherer Priorität jene mit niedrigerer Priorität, wobei die Priorität von der Farbe abhängt. Der Rumpf der gefressenen Schlange wird (Zug für Zug) zu einem Teil der fressenden Schlange, wodurch sich die fressende Schlange verlängert. Ein Spieler, dessen Schlange gefressen wird, scheidet aus dem Spiel aus. Trifft ein Schlangenkopf auf den Rumpf einer andersfarbigen Schlange, frisst sie den Rest der Schlange unabhängig von der Priorität, während die teilweise gefressene Schlange entsprechend kürzer wird. Gleichfarbige Schlangen können sich nicht fressen; wenn sie aufeinandertreffen stoppen sie ihre Bewegung, sodass sich nie zwei Schlangen auf demselben Feld befinden können.Anfangs besteht jede Schlange nur aus einem Kopf. Ziel jeden Spielers (auch des Computers) ist es so lange wie möglich zu werden und andersfarbige Schlangen zu eliminieren. Ein Spiel ist zu Ende wenn alle verbliebenen Schlangen dieselbe Farbe haben. Der Spieler dieser Farbe (oder der Computer falls kein Spieler übrig ist) hat mit einer der Länge der längsten verbliebenen Schlange entsprechenden Punktezahl gewonnen.";
        JTextArea textArea = new JTextArea(cutStringByLength(text, 100));
        textArea.setEditable(false);
        contentPanel.add(textArea);
        // insert back to menu button
        insertBackButton("Back To Menu", 150, 30);
        this.add(contentPanel);
        // redraw frame
        drawContent();
    }

    public void drawImage() {
        reset();
        this.add(new ImageTemplateJPanel());
        this.pack();
    }

    // Swing Program Template
    @SuppressWarnings("serial")
    public class ImageTemplateJPanel extends JPanel {

        public static final int CANVAS_WIDTH = 500;
        public static final int CANVAS_HEIGHT = 500;
        public static final int BOARDER = 1;
        // ......

        // private variables of GUI components
        // ......
        /**
         * Constructor to setup the GUI components
         */
        public ImageTemplateJPanel() {
            setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
            JButton btn = new JButton("back");
            this.setLayout(null);
            btn.setBounds(300, 300, 120, 20);
            //this.add(btn);
            // "this" JPanel container sets layout
            // setLayout(new ....Layout());

            // Allocate the UI components
            // .....

            // "this" JPanel adds components
            // add(....)

            // Source object adds listener
            // .....
        }

        /**
         * Custom painting codes on this JPanel
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageProceedingData img = model.getCurrentImage();
            // paint background
            int tileLength = model.getParcelLength();
            g.setColor(Color.WHITE);
            g.clearRect(0, 0, tileLength * img.getNumOfXFields(), tileLength * img.getNumOfYFields());
            // initiate some help variables
            RectangleData currentSnake;
            int firstRectX = 0;
            int firstRectY = 0;
            multisnakeglobal.Point currentCoords;
            // until no data left, iteration over snakes
            while (!img.isEmpty()) {
                // read data of next snake in line
                currentSnake = img.getNextRectangleData();
                // read coords of the current snake
                currentCoords = currentSnake.getNextCoord();
                // save the first coords
                firstRectX = (currentCoords.getX()) * tileLength;
                firstRectY = (currentCoords.getY()) * tileLength;
                // set color
                g.setColor(Color.WHITE);
                // fill first tile of the snake on board including a boarder
                g.fillRect(firstRectX, firstRectY, tileLength, tileLength);
                g.setColor(currentSnake.getColor());
                g.fillRect(firstRectX + BOARDER, firstRectY + BOARDER, tileLength - 2 * BOARDER, tileLength - 2 * BOARDER);
                // iterate over remaining coords of the snaketiles
                currentCoords = currentSnake.getNextCoord();
                while ((currentCoords.getX() != -1) && (currentCoords.getY() != -1)) {
                    // copy the first tile of the snake to the new tile position
                    g.copyArea(firstRectX, firstRectY, tileLength, tileLength, currentCoords.getX() * tileLength - firstRectX, currentCoords.getY() * tileLength - firstRectY);
                    currentCoords = currentSnake.getNextCoord();
                }
                // add number to head of the snake
                g.setColor(model.brightness(currentSnake.getColor()) < 130 ? Color.WHITE : Color.BLACK);
                g.drawString(currentSnake.getNumber(), firstRectX, firstRectY + tileLength);
            }
        }
    }

    // inserts line breaks into a string
    private String cutStringByLength(String text, int n) {
        int i = 0;
        Boolean check = false;
        String newText = "";
        for (char ch : text.toCharArray()) {
            if (!check) {
                newText += ch;
            } else {
                check = false;
            }
            if ((i >= n - 5 && ch == ' ') || (i >= n - 20 && ch == '.') || (i >= n - 15 && ch == ',')) {
                if (ch == '.' || ch == ',') {
                    check = true;
                }
                newText += "\n";
                i = 0;
            }
            i++;
        }
        return newText;
    }

    private void insertBackButton(String title, int width, int height) {
        backToMenuButton.setText(title);
        backToMenuButton.setBounds((int) (defaultWindowSize.getWidth() - (width + 10)), (int) (defaultWindowSize.getHeight() - (height + 30)), width, height);
        this.add(backToMenuButton);
    }

    // event listeners for buttons
    void addStartServerListener(ActionListener mal) {
        menuButtonList.get(0).addActionListener(mal);
    }

    void addJoinGameListener(ActionListener mal) {
        menuButtonList.get(1).addActionListener(mal);
    }

    void addOptionsListener(ActionListener mal) {
        menuButtonList.get(2).addActionListener(mal);
    }

    void addHelpListener(ActionListener mal) {
        menuButtonList.get(3).addActionListener(mal);
    }

    void addExitListener(ActionListener mal) {
        menuButtonList.get(4).addActionListener(mal);
    }

    void addDrawImageListener(ActionListener mal) {
        menuButtonList.get(5).addActionListener(mal);
    }

    void addBackToMenuListener(ActionListener mal) {
        backToMenuButton.addActionListener(mal);
    }
}
