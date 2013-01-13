/* DefaultEditor.java 1.0 2010-2-2
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

import javax.swing.JComponent;

import org.chess.game.BoardView;

/**
 * <B>DefaultEditor</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-7 created
 * @since chess Ver 1.0
 * 
 */
public class DefaultEditor implements Editor{

	private BoardView view;
	private Tool currentTool;
	
	
	@Override
	public void setView(BoardView view) {
		this.view =view;
	}

	@Override
	public void setTool(Tool tool) {
		if(tool == null ){
			return;
		}
		JComponent c= view.getComponent();
		deactivateView(c);
		activateView(tool, c);
		this.currentTool=tool;
	}
	
	private void deactivateView(JComponent c) {
		if(c==null){
			return;
		}
		c.removeMouseListener(currentTool);
		c.removeMouseMotionListener(currentTool);
		c.removeKeyListener(currentTool);
	}
	
	private void activateView(Tool tool, JComponent c) {
		c.addMouseListener(tool);
		c.addMouseMotionListener(tool);
		c.addKeyListener(tool);
		tool.activate(view);
		
	}



	
}
