/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;

import java.io.*;
import java.net.*;
import multisnakeglobal.*;

/**
 *
 * @author hanse
 */
public class NetworkServerPlayer implements IPlayer, Runnable {

    private ConnectionState state = ConnectionState.NOTREADY;
    private String nick = "no nick";
    private Socket connection = null;
    private ObjectOutputStream out = null;
    private Direction keyChange = null;
    private int id;
    
    @Override
    public ConnectionState getState() {
        return state;
    }

    @Override
    public String getNick() {
        return nick;
    }

    @Override
    public Direction getChangedKey() {
        Direction k = this.keyChange;
        this.keyChange=null;
        return k;
    }

    @Override
    public void setId(int id) {
        this.id = id;
        if(state==ConnectionState.READY) sendMessage(new SetIdMessage(id));
    }

    @Override
    public void updateGameData(IGameData gd) {
        if(state==ConnectionState.READY) sendMessage(new UpdateGameDataMessage(gd));
    }

    public void setConnection(Socket connection) {
        this.connection = connection;    
    }
        
    @Override
    public void run() {
        System.out.println("ready steady go!");
        try {
            if(connection != null) {
                state = ConnectionState.READY;
                this.out = new ObjectOutputStream(connection.getOutputStream());
                this.out.flush();
                ObjectInputStream in = new ObjectInputStream(connection.getInputStream());
                System.out.println("foo");
                while(true) {
                    INetworkMessage m = (INetworkMessage)in.readObject();
                    System.out.println("got object");
                    m.accept(this);
                }
            } 
        } catch(IOException e) {
            disconnect();
        } catch(ClassNotFoundException e) {
            disconnect();
        }
    }
    
    public void disconnect() {
        try{
            connection.close();
        } catch(IOException e) {}
        finally {state = ConnectionState.DISCONNECTED; out=null;}
    }
    
    private void sendMessage(INetworkMessage m) {
        if(out!=null) {
            try {
                out.reset();
                out.writeObject(m);
            }
            catch(IOException e) { disconnect();}
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
        throw new UnsupportedOperationException("Not supported for server.");
    }

    @Override
    public void visit(UpdateGameDataMessage ms) {
        throw new UnsupportedOperationException("Not supported for server.");
    }

    @Override
    public void setChangedKey(Direction k) {
        throw new UnsupportedOperationException("Not supported for server.");
    }

    @Override
    public IGameData getGameData() {
        throw new UnsupportedOperationException("Not supported for server.");
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setNick(String nickname) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
