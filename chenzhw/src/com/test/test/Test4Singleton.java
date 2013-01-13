/* Test4Singleton.java 1.0 2010-2-2
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
package com.test.test;

/**
 * <B>Test4Singleton</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-12 created
 * @since com.test.test Ver 1.0
 * 
 */
public class Test4Singleton {

	private static class Singleton{
		private static final Test4Singleton instance=new Test4Singleton();
	}
	
	
	private Test4Singleton(){
		System.out.println("single creating ... ");

		// do something
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static final Test4Singleton getInstance(){
		return Singleton.instance;
	}
	
	
	public static void main(String[] args) {
		for(int i=0;i<4;i++){
			System.out.println("create");
			new Thread(new Runnable() {
				public void run() {
					Test4Singleton.getInstance();
				}
			}).start();
			System.out.println(i+"created .");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
