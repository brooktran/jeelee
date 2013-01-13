/* DataManager.java 1.0 2010-2-2
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
package org.jtools.app.persistent;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.daily.IStorable;
import org.divine.persistance.Initializer;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jtools.annotations.RTF;
import org.jtools.annotations.Storable;
import org.zhiwu.utils.AppResources;
import org.zhiwu.utils.FileUtils;
import org.zhiwu.utils.ZipUtils;
import org.zhiwu.xml.DataSourceException;
import org.zhiwu.xml.DefaultXMLManagerImpl;
import org.zhiwu.xml.XMLManager;

/**
 * <B>DataManager</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-11-16 created
 * @since org.jtools.app.persistent Ver 1.0
 * 
 */
public class DataManager {
	
	private static final String LABELS="org.jtools.app.Labels";
	
	public static final String XML_TEMPLE_PATH = "resources/info/temple.xml";

	private final static String DBPath;
	/** The path of the backup database . */
	private final static String backupDBPath;
	private final static String tmpPath;
	private final static String tmpDBPath;
	
	private XMLManager xmlManager;
	
	static{
		AppResources r=AppResources.getResources(LABELS);
		DBPath = r.getString("db");
		backupDBPath=r.getString("bkdb");
		// sets temp path
		File f = new File(DBPath);
		Random random=new Random();
		long l=random.nextLong();
//		tmpPath = f.getParent() + File.separator + "~tmp"+l + File.separator;
		tmpPath=f.getParent() + File.separator +"temp"+ File.separator;
		tmpDBPath = f.getParent()+File.separator+"~tmp"+l+".ldb";

	}
	
