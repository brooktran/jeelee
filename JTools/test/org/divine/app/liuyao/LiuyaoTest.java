/* LiuyaoTest.java 1.0 2010-2-2
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

import java.util.Calendar;

import org.divine.app.liuyao.EightDiagrams;
import org.divine.app.liuyao.FourQuadrant;
import org.divine.app.liuyao.LiuYao;
import org.divine.app.liuyao.Lunar;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <B>LiuyaoTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-6-5 created
 * @since org.divine.app.liuyao Ver 1.0
 * 
 */
public class LiuyaoTest extends TestCase{
	
	public void testSixBings() throws Exception {
		EightDiagrams diagrams = new EightDiagrams("000000");
		Calendar c=Calendar.getInstance();
		Lunar lunar=new Lunar(c);

		diagrams.setup();
		c.setTime(Lunar.CHINESE_TIME_FORMAT
				.parse("2010年6月3日23:25:11"));//庚寅年、辛已月、甲申日 2010年06月07日
		lunar.setCalendar(c);
		LiuYao liuyao=new LiuYao(lunar, diagrams);
		System.out.println(lunar);
		
	}
	
	@Deprecated
	public void testPaipan() throws Exception {
		EightDiagrams diagrams = new EightDiagrams(new FourQuadrant[] {// 兑宫：水山蹇   2爻妻财伏
				FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YANG),
				FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YANG),
				FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), 
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG) });
		diagrams.setup();
		
		Calendar c=Calendar.getInstance();
		Lunar lunar=new Lunar(c);
		c.setTime(Lunar.CHINESE_TIME_FORMAT
				.parse("2010年6月3日23:25:11"));//庚寅年、辛已月、甲申日 2010年06月07日
		LiuYao liuyao=new LiuYao(lunar, diagrams);
		
		for(int i=liuyao.paiPan().length;i>=0;i--){
			System.out.println(liuyao.paiPan()[i]);
		}
		
	}

	public static void main(String[] args) {
		TestRunner.run(LiuyaoTest.class);
	}
}
