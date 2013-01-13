/**
 * 
 */
package com.test.headfirst.starbuzz;

/**
 * 饮料类
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-2 新建
 * @since eclipse Ver 版本号
 * 
 */
public abstract class Beverage {
	String description="Unknow Beverage";
	
	public String getDescription(){
		return description;
	}
	
	public abstract double cost();
}
