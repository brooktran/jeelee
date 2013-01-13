/*
 **FileSplitterFetch.java
 */
package com.test.breakpointcontinuetransmission;

import java.io.*;
import java.net.*;

/**
 * The Class FileSplitterFetch. 负责部分文件的抓取
 */
public class FileSplitterFetch extends Thread {

	/** File URL. */
	String sURL; 

	/** File Snippet Start Position 分割后的文件的开始位置*/
	long nStartPos; 

	/**  File Snippet End Position */
	long nEndPos; 

	/** Thread's ID */
	int nThreadID; 

	/** Downing is over */
	boolean bDownOver = false; 

	/** Stop identical */
	boolean bStop = false; 

	/** File Access interface*/
	FileAccessI fileAccessI = null;

	/**
	 * Instantiates a new file splitter fetch.
	 * 
	 * @param sURL
	 *            the s url
	 * @param sName
	 *            the s name
	 * @param nStart
	 *            the n start
	 * @param nEnd
	 *            the n end
	 * @param id
	 *            the id
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public FileSplitterFetch(String sURL, String sName, long nStart, long nEnd,
			int id) throws IOException {
		this.sURL = sURL;
		this.nStartPos = nStart;
		this.nEndPos = nEnd;
		nThreadID = id;
		fileAccessI = new FileAccessI(sName, nStartPos);
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while (nStartPos < nEndPos && !bStop) {
			try {
				URL url = new URL(sURL);
				HttpURLConnection httpConnection = (HttpURLConnection) url
						.openConnection();
				httpConnection.setRequestProperty("User-Agent", "NetFox");
				String sProperty = "bytes=" + nStartPos + "-";
				httpConnection.setRequestProperty("RANGE", sProperty);
				Utility.log(sProperty);
				InputStream input = httpConnection.getInputStream();
				// logResponseHead(httpConnection);
				byte[] b = new byte[1024];
				int nRead;
				while ((nRead = input.read(b, 0, 1024)) > 0
						&& nStartPos < nEndPos && !bStop) {
					nStartPos += fileAccessI.write(b, 0, nRead);
					// if(nThreadID == 1)
					// Utility.log("nStartPos = " + nStartPos + ", nEndPos = " +
					// nEndPos);
				}
				Utility.log("Thread " + nThreadID + " is over!");
				bDownOver = true;
				// nPos = fileAccessI.write (b,0,nRead);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 打印回应的头信息
	/**
	 * Log response head.
	 * 
	 * @param con
	 *            the con
	 */
	public void logResponseHead(HttpURLConnection con) {
		for (int i = 1;; i++) {
			String header = con.getHeaderFieldKey(i);
			if (header != null)
				// responseHeaders.put(header,httpConnection.getHeaderField(header));
				Utility.log(header + " : " + con.getHeaderField(header));
			else
				break;
		}
	}

	/**
	 * Splitter stop.
	 */
	public void splitterStop() {
		bStop = true;
	}
}
