/* JReader.java 1.0 2010-2-2
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
package org.jreader.reader;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.client.ClientProtocolException;
import org.jreader.net.HttpConnector;
import org.jreader.persistent.Subscriber;
import org.jreader.rss.RssReader;
import org.zhiwu.xml.DataSourceException;

import com.sun.syndication.io.FeedException;

/**
 * <B>JReader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-23 created
 * @since org.jreader.reader Ver 1.0
 * 
 */
public class Reader {
	public static final int Rss = 2;
	public static final int Blog = 1;
	public static final int Unknow = 0;

	private final String uri;
	private final int uriType;
	private final Subscriber subscriber;

	/*
	 * *
	 * 
	 * @param string
	 */
	public Reader(String uri, int uriType, Subscriber subscriber) {
		this.uri = uri;
		this.uriType = uriType;
		this.subscriber=subscriber;
	}

	public static void main(String[] args) throws Exception {
		Subscriber subscriber=new Subscriber("韩寒","");
		Reader br = new Reader("http://blog.sina.com.cn/rss/1191258123.xml",
				Rss,subscriber);
		br.start();
	}

	/*
	 * *
	 */
	public void start() {
		HttpConnector conn = new HttpConnector(uri);
		try {
			conn.start();

			if (uriType == Rss) {
				readRss(conn.getEntity().getContent());
			}

		} catch (ClientProtocolException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (DataSourceException e) {
			e.printStackTrace();
		}

		//		
		// String
		// contentType=conn.getHeader(HttpConnector.HEADER_CONTENT_TYPE).getValue();
		// if(contentType.endsWith("xml")){// rss ?
		//			
		// }else if(contentType.endsWith("html")){// blog
		//			
		// }
	}

	/*
	 * *
	 * 
	 * @param content
	 */
	private void readRss(InputStream inputStream) throws DataSourceException {
		try {
			RssReader reader = new RssReader(inputStream);
			reader.save(subscriber);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FeedException e) {
			e.printStackTrace();
		}
	}

	/*
	 * *
	 */
	private void readRss() {
		RssReader reader = new RssReader();
		reader.start();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// 判断是rss源还是html

	}

}
