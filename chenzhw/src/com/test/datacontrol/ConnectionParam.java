package com.test.datacontrol;

/**
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class ConnectionParam {
	/**
	 * 数据库驱动程序
	 */
	private String driver;
	/**
	 * 数据连接的资源
	 */
	private String source;

	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 密码
	 */
	private String password;
	
	private int maxSize=10;
	
	/**
	 * 连接的最大空闲时间
	 */
	private int timeOut=6000;
	
	/**
	 * 等待连接的时间
	 */
	private long waitTime=3000;

	/**
	 * @return driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * 作用:
	 * @since eclipse
	 */
	public ConnectionParam(String driver,String source) {
		this.driver=driver;
		this.source=source;
		
	}
	/**
	 * @param driver 要设置的 driver
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password 要设置的 password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source 要设置的 source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return timeOut
	 */
	public int getTimeOut() {
		return timeOut;
	}

	/**
	 * @param timeOut 要设置的 timeOut
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
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

	/**
	 * @return maxSize
	 */
	public int getMaxSize() {
		return maxSize;
	}

	/**
	 * @param maxSize 要设置的 maxSize
	 */
	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
