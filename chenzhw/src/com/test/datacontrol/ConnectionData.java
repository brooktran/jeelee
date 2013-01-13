package com.test.datacontrol;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class ConnectionData {
	
	private List<ConnData> conns=new ArrayList<ConnData>();
	
	private ConnectionParam param;// 配置参数
	
	/**
	 * 作用:
	 * 
	 * @since eclipse
	 */
	public ConnectionData(ConnectionParam param) {
		this.param=param;
	}
	
	public void init() {
		try{
			Class.forName(param.getDriver());
			Connection conn=DriverManager.getConnection(param.getSource());// 不适合SQL，可重写该函数
			ConnData cData=new ConnData(conn,true);
			synchronized (conns) {
				conns.add(cData);
			}
		}catch (SQLException e) {
			e.printStackTrace();
			// System.err.println("数据库不存在");
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
			// System.err.println("找不到驱动程序");
		}
	}
	
	public synchronized Connection getFreeConnection(long timeout) {
		Connection conn=null;
		Iterator<ConnData> it=conns.iterator();
		while (it.hasNext()) {
			ConnData cData=(ConnData)it.next();
			if(!cData.isUse()){
				conn=cData.getConn();// 返回一个代理Connection
				// 代理实例类(用来代理Connection接口的实例),并且把池的引用传过去,好控制池中的连接对象
				//ConnectionProxy connProxy=new ConnectionProxy(conn);
				cData.setUse(true);
				//conn=connProxy.getConnection();
				break;
			}
		}
		if(null==conn&&timeout>0){
			// 等待nTimeout毫秒以便看是否有空闲连接
			try{
				Thread.sleep(timeout);
				timeout=0;
				conn=getFreeConnection(timeout);
			}catch (Exception e) {
			}
		}
		return conn;
	}
	
	public Connection getConnection() {
		Connection conn=getFreeConnection(param.getWaitTime());
		if(null==conn){
			if(getConnnectionCount()<param.getMaxSize()){// 尚有连接可用
				try{
					Class.forName(param.getDriver());
					conn=DriverManager.getConnection(param.getSource());// 不适合SQL，可重写该函数
					synchronized (conns) {
						conns.add(new ConnData(conn,true));
					}
					//ConnectionProxy connProxy=new ConnectionProxy(conn);
					//conn=connProxy.getConnection();// 返回代理
				}catch (SQLException e) {
					e.printStackTrace();
					// System.err.println("数据库不存在");
				}catch (ClassNotFoundException e) {
					e.printStackTrace();
					// System.err.println("找不到驱动程序");
				}
			}
		}
		return conn;
	}
	
	/**
	 * 作用:返回已有连接数
	 * 
	 * @return int
	 */
	public int  getConnnectionCount() {
		return conns.size();
	}
	
	/**
	 * 作用:关闭所有连接
	 * 
	 */
	public void close() {
		Connection conn=null;
		Iterator<ConnData> it=conns.iterator();
		while (it.hasNext()) {
			try{
				((ConnData)it.next()).getConn().close();
			}catch (SQLException e) {
				e.printStackTrace();
				// System.err.println("数据库不存在");
			}
		}
	}
	
	public boolean isEmpty(){
		return conns.size()<param.getMaxSize();
	}
}





















