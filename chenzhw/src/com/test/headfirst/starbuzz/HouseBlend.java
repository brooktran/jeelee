package com.test.headfirst.starbuzz;

/**
 * 混合咖啡
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class HouseBlend extends Beverage {
	public HouseBlend(){
		description="house Blend Coffee";
	}

	/**
	 * @see com.test.headfirst.starbuzz.Beverage#cost()
	 */
	@Override
	public double cost() {
		return .89;
	}

}
