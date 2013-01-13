package test;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.mepper.editor.map.DiamondMapTest;



public class TestAll{
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestAll.class);
	}
	
	public static Test suite(){
		TestSuite suite=new TestSuite("Test for all object ");
		
		// JUint begin
//		suite.addTestSuite(ResourceBundleUtilTest.class);
		
//		suite.addTestSuite(DefaultLayerTest.class);
		
		suite.addTestSuite(DiamondMapTest.class);
//		suite.addTestSuite(IsometricMapTest.class);
//		suite.addTestSuite(XMLWriterTest.class);
		
		// JUnit end
		return suite;
	}
}