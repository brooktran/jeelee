package com.test.datacontrol;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public abstract class DBConnection implements IDBConnection{
	/**
	 * @see com.test.datacontrol.IDBConnection#executeQuery(java.lang.String)
	 */
	public ResultSet executeQuery(String sql) throws SQLException {
		// TODO 自动生成方法存根
		return null;
	}

	/**
	 * @see com.test.datacontrol.IDBConnection#executeUpdate(java.lang.String)
	 */
	public void executeUpdate(String sql) throws SQLException {
		// TODO 自动生成方法存根
		
	}
}
