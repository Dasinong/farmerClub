package com.dasinong.farmerClub.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LunarHelper {
	private int syear;
	private int smonth;
	private int sday;
	private int year;
	private int month;
	private int day;
	public String jieqi;
	public String festival;
	private boolean leap;
	final static String chineseNumber[] = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二" };
	static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	final static long[] lunarInfo = new long[] { 0x04bd8, 0x04ae0, 0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0,
			0x09ad0, 0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540, 0x0d6a0, 0x0ada2, 0x095b0, 0x14977,
			0x04970, 0x0a4b0, 0x0b4b5, 0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970, 0x06566, 0x0d4a0,
			0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3, 0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
			0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0, 0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573,
			0x052d0, 0x0a9a8, 0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570, 0x05260, 0x0f263, 0x0d950,
			0x05b57, 0x056a0, 0x096d0, 0x04dd5, 0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0, 0x195a6,
			0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50, 0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970,
			0x064b0, 0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0, 0x0c960, 0x0d954, 0x0d4a0, 0x0da50,
			0x07552, 0x056a0, 0x0abb7, 0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50, 0x055d9, 0x04ba0,
			0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954, 0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
			0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0, 0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45,
			0x0b5a0, 0x056d0, 0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20, 0x0ada0 };

	// ====== 传回农历 y年的总天数
	final private static int yearDays(int y) {
		int i, sum = 348;
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((lunarInfo[y - 1900] & i) != 0)
				sum += 1;
		}
		return (sum + leapDays(y));
	}

	// ====== 传回农历 y年闰月的天数
	final private static int leapDays(int y) {
		if (leapMonth(y) != 0) {
			if ((lunarInfo[y - 1900] & 0x10000) != 0)
				return 30;
			else
				return 29;
		} else
			return 0;
	}

	// ====== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
	final private static int leapMonth(int y) {
		return (int) (lunarInfo[y - 1900] & 0xf);
	}

	// ====== 传回农历 y年m月的总天数
	final private static int monthDays(int y, int m) {
		if ((lunarInfo[y - 1900] & (0x10000 >> m)) == 0)
			return 29;
		else
			return 30;
	}

	// ====== 传回农历 y年的生肖
	final public String animalsYear() {
		final String[] Animals = new String[] { "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊", "猴", "鸡", "狗", "猪" };
		return Animals[(year - 4) % 12];
	}

	// ====== 传入 月日的offset 传回干支, 0=甲子
	final private static String cyclicalm(int num) {
		final String[] Gan = new String[] { "甲", "乙", "丙", "丁", "戊", "己", "庚", "辛", "壬", "癸" };
		final String[] Zhi = new String[] { "子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥" };
		return (Gan[num % 10] + Zhi[num % 12]);
	}

	// ====== 传入 offset 传回干支, 0=甲子
	final public String cyclical() {
		int num = year - 1900 + 36;
		return (cyclicalm(num));
	}

	/** */
	/**
	 * 传出y年m月d日对应的农历. yearCyl3:农历年与1864的相差数 ? monCyl4:从1900年1月31日以来,闰月数
	 * dayCyl5:与1900年1月31日相差的天数,再加40 ?
	 * 
	 * @param cal
	 * @return
	 */
	public LunarHelper(Calendar cal) {
		@SuppressWarnings("unused")
		int yearCyl, monCyl, dayCyl;
		int leapMonth = 0;
		Date baseDate = null;
		try {
			baseDate = chineseDateFormat.parse("1900年1月31日");
		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use
									// Options | File Templates.
		}

		Date sdate = cal.getTime();
		syear = sdate.getYear() + 1900;
		smonth = sdate.getMonth() + 1;
		sday = sdate.getDate();

		// 求出和1900年1月31日相差的天数
		int offset = (int) ((cal.getTime().getTime() - baseDate.getTime()) / 86400000L);
		dayCyl = offset + 40;
		monCyl = 14;

		// 用offset减去每农历年的天数
		// 计算当天是农历第几天
		// i最终结果是农历的年份
		// offset是当年的第几天
		int iYear, daysOfYear = 0;
		for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
			daysOfYear = yearDays(iYear);
			offset -= daysOfYear;
			monCyl += 12;
		}
		if (offset < 0) {
			offset += daysOfYear;
			iYear--;
			monCyl -= 12;
		}
		// 农历年份
		year = iYear;

		yearCyl = iYear - 1864;
		leapMonth = leapMonth(iYear); // 闰哪个月,1-12
		leap = false;

		// 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
		int iMonth, daysOfMonth = 0;
		for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
			// 闰月
			if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
				--iMonth;
				leap = true;
				daysOfMonth = leapDays(year);
			} else
				daysOfMonth = monthDays(year, iMonth);

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
		month = iMonth;
		day = offset + 1;
	}

	public static String getChinaDayString(int day) {
		String chineseTen[] = { "初", "十", "廿", "卅" };
		int n = day % 10 == 0 ? 9 : day % 10 - 1;
		if (day > 30)
			return "";
		if (day == 10)
			return "初十";
		else
			return chineseTen[day / 10] + chineseNumber[n];
	}

	public String toString() {
		return year + "年" + (leap ? "闰" : "") + chineseNumber[month - 1] + "月" + getChinaDayString(day);
	}

	public String getDay() {
		return getChinaDayString(day);
	}

	public String getFestival() {
		if (month == 1 && day == 1)
			return "春节";
		if (month == 1 && day == 15)
			return "元宵节";
		if (month == 2 && day == 2)
			return "二月二";
		if (month == 5 && day == 5)
			return "端午节";
		if (month == 7 && day == 7)
			return "七夕节";
		if (month == 8 && day == 15)
			return "中秋节";
		if (month == 9 && day == 9)
			return "重阳节";
		if (month == 12 && day == 8)
			return "腊八节";
		if (month == 12 && day == 23)
			return "小年";

		if (smonth == 1 && sday == 1)
			return "元旦";
		if (smonth == 2 && sday == 14)
			return "情人节";
		if (smonth == 3 && sday == 12)
			return "植树节";
		if (smonth == 4 && sday == 1)
			return "愚人节";
		if (smonth == 5 && sday == 1)
			return "劳动节";
		if (smonth == 6 && sday == 1)
			return "儿童节";
		if (smonth == 10 && sday == 1)
			return "国庆节";
		if (smonth == 10 && sday == 31)
			return "万圣节";
		if (smonth == 12 && sday == 24)
			return "平安夜";
		if (smonth == 12 && sday == 25)
			return "圣诞节";
		return "";
	}

	public String getJieQi() {
		int _syear = this.syear % 100;
		double[][] coefficient = new double[][] { { 5.4055, 2019, -1 }, // 小寒
				{ 20.12, 2082, 1 }, // 大寒
				{ 3.87, 0 }, // 立春
				{ 18.74, 2026, -1 }, // 雨水
				{ 5.63, 0 }, // 惊蛰
				{ 20.646, 2084, 1 }, // 春分
				{ 4.81, 0 }, // 清明
				{ 20.1, 0 }, // 谷雨
				{ 5.52, 1911, 1 }, // 立夏
				{ 21.04, 2008, 1 }, // 小满
				{ 5.678, 1902, 1 }, // 芒种
				{ 21.37, 1928, 1 }, // 夏至
				{ 7.108, 2016, 1 }, // 小暑
				{ 22.83, 1922, 1 }, // 大暑
				{ 7.5, 2002, 1 }, // 立秋
				{ 23.13, 0 }, // 处暑
				{ 7.646, 1927, 1 }, // 白露
				{ 23.042, 1942, 1 }, // 秋分
				{ 8.318, 0 }, // 寒露
				{ 23.438, 2089, 1 }, // 霜降
				{ 7.438, 2089, 1 }, // 立冬
				{ 22.36, 1978, 1 }, // 小雪
				{ 7.18, 1954, 1 }, // 大雪
				{ 21.94, 2021, -1 }// 冬至
		};
		String[] term_name = new String[] { "小寒", "大寒", "立春", "雨水", "惊蛰", "春分", "清明", "谷雨", "立夏", "小满", "芒种", "夏至",
				"小暑", "大暑", "立秋", "处暑", "白露", "秋分", "寒露", "霜降", "立冬", "小雪", "大雪", "冬至" };

		int idx1 = (smonth - 1) * 2;
		int _leap_value = (int) Math.floor((_syear - 1) / 4);

		int day1 = (int) Math.floor(_syear * 0.2422 + coefficient[idx1][0]) - _leap_value;
		if ((coefficient[idx1][1] != 0) && coefficient[idx1][1] == year) {
			day1 += coefficient[idx1][2];
		}
		int day2 = (int) Math.floor(_syear * 0.2422 + coefficient[idx1 + 1][0]) - _leap_value;
		if ((coefficient[idx1 + 1][1] != 0) && coefficient[idx1 + 1][1] == year) {
			day1 += coefficient[idx1 + 1][2];
		}
		// echo __FILE__.'->'.__LINE__.'
		// $day1='.$day1,',$day2='.$day2.'<br/>'.chr(10);
		if (sday == day1)
			return term_name[idx1];
		if (sday == day2)
			return term_name[idx1 + 1];
		return "";
	}

	public static Map<String, String> getTodayLunar() {
		Calendar today = Calendar.getInstance();
		Map<String, String> date = new HashMap<String, String>();
		date.put("date", "" + today.getTime().getDate());
		date.put("day", "" + today.getTime().getDay());
		LunarHelper lh = new LunarHelper(today);
		String jieqi = lh.getJieQi();
		if (jieqi.equals("")) {
			date.put("lunar", lh.getDay());
		} else {
			date.put("lunar", jieqi);
		}
		return date;
	}

}