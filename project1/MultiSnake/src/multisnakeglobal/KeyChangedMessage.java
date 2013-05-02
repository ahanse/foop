/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

/**
 *
 * @author hanse
 */
public class KeyChangedMessage implements INetworkMessage {
    public Direction keyChange = null;
    public KeyChangedMessage(Direction kc) {
        this.keyChange = kc;
    }
    @Override
    public void accept(IPlayer p) {
        p.visit(this);
    }
}
