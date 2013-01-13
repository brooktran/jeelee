/* AbstractConfigHandle.java 1.0 2010-2-2
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
package org.zhiwu.app.config;

/**
 * <B>AbstractConfigHandle</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-12-8 created
 * @since  org.zhiwu.app.config Ver 1.0
 */
public abstract class AbstractConfigHandle implements ConfigHandle{
	/**
	 * @uml.property  name="name"
	 */
	protected String name;
	/**
	 * handle target.
	 */
//	protected Object newValue;
//	/**
//	 * for future.
//	 */
//	protected Object oldValue;
	/**
	 * 
	 */
	public AbstractConfigHandle(String name) {
		this.name = name;
	}
	/**
	 * @return
	 * @uml.property  name="name"
	 */
	@Override
	public String getName() {
		return name;
	}
//	/**
//	 * @param target the target to set
//	 */
//	public void setNewValue(Object o) {
//		this.newValue = o;
//	}
//	/**
//	 * @return the target
//	 */
//	public Object newValue() {
//		return newValue;
//	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigHandle#oldValue()
	 */
//	@Override
//	public Object oldValue() {
//		return oldValue;
//	}
//	
//	/* (non-Javadoc)
//	 * @see org.zhiwu.app.config.ConfigHandle#setOldValue(java.lang.Object)
//	 */
//	@Override
//	public void setOldValue(Object o) {
//		this.oldValue=o;
//	}
}
