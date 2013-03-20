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
import java.util.Date;

public interface IGameData extends java.io.Serializable{
    List<ISnake> getSnakes();
    GameState getStatus();
    Point getDimensions();
    Date getTimeStamp();
}
