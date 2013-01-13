/* ConfigAction.java 1.0 2010-2-2
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
import org.zhiwu.app.config.ConfigView;

/**
 * <B>ConfigAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-12-7 created
 * @since org.zhiwu.app.action Ver 1.0
 * 
 */
public class ConfigAction extends AppAction{
	public static final String ID="config";

	/**
	 * @param app
	 */
	public ConfigAction(Application app) {
		super(app,ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		ConfigView view=new ConfigView(app.getConfig());
		view.showDialog(null);
	}
//	@Override
//	public String getID() {
//		return ID;
//	}
}
