/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Benedikt
 */
public class MainMenuPanel extends JPanel implements ActionListener {

    private ArrayList<JButton> menuButtonList;
    private MainFrame parentFrame;

    public MainMenuPanel(MainFrame parentFrame) {
    	super();
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);

        menuButtonList = new ArrayList<JButton>();
        JButton tmp = new JButton("Start New Server");
        tmp.setActionCommand("NewServer");
        menuButtonList.add(tmp);

        tmp = new JButton("Join Game");
        tmp.setActionCommand("JoinServer");
        menuButtonList.add(tmp);

        tmp = new JButton("Options");
        tmp.setActionCommand("Options");
        menuButtonList.add(tmp);

        tmp = new JButton("Help");
        tmp.setActionCommand("Help");
        menuButtonList.add(tmp);

        tmp = new JButton("Exit");
        tmp.setActionCommand("Exit");
        menuButtonList.add(tmp);

        tmp = new JButton("Game Panel");
        tmp.setActionCommand("GamePanel");
        menuButtonList.add(tmp);

        //place buttons on menupanel
        int numberOfButtons = menuButtonList.size();
        this.setLayout(null);
        int width = 150;
        int height = 30;
        double y = ((windowSize.getHeight() - 100) / (2 * numberOfButtons)) - height + 50;
        for (JButton button : menuButtonList) {
            button.addActionListener(this);
            double x = (windowSize.getWidth() - width) / 2;
            button.setBounds((int) x, (int) y, width, height);
            this.add(button);
            y += (windowSize.getHeight() - 100) / numberOfButtons;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if (com.equals("")) {
        } else if (com.equals("Exit")) {
            System.exit(0);
        } else {
            parentFrame.drawPanel(com);
        }
    }
}
