/* @(#)BinaryFile4SWF.java 1.0 2009-11-18
 * 
 * Copyright (c) 2009 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package com.chenzhw.IOFileControl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Formatter;

/**
 * <B>BinaryFile4SWF</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2009-11-18 created
 * @since chenzhw Ver 1.0
 * 
 */
public class BinaryFile4SWF {
	private static final String filePath="bin/com/chenzhw/IOFileControl/test.swf";

	/**
	 * 
	 * @since chenzhw
	 */
	public BinaryFile4SWF() {
		read(filePath);
	}


	private void read(String fileName){
		File f;
		try {
			f=new File(fileName);
			if (!f.exists()&&!f.canRead()) {
				System.err.println(f.getAbsolutePath());
				System.err.println("Can't not read the file or the file does not exist");
			}

			long length=f.length();
			int ch=0;
			System.out.println("file length:  "+length);

			byte[] buffer=new byte[16];
			InputStream is=new FileInputStream(f);

			int isPrintln=0;
			StringBuffer sb =new StringBuffer();
			Formatter formatter=new Formatter(sb);

			//			DecimalFormat df=new DecimalFormat ("00");
			//			df.setGroupingUsed(false);
			while((ch=is.read())!=-1){
				//				System.out.println("...   "+formatter.format("%02x ", ch).toString());
				formatter.format("%02x ", ch);
				formatter.out();
				System.out.println(sb.toString());
				//	System.out.printf("%02x ",ch);
				if((++isPrintln)%16 == 0){
					System.exit(0);
					System.out.println();
				}

			}
			formatter.close();

			//			while((ch=is.read(buffer))!=-1){
			//
			//
			//				for (byte element : buffer) {
			//					System.out.print(Integer.toHexString(element)+" ");
			//				}
			//				System.out.println();
			//			}

			is.close();



		} catch (Exception e) {
			e
			.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new BinaryFile4SWF();
	}
}
