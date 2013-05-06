/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

/**
 *
 * @author hanse
 */
public class MultiSnakeClient {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // create new Frame
        MainFrame main=new MainFrame();
        // set visible, add elements and open main menu
        main.setVisible(true);
        main.addPanels();
        main.drawPanel("MainMenu");
    }
}
