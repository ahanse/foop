/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;
import multisnakeglobal.*;

/**
 *
 * @author hanse
 */
public class MultiSnakeServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GameData gd = new GameData(new Point(20,20));
        NetworkServer ns = new NetworkServer(2);
        while(true) {
        }
    }
}