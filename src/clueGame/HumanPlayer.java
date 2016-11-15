package clueGame;

import java.util.Map;
import java.util.Set;

public class HumanPlayer extends Player{

	@Override
	public void makeMove(Set<BoardCell> targets, Board board) {
		for(BoardCell c:targets) {
			c.isTarget = true;
		}
		mustFinish = true;
	}
	public String getLocationType(Board board) {
		Map <Character, String> legend = board.getLegend();
		return legend.get(board.getCellAt(row, column).getInitial());
	}
}
