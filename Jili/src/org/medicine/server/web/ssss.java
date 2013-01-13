/* ssss.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.medicine.server.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zhiwu.utils.AppLogging;

/**
 * <B>ssss</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-18 created
 * @since Jili Ver 1.0
 * 
 */
public class ssss extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		out.println("<HTML>");
		out.println("<HEAD><TITLE>Hello World</TITLE></HEAD>");
		out.println("<BODY>");
		out.println("<BIG>Hello World</BIG>");
		out.println("</BODY></HTML>");
	}
	
	public static void main(String[] args)  {
		try {
			ServerSocket ss = new ServerSocket(9999);
			
			//We wait a bit here (for the flow)...
			Thread.sleep(1000);
			//Listening to port:
			
			
			Socket socket =new Socket("127.0.0.1", 8080);
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
			String strReturn = " \r\n";
			wr.write("GET index.html / HTTP/1.1"+strReturn);
			wr.write("Accept: image/gif, image/x-xbitmap, image/jpeg, image/pjpeg, application/x-shockwave-flash, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-ms-application, application/x-ms-xbap, application/vnd.ms-xpsdocument, application/xaml+xml, */*"+strReturn);
			wr.write("Accept-Language: zh-cn"+strReturn);
			  wr.write("Accept-Encoding: gzip, deflate"+strReturn);
			  wr.write("User-Agent: Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729)"+strReturn);
			  wr.write("Host: localhost:88"+strReturn);
			  wr.write("Connection: Keep-Alive"+strReturn);
			  wr.newLine();
			  wr.newLine(); //wr.write（strReturn ）;也不行
			  wr.flush();
			
			
			
			
			while(true){
				if(ss!=null){
					Socket s = ss.accept();
					
					PrintWriter p= new PrintWriter(s.getOutputStream(),true);
					BufferedReader buf = new BufferedReader(new InputStreamReader(s.getInputStream()));
					String string = null;
					while( (string = buf.readLine())!= null){
						System.out.println(string);
					}
				}
			}
			
		} catch (IOException e) {
			AppLogging.handleException(e);//"Error opening Socket: " + e.getMessage() + "\n"
		} catch (InterruptedException e) {
			AppLogging.handleException(e);
		}
		
		
		
		
	}
}
