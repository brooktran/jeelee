/* Pieces.java 1.0 2010-2-2
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

import java.awt.Image;
import java.io.File;

import org.chess.app.ChessApplication;
import org.chess.game.Piece;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.utils.ImageUtils;

/**
 * <B>Pieces</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.renju.chess Ver 1.0
 * 
 */
public class ColorPieceHelper {
	public static final Piece[] pieces;
	public static final Piece[] obstacles;
	
	static{
		AppPreference pref =AppManager.getPreference(ChessApplication.class.getName());
		String imageDir = pref.get("image.dir");
		obstacles =new Piece[pref.getInteger("obstacle.num")+1];
		for(int i=obstacles.length;i>1;i--){
			int p=i-1;
			Image image =ImageUtils.readImage(new File(imageDir+(-p)+".png"));
			obstacles[p]=new ColorPiece( -p, image);
		}
		
		pieces=new Piece[]{ColorPiece.EMPTY,
				ColorPiece.RED,ColorPiece.BLUE,ColorPiece.GREEN,ColorPiece.CYAN,
				ColorPiece.YELLOW,ColorPiece.MAGENTA,ColorPiece.PINK,ColorPiece.PIECE_8
				,ColorPiece.PIECE_9,ColorPiece.PIECE_10,ColorPiece.PIECE_11,ColorPiece.PIECE_12};
	}
	
	private ColorPieceHelper() {
	}

	public static int pieceCount() {
		return pieces.length;
	}

	public static int getPieceID(int index) {
		Piece p= index>0?pieces[index]:obstacles[-index];
		return p.getID();
	}
	
	public static Piece getPiece(int index){
		if (index >0) {
			return pieces[index];
		}
		return obstacles[-index];
		
	}

}
