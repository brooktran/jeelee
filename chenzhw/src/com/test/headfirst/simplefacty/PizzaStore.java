package com.test.headfirst.simplefacty;

import com.test.headfirst.simplefacty.pizza.Pizza;

/**
 * 比萨类，由一群子类来负责实例化
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public abstract class PizzaStore {
	
	public Pizza orderPizza(String type){
		Pizza pizza;
		//从工厂对象中返回PizzaStore
		pizza=createPizza(type);
		
		pizza.prepare();
		pizza.bake();
		pizza.cut();
		pizza.box();
		
		return pizza;
		
	}
	
	protected abstract Pizza createPizza(String type);
	//
}
