/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

/**
 *
 * @author Benedikt
 */
public class ServerOptions {
    private String tick;
    private String dimX;
    private String dimY;

    public ServerOptions(String tick, String dimX, String dimY) {
        this.tick = tick;
        this.dimX = dimX;
        this.dimY = dimY;
    }

    public String getTick() {
        return tick;
    }

    public String getDimX() {
        return dimX;
    }

    public String getDimY() {
        return dimY;
    }
}
