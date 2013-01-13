/* GuiderContentEvent.java 1.0 2010-2-2
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
package org.zhiwu.app.guider;

import java.util.EventObject;

/**
 * <B>GuiderContentEvent</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-8-4 created
 * @since  org.zhiwu.app Ver 1.0
 */
public class GuiderContentEvent extends EventObject{

	public static final String NextEnable = "next.enable";
	public static final String FinishEnable = "finish.enable";
	/**
	 * @uml.property  name="name"
	 */
	private String name;
	private Object value;

	/**
	 * @param source
	 */
	public GuiderContentEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param absGuiderContent
	 * @param name
	 * @param b
	 */
	public GuiderContentEvent(Object source, String name,
			Object value) {
		super(source);
		this.name=name;
		this.value=value;
	}

	/**
	 * @return
	 * @uml.property  name="name"
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * @return
	 */
	public Object getData() {
		// TODO Auto-generated method stub
		return value;
	}

}
