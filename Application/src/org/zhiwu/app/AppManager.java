/* AppManager.java 1.0 2010-2-2
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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.PreferenceManager;
import org.zhiwu.utils.SwingResources;

/**
 * <B>AppManager</B> managers the UI resources.
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-12-10 created
 * @since  org.zhiwu.app Ver 1.0
 */
public class AppManager{
	private static String Labels = "org.zhiwu.app.Labels";
	private static Locale locale;
	private static Map<Object, Object> AppDefault=new HashMap<Object, Object>();
	protected  static  PropertyChangeSupport propertySupport = new PropertyChangeSupport(AppManager.class);
	protected static SwingResources r;

	public static void put(Object key,Object value){
		Object oldValue=AppDefault.put(key, value);
		firePropertyChange(key.toString(), oldValue, value);
	}
	
	public static Object get(Object key){
		return AppDefault.get(key);
	}

	public static SwingResources getResources(){
		if(r==null){
			createResources();
		}
		return r;
	}

	private static void createResources() {
			if(r==null){
				r= SwingResources.getResources(Labels,locale==null?Locale.getDefault():locale);
			}
	}

	public static void setLabels(String labels) {
		Labels = labels;
		createResources();
	}

	public static  String getLabels() {
		return Labels;
	}
	public static  void setLocale(Locale l) {
		Locale oldValue = locale;
		Locale.setDefault(locale = l);
		createResources();
		getPreference(Application.class.getName()).put("locale", l.toString());
		firePropertyChange(ApplicationConstant.PROPERTY_LOCALE, oldValue, locale);
	}

	public static Locale getLocale() {
		return locale;
	}

	public static void addPropertyChangeListener(PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(listener);
	}

	public static void addPropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertySupport.addPropertyChangeListener(propertyName, listener);
	}

	protected static void firePropertyChange(String propertyName,
			boolean oldValue, boolean newValue) {
		propertySupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected static void firePropertyChange(String propertyName, int oldValue,
			int newValue) {
		propertySupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	protected static void firePropertyChange(String propertyName,
			Object oldValue, Object newValue) {
		propertySupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	public static void removePropertyChangeListener(
			PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(listener);
	}

	public static void removePropertyChangeListener(String propertyName,
			PropertyChangeListener listener) {
		propertySupport.removePropertyChangeListener(propertyName, listener);
	}

	
	///  preference
	/**
	 * @uml.property  name="prefManager"
	 * @uml.associationEnd  
	 */
	private static final PreferenceManager prefManager=PreferenceManager.getInstance();
	public static AppPreference getPreference(String name) {
		return prefManager.getPreference(name);
	}


	
}
