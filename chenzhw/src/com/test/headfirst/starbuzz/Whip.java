package com.test.headfirst.starbuzz;

/**
 * 豆浆类
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class Whip extends Beverage {
	Beverage beverage;
	
	public Whip(Beverage beverage){
		this.beverage=beverage;
	}
	/**
	 * @see com.test.headfirst.starbuzz.Beverage#cost()
	 */
	@Override
	public double cost() {
		return beverage.cost()+10.95;
	}
	
	@Override
	public String getDescription(){
		return beverage.getDescription()+",Whip";
	}

}
