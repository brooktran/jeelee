/* MilitaryPieceHelper.java 1.0 2010-2-2
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

import java.awt.Color;

import org.chess.game.Piece;
import org.chess.game.Player;

/**
 * <B>MilitaryPieceHelper</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-7 created
 * @since chess Ver 1.0
 * 
 */
public class MilitaryPieceHelper {
	private static final Color[] PLAYER_COLORS=new Color[]{
		new Color(217,108,2),new Color(47,105,168),
		new Color(117,157,6),new Color(144,67,166)};
	private static final Piece[] PIECES=new Piece[]{
		null,MilitaryPiece.MINE,MilitaryPiece.ENGINEER
		,MilitaryPiece.PLATOON_COMMANDER,MilitaryPiece.COMPANY_COMMANDER,
		MilitaryPiece.BATTALION_COMMANDER
		,MilitaryPiece.REGIMENT_COMMANDER,
		MilitaryPiece.BRIGADIER_COMMANDER,
		MilitaryPiece.DIVISION_COMMANDER
		,MilitaryPiece.ARMY_COMMANDER,
		MilitaryPiece.HEAD_COMMANDER,MilitaryPiece.BOMB
		,MilitaryPiece.FLAG
	};
	

	public static Color getColor(int piece) {
		return PLAYER_COLORS[piece/25];
	}
	public static int getPiece(int value, int index) {
		return index*25+value;
	}
	public static String getPieceName(int piece) {
		piece = piece%25;
		return PIECES[piece].getName();
	}
	public static boolean isSameSide(int piece, int other) {
		return piece/25 == other/25;
	}
	public static boolean isPlayerSide(int piece) {//XXX
		return piece/25 ==Player.PLAYER;
	}
}
