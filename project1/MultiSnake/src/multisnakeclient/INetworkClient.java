/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import multisnakeglobal.ConnectionState;
import multisnakeglobal.IGameData;
import multisnakeglobal.KeyChange;
/**
 *
 * @author Benedikt
 */
public interface INetworkClient {
    IGameData getGameData();
    int getOwnID();
    ConnectionState getStatus();
    void setKeyChange(KeyChange key);
}
