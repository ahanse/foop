/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

/**
 *
 * @author Benedikt
 */
public class Point {
    
	private int X;
    private int Y;

    public Point(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public void setX(int X) {
        this.X = X;
    }

    public int getY() {
        return Y;
    }

    public void setY(int Y) {
        this.Y = Y;
    }
    
    @Override
    public boolean equals(Object o) {
    	if(!(o instanceof Point))
    		return false;
    	Point p=(Point)o;
        return p.getX() == X && p.getY() == Y;
    }
    
    @Override
	public String toString() {
		return "("+X+","+Y+")";
	}
    
    public Point nextPoint(Direction d, Point dimension) {
        Point goalHead = new Point(0,0);
        switch(d) {
            case UP:
                goalHead.setX(this.getX());
                goalHead.setY(posMod((this.getY() - 1), dimension.getY()));
                break;
            case DOWN:
                goalHead.setX(this.getX());
                goalHead.setY(posMod((this.getY() + 1), dimension.getY()));
                break;
            case LEFT:
            goalHead.setX(posMod((this.getX() - 1), dimension.getX()));
                goalHead.setY(this.getY());
                break;
            case RIGHT:
                goalHead.setX(posMod((this.getX() + 1), dimension.getX()));
                goalHead.setY(this.getY());
                break;
        }
        return goalHead;
    }

    private int posMod(int n, int mod) {
        n = n % mod;
        return n >= 0 ? n : n + mod;
    }
}
