/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Benedikt
 */
public class BenesBot extends Bot {

    private int Zaehler;
    private int oldPriority;
    
    public BenesBot(IGameData gd, int ownID) {
        super(gd, ownID);
        Zaehler=-10;
        oldPriority=-1;
    }

    public Direction play() {
        Zaehler++;
        
        List<ISnake> snakes = gd_.getSnakes();
        double[] weights = new double[snakes.size()];
        ISnake mySnake = null;
        for (ISnake s : snakes) {
            if (s.getID() == ownID_) {
                mySnake = s;
            }
        }
        if (mySnake == null) {
            return null;
        }
        if (mySnake.isDead()) {
            return null;
        }
        
        if(oldPriority!=mySnake.getPriority())
        {
            //Priorities just changed
            Zaehler=0;
        }
        
        for (int i = 0; i < weights.length; i++) {
            if (snakes.get(i) == mySnake) {
                weights[i] = -2;
            } else if (snakes.get(i).isDead()) {
                weights[i] = -1;
            }else {
                int dist = calculateDistance(snakes.get(i).getHead(), mySnake.getHead());
                weights[i] = ((double) snakes.get(i).getCoordinates().size()) / (dist * dist);
            }
            
            if(snakes.get(i).isBot())
            {
                weights[i]=weights[i]/1000;
            }
        }
        int ind = getMaxInd(weights);

        ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
        possibleDirections.add(Direction.UP);
        possibleDirections.add(Direction.DOWN);
        possibleDirections.add(Direction.LEFT);
        possibleDirections.add(Direction.RIGHT);
        for (int i = 0; i < possibleDirections.size(); i++) {
            if (moveForbidden(snakes, mySnake, possibleDirections.get(i))) {
                possibleDirections.remove(i);
                i--;
            }
        }

        if (possibleDirections.isEmpty()) {
            possibleDirections.add(Direction.UP);
            possibleDirections.add(Direction.DOWN);
            possibleDirections.add(Direction.LEFT);
            possibleDirections.add(Direction.RIGHT);
        }
        if (weights[ind]>=0) {
            int minDist = calculateDistance(mySnake.getHead().nextPoint(possibleDirections.get(0), gd_.getDimensions()), snakes.get(ind).getHead());
            for (int i = 1; i < possibleDirections.size(); i++) {
                int dist = calculateDistance(mySnake.getHead().nextPoint(possibleDirections.get(i), gd_.getDimensions()), snakes.get(ind).getHead());
                if (dist < minDist) {
                    minDist = dist;
                    for (int j = i - 1; j >= 0; j--) {
                        possibleDirections.remove(j);
                    }
                    i = 0;
                } else if (dist > minDist) {
                    possibleDirections.remove(i);
                    i--;
                }
            }

            minDist = calculateMinDistToSnake(mySnake.getHead().nextPoint(possibleDirections.get(0), gd_.getDimensions()), snakes.get(ind));
            for (int i = 1; i < possibleDirections.size(); i++) {
                int dist = calculateMinDistToSnake(mySnake.getHead().nextPoint(possibleDirections.get(i), gd_.getDimensions()), snakes.get(ind));
                if (dist < minDist) {
                    minDist = dist;
                    for (int j = i - 1; j >= 0; j--) {
                        possibleDirections.remove(j);
                    }
                    i = 0;
                } else if (dist > minDist) {
                    possibleDirections.remove(i);
                    i--;
                }
            }
            Point p1 = getNextPos(snakes.get(ind));
            minDist = calculateDistance(mySnake.getHead().nextPoint(possibleDirections.get(0), gd_.getDimensions()), p1);
            for (int i = 1; i < possibleDirections.size(); i++) {
                int dist = calculateDistance(mySnake.getHead().nextPoint(possibleDirections.get(i), gd_.getDimensions()), p1);
                if (dist < minDist) {
                    minDist = dist;
                    for (int j = i - 1; j >= 0; j--) {
                        possibleDirections.remove(j);
                    }
                    i = 0;
                } else if (dist > minDist) {
                    possibleDirections.remove(i);
                    i--;
                }
            }
        }
        int move = generator_.nextInt(possibleDirections.size());
        return possibleDirections.get(move);
    }

    private int getMaxInd(double[] a) {
        if (a.length == 0) {
            return -1;
        }
        int erg = 0;
        double max = a[0];
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max) {
                erg = i;
                max = a[i];
            }
        }
        return erg;
    }

    private int calculateDistance(Point p1, Point p2) {
        int distX = Math.abs(p1.getX() - p2.getX());
        int distY = Math.abs(p1.getY() - p2.getY());

        distX = Math.min(distX, gd_.getDimensions().getX() - distX);
        distY = Math.min(distY, gd_.getDimensions().getY() - distY);

        return distX + distY;
    }

    private boolean moveForbidden(List<ISnake> snakes, ISnake mySnake, Direction dir) {
        Point newPos = mySnake.getHead().nextPoint(dir, gd_.getDimensions());
        for (ISnake s : snakes) {
            //TODO: nextPriority!!!
            if(!s.isDead())
            {
                if (s != mySnake && calculateDistance(newPos, s.getHead()) <= 2&& (s.getPriority() > mySnake.getPriority()|| (Zaehler>=9 && s.getNextPriority()>mySnake.getNextPriority()))) {
                    return true;
                }
            }
        }
        return false;
    }

    private Point calcNewPos(final Point p, Direction dir) {
        Point erg = new Point(p.getX(), p.getY());
        switch (dir) {
            case UP:
                erg.setX(erg.getX() - 1);
            case DOWN:
                erg.setX(erg.getX() + 1);
            case LEFT:
                erg.setY(erg.getX() - 1);
            case RIGHT:
                erg.setY(erg.getX() + 1);
        }
        if (erg.getX() < 0) {
            erg.setX(gd_.getDimensions().getX() - erg.getX());
        } else if (erg.getX() >= gd_.getDimensions().getX()) {
            erg.setX(erg.getX() - gd_.getDimensions().getX());
        } else if (erg.getY() < 0) {
            erg.setY(gd_.getDimensions().getY() - erg.getY());
        } else if (erg.getY() >= gd_.getDimensions().getY()) {
            erg.setY(erg.getY() - gd_.getDimensions().getY());
        }
        return erg;
    }

    private int calculateMinDistToSnake(Point p, ISnake s) {
        if (s.isDead()) {
            return -1;
        }
        int erg = calculateDistance(p, s.getCoordinates().get(0));

        for (Point p1 : s.getCoordinates()) {
            int tmp = calculateDistance(p, p1);
            if (tmp < erg) {
                erg = tmp;
            }
        }
        return erg;
    }

    private Point getNextPos(ISnake s) {
        if (s.getCoordinates().size() <= 1) {
            return s.getHead();
        }
        int x = 2 * s.getHead().getX() - s.getCoordinates().get(1).getX();
        int y = 2 * s.getHead().getY() - s.getCoordinates().get(1).getY();
        if (x < 0) {
            x = 1;
        } else if (x >= gd_.getDimensions().getX()) {
            x = gd_.getDimensions().getX() - 2;
        }
        if (y < 0) {
            y = 1;
        } else if (y >= gd_.getDimensions().getY()) {
            y = gd_.getDimensions().getY() - 2;
        }
        return new Point(x, y);
    }
}
