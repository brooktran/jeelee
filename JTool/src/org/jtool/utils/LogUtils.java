package org.jtool.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
/**
 * <B>LogUtils</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-6-19 created
 * @since bank4corba Ver 1.0
 * 
 */
public class LogUtils {

	private PrintWriter printWriter;

	//private final String filename;


	/**
	 * 
	 * @since bank4corba
	 * @param string
	 * @param b
	 */
	public LogUtils(String filename) throws IOException{

		try {
			printWriter = new PrintWriter(new FileOutputStream(filename),true);
			// Can't open the requested file. Open the default file.
		}
		catch (IOException e1) {
			System.err.println("Warning: LogUtils could not open \""
					+ filename + "\" to write log to. Make sure that your Java " +
					"process has permission to write to the file and that the directory exists."
			);
			try {
				printWriter = new PrintWriter(new FileOutputStream("DCB_" +
						System.currentTimeMillis() + ".log"), true
				);
			}
			catch (IOException e2) {
				throw new IOException("Can't open any log file");
			}
		}

	}

	public void log(String msg) throws IOException{
		printWriter.println(msg);
	}
























	public void close(){
		printWriter.close();
	}

}
