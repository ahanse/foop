/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import multisnakeglobal.Direction;
import multisnakeserver.MultiSnakeServer;

/**
 *
 * @author Benedikt
 */
public final class MainFrame extends JFrame implements KeyListener, Runnable {

    private JPanel mainPanel;
    private Options options;
    private NetworkClient network;
    private Thread serverThread;
    private GamePanel gamePanel;
    private String[] soptions;

    public MainFrame() {
        options = new Options();
        initiateFrameProperties();
    }
    
    // set key properties of the frame
    public void initiateFrameProperties() {
        this.setTitle("MultiSnake Client GUI Test");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        mainPanel = new JPanel(new CardLayout());
        this.getContentPane().add(mainPanel, BorderLayout.CENTER);
        // detect whether fullscreen frame or not
        if (options.getFullscreen()) {
            this.setUndecorated(true);
            this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        } else {
            mainPanel.setPreferredSize(new Dimension(options.getWindowWidth(), options.getWindowHeight()));
            this.pack();
        }
        this.setLocationRelativeTo(null);
        // key listener for direction keys
        this.addKeyListener(this);
    }
    
    // create networkclient and register observer
    public void connectToServer(String IP, int Port) {
        network = new NetworkClient(IP, Port);
        network.setNick(options.getNickname());
        network.addObserver(gamePanel);
    }

    // panels for each function, named with strings
    public void addPanels() {
        mainPanel.add(new MainMenuPanel(this), "MainMenu");
        mainPanel.add(new OptionsPanel(this), "Options");
        mainPanel.add(new NewServerPanel(this), "NewServer");
        mainPanel.add(new JoinServerPanel(this), "JoinServer");
        gamePanel = new GamePanel(this);
        mainPanel.add(gamePanel, "GamePanel");
    }

    // draw a specific panel, called by a string
    public void drawPanel(String panelName) {
        CardLayout layout = (CardLayout) mainPanel.getLayout();
        layout.show(mainPanel, panelName);
        drawContent();
        this.requestFocus();
    }
    
    // redraw frame content
    public void drawContent() {
        this.revalidate();
        this.repaint();
    }
    
    public Options getOptions() {
        return options;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction key;
        switch (e.getKeyCode()) {
            case 37:
                key = Direction.LEFT;
                break;
            case 38:
                key = Direction.UP;
                break;
            case 39:
                key = Direction.RIGHT;
                break;
            case 40:
                key = Direction.DOWN;
                break;
            default:
                key = null;
                break;
        }
        if (network != null) {
            network.setChangedKey(key);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        try {
            MultiSnakeServer.main(soptions);
        } catch (InterruptedException e) {
            System.out.println("Error!");
        }
    }

    // start multisnakeserver in a new thread
    void startServer(String[] opt) {
        soptions = opt;
        if (serverThread == null) {
            serverThread = new Thread(this);
            serverThread.start();
        }
    }
}
