package com.test.xml;
import java.util.Properties;

import javax.xml.namespace.QName;
//
//import com.ddtek.xquery3.XQConnection;//
//import com.ddtek.xquery3.XQException;//
//import com.ddtek.xquery3.XQExpression;
//import com.ddtek.xquery3.XQItemType;
//import com.ddtek.xquery3.XQSequence;
//import com.ddtek.xquery3.xqj.DDXQDataSource;//

/**
 * 在所有程序中第一步都是一样的。实际上可以将处理数据源配置和连接的代码打包成一个工具类
 * 
 * 看起来很长和复杂。主要是因为该测试程序按照非常模块化的方法编写 — 使用构造函数 init() 方法 — 以便不需要很大修改就能用于您自己的程序。
 * 该程序取得文件名（从命令行获得并传递给类的构造函数），然后执行下面的代码： dataSource = new DDXQDataSource(); conn =
 * dataSource.getConnection();
 * 
 * 首先建立一个新的数据源，该对象的类型是
 * com.ddtek.xquery3.xqj.DDXQDataSource。由于没有连接到数据库，不需要其他配置，因此使用了空构造函数。然后使用数据源得到新的
 * com.ddtek.xquery3.XQConnection 对象。该对象对于建立和执行新的 XQuery
 * 表达式非常重要，虽然现在还不能执行这些功能，但是可以接受查询字符串并执行了。
 */
public class XQueryTester {
//	private String filename;// Filename for XML document to query
//
//	private DDXQDataSource dataSource;// Data Source for querying
//
//	private XQConnection conn;// Connection for querying
//
//	public XQueryTester(String filename) {
//		this.filename = filename;
//	}
//
//	public void init() throws XQException {
//		dataSource = new DDXQDataSource();
//		conn = dataSource.getConnection();
//	}
//
//	public String query(String queryString) throws XQException {
//		XQExpression expression = conn.createExpression();// 建立了一个新的表达式对象。然后，查询的文档文件名绑定到
//															// docName 变量
//		expression.bindString(new QName("docName"), filename, conn
//				.createAtomicType(XQItemType.XQBASETYPE_STRING));
//		XQSequence results = expression.executeQuery(queryString);
//		return results.getSequenceAsString(new Properties());
//	}
//
//	public static void main(String[] args) {
//		if (args.length != 1) {
//			System.err.println("Usage: java xqj.XQueryTester [XML filename]");
//			System.exit(-1);
//		}
//		try {
//			String xmlFilename = args[0];
//			XQueryTester tester = new XQueryTester(xmlFilename);
//			tester.init();
//		} catch (Exception e) {
//			e.printStackTrace(System.err);
//			System.err.println(e.getMessage());
//		}
//	}
}
