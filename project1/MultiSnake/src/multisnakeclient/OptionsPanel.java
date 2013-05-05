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
    private JComboBox lstRes;
    private JTextField txtLength;
    private JTextField txtNick;

    public OptionsPanel(MainFrame parentFrame) {
    	super();
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);
        this.setLayout(new BorderLayout());
        
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JPanel pnlOptions = new JPanel (new GridLayout(0,2,0,10));        
        
        // create Options
        JLabel lblRes = new JLabel("Window Resolution");
        lstRes = new JComboBox(parentFrame.getOptions().getRESLIST());
        lstRes.setSelectedItem(new Point(parentFrame.getOptions().getWindowWidth(),parentFrame.getOptions().getWindowHeight()));
        
        JLabel lblLength = new JLabel("Max Tile Length");
        txtLength = new JTextField(10);
        txtLength.setText(parentFrame.getOptions().getMaxParcelLength()+"");
        
        JLabel lblNick = new JLabel("Nickname");
        txtNick = new JTextField(50);
        txtNick.setText(parentFrame.getOptions().getNickname());
        
        pnlOptions.add(lblRes);
        pnlOptions.add(lstRes);
        pnlOptions.add(lblLength);
        pnlOptions.add(txtLength);
        pnlOptions.add(lblNick);
        pnlOptions.add(txtNick);
        
        pnlOptions.setBorder(BorderFactory.createEmptyBorder(20,50,0,50));

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
        this.add(pnlOptions,BorderLayout.PAGE_START);
        this.add(btnPnl,BorderLayout.PAGE_END);
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if (com.equals("")) {
        } else if (com.equals("Save")) {
        	Point res = (Point)lstRes.getSelectedItem();
            parentFrame.getOptions().saveOptions(Integer.parseInt(txtLength.getText()),(int)res.getX(),(int)res.getY(),txtNick.getText());
            JOptionPane.showMessageDialog(parentFrame, "Options saved! Please restart to take effect!");
        } else if (com.equals("Back")) {
        	parentFrame.drawPanel("MainMenu");
        } else {
            
        }
    }
}
