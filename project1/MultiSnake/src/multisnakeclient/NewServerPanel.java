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
public class NewServerPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private ArrayList<JButton> menuButtonList;
    private JTextField tickTFld;
    private JTextField boardXTFld;
    private JTextField boardYTFld;

    public NewServerPanel(MainFrame parentFrame) {
    	super();
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);
        this.setLayout(new BorderLayout());
        
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JPanel optPnl = new JPanel (new GridLayout(0,2,0,10));        
        
        // create Options
        JLabel tickLbl = new JLabel("Game tick(ms)");
        tickTFld = new JTextField(10);
        tickTFld.setText("1000");
        JLabel dimXLbl = new JLabel("Game Board X");
        boardXTFld = new JTextField(10);
        boardXTFld.setText("30");
        JLabel dimYLbl = new JLabel("Game Board Y");
        boardYTFld = new JTextField(10);
        boardYTFld.setText("30");
        
        optPnl.add(tickLbl);
        optPnl.add(tickTFld);
        optPnl.add(dimXLbl);
        optPnl.add(boardXTFld);
        optPnl.add(dimYLbl);
        optPnl.add(boardYTFld);
        
        optPnl.setBorder(BorderFactory.createEmptyBorder(20,50,0,50));

        // create Buttons
        menuButtonList = new ArrayList<JButton>();
        //Button save Options
        JButton tmp = new JButton("Start New Server");
        tmp.setActionCommand("Start");
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
        } else if (com.equals("Start")) {
            if(!inputCorrect()){
                JOptionPane.showMessageDialog(this,"Einer der eingegebenen Werte stellt keinen gültigen Wert dar.","Fehler!",JOptionPane.OK_OPTION);
            }
            else{
                
            }
            
        } else if (com.equals("Back")) {
        	parentFrame.drawPanel("MainMenu");
        } else {
            
        }
    }
    
    private Boolean inputCorrect()
    {
        int tick;
        try{
            tick=Integer.parseInt(tickTFld.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(tick<=0){
            return false;
        }
        int xCoord;
        try{
            xCoord=Integer.parseInt(boardXTFld.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(xCoord<=0)
        {
            return false;
        }
        
        int yCoord;
        try{
            yCoord=Integer.parseInt(boardXTFld.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(yCoord<=0)
        {
            return false;
        }
        
        return true;
    }
}
