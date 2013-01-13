/* Lin.java 1.0 2010-2-2
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
 * <B>Lin</B>
 * 临
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-10-17 created
 * @since org.divine.app.liuyao.resolve.relationship Ver 1.0
 * 
 */
public class Lin extends AbstractRelationship{

	/**
	 * @param hight
	 * @param low
	 */
	public Lin(Item hight, Item low) {
		super(hight, low);
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "临"+hight;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#getLabel()
	 */
	@Override
	public String getLabel() {
		return "临"+getSourceLabel();
	}

	
	

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.relationship.IRelationship#isLowAvailable()
	 */
	@Override
	public boolean isLowAvailable() {
		return true;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.relationship.IRelationship#getStrength()
	 */
	@Override
	public int getStrength() {
		return hight.getStrength()*AbstractRelationship.StrengthLin;
	}

}
