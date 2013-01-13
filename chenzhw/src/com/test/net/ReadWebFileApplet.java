package com.test.net;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.net.*;
import java.io.*;

/**
 * Title:        获取网络文件
 * Description:  获取已知的网络文件。
 * Copyright:    Copyright (c) 2002
 * Company:      北京师范大学计算机系
 * @author 孙一林
 * @version 1.0
 */

public class ReadWebFileApplet extends Applet {
  TextField textField1 = new TextField();
  Button button1 = new Button();
  TextArea textArea1 = new TextArea();

  public ReadWebFileApplet() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit() throws Exception {
    textField1.setText("输入网络地址和文件名");
    //  textField1.setText("http://www.bnu.edu.cn/focus/survey/gaikuang.htm");
    textField1.setBounds(new Rectangle(0, 1, 179, 30));//偏移量（离屏幕左侧多少，顶端多少），高度，宽度。 
    this.setLayout(null);
    button1.setLabel("获取网络文件");
    button1.setBounds(new Rectangle(186, 4, 108, 28));
    button1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        button1_actionPerformed(e);
      }
    });
    textArea1.setBounds(new Rectangle(1, 37, 500, 400));//偏移量（离屏幕左侧多少，顶端多少），高度，宽度。 
    this.add(textField1, null);
    this.add(textArea1, null);
    this.add(button1, null);
  }

  public void ReadURL(String URLName) throws Exception {
    int HttpResult;                               // 服务器返回的状态
    URL url =new URL(URLName);                    // 创建URL
    URLConnection urlconn = url.openConnection(); // 连接并取得返回状态码
    urlconn.connect();
    HttpURLConnection httpconn =(HttpURLConnection)urlconn;
    HttpResult = httpconn.getResponseCode();
    if(HttpResult != HttpURLConnection.HTTP_OK)     // 如果不等于HTTP_OK，则连接不成功
      textArea1.setText("无法连接到" + textField1.getText());
    else {
      int filesize = urlconn.getContentLength();    // 取数据长度
      InputStreamReader isReader = new InputStreamReader(urlconn.getInputStream());
      //~
      File file=new File("google.gif");
      OutputStreamWriter osw=new OutputStreamWriter(new FileOutputStream(file));
      long countI=20480;
      //~
      char[] buffer = new char[20480];               // 创建存放输入流的缓冲  2048 Byte
      int num = 0;                                  // 读入的字节数
      while(num>-1) {
        num = isReader.read(buffer);                // 读入到缓冲区
        if(num < 0) {
        	textArea1.append("Over!!");
        	break;                          // 已经读完
        }
        //~
        //textArea1.append(urlconn.getContentEncoding());
        osw.write(buffer);
        textArea1.append((countI+=20480)+"\n");
        //~
       // textArea1.append(new String(buffer,0,num)); // 显示获取网络内容
      }
      isReader.close();                             //关闭输入流
    }
  }

  void button1_actionPerformed(ActionEvent e) {
    String str = e.getActionCommand();              // 取得发出命令控件显示的名称
    try {
      textArea1.setText("");                        // 清除文本区
      ReadURL(textField1.getText());                // 读指定的数据并显示
    }
    catch(Exception ex) {
      textArea1.append("\n\n读取失败"+ex.getMessage());
    }
  }
  public static void main(String[] args) {          // 定义Application程序入口
    Frame f = new Frame("获取网络文件");              // 定义窗体
    ReadWebFileApplet readWebFile = new ReadWebFileApplet();
    readWebFile.init();
    readWebFile.start();                            // 启动程序
    f.add("Center",readWebFile);                    // 显示在窗体内
    f.setSize(500,600);
    f.show();
  }
}