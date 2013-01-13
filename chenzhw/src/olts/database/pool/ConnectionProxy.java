package olts.database.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

/**
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-1-3 新建
 * @since OnlineTest Ver 1.0
 * 
 */
public class ConnectionProxy implements InvocationHandler{
	private final static String METHOD_NAME="close";
	private ConnectionPool pool;////////////////////
	private Connection conn;
	
	public ConnectionProxy(ConnectionPool pool) {
		this.pool=pool;
	}

	public Connection getConnection(Connection conn) {
		this.conn=conn;
		Class[] interfaces=conn.getClass().getInterfaces();
		if (null==interfaces||0==interfaces.length) {
			interfaces=new Class[1];
			interfaces[0]=Connection.class;
		}
		Connection connProxy=(Connection)Proxy.newProxyInstance(
				conn.getClass().getClassLoader(),
				interfaces,this);
		return connProxy;
	}
	/**
	 * 拦截close方法
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object object=null;
		if(METHOD_NAME.equals(method.getName())){
			pool.releaseConnection(conn);
		}
		else {
			method.invoke(conn, args);
		}
		return object;
	}
	
	
}
