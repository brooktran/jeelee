/* Message.java 1.0 2010-2-2
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
package org.jtools.jreader.ui.wizards.lang;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * <B>Message</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-12 created
 * @since org.jtools.lang Ver 1.0
 * 
 */
public class Message {
	private final static ResourceBundle resources=ResourceBundle.getBundle("org.jtools.jreader.ui.wizards.lang.JReader");

	private Message() {
	}

	public static String getString(String key) {
		try {
			return resources.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

}
