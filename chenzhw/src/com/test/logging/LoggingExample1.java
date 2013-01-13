package com.test.logging;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

/**
 * 类<B> LoggingExample1 </B>是
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-15 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class LoggingExample1 {
	public void testLogger() {
		try {
			LogManager lm = LogManager.getLogManager();// 1.1
			Logger logger;								// 1.2

			FileHandler fh = new FileHandler("log_test.xml");//1.3

			logger = Logger.getLogger("LoggingExample");	// 1.4

			lm.addLogger(logger);							// 2.1
			logger.setLevel(Level.INFO);
			fh.setFormatter(new XMLFormatter());			//2.2

			logger.addHandler(fh);							//2.3
			// root logger defaults to SimpleFormatter. We don't want messages
			// logged twice.
			// logger.setUseParentHandlers(false);
			logger.log(Level.INFO, "test 1");
			logger.log(Level.INFO, "test 2");
			logger.log(Level.INFO, "test 3");
			fh.close();										//3

		} catch (SecurityException e) {
			System.out.println("Exception thrown: " + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Exception thrown: " + e);

			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		new LoggingExample1() .testLogger();
	}
}
