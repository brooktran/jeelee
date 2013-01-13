/* Renju.java 1.0 2010-2-2
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
package org.chess.renju.color;

import java.util.Random;

import org.chess.game.SinglePlayerGame;

/**
 * <B>Renju</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-17 created
 * @since Renju Ver 1.0
 * 
 */
public class ColorRenju extends SinglePlayerGame  {
	protected static final Random random = new Random();;
	protected int[] nextPieces=new int[0];
	protected boolean alreadyDiminish;
	
	 
	public int[] getNextPieces() {
		return nextPieces;
	}
	
	@Override
	public ColorRenjuMode getMode() {
		return (ColorRenjuMode) super.getMode();
	}
	
	@Override
	public void selecte(int x, int y) {
		if (!validPosition(x, y)) {
			return;
		}
		int selected = getPieceAt(x, y);

		if (selected != 0) {
			selectedX = x;
			selectedY = y;
			setSelected(true);
		}else {
			// try moving
			if (isSelected && canMove(selectedX, selectedY, x, y)) {
				move(selectedX, selectedY, x, y,true);
				if(!alreadyDiminish){
					next(getMode().pieceNum);
				}
				isSelected = false;
			}	
		} 
	}
	public void next() {
		addScore(getMode() .nextScore);
		next(getMode() .pieceNum);
	}
	

	public void next(int pieceNumber) {
		pushNext(nextPieces);
		preparePieces(pieceNumber) ;
	}
	
	@Override
	public void reset() {
		super.reset();
		
		mode.addScore(score);
		setScore(-getMode() .nextScore);
		
		mode.reset();
		board = new int[mode.getRow()][mode.getColumn()];
		
		prepareObstcale();
		preparePieces(getMode().initialNum);
		
		next();
	}


	public void preparePieces(int pieceNumber) {
		nextPieces=getMode().isAIOpen()? getAIPiece(pieceNumber):getRandomPiece(pieceNumber,getMode().pieceType,true);
	}
	public void prepareObstcale() {
		pushNext(getRandomPiece(getMode().obstacle,ColorPieceHelper.obstacles.length-1,false));
	}
	
	private int[] getAIPiece(int pieceNumber) {
		return getRandomPiece(pieceNumber,getMode().pieceType,true);
	}

	private void pushNext(int[] pieces) {
		int chessNum = getEmptyCount();
		chessNum= chessNum<pieces.length?chessNum:pieces.length;
		int piecePointer =0;
		while (piecePointer < chessNum ) {
			int x = random.nextInt(mode.getRow() * mode.getColumn()) % mode.getRow();
			int y = random.nextInt(mode.getRow() * mode.getColumn()) % mode.getColumn();
			if (board[x][y] == 0) {
				setPieceAt(pieces[piecePointer],x,y,getMode().diminishSystemPiece);
				piecePointer++;
			}
		}
	}

	private int[] getRandomPiece(int pieceNumber, int range,boolean  factor) {
		int[] pieces = new int[pieceNumber];
		int flag=factor?1:-1;
		for (int i = 0; i < pieceNumber; i++) {
			int id= flag*(random.nextInt(range)+1);
			if(id ==0){
				i--;
				continue;
			}
			pieces[i] = ColorPieceHelper.getPieceID(id);
		}
		return pieces;
	}

	protected void setPieceAt(int piece, int x, int y, boolean checkDiminish) {
		board[x][y] = piece;
		if(checkDiminish){
			alreadyDiminish=diminish(x, y);
		}
	}

