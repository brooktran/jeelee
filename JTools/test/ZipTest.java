/* ZipTest.java 1.0 2010-2-2
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


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.jdom.Element;
import org.zhiwu.xml.DefaultXMLManagerImpl;
import org.zhiwu.xml.XMLManager;

/**
 * <B>ZipTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-4-25 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public class ZipTest {

	public static final String SS_STRING = "我是撒旦发发得分热管梵蒂冈地方的发送方阿斯发达算法";
	public static final String EIGHT_STRING = "000000坤为地 100000山地剥 010000水地比  110000风地观 "
			+ "001000雷地豫  101000火地晋 011000泽地萃  111000天地否 "
			+ "000100地山谦  100100艮为山  010100水山蹇 110100风山渐 "
			+ "001100雷山小过 101100火山旅 011100泽山咸 111100天山遯 "
			+ "000010地水师 100010山水蒙   010010坎为水 110010风水涣 "
			+ "001010雷水解 101010火水未既 011010泽水困 111010天水讼 "
			+ "000110地风升 100110山风蛊  010110水风井 110110巽为风 "
			+ "001110雷风恒 101110火风鼎  011110泽风大过 111110天风姤 "
			+ "000001地雷复 100001山雷颐  010001水雷屯 110001风雷益 "
			+ "001001震为雷 101001火雷噬嗑 011001泽雷随 111001天雷无妄 "
			+ "000101地火明夷 100101山火贲 010101水火即济 110101风火家人 "
			+ " 001101雷火丰 101101离为火  011101泽火革 111101天火同人 "
			+ "000011地泽临 100011山泽损 010011水泽节 110011风泽中孚 001011雷泽随 "
			+ "101011火泽睽 011011兑为泽 111011天泽屦 000111地天泰 "
			+ "100111山天大畜 010111水天需 110111风天小畜 001111雷天大壮 "
			+ "101111火天大有 011111泽天夬 111111乾为天";

	public static void main(String[] args) throws IOException, Exception {
		testZipFile();
		
		testDeflater();
	}

	/**
	 * 
	 */
	private static void testDeflater() throws Exception {
		String testString="101011火泽睽".substring(6);
		byte[] data=testString.getBytes();
		
		Deflater deflater=new Deflater(Deflater.BEST_COMPRESSION);
		deflater.reset();
		deflater.setInput(data);
		deflater.finish();
		
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		byte[] buf=new byte[2048];
		while(!deflater.finished()){
			int p=deflater.deflate(buf);
			baos.write(buf,0,p);
		}
		
		
		byte[] newData=baos.toByteArray();
		System.out.println(new String(newData));
		baos.reset();
		
		Inflater inflater=new Inflater();
		inflater.reset();
		inflater.setInput(newData);
		inflater.finished();
		
		while(!inflater.finished()){
			int p=inflater.inflate(buf);
			baos.write(buf, 0, p);
		}
		
		data=baos.toByteArray();
		String string=new String(data);
		System.out.println(new String(data));
	}

	/**
	 * 
	 */
	private static void testZipFile() throws Exception {
		
		byte[] buf=new byte[20480];
		XMLManager manager = new DefaultXMLManagerImpl(new BufferedInputStream(new FileInputStream("resources/info/temple.xml")));

		StringTokenizer t = new StringTokenizer(EIGHT_STRING);
		Element root = manager.getRootElement();
		while (t.hasMoreTokens()) {
			String string = t.nextToken();
			Element e = new Element("Diagrams");
			e.setAttribute("id", string.substring(0, 6));
			e.setAttribute("name", string.substring(6));
			root.addContent(e);
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		manager.saveToStream(baos);

		byte[] data = baos.toByteArray();
		byte[] output = new byte[0];
		Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);
		compresser.reset();
		compresser.setInput(data);
		compresser.finish();

		baos = new ByteArrayOutputStream(data.length);
		try {
			buf = new byte[2048];
			while (!compresser.finished()) {
				int i = compresser.deflate(buf);
				baos.write(buf, 0, i);
			}
			output = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			output = data;
		} finally {
			try {
				baos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		compresser.end();

		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
				new FileOutputStream(new File("test.zip"))));

		ZipEntry entry = new ZipEntry("0001.xml");
		out.putNextEntry(entry); 
		out.write(output);

		out.flush();
		out.close();
		
		
		
		
		ZipFile file = new ZipFile("test.zip");
		ZipEntry e = file.getEntry("0001.xml");
		InputStream is = file.getInputStream(e);

		baos .reset();
		buf = new byte[2048];
		int c = -1;
		while ((c = is.read(buf)) != -1) {
			baos.write(buf, 0, c);
		}

		data = baos.toByteArray();

		baos.reset();

		Inflater inflater = new Inflater();
		inflater.reset();
		inflater.setInput(data);
		inflater.finished();

		while (!inflater.finished()) {
			c = inflater.inflate(buf);
			baos.write(buf, 0, c);
		}

		is = new ByteArrayInputStream(baos.toByteArray());
		manager=new DefaultXMLManagerImpl(is);
		root=manager.getRootElement();
		System.out.println(root);
		List<Element> l=root.getChildren();
		for (Iterator iterator = l.iterator(); iterator.hasNext();) {
			Element element = (Element) iterator.next();
			System.out.println(element.getAttributeValue("name"));
		}
		
		
		BufferedReader r = new BufferedReader(new InputStreamReader(is));

		String s;
		while ((s = r.readLine()) != null) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0, j = s.length(); i < j; i++) {
				char ch = s.charAt(i);
				if (ch >= 0 && ch <= 255) {
					sb.append(ch);
				} else {
					byte[] b = null;
					try {
						b = Character.toString(ch).getBytes("utf-8");
					} catch (Exception e2) {
						e2.printStackTrace();
					}

					for (j = 0; j < b.length; j++) {
						int k = b[j];
						if (k < 0)
							k += 256;
						sb.append("%" + Integer.toHexString(k).toUpperCase());
					}
				}
			}

//			System.out.println(sb.toString());
		}		
	}

	/**
	 * 
	 */
	private static void addXMLToZip() {
		try {
			XMLManager manager = new DefaultXMLManagerImpl(
					"resources/info/EightDiagrams.xml", true);

			StringTokenizer t = new StringTokenizer(EIGHT_STRING);
			Element root = manager.getRootElement();
			while (t.hasMoreTokens()) {
				String string = t.nextToken();
				Element e = new Element("Diagrams");
				e.setAttribute("id", string.substring(0, 6));
				e.setAttribute("name", string.substring(6));
				System.out.println(string.substring(6));
				root.addContent(e);
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			manager.saveToStream(baos);

			byte[] data = baos.toByteArray();
			byte[] output = new byte[0];
			Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);
			compresser.reset();
			compresser.setInput(data);
			compresser.finish();

			ByteArrayOutputStream baos2 = new ByteArrayOutputStream(data.length);
			try {
				byte[] buf = new byte[2048];
				while (!compresser.finished()) {
					int i = compresser.deflate(buf);
					baos2.write(buf, 0, i);
				}
				output = baos2.toByteArray();
			} catch (Exception e) {
				e.printStackTrace();
				output = data;
			} finally {
				try {
					baos2.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

			compresser.end();

			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(
					new FileOutputStream(new File("test.zip"))));

			ZipEntry entry = new ZipEntry("0000.xml");
			out.putNextEntry(entry);

			out.write(output);

			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
