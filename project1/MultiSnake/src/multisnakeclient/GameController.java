/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author max
 */

/* this class will be part of a MVC design. The controller coordinates 
 * view and model */
public class GameController {

    private Thread t = null;
    private Boolean threadSuspended = false;
    private GameModel model;
    private GameView view;
    //private double times = 0;
    //private int counter = 0;

    public GameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
        addListener();
    }

    private void addListener() {
        // add listeners for view
        this.view.addStartServerListener(new StartServerListener());
        this.view.addJoinGameListener(new JoinGameListener());
        this.view.addOptionsListener(new OptionsListener());
        this.view.addHelpListener(new HelpListener());
        this.view.addExitListener(new ExitListener());
        this.view.addDrawImageListener(new DrawImageListener());
        this.view.addBackToMenuListener(new BackToMenuListener());
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
            model.setWindowWidth(1000);
            model.setWindowHeight(500);
            model.setParcelLength(20);
            model.saveOptions();
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

    class BackToMenuListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            t = null;
            view.viewMainMenu();
        }
    }

    // image testing - ignore this
    class DrawImageListener implements ActionListener, Runnable {

        @Override
        public void actionPerformed(ActionEvent e) {
            // create Thread for updating the image
            t = new Thread(this);
            t.start();
        }

        @Override
        // the method called by start thread
        public void run() {
            // construct to handle a stop following 
            // http://docs.oracle.com/javase/1.4.2/docs/guide/misc/threadPrimitiveDeprecation.html
            Thread thisThread = Thread.currentThread();
            while (t == thisThread) {
                try {
                    long start = System.nanoTime();
                    model.simulateData(20, 20);
                    view.drawImage();
                    long elapsedTime2 = (System.nanoTime() - start);
                    // delay to bitrate milliseconds
                    int bitrate = 20;
                    Thread.sleep(Math.max(0, bitrate - (int) (elapsedTime2 / 1000000)));
                    long elapsedTime = (System.nanoTime() - start);
                    //System.out.printf("Time taken: " + ((double) elapsedTime) / (1000000000) + " s\n");
                    synchronized (this) {
                        while (threadSuspended && t == thisThread) {
                            wait();
                        }
                    }
                } catch (InterruptedException e) {
                }
            }
        }
    }
}
