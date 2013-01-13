/* ApplicationRunner.java 1.0 2010-2-2
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
package org.zhiwu.app;

import java.awt.EventQueue;
import java.util.Locale;
import java.util.StringTokenizer;

import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.PreferenceManager;
import org.zhiwu.utils.AppLogging;

/**
 * <B>ApplicationRunner</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-5 created
 * @since Application Ver 1.0
 * 
 */
public class ApplicationRunner {

	public static void runApplication(final Application app,final Model model){
		AppPreference pref= AppManager.getPreference(Application.class.getName());
		AppManager.setLabels(pref.get("labels"));
		initLocale() ;
		
		app.setModel(model);
		
		StringTokenizer st = new StringTokenizer(pref.get("views"),";");
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
	
	protected static void initLocale() {
		Locale l;
		AppPreference prep=PreferenceManager.getInstance().getPreference(Application.class.getName());
		String localeString=prep.get("locale");
		int p=localeString.indexOf("_");
		l=new Locale(localeString.substring(0, p),localeString.substring(p+1));
		AppManager.setLocale(l);
	}
}
