/* RetryAction.java 1.0 2010-2-3
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
import org.zhiwu.app.IApplication;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>RetryAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2010-2-3 created
 * @since JCompareEditor Ver 1.0
 * 
 */
public class RetryAction extends AppAction{

	public static final String ID="retry";
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @since JCompareEditor
	 * @param app
	 */
	public RetryAction(IApplication app) {
		super(app);

		AppResources appResources=AppResources.getResources("org.compare.Labels");
		appResources.configAction(this, ID);
	}

	private void updateView() {
		CompareView view=(CompareView)app.getView();
		view.setEnabled(true);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		CompareView view=(CompareView)app.getView();
		view.setEnabled(true);
	}

}
