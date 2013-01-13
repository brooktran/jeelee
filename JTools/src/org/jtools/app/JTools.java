package org.jtools.app;
/* Divine.java 1.0 2010-2-2
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


import java.awt.EventQueue;
import java.util.StringTokenizer;

import org.zhiwu.app.Application;
import org.zhiwu.app.Model;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.PreferenceManager;
import org.zhiwu.utils.AppLogging;

/**
 * <B>Divine</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-20 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public class JTools {
	public static void main(String[] args) {
		final Application app = new JToolsApplication();
		
		Model model=new JToolsModel();
		model.setName("0000_0.2");
		model.setVersion("0.2.1.100519238773");
		model.setCopyright("2010 all right reserve");
		app.setModel(model);
		
		AppPreference prep=PreferenceManager.getInstance().getPreference(Application.class.getName());
		StringTokenizer st=new StringTokenizer(prep.get("views"),",");
		while(st.hasMoreTokens()){
			model.addView(st.nextToken());
		}
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					app.launch();
				} catch (Exception e) {
					AppLogging.handleException(e);
				}
			}
		});
	}

}
