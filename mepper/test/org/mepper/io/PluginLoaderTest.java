/* PluginLoaderTest.java 1.0 2010-2-2
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
package org.mepper.io;

import junit.framework.TestCase;
import junit.swingui.TestRunner;

import org.mepper.app.MapApplication;
import org.zhiwu.app.AppManager;

/**
 * <B>PluginLoaderTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-5-28 created
 * @since org.mepper.io Ver 1.0
 * 
 */
public class PluginLoaderTest extends TestCase {
	
	public void testLoaderClass() throws Exception {
		try {
			PluginLoader loader = PluginLoader.getInstance();
			Pluginable pp=loader.loadClass("org.map.MapIO",AppManager.getPreference(MapApplication.class.getName()).get("map.io.plugin"),
					"org.mepper.io.MapReader"
					);
			System.out.println(pp);
			 pp=loader.loadClass("org.map.MapIO",AppManager.getPreference(MapApplication.class.getName()).get("map.io.plugin"),
						"org.mepper.io.MapWriter"
						);
				System.out.println(pp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static void main(String[] args) {
		TestRunner.run(PluginLoaderTest.class);
	}

}
