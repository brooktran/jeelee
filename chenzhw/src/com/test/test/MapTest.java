package com.test.test;

import java.util.HashMap;
import java.util.Map;

/**
 * <B>MapTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2009-7-5 created
 * @since chenzhw Ver 1.0
 * 
 */
public class MapTest {

	public static void main(String[] args) {
		//测试Map的初始容量
		Map<Integer, Integer> map=new HashMap<Integer, Integer>(7);
		
		System.out.println("starting, current size= "+ map.size());
		for (int i = 0; i < 7; i++) {
			System.out.println("adds "+i+", current size= "+ map.size());
			map.put(i, i);
			
		}
		
		
		
		
	}
}
