/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;

/**
 *
 * @author hanse
 */
public class NetworkClient extends Observable implements Runnable, IPlayer {
    private ConnectionState state=ConnectionState.NOTREADY;
    private String nick="no nick";
    private Socket connection=null;
    private ObjectOutputStream outStream = null;
    private Direction keyChange = null;
    private int id=0;
    
    private Thread t = null;
    private IGameData gd; 
        
    public NetworkClient() {
        gd = new GameData(new Point(1,1));
    }
    
    public void connect(String host, int port) throws IOException {
        connect(new Socket(host, port));
    }
    
    public void connect(Socket connection) {
        this.connection = connection;
        System.out.println("Connected client/server.");
        t = new Thread(this);
        t.start();
    }
    
    @Override
    public void run() {
        try {
            state = ConnectionState.READY;
            super.setChanged();
            super.notifyObservers();
            this.outStream = new ObjectOutputStream(connection.getOutputStream());
            this.outStream.flush();
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
    
    public synchronized void disconnect() {
        try{
            connection.close();
        } catch(IOException e) {}
        state  = ConnectionState.DISCONNECTED;
        System.out.println("Disconnected client.");
        connection = null;
        super.setChanged();
        super.notifyObservers(); 
    }
    
    private void sendMessage(INetworkMessage m) {
        if(outStream!=null) {
            try {
                //System.out.println("Message sent!");
                outStream.reset();
                outStream.writeObject(m);
            }
            catch(IOException e) { disconnect();}
        }
    }

    @Override
    public synchronized ConnectionState getState() {
        return this.state;
    }

    @Override
    public synchronized String getNick() {
        return this.nick;
    }
   
    @Override
    public void setNick(String nickname) {
        this.nick = nickname;
        if(this.connection!=null) {
            sendMessage(new AnnounceNickMessage(nickname));
        }
    }

    @Override
    public synchronized Direction getChangedKey() {
        Direction k = this.keyChange;     
        this.keyChange=null;
        return k;
    }

    @Override
    public synchronized void setChangedKey(Direction k) {
        if(this.connection!=null) {
            sendMessage(new KeyChangedMessage(k));
            keyChange = k;
        }
    }

    @Override
    public synchronized void setId(int id) {
        //super.setChanged();
        //super.notifyObservers();       
        this.id = id;
        if(state==ConnectionState.READY) sendMessage(new SetIdMessage(id));
    }

    @Override
    public synchronized  void updateGameData(IGameData gd) {
        //super.setChanged();
        //super.notifyObservers();       
        this.gd = gd;
        if(state==ConnectionState.READY) sendMessage(new UpdateGameDataMessage(gd));
    }

    @Override
    public synchronized IGameData getGameData() {
        return this.gd;
    }

    @Override
    public synchronized int getId() {
        return this.id;
    }

    @Override
    public synchronized void visit(AnnounceNickMessage ms) {
        this.nick = ms.nick;
    }

    @Override
    public synchronized void visit(KeyChangedMessage ms) {
        this.keyChange = ms.keyChange;
    }

    @Override
    public synchronized void visit(SetIdMessage ms) {
        this.id = ms.id;
    }

    @Override
    public  synchronized void visit(UpdateGameDataMessage ms) {
        this.gd = ms.gameData;
    }
}
