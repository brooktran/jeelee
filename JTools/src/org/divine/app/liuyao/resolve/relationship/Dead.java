/* Dead.java 1.0 2010-2-2
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
 * <B>Dead</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-8 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class Dead extends AbstractRelationship {

	/**
	 * @param hight
	 * @param strength
	 */
	public Dead(Item source , Item to) {
		super(source,to);
	}
	
	@Override
	public String getLabel() {
		return "死在"+getSourceLabel();
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.IRelationship#isLowAvailable()
	 */
	@Override
	public boolean isLowAvailable() {
		return true;
	}

}
