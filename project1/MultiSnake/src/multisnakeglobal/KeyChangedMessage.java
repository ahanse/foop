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
    public KeyChange keyChange = null;
    public KeyChangedMessage(KeyChange kc) {
        this.keyChange = kc;
    }
    @Override
    public void accept(IPlayer p) {
        p.visit(this);
    }
}
