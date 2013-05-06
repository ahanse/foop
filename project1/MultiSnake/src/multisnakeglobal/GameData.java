/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author thb
 */
public class GameData implements IGameData{
    Vector<ISnake> snakes_;
    
    GameState state_;
    Point dimensions_;
    long timestamp_;
    
    public GameData(Point dimensions) {
        timestamp_ = 0;
        dimensions_ = dimensions;
        snakes_ = new Vector();
        setState(GameState.WAITINGFORPLAYERS);
    }
    
    public Vector<ISnake> getSnakes() {
        return snakes_;
    }

    public void startGame(int numberOfSnakes) {
        setState(GameState.TIMETOFIGHT);
        timestamp_ = System.currentTimeMillis() + 3000;
        generateSnakes(numberOfSnakes);
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
    
    private void makeSnake(Point headLocation, int length, Direction direction) {
        Snake s = new Snake(headLocation);
        s.setDirection(direction);
        PointTree prevPoint = s.getPointTree();
        for(int i = 0; i <= length - 1; ++i) {
            Point p = prevPoint.getRoot().nextPoint(inverseDirection(direction), dimensions_);
            PointTree nextPoint = new PointTree(p);
            prevPoint.addRightMostChild(nextPoint);
            prevPoint = nextPoint;
        }
        snakes_.add(s);
    }
    
    public void generateSnakes(int number) {
        Point h = new Point((int) dimensions_.getX()/number-1,(int) dimensions_.getY()/number-1);
        int length = 5;
        for(int i = 0; i < number; ++i) {
            // FIXME
            makeSnake(new Point(h.getX()*number,h.getY()*number),length,Direction.DOWN);
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
            
        }

        for(Iterator<ISnake> i = snakes_.iterator(); i.hasNext();) {
            Snake s = (Snake)(i.next());
            
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
                
                if(t.contains(goalHead)) {
                    doMoveTransformation = false;
                
                    // snakes with higher priority than us
                    if(t.getHead().equals(goalHead) &&
                       t.getPriority() >= s.getPriority()) {
                        // do nothing, we cannot move onto a snake with higher
                        // priority - do nothing
                    }
                    else {
                        // snakes with higher priority than us
                        s.eat(t.getEaten(goalHead));
                    }
                }
            }
            
            if(doMoveTransformation) {
                s.moveTransformation(goalHead);
            }
        }
    }
}
