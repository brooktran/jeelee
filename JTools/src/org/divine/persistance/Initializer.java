/* Initializer.java 1.0 2010-2-2
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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.jdom.Element;
import org.jtools.app.persistent.Compresser;
import org.jtools.app.persistent.DataException;
import org.zhiwu.utils.AppResources;
import org.zhiwu.xml.DataSourceException;
import org.zhiwu.xml.DefaultXMLManagerImpl;
import org.zhiwu.xml.XMLManager;

/**
 * <B>Initializer</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-4-27 created
 * @since org.divine.persistance Ver 1.0
 * 
 */
public class Initializer implements Runnable {
	public static final String XML_TEMPLE_PATH = "resources/info/temple.xml";
	private static int initFlag = 0;

	private static final Object LOCKER = new Object();
	private static volatile Initializer instance;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		reset();
		init();
		try {
			start();
		} catch (DataException e) {
			e.printStackTrace();
		}finally{
			try {
				out.flush();
//				out.closeEntry();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	private void init() {
	}

	/**
	 * 
	 */
	private void reset() {
	}

	public void start() throws DataException {
		initTable();
	}

	/**
	 * 
	 */
	private void initTable() throws DataException {
		// init liuyao.ly
		initLiuyao();
		// init diagrams.ly
		 initDiagrams();
	}

	/**
	 * 
	 */
	private void initLiuyao() throws DataException {
		if (existsTable("liuyao")) {
			return;
		}

		try {
			XMLManager manager = createTable();
			saveTable(manager, "liuyao.ly");
		} catch (FileNotFoundException e) {
			if (!createTemple()) {
				throw new DataException(e);
			}
		} catch (DataSourceException e) {
			e.printStackTrace();
			if (!createTemple()) {
				throw new DataException(e);
			}
		} catch (DataException e) {
			if (!createTemple()) {
				throw new DataException(e);
			}
		}

	}

	/**
	 * @param string
	 * @return
	 */
	private boolean existsTable(String string) {
		try {
			return LiuyaoDataManager.getInstance().getTable("liuyao") != null;
		} catch (DataException e) {// file no exist
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	/**
	 * Inits the diagrams table.
	 * 
	 * @throws DataException the data exception
	 */
	public void initDiagrams() throws DataException {
		// System.out.println("Initializer.initDiagrams()");
		final String EIGHT_STRING = "111111坤为地 011111山地剥 101111水地比  001111风地观 "
				+ "110111雷地豫  010111火地晋 100111泽地萃  000111天地否 "
				+ "111011地山谦  011011艮为山  101011水山蹇 001011风山渐 "
				+ "110011雷山小过 010011火山旅 100011泽山咸 000011天山遯 "
				+ "111101地水师 011101山水蒙   101101坎为水 001101风水涣 "
				+ "110101雷水解 010101火水未既 100101泽水困 000101天水讼 "
				+ "111001地风升 011001山风蛊  101001水风井 001001巽为风 "
				+ "110001雷风恒 010001火风鼎  100001泽风大过 000001天风姤 "
				+ "111110地雷复 011110山雷颐  101110水雷屯 001110风雷益 "
				+ "110110震为雷 010110火雷噬嗑 100110泽雷随 000110天雷无妄 "
				+ "111010地火明夷 011010山火贲 101010水火即济 001010风火家人 "
				+ " 110010雷火丰 010010离为火  100010泽火革 000010天火同人 "
				+ "111100地泽临 011100山泽损 101100水泽节 001100风泽中孚 110100雷泽随 "
				+ "010100火泽睽 100100兑为泽 000100天泽屦 111000地天泰 "
				+ "011000山天大畜 101000水天需 001000风天小畜 110000雷天大壮 "
				+ "010000火天大有 100000泽天夬 000000乾为天";

		initFlag++;
		try {
			XMLManager manager = createTable();

			StringTokenizer t = new StringTokenizer(EIGHT_STRING);
			Element root = manager.getRootElement();
			while (t.hasMoreTokens()) {
				String s = t.nextToken();
				Element e = new Element("Diagram");
				e.setAttribute("id", s.substring(0, 6));
				e.setAttribute("name", s.substring(6));
				root.addContent(e);
			}

			saveTable(manager, "Diagrams.ly");
		} catch (FileNotFoundException e) {
			if (!createTemple()) {
				throw new DataException(e);
			}
		} catch (DataSourceException e) {
			if (!createTemple()) {
				throw new DataException(e);
			}
		}
	}

	/**
	 * @param manager
	 * @throws DataException
	 */
	private void saveTable(XMLManager manager, final String tableName)
			throws DataException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		manager.saveToStream(baos);
		try {
			baos.flush();

			// the relation information about the six-four diagrams
			addData(baos.toByteArray(), tableName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * @return
	 * @throws DataSourceException
	 * @throws FileNotFoundException
	 */
	protected XMLManager createTable() throws DataSourceException,
			FileNotFoundException {
		XMLManager manager = new DefaultXMLManagerImpl(
				new BufferedInputStream(new FileInputStream(
						XML_TEMPLE_PATH)));
		return manager;
	}

	/**
	 * 
	 */
	private boolean createTemple() {
		if (initFlag > 2) {
			return false;
		}
		// TODO create files temple.xml

		init();
		return true;
	}

	/**
	 * @param byteArray
	 */
	private void addData(byte[] data, final String name) throws DataException {
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
//				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	private ZipOutputStream out = null;

	/**
	 * @return
	 */
	private ZipOutputStream getDatabaseOutput() {
		try {
			if (out == null) {
				out = new ZipOutputStream(
						new BufferedOutputStream(new FileOutputStream(new File(
								AppResources.getResources("org.jtools.app.Labels")
										.getString("db")), true)));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return out;
	}


	/**
	 * @return
	 */
	public static Initializer getInstance() {
		if (instance == null) {
			synchronized (LOCKER) {
				if (instance == null) {
					instance = new Initializer();
				}
			}
		}
		return instance;
	}


}
