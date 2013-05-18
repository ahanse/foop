/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

import java.util.Vector;
import java.util.Iterator;

/**
 *
 * @author thb
 */
public class Snake implements ISnake {
    private int priority_;
    private int next_priority_;
    private int id_;
    private String name_;
    private boolean isBot;
    
    private PointTree tiles_;
    
    public PointTree getPointTree() {
        return tiles_;
    }
    
    // The direction in which the snake will attempt to move
    private Direction direction_;
    
    public Snake(Point head, int id, int priority, boolean isBot) {
        tiles_ = new PointTree(head);
        id_ = id;
        priority_ = priority;
	next_priority_ = priority;
	name_ = "";
        this.isBot=isBot;
    }
    
    public boolean isDead() {
        return tiles_.isEmpty();
    }
    
    public Point getHead() {
        return tiles_.getRoot();
    }
    
    public int getPriority() {
        return priority_;
    }

    public void setPriority(int priority) {
        priority_ = priority;
    }

    public void updatePriority(int next_priority) {
	priority_ = next_priority_;
	next_priority_ = next_priority;
    }
    
    
    public int getNextPriority() {
        return next_priority_;
    }

    public void setNextPriority(int next_priority) {
        next_priority_ = next_priority;
    }
    
    public int getID() {
        return id_;
    }
    
    public String getName() {
        return name_;
    }

    public void setName(String name) {
        name_ = name;
    }
    
    public Vector<Point> getCoordinates() {
        Vector<Point> nodes = new Vector();
        tiles_.getNodes(nodes);
        return nodes;
    }
    
    public void setDirection(Direction direction) {
        direction_ = direction;
    }
    
    public Direction getDirection() {
        return direction_;
    }
    
    public boolean contains(Point p) {
        return tiles_.contains(p);
    }
    
    // the snake does not know about the dimensions of our playing field
    // therefore it requires the location of its new head to move.
    public void moveTransformation(Point newHead) {
        PointTree tiles_old = tiles_;
        tiles_ = new PointTree(newHead);
        tiles_.addRightMostChild(tiles_old);
        tiles_.deleteRightMostLeaf();
    }
    
    public void eat(PointTree t) {
        PointTree tiles_old = tiles_;
        tiles_ = t;
        tiles_.addRightMostChild(tiles_old);
    }
    
    public PointTree getEaten(Point p) {
        PointTree returntree;
        if(p.equals(getHead())){
            returntree = tiles_;
            tiles_ = new PointTree(null);
        } else {
            returntree = tiles_.findAndDeleteSubTree(p);
        }
        
        return returntree;
    }
    
    public String toString() {
        return "id = " + id_ + ":" + direction_ +":" + tiles_;
    }

    @Override
    public boolean isBot() {
        return isBot;
    }
    
}
