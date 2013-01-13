/* DefaultConfig.java 1.0 2010-2-2
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

import java.util.ArrayList;
import java.util.List;

import org.zhiwu.utils.LeftRightList;

/**
 * <B>DefaultConfig</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-12-8 created
 * @since  org.zhiwu.app.config Ver 1.0
 */
public class DefaultConfig implements Config{
	private final List<Class<? extends ConfigItem>> items;
	private final LeftRightList<Class<? extends ConfigItem>, ConfigListener> listeners;
	
	public DefaultConfig() {
		items=new ArrayList<Class<? extends ConfigItem>>();
		listeners=new LeftRightList<Class<? extends ConfigItem>, ConfigListener>();
	}

	@Override
	public int itemSize() {
		return items.size();
	}
	
	/**
	 * @return
	 * @uml.property  name="items"
	 */
	@Override
	public List<Class<? extends ConfigItem>> getItems(){
		return items;
	}
	
	@Override
	public List<ConfigListener> getListener(Class<? extends ConfigItem> c) {
		return listeners.getAllRight(c);
	}
	@Override
	public void addItem(Class<? extends ConfigItem> c) {
		items.add(c);
	}

	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.Config#addConfigListener(java.lang.Class, org.zhiwu.app.config.ConfigListener)
	 */
	@Override
	public void addConfigListener(Class<? extends ConfigItem> c,
			ConfigListener l) {
		listeners.add(c, l);
	}

}
