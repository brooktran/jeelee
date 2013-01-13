/* DivineModel.java 1.0 2010-2-2
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
package org.jtools.app;

import org.divine.actions.AboutAction;
import org.divine.actions.CheckUpdateAction;
import org.zhiwu.app.AbsModel;
import org.zhiwu.app.action.ConfigAction;
import org.zhiwu.app.action.ExitAction;

/**
 * <B>DivineModel</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-28 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public class JToolsModel extends AbsModel{
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.AbsModel#init(org.zhiwu.app.IApplication)
	 */
//	public void init(IApplication app) {
//		app.addPropertyChangeListener(new PropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent evt) {
//				if (evt.getPropertyName().equals(DivineApplication.STATE_PROPERTY)) {
//					if (evt.getNewValue().equals("started")) {
//						initialData();
//					}
//				}
//			}
//		});
//		super.init(app);
//		
//	}
//	/**
//	 * 
//	 */
//	protected void initialData() {
//		AppResources resources=AppResources.getResources("org.jtools.app.Labels", app.getLocale());
//		File f=new File(resources.getString("db"));
//		if (f.exists()) {
//			return;
//		}
//		
//		app.setState("Init_Data");
//		
//		Initializer initializer=Initializer.getInstance();
//		initializer.run();
//		
//		
//	}
	/* (non-Javadoc)
	 * @see org.zhiwu.app.AbsModel#initActions()
	 */
	@Override
	protected void initActions() {
//		super.initActions();
		putAction(ExitAction.ID, new ExitAction(app));
//		putAction(SaveAction.ID, new SaveAction(app));  // init from the default applicatioin.
		putAction(CheckUpdateAction.ID, new CheckUpdateAction(app));
		putAction(AboutAction.ID, new AboutAction(app));
		putAction(ConfigAction.ID,new ConfigAction(app));
	}
	

}
