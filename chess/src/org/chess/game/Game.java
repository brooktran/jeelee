/* Chess.java 1.0 2010-2-2
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

import java.beans.PropertyChangeListener;

/**
 * <B>Chess</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public interface Game {

	void reset();
	int getScore();
	GameMode getMode();
	void addPropertyChangeListener(PropertyChangeListener l);

//	void selecte(int x, int y);
//	boolean isSelected();
//	Point getSelectPoint();

	int getPieceAt(int i, int j);
	String getState();

}
