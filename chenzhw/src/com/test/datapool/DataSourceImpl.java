package com.test.datapool;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.sql.DataSource;

/**
 * DataSourceImpl是一个实现了接口javax.sql.DataSource的类，
 * 该类维护着一个连接池的对象。由于该类是一个受保护的类，
 * 因此它暴露给使用者的方法只有接口DataSource中定义的方法，
 * 其他的所有方法对使用者来说都是不可视的。
 * 我们先来关心用户可访问的一个方法getConnection
 * 
 * DataSourceImpl类中实现getConnection方法的跟正常的数据库连接池的逻辑是一致的，
 * 首先判断是否有空闲的连接，如果没有的话判断连接数是否已经超过最大连接数等等的一些逻辑。
 * 但是有一点不同的是通过DriverManager得到的数据库连接并不是及时返回的，
 * 而是通过一个叫_Connection的类中介一下，然后调用_Connection.getConnection返回的。
 * 如果我们没有通过一个中介也就是JAVA中的Proxy来接管要返回的接口对象，
 * 那么我们就没有办法截住Connection.close方法。
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class DataSourceImpl implements DataSource{
	
	private List<DBConnection> conns=new ArrayList<DBConnection>();
	
	private ConnectionParam param;
	
	public DataSourceImpl(ConnectionParam connectionParam){
		
	}
	
	public void initConnection() {
		
	}
	
	public void stop() {
		
	}
	
	/**
	 * 关闭该连接池中的所有数据库连接
	 * @return int 返回被关闭连接的个数
	 * @throws SQLException
	 */
	public int close() throws SQLException
	{
		int cc = 0;
		SQLException excp = null;
		Iterator iter = conns.iterator();
		while(iter.hasNext()){
			try{
				((DBConnection)iter.next()).close();
				cc ++;
			}catch(Exception e){
				if(e instanceof SQLException)
					excp = (SQLException)e;
			}
		}
		if(excp != null)
			throw excp;
		return cc;
	}

	
	public int getConnectionCount() {
		return conns.size();
	}
	protected synchronized Connection getFreeConnection(long timeOut)
									throws SQLException{
		Connection conn=null;
		Iterator<DBConnection> it=conns.iterator();
		while (it.hasNext()) {
			DBConnection _conn=(DBConnection)it.next();
			if(!_conn.getIsUse()){
				conn=_conn.getConnection();//返回一个代理Connection
				_conn.setIsUse(true);
				break;
			}
		}
		if(conn==null&&timeOut>0){
//			等待nTimeout毫秒以便看是否有空闲连接
			try {
				Thread.sleep(timeOut);
			} catch (Exception e) {
			}
			conn=getFreeConnection(0);
			if(conn==null){
				throw new SQLException("没有可用数据库连接");
			}
		}
		return conn;
	}

	/** 
	 * @see javax.sql.DataSource#getConnection(java.lang.String, java.lang.String)
	 */
	public Connection getConnection(String username, String password) throws SQLException {
//		首先从连接池中找出空闲的对象
		Connection connection=getFreeConnection(0);
		if (connection==null) {
//			判断是否超过最大连接数,如果超过最大连接数
			//则等待一定时间查看是否有空闲连接,否则抛出异常告诉用户无可用连接
			if(getConnectionCount()>param.getMaxConnection()){
				connection=getFreeConnection(param.getWaitTime());
			}else{//没有超过连接数，重新获取一个数据库的连接
				param.setUsername(username);
				param.setPassword(password);
				Connection _conn=DriverManager.getConnection(param.getUrl(),
						username,password);
				DBConnection dbconn=new DBConnection(_conn,true);
				synchronized (conns) {
					conns.add(dbconn);
				}
				connection=dbconn.getConnection();
			}
		}
		return connection;
	}

	/* （非 Javadoc）
	 * @see javax.sql.CommonDataSource#getLogWriter()
	 */
	public PrintWriter getLogWriter() throws SQLException {
		// TODO 自动生成方法存根
		return null;
	}

	/* （非 Javadoc）
	 * @see javax.sql.CommonDataSource#getLoginTimeout()
	 */
	public int getLoginTimeout() throws SQLException {
		// TODO 自动生成方法存根
		return 0;
	}

	/* （非 Javadoc）
	 * @see javax.sql.CommonDataSource#setLogWriter(java.io.PrintWriter)
	 */
	public void setLogWriter(PrintWriter out) throws SQLException {
		// TODO 自动生成方法存根
		
	}

	/* （非 Javadoc）
	 * @see javax.sql.CommonDataSource#setLoginTimeout(int)
	 */
	public void setLoginTimeout(int seconds) throws SQLException {
		// TODO 自动生成方法存根
		
	}

	/* （非 Javadoc）
	 * @see java.sql.Wrapper#isWrapperFor(java.lang.Class)
	 */
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO 自动生成方法存根
		return false;
	}

	/* （非 Javadoc）
	 * @see java.sql.Wrapper#unwrap(java.lang.Class)
	 */
	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO 自动生成方法存根
		return null;
	}

	/**
	 * @see javax.sql.DataSource#getConnection()
	 */
	public Connection getConnection() throws SQLException {
		// TODO 自动生成方法存根
		return null;
	}
	
}
