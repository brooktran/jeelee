/* MapIOHelper.java 1.0 2010-2-2
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
package org.mepper.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.mepper.io.xml.XMLReader;
import org.mepper.io.xml.XMLWriter;
import org.mepper.resources.StorableResource;
import org.zhiwu.utils.AppLogging;


/**
 * <B>MapIOHelper</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-3-6 created
 * @since org.map.editor.io Ver 1.0
 * 
 */
public class MapIOHelper {
	public void save(StorableResource r, String path) throws IOException {
		try {
			MapWriter writer = getWriter();
			writer.writeResource(r, path);
		} catch (Exception e) {
			AppLogging.handleException(e);
		}
	}

	public StorableResource[] read(String path) {
		File f=new File(path);
		if (!f.exists()) {
			return new StorableResource[0] ;
		}
		return read(f);
	}

	private StorableResource[] read(File f) {
		MapReader reader=getReader();
		List<StorableResource> resources=new ArrayList<StorableResource>();

		try {
			if (f.isDirectory()) {
				for (File file : f.listFiles(reader.getFilters()[0])) {
					readResource(reader, resources, file);
				}
			} else {
				readResource(reader, resources, f);
			}
		} catch (IOException e) {
			AppLogging.handleException(e);
			return new StorableResource[0];
		}
		
		return resources.toArray(new StorableResource[resources.size()]);
	}

	private StorableResource readResource(MapReader reader,
			List<StorableResource> resources, File file) throws IOException,
			FileNotFoundException {
		StorableResource r=readFromFile(reader, file);
		if(r!= null){
			resources.add(r);
		}
		return r;
	}

	private StorableResource readFromFile(MapReader reader, File file)
			throws IOException, FileNotFoundException {
		StorableResource r = null;
		try {
			FileInputStream fis= new FileInputStream(file);
			r = reader.readResource(fis);
			fis.close();
		} catch (Exception e) {
			AppLogging.handleException(e);
		}
		return  r;
	}
	
	
	public static MapWriter getWriter(){
//		MapWriter writer=null;
//		try {
//			PluginLoader loader = PluginLoader.getInstance();
//			writer= (MapWriter)loader.loadClass("org.map.MapIO",AppManager.getPreference(MapApplication.class.getName()).get("map.io.plugin"),
//					"org.mepper.io.MapWriter");
//		} catch (IOException e) {
//			AppLogging.handleException(e);
//		}
//		return writer;
		return new XMLWriter();
	}
	
	public static MapReader getReader(){
//		MapReader reader = null;
//		try {
//			PluginLoader loader = PluginLoader.getInstance();
//			reader= (MapReader)loader.loadClass("org.map.MapIO",AppManager.getPreference(MapApplication.class.getName()).get("map.io.plugin"),
//					"org.mepper.io.MapReader");
//		} catch (IOException e) {
//			AppLogging.handleException(e);
//		}
//		return reader;
		return new XMLReader();
	}

}
