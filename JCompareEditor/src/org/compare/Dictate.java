/* Dictate.java 1.0 2010-2-3
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
package org.compare;

import java.io.Serializable;
import java.util.Date;

/**
 * <B>Dictate</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2010-2-3 created
 * @since JCompareEditor Ver 1.0
 * 
 */
public class Dictate implements Serializable{
	public String title;
	public Date date;
	public String target;
	public String source;
	public String translated;
	public String comment;
	private static final long serialVersionUID = 6033242342535182157L;
	/**
	 * 
	 * @since JCompareEditor
	 */
	public Dictate( String title,Date date,String dictate,String passage,
			String translated, String comment) {
		this.title=title;
		this.date=date;
		target=dictate;
		source=passage;
		this.translated=translated;
		this.comment=comment;
	}

	/**
	 * 
	 * @since JCompareEditor
	 */
	public Dictate() {
		this("", new Date(), "", "", "", "");
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj == this) {
			return true;
		}


		if (obj instanceof Dictate) {
			Dictate other = (Dictate )obj;
			if (!other.title.equals(title)) {
				return false;
			}
			if(!other.date .equals(date)){
				return false;
			}
			if(!other.target .equals(target)){
				return false;
			}
			if(!other.source .equals(source)){
				return false;
			}
			if(!other.translated .equals(translated)){
				return false;
			}
			if(!other.comment .equals(comment)){
				return false;
			}
			return true;
		}
		return false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "title:"+title+
		",date:"+date+
		",target:"+target+
		",source:"+source+
		",translated:"+translated+
		",comment:"+comment;
	}
}
