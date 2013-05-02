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
        /*GameModel model = new GameModel();
        GameView view = new GameView(model,model.getWindowWidth(),model.getWindowHeight());
        GameController controller = new GameController(model,view);
        view.setVisible(true);*/
        MainFrame main=new MainFrame();
        main.setVisible(true);
    }
}
