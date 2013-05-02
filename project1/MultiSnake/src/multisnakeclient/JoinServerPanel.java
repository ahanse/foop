/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import multisnakeglobal.Point;
import multisnakeserver.MultiSnakeServer;
/**
 *
 * @author Benedikt
 */
public class JoinServerPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private ArrayList<JButton> menuButtonList;
    private JTextField txtIP;
    private JTextField txtPort;

    public JoinServerPanel(MainFrame parentFrame) {
    	super();
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);
        this.setLayout(new BorderLayout());
        
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JPanel optPnl = new JPanel (new GridLayout(0,2,0,10));        
        
        // create Options
        JLabel lblIP = new JLabel("Server IP:");
        txtIP = new JTextField(10);
        txtIP.setText("");
        JLabel lblPort = new JLabel("Server Port:");
        txtPort = new JTextField(10);
        txtPort.setText("1337");
        
        optPnl.add(lblIP);
        optPnl.add(txtIP);
        optPnl.add(lblPort);
        optPnl.add(txtPort);
        
        optPnl.setBorder(BorderFactory.createEmptyBorder(20,50,0,50));

        // create Buttons
        menuButtonList = new ArrayList<JButton>();
        //Button save Options
        JButton tmp = new JButton("Join Server");
        tmp.setActionCommand("Join");
        menuButtonList.add(tmp);
        
        //Button go back to MainMenu
        tmp = new JButton("Back To Mainmenu");
        tmp.setActionCommand("Back");
        menuButtonList.add(tmp);
        
        //perform actions on buttons
        for (JButton button : menuButtonList) {
        	btnPnl.add(button);
            button.addActionListener(this);
        }
        this.add(optPnl,BorderLayout.PAGE_START);
        this.add(btnPnl,BorderLayout.PAGE_END);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if (com.equals("")) {
        } else if (com.equals("Join")) {
            parentFrame.drawPanel("GamePanel");
        } else if (com.equals("Back")) {
        	parentFrame.drawPanel("MainMenu");
        } else {
            
        }
    }
}