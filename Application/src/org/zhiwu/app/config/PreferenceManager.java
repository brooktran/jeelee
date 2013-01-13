/* AppPreferences.java 1.0 2010-2-2
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
package org.zhiwu.app.config;

import org.zhiwu.utils.LeftRightList;

/**
 * <B>AppPreferences</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-11-15 created
 * @since  org.zhiwu.utils Ver 1.0
 */
public class PreferenceManager{
	/**
	 * @uml.property  name="prefs"
	 * @uml.associationEnd  
	 */
	private static final LeftRightList<String, AppPreference> prefs =
		new LeftRightList<String, AppPreference>();
	
	/**
	 * @author  root
	 */
	private static class SingletonHolder{
		/**
		 * @uml.property  name="instance"
		 * @uml.associationEnd  
		 */
		private static PreferenceManager instance = new PreferenceManager();
	}
	
	private PreferenceManager(){
	}
	
	public static PreferenceManager getInstance (){
		return SingletonHolder.instance;
	}
	
	
	public AppPreference getPreference(String path){
		AppPreference pref=getExistPreference(path);
		if(pref == null){
			pref=createPref(path);
		}

		return pref;
	}

	/**
	 * @param path
	 * @return
	 */
	private AppPreference createPref(String path) {
		AppPreference p= new AppPreference(path);
		prefs.add(path, p);
		return p;
	}

	/**
	 * @param path
	 * @return
	 */
	private static AppPreference getExistPreference(String path) {
		return prefs.getRight(path);
	}

	public void save() {
		for(AppPreference p:prefs.getAllRight()){
			p.save();
		}
	}
}
