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
    private int MAXPLAYER = 10;
    private Point[] RESLIST = {new Point(400,400),new Point(600,400),new Point(800,400),new Point(800,600)};

    private static String FILENAME = "options.dat";
    
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getMAXPLAYER() {
		return MAXPLAYER;
	}

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

            setMaxParcelLength((int) (Integer) save.readObject());
            setWindowWidth((int) (Integer) save.readObject());
            setWindowHeight((int) (Integer) save.readObject());
            setNickname((String) save.readObject());

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
            save.writeObject(getMaxParcelLength());
            save.writeObject(getWindowWidth());
            save.writeObject(getWindowHeight());
            save.writeObject(getNickname());
            //close file
            save.close();
        } catch (Exception e) {
        }
    }
    
 // saves the options in this class to a file
    public final void saveOptions(int maxParcelLength, int windowWidth, int windowHeight) {
        try {
            //open a file to write to
            FileOutputStream saveFile = new FileOutputStream(FILENAME);
            //create an ObjectOutputStream to put objects into save file
            ObjectOutputStream save = new ObjectOutputStream(saveFile);
            // write on Stream
            save.writeObject(maxParcelLength);
            save.writeObject(windowWidth);
            save.writeObject(windowHeight);
            //close file
            save.close();
        } catch (Exception e) {
        }
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

    public void setMaxParcelLength(int maxParcelLength) {
        this.maxParcelLength = maxParcelLength;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }
    
    
}
