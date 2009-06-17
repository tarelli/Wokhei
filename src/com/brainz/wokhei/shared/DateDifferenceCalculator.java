package com.brainz.wokhei.shared;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class DateDifferenceCalculator {



	public static float getDifferenceInHours(Date before, Date after)
	{
		float diffHours = -1;

		if(before!=null && after!=null)
		{
			DateTimeFormat fmtHrs = DateTimeFormat.getFormat("hh");
			DateTimeFormat fmtMinutes = DateTimeFormat.getFormat("mm");
			DateTimeFormat fmtDays = DateTimeFormat.getFormat("dd");
			DateTimeFormat fmtManses = DateTimeFormat.getFormat("MM");
			DateTimeFormat fmtYrs = DateTimeFormat.getFormat("yyyy");

			long beforeSeconds=0;
			long afterSeconds=0;

			beforeSeconds+=Integer.parseInt(fmtHrs.format(before))*3600;
			beforeSeconds+=Integer.parseInt(fmtMinutes.format(before))*60;
			beforeSeconds+=Integer.parseInt(fmtDays.format(before))*3600*24;
			beforeSeconds+=Integer.parseInt(fmtManses.format(before))*3600*24*getDaysInManses(Integer.parseInt(fmtManses.format(before)),Integer.parseInt(fmtYrs.format(before)));
			beforeSeconds+=Integer.parseInt(fmtYrs.format(before))*3600*24*getDaysInYear(Integer.parseInt(fmtYrs.format(before)));

			afterSeconds+=Integer.parseInt(fmtHrs.format(after))*3600;
			afterSeconds+=Integer.parseInt(fmtMinutes.format(after))*60;
			afterSeconds+=Integer.parseInt(fmtDays.format(after))*3600*24;
			afterSeconds+=Integer.parseInt(fmtManses.format(after))*3600*24*getDaysInManses(Integer.parseInt(fmtManses.format(after)),Integer.parseInt(fmtYrs.format(after)));
			afterSeconds+=Integer.parseInt(fmtYrs.format(after))*3600*24*getDaysInYear(Integer.parseInt(fmtYrs.format(after)));

			long diff = afterSeconds - beforeSeconds;

			//difference in hours
			diffHours = diff / (3600f);

		}

		return diffHours;
	}


	private static boolean isLeapYear(int year){
		return ((((year % 4) == 0) && ((year % 100 )!= 0)) || ((year % 400) == 0));

	}

	private static int getDaysInYear(int parseInt) 
	{
		if(isLeapYear(parseInt))
			return 366;
		else return 365;
	}

	private static int getDaysInManses(int month, int year) {
		switch(month)
		{
		case 1:
			return 31;
		case 2:
			if(isLeapYear(year))
				return 29;
			else 
				return 28;
		case 3:
			return 31;
		case 4:
			return 30;
		case 5:
			return 31;
		case 6:
			return 30;
		case 7:
			return 31;
		case 8:
			return 31;
		case 9:
			return 30;
		case 10:
			return 31;
		case 11:
			return 30;
		case 12:
			return 31;
		}
		return 0;
	}

}
