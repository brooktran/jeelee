/* Daily.java 1.0 2010-2-2
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
package org.daily;

import java.util.Calendar;

import org.jtools.annotations.RTF;
import org.jtools.annotations.Storable;
import org.zhiwu.utils.DateUtils;

/**
 * <B>Daily</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-11-14 created
 * @since org.daily Ver 1.0
 * 
 */
@Storable
public class Daily implements IStorable {
	@Storable
	private boolean delete;
	@Storable
	private String star;
	@Storable
	private String ID;

	@Storable
	private String title;

	@Storable
	private String createDate;
	@RTF
	private String content;// cdata
	
	/**
	 * key words of the daily. seperate by ","/" "
	 */@Storable
	private String tags;
	
	/**
	 * 
	 */
	public Daily() {
		this(System.currentTimeMillis()+"","10000",
				"",DateUtils.format(Calendar.getInstance().getTime(), "yy-MM-dd HH:mm"),"","");
	}

	/**
	 * @param string
	 * @param string2
	 * @param string3
	 * @param string4
	 */
	public Daily(String ID, String star,String title, String date, String content,String tags) {
		this.ID=ID;
		this.star=star;
		this.title=title;
		this.createDate=date;
		this.content=content;
		this.tags=tags;
		setDelete(false);
	}

	/**
	 * @param encode
	 */
	public void setContent(String content) {
		this.content=content;
	} 

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setDate(String date) {
		this.createDate = date;
	}

	/**
	 * @return the createDate
	 */
	public String getDate() {
		return createDate;
	}

	/**
	 * @param tags the tags to set
	 */
	public void setTags(String tags) {
		this.tags = tags;
	}

	/**
	 * @return the tags
	 */
	public String getTags() {
		return tags;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}

	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}

	/**
	 * @return
	 */
	public String getContent() {
		return content;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Daily[ID="+ID+
			", star="+star+
			", title="+title+
			",createDate="+createDate+
			",tag="+tags+
			"]";
	}

	/**
	 * @param star the star to set
	 */
	public void setStar(String star) {
		this.star = star;
	}

	/**
	 * @return the star
	 */
	public String getStar() {
		return star;
	}

	/**
	 * @param delete the delete to set
	 */
	public void setDelete(boolean delete) {
		this.delete = delete;
	}

	/**
	 * @return the delete
	 */
	public boolean isDelete() {
		return delete;
	}



}
