/* test.java 1.0 2010-2-2
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
package org.jtools.jreader.internal.rss;

import java.net.URL;
import java.util.Iterator;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * <B>test</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-18 created
 * @since org.jreader.rss Ver 1.0
 * 
 */
public class test {

	public static void main(String[] args) throws Exception {
		URL url = new URL("http://blog.sina.com.cn/rss/1760976054.xml");
		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(url));

		List<SyndEntry> list = feed.getEntries();
		for (Iterator<SyndEntry> i = list.iterator(); i.hasNext();) {
			SyndEntry entry = i.next();
			System.out.println(entry.toString());
		}

		

	}

}
