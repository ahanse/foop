/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeglobal;

/**
 *
 * @author hanse
 */

public interface INetworkMessage extends java.io.Serializable {
   void accept(IPlayer player);
}
