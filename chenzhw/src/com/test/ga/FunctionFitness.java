package com.test.ga;


/**
 * 计算xyz*sin(xyz)最大值的遗传函数适应值 2007-4-25
 * 
 * @author Zou Ang Contact <a href ="mailto:brook.tran.c@gmail.com">Brook Tran</a>
 */
public class FunctionFitness implements FitnessCalculate {
	
	/**
	 * x、y、z都用8位来编码，即x,y,z都属于[0,255]
	 * 
	 */
	public double calculate(char[] chromosome) {
		double x=0;
		double y=0;
		double z=0;
		for(int i=0;i<8;i++)
		{
			int j=i+8;
			int k=i+16;
			x=x+Math.pow(2, 7-i)*(Integer.valueOf(chromosome[i]-48));
			y=y+Math.pow(2, 7-i)*(Integer.valueOf(chromosome[j]-48));
			z=z+Math.pow(2, 7-i)*(Integer.valueOf(chromosome[k]-48));
		}
		return x*y*z*Math.sin(x*y*z);
	}

}
