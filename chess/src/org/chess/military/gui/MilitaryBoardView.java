/* MilitaryBoardView.java 1.0 2010-2-2
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
package org.chess.military.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

import org.chess.game.AbstractBoardView;
import org.chess.game.Game;
import org.chess.game.GameMode;
import org.chess.military.MilitaryPieceHelper;
import org.zhiwu.app.AppManager;
import org.zhiwu.utils.AppResources;

/**
 * <B>MilitaryBoardView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public class MilitaryBoardView extends AbstractBoardView{

	private static final int BOUND_OFFSET = 2;
	private int stationWidth,stationHeight;
	private Point selected;
	public MilitaryBoardView(Game game) {
		super(game);
	}
	
	@Override
	protected void resizeBounds() {
		super.resizeBounds();
		bounds.y += pieceHeight/2;
		stationWidth = pieceWidth/2;
		stationHeight =pieceHeight/2;
	}
	
	
	
	@Override
	protected void paintGame(Graphics2D g) {
		paintBoard((Graphics2D) g.create());
		paintPieces((Graphics2D) g.create());
		paintGameState((Graphics2D) g.create());
	}
	private void paintPieces(Graphics2D g) {
		g.translate(bounds.x , bounds.y);
		GameMode mode =game.getMode();
		g.translate(-pieceWidth/4,-pieceHeight/4);
		
		Font font =new Font(Font.DIALOG, Font.BOLD, pieceWidth/2);
		g.setFont(font);
		
		int width =  pieceWidth*2/3,height=pieceHeight*2/3;
		//paint pieces
		for(int i=0;i<mode.getRow();i++){
			for(int j=0;j<mode.getColumn();j++){
				int piece = game.getPieceAt(i, j);
				if(piece <1){
					continue;
				}
				
				g.setColor(MilitaryPieceHelper.getColor(piece));
				g.fill3DRect(i*pieceWidth, j*pieceHeight, width, height, true);
				
				g.setColor(Color.WHITE);
				g.drawString(MilitaryPieceHelper.getPieceName(piece), 
						(i+0.05f) * pieceWidth, (j+0.5f) * pieceHeight);
//				g.drawString(MilitaryPieceHelper.getPieceName(piece)+" "+i+ " "+j, i * pieceWidth, j * pieceHeight);

			}
		} 
		
		// paint selected
		if (selected!=null) {
			g.setColor(Color.RED);
			g.setStroke(new BasicStroke(3));
//			Point screen = mapToScreen(selected);
			g.drawRect(selected.x*pieceWidth, selected.y*pieceHeight, width, height);
		}
	}
	
	private void paintBoard(Graphics2D g) {
		g.translate(bounds.x, bounds.y);

		drawBaseRoad(g);
		drawRailRoad(g);
		drawStation(g);
		drawCamp(g);
	}
	
	
	private static final int[][] exclude1 = new int[][] { { 1, 1 }, { 1, 3 },
			{ 1, 5 }, { 2, 2 }, { 3, 1 }, { 3, 3 }, { 3, 5 } };
	private static final int[][] exclude2 = new int[][] { { 1, 0 }, { 1, 2 },
			{ 1, 4 }, { 2, 3 }, { 3, 0 }, { 3, 2 }, { 3, 4 } };
	private static final int[][] exclude3 = new int[][] { { 0, 1 }, { 0, 3 },
			{ 2, 1 }, { 2, 3 }, { 3, 2 }, { 4, 1 }, { 4, 3 } };
	private static final int[][] exclude4 = new int[][] { { 1, 1 }, { 1, 3 },
			{ 2, 2 }, { 3, 1 }, { 3, 3 }, { 5, 1 }, { 5, 3 } };

	private void drawStation(Graphics2D g) {
		g.setColor(new Color(90, 80, 50));
		g.setStroke(new BasicStroke(3));
		g.translate(-pieceWidth/4,-pieceHeight/4);
		
		drawStation(6,11,exclude1,g,true);
		drawStation(6,0,exclude2,g,true);
		drawStation(0,6,exclude3,g,false);
		drawStation(11,6,exclude4,g,false);
		drawStation(6,6,g);
	}



	private void drawStation(int x, int y, Graphics2D g) {
		int row=6,column=6;
		Color background = getBackground();
		Color stationColor=new Color(90,80,50);
		for(int i=0;i<row;i+=2){
			for(int j=0;j<column;j+=2){
				g.setColor(background);
				g.fillRect((i+x)*pieceWidth, (j+y)*pieceHeight, 
						stationWidth, stationHeight);
				g.setColor(stationColor);
				g.drawRect((i+x) * pieceWidth, (j+y) * pieceHeight, 
						stationWidth,stationHeight);
			}
		}
	}



	private void drawStation(int x, int y, int[][] exclude,Graphics2D g, boolean direction) {
		int row=5,column=5;
		row+= direction?0:1;
		column+=direction?1:0;
		Color background = getBackground();
		Color stationColor=new Color(90,80,50);
		int counter =0;
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				if(counter <exclude.length){
					if (exclude[counter][0] == i && 
							exclude[counter][1]==j) {
						counter++;
						continue;
					}
				}
				g.setColor(background);
				g.fill3DRect((i+x)*pieceWidth, (j+y)*pieceHeight, 
						stationWidth, stationHeight,true);
				g.setColor(stationColor);
				g.draw3DRect((i+x) * pieceWidth, (j+y) * pieceHeight, 
						stationWidth,stationHeight,true);
			}
		}
	}



	private void drawCamp(Graphics2D g) {
		g.setStroke(new BasicStroke(3));
		
		drawCamp(6,11,exclude1,g,true);
		drawCamp(6,0,exclude2,g,true);
		drawCamp(0,6,exclude3,g,false);
		drawCamp(11,6,exclude4,g,false);
		
	}
	
	private void drawCamp(int x, int y, int[][] exclude,Graphics2D g, boolean direction) {
		int row=5,column=5;
		row+= direction?0:1;
		column+=direction?1:0;
		Color background = getBackground();
		Color stationColor=new Color(248,145,84);
		int counter =0;
		for(int i=0;i<row;i++){
			for(int j=0;j<column;j++){
				if(counter <exclude.length){
					if (exclude[counter][0] == i && 
							exclude[counter][1]==j) {
						counter++;
						
						g.setColor(background);
						g.fillOval((i+x)*pieceWidth, (j+y)*pieceHeight, 
								stationWidth, stationHeight);
						g.setColor(stationColor);
						g.drawOval((i+x) * pieceWidth, (j+y) * pieceHeight, 
								stationWidth,stationHeight);
					}
				}
				
			}
		}
	}



	private void drawRailRoad(Graphics2D g) {
		g.setColor(new Color(130,98,35));
		g.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 
				1, new float[]{20,10}, 0));
		
		
		for(int i=0;i<17;i++){
			if(i==6 || i==10){
				if(i%2 == 0){
					g.drawLine(i*pieceWidth, pieceHeight, i*pieceWidth, bounds.height-2*pieceHeight);
				}
			}else if(i==1 || i==5 || i==11 || i==15){
				g.drawLine(i*pieceWidth, 6*pieceHeight, i*pieceWidth, 10*pieceHeight);
			}else if(i==8){
				g.drawLine(i*pieceWidth, 5*pieceHeight, i*pieceWidth, 11*pieceHeight);
			}
		}
		
		for(int j=0;j<17;j++){
			if(j==6 || j==10){
					g.drawLine(pieceWidth,j*pieceHeight, bounds.width-2*pieceWidth, j*pieceHeight);
			}else if(j==1 || j==5 || j==11 || j==15){
				g.drawLine(6*pieceWidth, j*pieceHeight, 10*pieceWidth, j*pieceHeight);
			}else if(j==8){
				g.drawLine(5*pieceWidth, j*pieceHeight, 11*pieceWidth, j*pieceHeight);
			}
		}
		g.drawArc(4*pieceWidth, 4*pieceHeight, 2*pieceWidth, 2*pieceHeight, -90, 90);
		g.drawArc(10*pieceWidth, 4*pieceHeight, 2*pieceWidth, 2*pieceHeight, -180, 90);
		g.drawArc(4*pieceWidth, 10*pieceHeight, 2*pieceWidth, 2*pieceHeight, 0, 90);
		g.drawArc(10*pieceWidth, 10*pieceHeight, 2*pieceWidth, 2*pieceHeight, 90, 90);
	}



	private void drawBaseRoad(Graphics2D g) {
		g.setColor(new Color(252,190,16));
		g.setStroke(new BasicStroke(2));

		for(int i=0;i<17;i++){
			if(i>5 && i< 11){
				if(i%2 == 0){
					g.drawLine(i*pieceWidth, 0, i*pieceWidth, bounds.height-pieceHeight);
				}else {
					g.drawLine(i*pieceWidth, 0,  i*pieceWidth, 5*pieceHeight);
					g.drawLine(i*pieceWidth, 11*pieceHeight,  i*pieceWidth,bounds.height-pieceHeight);
				}
			}else{
				g.drawLine(i*pieceWidth, 6*pieceHeight, i*pieceWidth, 10*pieceHeight);
			}
		}
		
		for(int j=0;j<17;j++){
			if(j>5 && j< 11){
				if(j%2 == 0){
					g.drawLine(0,j*pieceHeight, bounds.width-pieceWidth, j*pieceHeight);
				}else {
					g.drawLine(0,j*pieceHeight, 5*pieceWidth, j*pieceHeight);
					g.drawLine(11*pieceWidth,j*pieceHeight, bounds.width-pieceWidth, j*pieceHeight);
				}
			}else{
				g.drawLine(6*pieceWidth, j*pieceHeight, 10*pieceWidth, j*pieceHeight);
			}
		}
		
		
		drawBras(1,6,g);
		drawBras(6,1,g);
		drawBras(11,6,g);
		drawBras(6,11,g);
	}

	private void drawBras(int x, int y, Graphics2D g) {
		g.drawLine((x+2)*pieceWidth, y*pieceHeight, (x+4)*pieceWidth, (y+2)*pieceHeight);
		g.drawLine(x*pieceWidth, y*pieceHeight, (x+4)*pieceWidth, (y+4)*pieceHeight);
		g.drawLine(x*pieceWidth, (y+2)*pieceHeight, (x+2)*pieceWidth, (y+4)*pieceHeight);

		g.drawLine((x+2)*pieceWidth, y*pieceHeight, (x)*pieceWidth, (y+2)*pieceHeight);
		g.drawLine((x+4)*pieceWidth, y*pieceHeight, (x)*pieceWidth, (y+4)*pieceHeight);
		g.drawLine((x+4)*pieceWidth, (y+2)*pieceHeight, (x+2)*pieceWidth, (y+4)*pieceHeight);

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
	public Point screenToMap(Point screen) {
		return new Point((screen.x-bounds.x+pieceWidth/4)/pieceWidth,
				(screen.y-bounds.y+pieceHeight/4)/pieceHeight);
	}
	
	@Override
	public Point mapToScreen(Point map) {
		return new Point( map.x*pieceWidth+bounds.x-pieceWidth*5/4,
				map.y*pieceHeight+bounds.y -pieceHeight/2);
	}
	
	
	@Override
	public void select(Point selected) {
		this.selected =selected;
		repaint();
	}
	
	@Override
	public void clearSelection() {
		this.selected =null;
		repaint();
	}

}
