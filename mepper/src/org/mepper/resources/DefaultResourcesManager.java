/* DefaultResourceManager.java 1.0 2010-2-2
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
package org.mepper.resources;

import java.io.File;
import java.io.IOException;

import org.mepper.app.MapApplication;
import org.mepper.editor.tile.CustomTile;
import org.mepper.editor.tile.Tile;
import org.mepper.io.MapIOHelper;
import org.mepper.io.Storable;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.utils.AppLogging;

/**
 * <B>DefaultResourceManager</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-11 created
 * @since org.mepper.resource Ver 1.0
 * 
 */
public class DefaultResourcesManager extends AbstractResourcesManager { 
	private static AbstractResourcesManager instance;
	private static final Object RESOURCE_LOCK = new Object();
	
	private static final int TILE_ID_INCREMENT = 9;
	private static int tileID= 1;
	
	private static final int LIBRARY_ID_INCREMENT = 1;
	private static int libraryID= 1;
	
	private static final int CUSTOM_ID_INCREMENT = 1;
	private static int customID= 1;
	
	
	public DefaultResourcesManager(){
	}
	
	public static ResourceManager getInstance(){//XXX move this method to DataManager
		if (instance == null) {
			synchronized (RESOURCE_LOCK) {
				if (instance == null) { 
					instance = new DefaultResourcesManager();
				}
			}
		}
		return instance;
	}
	
	@Override
	public void init() {
		initID();
		initNodes();
		// read from files
		readLibrarys();
		addDefaultLibrary();
	}
	
	public void addDefaultLibrary() {
		if(root.getChildCount() == 0){
			LibraryResource lib=new LibraryResource("默认资源");//TODO I18N
			createResourceID(lib);
			addResourceChild(lib);
		}
	}

	public void initID() {
		AppPreference pref=AppManager.getPreference(MapApplication.class.getName());
		tileID = pref.getInteger("tile.id");
	}

	public void initNodes() {
		root=new LibraryResource("root");
		root.setID(-1);
		currentNode = root;
	}
	
	@Override
	public void createResourceID(StorableResource r) {
		synchronized (RESOURCE_LOCK) {
			Storable s= r.getSource();
			
			if(r instanceof LibraryResource){
				generateLibraryID(r);
				return;
			}
			
			if (s instanceof Tile) {
				generateTileID(s);
				return;
			}
			if(s instanceof CustomTile){
				generateCustomTileID(s);
				return;
			}
		}
	}

	private void generateCustomTileID(Storable s) {
		  customID += CUSTOM_ID_INCREMENT;
		  s.setID(customID);
	}

	private void generateLibraryID(StorableResource r) {
		libraryID += LIBRARY_ID_INCREMENT;
		r.setID(libraryID);
	}

	private void generateTileID(Storable s) {
		do {
			tileID += TILE_ID_INCREMENT;
			s.setID(tileID);
		} while (existID(tileID));
		s.setID(tileID);
	}

	private boolean existID(int id) {
		return existID(root,id);
	}

	private boolean existID(StorableResource node, int id) {
		if(node.getID() == id){
			return true;
		}
		for(int i=0,j=node.getChildCount();i<j;i++){
			StorableResource r=node.getChild(i);
			if (existID(r, id)) {
				return true;
			}
		}
		return false;
	}

	public void readLibrarys() {
		String dataPath = getDataPath()+"library";
		MapIOHelper helper = new MapIOHelper();
		for (StorableResource r : helper.read(dataPath)) {
			root.addChild(r);
		}
	}

	@Override
	public void save() {
		AppPreference pref=AppManager.getPreference(MapApplication.class.getName());
		pref.put("tile.id",tileID);
		
		String dataPath = getDataPath()+"library/";
		MapIOHelper helper = new MapIOHelper();

		File f = new File(dataPath);
		if(!f.exists()){
			f.mkdirs();
		}
		
		try {
			for (int i = 0, j = root.getChildCount(); i < j; i++) {
				helper.save(root.getChild(i), dataPath);
			}
		} catch (IOException e) {
			AppLogging.handleException(e);
		}
	}
}
