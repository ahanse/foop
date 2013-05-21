/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;

import java.io.IOException;
import multisnakeglobal.IPlayer;
import multisnakeglobal.NetworkClient;

/**
 *
 * @author hanse
 */
public class NetworkServer {
    private NetworkClient[] players;
    private NetworkServerThread serverThread;
    private Thread thread;
    
    public NetworkServer(int numPlayers) {
        this(numPlayers, 1234);
    }
    public NetworkServer(int numPlayers, int port){
        players = new NetworkClient[numPlayers];
        for(int i=0;i<numPlayers; i++) {players[i] = new NetworkClient();}
        serverThread = new NetworkServerThread(port, players);
        thread = new Thread(serverThread);
        thread.start();
    }
 
    public IPlayer[] getPlayers() {    
        return players;
    }
    
    public void endGame() {
        for(NetworkClient p: players) {
            p.disconnect();
        }
        try {
            serverThread.close();
        } catch(IOException e) {}
        thread = null; 
    }
    
    
}
