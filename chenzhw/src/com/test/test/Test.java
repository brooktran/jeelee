package com.test.test;

import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-12-27 新建
 * @since eclipse Ver 1.0
 * 
 */
public class Test {
	
	/*
	//初始化顺序测试
	{
		System.out.println(i);
	}
	static int i=5;
	A a=new A("a");
	static{
		System.out.println("0123");
	}
	
	static A aa=new A("aa");
	*/
	
	
	//测试输入与输出阻塞问题
	public static void testInput() throws Exception {// 两个线程同时输出有问题，一个输入一个输出没问题
		Thread t1=new Thread(){
			public void run(){
				while(true){
					System.out.println("  ----------------------- T1");
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		t1.start();
		Thread.sleep(5000);
		System.exit(0);
	}
	
	public static void main(String[] args) throws Exception{
		testInput();
		while(true){
			System.out.println();
			System.out.println(Console.readString());
		}
		
		//urlTest();
		
		
		
		/*System.out.println(0x07);
		System.out.println((0x07& 0xf0));
		System.out.println((0x07& 0xf0) >> 4);
		Test test=new Test();
		test.testA();
		testFloat();
		asertTest();
		System.out.println(3.5 % 2);
		testArrayCopy();
		testInt();*/
		/*int i=1==2?20:
			2==3?30:
				3==4?40:
					4==5?5:6;*/
		/*char[] d={'g','d'};
		System.out.println((d.toString()));
		 public String toString() {
				return getClass().getName() + "@" + Integer.toHexString(hashCode());
			    }

		StringBuffer sb=new StringBuffer("asdf");
		System.out.println(sb);
		sb.append("123");
		System.out.println(sb);*/
	}
	
	//测试初始化
	
	public void testSomeOutPut() throws Exception {
		System.out.println(new ArrayList<String>().size());//0
		System.out.println(getProperty("aa"));
		System.out.println("aa".startsWith(""));//true
		System.out.println("-\0-");// 打印   \0  ：空格
	}
	
	
	///测试按值传递
	public void testA() {
		A a=new A(1);
		a.a=10;
		System.out.println(a.a);
		fun(a);
		System.out.println(a.a);
	}
	
	public void fun(final A a){
		a.a=20;
		
	}
	
	private static void urlTest(){
		//Toolkit toolkit=Toolkit.getDefaultToolkit();
		print(com.test.test.Test.class.getResource("").toString());
	}
	
	public static void print(Object string){
		System.out.println(string);
	}
	
	public static void testArrayCopy(){
		double[] ds1=new double[3];
		for (int i = 0; i < ds1.length; i++) {
			ds1[i]=Math.random();
		}
		double[] ds2=new double[3];
		System.arraycopy(ds1, 0, ds2, 0, 3);
		//ds2=ds1;
		for(int i=0;i<3;i++){
			System.out.print(ds1[i]);
		}
		System.out.println(printArray(ds1));
		System.out.println(printArray(ds2));
		for (int i = 0; i < ds1.length; i++) {
			ds1[i]=Math.random();
		}
		System.out.println();
		System.out.println(printArray(ds1));
		System.out.println(printArray(ds2));
	}
	
	public static void testInt(){
		int[] i=new int[3];
		i[0]=5;
		int[] j=new int[3];
		j[0]=i[0];
		System.out.print(j[0]);
		i[0]=10;
		System.out.print(j[0]);
	}
	
	public static String printArray(double[] ds){
		String string="";
		for (int i = 0; i < ds.length; i++) {
			string+=ds[i]+" ";
		}
		return string;
	}
	
	public void arrayLengthTest() throws Exception {
		int[][] ints=new int[3][10];
		ints[0][0]=1;
		ints[0][1]=2;
		ints[0][2]=3;
		ints[1][0]=4;
		ints[1][1]=5;
		ints[1][2]=6;
		System.out.print(ints.length);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < ints[i].length; j++) {
				System.out.println(ints[i][j]);
			}
		}
	}
	
	/**
	 * 
	 * 作用:测试转换精度
	 *
	 */
	public static void testFloat() {
		DecimalFormat df=new DecimalFormat("###,###.0000");
		long x=Long.MAX_VALUE;
		double d=x;
		float f=x;
		System.out.println(Double.valueOf(x));
		System.out.println(""+df.format(x));
		System.out.println("" + df.format(d) );
		System.out.println("" + df.format(f));
		System.out.println("" + df.format(f+1));
	}
	
	public static void asertTest() {
		int i=1;
		assert i==1:"错误";
		System.out.println(i);
	}
	
	public static String getProperty(String key) {
		return System.getProperty(key);
	}
	

}

class A{
	A(int a){
		this.a=a;
	}
	A(String s){
		this.s=s;
		System.out.println(s);
	}
	static int a=0;
	String s;
	/*{
		System.out.println(s);
	}*/
}
