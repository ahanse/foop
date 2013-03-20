/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeclient;

import multisnakeglobal.IConnectionState;
import multisnakeglobal.IGameData;
/**
 *
 * @author Benedikt
 */
public interface INetworkClient {
    IGameData getGameData();
    int getOwnID();
    IConnectionState getStatus();
}
