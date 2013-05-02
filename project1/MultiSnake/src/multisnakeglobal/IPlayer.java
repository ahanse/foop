
package multisnakeglobal;
 
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
    void visit(AnnounceNickMessage ms); 
    void visit(KeyChangedMessage ms); 
    void visit(SetIdMessage ms); 
    void visit(UpdateGameDataMessage ms); 
}
