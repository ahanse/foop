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
        timestamp_ = System.currentTimeMillis();
        dimensions_ = dimensions;
        state_ = GameState.PAUSED;
        snakes_ = new Vector();
    }
    
    public Vector<ISnake> getSnakes() {
        return snakes_;
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
        for(Iterator<ISnake> i = snakes_.iterator(); i.hasNext();) {
            Snake s = (Snake)(i.next());
            
            Point currentHead = s.getHead();
            Point goalHead = new Point(0,0);
            
            switch(s.getDirection()) {
                case UP:
                    goalHead.setX(currentHead.getX());
                    goalHead.setY((currentHead.getY() - 1) % dimensions_.getY());
                    break;
                case DOWN:
                    goalHead.setX(currentHead.getX());
                    goalHead.setY((currentHead.getY() + 1) % dimensions_.getY());
                    break;
                case LEFT:
                    goalHead.setX((currentHead.getX() - 1) % dimensions_.getX());
                    goalHead.setY(currentHead.getY());
                    break;
                case RIGHT:
                    goalHead.setX((currentHead.getX() + 1) % dimensions_.getX());
                    goalHead.setY(currentHead.getY());
                    break;
            }
            
            // Should we do a normal move?
            // If we eat a snake the eaten tile
            // becomes the new head and we do not have to move.
            // If we would move onto a snake with higher priority we cannot
            // move.
            boolean doMoveTransformation = true;
            
            for(Iterator<ISnake> j = snakes_.iterator(); j.hasNext();) {
                Snake t = (Snake)(i.next());
                
                if(t.contains(goalHead)) {
                    doMoveTransformation = false;
                
                    // snakes with higher priority than us
                    if(t.getPriority() >= s.getPriority()) {
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
