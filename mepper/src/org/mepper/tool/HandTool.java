/* HandTool.java 1.0 2010-2-2
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

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.mepper.editor.EditorView;

/**
 * <B>HandTool</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-5-2 created
 * @since org.mepper.tool Ver 1.0
 * 
 */
public class HandTool extends AbstractTool{
	
	private Point startPoint ;
	
	@Override
	public void mousePressed(MouseEvent e) {
		startPoint = e.getPoint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		EditorView v= editor.getActivateView();
		JScrollPane scrollPane = (JScrollPane) v.getComponent().getParent().getParent();//FIXME NullPointerException
		JScrollBar HBar = scrollPane.getHorizontalScrollBar();
		JScrollBar VBar = scrollPane.getVerticalScrollBar();
		HBar.setValue(HBar.getValue() - e.getX() + startPoint.x);
		VBar.setValue(VBar.getValue() - e.getY() + startPoint.y);
	}
	
	
	
	

	@Override
	public String getID() {
		return "tool.hand";
	}
	
	
	@Override
	public Cursor getCursor() {
		return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	}

}
