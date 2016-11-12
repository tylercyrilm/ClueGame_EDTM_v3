package clueGame;

import java.util.Set;

public class HumanPlayer extends Player{

	@Override
	public void makeMove(Set<BoardCell> targets) {
		for(BoardCell c:targets) {
			c.isTarget = true;
		}
		mustFinish = true;
	}

}
