/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;

import multisnakeglobal.IPlayer;
import multisnakeglobal.NetworkClient;

/**
 *
 * @author hanse
 */
public class NetworkServer {
    private NetworkClient[] players;
    private Thread serverThread;
    
    public NetworkServer(int numPlayers) {
        this(numPlayers, 1234);
    }
    public NetworkServer(int numPlayers, int port){
        players = new NetworkClient[numPlayers];
        for(int i=0;i<numPlayers; i++) {players[i] = new NetworkClient();}
        serverThread = new Thread(new NetworkServerThread(port, players));
        serverThread.start();
    }
 
    public IPlayer[] getPlayers() {    
        return players;
    }
    
    public void endGame() {
        for(NetworkClient p: players) {
            p.disconnect();
        }
        serverThread=null;
    }
    
    
}
