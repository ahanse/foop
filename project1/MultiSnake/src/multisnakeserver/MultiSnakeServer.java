/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package multisnakeserver;

import multisnakeglobal.*;

/**
 *
 * @author hanse
 */
public class MultiSnakeServer {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        int dimX,dimY,numOfPlayers,tick,numOfBots;
        if(args.length!=0) {
            tick = Integer.parseInt(args[0]);
            dimX = Integer.parseInt(args[1]);
            dimY = Integer.parseInt(args[2]);
            numOfPlayers = Integer.parseInt(args[3]);
            numOfBots = Integer.parseInt(args[4]);
        }
        else {
            tick = 300;
            dimX = 30;
            dimY = 30;
            numOfPlayers = 1;
            numOfBots = 0;
        }
        GameData gd = new GameData(new Point(dimX,dimY));
        NetworkServer ns = new NetworkServer(numOfPlayers);
        multisnakeglobal.IPlayer[] players = ns.getPlayers();

	Bot[] bots = new Bot[numOfBots];
        
        for(int i=0;i<numOfBots;i++)
        {
            bots[i]=new Bot();
        }

        //wait until all clients are connected
        boolean allReady; 
        do {
            allReady = true; 
            for(multisnakeglobal.IPlayer p:players) 
            {
                allReady &= (!(p.getState()==ConnectionState.NOTREADY));
            }
            Thread.sleep(100);
        } while(!allReady);
        gd.startGame(numOfPlayers + numOfBots);
        Thread.sleep(500);
        for(int p=0; p<players.length; p++) {
            players[p].setId(p);
        }
        while(true) {
            Thread.sleep(tick);
            for(int p=0; p<players.length; p++) 
            {
                Direction k = players[p].getChangedKey();
                if(k!=null)
                    gd.setSnakeDirection(p, k);
            }

            for(int i = 0; i < numOfBots; ++i) {
            	Direction k = bots[i].play();
                if(k!=null)
                    gd.setSnakeDirection(i + numOfPlayers, k);
            }


            gd.playTurn();
            for(int p=0; p<players.length; p++) 
            {
                players[p].updateGameData(gd);
            }
        }
    }
}
