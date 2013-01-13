package com.test.ga;

import java.util.Random;

public class Randomizer {
	private int lower;
	private int upper;
	
	private static Random random=new Random();
	
	public Randomizer(int lower,int upper){
		if(upper<=lower){
			throw new IllegalStateException("Upper is smaller than lower");
		}
		this.lower=lower;
		this.upper=upper;
	}

	public Double nextDouble()
	{
		return Double.valueOf(lower+(upper-lower)*random.nextDouble());
	}
	
	public Integer nextInteger(){
		return Integer.valueOf(lower+random.nextInt(upper-lower));
	}
	
	public char[] nextBitArray(int length){
		if(length<=0){
			throw new IllegalStateException("length is less than ZERO!");
		}
		char[] temp=new char[length];
		for(int i=0;i<length;i++)
			temp[i]=random.nextBoolean()?'0':'1';
		return temp;
	}

}
