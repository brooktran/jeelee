/* Subscriber.java 1.0 2010-2-2
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

import java.util.ArrayList;
import java.util.List;

/**
 * <B>Subscriber</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-25 created
 * @since org.jreader.reader Ver 1.0
 * 
 */
public class Subscriber implements JReaderItem{
	/**
	 * @return the entries
	 */
	public List<ArticleEntry> getEntries() {
		return entries;
	}




	/**
	 * @param entries the entries to set
	 */
	public void setEntries(List<ArticleEntry> entries) {
		this.entries = entries;
	}




//	/**
//	 * @return the category
//	 */
//	public Category getCategory() {
//		return category;
//	}




	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}




	private String name;
	private String password;
	private String link;
	private Category category;// the  parent
	private String title;
	private String copyright;
	private String language;
	private String published;
	private String update;
	private String author;
	private List<ArticleEntry> entries;
	private String user;
	

//	/**
//	 * 
//	 */
//	public Subscriber(String name,String password) {
//		this.name=name;
//		this.password=password;
//	}

	/**
	 * @param link
	 */
	public Subscriber(String link) {
		this.link=link;
		entries=new ArrayList<ArticleEntry>();
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
	 * @return the published
	 */
	public String getPublished() {
		return published;
	}

	/**
	 * @param published the published to set
	 */
	public void setPublished(String published) {
		this.published = published;
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

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the copyright
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

	/**
	 * @param category the category to set
	 */
	public void setParent(Category category) {
		this.category = category;
	}

	/**
	 * @return the category
	 */
	public Category getParent() {
		return category;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.jtools.jreader.persistent.JReaderItem#addChildren(java.util.List)
	 */
	@Override
	public void addChildren(List<? extends JReaderItem> children) {
		throw new UnsupportedOperationException();
	}
	/* (non-Javadoc)
	 * @see org.jtools.jreader.persistent.JReaderItem#getChildren()
	 */
	@Override
	public JReaderItem[] getChildren() {
		return new JReaderItem[0];
	}
	/* (non-Javadoc)
	 * @see org.jtools.jreader.persistent.JReaderItem#hasChildren()
	 */
	@Override
	public boolean hasChildrenItems() {
		return false;
	}

	/**
	 * @param title
	 */
	public void setTitle(String title) {
		this.title=title;
		
	}

	/**
	 * @param copyright
	 */
	public void setCopyright(String copyright) {
		this.copyright=copyright;
	}

	/**
	 * @param language
	 */
	public void setLanguage(String language) {
		this.language=language;
	}

	/**
	 * @param articleEntry
	 */
	public void addEntry(ArticleEntry articleEntry) {
		entries.add(articleEntry);
	}
	
	/* (non-Javadoc)
	 * @see org.jtools.jreader.persistent.JReaderItem#hasParent()
	 */
	@Override
	public boolean hasParent() {
		return category !=null;
	}




	/**
	 * @return
	 */
	public String getUser() {
		return this.user;
	}

}
