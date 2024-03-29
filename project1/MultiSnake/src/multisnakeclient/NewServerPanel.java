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
    private JTextField txtPlaytime;
    private JTextField txtNewPriority;

    // constructor adds server option components
    public NewServerPanel(MainFrame parentFrame) {
    	super();
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);
        this.setLayout(new BorderLayout());
        
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JPanel optPnl = new JPanel (new GridLayout(0,2,0,10));        
        
        String[] savedSettings = parentFrame.readSettings("soptions.dat");
        // create Options
        JLabel lblTick = new JLabel("Game tick(ms)");
        txtTick = new JTextField(10);
        txtTick.setText(savedSettings[0]);
        JLabel lblDimX = new JLabel("Game Board X");
        txtDimX = new JTextField(10);
        txtDimX.setText(savedSettings[1]);
        JLabel lblDimY = new JLabel("Game Board Y");
        txtDimY = new JTextField(10);
        txtDimY.setText(savedSettings[2]);
        JLabel lblNumOfPlayers = new JLabel("Number of Players (Humans)");
        txtNumOfPlayers = new JTextField(10);
        txtNumOfPlayers.setText(savedSettings[3]);
        JLabel lblNumOfAi = new JLabel("Number of AI");
        txtNumOfAi = new JTextField(10);
        txtNumOfAi.setText(savedSettings[4]);
        JLabel lblPlaytime = new JLabel("Playtime in Seconds");
        txtPlaytime = new JTextField(10);
        txtPlaytime.setText(savedSettings[5]);
        JLabel lblNewPriority = new JLabel("Ticks to new Priority");
        txtNewPriority = new JTextField(10);
        txtNewPriority.setText(savedSettings[6]);
        
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
        optPnl.add(lblPlaytime);
        optPnl.add(txtPlaytime);
        optPnl.add(lblNewPriority);
        optPnl.add(txtNewPriority);
        
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
    
    //action listener for buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if (com.equals("")) {
        } else if (com.equals("Start")) {
            if(!inputCorrect()){
                JOptionPane.showMessageDialog(this,"Einer der eingegebenen Werte stellt keinen gültigen Wert dar.","Fehler!",JOptionPane.OK_OPTION);
            }
            else{
                String[] options={txtTick.getText(),txtDimX.getText(),txtDimY.getText(),txtNumOfPlayers.getText(),txtNumOfAi.getText(),txtPlaytime.getText(),txtNewPriority.getText()};
                parentFrame.startServer(options);
                
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
    
    //checks user inputs
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
            yCoord=Integer.parseInt(txtDimY.getText());
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
        
        int playtime;
        try{
            playtime=Integer.parseInt(txtPlaytime.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(playtime<=0)
        {
            return false;
        }
        
        int newPriority;
        try{
            newPriority=Integer.parseInt(txtNewPriority.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(newPriority<=0)
        {
            return false;
        }
        
        return true;
    }
}
