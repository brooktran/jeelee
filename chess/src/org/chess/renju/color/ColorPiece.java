/* Piece.java 1.0 2010-2-2
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

import java.awt.Color;
import java.awt.Image;

import org.chess.game.Piece;

/**
 * <B>Piece</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.renju.chess Ver 1.0
 * 
 */
public class ColorPiece extends Piece{
	
	public ColorPiece(int id, Image image){
		super(id, image);
	} 

	public static final Piece EMPTY = new Piece( 0, Color.BLACK);
	public static final Piece RED = new Piece( 1, Color.RED);
	public static final Piece BLUE = new Piece( 2, Color.BLUE);
	public static final Piece GREEN = new Piece( 3, Color.GREEN);
	public static final Piece CYAN = new Piece( 4, Color.CYAN);
	public static final Piece YELLOW = new Piece( 5, Color.YELLOW);
	public static final Piece MAGENTA = new Piece( 6, Color.MAGENTA);
	public static final Piece PINK = new Piece( 7, Color.BLACK);
	public static final Piece PIECE_8 = new Piece( 8,new Color(128, 0, 0));
	public static final Piece PIECE_9 = new Piece( 9, new Color(255, 64,0));
	public static final Piece PIECE_10 = new Piece( 10, new Color(128,128, 0));
	public static final Piece PIECE_11 = new Piece( 10, new Color(128, 70, 64));
	public static final Piece PIECE_12 = new Piece( 10, new Color(255,128,0) );

	
	
}
