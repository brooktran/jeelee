/* SubscirberFactory.java 1.0 2010-2-2
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

import java.util.List;

import org.jtools.jreader.persistent.ArticleEntry;
import org.jtools.jreader.persistent.Subscriber;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * <B>SubscirberFactory</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-14 created
 * @since org.jtools.jreader.internal.rss Ver 1.0
 * 
 */
public class SubscirberFactory {
	public static Subscriber rssToSubscriber(RssReader rss) {
		
		Subscriber sub=new Subscriber(rss.getLink());
		sub.setTitle(rss.getTitle());
		//TODO password name
		sub.setCopyright(rss.getCopyright());
		sub.setLanguage(rss.getLanguage());
		sub.setPublished(rss.getPublished());
		sub.setAuthor(rss.getAuthor());
		
		List entries=rss.getEntries();
		for(Object o:entries){
			SyndEntry e=(SyndEntry)o;
			sub.addEntry(new ArticleEntry(e));
		}
		
		return sub;
	}
	
	
	

}
