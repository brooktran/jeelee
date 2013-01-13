package com.test.datacontrol;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.xml.bind.Binder;

/**
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class ConnectionPool {
	private static Map<String, ConnectionData> connPool;// 生命周期直至程序结束
	static {
		connPool = new HashMap<String, ConnectionData>();
	}

	/**
	 * 作用:从连接池中获取指定名称对应的连接池对象
	 * 
	 * @param name 连接池对象对应的名称
	 * @return Connection 返回名称对应的连接池对象
	 * @throws NameNotFoundException
	 *             无法找到指定的连接池对象(name不存在在连接池中)
	 */
	public static Connection getConnection(String name)
			throws NameNotFoundException {
		if (null == name || 0 == name.trim().length()) {
			throw new NameNotFoundException(name);
		}
		ConnectionData conn = null;
		conn = (ConnectionData) connPool.get(name);
		if (null == conn || !(conn instanceof Connection)) {
			throw new NameNotFoundException(name);// name不存在在连接池中
		}
		//返回代理
		return conn.getConnection();
	}

	public static void releaseConnection(Connection conn) {
	}
	/**
	 * 作用:绑定数据库连接
	 * 
	 * @param name
	 *            对应连接池对象的名称
	 * @param param
	 *            连接池对象的配置参数
	 * @throws NameAlreadyBoundException
	 *             如果绑定成功后返回连接池对象
	 * @return DataSource 如果绑定成功后返回连接池对象
	 */
	public static ConnectionData bind(String name, ConnectionParam param)
			throws NameAlreadyBoundException {
		ConnectionData conn = null;
		try {
			getConnection(name);
			throw new NameAlreadyBoundException(name);// 若能执行到这一步，则证明getDataSource找到name
		} catch (NameNotFoundException e) {// 若不存在,添加name
			conn = new ConnectionData(param);// 新建连接
			conn.init();
			connPool.put(name, conn);
		}
		return conn;
	}

	/**
	 * 作用:删除一个数据库连接池对象
	 * 
	 * @param name
	 * @throws NameNotFoundException
	 */
	public static void deleteBind(String name) throws NameNotFoundException {
		ConnectionData conn = (ConnectionData) getConnection(name);
		try{
			conn.close();
		}
		finally{
			conn=null;
		}
		connPool.remove(name);
	}

}
