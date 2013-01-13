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
package org.mepper.tool;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.MissingResourceException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.EventListenerList;

import org.mepper.editor.Editor;
import org.mepper.editor.EditorView;
import org.mepper.editor.map.Map;
import org.zhiwu.app.AppManager;
import org.zhiwu.utils.MouseKeyAdapter;
 
/**
 * <B>AbstractTool</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-8 created
 * @since org.mepper.tool Ver 1.0
 * 
 */
public abstract class AbstractTool extends MouseKeyAdapter implements Tool{
	protected EventListenerList listenerList= new EventListenerList();
	protected boolean popupTrigger;
	protected Editor editor;
	protected JComponent component;
//	protected String message;

	@Override
	public void deactivate(Editor editor) {
		this.editor = editor;
	}

	@Override
	public void activate(Editor editor) {
		 this.editor = editor;
	}

	@Override
	public void addToolListener(ToolListener l) {
		listenerList.add(ToolListener.class, l);
	}

	@Override
	public void removeToolListener(ToolListener l) {
		listenerList.remove(ToolListener.class, l);
	}

	@Override
	public Cursor getCursor() {
		return Cursor.getDefaultCursor();
	}

	@Override
	public JComponent getOptionComponent() {
		if(component == null){
			component = new JButton(AppManager.getResources().getString(getID()));
		}
		return component;
	}
	
	@Override
	public void setEditor(Editor editor) {
		this.editor = editor;
	}

	protected Map getMap(){
		EditorView view=editor.getActivateView();
		view.getComponent().requestFocus();
		Map map=editor.getActivateView().getMap(); 
		return map;
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
	
	protected void fireToolChanged() {
		fireToolChanged(new ToolEvent(this));
	}
	protected void fireToolChanged(ToolEvent e) {
		Object[] listeners=listenerList.getListenerList();
		for(int i=0,j=listeners.length-1;i<j;i+=2){
			if (listeners[i]== ToolListener.class) {
				((ToolListener)listeners[i+1]).toolChanged(e);
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)){
			handlePopupMenu(e);
			popupTrigger =true;
			return;
		}
		popupTrigger =false;
	}
	
	protected void handlePopupMenu(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		EditorView view= editor.getActivateView();
		Point p = e.getPoint();
		Point mapPoint =view.screenToMap(p);
//		message = mapPoint.x+","+mapPoint.y;
		fireToolChanged(new ToolEvent(this,e.getPoint(),mapPoint));
	}
	
	
	
	@Override
	public char getAccelerator() {
		try {
			return AppManager.getResources().getChar(getID()+".acc");
		} catch (MissingResourceException e) {
			return Character.MIN_VALUE;
		}
		
	}
	
}
