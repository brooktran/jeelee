/* Config.java 1.0 2010-2-2
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

import java.util.List;


/**
 * <B>Config</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-12-7 created
 * @since org.zhiwu.app.config Ver 1.0
 * 
 */
public interface Config {
	/**
	 * @return
	 */
//	Iterator<Pair<Class<? extends ConfigItem>, ConfigListener>> iterator();

	/**
	 * @param c
	 */
//	void put(Class<? extends ConfigItem> c,ConfigListener observer);

	/**
	 * @return
	 */
	int itemSize();
	
	public List<Class<? extends ConfigItem>> getItems();

	/**
	 * @param c
	 */
	void addItem(Class<? extends ConfigItem> c);

	/**
	 * @param c
	 * @param l
	 */
	void addConfigListener(Class<? extends ConfigItem> c, ConfigListener l);

	/**
	 * @param c
	 * @return
	 */
	List<ConfigListener> getListener(Class<? extends ConfigItem> c);

}
