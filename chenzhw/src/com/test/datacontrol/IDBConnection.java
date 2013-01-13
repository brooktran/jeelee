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
public interface IDBConnection {
	
	/**
	 * 作用:执行查询
	 * @param sql	查询语句
	 * @return ResultSet 查询结果
	 * @throws SQLException
	 */
	ResultSet executeQuery(String sql)throws SQLException;

	/**
	 * 作用:更新数据库操作
	 * @param sql 查询语句
	 * @throws SQLException
	 */
	void executeUpdate(String sql)throws SQLException;
}
