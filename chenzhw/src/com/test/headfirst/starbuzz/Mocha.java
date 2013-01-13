package com.test.headfirst.starbuzz;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class Mocha extends CondimentDecorator{
	Beverage beverage;
	public Mocha(Beverage beverage){
		this.beverage=beverage;
	}
	
	@Override
	public String getDescription(){
		return beverage.getDescription()+",Mocha";
	}
	
	@Override
	public double cost(){
		return .20+beverage.cost();
	}
	
}
