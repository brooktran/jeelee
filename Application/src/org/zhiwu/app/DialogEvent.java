/* DialogEvent.java 1.0 2010-2-2
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
package org.zhiwu.app;

import java.util.EventObject;

/**
 * <B>DialogEvent</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-3-28 created
 * @since  org.zhiwu.app Ver 1.0
 */
public class DialogEvent  extends EventObject {
	/**
	 * The data.
	 * @uml.property  name="data"
	 */
	protected Object data;

	/**
	 * The name.
	 * @uml.property  name="name"
	 */
	protected String name;

	/**
	 * The Constructor.
	 * 
	 * @param source the source
	 * 
	 * @since Application Framework
	 */
	public DialogEvent(Object source) {
		super(source);
	}

	/**
	 * The Constructor.
	 * 
	 * @param source the source
	 * @param name the name
	 * 
	 * @since Application Framework
	 */
	public DialogEvent(Object source,String name) {
		super(source);
		this.name = name;
	}

	/**
	 * Instantiates a new map event.
	 * 
	 * @param source the source
	 * @param name the name
	 * @param data the data
	 */
	public DialogEvent(Object source,String name,  Object data) {
		super(source);
		this.data = data;
		this.name = name;
	}

	/**
	 * Gets the data.
	 * @return  the data
	 * @uml.property  name="data"
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Sets the name.
	 * @param name  the name to set
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the name.
	 * @return  the name
	 * @uml.property  name="name"
	 */
	public String getName() {
		return name;
	}
}
