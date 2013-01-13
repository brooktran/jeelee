package com.test.net;


/**
 * Title:        Java网络编程演示
 * Description:  Java网络编程演示，用于北京师范大学计算机系Java课程教学示范。
 * Copyright:    Copyright (c) 2001
 * Company:      北京师范大学计算机系
 * @author 孙一林
 * @version 1.0
 */
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
public class getURLImage extends Frame
{
	private Image img;
	public void paint(Graphics g){
		g.drawImage(img,100,100,this);
	}
public void processWindowEvent(WindowEvent e)
 {super.processWindowEvent(e);
  if(e.getID()==WindowEvent.WINDOW_CLOSING)
         System.exit(0);
  }
 public static void main(String args[]) throws MalformedURLException,IOException
  {/*if(args.length!=1)
      {System.out.println("Usage:java URLTest3<imageurl>");
       System.exit(-1);
      }*/
   //URL url=new URL(args[0]);
	 URL url=new URL("http://www.pkuit.com/bbs/attachments/month_0803/20080320_5276b6c081dc094142405lhrzWclB0PI.jpg");
	 getURLImage urlt= new getURLImage();
	 urlt.img = urlt.createImage((ImageProducer) url.getContent());
	 urlt.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	 urlt.setSize(500, 300);
	 urlt.setVisible(true);
  }
}
