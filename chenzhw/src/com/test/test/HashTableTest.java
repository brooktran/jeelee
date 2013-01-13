/**
 * 
 */
package com.test.test;

import java.util.Enumeration;
import java.util.Hashtable;


/**
 * 本类是
 * 功能:
 * 相关信息:
 * 和其他类关系:
 * 为什么选这个类:
 * 使用方法:
 * sample:
 * 注意事项:
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-5 新建
 * @since eclipse Ver 1.0
 * 
 */
public class HashTableTest {
	public static void main(String[] args) {
		Hashtable<MyKey,Integer> ht=new Hashtable<MyKey,Integer>();
		ht.put(new MyKey("zhangsan",19), new Integer(1));
		ht.put(new MyKey("zhangsi",18), new Integer(2));
		ht.put(new MyKey("laosan",30), new Integer(4));
		
		Enumeration e=ht.keys();//
		
		while(e.hasMoreElements()){
			MyKey key=(MyKey)e.nextElement();
			System.out.println(key.toString()+"="+ht.get(key));
		}
	}
	
	
	
}
class MyKey{
	private String name=null;
	private int age=0;
	
	public MyKey(String name,int age){
		this.name=name;
		this.age=age;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof MyKey){
			MyKey myKey=(MyKey)obj;
			if(name.equals(myKey.name)&&age==myKey.age){
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
	@Override
	public int hashCode(){
		return name.hashCode()+age;
	}
	
	@Override
	public String toString(){
		return name+","+age;
	}
}