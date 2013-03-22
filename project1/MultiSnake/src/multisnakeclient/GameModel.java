/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import java.awt.Graphics;
import java.awt.image.*;
import java.io.*;

/**
 *
 * @author max
 */

/* this class will be part of a MVC design. The model in which the gamedata
 * will be preserved */
public class GameModel {

    // basic options for display
    // length of one parcel on the game board
    private int parcelLength = 0;
    // proportions of program window
    private int windowWidth = 0;
    private int windowHeight = 0;
    private static String filename = "options.dat";

    public GameModel() {
        readOptions(filename);
    }

    // reads the options in this class from a file and sets them
    public final void readOptions(String filename) {
        try {
            //open file to read from
            FileInputStream saveFile = new FileInputStream(filename);
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
    public final void saveOptions(String filename) {
        try {
            //open a file to write to
            FileOutputStream saveFile = new FileOutputStream(filename);
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

    // creates a rectangle of height and length in one color (ARGB)
    public BufferedImage createOneColorRectangle(int width, int height, int argbcolor, int borderLength, int borderColor) {
        BufferedImage rect = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
        int[] tmp = new int[width * height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                //if(Math.max(Math.abs((width-1) / 2 - x),Math.abs((height-1) / 2 - y)) > (width-1)/2-borderLength)
                if (Math.abs(width - x) <= borderLength || Math.abs(height - y) <= borderLength || x < borderLength || y < borderLength) //tmp[i]=argbcolor;
                {
                    rect.setRGB(x, y, borderColor);
                } else //tmp[i]=borderColor;
                {
                    rect.setRGB(x, y, argbcolor);
                }
            }
        }
        //rect.setRGB(0,0,width,height,tmp,0,1);
        return rect;
    }

    public BufferedImage createOneColorRectangle(int argbcolor) {
        return createOneColorRectangle(this.parcelLength, this.parcelLength, argbcolor, 0, 0);
    }

    public BufferedImage createOneColorRectangle(int argbcolor, int borderLength, int borderColor) {
        return createOneColorRectangle(this.parcelLength, this.parcelLength, argbcolor, borderLength, borderColor);
    }
    
    // takes a Matrix of rectangles and conjoins them to one image
    public BufferedImage createGameBoard(BufferedImage[][] imgMatrix) {
        BufferedImage tmp;
        BufferedImage tmp2 = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        for (BufferedImage[] imgList : imgMatrix) {
            tmp = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
            for (BufferedImage img : imgList) {
                tmp = attachImagesX(tmp,img);
            }
            tmp2 = attachImagesY(tmp2,tmp);

        }
        return tmp2;
    }

    // attaches a BufferedImage to the right of another one
    public BufferedImage attachImagesX(BufferedImage img1, BufferedImage img2) {
        if (img1.getWidth() > 1) {
            BufferedImage resultImage = new BufferedImage(img1.getWidth()
                    + img2.getWidth(), img1.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = resultImage.getGraphics();
            g.drawImage(img1, 0, 0, null);
            g.drawImage(img2, img1.getWidth(), 0, null);
            return resultImage;
        } else {
            return img2;
        }
    }

    // attaches a BufferedImage under another one
    public BufferedImage attachImagesY(BufferedImage img1, BufferedImage img2) {
        if (img1.getHeight() > 1) {
            BufferedImage resultImage = new BufferedImage(img1.getWidth(),
                    img1.getHeight() + img2.getHeight(),
                    BufferedImage.TYPE_INT_RGB);
            Graphics g = resultImage.getGraphics();
            g.drawImage(img1, 0, 0, null);
            g.drawImage(img2, 0, img1.getHeight(), null);
            return resultImage;
        } else {
            return img2;
        }
    }

    // converts component based argb values to an argb value to use BufferedImage.setRGB
    public int componentToARGB(int red, int green, int blue, int transparency) {
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
