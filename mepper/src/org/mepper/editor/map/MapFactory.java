/* MapFactory.java 1.0 2010-2-2
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
package org.mepper.editor.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.mepper.app.MapApplication;
import org.mepper.utils.MepperConstant;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.AppResources;

/**
 * <B>MapFactory</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-11 created
 * @since org.mepper.editor.map Ver 1.0
 * 
 */

@SuppressWarnings("unchecked")
public class MapFactory {
	private static final java.util.Map<String, Class<? extends Map>> supportedMaps;
	private static Map map;
	
	static{
		AppPreference p= AppManager.getPreference(MapApplication.class.getName());
		String[] mapTypes = p.get("supported.map.type").split(",");
		
		AppResources r= AppManager.getResources();
		supportedMaps = new HashMap<String, Class<? extends Map>>(mapTypes.length);
		
		for(String mapType:mapTypes){
			try {
				supportedMaps.put(r.getString(mapType), (Class<? extends Map>) Class.forName(mapType) );
			} catch (ClassNotFoundException e) {
				AppLogging.handleException(e);
			}
		}
		
	}
	
	private MapFactory(){
	}
	

	public static Map getDefaultMap() {
		Map map=new DiamondMap();
		map.setID((int) System.currentTimeMillis());
		return map;
	}

	public static Object[] getSupportedTypes() {
		return supportedMaps.keySet().toArray();
	}
	
	public static Map getMap(String key){
		Map map = null;
		try {
			map = supportedMaps.get(key).newInstance();
			map.setID(MepperConstant.DEFAULT_DEFINITION_MAP_ID);
		} catch (InstantiationException e) {
			AppLogging.handleException(e);
		} catch (IllegalAccessException e) {
			AppLogging.handleException(e);
		}
		return map;
	}

	public static String getMapTypeDisplayName(Map map) {
		Iterator<Entry<String, Class<? extends Map>>> it=supportedMaps.entrySet().iterator();
		while(it.hasNext()){
			Entry<String, Class<? extends Map>> en=it.next();
			if(en.getValue() .equals( map.getClass())){
				return en.getKey();
			}
		}
		return "";
	}


	/**
	 * copy attribute of the map without ID
	 */
	public static void copy(Map oldVal, Map newVal) {
		newVal.setOffset(oldVal.getOffset().create());
		newVal.setTileStep(oldVal.getTileWidth(), oldVal.getTileHeight());
		newVal.setLogicalSize(oldVal.getLogicalSize().width, oldVal.getLogicalSize().height);
		newVal.setName(oldVal.getName());
		for(int i=0,j=oldVal.getLayerCount();i<j;i++){
			newVal.addLayer(oldVal.getLayer(i), i);
		}
	}
	
	
	///
	public static Map getDefaultDefinitionMap(){
		if(map == null){
			map = getMap ();
		}
		return map;
	}
	
	public static Map refreshDefaultDefinitionMapType(Map newValue){
		if(! map.getClass().equals(newValue.getClass())){
			synchronized (map) {
				try {
					Map oldValue = newValue.getClass().newInstance();
					copy(oldValue, newValue);
					newValue.setID(MepperConstant.DEFAULT_DEFINITION_MAP_ID);
					newValue.setName(getMapTypeDisplayName(newValue));
					map=newValue;
				} catch (Exception e) {
					AppLogging.handleException(e);
				}
			}
		}
		return map;
	}

	private synchronized static  Map getMap() {
		if(map != null){
			return map;
		}
		AppPreference pref  = AppManager.getPreference(MapApplication.class.getName());
		String classname = pref.get("map.type");
		try {
			map =(Map) Class.forName(classname).newInstance();
			map.setID(MepperConstant.DEFAULT_DEFINITION_MAP_ID);
			map.setName(getMapTypeDisplayName(map));
			map.setSize(pref.getInteger("map.logical.width"),
					pref.getInteger("map.logical.height"));
		} catch (ClassNotFoundException e) {
			AppLogging.handleException(e);
		} catch (InstantiationException e) {
			AppLogging.handleException(e);
		} catch (IllegalAccessException e) {
			AppLogging.handleException(e);
		}
		
		return map;
	}

}
