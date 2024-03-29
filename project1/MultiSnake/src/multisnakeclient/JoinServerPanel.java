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
public class JoinServerPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;
    private ArrayList<JButton> menuButtonList;
    private JTextField txtIP;
    private JTextField txtPort;

    //Constructor getting the parent frame
    public JoinServerPanel(MainFrame parentFrame) {
    	super();
        
        //setting panel properties and adding all needed components
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);
        this.setLayout(new BorderLayout());
        
        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JPanel optPnl = new JPanel (new GridLayout(0,2,0,10));        
        
        String[] joinServerOptions = parentFrame.readSettings("joptions.dat");
        // create Options
        JLabel lblIP = new JLabel("Server IP:");
        txtIP = new JTextField(10);
        txtIP.setText(joinServerOptions[0]);
        JLabel lblPort = new JLabel("Server Port:");
        txtPort = new JTextField(10);
        txtPort.setText(joinServerOptions[1]);
        
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
    

    //Event handler for buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if (com.equals("")) {
        } else if (com.equals("Join")) {
            if(!inputCorrect()){
                JOptionPane.showMessageDialog(this,"Einer der eingegebenen Werte stellt keinen gültigen Wert dar.","Fehler!",JOptionPane.OK_OPTION);
            }
            else{
                parentFrame.saveSettings("joptions.dat",new String[]{txtIP.getText(),txtPort.getText()});
                parentFrame.connectToServer(txtIP.getText(),Integer.parseInt(txtPort.getText()));
                parentFrame.drawPanel("GamePanel");
            }
        } else if (com.equals("Back")) {
        	parentFrame.drawPanel("MainMenu");
        } else {
            
        }
    }
    
    //checks for correct port input
    private Boolean inputCorrect()
    {
        int port;
        try{
            port=Integer.parseInt(txtPort.getText());
        }
        catch(NumberFormatException ex){
            return false;
        }
        if(port<=0){
            return false;
        }
        return true;
    }
}
