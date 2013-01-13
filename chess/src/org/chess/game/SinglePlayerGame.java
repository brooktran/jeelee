/* SinglePlayerGame.java 1.0 2010-2-2
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

import java.awt.Point;

/**
 * <B>SinglePlayerGame</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-8 created
 * @since chess Ver 1.0
 * 
 */
public class SinglePlayerGame extends AbstractGame {
	protected int selectedX;
	protected int selectedY;
	protected boolean isSelected;
	
	
	public int getSelectedX() {
		return selectedX;
	}

	public int getSelectedY() {
		return selectedY;
	}
	
	public void selecte(int x, int y) {
	}


	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
	
		this.isSelected = isSelected;
	}
	
	
	
	public Point getSelectPoint() {
		return new Point(selectedX, selectedY);
	}
	
	
	
	
	
	
	
}
