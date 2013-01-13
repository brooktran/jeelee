package com.test.datapool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据连接的自封装，屏蔽了close方法
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class DBConnection  implements InvocationHandler{
	
	private final static String CLOSE_METHOD_NAME="close";
	
	private Connection conn=null;
	
	//	数据库的忙状态
	private boolean isUse=false;
	
	//	用户最后一次访问该连接方法的时间
	private long lastAccessTime=System.currentTimeMillis();
	
	/**
	 * 作用:
	 * @since eclipse
	 */
	public DBConnection(Connection conn,boolean isUser) {
		this.conn=conn;
		this.isUse=isUser;
	}
	
	/**
	 * 该方法真正的关闭了数据库的连接
	 * @throws SQLException
	 */
	public void close() throws SQLException{
		conn.close();
	}
	
	/**
	 * Returns the inUse.
	 * @return boolean
	 */
	public boolean getIsUse() {
		return isUse;
	}

	
	public Connection getConnection() {
		//		返回数据库连接conn的接管类，以便截住close方法
//		返回一个指定接口的代理类实例，该接口可以将方法调用指派到指定的调用处理
        //参数分别为:代理类的类加载器,代理类要实现的接口列表,方法调用的调用处理程序
		Connection proxyConn=(Connection)Proxy.newProxyInstance(
				conn.getClass().getClassLoader(), 
				conn.getClass().getInterfaces(),
				this);
		return proxyConn;
	}
	

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object object=null;
		//		判断是否调用了close的方法，如果调用close方法则把连接置为无用状态
		if (CLOSE_METHOD_NAME.equals(method.getName())) {
			setIsUse(false);
		}else {
			object=method.invoke(conn, args);//执行method
		}
//		设置最后一次访问时间，以便及时清除超时的连接
		lastAccessTime=System.currentTimeMillis();
		return object;
	}
	
	/**
	 * Sets the inUse.
	 * @param isUse The inUse to set
	 */
	public void setIsUse(boolean isUse) {
		this.isUse = isUse;
	}
	
	/**
	 * Returns the lastAccessTime.
	 * @return long
	 */
	public long getLastAccessTime() {
		return lastAccessTime;
	}


}



















