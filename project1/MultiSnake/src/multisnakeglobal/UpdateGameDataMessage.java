/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

/**
 *
 * @author hanse
 */
public class UpdateGameDataMessage implements INetworkMessage {
    public IGameData gameData;
    public UpdateGameDataMessage(IGameData gameData) {this.gameData=gameData;}
    
}
