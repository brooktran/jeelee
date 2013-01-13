package com.test.database.pool;

import java.sql.Connection;
import java.util.Enumeration;
import java.util.Properties;

/**
 * <B>DBConnectionDefaultPool</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0.01 2009-4-10 created
 * @since chenzhw Ver 1.0
 * 
 */
public class DBConnectionDefaultPool extends DBConnectionProvider{

	private ConnectionPool connectionPool=null;
	private Properties props;
	private Properties propDescriptions;
	
	private Object initLock=new Object();
	
	public DBConnectionDefaultPool(){
		props=new Properties();
		propDescriptions=new Properties();
		//Initialize all property values
        initializeProperties();
        //Load any existing property values
        loadProperties();
        
	}
	
	/**
	 * Returns a database connection.
	 * @return the connection
	 */
	public Connection getConnection(){
		if (connectionPool==null) {
			// Block until the init has been done.
			synchronized (initLock) {// waitting for init.
				if (connectionPool==null) {
					//Something has gone wrong.
					return null;
				}
			}
		}
		return new ConnectionProxy(connectionPool.getConnection(),connectionPool);
	}
	
	/**
     * Returns the value of a property of the connection provider.
     *
     * @param name the name of the property.
     * @returns the value of the property.
     */
    public String getProperty(String name) {
        return (String)props.get(name);
    }

    /**
     * Returns the description of a property of the connection provider.
     *
     * @param name the name of the property.
     * @return the description of the property.
     */
    public String getPropertyDescription(String name) {
        return (String)propDescriptions.get(name);
    }

    /**
     * Returns an enumeration of the property names for the connection provider.
     */
    public Enumeration<?> propertyNames() {
        return props.propertyNames();
    }

    /**
     * Sets a property of the connection provider. Each provider has a set number
     * of properties that are determined by the author. Trying to set a non-
     * existant property will result in an IllegalArgumentException.
     *
     * @param name the name of the property to set.
     * @param value the new value for the property.
     *
     */
    public void setProperty(String name, String value) {
        props.put(name, value);
        saveProperties();
    }

    /**
     * Give default values to all the properties and descriptions.
     */
    private void initializeProperties() {
        props.put("driver","");
        props.put("server","");
        props.put("username","");
        props.put("password","");
        props.put("minConnections","");
        props.put("maxConnections","");
        props.put("logPath","");
        props.put("connectionTimeout","");

        propDescriptions.put("driver","JDBC driver. e.g. 'oracle.jdbc.driver.OracleDriver'");
        propDescriptions.put("server","JDBC connect string. e.g. 'jdbc:oracle:thin:@203.92.21.109:1526:orcl'");
        propDescriptions.put("username","Database username. e.g. 'Scott'");
        propDescriptions.put("password","Database password. e.g. 'Tiger'");
        propDescriptions.put("minConnections","Minimum # of connections to start with in pool. Three is the recommended minimum");
        propDescriptions.put("maxConnections","Maximum # of connections in dynamic pool. Fifteen should give good performance for an average load.");
        propDescriptions.put("logPath","Absolute path name for log file. e.g. 'c:\\logs\\jiveDbLog.log'");
        propDescriptions.put("connectionTimeout","Time in days between connection resets. e.g. '.5'");
    }

    /**
     * Load whatever properties that already exist.
     */
    private void loadProperties() {
        String driver = PropertyManager.getProperty("DbConnectionDefaultPool.driver");
        String server = PropertyManager.getProperty("DbConnectionDefaultPool.server");
        String username = PropertyManager.getProperty("DbConnectionDefaultPool.username");
        String password = PropertyManager.getProperty("DbConnectionDefaultPool.password");
        String minConnections = PropertyManager.getProperty("DbConnectionDefaultPool.minConnections");
        String maxConnections = PropertyManager.getProperty("DbConnectionDefaultPool.maxConnections");
        String logPath = PropertyManager.getProperty("DbConnectionDefaultPool.logPath");
        String connectionTimeout = PropertyManager.getProperty("DbConnectionDefaultPool.connectionTimeout");

        if (driver != null) {  props.setProperty("driver", driver);  }
        if (server != null) {  props.setProperty("server", server);  }
        if (username != null) {  props.setProperty("username", username);  }
        if (password != null) {  props.setProperty("password", password);  }
        if (minConnections != null) {  props.setProperty("minConnections", minConnections);  }
        if (maxConnections != null) {  props.setProperty("maxConnections", maxConnections);  }
        if (logPath != null) {  props.setProperty("logPath", logPath);  }
        if (connectionTimeout != null) {  props.setProperty("connectionTimeout", connectionTimeout);  }
    }

    private void saveProperties() {
        PropertyManager.setProperty("DbConnectionDefaultPool.driver", props.getProperty("driver"));
        PropertyManager.setProperty("DbConnectionDefaultPool.server", props.getProperty("server"));
        PropertyManager.setProperty("DbConnectionDefaultPool.username", props.getProperty("username"));
        PropertyManager.setProperty("DbConnectionDefaultPool.password", props.getProperty("password"));
        PropertyManager.setProperty("DbConnectionDefaultPool.minConnections", props.getProperty("minConnections"));
        PropertyManager.setProperty("DbConnectionDefaultPool.maxConnections", props.getProperty("maxConnections"));
        PropertyManager.setProperty("DbConnectionDefaultPool.logPath", props.getProperty("logPath"));
        PropertyManager.setProperty("DbConnectionDefaultPool.connectionTimeout", props.getProperty("connectionTimeout"));
    }
}
