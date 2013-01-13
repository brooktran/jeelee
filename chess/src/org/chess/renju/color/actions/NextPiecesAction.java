/* NextPiecesAction.java 1.0 2010-2-2
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

import org.chess.renju.color.ColorRenjuProxy;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;

/**
 * <B>NextPiecesAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-17 created
 * @since Renju Ver 1.0
 * 
 */
public class NextPiecesAction extends AppAction{
	public static final String ID = "next.pieces";
	private final ColorRenjuProxy renju;

	public NextPiecesAction(Application app, ColorRenjuProxy renju) {
		super(app,ID);
		this.renju=renju;
	}
 
	@Override
	public void actionPerformed(ActionEvent e) {
		renju.next();
	}
	
	@Override
	public String getID() {
		return ID;
	}

}
