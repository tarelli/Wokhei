package com.brainz.wokhei.shared;

import java.util.Date;

public class DateDifferenceCalculator {

	public static float getDifferenceInHours(Date before, Date after) {
		float diffHours = -1;

		if (before != null && after != null) {

			long beforeSeconds = getSecInDate(before);
			long afterSeconds = getSecInDate(after);
			long diff = afterSeconds - beforeSeconds;

			// difference in hours
			diffHours = diff / (3600f);

		}

		return diffHours;
	}

	/**
	 * @param date
	 * @return
	 */
	private static long getSecInDate(Date date) {
		long sec = 0;
		sec += getSecsFrom1970ToYear(date.getYear()); // all the seconds from
		// 1970 till current
		// year
		sec += getSecsFromJanuaryToMonth(date.getMonth(), date.getYear() + 1900); // all
		// the
		// seconds
		// from
		// january
		// of
		// the
		// above year till the month - 1 of above year
		sec += date.getDate() * 3600 * 24; // every day is made of 3600x24
		// seconds. all the days from the
		// 1st till the day -1
		sec += date.getHours() * 3600; // every hour is made of 3600 seconds.
		// all the hours from 0am till the
		// current hour -1
		sec += date.getMinutes() * 60; // every minute is made of 60 seconds.
		// all the minutes from 0 till the
		// current minute -1 (didnt pass yet!)
		System.out.println("The Seconds in date " + date + " are : " + sec);
		return sec;
	}

	/**
	 * @param year
	 * @return
	 */
	private static long getSecsFrom1970ToYear(int year) {
		long secs = 0;
		for (int yearI = 70; yearI < year; yearI++) {
			secs += getDaysInYear(yearI) * 3600 * 24;
		}
		return secs;
	}

	/**
	 * @param year
	 * @return
	 */
	private static long getSecsFromJanuaryToMonth(int month, int year) {
		long secs = 0;
		for (int monthI = 0; monthI < month; monthI++) {
			secs += 3600 * 24 * getDaysInManses(monthI, year);
		}
		return secs;
	}

	/**
	 * @param year
	 * @return
	 */
	private static boolean isLeapYear(int year) {
		return ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 400) == 0));

	}

	/**
	 * @param parseInt
	 * @return
	 */
	private static int getDaysInYear(int parseInt) {
		if (isLeapYear(parseInt))
			return 366;
		else
			return 365;
	}

	/**
	 * @param month
	 * @param year
	 * @return
	 */
	private static int getDaysInManses(int month, int year) {
		switch (month) {
		case 0:
			return 31;
		case 1:
			if (isLeapYear(year))
				return 29;
			else
				return 28;
		case 2:
			return 31;
		case 3:
			return 30;
		case 4:
			return 31;
		case 5:
			return 30;
		case 6:
			return 31;
		case 7:
			return 31;
		case 8:
			return 30;
		case 9:
			return 31;
		case 10:
			return 30;
		case 11:
			return 31;
		}
		return 0;
	}

}
