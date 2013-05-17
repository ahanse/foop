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
/**
 *
 * @author Benedikt
 */
public class NewServerPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private ArrayList<JButton> menuButtonList;
    private JTextField txtTick;
    private JTextField txtDimX;
    private JTextField txtDimY;
    private JTextField txtNumOfPlayers;
    private JTextField txtNumOfAi;

    public NewServerPanel(MainFrame parentFrame) {
    	super();
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);
        this.setLayout(new BorderLayout());
        
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JPanel optPnl = new JPanel (new GridLayout(0,2,0,10));        
        
        // create Options
        JLabel lblTick = new JLabel("Game tick(ms)");
        txtTick = new JTextField(10);
        txtTick.setText("200");
        JLabel lblDimX = new JLabel("Game Board X");
        txtDimX = new JTextField(10);
        txtDimX.setText("30");
        JLabel lblDimY = new JLabel("Game Board Y");
        txtDimY = new JTextField(10);
        txtDimY.setText("30");
        JLabel lblNumOfPlayers = new JLabel("Number of Players (Humans)");
        txtNumOfPlayers = new JTextField(10);
        txtNumOfPlayers.setText("1");
        JLabel lblNumOfAi = new JLabel("Number of AI");
        txtNumOfAi = new JTextField(10);
        txtNumOfAi.setText("0");
        
        optPnl.add(lblTick);
        optPnl.add(txtTick);
        optPnl.add(lblDimX);
        optPnl.add(txtDimX);
        optPnl.add(lblDimY);
        optPnl.add(txtDimY);
        optPnl.add(lblNumOfPlayers);
        optPnl.add(txtNumOfPlayers);
        optPnl.add(lblNumOfAi);
        optPnl.add(txtNumOfAi);
        
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
                String[] options={txtTick.getText(),txtDimX.getText(),txtDimX.getText(),txtNumOfPlayers.getText(),txtNumOfAi.getText()};
                parentFrame.startServer(options);
                
                //TODO: own IP and port
                parentFrame.connectToServer("127.0.0.1", 1234);
                parentFrame.drawPanel("GamePanel");
                
                //wieso sollte man direkt zum Server connecten? Dann ist der eigene Platz schon besetzt, wenn man wirklich das Spiel joinen will über das Join Game Panel
                //parentFrame.ConnectToServer("127.0.0.1", 1234);
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
            tick=Integer.parseInt(txtTick.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(tick<=0){
            return false;
        }
        int xCoord;
        try{
            xCoord=Integer.parseInt(txtDimX.getText());
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
            yCoord=Integer.parseInt(txtDimX.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(yCoord<=0)
        {
            return false;
        }
        
        int numOfPlayers;
        try{
            numOfPlayers=Integer.parseInt(txtNumOfPlayers.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(numOfPlayers<=0)
        {
            return false;
        }
        
        int numOfAi;
        try{
            numOfAi=Integer.parseInt(txtNumOfAi.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(numOfAi<0)
        {
            return false;
        }
        
        return true;
    }
}
