/**
 * 
 */
package com.test.test;


/**
 * 自己实现一个简单的HashMap及其原理 ,HashCodeTest实现了Map.Entry 
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-4 新建
 * @since eclipse Ver 版本号
 * 
 */
public class HashCodeTest{
	public static void main(String[] args) {
		String[] hello="hello hello".split(" ");
		System.out.println(hello[0].hashCode());
		System.out.println(hello[1].hashCode());
	}
	
}
