
package multisnakeglobal;
 
/**
 *
 * @author hanse
 */
public interface IPlayer {
    ConnectionState getState();
    String getNick();
    void setNick(String nickname);
    Direction getChangedKey();
    void setChangedKey(Direction k);
    void setId(int id);
    void updateGameData(IGameData gd);
    IGameData getGameData();
    int getId();
    void visit(AnnounceNickMessage ms); 
    void visit(KeyChangedMessage ms); 
    void visit(SetIdMessage ms); 
    void visit(UpdateGameDataMessage ms); 
}
