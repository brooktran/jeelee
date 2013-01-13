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
package org.chess.military.ai;

import org.chess.renju.utils.Position;

/**
 * <B>MoveGenerator</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public class MoveGenerator {
	private Movement[][] movements;
	private int movementCount;
	
	
	public boolean isValidMove(int[][] board,Position from,Position to){
		if(from.equals(to)){
			return false;
		}
		
		int chessFrom=board[from.x][from.y];
		int chessTo = board[to.x][to.y];
		
		if(isSameCampaign(chessFrom,chessTo)){
			return false;
		}
		
		
		switch(chessFrom){
		
			
		
		
		
		
		
		}
		
		return false;
	}


	/**
	 * @param chessFrom
	 * @param chessTo
	 * @return
	 */
	private boolean isSameCampaign(int chessFrom, int chessTo) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
