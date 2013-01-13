package com.test.datapool;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.Map;

import javax.naming.NameAlreadyBoundException;
import javax.naming.NameNotFoundException;
import javax.sql.DataSource;

/**
 * 连接池类厂，该类常用来保存多个数据源名称合数据库连接池对应的哈希
 * 通过该类来将一个连接池对象与一个名称对应起来，使用者通过该名称就可以获取指定的连接池对象
 * ConnectionFactory主要提供了用户将将连接池绑定到一个具体的名称上以及取消绑定的操作。
 * 使用者只需要关心这两个类即可使用数据库连接池的功能。
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class ConnectionFactory {
	private static Map<String, DataSource> connectionPool;
	static {
		connectionPool = new Hashtable<String, DataSource>();
	}

	/**
	 * 作用:从连接池工厂中获取指定名称对应的连接池对象
	 * 
	 * @param name
	 *            连接池对象对应的名称
	 * @return Connection 返回名称对应的连接池对象
	 * @throws NameNotFoundException
	 *             无法找到指定的连接池(name不存在在连接池中)
	 */
	public static DataSource getDataSource(String name)
			throws NameNotFoundException {
		Object object = null;
		object = connectionPool.get(name);
		if (null == object || !(object instanceof DataSource)) {
			throw new NameNotFoundException(name);//name不存在在连接池中
		}
		return (DataSource) object;
	}

	/**
	 * 作用:将指定的名字和数据库连接配置绑定在一起并初始化数据库连接池
	 * 
	 * @param name
	 *            对应连接池的名称
	 * @param connectionParam
	 *            连接池的配置参数，具体请见类ConnectionParam
	 * @return DataSource 如果绑定成功后返回连接池对象
	 * @throws NameAlreadyBoundException
	 *             如果绑定成功后返回连接池对象
	 * @throws ClassNotFoundException
	 *             无法找到连接池的配置中的驱动程序类
	 * @throws IllegalAccessError
	 *             连接池配置中的驱动程序类有误
	 * @throws InstantiationException
	 *             无法实例化驱动程序类
	 * @throws SQLException
	 *             无法正常连接指定的数据库
	 */
	public static DataSource bind(String name, ConnectionParam connectionParam)
			throws NameAlreadyBoundException, ClassNotFoundException,
			IllegalAccessError, InstantiationException, SQLException {
		DataSourceImpl source = null;
		try {
			getDataSource(name);
			//判断name是否存在在连接池中.若不存在，getDataSource()抛出NameNotFoundException
			throw new NameAlreadyBoundException(name);//若能执行到这一步，则证明getDataSource找到name
			/*
			 * This exception is thrown by methods to indicate that a binding
			 * cannot be added because the name is already bound to another
			 * object.
			 */
		} catch (NameNotFoundException e) {//若不存在,添加name
			source = new DataSourceImpl(connectionParam);//新建连接
			source.initConnection();
			connectionPool.put(name, source);//添加
		}
		return source;
	}

	/**
	 * 作用:重新绑定数据库连接池
	 * 
	 * @param name
	 *            对应连接池的名称
	 * @param connectionParam
	 *            连接池的配置参数，具体请见类ConnectionParam
	 * @return DataSource 如果绑定成功后返回连接池对象
	 * @throws NameAlreadyBoundException
	 *             名字name已经绑定则抛出该异常
	 * @throws ClassNotFoundException
	 *             法找到连接池的配置中的驱动程序类
	 * @throws IllegalAccessError
	 *             连接池配置中的驱动程序类有误
	 * @throws InstantiationException
	 *             无法实例化驱动程序类
	 * @throws SQLException
	 *             无法正常连接指定的数据库
	 */
	public static DataSource rebind(String name, ConnectionParam connectionParam)
			throws NameAlreadyBoundException, ClassNotFoundException,
			IllegalAccessError, InstantiationException, SQLException {
		try {
			deleteBind(name);
		} catch (Exception e) {
		}
		return bind(name, connectionParam);
	}

	/**
	 * 作用:删除一个数据库连接池对象
	 * @param name
	 * @throws NameNotFoundException
	 */
	public static void deleteBind(String name) throws NameNotFoundException {
		DataSource dataSource = getDataSource(name);
		if (dataSource instanceof DataSourceImpl) {
			DataSourceImpl dSourceImpl = (DataSourceImpl) dataSource;
			try{
				dSourceImpl.stop();
				dSourceImpl.close();
			}catch (Exception e) {
			}
			finally{
				dSourceImpl=null;
			}
			connectionPool.remove(name);
		}

	}

}
