package com.test.net;

import java.io.*;

/**
 * Title: Java实现网络文件传输 Description: 在客户端请求Web服务器传输指定文件，并将文件保存。 Copyright:
 * Copyright (c) 2002 Company:
 * 
 * @author 钟华
 * @version 1.0
 */

public class FileAccess implements Serializable { // 定义访问文件类

	RandomAccessFile oSavedFile;

	long nPos;//

	public FileAccess() throws IOException {
		this("", 0);
	}

	public FileAccess(String sName, long nPos) throws IOException {
		oSavedFile = new RandomAccessFile(sName, "rw");
		this.nPos = nPos;
		oSavedFile.seek(nPos);//定位
	}

	public synchronized int write(byte[] b, int nStart, int nLen) {
		int n = -1;
		try {
			oSavedFile.write(b, nStart, nLen);
			n = nLen;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return n;
	}
}