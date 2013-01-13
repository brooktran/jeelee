package com.test.opensource.csvparser;

import junit.framework.TestCase;

/**
 * 类<B> TestCsvParser </B>是
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-1-31 新建
 * @since eclipse Ver 1.0
 * 
 */
public class TestCsvParser extends TestCase{
	public void testParseSimpleLine() {
		CsvParser parser=new CsvParser();
		parser.parse("Bill,Gates,555-1234");
		
		assertEquals("Bill",parser.get(0));
		assertEquals("Gates", parser.get(1));
		assertEquals("555-1234", parser.get(2));
	}
}
