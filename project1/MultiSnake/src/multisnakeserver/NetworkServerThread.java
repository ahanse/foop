/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;

import java.io.*;
import java.net.*;
import multisnakeglobal.ConnectionState;
import multisnakeglobal.NetworkClient;

/**
 *
 * @author hanse
 */
public class NetworkServerThread implements Runnable {

    private int port;
    private NetworkClient[] players;
    private ServerSocket server;
    private boolean running = true; 
    
    public NetworkServerThread(int port, NetworkClient[] players) {
        this.players = players;
        this.port = port;
    }
    
    private NetworkClient getFirstNOTREADYPlayer() {
        for(NetworkClient p : players) {
            if(p.getState() == ConnectionState.NOTREADY)
                return p;
        }
        return null;
    }
    
    public synchronized void close() throws IOException {
        if(server!=null) {
            server.close();
            this.server=null;
        }
    }
    
    @Override
    public void run() {
        try {
            server = new ServerSocket(port);
            while(this.server!=null) {
                Socket clientSocket = server.accept();
                System.out.println("Client connected to server.");
                NetworkClient nrp = getFirstNOTREADYPlayer();
                if(nrp==null) {
                    System.out.println("No free player slots.");
                    clientSocket.close();
                }
                else {
                    nrp.connect(clientSocket);
                }
            }
        } catch(IOException e) {
            System.err.printf("Could not listen on port %d.", port);
        }
        
    }
    
}
