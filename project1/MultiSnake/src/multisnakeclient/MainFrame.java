/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import multisnakeglobal.NetworkClient;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.*;
import multisnakeglobal.Direction;
import multisnakeserver.MultiSnakeServer;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
    
    public void connectToServer(String IP, int Port)
    {
        network=new NetworkClient();
        try {
            network.addObserver(gamePanel);
            network.connect(IP, Port);
            Thread.sleep(50);
            network.setNick(options.getNickname());
            
        } catch (IOException e) {
            //TODO: Error window!
            System.err.println("Could not connect!");
        } catch (InterruptedException e) {
            //TODO: Error window!
            System.err.println("Could not connect!");
        }
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
        int[] directionKeys = options.getDirectionKeys();
        int keycode = e.getKeyCode();
        if(keycode == directionKeys[0])
            key = Direction.LEFT;
        else if (keycode == directionKeys[1])
            key = Direction.UP;
        else if (keycode == directionKeys[2])
            key = Direction.RIGHT;
        else if (keycode == directionKeys[3])
            key = Direction.DOWN;
        else
            key = null;
        if (network != null) {
            network.setChangedKey(key);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void run() {
        //Thread thisThread = Thread.currentThread();
        //while(serverThread!=thisThread)
        try {
            MultiSnakeServer.main(soptions);
            network.disconnect();
        } catch (InterruptedException iex) {
            throw new RuntimeException("Interrupted",iex);
        }
    }

    // start multisnakeserver in a new thread
    void startServer(String[] opt) {
        soptions = opt;
        saveServerSettings();
        if (true) {
            serverThread = new Thread(this);
            serverThread.start();
        }            
    }
    
    private void saveServerSettings() {
        try {
            //open a file to write to
            FileOutputStream saveFile = new FileOutputStream("sOptions.dat");
            //create an ObjectOutputStream to put objects into save file
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            // write on Stream
            save.writeObject(this.soptions);
            //close file
            save.close();
        } catch (Exception e) {
        }
    }
    
    public String[] readServerSettings() {
        try {
            //open file to read from
            FileInputStream saveFile = new FileInputStream("sOptions.dat");
            //create an ObjectInputStream to get objects from save file
            ObjectInputStream save = new ObjectInputStream(saveFile);
            this.soptions = (String[]) save.readObject();
            return this.soptions;
        } catch (Exception e) {
            String[] standard = {"200","30","30","1","0","120"};
            return standard;
        }
    }
}
