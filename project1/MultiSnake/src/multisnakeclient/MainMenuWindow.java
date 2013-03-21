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
public class MainMenuWindow extends JFrame {
    
    private Dimension defaultWindowSize;
    private ArrayList<JButton> buttonList;

    public MainMenuWindow(String title,int defaultWidth, int defaultHeight) {
        super(title);
        defaultWindowSize = new Dimension(defaultWidth,defaultHeight);
        // set standard attributes
        this.setSize(defaultWindowSize);
        this.setLocation(100,100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        // create a panel
        JPanel jpanel = new JPanel();
        jpanel.setSize(defaultWindowSize);
        
        // init buttons
        buttonList = new ArrayList<JButton>();
        buttonList.add(new JButton("Start New Server"));
        buttonList.add(new JButton("Join Game"));
        buttonList.add(new JButton("Options"));
        buttonList.add(new JButton("Help"));
        buttonList.add(new JButton("Exit"));
        
        // add panel to the frame
        this.add(jpanel);
        
        //place buttons on panel
        int numberOfButtons = buttonList.size();
        jpanel.setLayout(null);
        int width = 150;
        int height = 30;
        double y = ((defaultWindowSize.getHeight()-100)/(2*numberOfButtons))-height+50;
        for (JButton button : buttonList) {
            double x = (defaultWindowSize.getWidth()-width)/2;
            button.setBounds((int) x,(int) y,width,height);
            jpanel.add(button);
            y += (defaultWindowSize.getHeight()-100)/numberOfButtons;
        }
        
        // event listeners for buttons
        buttonList.get(0).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startServerButtonActionPerformed(evt);
            }
        });
        buttonList.get(1).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinGameButtonActionPerformed(evt);
            }
        });
        buttonList.get(2).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsButtonActionPerformed(evt);
            }
        });
        buttonList.get(3).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });
        buttonList.get(4).addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });
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
