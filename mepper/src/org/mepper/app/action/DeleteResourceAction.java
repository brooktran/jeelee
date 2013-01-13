/* DeleteResourceAction.java 1.0 2010-2-2
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

import org.mepper.resources.LibraryResource;
import org.mepper.resources.ProjectManager;
import org.mepper.resources.StorableResource;
import org.mepper.utils.MepperConstant;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppOptionPanel;

/**
 * <B>DeleteResourceAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-9-27 created
 * @since org.mepper.app.action Ver 1.0
 * 
 */
public class DeleteResourceAction extends AppAction{
	public final static String ID="delete";
	private final ProjectManager manager;
	public DeleteResourceAction(Application app,ProjectManager manager) {
		super(app,ID);
		this.manager = manager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		StorableResource child = manager.getCurrentNode();
		if(!AppOptionPanel.showConfirmDeleteDialog(app.getView().getComponenet(),child.toString())){
			return;
		}
		if (child instanceof LibraryResource) {
			while(child.getParentResource().getID() != MepperConstant.LIBRARY_SET_ID){
				child = child.getParentResource();
			}
		}
		manager.removeResource(child);
	}
//	@Override
//	public String getID() {
//		return ID;
//	}
}
