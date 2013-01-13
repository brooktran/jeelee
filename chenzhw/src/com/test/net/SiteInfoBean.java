package com.test.net;

/**
 * Title:        Java实现网络文件传输
 * Description:  在客户端请求Web服务器传输指定文件，并将文件保存。
 * Copyright:    Copyright (c) 2002
 * Company:
 * @author 钟华
 * @version 1.0
 */

public class SiteInfoBean {     // 定义获取和设置相关文件信息类
  private String sSiteURL;    // 定义URL变量
  private String sFilePath; // 定义存文件路径变量
  private String sFileName; // 定义文件名变量
  private int nSplitter; // 定义传输文件计数值

  public SiteInfoBean(){
    this("","","",5);     // 设置传输文件计数值
  }

  public SiteInfoBean(String sURL,String sPath,String sName,int nSpiltter){
    sSiteURL= sURL;
    sFilePath = sPath;
    sFileName = sName;
    this.nSplitter = nSpiltter;
  }

  public String getSSiteURL(){
    return sSiteURL;
  }

  public void setSSiteURL(String value){
    sSiteURL = value;
  }

  public String getSFilePath(){
    return sFilePath;
  }

  public void setSFilePath(String value){
    sFilePath = value;
  }

  public String getSFileName(){
    return sFileName;
  }

  public void setSFileName(String value){
    sFileName = value;
  }

  public int getNSplitter(){
    return nSplitter;
  }

  public void setNSplitter(int nCount){
    nSplitter = nCount;
  }
}