/* ItemEvent.java 1.0 2010-2-2
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
package org.divine.app.liuyao.resolve;

import java.util.EventObject;

import org.divine.app.liuyao.resolve.relationship.IRelationship;

/**
 * <B>ItemEvent</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-10-12 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class ItemEvent extends EventObject{
	private final IRelationship r;

	/**
	 * @param yao
	 * @param r
	 */
	public ItemEvent(Item item, IRelationship r) {
		super(item);
		this.r=r;
	
	}

	/**
	 * @return
	 */
	public IRelationship getRelationsip() {
		return r;
	}

}
