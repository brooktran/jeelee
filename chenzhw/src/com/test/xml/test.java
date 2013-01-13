package com.test.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//
///**
// * 
// * @author Brook Tran. Email: brook.tran.c@gmail.com
// * @version Ver 1.0 2008-11-16 新建
// * @since xml Ver 1.0
// * 
// */




public class test {
}
//	public static void main(String[] args) {
//		try {
//			 String xmlFilename = "authorList.xml";
//			 XQueryTester tester = new XQueryTester(xmlFilename);
//			 tester.init();
//
//			 final String sep = System.getProperty("line.separator");
//			 String queryString =
//				 "declare variable $docName as xs:string external;" + sep +
//			 " <results> "+
//			 " {"+
//			 " for $a in doc($docName)/authorList/author " +
//			 " where count($a/book)>3 "+
//			 " return " +
//			 " <author>{$a/@name}</author>" +
//			 " } "+
//			 " </results> ";
//			 System.out.println(tester.query(queryString));
//			 } catch (Exception e) {
//			 e.printStackTrace(System.err);
//			 System.err.println(e.getMessage());
//			 }
//	}
//
//}
