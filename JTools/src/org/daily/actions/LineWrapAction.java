/* LineWrapAction.java 1.0 2010-2-2
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
 * <B>LineWrapAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-11-15 created
 * @since org.daily.actions Ver 1.0
 * 
 */
public class LineWrapAction extends AppAction {
	public static final String ID="daily.LineWrap";

	/**
	 * @param app
	 */
	public LineWrapAction(Application app) {
		super(app);
		app.getResource().configAction(this, ID);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		DailyView v=(DailyView)app.getView();
		v.setLineWrap(!v.getLineWrap());
	}


	@Override
	public String getID() {
		return ID;
	}
	
	
}
