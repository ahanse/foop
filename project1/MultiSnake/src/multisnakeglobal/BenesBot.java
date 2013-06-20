/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

import java.util.ArrayList;
import java.util.List;

/**
 * class for calculating moves of a computer-controlled snanke
 * @author Benedikt
 */
public class BenesBot extends Bot {
    
    private int counter;
    private int oldPriority;
    private double AntiHuman;
    
    //constructor, gets a reference to the game data, which doesn't get changed, 
    // the ID of the computer-controlled snake and a factor, which indicates how 
    // strong the bots play together against the human players. 1 means the bot 
    // plays against human and against other bots. Bigger than 1 means, that the 
    // bots prefer to aim at human snakes.
    public BenesBot(IGameData gd, int ownID, double AntiHuman) {
        super(gd, ownID);
        counter=-10;
        oldPriority=-1;
        this.AntiHuman=Math.abs(AntiHuman);
    }

    // calculates one move of the bot and returns dthe direction to move
    public Direction play() {
        counter++; //counts the turns since the last priority-change
        
        List<ISnake> snakes = gd_.getSnakes();
        double[] weights = new double[snakes.size()];
        
        //searching the own snake
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
            oldPriority=mySnake.getPriority();
            //Priorities just changed
            counter=0;
        }
        
        //calculates a weight for each snake
        for (int i = 0; i < weights.length; i++) {
            if (snakes.get(i) == mySnake) {
                weights[i] = -2;
            } else if (snakes.get(i).isDead()) {
                weights[i] = -1;
            }else {
                int dist = calculateDistance(snakes.get(i).getHead(), mySnake.getHead());
                weights[i] = ((double) snakes.get(i).getCoordinates().size()) / (dist * dist);
            }
            
            if(snakes.get(i).isBot()) //changing bot snake weights with AntHuman-factor
            {
                weights[i]=weights[i]/AntiHuman;
            }
        }
        
        //snake index with maximal weight, this snake will get the new target
        int ind = getMaxInd(weights);

        //list all possible directions with no risk to get eat by a snake
        ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
        possibleDirections.add(Direction.UP);
        possibleDirections.add(Direction.DOWN);
        possibleDirections.add(Direction.LEFT);
        possibleDirections.add(Direction.RIGHT);
        for (int i = 0; i < possibleDirections.size(); i++) {
            if (moveForbidden(snakes, mySnake, possibleDirections.get(i),2)) {
                possibleDirections.remove(i);
                i--;
            }
        }

        //if in every direction is a snake with high priority then check with 
        // less radius (1 instead of 2)
        if (possibleDirections.isEmpty()) {
            possibleDirections.add(Direction.UP);
            possibleDirections.add(Direction.DOWN);
            possibleDirections.add(Direction.LEFT);
            possibleDirections.add(Direction.RIGHT);
            for (int i = 0; i < possibleDirections.size(); i++) {
                if (moveForbidden(snakes, mySnake, possibleDirections.get(i),1)) {
                    possibleDirections.remove(i);
                    i--;
                }
            }
        }
        
        // if still in every direction is a snake with high priority then take 
        // all directions as possible
        if (possibleDirections.isEmpty()) {
            possibleDirections.add(Direction.UP);
            possibleDirections.add(Direction.DOWN);
            possibleDirections.add(Direction.LEFT);
            possibleDirections.add(Direction.RIGHT);
        }
        
        if (weights[ind]>=0) { //checks if the target snake isn't dead or the own snake (if only one snake remains)
            
            // find the possible directions with minimal distance to the target snake head and remove all directions with higher distances
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
            
            // filter from all remaining directions those, which have minimal distance to one of the tiles of the target snake
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
            
            //calculates the head position of the target snake after this turn if the snake wouldn't change direction
            Point p1 = getNextPos(snakes.get(ind));
            
            //filter from all remaining directions those, which have minimal distance to p1
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
        
        // move in an arbitrary remaining direction
        int move = generator_.nextInt(possibleDirections.size());
        return possibleDirections.get(move);
    }

    // returns the index of the maximum of a double array
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

    // calculates the distance between two points on our playground, 
    // considering that snakes can walk through walls.
    private int calculateDistance(Point p1, Point p2) {
        int distX = Math.abs(p1.getX() - p2.getX());
        int distY = Math.abs(p1.getY() - p2.getY());

        distX = Math.min(distX, gd_.getDimensions().getX() - distX); // the second one is the distance through the wall
        distY = Math.min(distY, gd_.getDimensions().getY() - distY);

        return distX + distY;
    }

    // Checks if a move in a direction would end near an enemy with higher 
    // priority (or if counter is is bigger or equal than 8 also with higher next priority).
    // If priorities are equal it also checks the next priorities if counter is 
    // bigger or equal than 8.
    private boolean moveForbidden(List<ISnake> snakes, ISnake mySnake, Direction dir, int AnzFelder) {
        Point newPos = mySnake.getHead().nextPoint(dir, gd_.getDimensions());
        boolean stopSituation=false;
        for (ISnake s : snakes) {
            if(!s.isDead())
            {
                if (s != mySnake && calculateDistance(newPos, s.getHead()) <= AnzFelder&& (s.getPriority() > mySnake.getPriority()|| (counter>=8 && s.getNextPriority()>mySnake.getNextPriority()))) {
                    return true;
                }
                else if(s != mySnake && calculateDistance(newPos, s.getHead()) <= 1&& (s.getPriority() == mySnake.getPriority()|| (counter>=8 && s.getNextPriority()==mySnake.getNextPriority())))
                {
                    stopSituation=true;
                }
             }
            
        }
        if(stopSituation)
        {
            for (ISnake s : snakes) {
                if(!s.isDead())
                {
                    if (s != mySnake && calculateDistance(mySnake.getHead(), s.getHead()) <= AnzFelder&& (s.getPriority() > mySnake.getPriority()|| (counter>=8 && s.getNextPriority()>mySnake.getNextPriority()))) {
                        return true;
                    }
                 }
            }
        }
        return false;
    }

    // Calculates the minimal distance from a point to the tiles of a snake.
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

    // calculates the position of the head of a snake after one turn, if the 
    // snake dosn't change the direction
    private Point getNextPos(ISnake s) {
        if (s.getCoordinates().size() <= 1) {
            return s.getHead(); // we can't find out  the snakes direction if there is only the head
        }
        
        // calculating the new point by adding head position + direction = 
        // head position + (head position - position of second tile) = 
        // 2*head position - position of second tile
        int x = 2 * s.getHead().getX() - s.getCoordinates().get(1).getX();
        int y = 2 * s.getHead().getY() - s.getCoordinates().get(1).getY();
        
        // checking the borders
        if (x < 0) {
            // can only happen if the x cooridnate of head is 0 and the second tile is on the right side of the board
            x = 1;
        } else if (x >= gd_.getDimensions().getX()) {
            // can only happen if the head is on the right side of the board and the second tile on the left side
            x = gd_.getDimensions().getX() - 2;
        }
        if (y < 0) {
            // can only happen if the y cooridnate of head is 0 and the second tile is on the bottom of the board
            y = 1;
        } else if (y >= gd_.getDimensions().getY()) {
            // can only happen if the head is on the top of the board and the second tile on the bottom
            y = gd_.getDimensions().getY() - 2;
        }
        return new Point(x, y);
    }
}
