/* FiveRenju.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.chess.renju.five;

import org.chess.game.GameModeFactory;
import org.chess.game.GameSuperviser;
import org.chess.game.SinglePlayerGame;

/**
 * <B>FiveRenju</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public class FiveRenju extends SinglePlayerGame{
	private final GameSuperviser superviser;
	
	
	public FiveRenju(GameSuperviser superviser){
		setMode(GameModeFactory.getGameMode(FiveRenjuMode.NAME));
		this.superviser=superviser;
	}

	@Override
	public void reset() {
		super.reset();
		mode.reset();
		board = new int[mode.getRow()][mode.getColumn()];
	}

	@Override
	public int getScore() {
		return 0;
	}

	@Override
	public void selecte(int x, int y) {
		if (!validPosition(x, y)) {
			return;
		}
		
		int selected = getPieceAt(x, y);
		if(selected != 0){
			return;
		}
		
		selectedX = x;
		selectedY = y;
		setSelected(true);
		
		
		int piece = FivePieceHelper.getPiece(superviser.currentPlayerSide()).getID();
		board[x][y]=piece;
		
		updateState(x,y);
		if(!isGameOver()){
			superviser.nextPlayer();
		}
		
	}
	
	private boolean isGameOver() {
		return state.equals("over");
	}

	public void updateState(int x, int y) {
		if(isGameOver(x,y)){
			setState("over");
		}else if(getEmptyCount()==0){
			setState("drawn");
		}
	}

	private boolean isGameOver(int x, int y) {
		boolean isGameOver = hasFivePiece(x, y, true, false, true, true)
		|| hasFivePiece(x, y, false, true, true, true)
		|| hasFivePiece(x, y, true, true, true, true)
		|| hasFivePiece(x, y, true, true, true, false);
		return isGameOver;
	}
	
	private boolean hasFivePiece(int x, int y, boolean horizontally,
			boolean vertically, boolean directionX, boolean directionY) {
		int counter = 0;
		int[][] diminish = new int[mode.getRow() + mode.getColumn()][2];

		counter = hasFivePiece(x, y, horizontally, vertically, directionX,
				directionY, diminish, counter);
		counter = hasFivePiece(x, y, horizontally, vertically, !directionX,
				!directionY, diminish, counter);

		if (++counter < 5) {
			return false;
		}

		return true;
	}

	private int hasFivePiece(int x, int y, boolean horizontally,
			boolean vertically, boolean directionX, boolean directionY,
			int[][] diminish, int counter) {
		int currentX, currentY;
		int factorX = directionX ? 1 : -1;
		int factorY = directionY ? 1 : -1;
		int old = board[x][y];
		int diminishNum = mode.getRow() + mode.getColumn();
		for (int i = 1; i < diminishNum; i++) {
			currentX = horizontally ? x + i * factorX : x;
			currentY = vertically ? y + i * factorY : y;
			if (currentX < 0 || currentY < 0 || currentX >= mode.getColumn()
					|| currentY >= mode.getColumn() || board[currentX][currentY] != old) {
				break;
			}
			if (board[currentX][currentY] == old) {
				diminish[counter][0] = currentX;
				diminish[counter][1] = currentY;
				counter++;
			}
		}
		return counter;
	}


}
