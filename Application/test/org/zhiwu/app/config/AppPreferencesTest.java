/* AppPreferencesTest.java 1.0 2010-2-2
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

import junit.framework.TestCase;

import org.zhiwu.app.Application;

/**
 * <B>AppPreferencesTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-12-13 created
 * @since org.zhiwu.app.config Ver 1.0
 * 
 */
public class AppPreferencesTest extends TestCase{

	/**
	 * Test method for {@link org.zhiwu.app.config.AppPreferences#AppPreferences(java.lang.String)}.
	 */
	public final void testAppPreferences() {
	}

	/**
	 * Test method for {@link org.zhiwu.app.config.AppPreferences#get(java.lang.String)}.
	 */
	public final void testGet() {
	}

	/**
	 * Test method for {@link org.zhiwu.app.config.AppPreferences#put(java.lang.Object, java.lang.Object)}.
	 */
	public final void testPut() {
	}

	/**
	 * Test method for {@link org.zhiwu.app.config.AppPreferences#save()}.
	 */
	public final void testSave() {
		AppPreference p=PreferenceManager.getInstance().getPreference(Application.class.getName());
		p.put("name", "unknown");
		p.save(); 
		// a pref open before anthor change.
		AppPreference p2=PreferenceManager.getInstance().getPreference(Application.class.getName());
		
		assertEquals(p.get("name"), "unknown");
		p.put("name", "newvalue"); 
		p.save();
		
		assertEquals(p2.get("name"), "newvalue");
		
	}

	/**
	 * Test method for {@link org.zhiwu.app.config.AppPreferences#getBoolean(java.lang.String)}.
	 */
	public final void testGetBoolean() {
	}

}
