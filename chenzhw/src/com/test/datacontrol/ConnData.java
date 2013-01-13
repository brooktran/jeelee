package com.test.datacontrol;

import java.sql.Connection;

/**
 * 每个连接的数据
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class ConnData {
	private Connection conn;// 连接
	private boolean isUse;
	
	ConnData(Connection conn, boolean isUse){
		this.conn=conn;
		this.isUse=isUse;
	}
	
	/**
	 * @return conn
	 */
	public Connection getConn() {
		return conn;
	}

	/**
	 * @param conn
	 *            要设置的 conn
	 */
	public void setConn(Connection conn) {
		this.conn = conn;
	}

	/**
	 * @return isUse
	 */
	public boolean isUse() {
		return isUse;
	}

	/**
	 * @param isUse 要设置的 isUse
	 */
	public void setUse(boolean isUse) {
		this.isUse = isUse;
	}

	

	
}