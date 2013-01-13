/* AbstractGame.java 1.0 2010-2-2
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
package org.chess.game;

import org.zhiwu.utils.AbstractBean;

/**
 * <B>AbstractGame</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public abstract class AbstractGame extends AbstractBean implements Game{

	protected GameMode mode;
	protected int score = 0;
	protected int[][] board;
	protected String state;
	
	public AbstractGame() {
		setState("begin");
	}

	public void setMode(GameMode mode) {
		this.mode =mode;
		board=new int[mode.getRow()][mode.getColumn()];
	}

	@Override
	public int getScore() {
		return score;
	}

	public void addScore(int score) {
		setScore(this.score+score);
	}

	public void setScore(int score) {
		this.score = score;
		checkPassLevel();
	}

	private boolean checkPassLevel() {
		if(mode.getPassScore() ==0){
			return false;
		}
		if(score >= mode.getPassScore()){
			setState("pass");
			setMode(mode.next());
			return true;
		}
		return false;
	}

	@Override
	public void reset() {
		setState("begin");
	}

	@Override
	public int getPieceAt(int x, int y) {
		return board[x][y];
	}

	@Override
	public GameMode getMode() {
		return mode;
	}

	protected void setState(String state) {
		firePropertyChange("state", null,this.state=state);
	}

	@Override
	public String getState() {
		return state;
	}

	
	protected boolean validPosition(int x, int y) {
		if (x < 0 || y < 0 || x >= mode.getRow() || y >= mode.getColumn() || board[x][y]<0) {
			return false;
		}
		return true;
	}
	
	public int getEmptyCount() {
		int counter=0;
		for(int i=0;i<mode.getRow();i++){
			for(int j=0;j<mode.getColumn();j++){
				counter += board[i][j]==0?1:0;
			}
		}
		return counter;
	}
	
	public void setPieceAt(int x, int y, int value) {
		board[x][y]=value;
		firePropertyChange("board", null, board);
	}

}
