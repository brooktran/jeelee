/* ConfigItemEvent.java 1.0 2010-2-2
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

import java.util.EventObject;

/**
 * <B>ConfigItemEvent</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-12-9 created
 * @since  org.zhiwu.app.config Ver 1.0
 */
public class ConfigItemEvent extends EventObject {
	private static final long serialVersionUID = 6969985351797828818L;
	/**
	 * @uml.property  name="itemName"
	 */
	private String itemName;
	/**
	 * @uml.property  name="oldValue"
	 */
	private Object oldValue;
	/**
	 * @uml.property  name="newValue"
	 */
	private Object newValue;
	
	/**
	 * @param source
	 */
	public ConfigItemEvent(Object source, String itemName,
		     Object oldValue, Object newValue) {
		super(source);
		this.itemName = itemName;
		this.newValue = newValue;
		this.oldValue = oldValue;
	   }

	
	
	/**
	 * @return  the itemName
	 * @uml.property  name="itemName"
	 */
	public String getItemName() {
		return itemName;
	}


	/**
	 * @param itemName  the itemName to set
	 * @uml.property  name="itemName"
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	/**
	 * @return  the oldValue
	 * @uml.property  name="oldValue"
	 */
	public Object getOldValue() {
		return oldValue;
	}


	/**
	 * @param oldValue  the oldValue to set
	 * @uml.property  name="oldValue"
	 */
	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}


	/**
	 * @return  the newValue
	 * @uml.property  name="newValue"
	 */
	public Object getNewValue() {
		return newValue;
	}


	/**
	 * @param newValue  the newValue to set
	 * @uml.property  name="newValue"
	 */
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}


	
}
