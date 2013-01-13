/* @(#)TileManagerAction.java 1.0 2010-1-7
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
package org.mepper.app.action;



import java.awt.event.ActionEvent;

import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;


/**
 * <B>TileManagerAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-1-7 created
 * @since map editor Ver 1.0
 * 
 */
public class TileManagerAction  extends AppAction {
	private static final long serialVersionUID = 1L;
	public static final String ID = "TileManagerAction";

	public TileManagerAction(Application app) {
		super(app,ID);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
//		final JFrame dialog=new TileView(app);
//		AppResources resource= AppResources.getResources("com.map.editor.Labels");
//		resource.configWindow(dialog, TileView.ID);
//		dialog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//TODO
//		dialog.setVisible(true);
		//		dialog.showMapDialog(new IDialogListener() {
		//			public void optionSelection(MapDialogEvent evt) {
		//				//TODO
		//			}
		//		});
	}

//	@Override
//	public String getID() {
//		return ID;
//	}
	
}

