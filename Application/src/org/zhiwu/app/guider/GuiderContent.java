/* GuiderContent.java 1.0 2010-2-2
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

import java.awt.Component;

import org.zhiwu.app.Application;

/**
 * <B>GuiderContent</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-3 created
 * @since org.zhiwu.app Ver 1.0
 * 
 */
public interface GuiderContent {

	/**
	 * @param defaultGuiderView
	 */
	void setGuider(DefaultGuiderView defaultGuiderView);

	/**
	 * @return
	 */
	Component getComponent();

	/**
	 * @return
	 */
	String getTitle();

	/**
	 * @return
	 */
	String getContentText();

	/**
	 * @param guiderContentListener
	 */
	void addGuiderContentListener(GuiderContentListener guiderContentListener);

	/**
	 * @return
	 */
	Class<? extends GuiderContent> getNext();

	/**
	 * 
	 */
	void prepare();

	/**
	 * 
	 */
	void finish();

	/**
	 * @param app
	 */
	void setApplication(Application app);



//	/**
//	 * @return
//	 */
//	boolean canNext();
//
//	/**
//	 * @return
//	 */
//	boolean canFinish();

}
