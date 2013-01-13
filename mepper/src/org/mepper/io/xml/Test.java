/* Test.java 1.0 2010-2-2
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
package org.mepper.io.xml;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

/**
 * <B>Test</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-23 created
 * @since mepper Ver 1.0
 * 
 */
public class Test {

	
	public static void main(String[] args) throws IOException, Exception {
		
		XMLStreamWriter writer = XMLOutputFactory.newInstance().
				createXMLStreamWriter(new FileOutputStream("test0000.xml"));
		writer.writeStartDocument();

		writer.writeStartElement("xixixixixi");
		writer.writeCharacters("adfadfadfadfasdfadf");
		
		
		writer.writeEndDocument();
		
		
		
		
		
		
		
		
		
	}

	private static void testdata() {
		int[][] array = new int[][]{
				{32,1,2,1,2,1,26,12,56}
		};
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for(int i=0;i<array.length;i++){
			for(int j=0;j<array[0].length;j++){
				out.write(array[i][j]);
			}
		}
		
		byte[] data =out.toByteArray();
		String string = new String(data);
		System.out.println('A'-'0');
		
		byte[] newData = string.getBytes();
		System.out.println(string.getBytes());
	}
}
