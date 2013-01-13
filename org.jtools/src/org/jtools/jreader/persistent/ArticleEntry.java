/* ArticleEntry.java 1.0 2010-2-2
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
package org.jtools.jreader.persistent;

import java.util.List;

import com.sun.syndication.feed.synd.SyndEntry;

/**
 * <B>ArticleEntry</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-14 created
 * @since org.jtools.jreader.persistent Ver 1.0
 * 
 */
public class ArticleEntry {
	private String title;
	private String descirption;
	private String publiced;
	private String link;//TODO links list
	private String update;
	private final List authors;//TODO authors
//	private String content;
	
	
	/**
	 * @param e
	 */
	public ArticleEntry(SyndEntry e) {
		title=e.getTitle();
		descirption=e.getDescription().getValue();
		publiced=e.getPublishedDate().getTime()+"";
		link=e.getLink();
		update=e.getLink();
		authors=e.getAuthors();
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the descirption
	 */
	public String getDescirption() {
		return descirption;
	}
	/**
	 * @param descirption the descirption to set
	 */
	public void setDescirption(String descirption) {
		this.descirption = descirption;
	}
	/**
	 * @return the publiced
	 */
	public String getPubliced() {
		return publiced;
	}
	/**
	 * @param publiced the publiced to set
	 */
	public void setPubliced(String publiced) {
		this.publiced = publiced;
	}
	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}
	/**
	 * @param link the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}
	/**
	 * @return the update
	 */
	public String getUpdate() {
		return update;
	}
	/**
	 * @param update the update to set
	 */
	public void setUpdate(String update) {
		this.update = update;
	}
//	/**
//	 * @return the author
//	 */
//	public String getAuthor() {
//		return author;
//	}
//	/**
//	 * @param author the author to set
//	 */
//	public void setAuthor(String author) {
//		this.author = author;
//	}
//	/**
//	 * @return the content
//	 */
//	public String getContent() {
//		return content;
//	}
//	/**
//	 * @param content the content to set
//	 */
//	public void setContent(String content) {
//		this.content = content;
//	}
	/**
	 * @return the authors
	 */
	public List getAuthors() {
		return authors;
	}
	
	

}
