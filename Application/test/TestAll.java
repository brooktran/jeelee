import junit.framework.Test;
import junit.framework.TestSuite;

import org.zhiwu.app.config.AppPreferencesTest;
import org.zhiwu.utils.FileUtilsTest;

/* TestAll.java 1.0 2010-2-2
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

/**
 * <B>TestAll</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-3-4 created
 * @since  Ver 1.0
 * 
 */
public class TestAll {

	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestAll.class);
	}
	
	public static Test suite(){
		TestSuite suite=new TestSuite("test for application");
		
		suite.addTestSuite(AppPreferencesTest.class);
		suite.addTestSuite(FileUtilsTest.class);
		
		
		
		return suite;
	}
}
