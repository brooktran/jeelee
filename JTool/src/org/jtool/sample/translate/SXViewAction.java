/* @(#)BEViewAction.java 1.0 2009-11-18
 * 
 * Copyright (c) 2009 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.jtool.sample.translate;

import java.awt.event.ActionEvent;

import org.jtool.app.IApplication;
import org.jtool.app.IView;
import org.jtool.app.action.AppAction;

/**
 * <B>BEViewAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-18 created
 * @since JTool Ver 1.0
 * 
 */
public class SXViewAction extends AppAction{
	private static final long serialVersionUID = 1L;
	public static final String ID = "SXView";

	/**
	 * 
	 * @since JTool
	 * @param app
	 */
	public SXViewAction(IApplication app) {
		super(app,ID);

	}

	/** 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//		app.setupView(new BinaryEditorView());

		IView view = new SXView();
		view.initApplication(app);
		view.init();
		app.setupView(view);
	}

}
