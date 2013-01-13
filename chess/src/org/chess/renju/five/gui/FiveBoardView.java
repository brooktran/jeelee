/* FiveBoardView.java 1.0 2010-2-2
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
package org.chess.renju.five.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import org.chess.game.DefaultBoardView;
import org.chess.game.GameMode;
import org.chess.game.SinglePlayerGame;
import org.chess.renju.five.FivePieceHelper;
import org.zhiwu.app.AppManager;
import org.zhiwu.utils.AppResources;

/**
 * <B>FiveBoardView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public class FiveBoardView extends DefaultBoardView{
	private static final int BOUND_OFFSET=4;
	public FiveBoardView(SinglePlayerGame game) {
		super(game);
		setBackground(new Color(246,203,101));
	}
	
	@Override
	protected void paintGame(Graphics2D g) {
		paintBoard((Graphics2D) g.create());
		paintPieces((Graphics2D) g.create());
		paintGameState((Graphics2D) g.create());
	}
	
	private void paintPieces(Graphics2D g) {
		g.translate(bounds.x +BOUND_OFFSET, bounds.y +BOUND_OFFSET);
		GameMode mode =game.getMode();
		
		//paint pieces
		for(int i=0;i<mode.getRow();i++){
			for(int j=0;j<mode.getColumn();j++){
				int piece = game.getPieceAt(i, j);
				if(piece == 0){
					continue;
				}
				g.setColor(FivePieceHelper.getPiece(piece).getColor());
				g.fillOval(i * pieceWidth, j * pieceHeight, pieceWidth,
						pieceHeight);

			}
		} 
		
		// paint selected 
		if(getGame() .isSelected()){
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(2));
			Point select= getGame() .getSelectPoint();
			g.drawRect(select.x*pieceWidth, select.y*pieceHeight, pieceWidth, pieceHeight);
		}
	}
	private void paintBoard(Graphics2D g) {
		g.setColor(new Color(106,22,0));
		g.setStroke(new BasicStroke(4,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		g.draw(bounds);
		
		g.setColor(new Color(0,0,0));
		g.setStroke(new BasicStroke(2));

		g.translate(bounds.x +BOUND_OFFSET, bounds.y +BOUND_OFFSET);
		GameMode mode = game.getMode();
		int row=mode.getRow(),column=mode.getColumn();
		for(int i=0;i<row;i++){
			g.drawLine(i*pieceWidth, 0, i*pieceWidth, column*pieceHeight);
		}
		for(int j=0;j<column;j++){
			g.drawLine(0, j*pieceHeight, column*pieceWidth, j*pieceHeight);
		}
		
	}
	
	protected void paintGameState(Graphics2D g) {
		String state=game.getState();
		if(state.equals("over") || state.equals("drawn")){
			Color c=new Color(128,128,128,128);
			g.setColor(c);
			g.fillRect(0, 0, getWidth(), getHeight());
			
			AppResources r=AppManager.getResources();
			Font font =new Font(Font.DIALOG, Font.BOLD | Font.ITALIC, 128);
			g.setFont(font);
			g.setColor(Color.RED);
			g.drawString(r.getString(state), bounds.x + bounds.width/4, bounds.y+ + bounds.height/3);
		}
	}
	
	@Override
	public SinglePlayerGame getGame() {
		return (SinglePlayerGame) super.getGame();
	}
	
}
