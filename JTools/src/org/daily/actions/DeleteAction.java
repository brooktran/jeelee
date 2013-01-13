/* DeleteAction.java 1.0 2010-2-2
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
package org.daily.actions;

import java.awt.event.ActionEvent;

import org.daily.DailyView;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;

/**
 * <B>DeleteAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-11-19 created
 * @since org.daily.actions Ver 1.0
 * 
 */
public  class DeleteAction extends AppAction {
	private static final long serialVersionUID = 2714236161951110165L;
	public static final String ID="delete";

	/**
	 * @param app
	 */
	public DeleteAction(Application app) {
		super(app,ID);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		getView().delete();
	}
	
	
	
	private DailyView getView(){
		return (DailyView)app.getView();
	}
	@Override
	public String getID() {
		return ID;
	}
	
}
