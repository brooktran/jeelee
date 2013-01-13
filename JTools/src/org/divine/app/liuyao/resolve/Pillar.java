/* Pillar.java 1.0 2010-2-2
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

import org.divine.app.liuyao.EarthlyBranch;
import org.divine.app.liuyao.FiveElement;
import org.divine.app.liuyao.GanZhi;
import org.divine.app.liuyao.resolve.relationship.IRelationship;

/**
 * <B>Pillar</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-7 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class Pillar extends AbstractItem{
	private final GanZhi ganZhi;
	
	public Pillar (Level level,GanZhi ganZhi){
		setLevel(level);
		this.ganZhi=ganZhi;
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getFiveElement()
	 */
	@Override
	public FiveElement fiveElement() {
		return ganZhi.getFiveElement();
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getBranch()
	 */
	@Override
	public EarthlyBranch getBranch() {
		return ganZhi.getBranch();
	}
	
	public boolean checkEmpty(EarthlyBranch earthlyBranch){
		return ganZhi.isEmpty(earthlyBranch);
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#hasPower()
	 */
	@Override
	public boolean hasPower() {
		return true;
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#addItemListener(org.divine.app.liuyao.resolve.ItemListener)
	 */
	@Override
	public void addItemListener(ItemListener l) {
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getRelationship(java.lang.Class)
	 */
	@Override
	public IRelationship getRelationship(Class<? extends IRelationship> c) {
		return null;
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getRelationship(java.lang.Class, org.divine.app.liuyao.resolve.Level)
	 */
	@Override
	public IRelationship getRelationship(Class<? extends IRelationship> c,
			Level level) {
		// TODO Auto-generated method stub
		return null;
	}
}
