package com.test.rmi;

/**
 * <B>RMIUtils</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0.01 2009-4-13 created
 * @since chenzhw Ver 1.0
 * 
 */
public class RMIUtils {

	public static String getCodebase(){
		return System.getProperty("java.rmi.server.codebase");
	}
	
	public static void main(String[] args) {
		
		System.out.println(getCodebase());
	}
}
