/*
 * AbstractRelationship.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran All rights reserved.
 * 
 * The copyright of this software is own by the authors. You may not use, copy
 * or modify this software, except in accordance with the license agreement you
 * entered into with the copyright holders. For details see accompanying license
 * terms.
 */
package org.divine.app.liuyao.resolve.relationship;

import java.util.ArrayList;
import java.util.List;

import org.divine.app.liuyao.resolve.Item;

/**
 * <B>AbstractRelationship</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-8 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public abstract class AbstractRelationship implements IRelationship {

	/** 克的力量 */
	public static final double StrengthRestricte = .5;
	/** 生的力量 */
	public static final double StrengthGenerate = .5;

	/** 四柱初始力量 */
	public static final int StrengthPillar = 100;

	/** 变爻初始力量 */
	public static final int StrengthChangedYao = 60;

	/** 动爻初始力量 */
	public static final int StrengthChangeYao = 0;

	/** 静爻初始力量 */
	public static final int StrengthYao = 0;
	/** 伏爻初始力量 */
	public static final int StrengthHideYao = -20;
	/** 临 力量 */
	public static final int StrengthLin = 50;

	// protected IRelationship strength;
//	protected boolean isEffective;
	protected Item hight;
	protected Item low;

	/** 表示是否有效.如子丑合,但丑被冲,故子丑合为无效. */
	protected boolean available;

	protected List<Item> invalid;

	/**
	 * @param from
	 */
	public AbstractRelationship(Item hight, Item low) {
		this.low = low;
		this.hight = hight;
		// this.strength=strength;
		init();
	}

	/**
	 * 
	 */
	protected void init() {
		available=true;
		invalid = new ArrayList<Item>(0);
		
		validate();
	}

	/**
	 * 是否成立
	 */
	protected void validate() {
		if (!hight.hasPower()) {
			available = false;
		}
	}

	protected String getSourceLabel() {
		return hight.getLabel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.resolve.IRelationship#getLabel()
	 */
	@Override
	public String getLabel() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.resolve.IRelationship#getSource()
	 */
	@Override
	public Item getSource() {
		return hight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.resolve.IRelationship#getTarget()
	 */
	@Override
	public Item getTarget() {
		return low;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.resolve.IRelationship#available()
	 */
	@Override
	public boolean available() {
		return available;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.resolve.IRelationship#setAvailable(boolean)
	 */
	public void setAvailable(boolean b) {
//		available = available ? b : available;// TODO 当为false,则不能变
		available=b;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.IRelationship#getHight()
	 */
	@Override
	public Item getHight() {
		return hight;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.relationship.IRelationship#getStrength()
	 */
	@Override
	public int getStrength() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.IRelationship#isLowAvailable()
	 */
//	public boolean isLowAvailable() {
//		return true;
//	}
}
