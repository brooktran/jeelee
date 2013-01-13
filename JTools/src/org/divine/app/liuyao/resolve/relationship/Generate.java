/* Generate.java 1.0 2010-2-2
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
 * <B>Generate</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-9 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class Generate extends AbstractRelationship {

	/**
	 * @param hight
	 * @param strength
	 */
	public Generate(Item source, Item to) {
		super(source,to);
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#init()
	 */
	@Override
	protected void init() {
		super.init();
		if (!available) {
			return;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "得"+hight+"生";
	}
	
	@Override
	public String getLabel() {
		return "得"+getSourceLabel()+"生";
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.IRelationship#isLowAvailable()
	 */
	@Override
	public boolean isLowAvailable() {
		// TODO Auto-generated method stub
		return true;
	}
	
/* (non-Javadoc)
 * @see org.divine.app.liuyao.resolve.relationship.IRelationship#getStrength()
 */
@Override
public int getStrength() {
	return (int) (low.getStrength() + hight.getStrength()
			* AbstractRelationship.StrengthGenerate);
}
}
