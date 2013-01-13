/* @(#)SaveAction.java 1.0 2010-2-2
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

import org.compare.CompareView;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>SaveAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2010-2-2 created
 * @since JCompareEditor Ver 1.0
 * 
 */
public class SaveAction extends AppAction {
	private static final long serialVersionUID = 1L;
	public static String ID = "cmpSave";

	/**
	 * 
	 * @since JCompareEditor
	 * @param app
	 */
	public SaveAction(Application app) {
		super(app);

		AppResources resource = AppResources.getResources("org.compare.Labels");
		resource.configAction(this, ID);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		CompareView view = (CompareView) app.getView();
		view.setEnabled(false);
		if (view.getEditor() == null) {
			return;
		}
		view.save();
		view.setEnabled(true);
	}

	@Override
	public String getID() {
		return ID;
	}
	
}
