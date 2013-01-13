package com.test.net;

/**
 * Title:        Java实现网络文件传输
 * Description:  在客户端请求Web服务器传输指定文件，并将文件保存。
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author 钟华
 * @version 1.0
 */

public class Utility {    // 定义输出提示信息及线程sleep类

  public Utility(){
  }

  public static void sleep(int nSecond){
    try{
      Thread.sleep(nSecond);
    }
    catch(Exception e){
      e.printStackTrace ();
    }
  }

  public static void log(String sMsg){
    System.out.println(sMsg);
  }

  public static void log(int sMsg){
    System.out.println(sMsg);
  }
}