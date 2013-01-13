package com.test.headfirst.starbuzz;

/**
 * 浓咖啡类
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class Espresso  extends Beverage{
	public Espresso(){
		description="Expresso";
	}
	
	@Override
	public double cost(){
		return 1.99;
	}

}
