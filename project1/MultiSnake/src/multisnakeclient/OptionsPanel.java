/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Benedikt
 */
public class OptionsPanel extends JPanel implements ActionListener {

    private MainFrame parentFrame;

    public OptionsPanel(MainFrame parentFrame) {
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
    }
}
