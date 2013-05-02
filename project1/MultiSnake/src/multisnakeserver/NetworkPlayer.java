/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;

import multisnakeglobal.IPlayer;
import java.net.*;
import java.io.*;
import multisnakeglobal.*;

/**
 *
 * @author hanse
 */
public class NetworkPlayer implements IPlayer, Runnable {

    private ConnectionState state = ConnectionState.NOTREADY;
    private String nick = "no nick";
    private Socket connection = null;
    private ObjectOutputStream out = null;
    private KeyChange keyChange = null;
    
    @Override
    public ConnectionState getStatus() {
        return state;
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public KeyChange getChangedKeys() {
        KeyChange k = this.keyChange;
        this.keyChange=null;
        return k;
    }

    @Override
    public void setId(int id) {
        if(state==ConnectionState.READY) sendMessage(new SetIdMessage(id));
    }

    @Override
    public void updateGameData(IGameData gd) {
        if(state==ConnectionState.READY) sendMessage(new UpdateGameDataMessage(gd));
    }

    public void setConnection(Socket connection) {
        this.connection = connection;    
    }
    
    private void handleMessage(AnnounceNickMessage nm) {
        
    }
    
    @Override
    public void run() {
        try {
            if(connection != null) {
                state = ConnectionState.READY;
                ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                while(true) {
                    INetworkMessage m = (INetworkMessage)in.readObject();
                    m.accept(this);
                }
            } 
        } catch(IOException e) {
            dissconnect();
        } catch(ClassNotFoundException e) {
            dissconnect();
        }
    }
    
    public void dissconnect() {
        try{
            connection.close();
        } catch(IOException e) {}
        finally {state = ConnectionState.DISCONNECTED; out=null;}
    }
    
    public void sendMessage(INetworkMessage m) {
        if(out!=null) {
            try {
                out.writeObject(m);
            }
            catch(IOException e) { dissconnect();}
        }
    }

    @Override
    public void visit(AnnounceNickMessage ms) {
        nick = ms.nick;
    }

    @Override
    public void visit(KeyChangedMessage ms) {
        keyChange=ms.keyChange;
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
