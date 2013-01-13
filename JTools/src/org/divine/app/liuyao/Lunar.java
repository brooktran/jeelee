/* Lunar.java 1.0 2010-2-2
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <B>Lunar</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-24 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public class Lunar {
	public static final String[] Gan = new String[] { "甲", "乙", "丙", "丁", "戊",
			"己", "庚", "辛", "壬", "癸" };
	public static final String[] Zhi = new String[] { "子", "丑", "寅", "卯", "辰",
			"巳", "午", "未", "申", "酉", "戌", "亥" };

	public final static String chineseNumber[] = { "零", "一", "二", "三", "四",
			"五", "六", "七", "八", "九", "十", "十一", "十二" };
	public static final SimpleDateFormat CHINESE_DATE_FORMAT = new SimpleDateFormat(
			"yyyy年MM月dd日");
	public static final SimpleDateFormat CHINESE_TIME_FORMAT = new SimpleDateFormat(
			"yyyy年MM月dd日HH:mm:ss");

	private static final String[] weekString = { "日", "一", "二", "三", "四", "五",
			"六" };
	private static final int[] solarMonth = { 31, 28, 31, 30, 31, 30, 31, 31,
			30, 31, 30, 31 };
	private static final String[] Animals = { "鼠", "牛", "虎", "兔", "龙", "蛇",
			"马", "羊", "猴", "鸡", "狗", "猪" };
	private static final String[] solarTerm = { "小寒", "大寒", "立春", "雨水", "惊蛰",
			"春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至", "小暑", "大暑", "立秋", "处暑",
			"白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };
	private static final int[] sTermInfo = { 0, 21208, 42467, 63836, 85337,
			107014, 128867, 150921, 173149, 195551, 218072, 240693, 263343,
			285989, 308563, 331033, 353350, 375494, 397447, 419210, 440795,
			462224, 483532, 504758 };

	// 国历节日 *表示放假日
	private static final String[] sFtv = { "0101*元旦", "0214 情人节", "0308 妇女节",
			"0312 植树节", "0314 国际警察日", "0315 消费者权益日", "0323 世界气象日", "0401 愚人节",
			"0407 世界卫生日", "0501*劳动节", "0504 青年节", "0508 红十字日", "0512 护士节",
			"0515 国际家庭日", "0517 世界电信日", "0519 全国助残日", "0531 世界无烟日", "0601 儿童节",
			"0605 世界环境日", "0606 全国爱眼日", "0623 奥林匹克日", "0625 全国土地日",
			"0626 反毒品日", "0701 建党节", "0707 抗战纪念日", "0711 世界人口日", "0801 建军节",
			"0908 国际扫盲日", "0909 毛泽东逝世纪念", "0910 教师节", "0917 国际和平日",
			"0920 国际爱牙日", "0922 国际聋人节", "0927 世界旅游日", "0928 孔子诞辰", "1001*国庆节",
			"1004 世界动物日", "1006 老人节", "1007 国际住房日", "1009 世界邮政日", "1015 国际盲人节",
			"1016 世界粮食日", "1024 联合国日", "1031 万圣节", "1108 中国记者日", "1109 消防宣传日",
			"1112 孙中山诞辰", "1114 世界糖尿病日", "1117 国际大学生节", "1128 感恩节",
			"1201 世界艾滋病日", "1203 世界残疾人日", "1209 世界足球日", "1220 澳门回归",
			"1225 圣诞节", "1226 毛泽东诞辰" };

	// 农历节日 *表示放假日
	private static final String[] lFtv = { "0101*春节、弥勒佛圣诞!", "0106  定光佛圣诞",
			"0115  元宵节", "0208  释迦牟尼佛出家", "0215  释迦牟尼佛涅盘", "0209  海空上师生日!",
			"0219  观世音菩萨圣诞", "0221  普贤菩萨圣诞", "0316  准提菩萨圣诞", "0404  文殊菩萨圣诞",
			"0408  释迦牟尼佛圣诞", "0415  佛吉祥日——释迦牟尼佛诞生、成道、涅盘三期同一庆(即南传佛教国家的卫塞节)",
			"0505  端午节", "0513  伽蓝菩萨圣诞", "0603  护法韦驮尊天菩萨圣诞",
			"0619  观世音菩萨成道——此日放生、念佛，功德殊胜", "0707  七夕情人节", "0713  大势至菩萨圣诞",
			"0715  中元节", "0724  龙树菩萨圣诞", "0730  地藏菩萨圣诞", "0815  中秋节",
			"0822  燃灯佛圣诞", "0909  重阳节", "0919  观世音菩萨出家纪念日", "0930  药师琉璃光如来圣诞",
			"1005  达摩祖师圣诞", "1107  阿弥陀佛圣诞", "1208  释迦如来成道日,腊八节", "1224  小年",
			"1229  华严菩萨圣诞" };

	// 某月的第几个星期几
	private static final String[] wFtv = { "0521 母亲节", "0717 合作节", "0631 父亲节",
			"0731 被奴役国家周" };

	/**   日期资料 */
	private static final int[] LUNAR_INFO = { 0x04bd8, 0x04ae0, 0x0a570,
			0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0, 0x055d2,
			0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0,
			0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5, 0x06a50,
			0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566,
			0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0,
			0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0, 0x1a5b4,
			0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550,
			0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8, 0x0e950,
			0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260,
			0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0,
			0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
			0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40,
			0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0, 0x074a3,
			0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960,
			0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7, 0x025d0,
			0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9,
			0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0,
			0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260, 0x0ea65,
			0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0,
			0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0, 0x055b2,
			0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0 };

	// Array iSolarLunarTable stored the offset days
	// in New Year of solar calendar and lunar calendar from 1901 to 2050;
	private static final char[] SolarLunarOffsetTable = { 49, 38, 28, 46, 34,
			24, 43, 32, 21, 40, // 1910
			29, 48, 36, 25, 44, 34, 22, 41, 31, 50, // 1920
			38, 27, 46, 35, 23, 43, 32, 22, 40, 29, // 1930
			47, 36, 25, 44, 34, 23, 41, 30, 49, 38, // 1940
			26, 45, 35, 24, 43, 32, 21, 40, 28, 47, // 1950
			36, 26, 44, 33, 23, 42, 30, 48, 38, 27, // 1960
			45, 35, 24, 43, 32, 20, 39, 29, 47, 36, // 1970
			26, 45, 33, 22, 41, 30, 48, 37, 27, 46, // 1980
			35, 24, 43, 32, 50, 39, 28, 47, 36, 26, // 1990
			45, 34, 22, 40, 30, 49, 37, 27, 46, 35, // 2000
			23, 42, 31, 21, 39, 28, 48, 37, 25, 44, // 2010
			33, 23, 41, 31, 50, 39, 28, 47, 35, 24, // 2020
			42, 30, 21, 40, 28, 47, 36, 25, 43, 33, // 2030
			22, 41, 30, 49, 37, 26, 44, 33, 23, 42, // 2040
			31, 21, 40, 29, 47, 36, 25, 44, 32, 22, // 2050
	};

	public static final int LAST_YEAR = 2050;
	public static final int BASE_YEAR = 1900;

	/**传回农历 y年的总天数
	 * @param year
	 */
	public static int daysOfYear(int year) {
		int sum = 348;// 29 * 12 =348
		for (int i = 0x8000; i > 0x8; i >>= 1) {
			if ((LUNAR_INFO[year - BASE_YEAR] & i) != 0)
				sum += 1;
		}

		return sum + leapDays(year);
	}

	/**传回农历 y年闰月的天数
	 * @param year
	 * @return
	 */
	public static int leapDays(int year) {
		if (leapMonth(year) != 0) {
			if ((LUNAR_INFO[year - BASE_YEAR] & 0x10000) != 0) {
				return 30;
			} else {
				return 29;
			}
		}
		return 0;
	}

	/**	传回农历 y年闰哪个月 1-12 , 没闰传回 0 
	 * @param y
	 * @return
	 */
	final public static int leapMonth(int y) {
		return (LUNAR_INFO[y - BASE_YEAR] & 0xf);
	}

	/** 传回农历 y年m月的总天数 
	 * @param y
	 * @param m
	 * @return
	 */
	final public static int lunarMonthDays(int y, int m) {
		if ((LUNAR_INFO[y - BASE_YEAR] & (0x10000 >> m)) == 0)
			return 29;
		else
			return 30;
	}

	/** 传回国历 y年某m+1月的天数
	 * @param year
	 * @param month
	 * @return
	 */
	public static final int solarMonthDays(int y, int m) {
		if (m == 1)
			return (((y % 4 == 0) && (y % 100 != 0) || (y % 400 == 0)) ? 29
					: 28);
		else
			return (solarMonth[m]);
	}

	/** 阳历某年是否闰年*/
	public static boolean isSolarLeap(int iYear) {
		return ((iYear % 4 == 0) && (iYear % 100 != 0) || iYear % 400 == 0);
	}

	// The lunar calendar is turned into the Solar calendar
	public static Calendar lunarToSolar(int year, int month, int date,
			int hourOfDay, int minute, int second) {
		int sYear, sMonth, sDay;
		int offset = iGetLNewYearOffsetDays(year, month, date)
				+ SolarLunarOffsetTable[year - 1901];
		int iYearDays = isSolarLeap(year) ? 366 : 365;
		if (offset >= iYearDays) {
			sYear = year + 1;
			offset -= iYearDays;
		} else {
			sYear = year;
		}
		sDay = offset + 1;
		for (sMonth = 1; offset >= 0; sMonth++) {
			sDay = offset + 1;
			offset -= solarMonthDays(sYear, sMonth);
		}
		sMonth--;
		Calendar calendar = Calendar.getInstance();
		calendar.set(sYear, sMonth - 1, sDay - 1, hourOfDay, minute, second);
		return calendar;
	}

	private static int iGetLNewYearOffsetDays(int iYear, int iMonth, int iDay) {
		int iOffsetDays = 0;
		int iLeapMonth = leapMonth(iYear);
		if ((iLeapMonth > 0) && (iLeapMonth == iMonth - 12)) {
			iMonth = iLeapMonth;
			iOffsetDays += lunarMonthDays(iYear, iMonth);
		}
		for (int i = 1; i < iMonth; i++) {
			iOffsetDays += lunarMonthDays(iYear, i);
			if (i == iLeapMonth)
				iOffsetDays += lunarMonthDays(iYear, iLeapMonth + 12);
		}
		iOffsetDays += iDay - 1;
		return iOffsetDays;
	}

	// ====== 传入 offset 传回干支, 0=甲子
	public String cyclical(int num) {
		return (Gan[num % 10] + Zhi[num % 12]);
	}

	// ====== 传回农历 y年的生肖
	public String animalsYear(int year) {
		final String[] Animals = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇",
				"马", "羊", "猴", "鸡", "狗", "猪" };
		return Animals[(year - 4) % 12];
	}

	// 阳历
	private Calendar calendar;
	// 国历
	// private int year;
	// private int month;
	// private int day;
	// private int time;

	public static final int LUNAR_YEAR = Calendar.FIELD_COUNT + 0;
	public static final int LUNAR_MONTH = Calendar.FIELD_COUNT + 1;
	public static final int LUNAR_DAY = Calendar.FIELD_COUNT + 2;

	public static final int FIEL_COUNT = Calendar.FIELD_COUNT + 3;
	public static final int[] FIELDS = new int[FIEL_COUNT];

	// private final int week;
	// 农历干支
	private GanZhi cYear;
	private GanZhi cMonth;
	private GanZhi cDay;
	private GanZhi cTime;
	private boolean leap;
	/**当前节气*/
	private int currentTerm;
	/**节气准确时间*/
	// private long termTime;
	/** 是否以节气换月*/
	private final boolean isTerm;
	/**农历节日*/
	private String lunarFestival = "";
	/**公历节日*/
	private String solarFestival = "";

	/**  
	* 传出y年m月d日对应的农历.  
	* yearCyl3:农历年与1864的相差数              ?  
	* monCyl4:从1900年1月31日以来,闰月数  
	* dayCyl5:与1900年1月31日相差的天数,再加40      ?  
	* @param cal   
	* @return   
	*/
	public Lunar(Calendar calendar) {
		isTerm = true;
		setCalendar(calendar);
	}

	/**
	 * 
	 * Use 24 hour of the
	 * @param i
	 */
	private void setTime(int t) {
		GanZhi cDay= this.cDay.next(0);// copy
		if (t >= 23) {
			cDay = cDay.next(1);
		}

		// 甲己还加甲，乙庚丙作初，丙辛从戊起，丁壬庚子居，戊癸何方发，壬子是顺行
		if (cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem( HeavenlyStem.JIA))
				|| cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem(HeavenlyStem.JI))) {// 甲己还加甲
			cTime.setSterm(HeavenlyStem.getHeavenlyStem(HeavenlyStem.JIA));
		} else if (cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem(HeavenlyStem.YI))
				|| cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GENG))) {
			cTime.setSterm(HeavenlyStem.getHeavenlyStem(HeavenlyStem.BING));
		} else if (cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem(HeavenlyStem.BING))
				|| cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem(HeavenlyStem.XIN))) {
			cTime.setSterm(HeavenlyStem.getHeavenlyStem(HeavenlyStem.WU));
		} else if (cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem(HeavenlyStem.DING))
				|| cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem(HeavenlyStem.REN))) {
			cTime.setSterm(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GENG));
		} else if (cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem(HeavenlyStem.WU))
				|| cDay.getSterm().equals(HeavenlyStem.getHeavenlyStem(HeavenlyStem.GUI))) {
			cTime.setSterm(HeavenlyStem.getHeavenlyStem(HeavenlyStem.REN));
		}
		int offSet = ((calendar.get(Calendar.HOUR_OF_DAY) + 1) % 24) / 2;
		cTime.setBranch(EarthlyBranch.getbranch(offSet));
		cTime.setSterm(cTime.getSterm().next(offSet));
	}

	/** 某年的第n个节气为几日(从0小寒起算)
	 * @param year
	 * @param n month*2 or month*2+1
	 * @return
	 */
	private int sTerm(int year, int n) {
		Calendar c = Calendar.getInstance();
		c.set(BASE_YEAR, 0, 6, 2, 5, 0);
		// c.setTimeInMillis(-2208549300000L);
		// 31556925974.7为地球公转周期，是毫秒。（如果换算成天，是265.242199天，
		// 所以每四百年就有97个闰年，同样，我们也可以看到，如果这样做的话，
		// 一百三十万年后，我们的公历算法将有一天的误差）;
		// ( 31556925974.7*(y-1900) + sTermInfo[n]*60000
		// )----表示y年的第n个节气点（15倍数度点）距1900年的小寒点的毫秒数。
		// Date.UTC(1900,0,6,2,5)----表示1900年一月六日两点五分是正小寒点。
		// 以上两者相加得到的是：y年的第n个节气点时点的int值
		c
				.setTimeInMillis((long) (31556925974.7 * (year - BASE_YEAR) + sTermInfo[n] * 60000L)
						+ c.getTime().getTime());
		return c.get(Calendar.DAY_OF_YEAR);
	}

	public static String getChinaDayString(int day) {
		String chineseTen[] = { "初", "十", "廿", "卅" };
		int n = day % 10 == 0 ? 10 : day % 10;
		if (day > 30)
			return "";
		if (day == 10)
			return "初十";
		else
			return chineseTen[day / 10] + chineseNumber[n];
	}

	/**
	 * 用于年
	 * @param value value must large than 0.
	 * @return
	 */
	public static String getChineseNumber(int value) {
		int p;
		StringBuffer sb = new StringBuffer();
		while (value > 0) {
			p = value % 10;
			sb.insert(0, chineseNumber[p]);
			value /= 10;
		}
		return sb.toString();
	}

	

	/**
	 * @param calendar
	 */
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
		reflesh();
	}

	/**
	 * @param field
	 * @param value
	 */
	public void set(int field, int value) {
		if (field < Calendar.FIELD_COUNT) {
			calendar.set(field, value);
		} else {
			FIELDS[field - Calendar.FIELD_COUNT] = value;
			calendar = lunarToSolar(get(LUNAR_YEAR), get(LUNAR_MONTH),
					get(LUNAR_DAY), calendar.get(Calendar.HOUR_OF_DAY),
					calendar.get(Calendar.MINUTE), calendar
							.get(Calendar.SECOND));

		}
		reflesh();

	}

	/**
	 * 
	 */
	private void reflesh() {
		int leapMonth = 0;
		Date baseDate = null;
		try {
			baseDate = CHINESE_DATE_FORMAT.parse("1900年1月31日");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/** 干支年月日偏移量:经过纠正,仅用于干支 */
		int yearCyl;
		int monCyl;
		int dayCyl;

		// 求出和1900年1月31日相差的天数
		int offset = (int) ((calendar.getTime().getTime() - baseDate.getTime()) / 86400000L);
		dayCyl = offset + 40;
		monCyl = 14;

		// 用offset减去每农历年的天数
		// 计算当天是农历第几天
		// i最终结果是农历的年份
		// offset是当年的第几天
		int iYear, daysOfYear = 0;
		for (iYear = BASE_YEAR; iYear < LAST_YEAR && offset > 0; iYear++) {
			daysOfYear = daysOfYear(iYear);
			offset -= daysOfYear;
			monCyl += 12;
		}
		if (offset < 0) {
			offset += daysOfYear;
			iYear--;
			monCyl -= 12;
		}
		// 农历年份
		FIELDS[LUNAR_YEAR - Calendar.FIELD_COUNT] = iYear;// year = iYear;

		yearCyl = iYear - 1864;// 甲子
		leapMonth = leapMonth(iYear); // 闰哪个月,1-12
		leap = false;

		// 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
		int iMonth, daysOfMonth = 0;
		for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
			// 闰月
			if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
				--iMonth;
				leap = true;
				daysOfMonth = leapDays(get(LUNAR_YEAR));
			} else
				daysOfMonth = lunarMonthDays(get(LUNAR_YEAR), iMonth);

			offset -= daysOfMonth;
			// 解除闰月
			if (leap && iMonth == (leapMonth + 1))
				leap = false;
			if (!leap)
				monCyl++;
		}
		// offset为0时，并且刚才计算的月份是闰月，要校正
		if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
			if (leap) {
				leap = false;
			} else {
				leap = true;
				--iMonth;
				--monCyl;
			}
		}
		// offset小于0时，也要校正
		if (offset < 0) {
			offset += daysOfMonth;
			--iMonth;
			--monCyl;
		}
		FIELDS[LUNAR_MONTH - Calendar.FIELD_COUNT] = iMonth;// month = iMonth;
		FIELDS[LUNAR_DAY - Calendar.FIELD_COUNT] = offset + 1;// day = offset +
																// 1;

		// 计算干支
		cYear = GanZhi.parseString(cyclical(yearCyl));
		cMonth = GanZhi.parseString(cyclical(monCyl));
		cDay = GanZhi.parseString(cyclical(dayCyl));
		// 
		cTime = new GanZhi();
		setTime(calendar.get(Calendar.HOUR_OF_DAY));

		// 计算节气
		for (currentTerm = 1; currentTerm < 25; currentTerm++) {
			int termTime = sTerm(get(LUNAR_YEAR), currentTerm);
			if (termTime == calendar.get(Calendar.DAY_OF_YEAR))
				break;
			else if (termTime > calendar.get(Calendar.DAY_OF_YEAR)) {
				--currentTerm;
				break;
			}
		}
		// "农历(月): " + currentTerm / 2+"\n"
		if (isTerm) {
			if (cMonth.getBranch().getValue() != EarthlyBranch.getbranch(
					currentTerm / 2 + 1).getValue()) {
				cMonth = cMonth.next(1);
				if (cMonth.getBranch().equals(EarthlyBranch.Yin)) {
					cYear = cYear.next(1);
				}
			}
		}

		// 公历节日
		for (int i = 0; i < sFtv.length; i++) {
			if (Integer.parseInt(sFtv[i].substring(0, 2)) == calendar
					.get(Calendar.MONTH) + 1
					&& Integer.parseInt(sFtv[i].substring(2, 4)) == calendar
							.get(Calendar.DAY_OF_MONTH)) {
				solarFestival = sFtv[i].substring(5);

			}
		}

		// 农历节日
		for (int i = 0; i < lFtv.length; i++) {
			if (Integer.parseInt(lFtv[i].substring(0, 2)) == get(LUNAR_MONTH)
					&& Integer.parseInt(lFtv[i].substring(2, 4)) == get(LUNAR_DAY)) {
				lunarFestival = lFtv[i].substring(5);
			}
		}

		// 月周节日
		for (int i = 0; i < wFtv.length; i++) {
			if (Integer.parseInt(wFtv[i].substring(0, 2)) == calendar
					.get(Calendar.MONTH) + 1
					&& Integer.parseInt(wFtv[i].substring(2, 3)) == calendar
							.get(Calendar.WEEK_OF_MONTH) - 1
					&& Integer.parseInt(wFtv[i].substring(3, 4)) == calendar
							.get(Calendar.DAY_OF_WEEK)) {
				solarFestival += wFtv[i].substring(5);
			}
		}
	}

	/**
	 * @return
	 */
	public GanZhi getLunarDay() {
		if (get(Calendar.HOUR_OF_DAY) >= 23) {
			return cDay.next(1);
		}
		return cDay;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * @return
	 */
	public GanZhi getLunarTime() {
		return cTime;
	}
//
//	public static void main(String[] args) throws ParseException {
//		Calendar today = Calendar.getInstance();
//		today.setTime(CHINESE_TIME_FORMAT.parse("2010年8月23日12:59:00"));
//		Lunar lunar = new Lunar(today);
//
//		// int term = lunar.sTerm(today.get(Calendar.YEAR), today
//		// .get(Calendar.MONTH) * 2);
//		// System.out.println(sTermInfo[today.get(Calendar.MONTH) * 2]);
//
//		System.out.println("北京时间："
//				+ CHINESE_DATE_FORMAT.format(today.getTime()) + "　农历" + lunar);
//
//		// System.out.println(lunar.cyclical(lunar.yearCyl));
//		// System.out.println(lunar.cyclical(lunar.monCyl));
//		// System.out.println(lunar.cyclical(lunar.dayCyl));
//		// System.out.println(lunar.cYear);
//		// System.out.println(lunar.cMonth);
//		// System.out.println(lunar.cDay);
//
//		EarthlyBranch[] emptys = lunar.cDay.getEmpty();
//		for (int i = 0, j = emptys.length; i < j; i++) {
//			// System.out.println(emptys[i]);
//		}
//
//	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(CHINESE_TIME_FORMAT.format(calendar.getTime()) + ", 农历:");
		sb.append(getChineseNumber(get(LUNAR_YEAR)) + "年 ");
		sb.append((leap ? "闰" : "") + chineseNumber[get(LUNAR_MONTH)] + "月 ");
		sb.append(getChinaDayString(get(LUNAR_DAY)) + "\n");

		sb.append(getLunarYear() + " " + getLunarMonth() + " " + getLunarDay() + " " + getLunarTime() + "\n");
		sb.append("农历(月): " + ((currentTerm / 2 == 0) ? 12 : (currentTerm / 2))
				+ "\n");
		sb.append("节气 " + solarTerm[currentTerm] + "\n");
		sb.append(lunarFestival + "\n");
		sb.append(solarFestival + "\n");
		sb.append("星期" + weekString[calendar.get(Calendar.DAY_OF_WEEK) - 1]
				+ "\n");

		return sb.toString();
	}
	
	public String getLunarString(){
		return getChineseNumber(get(LUNAR_YEAR)) + "年 "+
		(leap ? "闰" : "") + chineseNumber[get(LUNAR_MONTH)] + "月 "+
		getChinaDayString(get(LUNAR_DAY));
	}

	public int get(int field) {
		if (field < Calendar.FIELD_COUNT) {
			return calendar.get(field);
		}
		return FIELDS[field - Calendar.FIELD_COUNT];
	}

	/**
	 * 
	 */
	public String getSolarTerm() {
		return solarTerm[currentTerm];
	}

	/**
	 * @return
	 */
	public GanZhi getLunarYear() {
		if (get(Calendar.HOUR_OF_DAY) >= 23) {
			// month
			int term = sTerm(get(LUNAR_YEAR), currentTerm);
			if (calendar.get(Calendar.DAY_OF_YEAR) + 86400000 > term) {
				GanZhi cMonth = this.cMonth.next(1);
				if(cMonth.getBranch().getValue()==EarthlyBranch.YIN){
					return cYear.next(1);
				}
				// year
//				if (currentTerm == 0) {// TODO it may be wrong
//				}
			}
		}
		return cYear;
	}

	/**
	 * @return
	 */
	public GanZhi getLunarMonth() {
		if (get(Calendar.HOUR_OF_DAY) >= 23) {
			// month
			int term = sTerm(get(LUNAR_YEAR), currentTerm);
			if (calendar.get(Calendar.DAY_OF_YEAR) + 86400000 > term) {
				return  cMonth.next(1);
			}
		}
		return cMonth;
	}
	
	/**
	 * @return
	 */
	public String getLunarTerm() {
		return solarTerm[currentTerm];
	}

	/**
	 * @return
	 */
	public String getEmptyString() {
		String emptyString="";
		EarthlyBranch[] emptys = cDay.getEmpty();
		for (int i = 0, j = emptys.length; i < j; i++) {
			emptyString +=emptys[i]+"  ";
		}
		
		return emptyString;
	}

}
