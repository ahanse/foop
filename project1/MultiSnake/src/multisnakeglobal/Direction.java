/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;


/**
 *
 * @author thb
 */
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;
    
    /**
     *
     * @return random direction
     */
    public static Direction getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
}
