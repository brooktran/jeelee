/* LunarTest.java 1.0 2010-2-2
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

import org.divine.app.liuyao.EarthlyBranch;
import org.divine.app.liuyao.GanZhi;
import org.divine.app.liuyao.HeavenlyStem;
import org.divine.app.liuyao.Lunar;

import junit.framework.TestCase;
import junit.textui.TestRunner;

/**
 * <B>LunarTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-4-5 created
 * @since org.divine.app.liuyao Ver 1.0
 * 
 */
public class LunarTest extends TestCase {

	public void testCreate() throws Exception {
		Calendar today = Calendar.getInstance();
		Lunar lunar = new Lunar(today);
	}

	public void testStem() throws Exception {
		Calendar someDay = Calendar.getInstance();
		someDay.setTime(Lunar.CHINESE_TIME_FORMAT.parse("2010年4月5日22:10:00"));
		Lunar lunar = new Lunar(someDay);
		assertEquals(lunar.getSolarTerm(), "清明");
	}

	public void testTime() throws Exception {
		Calendar someDay = Calendar.getInstance();
		someDay.setTime(Lunar.CHINESE_TIME_FORMAT.parse("2010年4月6日14:25:11"));// 庚寅年
																				// 庚辰月
																				// 丙戌日
																				// 乙未时
		Lunar lunar = new Lunar(someDay);
		assertEquals(lunar.getLunarTime(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.YI),
				EarthlyBranch.Wei));

		someDay.setTime(Lunar.CHINESE_TIME_FORMAT.parse("2010年8月7日14:25:11"));// 庚寅年
																				// 癸未月
																				// 己丑日
																				// 辛未时
		lunar.setCalendar(someDay);
		assertEquals(lunar.getLunarTime(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.XIN),
				EarthlyBranch.Wei));

		someDay.setTime(Lunar.CHINESE_TIME_FORMAT.parse("2010年8月7日23:25:11"));// 庚寅年
																				// 甲申月
																				// 庚寅日
																				// 丙子时
		lunar.setCalendar(someDay);
		assertEquals(lunar.getLunarTime(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.BING),
				EarthlyBranch.Zi));

	}

	public void testGanZhi() throws Exception {
		try {
			Calendar someDay = Calendar.getInstance();
			someDay.setTime(Lunar.CHINESE_DATE_FORMAT.parse("2010年4月4日"));
			Lunar lunar = new Lunar(someDay);
			assertEquals(lunar.getLunarYear(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GENG),
					EarthlyBranch.Yin));
			assertEquals(lunar.getLunarMonth(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.JI),
					EarthlyBranch.Mao));

			someDay.setTime(Lunar.CHINESE_DATE_FORMAT.parse("2010年4月6日"));
			lunar.setCalendar(someDay);
			assertEquals(lunar.getLunarYear(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GENG),
					EarthlyBranch.Yin));
			assertEquals(lunar.getLunarMonth(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GENG),
					EarthlyBranch.Chen));
			assertEquals(lunar.getLunarDay(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.BING),
					EarthlyBranch.Xu));

			// 交节气换月
			someDay.setTime(Lunar.CHINESE_TIME_FORMAT
					.parse("2010年4月5日22:12:00"));
			lunar.setCalendar(someDay);
			assertEquals(lunar.getLunarYear(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GENG),
					EarthlyBranch.Yin));
			assertEquals(lunar.getLunarMonth(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GENG),
					EarthlyBranch.Chen));
			assertEquals(lunar.getLunarDay(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.YI),
					EarthlyBranch.You));

			// 节气交换 年变
			// someDay.setTime(Lunar.CHINESE_TIME_FORMAT
			// .parse("2010年2月2日10:12:00"));
			// lunar.setCalendar(someDay);
			// assertEquals(lunar.getLunarYear(), new GanZhi(HeavenlyStem.Ji,
			// EarthlyBranch.Chou));
			// assertEquals(lunar.getLunarMonth(), new GanZhi(HeavenlyStem.Ding,
			// EarthlyBranch.Chou));
			// assertEquals(lunar.getLunarDay(), new GanZhi(HeavenlyStem.Gui,
			// EarthlyBranch.Wei));

			// 节气交换 年变
			someDay.setTime(Lunar.CHINESE_TIME_FORMAT
					.parse("2010年8月8日00:25:11"));// 庚寅年 甲申月 庚寅日 丙子时
			lunar.setCalendar(someDay);
			assertEquals(lunar.getLunarYear(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GENG),
					EarthlyBranch.Yin));
			assertEquals(lunar.getLunarMonth(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.JIA),
					EarthlyBranch.Shen));
			assertEquals(lunar.getLunarDay(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GENG),
					EarthlyBranch.Yin));
			assertEquals(lunar.getLunarTime(), new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.BING),
					EarthlyBranch.Zi));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void testTurnToSolar() throws Exception {
		Calendar c = Lunar.lunarToSolar(2010, 2, 22, 21, 0, 0);
		assertEquals(c.get(Calendar.YEAR), 2010);
		assertEquals(c.get(Calendar.MONTH), 3);
		assertEquals(c.get(Calendar.DAY_OF_MONTH), 6);
		assertEquals(c.get(Calendar.HOUR_OF_DAY), 21);
		
		c = Lunar.lunarToSolar(2016, 7, 12, 21, 0, 0);
		assertEquals(c.get(Calendar.YEAR), 2016);
		assertEquals(c.get(Calendar.MONTH), 7);
		assertEquals(c.get(Calendar.DAY_OF_MONTH), 14);
		assertEquals(c.get(Calendar.HOUR_OF_DAY), 21);
	}
	
	public void testChineseNumber() throws Exception {
		assertEquals("一零", Lunar.getChineseNumber(10));
		assertEquals("一二三四五六七八九零", Lunar.getChineseNumber(1234567890));

	}

	public static void main(String[] args) {
		TestRunner.run(LunarTest.class);
	}
}
