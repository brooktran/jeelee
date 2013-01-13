/*
 * @(#)OpenRecentAction.java  1.1  2008-03-19
 *
 * Copyright (c) 1996-2008 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */
package org.jtool.sample.job.action;


import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.Action;

import org.jtool.app.IApplication;
import org.jtool.app.action.AppAction;

/**
 * OpenRecentAction.
 *
 * @author Werner Randelshofer.
 * @version 1.1 2008-03-19 Check whether file exists before attempting to
 * open it. 
 * <br>1.0 June 15, 2006 Created.
 */
public class OpenRecentAction extends AppAction {
	private static final long serialVersionUID = 1L;
	public final static String ID = "openRecent";
	private File file;

	/** Creates a new instance. */
	public OpenRecentAction(IApplication app, File file) {
		super(app);
		this.file = file;
		putValue(Action.NAME, file.getName());
	}


	public void actionPerformed(ActionEvent evt) {
	}

}
