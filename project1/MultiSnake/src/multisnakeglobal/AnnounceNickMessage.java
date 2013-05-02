/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

/**
 *
 * @author hanse
 */
public class AnnounceNickMessage implements INetworkMessage {
    public String nick="test";
    public AnnounceNickMessage(String nick) {this.nick = nick;}
    @Override
    public void accept(IPlayer p) {
        p.visit(this);
    }
}
