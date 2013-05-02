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
/**
 *
 * @author Benedikt
 */
public class OptionsPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private ArrayList<JButton> menuButtonList;
    private JComboBox resList;
    private JTextField lngthTFld;

    public OptionsPanel(MainFrame parentFrame) {
    	super();
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);
        this.setLayout(new BorderLayout());
        
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JPanel optPnl = new JPanel (new GridLayout(0,2,0,10));        
        
        // create Options
        JLabel resLbl = new JLabel("Window Resolution");
        resList = new JComboBox(parentFrame.getOptions().getRESLIST());
        resList.setSelectedItem(new Point(parentFrame.getOptions().getWindowWidth(),parentFrame.getOptions().getWindowHeight()));
        JLabel lngtLbl = new JLabel("Max Tile Length");
        lngthTFld = new JTextField(10);
        lngthTFld.setText(parentFrame.getOptions().getMaxParcelLength()+"");
        
        optPnl.add(resLbl);
        optPnl.add(resList);
        optPnl.add(lngtLbl);
        optPnl.add(lngthTFld);
        
        optPnl.setBorder(BorderFactory.createEmptyBorder(20,50,0,50));

        // create Buttons
        menuButtonList = new ArrayList<JButton>();
        //Button save Options
        JButton tmp = new JButton("Save Settings");
        tmp.setActionCommand("Save");
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
        } else if (com.equals("Save")) {
        	Point res = (Point)resList.getSelectedItem();
            parentFrame.getOptions().saveOptions(Integer.parseInt(lngthTFld.getText()),(int)res.getX(),(int)res.getY());
            JOptionPane.showMessageDialog(parentFrame, "Options saved! Please restart to take effect!");
        } else if (com.equals("Back")) {
        	parentFrame.drawPanel("MainMenu");
        } else {
            
        }
    }
}
