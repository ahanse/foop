/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
    private JLabel lblRes;
    private JTextField txtLength;
    private JTextField txtNick;
    private JComboBox lstColor;
    private JCheckBox chkFull;
    private JTextField[] txtDirections = new JTextField[4];
    private int[] directionKeys;

    //Constructor adding components for client options
    public OptionsPanel(MainFrame parentFrame) {
        super();
        Dimension windowSize = parentFrame.getSize();
        this.parentFrame = parentFrame;
        this.setSize(windowSize);
        this.setLayout(new BorderLayout());
        
        this.directionKeys = parentFrame.getOptions().getDirectionKeys();

        JPanel btnPnl = new JPanel(new FlowLayout(FlowLayout.TRAILING));

        JPanel pnlOptions = new JPanel(new GridLayout(0, 2, 0, 10));

        // create Options
        lblRes = new JLabel("Window Resolution");
        lstRes = new JComboBox(parentFrame.getOptions().getRESLIST());
        lstRes.setSelectedItem(new Point(parentFrame.getOptions().getWindowWidth(), parentFrame.getOptions().getWindowHeight()));

        JLabel lblLength = new JLabel("Max Tile Length");
        txtLength = new JTextField(10);
        txtLength.setText(parentFrame.getOptions().getMaxParcelLength() + "");

        JLabel lblNick = new JLabel("Nickname");
        txtNick = new JTextField(50);
        txtNick.setText(parentFrame.getOptions().getNickname());

        Color[] colorpool = new Color[parentFrame.getOptions().getCOLORPOOL().length];
        for (int i = 0; i < parentFrame.getOptions().getCOLORPOOL().length; i++) {
            colorpool[i] = new Color(parentFrame.getOptions().getCOLORPOOL()[i]);
        }

        JLabel lblColor = new JLabel("Choose color of own snake");
        lstColor = new JComboBox(colorpool);
        lstColor.setRenderer(new CellColorRenderer());
        lstColor.setSelectedIndex(parentFrame.getOptions().getOwnColorInd());

        JLabel lblFull = new JLabel("");
        chkFull = new JCheckBox("Fullscreen",parentFrame.getOptions().getFullscreen());
        chkFull.setActionCommand("Fullscreentoggle");
        chkFull.addActionListener(this);
        //chkFull.setSelected(parentFrame.getOptions().getFullscreen());

        toggleResolutionList();
        
        pnlOptions.add(lblFull);
        pnlOptions.add(chkFull);
        pnlOptions.add(lblRes);
        pnlOptions.add(lstRes);
        pnlOptions.add(lblLength);
        pnlOptions.add(txtLength);
        pnlOptions.add(lblNick);
        pnlOptions.add(txtNick);
        pnlOptions.add(lblColor);
        pnlOptions.add(lstColor);
        
        JLabel[] lblDirections = new JLabel[4];
        
        lblDirections[0] = new JLabel("Left Key");
        JTextField dirtmp = new JTextField(10);
        dirtmp.setText(KeyEvent.getKeyText(directionKeys[0]));
        txtDirections[0] = dirtmp;
        
        lblDirections[1] = new JLabel("Up Key");
        dirtmp = new JTextField(10);
        dirtmp.setText(KeyEvent.getKeyText(directionKeys[1]));
        txtDirections[1] = dirtmp;
        
        lblDirections[2] = new JLabel("Right Key");
        dirtmp = new JTextField(10);
        dirtmp.setText(KeyEvent.getKeyText(directionKeys[2]));
        txtDirections[2] = dirtmp;
        
        lblDirections[3] = new JLabel("Down Key");
        dirtmp = new JTextField(10);
        dirtmp.setText(KeyEvent.getKeyText(directionKeys[3]));
        txtDirections[3] = dirtmp; 
        
        txtDirections[0].addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keycode = e.getKeyCode();
                txtDirections[0].setText(KeyEvent.getKeyText(keycode));
                directionKeys[0]=keycode;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO: Do something for the keyTyped event
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO: Do something for the keyPressed event
            }
        });
        
        txtDirections[1].addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keycode = e.getKeyCode();
                txtDirections[1].setText(KeyEvent.getKeyText(keycode));
                directionKeys[1]=keycode;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO: Do something for the keyTyped event
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO: Do something for the keyPressed event
            }
        });
        
        txtDirections[2].addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keycode = e.getKeyCode();
                txtDirections[2].setText(KeyEvent.getKeyText(keycode));
                directionKeys[2]=keycode;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO: Do something for the keyTyped event
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO: Do something for the keyPressed event
            }
        });
        
        txtDirections[3].addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                int keycode = e.getKeyCode();
                txtDirections[3].setText(KeyEvent.getKeyText(keycode));
                directionKeys[3]=keycode;
            }

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO: Do something for the keyTyped event
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO: Do something for the keyPressed event
            }
        });
        
        for(int i=0;i<4;i++) {
            pnlOptions.add(lblDirections[i]);
            pnlOptions.add(txtDirections[i]);
        }

        pnlOptions.setBorder(BorderFactory.createEmptyBorder(20, 50, 0, 50));

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
        this.add(pnlOptions, BorderLayout.PAGE_START);
        this.add(btnPnl, BorderLayout.PAGE_END);
    }

    // displays resultion list iff full screen is disabled
    private void toggleResolutionList() {
        if (chkFull.isSelected()) {
            lblRes.setVisible(false);
            lstRes.setVisible(false);
        } else {
            lblRes.setVisible(true);
            lstRes.setVisible(true);
        }
    }

    // displays colors colored
    private class CellColorRenderer extends JLabel implements ListCellRenderer {

        public CellColorRenderer() {
            setOpaque(true);
        }

        @Override
        public void setBackground(Color col) {
        }

        public void setMyBackground(Color col) {
            super.setBackground(col);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            setText(" ");
            setBorder(BorderFactory.createLineBorder(Color.black));
            setMyBackground((Color) value);
            setForeground((Color) value);
            return this;
        }
    }

    //action listener for buttons and full screen toggle
    @Override
    public void actionPerformed(ActionEvent e) {
        String com = e.getActionCommand();
        if (com.equals("")) {
        } else if (com.equals("Save")) {
            Point res = (Point) lstRes.getSelectedItem();
            parentFrame.getOptions().saveOptions(Integer.parseInt(txtLength.getText()), (int) res.getX(), (int) res.getY(), txtNick.getText(), lstColor.getSelectedIndex(), chkFull.isSelected(),this.directionKeys);
            JOptionPane.showMessageDialog(parentFrame, "Options saved! Please restart to take effect!");
        } else if (com.equals("Back")) {
            parentFrame.drawPanel("MainMenu");
        } else if (com.equals("Fullscreentoggle")) {
            toggleResolutionList();
        } else {
        }
    }
}
