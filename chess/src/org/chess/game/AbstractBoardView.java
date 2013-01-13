/* DefaultBoardView.java 1.0 2010-2-2
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
package org.chess.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.chess.renju.utils.GraphicsRenderinghints;

/**
 * <B>DefaultBoardView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-8 created
 * @since chess Ver 1.0
 * 
 */
public class AbstractBoardView extends JPanel implements BoardView{
	protected final Game game; 
	protected Rectangle bounds;
	protected int pieceWidth,pieceHeight;
	
	public AbstractBoardView(Game game) {
		this.game = game;
		bounds = new Rectangle(0,0,0,0);
		
		addListeners();
	}
	
	
	private void addListeners() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				resizeBounds();
				repaint();
			}
		});
	}
	protected void resizeBounds() {
		int h=getHeight(),w=getWidth();
		int width = w<h?w:h;
		int height = h<w?h:w;
		bounds = new Rectangle((w-width)/2, (h-height)/2,width,width);
		
		pieceWidth = bounds.width/game.getMode().getRow();
		pieceHeight = bounds.height/game.getMode().getColumn();
	}
	
	@Override
	protected void paintComponent(Graphics g2) {
		super.paintComponent(g2);
		
		Graphics2D g=(Graphics2D) g2;
		GraphicsRenderinghints.setViewRenderingHints(g);
	
		paintGame((Graphics2D) g.create());
	}

	protected void paintGame(Graphics2D g) {
	}


	@Override
	public Game getGame() {
		return game;
	}
	
	@Override
	public JComponent getComponent() {
		return this;
	}
	
	@Override
	public Point mapToScreen(Point map) {
		return new Point(map.x*pieceWidth+bounds.x,map.y*pieceHeight+bounds.y);
	}
	
	@Override
	public Point screenToMap(Point screen) {
		return new Point((screen.x-bounds.x)/pieceWidth,(screen.y-bounds.y)/pieceHeight);
	}
	
	@Override
	public void select(Point selected) {//map point
	}
	
	@Override
	public void clearSelection() {
	}
	
}
