/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

/**
 *
 * @author Benedikt
 */
import java.util.List;

public interface ISnake {
    List<Point> getKoordinaten();
    Point getHead();
    int getPriority();
    int getID();
    String getName();
}