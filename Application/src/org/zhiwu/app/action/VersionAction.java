/* VersionAction.java 1.0 2010-2-2
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
import java.io.IOException;

import org.zhiwu.app.Application;
import org.zhiwu.utils.AppLogging;

/**
 * <B>VersionAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-12-1 created
 * @since org.zhiwu.app.action Ver 1.0
 * 
 */
public class VersionAction extends AppAction{
	public static final String ID="version";

	/**
	 * @param app
	 */
	public VersionAction(Application app) {
		super(app,ID);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Runtime.getRuntime().exec("notepad.exe version.txt");
		} catch (IOException e1) {
			AppLogging.handleException(e1);
		}
	}

//	@Override
//	public String getID() {
//		return ID;
//	}
}
