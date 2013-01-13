package com.test.headfirst.simplefacty.pizzafinally;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class NYPizzaIngredientFactory implements PizzaIngredientFactory {
	
	/**
	 * 面团
	 */
	public Dough createDough(){
		return new ThickCrustDough();
	}
	
	/**
	 * 腊肉
	 */
	public Sauce createSauce(){
		return new MarinaraSauce();
	}
	
	/**
	 * 乳酪
	 */
	public Cheese createCheese(){
		return new ReggianoCheese();
	}
	
	/**
	 * 素食，蔬菜
	 */
	public Veggies[] createVeggies(){
		Veggies[] veggies={new BlackOlives(),new BlackOlives()};
		return veggies;
		
	}
	
	/**
	 * 意大利式腊肠切片，纽约和芝加哥都会用到
	 */
	public Pepperoni createPepperoni(){
		return new SlicedPepperoni();
	}
	
	
	/**
	 * 蛤俐
	 */
	public Clams createClam(){
		return new FreshClams();
	}
}
