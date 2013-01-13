/* MepperConstant.java 1.0 2010-2-2
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
package org.mepper.utils;

import org.mepper.app.MapApplication;
import org.zhiwu.app.AppManager;

/**
 * <B>MepperConstant</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-24 created
 * @since org.mepper.utils Ver 1.0
 * 
 */
public interface MepperConstant {
	////  resource
	public static final int DEFAULT_DEFINITION_TILE_ID		=	-100;
	public static final int DEFAULT_DEFINITION_MAP_ID		=	-200;
	
	//// project
	public static final int ROOT_SET_ID=-1;
	public static final int MAP_SET_ID=-2;
	public static final int LIBRARY_SET_ID=-3;
	
	
	// String
	public static final String EDITOR_USER_OBJECT="editor.user.object";
	

	
	public static final WordList RECENT_PROPERTIES =
			WordList.parse(AppManager.getPreference(MapApplication.class.getName()).get("recent.properties"));
    
}