	/**
	 * 
	 */
	public DataManager() {
		// TODO Auto-generated constructor stub
	}
	
	
	/**
	 * @param d
	 */
	public synchronized  void remove(IStorable o) {
		if(o.getClass().getAnnotation(Storable.class) ==null){
			throw new IllegalArgumentException("the object was not stroable.");//$NON-NLS-1$
		}
		XMLManager xml;
		try {
			xml = new DefaultXMLManagerImpl(getTable(o.getClass().getName()));
			Element e=(Element)xml.getSingleNodeByXPath(xml.getRootElement(), 
					"/root/"+o.getClass().getName()+"[@ID='"+o.getID()+"']");
			if(e!= null){// create a new element
				e.getParent().removeContent(e);
			}
			
			saveTable(xml, o.getClass().getName());
		} catch (DataSourceException e1) {
			e1.printStackTrace();
		} catch (DataException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}
	
	
	/**
	 * @param d 
	 */
	public synchronized void saveObject(IStorable o) throws IOException{
		if(o.getClass().getAnnotation(Storable.class) ==null){
			throw new IllegalArgumentException("the object was not stroable.");//$NON-NLS-1$
		}
		
		// check objecct exist.
		try {
			XMLManager xml=new DefaultXMLManagerImpl(getTable(o.getClass().getName()));
			Element e=(Element)xml.getSingleNodeByXPath(xml.getRootElement(), 
					"/root/"+o.getClass().getName()+"[@ID='"+o.getID()+"']");
			
			if(e!= null){// create a new element
				e.getParent().removeContent(e);
			}else {
				e=new Element(o.getClass().getName());
			}
			Field[] fields = o.getClass().getDeclaredFields();
			for (Field f : fields) {
				if (f.getAnnotation(Storable.class) != null) {
					f.setAccessible(true);
					e.setAttribute(f.getName(), f.get(o).toString());
				}else if (f.getAnnotation(RTF.class) != null) {
					// CDATA data=new CDATA(f.get(o).toString());
					// e.addContent(data);
					f.setAccessible(true);
					e.setText(f.get(o).toString());
				}
			}
			xml.getRootElement().addContent(e);
				
			saveTable(xml, o.getClass().getName());
			
		} catch (DataSourceException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the table.
	 * 
	 * @param name the name of the table.
	 * 
	 * @return the table
	 * 
	 * @throws DataException the data exception
	 */
	public InputStream getTable(String name) throws DataException, IOException {
		InputStream is = null;
		try {
			is = new FileInputStream(tmpPath + File.separator + name +".ts");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			baos.reset();
			byte[] buf = new byte[1024];
			int i = -1;
			while ((i = is.read(buf)) != -1) {
				baos.write(buf, 0, i);
			}
			baos.flush();
			byte[] data = baos.toByteArray();
			baos.close();

//			initCount = 0;
			return new ByteArrayInputStream(Compresser.decompression(data));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
//			if (initCount < 3) {

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null)
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	
	

	public void saveTable(XMLManager manager, final String tableName)
			throws DataException, IOException {
		FileOutputStream fos=
			new FileOutputStream(new File(tmpPath+tableName+".ts"));
		fos.write(Compresser.compression(getXMLData(manager)));
		fos.flush();
		fos.close();
	}

	/**
	 * @param manager
	 * @return
	 */
	private byte[] getXMLData(XMLManager manager) {
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		manager.saveToStream(baos);
		return baos.toByteArray();
	}


	public void addData(byte[] data, final String name) throws DataException {
		// TODO if data exist, then backup it.
		byte[] output = Compresser.compression(data);
		ZipOutputStream os = getDatabaseOutput();

		ZipEntry entry = new ZipEntry(name);
		try {
			os.putNextEntry(entry);
			os.write(output);
		} catch (IOException e) {
			throw new DataException(e);
		} finally {
			try {
				os.flush();
				// os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	
	/**
	 * if the data doesn't exist, then create it.
	 */
	public void initData(){
		Initializer initializer=new Initializer();
		
		Thread thread = new Thread(initializer);
		thread.setDaemon(true);
		thread.start();
	}
	
	/////////////////////  prepare  ///////////////////////////
	
	public boolean prepare() throws IOException{
		String dbp=DBPath;
		File file = new File(dbp);
		if (!file.exists()) {
			dbp=backupDBPath;
		}

		if (existTmpFile()) {
			deleteTmpFile();
		}

		prepareTmpFile(dbp);

		if (existTmpFile()) {
			return true;
		}
		return false;
	}
	/**
	 * @return
	 */
	private boolean existTmpFile() {
		return new File(tmpPath).exists();
	}
	
	/**
	 * 
	 */
	private void deleteTmpFile() {
		FileUtils.delete(tmpPath);
	}
	
	/**
	 * @return
	 */
	private ZipOutputStream getDatabaseOutput() {
		ZipOutputStream out = null;
		try {
			out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(new File(tmpDBPath), true)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			// TODO 致命错误,重启程序
		}
		return out;
	}


	private void prepareTmpFile(String DBPath) throws IOException {
		ZipUtils zip = new ZipUtils(DBPath);
		zip.decompress(tmpPath);
	}
	
	
//////////////////////////////
	
	/**
	 * delete the temp files, and compress the data to a file.
	 */
	public void close()  {
//		FileUtils.delete(DBPath);

		ZipOutputStream zos = getDatabaseOutput();
		ZipUtils zip = new ZipUtils(tmpPath);
		boolean closeFlag=zip.compress(zos);
		
		try {
			zos.flush();
			zos.close();
		} catch (IOException e) {
			e.printStackTrace();
			closeFlag=false;
		}
		
		if (closeFlag) {
			deleteTmpFile();// delete tmp path
			FileUtils.delete(DBPath);
			File file = new File(tmpDBPath);
			file.renameTo(new File(DBPath));
		}
	}


	
	
	
	//////////////////////////////////////////////////////
	/**
	 * @param name
	 */
	public void createTable(String name) throws DataException {
		if(existTable(name)){
			return;
		}
		
		try {
			XMLManager xml=new DefaultXMLManagerImpl(
					new BufferedInputStream(
							new FileInputStream(XML_TEMPLE_PATH)));
			saveTable(xml, name);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DataSourceException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @param name
	 * @return
	 */
	protected boolean existTable(String name) throws DataException {
		try {
			return getTable(name) != null;
		} catch (IOException e) {
//			e.printStackTrace();
			return false;
		}
	}


	/**
	 * return the xml element data.
	 * @param tableName
	 * @param itemName
	 * @return 
	 * @throws DataSourceException
	 * @throws DataException
	 * @throws IOException
	 */
	public List<? extends IStorable> getSaved(String tableName, Class<? extends IStorable> c) throws DataSourceException, DataException, IOException {
		List<IStorable> result=new ArrayList<IStorable>();
		if(c.getAnnotation(Storable.class) == null){
			return null;
		}

		try {
			XMLManager xml = new DefaultXMLManagerImpl(getTable(tableName));
			List<Object> elements = xml.getNodesByXPath(xml.getRootElement(),
					"/root/" + c.getName());
			for (Object o : elements) {
				Element e = (Element) o;
				IStorable storable;
				storable = c.newInstance();
				@SuppressWarnings("unchecked")
				List<Object> attributes = e.getAttributes();
				for (Object o2 : attributes) {
					Attribute a = (Attribute) o2;
					Field f = storable.getClass().getDeclaredField(a.getName());
					f.setAccessible(true);
					setField(storable, a, f);
				}

				Field[] fields = storable.getClass().getDeclaredFields();
				for (Field f : fields) {
					if (f.getAnnotation(RTF.class) != null) {
						f.setAccessible(true);
						f.set(storable, e.getText());
					}
				}
				result.add(storable);
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}

		return result;
	}


	/**
	 * @param storable
	 * @param a
	 * @param f
	 * @throws IllegalAccessException
	 */
	private void setField(IStorable storable, Attribute a, Field f)
			throws IllegalAccessException {
		Class<?> c=f.getType();
		if(c.getName().equals("boolean")){
			boolean b=Boolean.parseBoolean(a.getValue());
			f.set(storable, b);
			return;
		}
		f.set(storable, a.getValue());
	}
	
}
