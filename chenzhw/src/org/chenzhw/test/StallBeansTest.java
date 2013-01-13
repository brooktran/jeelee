package org.chenzhw.test;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.chenzhw.beans.StallBeans;


/**
 * <B> StallBeansTest </B>
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-4-6 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class StallBeansTest extends TestCase{
	
	public void testGetTwelveHour(){
		int hour=50;
		StallBeans stallBeans=new StallBeans(hour);
		assertEquals(stallBeans.getTwelveHour(), hour*60*12);
		//assertEquals(stallBeans.getTime(100000), actual)
		assertEquals(stallBeans.getOneHour(), hour*60);
		assertEquals(stallBeans.getTime(100), 2);
		
	}
	
	public static void main(String[] args) {
		//TestSuite suite=new TestSuite();
		//suite.addTest(StallBeansTest);
		TestRunner.run(new StallBeansTest());
	}
}
