/* @(#)PlayAcion.java 1.0 2010-2-1
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
package org.compare.action;

import java.awt.event.ActionEvent;
import java.io.IOException;

import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>PlayAcion</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2010-2-1 created
 * @since JCompareEditor Ver 1.0
 * 
 */
public class PlayAcion extends AppAction{
	public static final String ID="play";


	/**
	 * 
	 * @since JCompareEditor
	 * @param app
	 */
	public PlayAcion(Application app) {
		super(app);
		AppResources resource = AppResources.getResources("org.compare.Labels");
		resource.configAction(this, ID);
	}

	/** 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			Runtime.getRuntime().exec("cmd /c E:/music/伍佰-诗情摇滚/13.Listen to Me.mp3");
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}

	@Override
	public String getID() {
		return ID;
	}
	


	private static final long serialVersionUID = 6916422535638293925L;

}
