package com.test.database.pool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-4-10 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class ConnectionPool implements Runnable {
	private Thread runner;

	private Connection[] connPool;
	private int[] connStatus;// 0 indicatting the connection is availabled. (2
	// indicates housekeeping lock).1 the connection
	// is using.

	/** The hash code of the connection. */
	private String[] connID;

	private long[] connLockTime, connCreateDate;

	private int currentConnection, lastConnection, minConnections,
			maxConnections, maxConnectionMSec;

	private String dbDriver, dbServer, dbLogin, dbPassword, logFileString;

	/** available: sets to false to destory, checked by getConnection(). */
	private boolean available = true;

	private String poolID;

	private PrintWriter logPrintWriter;
	private SQLWarning SQLWarning;

	/**
	 * Instantiates a new connection pool.
	 * 
	 * @param dbDriver
	 *            JDBC driver. e.g. 'oracle.jdbc.driver.OracleDriver'
	 * @param dbServer
	 *            JDBC connect string. e.g.
	 *            'jdbc:oracle:thin:@203.92.21.109:1526:orcl'
	 * @param dbLogin
	 *            Database login name.
	 * @param dbPassword
	 *            Database password.
	 * @param minConns
	 *            Minimum number of connections to start with.
	 * @param maxConns
	 *            Maximum number of connections in dynamic pool
	 * @param logFileString
	 *            tAbsolute path name for logPrintWriter file. e.g. 'c:\temp\mylog.logPrintWriter'
	 * @param maxConnTime
	 *            Time in days between connection resets. (Reset does a basic
	 *            cleanup)
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public ConnectionPool(String dbDriver, String dbServer, String dbLogin,
			String dbPassword, int minConns, int maxConns,
			String logFileString, double maxConnTime) throws IOException {
		connPool = new Connection[maxConns];
		connStatus = new int[maxConns];
		connLockTime = new long[maxConns];
		connCreateDate = new long[maxConns];
		connID = new String[maxConns];
		currentConnection = minConns;
		this.maxConnections = maxConns;
		this.dbDriver = dbDriver;
		this.dbServer = dbServer;
		this.dbLogin = dbLogin;
		this.dbPassword = dbPassword;
		maxConnectionMSec = (int) (maxConnTime * 86400000.0);// 86400 sec/day
		maxConnectionMSec = maxConnectionMSec < 30000 ? 30000
				: maxConnectionMSec;// Recycle no less than 30 seconds.

		try {
			logPrintWriter = new PrintWriter(new FileOutputStream(
					logFileString), true);
		} catch (IOException e) {
			// Can't open the requested file. Open the default file.
			try {
				logPrintWriter = new PrintWriter(new FileOutputStream("Log_"
						+ System.currentTimeMillis()), true);
			} catch (IOException e2) {
				throw new IOException("Can't open any logPrintWriter file");
			}
		}
		// Write the pid file (used to clean up dead/broken connection)
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy.MM.dd G 'at' hh:mm:ss a zzz");
		java.util.Date nowc = new java.util.Date();
		poolID = formatter.format(nowc);

		BufferedWriter pidout = new BufferedWriter(new FileWriter(logFileString
				+ "pid"));
		pidout.write(poolID);
		pidout.close();

		logPrintWriter.println("Starting ConnectionPool:");
		logPrintWriter.println("dbDriver = " + dbDriver);
		logPrintWriter.println("dbServer = " + dbServer);
		logPrintWriter.println("dbLogin = " + dbLogin);
		logPrintWriter.println("logPrintWriter file = " + logFileString);
		logPrintWriter.println("minconnections = " + minConns);
		logPrintWriter.println("maxconnections = " + maxConns);
		logPrintWriter.println("Total refresh interval = " + maxConnTime
				+ " days");
		logPrintWriter.println("-----------------------------------------");

		// Initialize the pool of connections with the mininum connections:
		// Problems creating connections may be caused during reboot when the
		// servlet is started before the database is ready. Handle this
		// by waiting and trying again. The loop allows 5 minutes for
		// db reboot.

		boolean connectionSucceeded = false;
		int dbLoop = 20;

		try {
			for (int i = 1; i < dbLoop; i++) {
				try {
					for (int j = 0; j < currentConnection; j++) {
						createConn(j);
					}
					connectionSucceeded = true;
					break;
				} catch (SQLException sqlEx) {
					try {
						Thread.sleep(15000);
					} catch (InterruptedException e) {
					}
				}
			}
			if (!connectionSucceeded) {// All attempts at connecting to db
										// exhausted
				// logPrintWriter.print("\r\nAll attempts at connecting to Database exhausted")
				throw new IOException();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException();
		}

		// Fire up the background housekeeping thread
		runner = new Thread(this);
		runner.start();

	}// End ConnectionPool()

	/**
	 * 
	 */
	private void createConn(int i) throws SQLException {
		Date nowDate = new Date();
		try {
			Class.forName(dbDriver);
			connPool[i] = DriverManager.getConnection(dbServer, dbLogin,
					dbPassword);
			connStatus[i] = 0;// OK prepared
			connID[i] = connPool[i].toString();
			connLockTime[i] = 0;
			connCreateDate[i] = nowDate.getTime();

			// logPrintWriter.println(now.toString() + "  Opening connection " +
			// String.valueOf(i) +
			// " " + connPool[i].toString() + ":");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new SQLException(e.getMessage());
		}
	}

	/**
	 * Housekeeping thread. Runs in the background with low CPU overhead.
	 * Connections are checked for warnings and closure and are periodically
	 * restarted. This thread is a catchall for corrupted connections and
	 * prevents the buildup of open cursors. (Open cursors result when the
	 * application fails to close a Statement). This method acts as fault
	 * tolerance for bad connection/statement programming.
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		 boolean forever = true;
         Statement stmt=null;
         String currCatalog=null;

         while(forever) {
             // Make sure the logPrintWriter file is the one this instance opened
             // If not, clean it up!
             try {
                 BufferedReader in = new BufferedReader(new FileReader(logFileString + "pid"));
                 String curr_pid = in.readLine();
                 if(curr_pid.equals(poolID)) {
                 }
                 else {
                     logPrintWriter.close();

                      // Close all connections silently - they are definitely dead.
                     for(int i=0; i < currentConnection; i++) {
                         try {
                             connPool[i].close();
                         }
                         catch (SQLException e1) {} // ignore
                     }
                     // Returning from the run() method kills the thread
                     return;
                 }
                 in.close();
             }
             catch (IOException e1) {
//                 logPrintWriter.println("Can't read the file for pid info: " +
//                     logFileString + "pid");
             }

             // Get any Warnings on connections and print to event file
             for(int i=0; i < currentConnection; i++) {
                 try {
                     SQLWarning = connPool[i].getWarnings();
                     if(SQLWarning != null) {
                         logPrintWriter.println("Warnings on connection " +
                             String.valueOf(i) + " " + SQLWarning);
                         connPool[i].clearWarnings();
                     }
                 }
                 catch(SQLException e) {
                     logPrintWriter.println("Cannot access Warnings: " + e);
                 }
             }

             for(int i=0; i < currentConnection; i++) { // Do for each connection
                 long age = System.currentTimeMillis() - connCreateDate[i];

                 synchronized(connStatus) {
                     if(connStatus[i] > 0) { // In use, catch it next time!
                         continue;
                     }
                     connStatus[i] = 2; // Take offline (2 indicates housekeeping lock)
                 }

                 try {  // Test the connection with createStatement call
                     if(age > maxConnectionMSec) {  // Force a reset at the max conn time
                         throw new SQLException();
                     }

                     stmt = connPool[i].createStatement();
                     connStatus[i] = 0;  // Connection is O.K.

                     // Some DBs return an object even if DB is shut down
                     if(connPool[i].isClosed()) {
                         throw new SQLException();
                     }
                    
                 }
                 catch(SQLException e) { // Connection has a problem, restart it
                     try {
                         logPrintWriter.println(new Date().toString() +
                             " ***** Recycling connection " +
                             String.valueOf(i) + ":"
                         );

                         connPool[i].close();
                         createConn(i);
                     }
                     catch(SQLException e1) {
                         logPrintWriter.println("Failed: " + e1);
                         connStatus[i] = 0;  // Can't open, try again next time
                     }
                 }
                 finally {
                     try {
                         if(stmt != null) {
                             stmt.close();
                         }
                     }
                     catch(SQLException e1){};
                 }
             }

             try {
                 Thread.sleep(20000);
             }  // Wait 20 seconds for next cycle
             catch(InterruptedException e) {
                 // Returning from the run method sets the internal
                 // flag referenced by Thread.isAlive() to false.
                 // This is required because we don't use stop() to
                 // shutdown this thread.
                 return;
             }
         }
	}

	/**
	 * Frees a connection. Replaces connection back into the main pool for
	 * reuse.
	 */
	public void freeConnection(Connection connection) {
		//String res = "";
		int thisConn = getConnectionID(connection);
		if (thisConn >= 0) {
			connStatus[thisConn] = 0;
		}
	}

	/**
	 * @return Returns the local JDBC ID for a connection or -1 if counldn't
	 *         found the connection.
	 */
	private int getConnectionID(Connection connection) {
		String connIDString = connection.toString();

		for (int i = 0; i < currentConnection; i++) {
			if (connID[i].equals(connIDString)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
     * Returns the age of a connection -- the time since it was handed out to
     * an application.
     */
	public long getAge(Connection connection){
		int thisconn=getConnectionID(connection);
		return System.currentTimeMillis()-connLockTime[thisconn];
	}

	   /**
     * Multi-phase shutdown.  having following sequence:
     * <OL>
     * <LI><code>getConnection()</code> will refuse to return connections.
     * <LI>The housekeeping thread is shut down.<br>
     *    Up to the time of <code>millis</code> milliseconds after shutdown of
     *    the housekeeping thread, <code>freeConnection()</code> can still be
     *    called to return used connections.
     * <LI>After <code>millis</code> milliseconds after the shutdown of the
     *    housekeeping thread, all connections in the pool are closed.
     * <LI>If any connections were in use while being closed then a
     *    <code>SQLException</code> is thrown.
     * <LI>The logPrintWriter is closed.
     * </OL><br>
     * Call this method from a servlet destroy() method.
     *
     * @param      millis   the time to wait in milliseconds.
     * @exception  SQLException if connections were in use after
     * <code>millis</code>.
     */
    public void destroy(int millis) throws SQLException {

        // Checking for invalid negative arguments is not necessary,
        // Thread.join() does this already in runner.join().

        // Stop issuing connections
        available=false;

        // Shut down the background housekeeping thread
        runner.interrupt();

        // Wait until the housekeeping thread has died.
        try { runner.join(millis); }
        catch(InterruptedException e){} // ignore

        // The housekeeping thread could still be running
        // (e.g. if millis is too small). This case is ignored.
        // At worst, this method will throw an exception with the
            // clear indication that the timeout was too short.

        long startTime=System.currentTimeMillis();

        // Wait for freeConnection() to return any connections
        // that are still used at this time.
        int useCount;
        while((useCount=getUseCount())>0 && System.currentTimeMillis() - startTime <=  millis) {
            try { Thread.sleep(500); }
            catch(InterruptedException e) {} // ignore
        }

        // Close all connections, whether safe or not
        for(int i=0; i < currentConnection; i++) {
            try {
                connPool[i].close();
            }
            catch (SQLException e1)
            {
                logPrintWriter.println("Cannot close connections on Destroy");
            }
        }

        if(useCount > 0) {
            //bt-test successful
            String msg="Unsafe shutdown: Had to close "+useCount+
                        " active DB connections after "+millis+"ms";
            logPrintWriter.println(msg);
            // Close all open files
            logPrintWriter.close();
            // Throwing following Exception is essential because servlet authors
            // are likely to have their own error logging requirements.
            throw new SQLException(msg);
        }

        // Close all open files
        logPrintWriter.close();

    }//End destroy()
    /**
     * Returns the number of connections in use.
     */
    public int getUseCount() {
        int useCount=0;
        synchronized(connStatus) {
            for(int i=0; i < currentConnection; i++) {
                if(connStatus[i] > 0) { // In use
                    useCount++;
                }
            }
        }
        return useCount;
    }//End getUseCount()

    /**
     * Returns the number of connections in the dynamic pool.
     */
    public int getSize() {
        return currentConnection;
    }//End getSize()

	/**
	 * This method hands out the connections in round-robin order. This prevents
	 * a faulty connection from locking up an application entirely. A browser
	 * 'refresh' will get the next connection while the faulty connection is
	 * cleaned up by the housekeeping thread.
	 * 
	 * If the min number of threads are ever exhausted, new threads are added up
	 * the the max thread count. Finally, if all threads are in use, this method
	 * waits 2 seconds and tries again, up to ten times. After that, it returns
	 * a null.
	 * 
	 * @return
	 */
	public Connection getConnection() {
		Connection connection=null;
		if (available) {
			boolean succeededFlag=false;
			for(int i=1;i<=10;i++){
				try{
					int loop=0;
					int roundRobin=lastConnection+1;
					if (roundRobin>=currentConnection) {
						roundRobin=0;
					}
					do {
						synchronized (connStatus) {
							if (connStatus[roundRobin]<1&&
									(!connPool[roundRobin].isClosed())) {
								connection=connPool[roundRobin];
								connStatus[roundRobin]=1;
								connLockTime[roundRobin]=System.currentTimeMillis();
								lastConnection=roundRobin;
								succeededFlag=true;
								break;
							}
							else {
								loop++;
								roundRobin=(++roundRobin)>currentConnection?0:roundRobin;
							}
						}
					} while (succeededFlag&&(loop<currentConnection));
				}catch (SQLException e) {
				}
				if (succeededFlag) {
					break;
				}else {
					synchronized (this) {//Add new connections to the pool
						if (currentConnection<maxConnections) {
							try {
								createConn(currentConnection);
								currentConnection++;
							} catch (SQLException e) {
								logPrintWriter.print("unable to create new connection"+e);
							}
						}
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO: handle exception
					}
					// Try again.
				}
			}// for: try 10 times loop
		} else {
            logPrintWriter.println("Unsuccessful getConnection() request during destroy()");
        } // End if(available)
		return connection;
	}
}






















