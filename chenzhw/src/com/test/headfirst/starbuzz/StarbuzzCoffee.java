package com.test.headfirst.starbuzz;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class StarbuzzCoffee {
	public static void main(String[] args) {
		Beverage beverage=new Espresso();
		System.out.println(beverage.getDescription()+"$"+beverage.cost());
		
		Beverage beverage2=new HouseBlend();
		beverage2=new Mocha(beverage2);
		beverage2=new Whip(beverage2);
		beverage2=new Mocha(beverage2);
		
		System.out.println(beverage2.getDescription()+" $"+beverage2.cost());
	}
}
