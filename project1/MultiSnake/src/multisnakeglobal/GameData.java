/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;
import java.util.Random;

/**
 *
 * @author thb
 */
public class GameData implements IGameData{
    Vector<ISnake> snakes_;
    
    GameState state_;
    Point dimensions_;
    long timestamp_;
    int ticks_;
    int playtime_;
    Random generator_;    

    public GameData(Point dimensions) {
        timestamp_ = 0;
        dimensions_ = dimensions;
        snakes_ = new Vector();
        setState(GameState.WAITINGFORPLAYERS);
	generator_ = new Random();
    }
    
    public Vector<ISnake> getSnakes() {
        return snakes_;
    }

    public void startGame(int numberOfSnakes, int numOfBots,int playtime) {
        setState(GameState.TIMETOFIGHT);
        timestamp_ = System.currentTimeMillis() + 3000;
        playtime_ = playtime;
	ticks_ = 0;
        generateSnakes(numberOfSnakes,numOfBots);
	shufflePriorities();
    }

    private void setState(GameState state) {
        state_ = state;
    }
    
    public GameState getStatus() {
        return state_;
    }
    public Point getDimensions() {
        return dimensions_;
    }
    
    public long getTimeStamp() {
        return timestamp_;
    }

    private Direction inverseDirection(Direction d) {
        switch(d) {
        case UP: return Direction.DOWN;
        case DOWN: return Direction.UP;
        case RIGHT: return Direction.LEFT;
        case LEFT: return Direction.RIGHT;
        }

        return null;
    }


    // FIXME: should be private
    public void shufflePriorities() {
        for(Iterator<ISnake> i = snakes_.iterator(); i.hasNext();) {
            Snake s = (Snake)(i.next());
            s.updatePriority(generator_.nextInt(snakes_.size()));
        }
        
    }
    
    // FIXME: should be private void
    public Snake makeSnake(Point headLocation, int length, Direction direction, int id, int priority, boolean isBot) {
        Snake s = new Snake(headLocation,id,priority, isBot);
        s.setDirection(direction);
        PointTree prevPoint = s.getPointTree();
        for(int i = 0; i < length - 1; ++i) {
            Point p = prevPoint.getRoot().nextPoint(inverseDirection(direction), dimensions_);
            PointTree nextPoint = new PointTree(p);
            prevPoint.addRightMostChild(nextPoint);
            prevPoint = nextPoint;
        }
        snakes_.add(s);
        
        //System.out.println("generated snake: " + s);
        return s;
    }
    
    // FIXME: should be private
    public void generateSnakes(int numOfPlayers, int numOfBots) {
        int length = 5;
        int number=numOfPlayers+numOfBots;
        int space = dimensions_.getX() / (1+number/2);
        
        for(int i = 0; i < number/2; ++i) {
            boolean isBot=i>numOfPlayers;
            makeSnake(new Point(space/2 + space*i,length),length,Direction.DOWN,i,i,isBot);
        }
        
        for(int i = number/2; i < number; ++i) {
            boolean isBot=i>numOfPlayers;
            makeSnake(new Point(space/2 + space*(i-number/2),dimensions_.getY() - length - 1),length,Direction.UP,i,number,isBot);
        }
    }
    
    // Process player input: tell a snake in which direction the player
    // wants it to move
    public void setSnakeDirection(int snakeIndex, Direction direction) {
        // Is this necessary? Or is an index out of bounds exception or
        // whatever this throws enough
        if (snakeIndex >= snakes_.size() || snakeIndex < 0) {
            return;
        }
        ((Snake)(snakes_.get(snakeIndex))).setDirection(direction);
        
        //System.out.println("updated snake direction: " + (Snake)(snakes_.get(snakeIndex)));
    }

    public void setSnakeName(int snakeIndex, String nick) {
        // Is this necessary? Or is an index out of bounds exception or
        // whatever this throws enough
        if (snakeIndex >= snakes_.size() || snakeIndex < 0) {
            return;
        }
        ((Snake)(snakes_.get(snakeIndex))).setName(nick);
    }
    
    public void playTurn() {

        switch(state_) {
        case WAITINGFORPLAYERS:
        case FINISHED: return;
        case TIMETOFIGHT:
            if(System.currentTimeMillis() > timestamp_) {
                setState(GameState.RUNNING);
            }
            else {
                return;
            }
        case RUNNING:
		if(System.currentTimeMillis() > timestamp_ + playtime_) { 
			setState(GameState.FINISHED);
			return;
		}
            
        }

	if(ticks_ % 15 == 0) {
		shufflePriorities();
	}

        for(Iterator<ISnake> i = snakes_.iterator(); i.hasNext();) {
            Snake s = (Snake)(i.next());

            if(s.isDead()) continue;
            
            Point currentHead = s.getHead();
            Point goalHead = currentHead.nextPoint(s.getDirection(), dimensions_);
            
            // Should we do a normal move?
            // If we eat a snake the eaten tile
            // becomes the new head and we do not have to move.
            // If we would move onto a snake with higher priority we cannot
            // move.
            boolean doMoveTransformation = true;

            
            for(Iterator<ISnake> j = snakes_.iterator(); j.hasNext();) {
                Snake t = (Snake)(j.next());

		if(t.isDead()) continue;
                
                if(t.contains(goalHead)) {
                    doMoveTransformation = false;
                
                    // snakes with higher priority than us
                    if(t.getHead().equals(goalHead) &&
                       t.getPriority() >= s.getPriority()) {
                        // do nothing, we cannot move onto a snake with higher
                        // priority - do nothing
                        
                        //System.out.println("snake cannot move: " + s);
                    }
                    else {
                        // snakes with higher priority than us
                        s.eat(t.getEaten(goalHead));
                        //System.out.println("snake eaten: " + s + "; " + t);
                    }
                }
            }
            
            if(doMoveTransformation) {
                s.moveTransformation(goalHead);
                //System.out.println("snake moved: " + s);
            }
        }

	ticks_++;
    }
}
