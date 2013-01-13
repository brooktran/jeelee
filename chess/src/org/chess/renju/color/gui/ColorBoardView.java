/*
 * ChessBoardView.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran All rights reserved.
 * 
 * The copyright of this software is own by the authors. You may not use, copy
 * or modify this software, except in accordance with the license agreement you
 * entered into with the copyright holders. For details see accompanying license
 * terms.
 */
package org.chess.renju.color.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;

import org.chess.game.DefaultBoardView;
import org.chess.game.GameMode;
import org.chess.game.SinglePlayerGame;
import org.chess.renju.color.ColorPieceHelper;
import org.chess.renju.color.ColorRenju;
import org.chess.renju.color.ColorRenjuMode;
import org.zhiwu.app.AppManager;
import org.zhiwu.utils.AppResources;

/**
 * <B>ChessBoardView</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.renju.app Ver 1.0
 * 
 */
public class ColorBoardView extends DefaultBoardView {

	public ColorBoardView(SinglePlayerGame renju) {
		super(renju);
		setBackground(new Color(255,204,153));
	}

	@Override
	protected void paintGame(Graphics2D g) {
		paintBoard((Graphics2D) g.create());
		paintPieces((Graphics2D) g.create());
		paintNextPieces((Graphics2D) g.create());
		paintGameState((Graphics2D) g.create());
	}
	 
	

	private void paintNextPieces(Graphics2D g) {
		ColorRenjuMode mode =(ColorRenjuMode) game.getMode();
		// paint next pieces
		
		
		double factor = mode.getPieceNum()*pieceHeight>bounds.y+bounds.height?
				(bounds.y+bounds.height)/(mode.getPieceNum()*pieceHeight) :1;
		int pieceWidth = (int)(this.pieceWidth*factor);
		int pieceHeight = (int)(this.pieceHeight*factor);
		// paint pieces
		int[] nextPieces = ((ColorRenju)game).getNextPieces();
		for (int i = 0; i < nextPieces.length; i++) {
			g.setColor(ColorPieceHelper.getPiece(nextPieces[i]).getColor());
			g.fillOval((int)(bounds.x-pieceWidth*1.2), (int)(i * pieceHeight*1.2), pieceWidth,
					pieceHeight);
		}		
	}

	private void paintPieces(Graphics2D g) {
		g.translate(bounds.x +3, bounds.y +3);
		GameMode mode =game.getMode();
		
		//paint pieces
		for(int i=0;i<mode.getRow();i++){
			for(int j=0;j<mode.getColumn();j++){
				int piece = game.getPieceAt(i, j);
				if(piece == 0){
					continue;
				}
				if(piece > 0){
					g.setColor(ColorPieceHelper.getPiece(piece).getColor());
					g.fillOval(i*pieceWidth, j*pieceHeight, pieceWidth, pieceHeight);
					continue;
				}
				if(piece < 0){
					Image image =ColorPieceHelper.getPiece(piece).getImage();
					int imageWidth = image.getWidth(this);
					int imageHeight =image.getHeight(this);
					
					double p,q,factor;
					p= imageWidth>pieceWidth?(double)pieceWidth/imageWidth:1;
					q=imageHeight>pieceHeight?(double)pieceHeight/imageHeight:1;
					factor = p>q? p:q;
					imageWidth = (int)(factor*imageWidth);
					imageHeight = (int)(factor*imageHeight);
					
					int x=(pieceWidth-imageWidth)/2;
					int y=(pieceHeight-imageHeight)/2;
					
					g.drawImage(ColorPieceHelper.getPiece(piece).getImage(), i*pieceWidth+x, j*pieceHeight+y,
							imageWidth, imageHeight, this);
				}
			}
		} 
		
		// paint selected 
		if(getGame().isSelected()){
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(2));
			Point select= getGame().getSelectPoint();
			g.drawRect(select.x*pieceWidth, select.y*pieceHeight, pieceWidth, pieceHeight);
		}
	}

	private void paintBoard(Graphics2D g) {
		g.setColor(new Color(177,97,38 ));
		g.setStroke(new BasicStroke(1,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND));
		g.draw(bounds);
		
		Color color1=new Color(233,177,119);
		Color color2=new Color(212,133,74);
		
		g.translate(bounds.x +3, bounds.y +3);
		for(int i=0;i<game.getMode().getRow();i++){
			for(int j=0;j<game.getMode().getColumn();j++){
				g.setColor((j+i)%2==0?color1:color2);
				g.fillRect(i*pieceWidth, j*pieceHeight, pieceWidth, pieceHeight);
			}
		}
		
	}
	
	protected void paintGameState(Graphics2D g) {
		String state=game.getState();
		if(state.equals("over") || state.equals("pass")){
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
