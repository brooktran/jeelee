package com.test.datapool;

import java.io.Serializable;

/**
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class ConnectionParam implements Serializable{

	private static final long serialVersionUID = 7707606053463491133L;//版本标识
	
	private String driver;//数据库驱动程序

	private String url;//数据连接的URL
	
	private String Password; //数据库密码

	private String username;//数据库用户名
	
	private int initConnection=0;//初始化连接数

	private int maxConnection=50;//最大连接数

	private long maxVacantTime=600000;//连接的最大空闲时间,10min
	
	private long waitTime=30000;////取连接的时候如果没有可用连接最大的等待时间,30s

	/**
	 * @return serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @return driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * @param driver 要设置的 driver
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * @return initConnection
	 */
	public int getInitConnection() {
		return initConnection;
	}

	/**
	 * @param initConnection 要设置的 initConnection
	 */
	public void setInitConnection(int initConnection) {
		this.initConnection = initConnection;
	}

	/**
	 * @return maxConnection
	 */
	public int getMaxConnection() {
		return maxConnection;
	}

	/**
	 * @param maxConnection 要设置的 maxConnection
	 */
	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}

	/**
	 * @return maxVacantTime
	 */
	public long getMaxVacantTime() {
		return maxVacantTime;
	}

	/**
	 * @param maxVacantTime 要设置的 maxVacantTime
	 */
	public void setMaxVacantTime(long maxVacantTime) {
		this.maxVacantTime = maxVacantTime;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return Password;
	}

	/**
	 * @param password 要设置的 password
	 */
	public void setPassword(String password) {
		Password = password;
	}

	/**
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url 要设置的 url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username 要设置的 username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return waitTime
	 */
	public long getWaitTime() {
		return waitTime;
	}

	/**
	 * @param waitTime 要设置的 waitTime
	 */
	public void setWaitTime(long waitTime) {
		this.waitTime = waitTime;
	}
}
