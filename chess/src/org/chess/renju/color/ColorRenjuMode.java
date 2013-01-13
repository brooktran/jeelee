/*
 * GameMode.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran All rights reserved.
 * 
 * The copyright of this software is own by the authors. You may not use, copy
 * or modify this software, except in accordance with the license agreement you
 * entered into with the copyright holders. For details see accompanying license
 * terms.
 */
package org.chess.renju.color;

import java.util.Random;

import org.chess.game.AbstractGameMode;
import org.zhiwu.app.config.AppPreference;

/**
 * <B>GameMode</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-17 created
 * @since Renju Ver 1.0
 * 
 */
public class ColorRenjuMode extends AbstractGameMode{
	public static final String CHALLENGE = "challenge";
	public static final String SURVIAL = "survival";
	public static final String RANDOM = "random";
	
	// //game configurate
	protected int pieceNum = 3; // the number of chess to add in each time;
	protected int diminishedScore = 10;
	protected int diminishNum = 5;
	protected boolean checkPassable = true;
	protected int pieceType = 6;
	protected int initialNum = pieceNum;
	protected boolean diminishSystemPiece;
	protected int obstacle ;
	protected int nextScore = 0;
	
//	private final String name;

	public ColorRenjuMode(AppPreference pref) {
		super(pref);
	}
	
	@Override
	public void init() {
		super.init();
		pieceNum = pref.getInteger("pieceNum");
		pieceType = pref.getInteger("pieceType");
		
		initialNum = pref.getInteger("initialNum");
		initialNum = initialNum == 0 ? pieceNum : initialNum;
		
		diminishedScore = pref.getInteger("diminishedScore");
		diminishNum = pref.getInteger("diminishNum");
		diminishSystemPiece=pref.getBoolean("diminish.system.piece");
		
		obstacle=pref.getInteger("obstacle");
		checkPassable = pref.getBoolean("checkPassable");
		nextScore= pref.getInteger("next.score");
		
	}
	
	@Override
	public void reset() {
		if(pref.getName().equals(RANDOM)){
			random();
		}
	}
	
	public void random(){
		Random random = new Random();
		
		pieceType = random.nextInt(ColorPieceHelper.pieces.length-4)+3;
		pieceNum = random.nextInt(8)+2;
		
		
		initialNum = random.nextInt(row*column/5);
		
		row = 6+random.nextInt(10);
		column = 6+random.nextInt(10);
		
		diminishNum = row<column?row/2:column/2;
		diminishedScore = 10;
		diminishSystemPiece = true;
		passScore = pref.getInteger("pass.score");
		obstacle= ColorPieceHelper.obstacles.length>1?random.nextInt(6):0;
		
		checkPassable = true;
		isOpenAI = false;
		
		nextScore= pref.getInteger("next.score");
		nextModeName="";
		
		displayName =pref.get("name");
		description="消除:"+diminishNum+"个, 过关:"+passScore+"分, 每次:"+pieceNum+"个, "+
				"种类:"+pieceType+", 难度:"	+getDifficultDegree();//TODO I18N
		
		if (highScore == null) {
			String highString = pref.get("high.score");
			String[] scores = highString.split(",");
			highScore = new int[30];
			for (int i = 0, p=0,j = scores.length; i < j; i++) {
				try {
					highScore[p++] = Integer.parseInt(scores[i]);
				} catch (Exception e) {
				}
			}
		}
		
	}
	
	public int getNextScore() {
		return nextScore;
	}

	public void setNextScore(int nextScore) {
		this.nextScore = nextScore;
	}

	

	private String getDifficultDegree() {
		double degree =0;
		double all = row*column;
		degree += Math.pow(initialNum*6-all,3);
		degree += Math.pow(obstacle*25-all,3);
		degree += Math.pow(pieceNum*2-diminishNum,3);
		degree += Math.pow(pieceType*9-all,3) ;
		
//		int p= degree>0?1:-1;
//		degree = Math.pow(degree, 1.0/3.0);
		degree /=1000;
		return (int)degree+"";
	}

	public boolean isDiminishSystemPiece() {
		return diminishSystemPiece;
	}

	public void setDiminishSystemPiece(boolean isDiminishSystemPiece) {
		this.diminishSystemPiece = isDiminishSystemPiece;
	}

	public int getPieceNum() {
		return pieceNum;
	}

	public void setPieceNum(int pieceNum) {
		this.pieceNum = pieceNum;
	}

	public int getDiminishedScore() {
		return diminishedScore;
	}

	public void setDiminishedScore(int diminishedScore) {
		this.diminishedScore = diminishedScore;
	}

	public int getDiminishNum() {
		return diminishNum;
	}

	public void setDiminishNum(int diminishNum) {
		this.diminishNum = diminishNum;
	}

	public boolean isCheckPassable() {
		return checkPassable;
	}

	public void setCheckPassable(boolean checkPassable) {
		this.checkPassable = checkPassable;
	}

	public int getPieceType() {
		return pieceType;
	}

	public void setPieceType(int pieceType) {
		this.pieceType = pieceType;
	}

	public int getInitialNum() {
		return initialNum;
	}

	public void setInitialNum(int initialNum) {
		this.initialNum = initialNum;
	}
}
