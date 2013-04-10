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
public class PointTree {
    private Point root_;
    private Vector<PointTree> children_;
    
    public Point getRoot() {
        return root_;
    }
        
    public boolean isEmpty() {
        return root_ == null;
    }
    
    public PointTree(Point root) {
        root_ = root;
        children_ = new Vector();
    }
    
    // Append a PointTree to the end of the list of children
    public void addRightMostChild(PointTree child) {
        children_.add(child);
    }
    
    public void deleteRightMostChild() {
        children_.remove(children_.size() - 1);
    }
    
    public PointTree getRightMostChild() {
        return children_.get(children_.size() - 1);
    }
    
    // Find and delete the right most leaf
    // We mark the leaf tree as empty to perform a clean up in the parent tree
    // by deleting the leaf       
    public Point deleteRightMostLeaf() {
        if(children_.isEmpty()) {
            Point p = root_;
            root_ = null;
            return p;
        } else {
            PointTree t = getRightMostChild();
            Point p = t.deleteRightMostLeaf();
            if(t.isEmpty()) {
                deleteRightMostChild();
            }
            return p;
        }
    }
    
    public void getNodes(Vector<Point> nodes) {
        nodes.add(root_);
        
        for(Iterator<PointTree> i = children_.iterator(); i.hasNext();) {
            PointTree t = i.next();
            t.getNodes(nodes);
        }
    }
    
    public boolean contains(Point p) {
        if(p.equals(root_)) {
            return true;
        }
        
        for(Iterator<PointTree> i = children_.iterator(); i.hasNext();) {
            PointTree t = i.next();
            if(t.contains(p)) {
                return true;
            }
        }
        
        return false;
    }
    
    public PointTree findAndDeleteSubTree(Point p) {
        for(int i = 0; i < children_.size(); ++i) {
            PointTree t = children_.get(i);
            if(t.getRoot().equals(p)) {
                children_.remove(i);
                return t;
            }
            PointTree u = t.findAndDeleteSubTree(p);
            if(t.findAndDeleteSubTree(p) != null) {
                return u;
            }
        }
        
        return null;
    }
}


