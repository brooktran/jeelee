/* FileUtils.java 1.0 2010-2-2
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

import java.awt.Component;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.zhiwu.app.Application;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.PreferenceManager;

/**
 * <B>FileUtils</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-31 created
 * @since org.zhiwu.utils Ver 1.0
 * 
 */
public class FileUtils {
	public final static String jpeg = "jpeg";
	public final static String jpg = "jpg";
	public final static String gif = "gif";
	public final static String tiff = "tiff";
	public final static String tif = "tif";
	public final static String png = "png";
	public final static String jps = "jps";
	public final static String bmp = "bmp";

	/**
	 * Get the extension of a file.
	 * eg: name of "Fileutils.java" will return "java".
	 */
	public static String getExtension(File f) {
		String s = f.getName();
		return getExtension(s);
	}

	/**
	 * Get the extension of a file path.
	 */
	public static String getExtension(String filePath) {
		String ext = null;
		int i = filePath.lastIndexOf('.');
		if (i > 0 && i < filePath.length() - 1) {
			ext = filePath.substring(i + 1).toLowerCase();
		}
		return ext;
	}
	
	public static boolean delete(String path){
		return delete(new File(path));
	}

	public static boolean delete(File file) {
		if(file.isDirectory()){
			String[] children=file.list();
			for(int i=0,j=children.length;i<j;i++){
				boolean success=delete(new File(file,children[i]));
				if(!success){
					return false;
				}
			}
		}
		
		return file.delete();
	}

	public static byte[] getFileBytes(File f) throws IOException {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		FileInputStream fis=new FileInputStream(f);
		byte[] buf=new byte[2048];
		for(int count=0;(count=fis.read(buf))!=-1;){
			baos.write(buf, 0, count);
		}
		fis.close();
		return baos.toByteArray();
	}
	
	
	
	//////////
	
	public static File openDirectorie(Component parent){
		return openFile(parent,JFileChooser.DIRECTORIES_ONLY,new FileFilter[0]);
	}
	
	public static File openFile(Component parent,int model,FileFilter... filters){
		JFileChooser fc=getFileChooser(false,model,filters);
		int result=fc.showOpenDialog(parent);
		return getFile(fc, result);
	}

	private static File getFile(JFileChooser fc, int result) {
		if(result == JFileChooser.APPROVE_OPTION){
			saveRecentPath(fc);
			File f=fc.getSelectedFile();
			return f;
		}
		return null;
	}
	
	public static File[] openFiles(Component parent,int model,FileFilter... filters){
		JFileChooser fc=getFileChooser(true,model,filters);
		int result=fc.showOpenDialog(parent);
		if(result == JFileChooser.APPROVE_OPTION){
			saveRecentPath(fc);
			File[] files=fc.getSelectedFiles();
			return files;
		}
		return null;
	}

	private static JFileChooser getFileChooser(boolean multiSelectionEnabled,int model, FileFilter[] filters) {
		JFileChooser fc=new JFileChooser();
		fc.setMultiSelectionEnabled(multiSelectionEnabled);
		fc.setFileHidingEnabled(false);
		fc.setFileSelectionMode(model);
		for(FileFilter f:filters){
			fc.addChoosableFileFilter(f);
		}
		
		String lastPath=PreferenceManager.getInstance().getPreference(Application.class.getName()).get("recent.path");
		if(lastPath!=null){
			fc.setCurrentDirectory(new File(lastPath));
		}
		return fc;
	}

	private static void saveRecentPath(JFileChooser fc) {
		String path=getFilePath(fc.getSelectedFile());
		AppPreference pref=PreferenceManager.getInstance().getPreference(Application.class.getName());
		pref.put("recent.path", path);
	}

	private static String getFilePath(File f) {
		String path=f.getPath();
		if(f.isDirectory()){
			return path;
		}
		int p=path.lastIndexOf(File.separator);
		return path.substring(0, p);
	}
	
	public static void create(String filename){
		File file=new File(filename);
		create(file);
	}
	
	public static void create(File f){
		if(f.exists()){
			return;
		}
		f.mkdirs();
	}

	
	public static File saveAs(Component parent,int model,FileFilter... filters) {
		JFileChooser fc=getFileChooser(false,model,filters);
		int result=fc.showSaveDialog(parent);
		return getFile(fc, result);
	}

	
	
}
