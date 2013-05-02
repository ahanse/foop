/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.net.Socket;
import java.util.Observable;
import multisnakeglobal.AnnounceNickMessage;
import multisnakeglobal.ConnectionState;
import multisnakeglobal.IGameData;
import multisnakeglobal.IPlayer;
import multisnakeglobal.KeyChange;
import multisnakeglobal.KeyChangedMessage;
import multisnakeglobal.SetIdMessage;
import multisnakeglobal.UpdateGameDataMessage;

/**
 *
 * @author hanse
 */
public class NetworkClient extends Observable implements Runnable, IPlayer {

    private Thread t = null;
    private IGameData gd; 
    private ConnectionState state=ConnectionState.NOTREADY;
    private int id=0;
    private String nick="no nick";
    private Socket socket=null;
    private String host;
    private int port;
    
    public NetworkClient(String host, int port){
        super();
        this.host = host;
        this.port = port;
        t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        /*try {
            socket = new Socket(host, port);
        } */  
    }

    @Override
    public ConnectionState getStatus() {
        return this.state;
    }

    @Override
    public String getNick() {
        return this.nick;
    }

    @Override
    public KeyChange getChangedKey() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setChangedKey(KeyChange k) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void updateGameData(IGameData gd) {
        this.gd = gd;
    }

    @Override
    public IGameData getGameData() {
        return this.gd;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void visit(AnnounceNickMessage ms) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(KeyChangedMessage ms) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(SetIdMessage ms) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void visit(UpdateGameDataMessage ms) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
