
package multisnakeserver;
 
import multisnakeglobal.IGameData;
import multisnakeglobal.KeyChange;
/**
 *
 * @author hanse
 */
public interface IPlayer {
    public enum playerStatus {NOTREADY, READY, DISCONNECTED};
    
    playerStatus getStatus();
    String getNick();
    KeyChange getChangedKeys();
    void setId(int id);
    void updateGameData(IGameData gd);
    
}
