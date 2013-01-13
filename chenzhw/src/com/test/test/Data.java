package com.test.test;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-12-25 新建
 * @since eclipse Ver 1.0
 * 
 */
public class Data implements InterfaceTest{
	public int i=50;
	
	Data(int i){
		this.i=i;
	}
	
	public Data createData() {
		ProxyTest proxy=new ProxyTest();
		return proxy.bind(new Data(i));
	}
	public void testMethod(){
		System.out.print("testMethod1");
	}
	
	public void testMethod2(){
		System.out.print("testMethod2");
	}
	
	public static void main(String[] args) {
		Data data =new Data(5);
		data.testMethod();
		Data data2=data.createData();
		data2.testMethod();
	}
}
