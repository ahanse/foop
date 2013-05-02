
package multisnakeglobal;
 
/**
 *
 * @author hanse
 */
public interface IPlayer {
    ConnectionState getStatus();
    String getNick();
    KeyChange getChangedKey();
    void setChangedKey(KeyChange k);
    void setId(int id);
    void updateGameData(IGameData gd);
    IGameData getGameData();
    int getId();
    void visit(AnnounceNickMessage ms); 
    void visit(KeyChangedMessage ms); 
    void visit(SetIdMessage ms); 
    void visit(UpdateGameDataMessage ms); 
}
