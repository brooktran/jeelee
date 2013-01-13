/* SixCooprationTest.java 1.0 2010-2-2
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
import org.divine.app.liuyao.FourQuadrant;
import org.divine.app.liuyao.GanZhi;
import org.divine.app.liuyao.HeavenlyStem;
import org.divine.app.liuyao.Yao;
import org.divine.app.liuyao.resolve.Item;
import org.divine.app.liuyao.resolve.Level;
import org.divine.app.liuyao.resolve.Pillar;

/**
 * <B>SixCooprationTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-21 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class SixCooprationTest {
	public void testCoopration() throws Exception {
		Item source, month;
		Yao target;
		month = new Pillar(Level.MONTH_LEVEL, new GanZhi(HeavenlyStem
				.getHeavenlyStem(HeavenlyStem.GUI), EarthlyBranch.Wei));// 子
		source = new Pillar(Level.DAY_LEVEL, new GanZhi(HeavenlyStem
				.getHeavenlyStem(HeavenlyStem.GUI), EarthlyBranch.Zi));// 未
		target = new Yao(1, FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YANG));
		target.setBranch(EarthlyBranch.Chou);// 丑
		target.addRelation(month);
		target.addRelation(source);

		System.out.println(target + target.getRelationString());

	}
}
