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
package org.militray.chess.game.ai;

/**
 * <B>MoveGenerator</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-5 created
 * @since Renju Ver 1.0
 * 
 */
public interface MoveGenerator {

	
	boolean isValidMove(int[][] position, int fromX,int fromY,int toX, int toY);
	// boolean isValidMove(byte[][] position, byte fromX,byte fromY,byte toX, byte toY);
	
	int generatePossibleMove(int[][] position, int player, int side);
}
