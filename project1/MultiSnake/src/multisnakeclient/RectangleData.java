/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.Color;
import java.util.Stack;
import multisnakeglobal.Point;

/**
 *
 * @author max
 */
// Class to represent one rectangle on the Game Board
public class RectangleData {

    private Color color;
    private String number;
    private Stack<Point> coords;

    
    public RectangleData(Color color, String number, Stack<Point> coords) {
        this.color = color;
        this.number = number;
        this.coords = coords;
    }

    public Point getNextCoord() {
        if (!coords.isEmpty()) {
            return coords.pop();
        } else {
            return null;
        }
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @return the coords
     */
    public Stack<Point> getCoords() {
        return coords;
    }
}
