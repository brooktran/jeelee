/* FiveElement.java 1.0 2010-2-2
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
package org.divine.app.liuyao;



/**
 * <B>FiveElement</B>
 * 五行
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-18 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public abstract class FiveElement extends Taiji {
	public static final String[] liveTable = { "长生", "沐浴", "冠带", "临官", "帝旺",
			"衰", "病", "死", "墓", "绝", "胎", "养" };
	
	public static final int GOLD=0;
	public static final int WATER=1;
	public static final int WOOD=2;
	public static final int FIRE=3;
	public static final int SOIL=4;
	
	/** 相生 */
	public static final int Generate = 0;
	/** 相克 */
	public static final int Restricte = 1;
	
	
	
	protected FiveElement(int v, String n) {
		super(v, n);
	}

	/**
	 * 生
	 * return true if other is generate by this.
	 * @param other
	 * @return
	 */
	public boolean isGenerate(FiveElement other) {
		return (value + 1) % 5 == other.value;
	}

	/**
	 * Checks if is restricte.
	 * 克
	 * @param other the other
	 * 
	 * @return true, if is restricte
	 */
	public boolean isRestricte(FiveElement other) {
		return (value + 2) % 5 == other.value;
	}

	/**
	 * 长生
	 * <<增删卜易>>只论生旺墓绝.
	 * @param branch
	 * @return
	 */
	public abstract boolean isChangSheng(EarthlyBranch branch);

	/**
	 * 帝旺
	 * <<增删卜易>>只论生旺墓绝.
	 * @param branch
	 * @return
	 */
	public abstract boolean isDiWang(EarthlyBranch branch);

	/**
	 * 墓
	 * <<增删卜易>>只论生旺墓绝.
	 * @param branch
	 * @return
	 */
	public abstract boolean isMu(EarthlyBranch branch);

	/**
	 * 绝
	 * <<增删卜易>>只论生旺墓绝.
	 * @param branch
	 * @return
	 */
	public abstract boolean isJue(EarthlyBranch branch);
	
	/**
	 * Gets the relationship of two element.
	 * 
	 * @param other the other
	 * 
	 * @return the relationship
	 */
	public SixRelatives getRelative(FiveElement other){
		if(other == null){
			return null;
		}
		
		if(isGenerate(other)){
			return SixRelatives.getRelatives(SixRelatives.SON);
		}
		if (other.isGenerate(this)) {
			return SixRelatives.getRelatives(SixRelatives.FATHER);
		}
		if(isRestricte(other)){
			return SixRelatives.getRelatives(SixRelatives.WIFE);
		}
		if(other.isRestricte(this)){
			return SixRelatives.getRelatives(SixRelatives.GHOST);
		}
		if (other.getValue()==value) {
			return SixRelatives.getRelatives(SixRelatives.BROTHER);
		}
		throw new IllegalArgumentException("is that a element ?");
	}


}
