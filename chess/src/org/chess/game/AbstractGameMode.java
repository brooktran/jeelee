/* AbstractGameMode.java 1.0 2010-2-2
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

import java.util.Arrays;

import org.zhiwu.app.config.AppPreference;
import org.zhiwu.utils.ArrayUtils;

/**
 * <B>AbstractGameMode</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public class AbstractGameMode implements GameMode{

	protected int row = 10;
	protected int column = 10;
	protected boolean isOpenAI = false;
	protected String description = "";
	protected int[] highScore;
	protected String displayName;
	protected int passScore;
	protected long failTime;
	protected String nextModeName;
	
	/// various
	protected final AppPreference pref;

	public AbstractGameMode(AppPreference pref) {
		this.pref =pref;
	}

	@Override
	public void init() {
		row = pref.getInteger("row");
		column = pref.getInteger("column");
		passScore = pref.getInteger("pass.score");
		isOpenAI = pref.getBoolean("AIOpen");
		
		nextModeName=pref.get("next");
		
		displayName =pref.get("name");
		description=pref.get("description");
		
		String highString = pref.get("high.score");
		String[] scores=highString.split(",");
		highScore=new int[30];
		for(int i=0,p=0,j=scores.length;i<j;i++){
			try {
				highScore[p++] = Integer.parseInt(scores[i]);
			} catch (Exception e) {
			}
		}		
	}
	
	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void addScore(int score) {
		if(highScore[0]>=score){
			return;
		}
		highScore[0]= score;
		Arrays.sort(highScore);
		
		pref.put("high.score", getHighScoreString());	
	}

	public String getHighScoreString() {
		return ArrayUtils.arrayToCommaString(highScore);
	}

	public boolean isOpenAI() {
		return isOpenAI;
	}

	public void setOpenAI(boolean isOpenAI) {
		this.isOpenAI = isOpenAI;
	}

	public String getName() {
		return displayName;
	}

	public void setName(String name) {
		this.displayName = name;
	}

	@Override
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	@Override
	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public boolean isAIOpen() {
		return isOpenAI;
	}

	public void setAIOpen(boolean aIOpen) {
		isOpenAI = aIOpen;
	}

	@Override
	public int getPassScore() {
		return passScore;
	}

	public void setPassScore(int passScore) {
		this.passScore = passScore;
	}

	public long getFailTime() {
		return failTime;
	}

	public void setFailTime(long failTime) {
		this.failTime = failTime;
	}

	public String getNextModeName() {
		return nextModeName;
	}

	public void setNextModeName(String nextModeName) {
		this.nextModeName = nextModeName;
	}
	
	@Override
	public int[] getHighScore() {
		return highScore;
	}

	@Override
	public String toString() {
		return displayName;
	}

	@Override
	public GameMode next(){
		if(nextModeName != null && nextModeName.trim().length()>0){
			GameMode mode = GameModeFactory.getGameMode(nextModeName);
			mode.init();
			return mode;
		}
		return this;
	}

	@Override
	public void reset() {
	}

	
}
