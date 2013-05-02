/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

/**
 *
 * @author hanse
 */
public class SetIdMessage implements INetworkMessage {
    public int id;
    public SetIdMessage(int id) {this.id=id;}
    @Override
    public void accept(IPlayer p) {
        p.visit(this);
    }
}
