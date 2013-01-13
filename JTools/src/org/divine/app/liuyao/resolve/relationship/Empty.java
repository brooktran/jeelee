/* Empty.java 1.0 2010-2-2
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
import org.divine.app.liuyao.resolve.Level;

/**
 * <B>Empty</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-10-12 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class Empty extends AbstractRelationship {

	/**
	 * @param hight
	 * @param low
	 */
	public Empty(Item hight, Item low) {
		super(hight, low);
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#validate()
	 */
	@Override
	protected void validate() {
		if(!hight.getLevel().isEqual(Level.Pillar_LEVEL)){
			available=false;
		}
		
		int v=low.getLevel().getValue();
		if(v == Level.YAO_LEVEL.getValue()){
			/*1、	静爻旬空，待冲静爻时，若静爻旺相有气，可发挥一半力量，
			 * 待出空时，才能正常发挥生克职能。
			*/
			
		}
		
	}

	/**
	 * 在旬空期间都无生克权，也不受其它爻的生与克，
	 * 只有出空、填实或者冲空时才有生行克权。
	 * @see org.divine.app.liuyao.resolve.IRelationship#isLowAvailable()
	 */
	@Override
	public boolean isLowAvailable() {
		return false;
	}
	
	@Override
	public String toString() {
		return "空";
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#getLabel()
	 */
	@Override
	public String getLabel() {
		return "空";
	}

}
