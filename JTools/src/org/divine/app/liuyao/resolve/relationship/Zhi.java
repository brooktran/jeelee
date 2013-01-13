/* Zhi.java 1.0 2010-2-2
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
package org.divine.app.liuyao.resolve.relationship;

import org.divine.app.liuyao.resolve.Item;

/**
 * <B>Zhi</B>
 * ֵ
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-10-17 created
 * @since org.divine.app.liuyao.resolve.relationship Ver 1.0
 * 
 */
public class Zhi extends AbstractRelationship{
	public Zhi(Item hight, Item low) {
		super(hight, low);
	}


	@Override
	public String toString() {
		return "值"+hight;
	}
	
	@Override
	public String getLabel() {
		return "值"+getSourceLabel();
	}
	
	@Override
	public boolean isLowAvailable() {
		return true;
	}
	
	@Override
	public int getStrength() {
		return StrengthPillar;
	}

}
