/* NewGameAction.java 1.0 2010-2-2
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
package org.chess.app.actions;

import java.awt.event.ActionEvent;

import org.chess.app.ChessView;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;

/**
 * <B>NewGameAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.renju.app.actions Ver 1.0
 * 
 */
public class NewGameAction extends AppAction{
	public static final String ID = "new.game";
	public NewGameAction(Application app) {
		super(app,ID);
	} 

	@Override
	public void actionPerformed(ActionEvent e) {
		((ChessView)app.getView()).newGame();
	}
	
	@Override
	public String getID() {
		return ID;
	}

}
