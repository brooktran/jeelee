/* Main.java 1.0 2010-2-2
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
package org.medicine.server.app;

import org.medicine.security.Authorizater;
import org.medicine.security.AuthorizaterFactory;
import org.medicine.security.AuthorizationEvent;
import org.medicine.security.AuthorizationListener;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.Application;
import org.zhiwu.app.ApplicationRunner;
import org.zhiwu.app.DefaultModel;
import org.zhiwu.app.Model;
import org.zhiwu.app.config.AppPreference;

/**
 * <B>Main</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-8 created
 * @since Jili Ver 1.0
 * 
 */
public class Main {
	public static void main(String[] args) {
		AppPreference pref= AppManager.getPreference(Application.class.getName());
		AppManager.setLabels(pref.get("labels"));
		
		final Model model = new DefaultModel("医药管理系统", "0.0.1", "(C) 2011 all right reserve");
		final Application app = new ServerApplication();
		final Authorizater authorizater = AuthorizaterFactory.getDefaultAuthorizator(app);
		authorizater.checkAuthorization(new AuthorizationListener() {
			@Override
			public void authorizationChange(AuthorizationEvent e) {
				if (authorizater.available()) {
					ApplicationRunner.runApplication(app, model);
				}else {
					System.exit(-1);
				}
			}
		});
	}
}