	public boolean canMove(int srcX, int srcY, int desX, int desY) {
		if (!getMode().checkPassable) {
			return board[desX][desY] == 0;
		}
		int piece = board[srcX][srcY];
		board[srcX][srcY] = 0;

		int[][] open = new int[mode.getRow() * mode.getColumn() / 2][2];
		int[][] closed = new int[mode.getRow() * mode.getColumn()][2];
		int[][] path = new int[mode.getRow() * mode.getColumn() / 2][2];
		int[][] around = new int[4][2];

		int closedPointer = -1, pathPointer = -1, aroundPointer;

		int openPointer = 0;
		open[0][0] = srcX;
		open[0][1] = srcY;

		do {
			// getMinimizeDistancePos()
			int pos = -1, p = 0, min = Integer.MAX_VALUE;
			for (; p <= openPointer; p++) {
				int dis = getDistance(open[p][0], open[p][1], desX, desY);
				if (dis < min) {
					min = dis;
					pos = p;
				}
			}
			if (pos == -1) {
				break;
			}

			int x = open[pos][0];
			int y = open[pos][1];
			open[pos][0] = open[openPointer][0];
			open[pos][1] = open[openPointer][1];
			openPointer--;

			// ///

			if (x == desX && y == desY) {
				board[srcX][srcY] = piece;
				return true;
			}

			closedPointer++;
			closed[closedPointer][0] = x;
			closed[closedPointer][1] = y;

			// pathPointer++;
			// path[pathPointer][0] = x;
			// path[pathPointer][1] = y;

			aroundPointer = prepareAround(x, y, around);
			int validAroundCount = -1;
			for (int i = 0; i <= aroundPointer; i++) {
				if (arrayContains(closed, closedPointer, around[i][0],
						around[i][1], desX, desY)
						|| arrayContains(open, openPointer, around[i][0],
								around[i][1], desX, desY)) {
					continue;
				}

				if (getDistance(around[i][0], around[i][1], desX, desY) == Integer.MAX_VALUE) {
					closedPointer++;
					closed[closedPointer][0] = around[i][0];
					closed[closedPointer][1] = around[i][1];
					continue;
				}

				openPointer++;
				open[openPointer][0] = around[i][0];
				open[openPointer][1] = around[i][1];

				validAroundCount++;
			}
			// / if aroundPointer<0 path.pop
		} while (openPointer >= 0);

		board[srcX][srcY] = piece;
		return false;
	}

	private boolean arrayContains(int[][] array, int pointer, int x, int y,
			int desX, int desY) {
		for (int i = 0; i <= pointer; i++) {
			if (array[i][0] == x && array[i][1] == y) {
				return true;
			}
		}
		return false;
	}

	private int getDistance(int x, int y, int desX, int desY) {
		return board[x][y] == 0 ? (x - desX) * (x - desX) + (y - desY)
				* (y - desY) : Integer.MAX_VALUE;
	}

	private int prepareAround(int x, int y, int[][] around) {
		int aroundPointer = -1;
		int[][] postions = new int[][] { { x - 1, y }, { x, y - 1 },
				{ x + 1, y }, { x, y + 1 } };

		for (int i = 0; i < 4; i++) {
			if (validPosition(postions[i][0], postions[i][1])) {
				aroundPointer++;
				around[aroundPointer][0] = postions[i][0];
				around[aroundPointer][1] = postions[i][1];
			}
		}
		return aroundPointer;
	}

	

	public void move(int srcX, int srcY, int desX, int desY, boolean checkDiminish) {
		int src = getPieceAt(srcX, srcY);
		setPieceAt(0, srcX, srcY,false);
		setPieceAt(src, desX, desY,checkDiminish);
	}

	// ///////// diminish
	private boolean diminish(int x, int y) {
		alreadyDiminish= canDiminish(x, y, true, false, true, true)
				|| canDiminish(x, y, false, true, true, true)
				|| canDiminish(x, y, true, true, true, true)
				|| canDiminish(x, y, true, true, true, false);
		return alreadyDiminish;

	}

	private boolean canDiminish(int x, int y, boolean horizontally,
			boolean vertically, boolean directionX, boolean directionY) {
		int counter = 0;
		int[][] diminish = new int[mode.getRow() + mode.getColumn()][2];

		counter = canDiminish(x, y, horizontally, vertically, directionX,
				directionY, diminish, counter);
		counter = canDiminish(x, y, horizontally, vertically, !directionX,
				!directionY, diminish, counter);

		if (++counter < getMode().diminishNum) {
			return false;
		}

		diminish[counter - 1][0] = x;
		diminish[counter - 1][1] = y;
		diminish(diminish, counter);
		return true;
	}

	private int canDiminish(int x, int y, boolean horizontally,
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

	protected void diminish(int[][] diminish, int counter) {
		for (int i = 0; i < counter; i++) {
			board[diminish[i][0]][diminish[i][1]] = 0;
		}
		
		int addScore =getMode().diminishedScore;
		addScore += counter>getMode().diminishNum? (counter-getMode().diminishNum)*getMode().nextScore:0;
		addScore(addScore);
	}
}
