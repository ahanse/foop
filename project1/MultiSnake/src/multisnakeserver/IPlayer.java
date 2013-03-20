
package multisnakeserver;
 
import multisnakeglobal.IConnectionState;
import multisnakeglobal.IGameData;
import multisnakeglobal.KeyChange;
/**
 *
 * @author hanse
 */
public interface IPlayer {
 
    IConnectionState getStatus();
    String getNick();
    KeyChange getChangedKeys();
    void setId(int id);
    void updateGameData(IGameData gd);
    
}
