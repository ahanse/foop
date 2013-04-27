/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import multisnakeglobal.KeyChange;

/**
 *
 * @author Benedikt
 */
public final class MainFrame extends JFrame implements KeyListener{
    
    private JPanel mainPanel;
    private Options options;
    private DummyNetwork network;
    
    public MainFrame()
    {
        options=new Options();
        this.setTitle("MultiSnake Client GUI Test");
        this.setSize(new Dimension(options.getWindowWidth(), options.getWindowHeight()));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        mainPanel=new JPanel(new CardLayout());
        this.add(mainPanel);
        network=new DummyNetwork();
        
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
    }

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
        KeyChange key;
        switch(e.getKeyCode())
        {
            case 37:
                key=KeyChange.LEFT;
                break;
            case 38:
                key=KeyChange.UP;
                break;
            case 39:
                key=KeyChange.RIGHT;
                break;
            case 40:
                key=KeyChange.DOWN;
                break;
            default:
                key=null;
                break;
        }
        network.setKeyChange(key);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }
    
}
