/* AbstractItem.java 1.0 2010-2-2
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

import java.util.Iterator;

import org.divine.app.liuyao.EarthlyBranch;
import org.divine.app.liuyao.FiveElement;
import org.divine.app.liuyao.LiuYao;
import org.divine.app.liuyao.resolve.relationship.AbstractRelationship;
import org.divine.app.liuyao.resolve.relationship.IRelationship;
import org.zhiwu.utils.AbstractBean;

/**
 * <B>AbstractItem</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-8 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public abstract  class AbstractItem extends AbstractBean implements Item{
	/** 旺相程度   爻的五个层次*/
	protected final int power=5;
	
	/** 是否有生克权  */
//	protected boolean hasPower;
	
	protected int strength;
	
	private Level level;
	
	
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getStrength()
	 */
	@Override
	public int getStrength() {
		return strength;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#setStrength(org.divine.app.liuyao.resolve.IRelationship)
	 */
	@Override
	public void addRelationship(IRelationship iRelationship) {
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String getLabel() {
		return getLevel().getName();//+getBranch();
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getBranch()
	 */
	@Override
	public EarthlyBranch getBranch() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getFiveElement()
	 */
	@Override
	public FiveElement fiveElement() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getLevel()
	 */
	@Override
	public Level getLevel() {
		return level;
	}
	
	/**
	 * 
	 */
	public void setLevel(Level l) {
		this.level=l;
		initStrength();
	}
	

	/**
	 * 
	 */
	private void initStrength() {

		int v=level.getValue(); //value
		int p= v/8; // power @see Level
		
		switch (p) {
		case 0:
//			hasPower=true;
			setStrength( AbstractRelationship.StrengthPillar ); // 四柱
			break;
		case 1:
//			hasPower=true;
			setStrength( AbstractRelationship.StrengthChangedYao ); // 变爻
			break;
		case 2:
//			hasPower=true;
			setStrength( AbstractRelationship.StrengthChangeYao ); // 动爻
			break;
		case 3:
//			hasPower=true;
			setStrength( AbstractRelationship.StrengthYao ); // 静爻
			break;
		case 4:
//			hasPower=false;
			setStrength( AbstractRelationship.StrengthHideYao ); // 伏爻
			break;

		default:
			throw new RuntimeException();
		}
	}

	/**
	 * Sets the strength.
	 * 
	 * @param strength
	 *            the new strength
	 */
	public void setStrength(int strength) {
		this.strength=strength;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#strengthIteator()
	 */
	@Override
	public Iterator<IRelationship> strengthIteator() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#addRelation(org.divine.app.liuyao.resolve.Item)
	 */
	@Override
	public boolean addRelation(Item month) {
		return false;
	}

	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getLiuYao()
	 */
	@Override
	public LiuYao getLiuYao() {
		return null;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#hasRelationship(java.lang.Class, org.divine.app.liuyao.resolve.Level)
	 */
	@Override
	public boolean hasRelationship(Class<? extends IRelationship> c, Level level) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#hasRelationship(java.lang.Class)
	 */
	@Override
	public boolean hasRelationship(Class<? extends IRelationship> c) {
		return false;
	}
}
