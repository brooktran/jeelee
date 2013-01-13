/* LiuyaoDataManager.java 1.0 2010-2-2
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
package org.divine.persistance;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipOutputStream;

import org.divine.app.liuyao.EightDiagrams;
import org.divine.app.liuyao.LiuYao;
import org.divine.app.liuyao.Lunar;
import org.jdom.Element;
import org.jtools.app.persistent.Compresser;
import org.jtools.app.persistent.DataException;
import org.zhiwu.utils.AppResources;
import org.zhiwu.utils.FileUtils;
import org.zhiwu.utils.ZipUtils;
import org.zhiwu.xml.DataSourceException;
import org.zhiwu.xml.DefaultXMLManagerImpl;
import org.zhiwu.xml.XMLManager;

/**
 * <B>LiuyaoDataManager</B>.
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-4-26 created
 * @since org.divine.persistance Ver 1.0
 */
public class LiuyaoDataManager {

	private static final Object LOCKER = new Object();

	private static final int BUFFER_SIZE = 1024 * 2;

	// means
	// zip
	private static volatile LiuyaoDataManager instance;

	private final String dbPath;
	
	/** The path of the backup database . */
	private final String bkdbPath;
	private final String tmpPath;
	private final String tmpDBPath;

//	private static int initCount = 0;

	/**
	* prevent to create a new instance.
	* 
	* Instantiates a new data manager.
	*/
	public LiuyaoDataManager() throws DataException {
		AppResources r = AppResources.getResources("org.jtools.app.Labels");
		dbPath = r.getString("db");
		bkdbPath=r.getString("bkdb");
		// sets temp path
		File f = new File(dbPath);
		Random random=new Random();
		long l=random.nextLong();
//		tmpPath = f.getParent() + File.separator + "~tmp"+l + File.separator;
		tmpPath = f.getParent() + File.separator + "temp" + File.separator;
//		tmpDBPath = f.getParent()+File.separator+"~tmp"+l+".ldb";
		tmpDBPath = f.getParent()+File.separator+"temp"+".ldb";
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
			is = new FileInputStream(tmpPath + File.separator + name + ".ly");

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
//				prepare();
//				getTable(name);
//			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Initial data.
	 */
	public void initialData() {
		Initializer initializer = new Initializer();

		Thread thread = new Thread(initializer);
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * @return
	 */
	public static LiuyaoDataManager getInstance() throws DataException {
		if (instance == null) {
			synchronized (LOCKER) {
				if (instance == null) {
					instance = new LiuyaoDataManager();
				}
			}
		}
		return instance;
	}

	/**
	 * @param labels
	 * @return
	 */
	public String getEightDiagramName(String labels) throws DataException,
			IOException {
		try {
			XMLManager m = new DefaultXMLManagerImpl(getTable("Diagrams"));
			Element e = (Element) m.getSingleNodeByXPath(m.getRootElement(),
					"/root/Diagram[@id='" + labels + "']");
			return e.getAttributeValue("name");
		} catch (DataSourceException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * @param liuYao
	 */
	public void saveLiuyao(LiuYao liuYao) throws IOException {
		try {
			XMLManager xmlManager = new DefaultXMLManagerImpl(
					getTable("liuyao"));

			Element e = (Element) xmlManager.getSingleNodeByXPath(xmlManager
					.getRootElement(), "/root/liuyao[@id='" + liuYao.getID()
					+ "']");

			if (e == null) {// create a new element
				e =new Element("liuyao");
				e.setAttribute("id", liuYao.getID());
				e.setAttribute("time", liuYao.getLunar().getCalendar()
						.getTimeInMillis()
						+ "");
				e.setAttribute("yaos", liuYao.getDiagrams().getLabels());
				e.setAttribute("problem", liuYao.getProblem());
				e.setText(liuYao.getParseText());
				xmlManager.getRootElement().addContent(e);
			}else {// modify the old element
				e.setText(liuYao.getParseText());
			}
			
			saveXmlManager(xmlManager,"liuyao");
		} catch (DataSourceException e1) {
			e1.printStackTrace();
		} catch (DataException e1) {
			e1.printStackTrace();
		} 
		// catch (FileNotFoundException e) {
		// e.printStackTrace();
		// }
	}

	/**
	 * @param xmlManager
	 * @throws IOException
	 * @throws DataException
	 */
	private void saveXmlManager(XMLManager xmlManager,String tableName) throws IOException,
			DataException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		xmlManager.saveToStream(baos);
		saveTable(baos.toByteArray(),tableName);
	}

	/**
	 * @param byteArray
	 */
	private synchronized void saveTable(byte[] data, String tableName) throws IOException,
			DataException {
		FileOutputStream fos = new FileOutputStream(new File(tmpPath
				+ tableName + ".ly"));
		fos.write(Compresser.compression(data));
		fos.flush();
		fos.close();
	}

	/**
	 * 
	 */
	public boolean prepare() throws IOException, DataException {
//		initCount++;
		String dbp=dbPath;
		File file = new File(dbp);
		if (!file.exists()) {
			dbp=bkdbPath;
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
	 * 
	 */
	private void deleteTmpFile() {
		FileUtils.delete(tmpPath);
	}

	/**
	 * @return
	 */
	private boolean existTmpFile() {
		return new File(tmpPath).exists();
	}

	private void prepareTmpFile(String dbPath) throws IOException {
		ZipUtils zip = new ZipUtils(dbPath);
		zip.decompress(tmpPath);
	}
	

	public void close()  {
//		FileUtils.delete(dbPath);

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
			FileUtils.delete(dbPath);
			File file = new File(tmpDBPath);
			file.renameTo(new File(dbPath));
		}
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

	/**
	 * @return
	 */
	public List<LiuYao> getSaved() {
		List<LiuYao> yaos=new ArrayList<LiuYao>();
		try {
			XMLManager xmlManager=new DefaultXMLManagerImpl(getTable("liuyao"));
			List<Object> elements= xmlManager.getNodesByXPath(
					xmlManager.getRootElement(), "/root/liuyao");
			for(int i=0,j=elements.size();i<j;i++){
				Element e=(Element) elements.get(i);
				// lunar
				long time=Long.parseLong(e.getAttributeValue("time"));
				Calendar calendar=Calendar.getInstance();
				calendar.setTimeInMillis(time);
				// eight diagrams
				EightDiagrams diagrams=new EightDiagrams(e.getAttributeValue("yaos"));
				diagrams.setup();
				LiuYao yao=new LiuYao(new Lunar(calendar), diagrams,e.getAttributeValue("id"));
				yao.setProblem(e.getAttributeValue("problem"));
				yao.setParseText(e.getText());
				yaos.add(yao);
			}
			
		} catch (DataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		return yaos;
	}

	/**
	 * @param ly
	 */
	public void deleteLiuyao(LiuYao liuYao) {
		if (liuYao==null) {
			return;
		}
		XMLManager xmlManager;
		try {
			xmlManager = new DefaultXMLManagerImpl(getTable("liuyao"));
			Element e = (Element) xmlManager.getSingleNodeByXPath(xmlManager
					.getRootElement(), "/root/liuyao[@id='" + liuYao.getID()
					+ "']");
			if(e==null){
				return;
			}
			xmlManager.getRootElement().removeContent(e);
			saveXmlManager(xmlManager, "liuyao");
		} catch (DataSourceException e1) {
			e1.printStackTrace();
		} catch (DataException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		
		
		
		
		
		
	}
}