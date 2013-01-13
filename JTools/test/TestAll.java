import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.divine.app.liuyao.LiuyaoTest;
import org.divine.app.liuyao.LunarTest;
import org.divine.app.liuyao.TestEightDiagrams;

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
 * @version Ver 1.0.01 2010-4-5 created
 * @since  Ver 1.0
 * 
 */
public class TestAll extends TestCase{

	
	public static void main(String[] args) {
		TestRunner.run(TestAll.class);
	}
	
	/**
	 * 
	 */
	public static Test suite() {
		TestSuite suite=new TestSuite("test for divine");
		
		suite.addTest(new TestSuite(TestEightDiagrams.class));
		suite.addTest(new TestSuite(LunarTest.class));
		suite.addTest(new TestSuite(LiuyaoTest.class));
		
		return suite;
	}
	
	
}
