package com.test.net;

import javax.swing.UIManager;
import java.awt.*;

/**
 * Title: Java实现网络文件传输 Description: 在客户端请求Web服务器传输指定文件，并将文件保存。 Copyright:
 * Copyright (c) 2002 Company:
 * 
 * @author 钟华
 * @version 1.0
 */

public class TestMethod {
	// private String sWebAddr = "http://localhost:8080/java.zip"; //
	// 定义Web地址和文件名
	
	// 例如，传输http://cs.bnu.edu.cn/source/vb/地址的vb.zip 则 sWebAddr =
	// "http://cs.bnu.edu.cn/source/vb/vb.zip"
	private String sWebAddr="http://im.baidu.com/download/BaiduHi_2.3_Beta1.exe";
	private String sSavePath = "G:/test/java/chenzhw"; // 定义存文件路径

	private String sSaveName = "BaiduHi_2.3_Beta1.exe"; // 定义文件名

	public TestMethod() {
		try {
			SiteInfoBean bean = new SiteInfoBean(sWebAddr, sSavePath,
					sSaveName, 5);
			SiteFileFetch fileFetch = new SiteFileFetch(bean);
			fileFetch.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new TestMethod();
	}
}
