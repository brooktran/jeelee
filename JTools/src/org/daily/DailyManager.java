/* DailyManager.java 1.0 2010-2-2
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
package org.daily;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jtools.app.persistent.DataException;
import org.jtools.app.persistent.DataManager;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.PreferenceManager;
import org.zhiwu.xml.DataSourceException;


/**
 * <B>DailyManager</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-11-15 created
 * @since org.daily Ver 1.0
 * 
 */
public class DailyManager  extends DataManager{
	private List<Daily> dailies;

	
	public DailyManager()throws DataException{
		
	}
	
	@SuppressWarnings("unchecked")
	public void getSaved() throws DataSourceException, DataException, IOException{
		if(dailies ==null){
			dailies=(List<Daily>) super.getSaved(Daily.class.getName(),Daily.class);
		}
		
	}
	
	public List<Daily> getDelete() throws DataSourceException, DataException, IOException{
		if(dailies ==null){
			getSaved();
		}
		

		List<Daily> result = new ArrayList<Daily>();

		for (Daily d : dailies) {
			if (d.isDelete()) {
				result.add(d);
			}
		}
		return result;
	}

	/**
	 * @return
	 */
	public List getAllDaily() throws DataSourceException, DataException,
			IOException {
		if (dailies == null) {
			getSaved();
		}
		List<Daily> result = new ArrayList<Daily>();

		for (Daily d : dailies) {
			if (!d.isDelete()) {
				result.add(d);
			}
		}
		return result;
	}
	
	
	
	/**
	 * 
	 */
	public void saveDaily(Daily d) throws IOException {
		saveObject(d);
	}
	
	/**
	 * initialize the database
	 */
	public void createDailyTable() throws DataException{
		super.createTable(Daily.class.getName());
	}


	/**
	 * @param text
	 */
	public void saveToFile(Daily daily,String text) throws IOException {
		text=prepareText(text);
		AppPreference pref=PreferenceManager.getInstance().getPreference(DailyView.class.getName());
		// set title
		String path=pref.get("backup.path");
		path=preparePath(path);
		
		String title=daily.getDate()+"."+daily.getTitle();
		title+=".txt";
		// remove the specil charactes for the file name. 
		// 
		FileWriter fw=new FileWriter(new File(path+title.trim().replaceAll("[\\t\\n<>/\"*?:\\\\]", ".")),false);

		fw.write(text);
		fw.flush();
		fw.close();
	}

	/**
	 * 修改换行
	 * @param text
	 * @return
	 */
	private String prepareText(String text) {
		String r="";
		String[] strings=text.split("\n");
		for(String s:strings){
			r +=s+"\r\n";
		}
		return r;
	}

	/**
	 * @param path
	 */
	private String preparePath(String path) {
		if(!path.endsWith(File.separator)){
			path+=File.separator;
		}
		File f=new File(path);
		if(!f.exists()){
			f.mkdirs();
		}
		return path;
	}

	/**
	 * @param selectDailies
	 */
	public void delete(List<Daily> dailies) {
		try {
			for (Daily d : dailies) {
				d.setDelete(true);
				saveObject(d);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 */
	public void checkPrepare() {
		try {
			if(! existTable(Daily.class.getName())){
				createDailyTable();
			}
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	
	
	
}
