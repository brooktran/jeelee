/* FiveElementFactory.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.divine.app.liuyao;

/**
 * <B>FiveElementFactory</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-19 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public class FiveElementFactory {
	private FiveElementFactory(){
	}
	
	
	public static FiveElement createElement(int v){
		if (v == FiveElement.GOLD) {
			return new Gold();
		}
		if (v == FiveElement.WATER) {
			return new Water();
		}
		if (v == FiveElement.WOOD) {
			return new Wood();
		}
		if (v == FiveElement.FIRE) {
			return new Fire();
		}
		if (v == FiveElement.SOIL) {
			return new Soil();
		}
		throw new IllegalArgumentException("the value of five element is illegal");
	}
}
