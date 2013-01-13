package org.jreader.reader;

import java.net.URL;
import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * <B>RomeTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-22 created
 * @since  Ver 1.0
 * 
 */
public class RomeTest {
	public static void main(String[] args) throws Exception {
		URL url=new URL("http://blog.sina.com.cn/rss/1191258123.xml");
		SyndFeedInput input=new SyndFeedInput();
		SyndFeed feed=input.build(new XmlReader(url));
		List list=feed.getEntries();
		for(int i=0;i<list.size();i++){
			SyndEntry entry=(SyndEntry)list.get(i);
			System.out.println(entry);
		}
	
	}
}



























