/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;

import java.io.*;
import java.net.*;
import multisnakeglobal.ConnectionState;

/**
 *
 * @author hanse
 */
public class NetworkServerThread implements Runnable {

    private int port;
    private NetworkPlayer[] players;
    private ServerSocket server;
    
    public NetworkServerThread(int port, NetworkPlayer[] players) {
        this.players = players;
        this.port = port;
    }
    
    private NetworkPlayer getFirstNOTREADYPlayer() {
        for(NetworkPlayer p : players) {
            if(p.getStatus() == ConnectionState.NOTREADY)
                return p;
        }
        return null;
    }
    
    public void close() throws IOException {
        if(server!=null) {
            server.close();
        }
    }
    
    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            while(true) {
                Socket client = server.accept();
                NetworkPlayer nrp = getFirstNOTREADYPlayer();
                if(nrp==null)
                    client.close();
                else {
                    Thread t = new Thread(nrp);
                    t.start();
                }
            }
        } catch(IOException e) {
            System.err.printf("Could not listen on port %d.", port);
        }
        
    }
    
}
