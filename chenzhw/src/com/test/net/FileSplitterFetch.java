package com.test.net;

import java.io.*;
import java.net.*;

/**
 * Title: Java实现网络文件传输 Description: 在客户端请求Web服务器传输指定文件，并将文件保存。 Copyright:
 * Copyright (c) 2002 Company:
 * 
 * @author 钟华
 * @version 1.0
 */

public class FileSplitterFetch extends Thread {
	/** 定义文件传输时使用的变量*/
	String sURL; 

	long nStartPos; // 分段文件传输开始位置

	long nEndPos; // 分段文件传输结束位置

	int nThreadID; // 子线程ID

	boolean bDownOver = false; // 完成文件传输

	boolean bStop = false; // 停止文件传输

	FileAccess fileAccess = null;

	public FileSplitterFetch(String sURL, String sName, long nStart, long nEnd,
			int id) throws IOException {
		this.sURL = sURL;
		this.nStartPos = nStart;
		this.nEndPos = nEnd;
		nThreadID = id;
		fileAccess = new FileAccess(sName, nStartPos);
	}

	public void run() {
		while (nStartPos < nEndPos && !bStop) {
			try {
				URL url = new URL(sURL);
				HttpURLConnection httpConnection = (HttpURLConnection) url
						.openConnection();
				httpConnection.setRequestProperty("User-Agent", "NetFox");//设置User-Agent
				String sProperty = "bytes=" + nStartPos + "-";
				httpConnection.setRequestProperty("RANGE", sProperty);//设置断点续传的开始位置
				Utility.log(sProperty);
				InputStream input = httpConnection.getInputStream();//获得输入流
				byte[] b = new byte[10240];//10K
				int nRead;
				while ((nRead = input.read(b, 0, 10240)) > 0
						&& nStartPos < nEndPos && !bStop) {
					nStartPos += fileAccess.write(b, 0, nRead);
				}
				Utility.log("Thread " + nThreadID + " is over!");
				bDownOver = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void logResponseHead(HttpURLConnection con) {
		for (int i = 1;; i++) {
			String header = con.getHeaderFieldKey(i);
			if (header != null)
				Utility.log(header + " : " + con.getHeaderField(header));
			else
				break;
		}
	}

	public void splitterStop() {
		bStop = true;
	}
}
