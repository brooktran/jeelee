/* SixOpposition.java 1.0 2010-2-2
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
 * <B>SixOpposition</B>
 * 六冲
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-8 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class SixOpposition extends AbstractRelationship {
	
	/** 冲 生. */
	private boolean isGenerate;
	/**
	 * 冲克
	 */
	private boolean isRestricte;

	/**
	 * @param hight
	 * @param strength
	 */
	public SixOpposition(Item hight, Item low) {
		super(hight,low);
		
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#init()
	 */
	@Override
	protected void init() {
		// effective
		
		
		
		
		
		
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "冲于"+hight;
	}
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#getLabel()
	 */
	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "被"+getSourceLabel()+"冲";
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.IRelationship#isLowAvailable()
	 */
	@Override
	public boolean isLowAvailable() {
		// TODO Auto-generated method stub
		return false;
	}
}