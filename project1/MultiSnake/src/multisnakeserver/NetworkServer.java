/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;

import multisnakeglobal.IPlayer;

/**
 *
 * @author hanse
 */
public class NetworkServer {
    private NetworkPlayer[] players;
    private Thread serverThread;
    
    public NetworkServer(int numPlayers) {
        this(numPlayers, 1234);
    }
    public NetworkServer(int numPlayers, int port){
        players = new NetworkPlayer[numPlayers];
        for(int i=0;i<numPlayers; i++) {players[i] = new NetworkPlayer();}
        serverThread = new Thread(new NetworkServerThread(port, players));
        serverThread.start();
    }
 
    public IPlayer[] getPlayers() {    
        return players;
    }
    
    public void endGame() {
        for(NetworkPlayer p: players) {
            p.dissconnect();
        }
    }
    
    
}
