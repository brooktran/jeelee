/* ReaderItemEvent.java 1.0 2010-2-2
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
package org.jreader.gui;

import java.util.EventObject;

/**
 * <B>ReaderItemEvent</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-7 created
 * @since org.jreader.gui Ver 1.0
 * 
 */
public class ReaderItemEvent extends EventObject{
	private String name;
	private Object value;

	/**
	 * @param source
	 */
	public ReaderItemEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param jReader
	 * @param name
	 * @param category
	 */
	public ReaderItemEvent(Object source, String name, Object value) {
		super(source);
		this.name=name;
		this.value=value;
	}

}
