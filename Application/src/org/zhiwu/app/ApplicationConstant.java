/*
 * ApplicationConstant.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran All rights reserved.
 * 
 * The copyright of this software is own by the authors. You may not use, copy
 * or modify this software, except in accordance with the license agreement you
 * entered into with the copyright holders. For details see accompanying license
 * terms.
 */
package org.zhiwu.app;


/**
 * <B>ApplicationConstant</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-11-15 created
 * @since org.zhiwu.app Ver 1.0
 * 
 */
public interface ApplicationConstant {

	/**
	 * property name
	 */
	String PROPERTY_VIEW_CHANGED = "view changed";
	/**
	 * property name
	 */
	String PROPERTY_SAVE = "save";
	String VIEW_DATA = "view data";
	String PROPERTY_LOCALE = "locale";
	
	
	/** The state property name -- STATE_PROPERTY. Application has some state one by one. They are
	 * begin, init, start and started. */
	public static final String STATE_PROPERTY="state";

	/** application key state. */
	String STATE_BEGIN = "application.state.begin";
	/** application key state. */
	String STATE_EXIT = "application.state.exit";
	/** application key state. */
	String STATE_INIT = "application.state.init";
	/** application key state. */
	String STATE_LAUNCH = "application.state.launch";
	/** application key state. */
	String STATE_STARTIING = "application.state.starting";
	/** application key state. */
	String STATE_STARTED = "application.state.started";
	/** application key state. */
	String STATE_STOP = "application.state.stop";
	/** application key state. */
	String STATE_INIT_VIEW = "application.state.init.view";
	
	String READING_DATA = "application.state.init.reading.data";
}
