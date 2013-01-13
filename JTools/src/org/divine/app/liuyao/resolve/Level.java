/* Level.java 1.0 2010-2-2
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

import org.divine.app.liuyao.Taiji;

/**
 * <B>Level</B>
 * The monthe,day is the first level, and the change Yao is the second.
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-7 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 */
public class Level extends Taiji{ // 0~7 means the first level; 8~17 means the second level;
	
	/** 所有层次 */
	 public static final Level All_LEVEL = new Level(-1,"所有层次");
	 
	 
////////////////////////
	/** 四柱*/
	 public static final Level Pillar_LEVEL = new Level(0,"四柱");
	 
	 
	/** 年 */
 public static final Level YEAR_LEVEL = new Level(1,"年");
	
	/** 月*/
	public static final Level MONTH_LEVEL = new Level(2,"月");
	
	/** 日 */
	public static final Level DAY_LEVEL = new Level(3,"日");
	
	/** 时 */
	public static final Level HOUR_LEVEL = new Level(4,"时");
	
	/** The Constant MINUTE_LEVEL. */
	public static final Level MINUTE_LEVEL = new Level(5,"刻");
	
//////////////
	/** 变爻 */
	public static final Level CHANGED_LEVEL = new Level(8,"变爻");

/////////////////
	/** 动爻  */
	public static final Level CHANGE_LEVEL = new Level(16,"动爻");
	
	/**  静爻	 */
	public static final Level YAO_LEVEL =new Level(24,"静爻");
	

	/**  伏爻	 */
	public static final Level HIDE_LEVEL =new Level(32,"伏爻");

	/**
	 * @param i
	 */
	public Level(int value,String name) {
		super(value,name);
		
		
		
	}

	/**
	 * Checks if is greater than other level.
	 * 
	 * @param other the other level
	 * 
	 * @return true, if is greater
	 */
	public boolean isGreater(Level other) {
		return (value / 8) < (other.value / 8);
	}

	public boolean isEqual(Level other){
		return (value/8) == (other.value /8);
	}
	public int getLevelIndex(){
		return value/8;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.Taiji#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
		if (!(other instanceof Level)) {
			return false;
		}
		
		Level newLevel = ((Level) other);
		if (newLevel.getValue() == All_LEVEL.getValue()) {//  所有层次
			return true;
		}
		return newLevel.value == value;
	}
}
