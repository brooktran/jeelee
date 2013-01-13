/* NewRandomGameAction.java 1.0 2010-2-2
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
package org.chess.renju.color.actions;

import java.awt.event.ActionEvent;

import org.chess.renju.color.ColorRenjuMode;
import org.chess.renju.color.gui.ColorRenjuView;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
 
/**
 * <B>NewRandomGameAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-18 created
 * @since Renju Ver 1.0
 * 
 */
public class NewRandomGameAction extends AppAction{
	
	public static final String ID = "new.random";
	public NewRandomGameAction(Application app) {
		super(app,ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		((ColorRenjuView)app.getView()).newGame(ColorRenjuMode.RANDOM);
	}
	
	@Override
	public String getID() {
		return ID;
	}
}
