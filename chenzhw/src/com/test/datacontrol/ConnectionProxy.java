package com.test.datacontrol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 绑定Connection的实例,并截获其实例的方法
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class ConnectionProxy implements InvocationHandler{
	private ConnData connData=null;
	private ConnectionPool pool;//引用连接池
	
	/**
	 * 作用:
	 * @since eclipse
	 */
	public ConnectionProxy(Connection conn,ConnectionPool pool) {
		this.connData=new ConnData(conn,true);
		this.pool=pool;
	}
	
	/**
	 * 该方法真正的关闭了数据库的连接
	 */
/*	public void close(){
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}*/
	
	/**
	 * 作用:返回数据库连接conn的接管类，以便截住close方法
	 * 
	 * @return connProxy 连接的代理
	 */
	public Connection getConnection() {
		Class[] interfaces=connData.getConn().getClass().getInterfaces();
		if(null==interfaces||0==interfaces.length){
			interfaces=new Class[1];
			interfaces[0]=Connection.class;
		}
		Connection connProxy=(Connection)Proxy.newProxyInstance(
				connData.getConn().getClass().getClassLoader(), 
				interfaces, this);
		return connProxy;
	}

	/**
	 * 方法调用拦截
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object object=null;
		if("close".equals(method.getName())){
			connData.setUse(false);
		}else {
			object=method.invoke(connData.getConn(), args);
		}
		return object;
	}
	
	public static void main(String[] args) {
		
	}
}




















