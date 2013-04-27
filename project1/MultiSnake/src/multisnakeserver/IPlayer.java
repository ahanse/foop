
package multisnakeserver;
 
import multisnakeglobal.ConnectionState;
import multisnakeglobal.IGameData;
import multisnakeglobal.KeyChange;
/**
 *
 * @author hanse
 */
public interface IPlayer {
 
    ConnectionState getStatus();
    String getNick();
    KeyChange getChangedKeys();
    void setId(int id);
    void updateGameData(IGameData gd);
    
}
