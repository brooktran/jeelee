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

import java.awt.Component;
import java.awt.Image;
import java.util.List;

/**
 * <B>ConfigItem</B> A ConfigItem is a set of properties
 * @author   Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version   Ver 1.0.01 2010-12-7 created
 * @since   org.zhiwu.app.config Ver 1.0
 */
public interface ConfigItem {
	/**
	 * @return
	 * @uml.property  name="label"
	 */
	String getLabel();

	/**
	 * @return
	 * @uml.property  name="icon"
	 */
	Image getIcon();

	/**
	 * @param  icon
	 * @uml.property  name="icon"
	 */
	void setIcon(Image icon);

	/**
	 * @param  string
	 * @uml.property  name="label"
	 */
	void setLabel(String string);

	/**
	 * @return
	 */
	Component getCompenent();

	/**
	 * @param app
	 */
//	void setApplication(IApplication app);

	/**
	 * 
	 */
	void init();

	/**
	 * @return
	 */
	boolean hasUnsaveChanged();

	/**
	 * 
	 */
	void handleChanged();

	/**
	 * @param value
	 */
	void addConfigListener(ConfigListener l);

	/**
	 * @param listener
	 */
	void addConfigListener(ConfigListener[] listeners);

	/**
	 * @param listener
	 */
	void addConfigListener(List<ConfigListener> listener);
}
