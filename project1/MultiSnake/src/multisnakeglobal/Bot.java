package multisnakeglobal;

import java.util.Random;
/**
 *
 * @author Thomas
 */
public class Bot {
	Random generator_;
	int ownID_;
	// DO NOT MODIFY gd_!
	GameData gd_;
	public Bot(GameData gd, int ownID) {
		generator_ = new Random();
		ownID_ = ownID;
		gd_ = gd;
	}

	public Direction play() {
		int move = generator_.nextInt(25);
		switch(move) {
			case 1: return Direction.UP;
			case 2: return Direction.DOWN;
			case 3: return Direction.LEFT;
			case 4: return Direction.RIGHT;
			default: return null;
		}
	}
}
