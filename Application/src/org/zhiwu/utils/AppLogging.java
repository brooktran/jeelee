/* AppLogging.java 1.0 2010-2-2
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
package org.zhiwu.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.zhiwu.app.Application;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.PreferenceManager;


/**
 * <B>AppLogging</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-12-7 created
 * @since org.zhiwu.utils Ver 1.0
 * 
 */
public class AppLogging {
	private static boolean testing;
	
	static{
		AppPreference p=PreferenceManager.getInstance().
			getPreference(Application.class.getName());
		testing=p.getBoolean("testing");
	}

	/**
	 * @param e
	 */
	public static void handleException(Exception e) {
		if(testing){
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, e.getMessage());
		
		try {
			FileWriter fw=new FileWriter(new File("app.log"), true);
			fw.write(Calendar.getInstance().toString());
			PrintWriter pw=new PrintWriter(fw,true);
			e.printStackTrace(pw);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
