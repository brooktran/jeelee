/* HttpConnector.java 1.0 2010-2-2
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
package org.jreader.net;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * <B>HttpConnector</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-24 created
 * @since org.jreader.net Ver 1.0
 * 
 */
public class HttpConnector {

	private final String uri;
	private HttpResponse response;

	/*
	 * *
	 * 
	 * @param url
	 */
	public HttpConnector(String uri) {
		this.uri = uri;
	}

	/*
	 * * Start.
	 * 
	 * @throws ClientProtocolException the client protocol exception
	 * 
	 * @Throws: IOException in case of a problem or the connection was aborted
	 * ClientProtocolException in case of an http protocol error
	 */
	public void start() throws ClientProtocolException, IOException {
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(uri);
		get
				.setHeader(
						"Accept",
						"image/jpeg, application/x-ms-application, image/gif, application/xaml+xml, image/pjpeg, application/x-ms-xbap, application/vnd.ms-excel, application/vnd.ms-powerpoint, application/msword, application/x-shockwave-flash, */*");
		get.setHeader("Accept-Language", "zh-cn");
		get.setHeader("Connection", "Keep-Alive");
		get.setHeader("Accept-Encoding", "gzip,deflate");
		get
				.setHeader(
						"Subscriber-Agent",
						"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0; QQWubi 87; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0) ");

		response = client.execute(get);
//		Header[] headers = response.getAllHeaders();
//		for (int i = 0; i < headers.length; i++) {
//			System.out.println(headers[i]);
//		}
	}

	public Header getHeader(String name) {
		return response.getFirstHeader(name);
	}

	public HttpEntity getEntity() {
		return response.getEntity();
	}

	public boolean hasHeader(String name, String value) {
		return response.getFirstHeader(name).equals(value);
	}
	
	
	public static final String HEADER_SERVER="Server";
	public static final String HEADER_DATE="Date";
	public static final String HEADER_CONTENT_TYPE="Content-Type";
	public static final String HEADER_TRANSFER_ENCODING="Transfer-Encoding";
	public static final String HEADER_CONNECTION="Connection";
	public static final String HEADER_X_POWERED_BY="X-Powered-By";
	public static final String HEADER_P3P="P3P";
	public static final String HEADER_CACHE_CONTROL="Cache-Control";
	public static final String HEADER_EXPIRES="Expires";
	public static final String HEADER_LAST_MODIFIED="Last-Modified";
	public static final String HEADER_AGE="Age";
	public static final String HEADER_X_CACHE="X-Cache";
	public static final String HEADER_CONTENT_ENCODING="Content-Encoding";
	

}
