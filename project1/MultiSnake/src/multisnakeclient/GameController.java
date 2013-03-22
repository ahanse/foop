/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author max
 */

 /* this class will be part of a MVC design. The controller coordinates 
  * view and model */
public class GameController {
    private GameModel model;
    private GameView view;
        
    public GameController(GameModel model,GameView view) {
        this.model = model;
        this.view = view;

        // add listeners for view
        view.addStartServerListener(new StartServerListener());
        view.addJoinGameListener(new JoinGameListener());
        view.addOptionsListener(new OptionsListener());
        view.addHelpListener(new HelpListener());
        view.addExitListener(new ExitListener());
        // image testing - ignore this
        view.addDrawImageListener(new DrawImageListener());
        view.addBackToMenuListener(new BackToMenuListener());
    }
    
    class StartServerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.viewNewServer();
        }
    }
    
    class JoinGameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.viewJoinGame();
        }
    }
    
    class OptionsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.viewOptions();
        }
    }
    
    class HelpListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.viewHelp();
        }
    }
    
    class ExitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }
    
    // image testing - ignore this
    class DrawImageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            /*model.setWindowWidth(1000);
            model.setWindowHeight(500);
            model.setParcelLength(20);
            model.saveOptions("options.dat");*/
            // create a matrix of n*m random rectangles using the methods of model
            // they seem to be pretty inefficient overall ^^
            // to transport the color ints instead of BufferedImages seems better
            // should be easy to modify the function in model to this 
            int n = 20; int m = 30;
            java.util.Random generator = new java.util.Random();
            java.awt.image.BufferedImage[][] imgMatrix = new java.awt.image.BufferedImage[n][m];
            for(int i=0;i<n;i++) {
                for(int j=0;j<m;j++) {
                    imgMatrix[i][j] = model.createOneColorRectangle(model.componentToARGB(generator.nextInt(255),generator.nextInt(255),generator.nextInt(255),255),1,model.componentToARGB(0,0,0,255));
                }
            }
            view.drawImage(model.createGameBoard(imgMatrix),50,50);
        }
    }
    
    class BackToMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.viewMainMenu();
        }
    }
}
