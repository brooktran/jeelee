/*
 * Renju.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran All rights reserved.
 * 
 * The copyright of this software is own by the authors. You may not use, copy
 * or modify this software, except in accordance with the license agreement you
 * entered into with the copyright holders. For details see accompanying license
 * terms.
 */
package org.chess.renju.color;

import java.io.File;

import org.chess.app.ChessApplication;
import org.chess.game.GameMode;
import org.chess.renju.utils.AudioPlayer;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.utils.AppLogging;

/**
 * <B>Renju</B>
 *  play sound end change the state of the Renju
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.renju.chess Ver 1.0
 * 
 */
public class ColorRenjuProxy extends ColorRenju{
	private final AudioPlayer audioPlayer;
	private final String soundPath;
	protected boolean sound; 
	
	public ColorRenjuProxy() {
		audioPlayer = new AudioPlayer();
		AppPreference pref = AppManager.getPreference(ChessApplication.class.getName());
		soundPath = pref.get("sound.path");
		sound=pref.getBoolean("sound");
		
		setState("begin");
	}
	
	public void begin(){
		play("background");
	}
	
	@Override
	public void reset() {
		super.reset();
		firePropertyChange("board", null, board);
	}
	
	@Override
	public void setMode(GameMode mode) {
		firePropertyChange("mode", this.mode,  mode);
		super.setMode(mode);
	}
	
	@Override
	public void setScore(int score) {
		firePropertyChange("score", this.score,  score);
		super.setScore(score);
	}	
	
	@Override
	protected void setPieceAt(int piece, int x, int y, boolean checkDiminish) {
		super.setPieceAt(piece, x, y, checkDiminish);
		firePropertyChange("board", null, board);
	}
	
	@Override
	public void selecte(int x, int y) {
		super.selecte(x, y);
		firePropertyChange("board", null, board);
		updateState();
	}
	
	@Override
	public void next() {
		super.next();
		updateState();
	}
	
	private void play(String name) {
		if(!sound){
			return;
		}
		try {
			audioPlayer.play(getSound(name));
		} catch (Exception e) {
			AppLogging.handleException(e);
		}
	}

	private File getSound(String name) {
		return new File(soundPath+name+".wav");	
	}
	
	
	@Override
	public void setSelected(boolean isSelected) {
		super.setSelected(isSelected);
		if(isSelected){
			play("select");
		}
	}
	
	
	@Override
	public void next(int pieceNumber) {
		super.next(pieceNumber);
		play("next");
	}
	
	
	@Override
	protected void diminish(int[][] diminish, int counter) {
		super.diminish(diminish, counter);
		if(counter>getMode().diminishNum){
			play("highest");
		}else{
			play("diminish");
		}
		
	}
	
	@Override
	public boolean canMove(int srcX, int srcY, int desX, int desY) {
		boolean resval= super.canMove(srcX, srcY, desX, desY);
		if(!resval){
			play("cannot");
		}
		return resval;
	}
	
	
	@Override
	protected void setState(String state) {
		if(state.equals("over") || state.equals("pass")){
			play("background");
		}
		super.setState(state);
	}
	
	public void updateState() {
		if(getEmptyCount()<=0){
			mode.addScore(score);
			score=0;
			setState("over");
			firePropertyChange("high.score", null, mode.getHighScoreString());
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
