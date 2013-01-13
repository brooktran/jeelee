/* DefaultAuthorizater.java 1.0 2010-2-2
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
package org.medicine.security;


import java.util.List;

import javax.swing.event.EventListenerList;

import org.medicine.entity.User;
import org.medicine.server.app.LoginDialog;
import org.zhiwu.app.AbsDialog;
import org.zhiwu.app.AppDialogListener;
import org.zhiwu.app.Application;
import org.zhiwu.app.DialogEvent;

/**
 * <B>DefaultAuthorizater</B>
 * for single user.
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-12 created
 * @since Jili Ver 1.0
 * 
 */
public class DefaultAuthorizater implements Authorizater {
	private final Application app;
	private boolean available ;
	protected EventListenerList listenerList = new EventListenerList();
	private List<User> users;
	private User currentUser;

	public DefaultAuthorizater(Application app) {
		this.app = app;
	}

	@Override
	public boolean available() {
		return false;
	}

	@Override
	public void checkAuthorization(AuthorizationListener l) {
		addAuthorizationListener(l);
		AbsDialog dialog = new LoginDialog();
		dialog.showDialog(new AppDialogListener() {
			@Override
			public void optionSelection(DialogEvent evt) {
				available= false;
				if(evt.getName().equals(AbsDialog.COMFIRM_OPTION)){
					
				}
				fireAuthorizationChange();
			}
		});
	}
	
	public void addAuthorizationListener(AuthorizationListener l){
		listenerList.add(AuthorizationListener.class, l);
	}
	

	protected void fireAuthorizationChange() {
		Object[] listeners=listenerList.getListenerList(); 
		AuthorizationEvent evt = new AuthorizationEvent(this);
		for(int i=0,j=listeners.length-1;i<j;i+=2){
			if (listeners[i] == AuthorizationListener.class) {
				((AuthorizationListener)listeners[i+1]).authorizationChange(evt);
			}
		}
	}

}
