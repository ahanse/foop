/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import multisnakeglobal.Point;

/**
 *
 * @author Benedikt
 */
public class Options {

    // basic options for display
    // length of one parcel on the game board
    private int maxParcelLength;
    // proportions of program window
    private int windowWidth;
    private int windowHeight;
    private int[] COLORPOOL;
    private int ownColorInd;
    private int MAXPLAYER = 10;
    private Point[] RESLIST = {new Point(640, 480), new Point(800, 600), new Point(1024, 768), new Point(1280, 720), new Point(1152, 864), new Point(1280, 960)};
    private static String FILENAME = "options.dat";
    private String nickname;

    public Options() {
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

    // converts component based argb values to an argb value to use BufferedImage.setRGB
    public final int componentToARGB(int red, int green, int blue, int transparency) {
        return (transparency << 24) | (red << 16) | (green << 8) | blue;
    }

    // reads the options in this class from a file and sets them
    public final void readOptions() {
        try {
            //open file to read from
            FileInputStream saveFile = new FileInputStream(FILENAME);
            //create an ObjectInputStream to get objects from save file
            ObjectInputStream save = new ObjectInputStream(saveFile);

            this.maxParcelLength = (int) (Integer) save.readObject();
            this.windowWidth = (int) (Integer) save.readObject();
            this.windowHeight = (int) (Integer) save.readObject();
            this.nickname = (String) save.readObject();
            this.ownColorInd = (int) (Integer) save.readObject();

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
            save.writeObject(this.maxParcelLength);
            save.writeObject(this.windowWidth);
            save.writeObject(this.windowHeight);
            save.writeObject(this.nickname);
            save.writeObject(this.ownColorInd);
            //close file
            save.close();
        } catch (Exception e) {
        }
    }

    // saves the options in this class to a file
    public final void saveOptions(int maxParcelLength, int windowWidth, int windowHeight, String nickname, int ownColorInd) {
        this.maxParcelLength = maxParcelLength;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.nickname = nickname;
        this.ownColorInd = ownColorInd;
        saveOptions();
    }

    public int getMaxParcelLength() {
        return maxParcelLength;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public int[] getCOLORPOOL() {
        return COLORPOOL;
    }

    public Point[] getRESLIST() {
        return RESLIST;
    }
    
    public String getNickname() {
        return nickname;
    }

    public int getMAXPLAYER() {
        return MAXPLAYER;
    }

    public int getOwnColorInd() {
        return ownColorInd;
    }
}
