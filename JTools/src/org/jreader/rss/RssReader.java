/* RssReader.java 1.0 2010-2-2
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
package org.jreader.rss;

import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jreader.persistent.Subscriber;
import org.jreader.utils.StringUtils;
import org.xml.sax.InputSource;
import org.zhiwu.xml.DefaultXMLManagerImpl;
import org.zhiwu.xml.XMLManager;
import org.zhiwu.xml.DataSourceException;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * <B>RssReader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-25 created
 * @since org.jreader.rss Ver 1.0
 * 
 */
public class RssReader {
	private SyndFeed feed;

	/*
	 * *
	 * 
	 * @param uri
	 */
	public RssReader() {
	}

	/*
	 * *
	 * 
	 * @param inputStream
	 */
	public RssReader(InputStream inputStream) throws IllegalArgumentException,
			FeedException {
		SyndFeedInput input = new SyndFeedInput();
		feed = input.build(new InputSource(inputStream));
	}

	/*
	 * *
	 */
	public void start() {
	}

	/*
	 * *
	 */
	public void save(Subscriber subscriber) throws FeedException, DataSourceException {
		// TODO move to io package
		SyndFeedOutput output = new SyndFeedOutput();
		Document document = output.outputJDom(feed);

		XMLManager local = new DefaultXMLManagerImpl("saved.xml", false);

		Element e=(Element) local.getSingleNodeByXPath( local.getRootElement(), 
				 "/reader/subscriber/user[text()='"+subscriber.getName()+"']/parent::*");
		if(e ==null){
			e=new Element("subscriber");
			
			e.addContent(new Element("user").setText(subscriber.getName()));
			e.addContent(new Element("password").setText(subscriber.getPassword()));
			e.addContent(new Element("link").setText(subscriber.getUri()));
			e.addContent(new Element("title").setText(feed.getTitle()));
			e.addContent(new Element("author").setText(feed.getAuthor()));
			e.addContent(new Element("copyright").setText(feed.getCopyright()));
			e.addContent(new Element("description").setText(feed.getDescription()));
			e.addContent(new Element("lanuage").setText(feed.getLanguage()));
//			e.addContent(new Element("category").setText(feed.getCategories().get(0)));
//			e.addContent(new Element("image").setText(feed.getImage()));

			if(feed.getImage()!=null){
				System.err.println("time to add image attribute .");
			}

			
			local.getRootElement().addContent(e); // save
		}
		
		
		List<SyndEntry> syndList=feed.getEntries();
		for(int i=0;i<syndList.size();i++){
			if(! existInLocal(syndList.get(i),e)){
				addEntity(syndList.get(i),e);
				
			}
		}
		
		
		


//		List<SyndEntry> list = feed.getEntries();
//		for (Iterator<SyndEntry> i = list.iterator(); i.hasNext();) {
//			SyndEntry entry = i.next();
//			System.out.println(entry);
//		}

		local.saveToFile(false);
	}

	/**
	 * @param syndEntry
	 * @param e
	 */
	private void addEntity(SyndEntry entity, Element e) {
		e.addContent(new Element("title").setText(entity.getTitle()));
		
		e.addContent(new Element("published").setText(
				entity.getPublishedDate()==null?"":entity.getPublishedDate().getTime()+""));
		e.addContent(new Element("update").setText(
				entity.getUpdatedDate()==null?"":entity.getUpdatedDate().getTime()+""));
		e.addContent(new Element("link").setText(entity.getLink()));
		e.addContent(new Element("author").setText(entity.getAuthor()));
		e.addContent(new Element("description").setText(
				
						StringUtils.htmlToString(StringUtils.removeHtmlSymbol(entity.getDescription().getValue()))
						)
						);
		
		
		
	}

	/**
	 * @param syndEntry
	 * @param e
	 * @return
	 */
	private boolean existInLocal(SyndEntry syndEntry, Element e) {
		List<Element> localEntityList=e.getChildren("entity");
		for(int i=0;i<localEntityList.size();i++){
			if(localEntityList.get(i).getChild("link").getText()
					.equals(syndEntry.getLink())){
				return true;
			}
		}
		return false;
	}

}
