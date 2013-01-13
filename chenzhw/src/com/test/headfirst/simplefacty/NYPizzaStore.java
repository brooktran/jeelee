package com.test.headfirst.simplefacty;

import com.test.headfirst.simplefacty.pizza.NYStyleCheesePizza;
import com.test.headfirst.simplefacty.pizza.NYStyleClamPizza;
import com.test.headfirst.simplefacty.pizza.NYStyleVeggiePizza;
import com.test.headfirst.simplefacty.pizza.Pizza;

/**
 * 加盟店：纽约风味比萨店
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class NYPizzaStore extends PizzaStore{
	
	@Override
	protected Pizza createPizza(String item){
		if(item.equals("cheese")){
			return new NYStyleCheesePizza();
		}
		else if(item.equals("veggie")){
			return new NYStyleVeggiePizza();
		}
		else if(item.equals("clam")){
			return new NYStyleClamPizza();
		}
		else 
			return null;
	} 
}
