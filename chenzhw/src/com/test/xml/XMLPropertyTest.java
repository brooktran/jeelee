package com.test.xml;

import junit.framework.TestCase;
import junit.textui.TestRunner;


/**
 * <B>XMLPropertyTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0.01 2009-4-17 created
 * @since chenzhw Ver 1.0
 * 
 */
public class XMLPropertyTest extends TestCase{
	
	public void testReader() throws Exception {
		XMLProperties xmlProperties=new XMLProperties("G:/test/java/chenzhw/src/com/test/xml/note.xml");//不带命名空间
		String value=xmlProperties.getProperty("to");// 除去根节点的Xpath路径
		System.out.println(value);
	}
	
	public static void main(String[] args) {
		TestRunner.run(new XMLPropertyTest());
	}
}
