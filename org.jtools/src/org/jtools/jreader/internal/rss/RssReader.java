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
package org.jtools.jreader.internal.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jtools.jreader.internal.xml.DataSourceException;
import org.jtools.jreader.internal.xml.DefaultXMLManagerImpl;
import org.jtools.jreader.internal.xml.IXMLManager;
import org.jtools.jreader.persistent.ReaderDAO;
import org.jtools.jreader.persistent.Subscriber;
import org.xml.sax.InputSource;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.SyndFeedOutput;
import com.sun.syndication.io.XmlReader;

/**
 * <B>RssReader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-25 created
 * @since org.jreader.rss Ver 1.0
 * 
 */
public class RssReader {
	private final SyndFeed feed;
	

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

	/**
	 * @param string
	 */
	public RssReader(String uri) throws IllegalArgumentException, FeedException, IOException {
		URL url = new URL(uri);
		SyndFeedInput input = new SyndFeedInput();
		feed = input.build(new XmlReader(url));
	}

	/*
	 * *
	 */
	public void start() {
	}

	/*
	 * *
	 */
	public void save(Subscriber subscriber) throws FeedException,
			DataSourceException {
		// TODO move to io package
		SyndFeedOutput output = new SyndFeedOutput();
		Document document = output.outputJDom(feed);

		IXMLManager local = new DefaultXMLManagerImpl(ReaderDAO.DATA_PATH, false);

		Element e = (Element) local.getSingleNodeByXPath(
				local.getRootElement(), "/reader/subscriber/user[text()='"
						+ subscriber.getName() + "']/parent::*");
		if (e == null) {
			e = new Element("subscriber");

			e.addContent(new Element("user").setText(subscriber.getName()));//TODO
			e.addContent(new Element("password").setText(subscriber
					.getPassword()));
			e.addContent(new Element("link").setText(subscriber.getLink()));
			e.addContent(new Element("title").setText(feed.getTitle()));
			e.addContent(new Element("author").setText(feed.getAuthor()));
			e.addContent(new Element("copyright").setText(feed.getCopyright()));
			e.addContent(new Element("description").setText(feed
					.getDescription()));
			e.addContent(new Element("lanuage").setText(feed.getLanguage()));
//			e.addContent(new Element("feed").setText(feed.get))
			// e.addContent(new
			// Element("category").setText(feed.getCategories().get(0)));
			// e.addContent(new Element("image").setText(feed.getImage()));

			if (feed.getImage() != null) {
				System.err.println("time to add image attribute .");
			}

			local.getRootElement().addContent(e); // save
		}

		List<SyndEntry> syndList = feed.getEntries();
		for (int i = 0; i < syndList.size(); i++) {
			if (!existInLocal(syndList.get(i), e)) {
				addEntity(syndList.get(i), e);

			}
		}

		// List<SyndEntry> list = feed.getEntries();
		// for (Iterator<SyndEntry> i = list.iterator(); i.hasNext();) {
		// SyndEntry entry = i.next();
		// System.out.println(entry);
		// }

		local.saveToFile(false);
	}

	/**
	 * @param syndEntry
	 * @param e
	 */
	private void addEntity(SyndEntry entry, Element parent) {
		Element e=new Element("entity");
		
		e.addContent(new Element("title").setText(entry.getTitle()));

		e.addContent(new Element("category").setText(entry.getTitle()));//TODO

		
		e.addContent(new Element("published")
				.setText(entry.getPublishedDate() == null ? "" : entry
						.getPublishedDate().getTime() + ""));
		e.addContent(new Element("updated")
				.setText(entry.getUpdatedDate() == null ? "" : entry
						.getUpdatedDate().getTime() + ""));
		e.addContent(new Element("link").setText(entry.getLink()));
		e.addContent(new Element("author").setText(entry.getAuthor()));
		//TODO comments
//		e.addContent(new Element("guid").setText(entity.get))
		e.addContent(new Element("description").setText(entry.getDescription().getValue()));

//		StringUtils.htmlToString(StringUtils.removeHtmlSymbol(entity
//				.getDescription().getValue()))));
		parent.addContent(e);
		

	}

	/**
	 * @param syndEntry
	 * @param e
	 * @return
	 */
	private boolean existInLocal(SyndEntry syndEntry, Element e) {
		List<Element> localEntityList = e.getChildren("entity");
		for (int i = 0; i < localEntityList.size(); i++) {
			if (localEntityList.get(i).getChild("link").getText()
					.equals(syndEntry.getLink())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return
	 */
	public String getTitle() {
		return feed.getTitle();
	}

	/**
	 * @return
	 */
	public String getLink() {
		return feed.getLink();
	}

	/**
	 * @return
	 */
	public String getCopyright() {
		return feed.getCopyright();
	}

	/**
	 * @return
	 */
	public String getLanguage() {
		return feed.getLanguage();
	}

	/**
	 * @return
	 */
	public String getPublished() {
		return feed.getPublishedDate().getTime()+"";
	}

	/**
	 * @return
	 */
	public String getAuthor() {
		return feed.getAuthor();
	}

	/**
	 * 
	 */
	public List getEntries() {
		return feed.getEntries();
	}


}
