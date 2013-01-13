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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * <B>DefaultBoardView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-8 created
 * @since chess Ver 1.0
 * 
 */
public class DefaultBoardView extends AbstractBoardView{
	private final PropertyChangeListener renjuListener=new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			String name = evt.getPropertyName();
			if(name.equals("board")){
				repaint();
				return;
			}
			if(name.equals("state")){
				String state=evt.getNewValue().toString();
				if(state.equals("over") || state.equals("pass")){
					removeMouseListener(mouseListener);
				}
			}
		}
	};
	
	private final MouseListener mouseListener=new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
			((SinglePlayerGame)game).selecte((e.getX()-bounds.x)/pieceWidth,(e.getY()- bounds.y)/pieceHeight);
			repaint();
		}
	};
	
	
	public DefaultBoardView(Game game) {
		super(game);
		
		initListeners();
	}
	private void initListeners() {
		game.addPropertyChangeListener(renjuListener);
		addMouseListener(mouseListener);		
	}
}
