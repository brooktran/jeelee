package com.test.headfirst.simplefacty.pizza;

import java.util.ArrayList;
import java.util.List;

/**
 * 比萨类：“工厂”的产品
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public abstract class Pizza {
	String name;
	String dough;//面团类型
	String sauce;//浆料类型
	List<String> toppings=new ArrayList<String>();//一套佐料
	
	
	
	/**
	 * 准备
	 */
	public void prepare(){
		//
		System.out.println("Preparint"+name);
		System.out.println("Tossing dough...");
		System.out.println("Adding sauce...");
		System.out.println("Adding toppings:");
		for(int i=0;i<toppings.size();i++){
			System.out.println(" "+toppings.get(i));
		}
	}

	/**
	 * 烘烤
	 */
	public void bake(){
		//
		System.out.println("Bake for 25 minutes at 350");
	}
	
	/**
	 * 切片
	 */
	public void cut(){
		//
		System.out.println("Cutting the pizza into diagonal slices");
	}
	
	/**
	 * 包装
	 */
	public void box(){
		//
		System.out.println("Place  pizza in official PizzaStore box");
	}
	
	public String getName(){
		return name;
	}
	
}
