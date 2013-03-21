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
        System.out.println("Ich bin der Client!");
        // create menu
        MainMenuWindow menu = new MainMenuWindow("MultiSnake TestGUI Client",400,400);
        // make it visible
        menu.setVisible(true);
    }
}
