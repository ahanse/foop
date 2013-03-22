/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.Color;
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
            view.viewDrawImage(Color.BLUE,Color.RED,300,300);
        }
    }
    
    class BackToMenuListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.viewMainMenu();
        }
    }
}
