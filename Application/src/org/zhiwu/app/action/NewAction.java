/* NewAction.java 1.0 2010-2-2
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
package org.zhiwu.app.action;

import java.awt.event.ActionEvent;

import org.zhiwu.app.Application;

/**
 * <B>NewAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-11-14 created
 * @since org.daily.actions Ver 1.0
 * 
 */
public class NewAction extends AppAction{
	public static final String ID ="daily.new";

	/**
	 * @param app
	 */
	public NewAction(Application app) {
		super(app);
		app.getResource().configAction(this, ID);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// auto save the data without ask the user.
		app.setEnable(false);
		app.getView().create();
		app.setEnable(true);
	}
//	@Override
//	public String getID() {
//		return ID;
//	}
}
