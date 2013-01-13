/* XMLBuilderTest.java 1.0 2010-2-2
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
package org.zhiwu.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.zhiwu.utils.AppLogging;

/**
 * <B>XMLBuilderTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.zhiwu.xml Ver 1.0
 * 
 */
public class XMLBuilderTest extends TestCase {
	
	
	public void testBuilder() throws Exception {
		try {
			XMLStreamBuilder builder = new XMLStreamBuilder();
			builder.build("test.xml");
			
			while(builder.hasNextTag()){
				System.out.println("type: "+builder.getEventType());
				System.out.println("location: "+builder.getLocation().getLineNumber());
				int event = builder.getEventType();
				
				if(event == XMLStreamBuilder.START_ELEMENT ){
					System.out.print("element");
					System.out.println("\t"+builder.getName()+"\t"+(builder.hasText()?builder.getElementText():""));
					for(int i=0,j= builder.getAttributeCount();i<j;i++){
						System.out.print("\t"+builder.getAttributeName(i));
						System.out.print("\t"+builder.getAttributeValue(i));
						System.out.println("\t"+builder.getAttributeType(i));
					}
				}else if(event == XMLStreamBuilder.CHARACTERS){
					System.out.print("text:" + (builder.hasText()?builder.getElementText():""));
				}
				
				
				System.out.println();
				event = builder.nextTag();
			}
			builder.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	private InputStream getXMLFile(){
		try {
			return new FileInputStream(("test.xml"));
		} catch (FileNotFoundException e) {
			AppLogging.handleException(e);
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		TestRunner.run(XMLBuilderTest.class);
	}

}
