package com.test.datacontrol;

import java.beans.Statement;
import java.sql.PreparedStatement;


/**
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class DBAccessConnection extends DBConnection{
	
	private final static String driver="sun.jdbc.odbc.JdbcOdbcDriver";
	private final static String source="jdbc:odbc:olts";
	private static String tableName="item";
	private Statement statement;
	private PreparedStatement preparedStatement;
	private ConnectionParam connectionParam;
	
	/**
	 * 作用:
	 * @since eclipse
	 */
	public DBAccessConnection() {
		connectionParam=new ConnectionParam(driver,source);
		connectionParam.setTimeOut(300000);
	}
	public static void main(String[] args) {
		DBAccessConnection accessConn=new DBAccessConnection();
	}
}
