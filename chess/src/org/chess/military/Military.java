/* Military.java 1.0 2010-2-2
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
package org.chess.military;

import java.awt.Point;
import java.util.Random;

import org.chess.game.AbstractGame;
import org.chess.game.GameMode;
import org.chess.game.GameModeFactory;

/**
 * <B>Military</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public class Military extends AbstractGame{

	public Military() {
		setMode(GameModeFactory.getGameMode(MilitaryMode.NAME));
	}
	
	@Override
	public void setMode(GameMode mode) {
		super.setMode(mode);
		initInvalidArea();
		
		printboard();
	}

	private void initInvalidArea() {
		initInvalidArea(0,0);
		initInvalidArea(11,0);
		initInvalidArea(0,11);
		initInvalidArea(11,11);
		initInvalidArea(6,6);
		
		for(int i=6;i<12;i+=2){
			for(int j=6;j<12;j+=2){
				board[i][j]=0;
			}
		}
		
		for(int i=0;i<6;i+=2){
			for(int j=0;j<6;j+=2){
				board[i][j]=-1;
			}
		}
	}

	private void initInvalidArea(int x, int y) {
		for(int i=0;i<6;i++){
			for(int j=0;j<6;j++){
				board[i+x][j+y]=-1;
			}
		}
	}
	
	@Override
	public MilitaryMode getMode() {
		return (MilitaryMode) super.getMode();
	}

	
	///  add player
	
	public void addPlayer(int index) {
		initBoard(index);
		
		printboard();
	}

	private void initBoard(int index) {
		if(index<0 || index >3){
			throw new IllegalArgumentException(index+"");
		}
		int[] pieces =getBoard(index);
		
		
		switch (index) {
		case 0:
			addPieces(6, 11,1,1, pieces);
			break;
		case 1:
			addPieces(10, 5,-1,-1, pieces);
			break;
		case 2:
			addPieces(5, 6,1,-1, pieces);
			break;
		case 3:
			addPieces(11, 10,-1,1, pieces);
			break;
		}
	}

	
	private void addPieces(int x, int y,int horizontal, int vertical, int[] pieces) {
		int X=x,Y=y;
		boolean factor = horizontal*vertical>0 ;
		for(int i=0;i<6;i++){
			for(int j=0;j<5;j++){
				if(pieces[i*5+j] >0){
					board[X][Y]=pieces[i*5+j];
				}
				X+= factor ? vertical:0;
				Y+= factor ? 0:horizontal;
			}
			Y = factor ? Y+horizontal :y;
			X = factor ? x :X+vertical;
		}
	}

	private int[] getBoard(int index){
		int[] board = getInitBoard();
		int[] candidateBoard;
		int p ;
		Random random =new Random();
		// flag 1  					1
		p = random.nextBoolean()?28:26;
		board[p]=MilitaryPieceHelper.getPiece(
				MilitaryPiece.VALUE_FLAG,index);
		
		// mine 3					4
		candidateBoard=new int[]{MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_MINE,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_MINE,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_MINE,index)};
		addCandidatePiece(board,candidateBoard,10,random);
		
		// bomb		2				6		
		candidateBoard=new int[]{MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_BOMB,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_BOMB,index)};
		addCandidatePiece(board,candidateBoard,25,random);
		
		// else		19				25		
		candidateBoard=new int[]{
				/// VALUE_ENGINEER	3	9
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_ENGINEER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_ENGINEER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_ENGINEER,index),
				
				/// VALUE_PLATOON_COMMANDER 3 12
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_PLATOON_COMMANDER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_PLATOON_COMMANDER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_PLATOON_COMMANDER,index),
				
				/// VALUE_COMPANY_COMMANDER 3 15
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_COMPANY_COMMANDER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_COMPANY_COMMANDER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_COMPANY_COMMANDER,index),
				
				/// VALUE_COMPANY_COMMANDER 2 17
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_BATTALION_COMMANDER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_BATTALION_COMMANDER,index),
				
				/// VALUE_REGIMENT_COMMANDER 2 19
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_REGIMENT_COMMANDER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_REGIMENT_COMMANDER,index),
				
				/// VALUE_BRIGADIER_COMMANDER 2 21
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_BRIGADIER_COMMANDER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_BRIGADIER_COMMANDER,index),
				
				/// VALUE_DIVISION_COMMANDER 2 23
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_DIVISION_COMMANDER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_DIVISION_COMMANDER,index),
				
				/// VALUE_ARMY_COMMANDER VALUE_HEAD_COMMANDER 2 25
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_ARMY_COMMANDER,index),
				MilitaryPieceHelper.getPiece(MilitaryPiece.VALUE_HEAD_COMMANDER,index),
				};
		addCandidatePiece(board,candidateBoard,30,random);
		return board;
	}

	private void addCandidatePiece(int[] board, int[] candidateBoard, int minPos, Random random) {
		int counter=0,p,q=30-minPos;
		while(counter<candidateBoard.length){
			p=random.nextInt(minPos)+q;
			if(board[p]!=0){
				continue;
			}
			board[p]=candidateBoard[counter++];
		}		
	}

	private int[] getInitBoard() {
		int[] board = new int[30];
		board[6]=-1;
		board[8]=-1;
		board[12]=-1;
		board[16]=-1;
		board[18]=-1;
		return board;
	}
	
	
	private void printboard(){
		System.out.println();
		for (int i = 0; i < 17; i++) {
			for (int j = 0; j < 17; j++) {
				if (board[i][j] == -1) {
					System.out.print("－");
				} else if (board[i][j] == 0) {
					System.out.print("⊙");
				} else {
					System.out.print(MilitaryPieceHelper
							.getPieceName(board[i][j]));
				}
			}
			System.out.println();
		}
	}
	
	public static final byte NON_ = -1; // non
	public static final byte BASE = 0;// BASE LOAD
	public static final byte STAT = 1;// STATION
	public static final byte CAMP = 2; ///capm
	public static final byte RAIL = 3;// RAIL LOAD
	public static final byte HEAD = 4;// HEADQUARTER

	private static final byte[][] MILITARY_BOARD=new byte[][]{
		{NON_,NON_,NON_,NON_,NON_,NON_,BASE,HEAD,BASE,HEAD,BASE,NON_,NON_,NON_,NON_,NON_,NON_},//0
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,RAIL,RAIL,RAIL,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//1
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,CAMP,BASE,CAMP,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//2
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,BASE,CAMP,BASE,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//3
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,CAMP,BASE,CAMP,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//4
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,RAIL,RAIL,RAIL,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//5
		{BASE,RAIL,RAIL,RAIL,RAIL,RAIL,RAIL,NON_,RAIL,NON_,RAIL,RAIL,RAIL,RAIL,RAIL,RAIL,BASE},//6
		{HEAD,RAIL,CAMP,BASE,CAMP,RAIL,NON_,NON_,NON_,NON_,NON_,RAIL,CAMP,BASE,CAMP,RAIL,HEAD},//7
		{BASE,RAIL,BASE,CAMP,BASE,RAIL,RAIL,NON_,RAIL,NON_,RAIL,RAIL,BASE,CAMP,BASE,RAIL,BASE},//8
		{HEAD,RAIL,CAMP,BASE,CAMP,RAIL,NON_,NON_,NON_,NON_,NON_,RAIL,CAMP,BASE,CAMP,RAIL,HEAD},//9
		{BASE,RAIL,RAIL,RAIL,RAIL,RAIL,RAIL,NON_,RAIL,NON_,RAIL,RAIL,RAIL,RAIL,RAIL,RAIL,BASE},//10
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,RAIL,RAIL,RAIL,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//11
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,CAMP,BASE,CAMP,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//12
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,BASE,CAMP,BASE,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//13
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,CAMP,BASE,CAMP,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//14
		{NON_,NON_,NON_,NON_,NON_,NON_,RAIL,RAIL,RAIL,RAIL,RAIL,NON_,NON_,NON_,NON_,NON_,NON_},//15
		{NON_,NON_,NON_,NON_,NON_,NON_,BASE,HEAD,BASE,HEAD,BASE,NON_,NON_,NON_,NON_,NON_,NON_}//16
	};

	public boolean canPlacePieceAt(int x, int y, int piece) {
		switch (piece) {
		case MilitaryPiece.VALUE_FLAG:
			return (x == 7 || x == 9) && y == 16;
		case MilitaryPiece.VALUE_MINE:
			return (x > 5 && x < 11) && (y == 15 || y == 16);
		case MilitaryPiece.VALUE_BOMB:
			return !((x > 5 && x < 11) && (y == 11));
		default:
			return true;
		}
	}
	public static void main(String[] args) {
		byte x= 0x11;
		System.out.println(5&1);
	}

	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canMove(Point from, Point to) {
		int fromPiece = 
		
		
		return false;
	}
}



