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
    public static void main(String[] args) throws InterruptedException {
        GameData gd = new GameData(new Point(30,20));
        NetworkServer ns = new NetworkServer(1);
        IPlayer[] players = ns.getPlayers();
        //wait until all clients are connected
        boolean allReady; 
        do {
            allReady = true; 
            for(IPlayer p:players) 
            {
                allReady &= (!(p.getState()==ConnectionState.NOTREADY));
            }
            Thread.sleep(100);
        } while(!allReady);
        gd.startGame(1);
        while(true) {
            for(int p=0; p<players.length; p++) 
            {
                Direction k = players[p].getChangedKey();
                if(k!=null)
                    gd.setSnakeDirection(p, k);
            }
            Thread.sleep(500);
            gd.playTurn();
            for(int p=0; p<players.length; p++) 
            {
                players[p].updateGameData(gd);
            }
        }
    }
}