/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author max
 */

/* THIS IS ONLY A TEST FILE. NOT PART OF PROJECT!! */

public class MainMenuWindow extends JFrame {
    
    private Dimension defaultWindowSize;
    private ArrayList<JButton> menuButtonList;

    public MainMenuWindow(String title,int defaultWidth, int defaultHeight) {
        super(title);
        defaultWindowSize = new Dimension(defaultWidth,defaultHeight);
        // set standard attributes
        this.setSize(defaultWindowSize);
        this.setLocation(100,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        // create main menu
        JPanel mainMenu = createMenuPanel();
        
        // add menupanel to the frame
        this.add(mainMenu);
    }
    
    // create and returns a panel containing the main menu buttons
    private JPanel createMenuPanel() {
        // create a panel
        JPanel menuPanel = new JPanel();
        menuPanel.setSize(defaultWindowSize);
        
        // init buttons
        menuButtonList = new ArrayList<JButton>();
        menuButtonList.add(new JButton("Start New Server"));
        menuButtonList.add(new JButton("Join Game"));
        menuButtonList.add(new JButton("Options"));
        menuButtonList.add(new JButton("Help"));
        menuButtonList.add(new JButton("Exit"));
        
        //place buttons on manupanel
        int numberOfButtons = menuButtonList.size();
        menuPanel.setLayout(null);
        int width = 150;
        int height = 30;
        double y = ((defaultWindowSize.getHeight()-100)/(2*numberOfButtons))-height+50;
        for (JButton button : menuButtonList) {
            double x = (defaultWindowSize.getWidth()-width)/2;
            button.setBounds((int) x,(int) y,width,height);
            menuPanel.add(button);
            y += (defaultWindowSize.getHeight()-100)/numberOfButtons;
        }
        
        // event listeners for buttons
        menuButtonList.get(0).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerButtonActionPerformed(evt);
            }
        });
        menuButtonList.get(1).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinGameButtonActionPerformed(evt);
            }
        });
        menuButtonList.get(2).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsButtonActionPerformed(evt);
            }
        });
        menuButtonList.get(3).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });
        menuButtonList.get(4).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
        return menuPanel;
    }
    // event methods for buttons
    private void startServerButtonActionPerformed(java.awt.event.ActionEvent evt) {
    }
    
    private void joinGameButtonActionPerformed(java.awt.event.ActionEvent evt) {
    }
    
    private void optionsButtonActionPerformed(java.awt.event.ActionEvent evt) {
    }
    
    private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {
    }
    
    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
        System.exit(0);
    }
}
