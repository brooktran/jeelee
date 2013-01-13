package com.test.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 类<B> CreateFile </B>是用于批量创建文件的类。
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-2-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class FileConsole {
	
	public static final String TXT=".txt";
	private File file;
	private String dir;//路径
	
	public FileConsole(String dir){
		this.dir=dir;
		file=new File(dir);
	}
	
	/**
	 * 作用:创建txt文本
	 * @param num 要创建的文本的数量
	 */
	public void createText(int num) throws IOException {
		if(!checkFileDirectory()){
			throw new IOException(dir+"is not exist or not a directory");
		}
		//file.delete();
		for(int i=0;i<num;i++){
			StringBuffer sb=new StringBuffer();
			for(int j=0;j<num;j++){
				sb.append(dir+j+"\r\n");
			}
			String fileName=dir+i+TXT;
			FileOutputStream out=new FileOutputStream(dir+"//"+fileName,true);
			out.write(sb.toString().getBytes("utf-8"));
			out.close();
		}
	}
	
	/**
	 * 作用:创建txt文本
	 * 
	 * @param context 要写入的内容
	 */
	public void createText( StringBuffer[] context) throws IOException {
		if(!checkFileDirectory()){
			throw new IOException(dir+"is not exist or not a directory");
		}
		//file.delete();
		
		String fileName = dir + TXT;
		FileOutputStream out = new FileOutputStream(dir + "//" + fileName, true);
		out.write(context.toString().getBytes("utf-8"));
		out.close();
		
	}
	
	public void clearDirectory(String suff) throws IOException{
		//setDir(dir);
		//StringBuffer sb=new StringBuffer();
		if(!checkFileDirectory()){
			throw new IOException(dir+"is not exist or not a directory");
		}
		//String[] fileName=file.list();
		File[] files=file.listFiles();
		for (File f : files) {
			f.delete();
		}
	}
	
	
	
	private boolean checkFileDirectory(){
		if(!file.exists()||!file.isDirectory()){
			return false;
		}
		return true;
	}
	
	/**
	 * @return dir
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @param dir 要设置的 dir
	 */
	public void setDir(String dir) {
		this.dir = dir;
		file=new File(dir);
	}

	/**
	 * @return file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file 要设置的 file
	 */
	public void setFile(File file) {
		this.file = file;
	}

	public static void main(String[] args) {
		FileConsole file=new FileConsole("testXXX");
		try {
			file.createText(10);
			//file.clearDirectory(FileConsole.TXT);
		} catch (IOException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
	}
}
