/* MoveGenerator.java 1.0 2010-2-2
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
package org.chess.renju.five.ai;

/**
 * <B>MoveGenerator</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public class MoveGenerator {
	private int[][] moveList ;//10,255
	private int moveCount;
	

	/**
	 * Adds the move.
	 * 在moveList中插入一个走法
	 * 
	 * @param toX
	 *            目标位置
	 * @param toY
	 *            目标位置
	 * @param ply
	 *            此走法所在层次
	 * @return the int
	 */
	protected int addMove(int toX,int toY,int ply){
//		moveList[ply][moveCount]
		return 0;
	}
	
	/**
	 * Creates the possible move.
	 * 产生给定局面下一步所有合法的走法
	 * @param position
	 *            the position
	 * @param ply
	 *            the ply
	 * @param side
	 *            the side
	 * @return the int
	 */
	public int createPossibleMove(byte[][] position,int ply,int side){
		return 0;
	}
	
}
