/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;

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
        serverThread = new Thread(new NetworkServerThread(port, players));
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
