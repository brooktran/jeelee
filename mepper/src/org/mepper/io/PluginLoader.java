/* PluginLoader.java 1.0 2010-2-2
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.mepper.app.MapApplication;
import org.zhiwu.app.AppManager;
import org.zhiwu.utils.AppLogging;

/**
 * <B>PluginLoader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-5-28 created
 * @since org.mepper.io Ver 1.0
 * 
 */
public class PluginLoader extends URLClassLoader{
	private static PluginLoader instance;
	private String pluginBase ;
	private final List<Pluginable> pluginables;
	
	private PluginLoader() {
		super(new URL[0]);
		pluginables=new LinkedList<Pluginable>();
	}
	
	public static synchronized PluginLoader getInstance (){
		if(instance == null){
			instance = new PluginLoader();
		}
		return instance;
	}
	
	public void setPluginBase(String base){
		this.pluginBase = base;
	}
	
	public Pluginable loadClass(String pluginType,String pluginName,String className) throws IOException{
		Pluginable plugin=getExistPlugin(className);
		if(plugin != null){
			return plugin;
		}
		return loadPlugin(pluginType, pluginName, className);
	}

	@SuppressWarnings("unchecked")
	private Pluginable loadPlugin(String pluginType, String pluginName,
			String className) throws IOException {
		File dir = new File(getPluginsDir());
		if(!dir.exists() || !dir.canRead()){
			return null;//FIXME throw new Exception( "Could not open directory for reading plugins: " + baseURL);
		}
		
		File[] files=dir.listFiles();
		for(File f:files){
			String path = f.getAbsolutePath();
			if(! path.endsWith(".jar")){
				continue;
			}
			
			JarFile jar= new JarFile(f);
			if(jar.getManifest() == null){
				continue;
			}
			
			Attributes attributes= jar.getManifest().getMainAttributes();
			if((!pluginType.equals(attributes.getValue("plugin-implement"))) || 
					(!pluginName.equals(attributes.getValue("plugin-name")))){
				continue;
			}
			String extension = attributes.getValue("extension");
			for(String ext:extension.split(";")){
				String[] exts=  ext.split("=");
				if(exts[0].equals(className) ){
					JarEntry entry = jar.getJarEntry(exts[1].replace('.', '/')+".class");
					if(entry!= null){
						Class<Pluginable> pluginClass=(Class<Pluginable>) 
							loadFromJar(jar,entry,exts[1]);
						try {
							Pluginable p=pluginClass.newInstance();
							pluginables.add(p);
							return p;
						} catch (Exception e) {
							AppLogging.handleException(e);
						}
					}
				}
			}
		}
		return null;
	}
	
	private Pluginable getExistPlugin(String className) {
		for(Pluginable p:pluginables){
			Class<? extends Pluginable> clazz=p.getClass();
			Class<?>[] interfaces=clazz.getInterfaces();
			for(Class<?> c:interfaces){
				if(c.getName().equals(className)){
					return p;
				}
			}
		}
		return null;
	}

	private Class<?> loadFromJar(JarFile jar, JarEntry entry,
			String className) throws IOException {
		byte[] buf= new byte[(int)entry.getSize()];
		
		InputStream in = jar.getInputStream(entry);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int n;
		while( (n=in.read(buf))>0){
			baos.write(buf,0,n);
		}
		buf = baos.toByteArray();
		if(buf.length < entry.getSize()){
			 throw new IOException(
	                    "Failed to read entire entry! (" + buf.length + "<" +
	                    entry.getSize() + ")");
		}
		
		return  defineClass(className,buf,0,buf.length);
	}

	private String getPluginsDir(){
		if(pluginBase == null){
			pluginBase = AppManager.getPreference(MapApplication.class.getName()).get("plugins.dir");
		}
		return pluginBase;
	}
}
