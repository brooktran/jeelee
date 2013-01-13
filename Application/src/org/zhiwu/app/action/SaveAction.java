/* SaveAction.java 1.0 2010-2-2
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
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.zhiwu.app.AbsView;
import org.zhiwu.app.Application;
import org.zhiwu.app.ApplicationConstant;
import org.zhiwu.utils.SwingResources;

/**
 * <B>SaveAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-24 created
 * @since org.divine.actions Ver 1.0
 * 
 */
public class SaveAction extends AppAction {
	public static final String ID = "save";

	private final PropertyChangeListener pl= new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if(evt.getPropertyName().equals(ApplicationConstant.PROPERTY_SAVE)){
				setEnabled((Boolean) evt.getNewValue());
			}
		}
	};
	
	public SaveAction(Application app) {
		super(app);
		
		SwingResources r = app.getResource();
		r.configAction(this, ID);
		AbsView view= (AbsView)app.getView();
		view.addPropertyChangeListener(pl);
		app.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals(ApplicationConstant.PROPERTY_VIEW_CHANGED)){
					((AbsView)evt.getOldValue()).removePropertyChangeListener(pl);
					((AbsView)evt.getNewValue()).addPropertyChangeListener(pl);
				}
			}
		});
		setEnabled(false);
	} 

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		AbsView view=(AbsView) app.getView();
		if(view.hasUnsavChanged()){
			view.save();
		}
	}
//	@Override
//	public String getID() {
//		return ID;
//	}
}
