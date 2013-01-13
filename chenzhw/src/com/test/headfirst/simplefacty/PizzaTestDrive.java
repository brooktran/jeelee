package com.test.headfirst.simplefacty;

import com.test.headfirst.simplefacty.pizza.ChicagoPizzaStore;
import com.test.headfirst.simplefacty.pizza.Pizza;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class PizzaTestDrive {
	
	public static void main(String[] args) {
		PizzaStore nyStore=new NYPizzaStore();
		PizzaStore chincagoStore=new ChicagoPizzaStore();
		
		Pizza pizza=chincagoStore.orderPizza("cheese");
		System.out.println("Joel ordered a "+pizza.getName()+"\0\n");
	}

}
