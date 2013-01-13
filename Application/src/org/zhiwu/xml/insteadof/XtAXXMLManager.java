/* XtAXXMLManager.java 1.0 2010-2-2
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
package org.zhiwu.xml.insteadof;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.zhiwu.utils.AppLogging;

/**
 * <B>XtAXXMLManager</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.zhiwu.xml.insteadof Ver 1.0
 * 
 */
public class XtAXXMLManager{
	public void read(String url){
		XMLInputFactory factory = XMLInputFactory.newInstance();
		try {
			XMLStreamReader parser= factory.createXMLStreamReader(new FileInputStream(url));
			
			while(parser.hasNext()){
				int event = parser.next();
				if(event == XMLStreamConstants.START_ELEMENT){
					System.out.println(parser.getName());
					
					for(int i=0,j= parser.getAttributeCount();i<j;i++){
						System.out.println(parser.getAttributeName(i));
						System.out.println(parser.getAttributeValue(i));
						System.out.println(parser.getAttributeType(i));
					}
				}
				System.out.println();
			}
			
		} catch (FileNotFoundException e) {
			AppLogging.handleException(e);
		} catch (XMLStreamException e) {
			AppLogging.handleException(e);
		}
	}
	
	
	
	
	
	public static void main(String[] args) {
		String path ="build.xml";
		XtAXXMLManager manager = new XtAXXMLManager();
		manager.read(path);
		
		
	}

}
