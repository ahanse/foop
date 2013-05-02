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

/**
 *
 * @author Benedikt
 */
public final class MainFrame extends JFrame implements KeyListener{
    
    private JPanel mainPanel;
    private Options options;
    private NetworkClient network;
    
    public MainFrame()
    {
        options=new Options();
        this.setTitle("MultiSnake Client GUI Test");
        //this.setSize(new Dimension(options.getWindowWidth(), options.getWindowHeight()));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        mainPanel=new JPanel(new CardLayout());
        mainPanel.setPreferredSize(new Dimension(options.getWindowWidth(), options.getWindowHeight()));
        this.getContentPane().add(mainPanel,BorderLayout.CENTER);
        network=new NetworkClient("127.0.0.1", 1234);
        this.pack();
        this.setLocationRelativeTo(null);
        
        addPanels();
        drawPanel("MainMenu");
        
        this.addKeyListener(this);
    }
    
    private void addPanels()
    {
        mainPanel.add(new MainMenuPanel(this),"MainMenu");
        mainPanel.add(new OptionsPanel(this),"Options");
        GamePanel panel=new GamePanel(this);
        network.addObserver(panel);
        mainPanel.add(panel,"GamePanel");
    }
    
    public void drawPanel(String panelName)
    {
        CardLayout layout=(CardLayout)mainPanel.getLayout();
        layout.show(mainPanel,panelName);
        drawContent();
        this.requestFocus();
    }

    public void drawContent() {
        this.revalidate();
        this.pack();
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
        switch(e.getKeyCode())
        {
            case 37:
                key=Direction.LEFT;
                break;
            case 38:
                key=Direction.UP;
                break;
            case 39:
                key=Direction.RIGHT;
                break;
            case 40:
                key=Direction.DOWN;
                break;
            default:
                key=null;
                break;
        }
        network.setChangedKey(key);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
