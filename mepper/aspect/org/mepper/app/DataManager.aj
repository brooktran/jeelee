/* DataManager.aj 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Chen Zhiwu
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.mepper.app;

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;

import org.mepper.resources.DefaultProjectManager;
import org.mepper.resources.DefaultResourcesManager;
import org.mepper.utils.MepperConstant;
import org.mepper.utils.Word;
import org.mepper.utils.WordList;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.utils.DateUtils;
import org.zhiwu.utils.FileUtils;


/**
 * <B>DataManager</B>
 * 
 * @author Zhi-Wu Chen. Email: <a href="mailto:c.zhiwu@gmail.com">c.zhiwu@gmail.com</a>
 * @version Ver 1.0.01 2011-11-22 created
 * @since mepper Ver 1.0
 * 
 */
public aspect DataManager {
	
	pointcut applicationInit():execution(public void org.mepper.app.MapApplication.init(..));
	pointcut applicationExit():execution(public void org.mepper.app.MapApplication.exit(..));
	
	before() : applicationInit(){
		DefaultResourcesManager.getInstance().init();
		DefaultProjectManager.getInstance().init();
	} 
	
	before():applicationExit(){
		backupDatabase();
		saveData();
	}
	
	private void saveData() {
		// save  mepper Constant
		WordList wordList= MepperConstant.RECENT_PROPERTIES;
		StringBuilder sb = new StringBuilder();
		Iterator<Word> it = wordList.iterator();
		while(it.hasNext()){
			sb.append(it.next().toString());
			sb.append(";");
		}
		AppPreference pref = AppManager.getPreference(MapApplication.class.getName());
		pref.put("recent.properties", sb.toString());
		
		
		DefaultResourcesManager.getInstance().save();
    	DefaultProjectManager.getInstance().save();
	}


	private void backupDatabase() {
		AppPreference pref= AppManager.getPreference(MapApplication.class.getName());
		String path =pref.get("database.path");
		String backup=path +"."+ DateUtils.format(Calendar.getInstance().getTime(), "yy-MM-dd HH-mm-ss")+File.separator;
		File bkf = new File(backup);
		if( ! bkf.exists()){
			bkf.mkdirs();
		}
		
		// backup all resources
		File dataDirectory=new File(path);
		if(! dataDirectory.exists()){
			dataDirectory.mkdirs();
		}
		
		File[] files=dataDirectory.listFiles();
		for(File f: files){
			String name = f.getName();
			if(f.getName().startsWith(".")){
				continue;
			}
			File newFile = new File(backup+name);
			f.renameTo(newFile);
			FileUtils.delete(f);
		}		
	}
	
	
	
}
