/* SixBeings.java 1.0 2010-2-2
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
 * <B>SixBeings</B>
 * 六神
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-4-23 created
 * @since org.divine.app.liuyao Ver 1.0
 * 
 */
public class SixBeings extends Taiji {

	public static final int QING_LONG = 0;
	public static final int ZHU_QUE = 1;
	public static final int GOU_CHEN = 2;
	public static final int TENG_SHE = 3;
	public static final int BAI_HU = 4;
	public static final int XIAN_WU = 5;

	private static final SixBeings[] SixBeings = new SixBeings[] {
			new SixBeings(QING_LONG, "青龙"), new SixBeings(QING_LONG, "朱雀"),
			new SixBeings(QING_LONG, "勾陈"), new SixBeings(QING_LONG, "腾蛇"),
			new SixBeings(QING_LONG, "白虎"), new SixBeings(QING_LONG, "玄武") };

	/**
	 * @param value
	 * @param name
	 */
	protected SixBeings(int value, String name) {
		super(value, name);
	}

	public static  SixBeings getBeings(int value) {
		return SixBeings[value];
	}

	/**
	 * @param i
	 * @return
	 */
	public SixBeings next(int i) {
		return SixBeings[(value+i)%SixBeings.length];
	}
}
