package com.test.test;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-12-25 新建
 * @since eclipse Ver 1.0
 * 
 */
public class ClassForNameTest {

	public static void main(String[] args) {
		try{
		Data data=(Data)Class.forName("com.test.test.Data").newInstance();
		System.out.println(data.i);
		}
		catch (Exception e) {
			System.err.println("找不到");
		}
	}
}
