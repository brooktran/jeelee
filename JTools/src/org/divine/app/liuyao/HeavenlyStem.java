/* HeavenlyStem.java 1.0 2010-2-2
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
 * <B>HeavenlyStem</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-18 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public abstract class HeavenlyStem extends Taiji {
	private final FiveElement fiveElement;// 天干所属五行
	private static final String[] HeavenlyStemNames = new String[] { "甲", "乙",
			"丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
	public static final int JIA=0;
	public static final int YI=1;
	public static final int BING=2;
	public static final int DING=3;
	public static final int WU=4;
	public static final int JI=5;
	public static final int GENG=6;
	public static final int XIN=7;
	public static final int REN=8;
	public static final int GUI=9;


	protected HeavenlyStem(int v, String n, FiveElement f) {
		super(v, n);
		fiveElement = f;
	}

	public static HeavenlyStem parseString(String name) {
		if (name.length() != 1) {
			throw new IllegalArgumentException(name
					+ "  is illegal for the argument.");
		}

		for (int i = 0, j = HeavenlyStemNames.length; i < j; i++) {
			if (name.equals(HeavenlyStemNames[i])) {
				return TEN_STEM[i];
			}
		}

		return null;

	}
	
	public static HeavenlyStem getHeavenlyStem(int value){
		return TEN_STEM[value];
	}

	/**
	 * Gets the six beings.
	 * 六兽歌： 
	
	甲乙起青龙，丙丁起朱雀，戊日起勾陈，己日起螣蛇，庚辛起白虎，壬癸起玄武。 
	（生辟字注释：【螣】：读音：teng，二声。螣蛇，古书上记载的一种能飞的蛇。
	此字在卜筮的书籍中，也有写成【腾蛇】或者【縢蛇】的。另外，【玄武】有的书籍也写作【元武】，其意思都一样。） 

	 * @return the six beings
	 */
	public abstract SixBeings getSixBeings();

	public static final HeavenlyStem[] TEN_STEM = new HeavenlyStem[] { new Jia(),
			new Yi(), new Bing(), new Ding(), new Wu(), new Ji(), new Geng(),
			new Xin(), new Ren(), new Gui() };

	/**
	 * @return
	 */
	public HeavenlyStem next(int i) {
		return TEN_STEM[(value + i) % 10];
	}
}
