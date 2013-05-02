/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import multisnakeglobal.*;
import multisnakeglobal.ConnectionState;
import multisnakeglobal.GameData;
import multisnakeglobal.IGameData;
import multisnakeglobal.IPlayer;
import multisnakeglobal.KeyChange;
import multisnakeglobal.KeyChangedMessage;
import multisnakeglobal.Point;
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
    private Socket connection=null;
    private String host;
    private int port;
    private ObjectOutputStream out = null;
    
    public NetworkClient(String host, int port){
        super();
        this.host = host;
        this.port = port;
        this.gd = new GameData(new Point(1,1));
        t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        try {
            connection = new Socket(host, port);
            state = ConnectionState.READY;
            super.setChanged();
            super.notifyObservers();
            this.out = new ObjectOutputStream(connection.getOutputStream());
            this.out.flush();
            ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                while(true) {
                    INetworkMessage m = (INetworkMessage)in.readObject();
                    m.accept(this);
                    super.setChanged();
                    super.notifyObservers(); 
                }
        } catch(IOException e) {
            disconnect();
        } catch(ClassNotFoundException e) {
            disconnect();
        }
        
    }
    
    private void disconnect() {
            state  = ConnectionState.DISCONNECTED;
            connection = null;
            super.setChanged();
            super.notifyObservers(); 
    }
    private void sendMessage(INetworkMessage m) {
        if(out!=null) {
            try {
                out.writeObject(m);
                System.out.println("foo");
            }
            catch(IOException e) { disconnect();}
        }
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
        if(this.connection!=null) {sendMessage(new KeyChangedMessage(k));}
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
        this.id = ms.id;
    }

    @Override
    public void visit(UpdateGameDataMessage ms) {
        this.gd = ms.gameData;
    }

}
