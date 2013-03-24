/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Stack;
import multisnakeglobal.ISnake;
import multisnakeglobal.Point;

/**
 *
 * @author max
 */
// class to represent a Game Board
public class ImageProceedingData {

    private Stack<RectangleData> rectangles;
    private int numOfXFields = 0;
    private int numOfYFields = 0;
    private int numOfSnakes = 0;

    public ImageProceedingData(int numOfXFields, int numOfYFields) {
        rectangles = new Stack<RectangleData>();
        this.numOfXFields = numOfXFields;
        this.numOfYFields = numOfYFields;
    }

    public void addRectangleFromSnake(ISnake snake, Color color) {
        Point head = snake.getHead();
        Stack<Point> coords = new Stack<Point>();
        for (Point p : snake.getKoordinaten()) {
            if ((p.getX() != head.getX()) && (p.getY() != head.getY())) {
                coords.push(p);
            }
        }
        coords.push(head);
        rectangles.push(new RectangleData(color, String.valueOf(snake.getPriority()), coords));
        numOfSnakes++;
    }

    public void addRectangleFromList(ArrayList<Point> list, int number, Color color) {
        Stack<Point> coords = new Stack<Point>();
        for (int i = 0; i < list.size(); i++) {
            coords.push(list.get(i));
        }
        rectangles.push(new RectangleData(color, String.valueOf(number), coords));
        numOfSnakes++;
    }

    public Boolean isEmpty() {
        return rectangles.isEmpty();
    }

    public RectangleData getNextRectangleData() {
        if (!rectangles.isEmpty()) {
            numOfSnakes--;
            return rectangles.pop();
        } else {
            return null;
        }
    }

    /**
     * @return the numOfXFields
     */
    public int getNumOfXFields() {
        return numOfXFields;
    }

    /**
     * @return the numOfYFields
     */
    public int getNumOfYFields() {
        return numOfYFields;
    }

    /**
     * @return the numOfSnakes
     */
    public int getNumOfSnakes() {
        return numOfSnakes;
    }
}
