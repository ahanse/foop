/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Random;
import java.util.Set;
import multisnakeglobal.*;

/**
 *
 * @author Benedikt
 */
public class DummyNetwork extends Observable implements Runnable, IPlayer {
    
    private Boolean threadSuspended = false;
    private Thread t = null;
    private DummyGameData data;
    
    public DummyNetwork(){
        super();
        t = new Thread(this);
        t.start();
        data=new DummyGameData();
    }

    @Override
    public void run() {
        Thread thisThread = Thread.currentThread();
        while (t == thisThread) {
            try {
                long start = System.nanoTime();
                long elapsedTime2 = (System.nanoTime() - start);
                // delay to bitrate milliseconds
                int bitrate = 200;
                Thread.sleep(Math.max(0, bitrate - (int) (elapsedTime2 / 1000000)));
                synchronized (this) {
                    while (threadSuspended && t == thisThread) {
                        wait();
                    }
                }
                super.setChanged();
                data.change();
                super.notifyObservers();
            } catch (InterruptedException e) {
            }
            
            
        }
    }

    @Override
    public IGameData getGameData() {
        return data;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public ConnectionState getStatus() {
        return ConnectionState.READY;
    }

    @Override
    public void setChangedKey(KeyChange key) {
        data.key=key;
    }

    @Override
    public String getNick() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public KeyChange getChangedKey() {
        return data.key;
    }

    @Override
    public void setId(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateGameData(IGameData gd) {
        throw new UnsupportedOperationException("Not supported yet.");
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

   public class DummyGameData implements IGameData
   {
       private List<ISnake> snakes;
       private KeyChange key=KeyChange.LEFT;
       private int dimX=30;
       private int dimY=30;
       
       public DummyGameData()
       {
           
           List<Point> tmp=new ArrayList<Point>();
           for(int i=0;i<10;i++)
           {
                tmp.add(new Point(i,10));
           }
           snakes=new ArrayList<ISnake>();
           snakes.add(new DummySnake(tmp,0,1));
           tmp=new ArrayList<Point>();
           for(int i=0;i<10;i++)
           {
                tmp.add(new Point(20,i));
           }
           snakes.add(new DummySnake(tmp,1,5));
       }

        @Override
        public List<ISnake> getSnakes() {
            return snakes;
        }

        @Override
        public GameState getStatus() {
            return GameState.RUNNING;
        }

        @Override
        public Point getDimensions() {
            return new Point(dimX,dimY);
        }

        @Override
        public long getTimeStamp() {
            return 0;
        }

        private void change() {
            /*for(Point coord:snakes.get(0).getCoordinates())
            {
                coord.setX((coord.getX()+1)%dimX);
            }
            for(Point coord:snakes.get(1).getCoordinates())
            {
                coord.setY((coord.getY()+1)%dimY);
            }*/
            if(key==null)
            {
                snakes.get(0).getCoordinates().add(0,randomDirection(snakes.get(0).getCoordinates().get(0),snakes.get(0).getCoordinates().get(1)));
            }
            else
            {
                Point head=snakes.get(0).getCoordinates().get(0);
                Point erg=new Point(head.getX(),head.getY());
                if(key==KeyChange.RIGHT)
                {
                    erg.setX(mod((head.getX()+1),dimX));
                }
                if(key==KeyChange.LEFT)
                {
                    erg.setX(mod((head.getX()-1),dimX));
                }   
                if(key==KeyChange.DOWN)
                {
                    erg.setY(mod((head.getY()+1),dimY));
                }
                if(key==KeyChange.UP)
                {
                    erg.setY(mod((head.getY()-1),dimY));
                }
                snakes.get(0).getCoordinates().add(0,erg);
            }
            snakes.get(0).getCoordinates().remove(snakes.get(0).getCoordinates().size()-1);
            
            
            snakes.get(1).getCoordinates().add(0,randomDirection(snakes.get(1).getCoordinates().get(0),snakes.get(1).getCoordinates().get(1)));
        
            snakes.get(1).getCoordinates().remove(snakes.get(1).getCoordinates().size()-1);
        }
        
        private Point randomDirection(Point head, Point second){
            Random randomGenerator = new Random();
            Point erg=new Point(second.getX(),second.getY());
            while(erg.equals(second))
            {
                erg.setX(head.getX());
                erg.setY(head.getY());
                if(randomGenerator.nextInt(2)==0)
                {
                    if(randomGenerator.nextInt(2)==0)
                    {
                        erg.setX(mod((head.getX()+1),dimX));
                    }
                    else
                    {
                        erg.setX(mod((head.getX()-1),dimX));
                    }   
                }
                else
                {
                    if(randomGenerator.nextInt(2)==0)
                    {
                        erg.setY(mod((head.getY()+1),dimY));
                    }
                    else
                    {
                        erg.setY(mod((head.getY()-1),dimY));
                    }
                }
            }
            return erg;
        }
        
        private int mod(int x, int y)
        {
            int erg=x%y;
            if(erg<0)
                erg=erg+y;
            return erg;
        }
        
        public class DummySnake implements ISnake
        {
            private List<Point> points;
            private int id;
            private int priority;
            
            public void setPriority(int priority) {
				this.priority = priority;
			}

			public DummySnake(List<Point> points, int id, int priority){
                this.points=points;
                this.id=id;
                this.priority=priority;
            }
            
            @Override
            public List<Point> getCoordinates() {
                return points;
            }

            @Override
            public Point getHead() {
                return points.get(0);
            }

            @Override
            public int getPriority() {
                return priority;
            }

            @Override
            public int getID() {
                return id;
            }

            @Override
            public String getName() {
                return "DummySnake";
            }
            
        }
   }
}
