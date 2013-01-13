/* TestEightDiagrams.java 1.0 2010-2-2
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

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.divine.app.liuyao.AncientDiagrams;
import org.divine.app.liuyao.EarthlyBranch;
import org.divine.app.liuyao.EightDiagrams;
import org.divine.app.liuyao.FourQuadrant;
import org.divine.app.liuyao.Yao;
import org.divine.app.liuyao.resolve.Level;

/**
 * <B>TestEightDiagrams</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-31 created
 * @since org.divine.app.liuyao Ver 1.0
 * 
 */
public class TestEightDiagrams extends TestCase {

	public void testQian() throws Exception {
		try {
			AncientDiagrams qian = AncientDiagrams.getDiagrams(0);
			assertEquals(qian.getYao(0), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG));
			assertEquals(qian.getYao(1), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG));
			assertEquals(qian.getYao(2), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testParse() throws Exception {
		try {
			EightDiagrams qian = new EightDiagrams(new FourQuadrant[] {
					FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG),
					FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG),
					FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG) });
			qian.setup();
			assertEquals(qian.getSelf(), 5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testOpposition() throws Exception {
		EarthlyBranch zi = EarthlyBranch.Zi;
		assertTrue(zi.isOpposition(EarthlyBranch.Wu));
		assertTrue(EarthlyBranch.Wu.isOpposition(zi));

		assertTrue(EarthlyBranch.Yin.isOpposition(EarthlyBranch.Shen));

	}
	
	public void testHide() throws Exception {
		EightDiagrams diagrams = new EightDiagrams(new FourQuadrant[] {// 兑宫：水山蹇   2爻妻财伏
				FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YANG),
				FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YANG),
				FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), 
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG) });
		diagrams.setup();
		
		System.out.println(diagrams);
		
	}
	/** 兑宫：水山蹇 <br />
	* 子孙子水 --　-- 　 　　<br />
	* 父母戌土 ----- 　  <br />
	* 兄弟申金 --　-- 世 　　 <br />
	* 兄弟申金 ----- 　 　　<br />
	* 妻财卯木 官鬼午火 --　-- 　 　　<br />
	* 父母辰土 --　-- 应 　　<br /><br />
	* 兑宫：雷山小过 (游魂)　 　　<br />
	* 青龙 　　　　 父母戌土 --　--　 　　<br />
	* 玄武 　　　　 兄弟申金 --　-- 　 　　<br />
	* 白虎 子孙亥水 官鬼午火 ----- 世 　　<br /> 
	* 螣蛇 　　　　 兄弟申金 ----- 　  ○  　　<br />
	* 勾陈 妻财卯木 官鬼午火 --　-- 　 　　   　　<br />
	* 朱雀 　　　　 父母辰土 --　-- 应 　　   　　<br />
	@throws Exception the exception */
	public void testHideYaos() throws Exception {
		EightDiagrams diagrams = new EightDiagrams(new FourQuadrant[] {// 兑宫：水山蹇   2爻妻财伏
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN) });
		diagrams.setup();
//		System.out.println(diagrams);
		// String[] strings = LiuYao.parseDiagram(diagrams);
		// for (int i = strings.length - 1; i >= 0; i--) {
		// System.out.println(strings[i]);
		// }

		List<Yao> yaos = diagrams.getYaos(Level.HIDE_LEVEL);
		for (Iterator<Yao> iterator = yaos.iterator(); iterator.hasNext();) {
			Yao yao = iterator.next();
			System.out.println(yao.getIndex() + "爻 " + yao);
		}
		
		System.out.println();
		diagrams = new EightDiagrams(new FourQuadrant[] {// 雷山小过   2爻妻财伏  4爻子孙
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN) });
		diagrams.setup();
		
		yaos = diagrams.getYaos(Level.HIDE_LEVEL);
		for (Iterator<Yao> iterator = yaos.iterator(); iterator.hasNext();) {
			Yao yao = iterator.next();
			System.out.println(yao.getIndex() + "爻 " + yao);
		}
	}

	public static void main(String[] args) {
		try {
			TestRunner.run(TestEightDiagrams.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
