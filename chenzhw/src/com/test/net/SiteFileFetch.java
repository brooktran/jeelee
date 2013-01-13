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

public class SiteFileFetch extends Thread { // 传输文件线程类
	SiteInfoBean siteInfoBean = null; // 文件信息Bean

	long[] nPos;

	long[] nStartPos; // 开始位置

	long[] nEndPos; // 结束位置

	FileSplitterFetch[] fileSplitterFetch; // 子线程对象

	long nFileLength; // 文件长度

	boolean bFirst = true; // 是否第一次取文件

	boolean bStop = false; // 停止标志

	File tmpFile; // 文件传输临时信息

	DataOutputStream dataoutput; // 输出到文件的输出流

	public SiteFileFetch(SiteInfoBean bean) throws IOException {
		siteInfoBean = bean;
		tmpFile = new File(bean.getSFilePath() + File.separator
				+ bean.getSFileName() + ".info");// File.separator 系统定义的分割符号
		if (tmpFile.exists()) {
			bFirst = false;
			read_nPos();
		} else {
			nStartPos = new long[bean.getNSplitter()];
			nEndPos = new long[bean.getNSplitter()];
		}
	}

	public void run() {
		// 获得文件长度
		// 分割文件
		// 实例FileSplitterFetch
		// 启动FileSplitterFetch线程
		// 等待子线程返回
		try {
			if (bFirst) {
				nFileLength = getFileSize();
				System.out.println("文件长度："+nFileLength);//~
				if (nFileLength == -1) {
					System.err.println("File Length is not known!");
				} else if (nFileLength == -2) {
					System.err.println("File is not access!");
				} else {//分配
					for (int i = 0; i < nStartPos.length; i++) {
						nStartPos[i] = (long) (i * (nFileLength / nStartPos.length));
					}
					for (int i = 0; i < nEndPos.length - 1; i++) {
						nEndPos[i] = nStartPos[i + 1];
					}
					nEndPos[nEndPos.length - 1] = nFileLength;
				}
			}
			// 启动子线程
			fileSplitterFetch = new FileSplitterFetch[nStartPos.length];
			for (int i = 0; i < nStartPos.length; i++) {
				fileSplitterFetch[i] = new FileSplitterFetch(siteInfoBean
						.getSSiteURL(), siteInfoBean.getSFilePath()
						+ File.separator + siteInfoBean.getSFileName(),
						nStartPos[i], nEndPos[i], i);
				Utility.log("Thread " + i + " , nStartPos = " + nStartPos[i]
						+ ", nEndPos = " + nEndPos[i]);
				fileSplitterFetch[i].start();
			}
			// 等待子线程结束
			// int count = 0;
			// 是否结束while循环
			boolean breakWhile = false;
			while (!bStop) {
				write_nPos();
				Utility.sleep(500);
				breakWhile = true;
				for (int i = 0; i < nStartPos.length; i++) {
					if (!fileSplitterFetch[i].bDownOver) {
						breakWhile = false;
						break;
					}
				}
				if (breakWhile)
					break;
			}
			System.out.println("文件传输结束！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 获得文件长度
	public long getFileSize() {
		int nFileLength = -1;
		try {
			URL url = new URL(siteInfoBean.getSSiteURL());
			HttpURLConnection httpConnection = (HttpURLConnection) url
					.openConnection();
			httpConnection.setRequestProperty("User-Agent", "NetFox");//设置User-Agent
			int responseCode = httpConnection.getResponseCode();
			if (responseCode >= 400) {
				processErrorCode(responseCode);
				return -2; // -2 为Web服务器响应错误
			}
			String sHeader;
			for (int i = 1;; i++) {
				sHeader = httpConnection.getHeaderFieldKey(i);
				// ~
				System.out.println(sHeader);
				// ~
				if (sHeader != null) {
					if (sHeader.equals("Content-Length")) {
						nFileLength = Integer.parseInt(httpConnection
								.getHeaderField(sHeader));
						break;
					}
				} else
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Utility.log(nFileLength);
		return nFileLength;
	}

	// 保存传输信息（文件指针位置）
	private void write_nPos() {
		try {
			dataoutput = new DataOutputStream(new FileOutputStream(tmpFile));
			dataoutput.writeInt(nStartPos.length);
			for (int i = 0; i < nStartPos.length; i++) {
				// dataoutput.writeLong(nPos[i]);
				dataoutput.writeLong(fileSplitterFetch[i].nStartPos);
				dataoutput.writeLong(fileSplitterFetch[i].nEndPos);
			}
			dataoutput.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 读取保存的下载信息（文件指针位置）
	private void read_nPos() {
		try {
			DataInputStream input = new DataInputStream(new FileInputStream(
					tmpFile));
			int nCount = input.readInt();
			nStartPos = new long[nCount];
			nEndPos = new long[nCount];
			for (int i = 0; i < nStartPos.length; i++) {//读取
				nStartPos[i] = input.readLong();
				nEndPos[i] = input.readLong();
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processErrorCode(int nErrorCode) {
		System.err.println("Error Code : " + nErrorCode);
	}

	// 停止文件传输
	public void siteStop() {
		bStop = true;
		for (int i = 0; i < nStartPos.length; i++)
			fileSplitterFetch[i].splitterStop();
	}
}