package com.test.bop;

import java.util.Random;

public class CountTheNumberOfOne {
	
	/**
	 * 方法一：除法
	 * @param v
	 */
	public static int count1(int v){
		int num=0;
		while(v!=0){
			if(v%2==1){
				num++;
			}
			v=(v/2);
		}
		return num; 
	}
	
	/**
	 * 使用位操作
	 * @param v
	 */
	public static int count2(int v){
		int num=0;
		while(v!=0){
			num+=v&0x01;
			v>>=1;
		}
		return num;
	}
	/**
	 * 使用与操作
	 * @param v
	 */
	public static int count3(int v){
		int num=0;
		while(v!=0){
			v&=(v-1);
			num++;
		}
		return num;
	}
	
	/**
	 * 用最快的方法判断一个数是否为偶数,若为偶数则返回true 否则 返回false
	 * @param number
	 */
	public static boolean isEvenNumber(int number){
		return (number&1)==0;
	}
	
	/*public static int count4(int v){
		int n;
		//x=x-(x>>2)&0xAAAAAAAA;
		return n;
		
	}*/
	
	public static void main(String[] args){
		Random rd=new Random();
		int number=rd.nextInt(20);
		System.out.println("number :"+number+"\nform by binary:"+
				Integer.toBinaryString(number)+" \n 二进制中共有"+count1(number)+"个1");
		System.out.println();
		System.out.println(count2(number));
		System.out.println(count3(number));
		System.out.println();
		System.out.println(isEvenNumber(number)?"是 一个偶数":"不是一个偶数");
		System.out.println();
		System.out.println("32位");
		System.out.println(Integer.toBinaryString(0xffffffff));
		System.out.println(Integer.toBinaryString(0xAAAAAAAA));
	}

}
