/* AbstractTool.java 1.0 2010-2-2
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
package org.chess.military.tool;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import org.chess.game.BoardView;
import org.chess.game.Game;
import org.zhiwu.utils.MouseKeyAdapter;

/**
 * <B>AbstractTool</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-7 created
 * @since chess Ver 1.0
 * 
 */
public abstract class AbstractTool extends MouseKeyAdapter implements Tool{
	protected boolean popupTrigger;
	protected BoardView view;
	protected EventListenerList listenerList= new EventListenerList();

	
	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)){
			handlePopupMenu(e);
			popupTrigger =true;
			return;
		}
		popupTrigger =false;
		handleLeftMouseButton(e);
	}
	
	protected void handleLeftMouseButton(MouseEvent e) {
	}

	protected void handlePopupMenu(MouseEvent e) {
	}
	
	protected void fireToolDone(){
		ToolEvent e=new ToolEvent(this);
		Object[] listeners=listenerList.getListenerList();
		for(int i=0,j=listeners.length-1;i<j;i+=2){
			if (listeners[i]== ToolListener.class) {
				((ToolListener)listeners[i+1]).toolDone(e);
			}
		}
	}
	
	
	@Override
	public void activate(BoardView view) {
		this.view = view;
	}
	
	protected Game getGame(){
		return view.getGame();
	}
	
	@Override
	public void addToolListener(ToolListener l) {
		listenerList.add(ToolListener.class, l);
	}

	@Override
	public void removeToolListener(ToolListener l) {
		listenerList.remove(ToolListener.class, l);
	}


}
