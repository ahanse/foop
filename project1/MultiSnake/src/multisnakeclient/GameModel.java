/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import multisnakeglobal.IGameData;
import multisnakeglobal.ISnake;
import multisnakeglobal.Point;

/**
 *
 * @author max
 */

/* this class will be part of a MVC design. The model in which the gamedata
 * will be preserved */
public class GameModel {

    // basic options for display
    // length of one parcel on the game board
    private int parcelLength;
    // proportions of program window
    private int windowWidth;
    private int windowHeight;
    private static int[] COLORPOOL;
    private static String FILENAME = "options.dat";
    private ImageProceedingData currentImage;

    public GameModel() {
        readOptions();
        COLORPOOL = new int[]{
            componentToARGB(0, 0, 128, 255),
            componentToARGB(0, 191, 255, 255),
            componentToARGB(0, 100, 0, 255),
            componentToARGB(85, 107, 47, 255),
            componentToARGB(0, 255, 0, 255),
            componentToARGB(255, 255, 0, 255),
            componentToARGB(184, 134, 11, 255),
            componentToARGB(178, 34, 34, 255),
            componentToARGB(255, 0, 0, 255),
            componentToARGB(255, 20, 147, 255),
            componentToARGB(255, 106, 106, 255)};
    }

    /**
     * @return the currentImage
     */
    public ImageProceedingData getCurrentImage() {
        return currentImage;
    }

    public ImageProceedingData simulateData(int numOfXTiles, int numOfYTiles) {
        currentImage = new ImageProceedingData(numOfXTiles, numOfYTiles);
        int numOfSnakes = 5;
        int m = 0;
        ArrayList<ArrayList<Point>> snakes = new ArrayList<ArrayList<Point>>();
        for (int i = 0; i < 5; i++) {
            snakes.add(new ArrayList<Point>());
        }
        Random randomGenerator = new Random();
        for (int i = 0; i < numOfXTiles; i++) {
            for (int j = 0; j < numOfYTiles; j++) {
                m = randomGenerator.nextInt(5);
                if (randomGenerator.nextInt(1000) % 2 == 0) {
                    snakes.get(m).add(new Point(i, j));
                }
            }
        }
        for (int i = 0; i < numOfSnakes; i++) {
            currentImage.addRectangleFromList(snakes.get(i), i, new Color(COLORPOOL[2*i]));
        }
        return currentImage;
    }

    // save GameData to a propper format
    public void saveGameData(IGameData data) {
        currentImage = new ImageProceedingData(data.getDimensions().getX(), data.getDimensions().getY());
        int i = 0;
        for (ISnake snake : data.getSnakes()) {
            getCurrentImage().addRectangleFromSnake(snake, new Color(COLORPOOL[i]));
            i++;
        }
    }

    // reads the options in this class from a file and sets them
    public final void readOptions() {
        try {
            //open file to read from
            FileInputStream saveFile = new FileInputStream(FILENAME);
            //create an ObjectInputStream to get objects from save file
            ObjectInputStream save = new ObjectInputStream(saveFile);

            setParcelLength((int) (Integer) save.readObject());
            setWindowWidth((int) (Integer) save.readObject());
            setWindowHeight((int) (Integer) save.readObject());

            //close file
            save.close();
        } catch (Exception e) {
        }
    }

    // saves the options in this class to a file
    public final void saveOptions() {
        try {
            //open a file to write to
            FileOutputStream saveFile = new FileOutputStream(FILENAME);
            //create an ObjectOutputStream to put objects into save file
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            // write on Stream
            save.writeObject(getParcelLength());
            save.writeObject(getWindowWidth());
            save.writeObject(getWindowHeight());
            //close file
            save.close();
        } catch (Exception e) {
        }
    }

    // calculates the brightness of a color (between 0..255)
    public int brightness(Color c) {
        return (int) Math.sqrt(
                c.getRed() * c.getRed() * .241
                + c.getGreen() * c.getGreen() * .691
                + c.getBlue() * c.getBlue() * .068);
    }

    // converts component based argb values to an argb value to use BufferedImage.setRGB
    public final int componentToARGB(int red, int green, int blue, int transparency) {
        return (transparency << 24) | (red << 16) | (green << 8) | blue;
    }

    /**
     * @return the parcelLength
     */
    public int getParcelLength() {
        return parcelLength;
    }

    /**
     * @param parcelLength the parcelLength to set
     */
    public void setParcelLength(int parcelLength) {
        this.parcelLength = parcelLength;
    }

    /**
     * @return the windowWidth
     */
    public int getWindowWidth() {
        return windowWidth;
    }

    /**
     * @param windowWidth the windowWidth to set
     */
    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    /**
     * @return the windowHeight
     */
    public int getWindowHeight() {
        return windowHeight;
    }

    /**
     * @param windowHeight the windowHeight to set
     */
    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }
}
