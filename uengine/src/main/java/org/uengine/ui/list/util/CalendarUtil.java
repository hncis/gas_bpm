/*
 * @(#)CalendarUtil.java
 *
 * Copyright (c) 2002 CNM Technologies, Inc.
 * All Rights Reserved.
 *
 */
package org.uengine.ui.list.util;

import java.util.Calendar;

import org.uengine.ui.list.datamodel.Constants;

/**
 * Calendar Utility.
 * @author alcolins
 *
 */
public class CalendarUtil {

    /**
     * Constants.LOCALE�� value
     */
    static String region = Constants.LOCALE;

    public static String[][] solarHolidays = getHolidaies(region);

    public static String[][] getHolidaies(String _region) {

        if(_region == null) {
            _region = "";
        }

 
        if(_region.equals("ko_KR")) { 
            String[][] holidaies = { {"11", "31", "45", "55", "66", "717",
                "815", "103", "1225"}
                , {"", "", "", "", "", "", "", "",""}
            };
            return holidaies;
        } else if(_region.equals("en_US")) { 
            String[][] holidaies = { {""}
                , {""}
            };
            return holidaies;
        } else {
            String[][] holidaies = { {""}
                , {""}
            }; 
            return holidaies;
        }
    }

    public static String setDate(int _year, int _month, int _day) {
        String res;

        res = Integer.toString(_year);
        if(_month < 10)
            res += "0";

        res += Integer.toString(_month);

        if(_day < 10)
            res += "0";

        res += Integer.toString(_day);

        return res;
    }

    public static int getYear(String date) {
        return Integer.parseInt(date.substring(0, 4));
    }

    public static int getMonth(String date) {
        return Integer.parseInt(date.substring(4, 6));
    }

    public static int getDate(String date) {
        return Integer.parseInt(date.substring(6, 8));
    }

    public static String getHour(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        String ret_hour = null;

        if(hour > 12) {
            if(hour < 10)
                ret_hour = "0" + Integer.toString(hour - 12);
            else
                ret_hour = Integer.toString(hour - 12);
        } else {
            if(hour < 10)
                ret_hour = "0" + Integer.toString(hour);
            else
                ret_hour = time.substring(0, 2);
        }

        return ret_hour;
    }

    public static String getMinute(String time) {
        return time.substring(2, 4);
    }

    public static String getAmPm(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        String res;

        if(hour >= 12)
            res = "PM";
        else
            res = "AM";

        return res;
    }

    public static int getDayOfWeek(int _year, int _month, int _day) {
        int year, month, date;

        Calendar cal = Calendar.getInstance();
        cal.set(_year, _month - 1, _day);

        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getStrDayOfWeek(int _year, int _month, int _day) {
        int dayofweek = getDayOfWeek(_year, _month, _day);
        String res;

        switch(dayofweek) {
            case 1:
                res = "Sun";
                break;
            case 2:
                res = "Mon";
                break;
            case 3:
                res = "Tue";
                break;
            case 4:
                res = "Wed";
                break;
            case 5:
                res = "Thu";
                break;
            case 6:
                res = "Fri";
                break;
            case 7:
                res = "Sat";
                break;
            default:
                res = null;
        }

        return res;
    }

    public static String getWeekDays(String _date, int _amount) {
        String res;

        int year = Integer.parseInt(_date.substring(0, 4));
        int month = Integer.parseInt(_date.substring(4, 6));
        int day = Integer.parseInt(_date.substring(6, 8));

        Calendar wCal = Calendar.getInstance();
        wCal.set(year, month - 1, day);
        wCal.add(Calendar.DATE, _amount);

        year = wCal.get(Calendar.YEAR);
        month = wCal.get(Calendar.MONTH) + 1;
        day = wCal.get(Calendar.DATE);

        res = Integer.toString(year);

        if(month < 10)
            res += "0" + Integer.toString(month);
        else
            res += Integer.toString(month);

        if(day < 10)
            res += "0";

        res += Integer.toString(day);

        return res;
    }

    public static String getStartDate(String date) {
        String startDate;

        int year = getYear(date);
        int month = getMonth(date);
        int day = getDate(date);

        int curDayOfWeek = getDayOfWeek(year, month, day);

        int startTmp = 1 - curDayOfWeek;

        Calendar tmpCalendar = Calendar.getInstance();

        tmpCalendar.set(year, month - 1, day);
        tmpCalendar.add(Calendar.DATE, startTmp);

        startDate = Integer.toString(tmpCalendar.get(Calendar.YEAR));

        if((tmpCalendar.get(Calendar.MONTH) + 1) < 10)
            startDate += "0";

        startDate += Integer.toString((tmpCalendar.get(Calendar.MONTH)) + 1);

        if(tmpCalendar.get(Calendar.DATE) < 10)
            startDate += "0";

        startDate += Integer.toString(tmpCalendar.get(Calendar.DATE));

        return startDate;

    }

    public static String getEndDate(String date) {
        String endDate;

        int year = getYear(date);
        int month = getMonth(date);
        int day = getDate(date);

        int curDayOfWeek = getDayOfWeek(year, month, day);

        int endTmp = 7 - curDayOfWeek;

        Calendar tmpCalendar = Calendar.getInstance();

        tmpCalendar.set(year, month - 1, day);
        tmpCalendar.add(Calendar.DATE, endTmp);

        endDate = Integer.toString(tmpCalendar.get(Calendar.YEAR));

        if((tmpCalendar.get(Calendar.MONTH) + 1) < 10)
            endDate += "0";

        endDate += Integer.toString((tmpCalendar.get(Calendar.MONTH)) + 1);

        if(tmpCalendar.get(Calendar.DATE) < 10)
            endDate += "0";

        endDate += Integer.toString(tmpCalendar.get(Calendar.DATE));

        return endDate;
    }

    public static int getFirstDayOfWeek(int _year, int _month) {
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.set(_year, _month - 1, 1);

        return(tmpCal.get(Calendar.DAY_OF_WEEK) - 1);
    }

    public static String getDateOfInt(int _year, int _month, int _day) {

        String strDate = Integer.toString(_year);

        strDate += "-";

        if(_month < 10)
            strDate += "0";

        strDate += Integer.toString(_month);
        strDate += "-";

        if(_day < 10)
            strDate += "0";

        strDate += Integer.toString(_day);

        return strDate;
    }

    public static int LastDateInMonth(int _year, int _month, int _day) {
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.set(Calendar.YEAR, _year);
        tmpCal.set(Calendar.MONTH, _month);
        tmpCal.set(Calendar.DATE, 0);

        return(tmpCal.get(Calendar.DATE));
    }

    public static int[][] getMonthDays(String date) {
        int[][] months = new int[6][7];
        int days = 1;

        int year = getYear(date);
        int month = getMonth(date);
        int day = getDate(date);

        for(int i = getFirstDayOfWeek(year, month); i < 7; i++) {
            months[0][i] = days;
            days++;
        }

        for(int i = 1; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                if(days <= LastDateInMonth(year, month, day)) {
                    months[i][j] = days;
                    days++;
                } else
                    break;
            }
        }

        return months;
    }

    public static boolean isSolarHoliday(String date) {
        boolean res = false;

        for(int i = 0; i < solarHolidays[0].length; i++) {
            if(solarHolidays[0][i].equals(date) == true) {
                res = true;
                break;
            }
        }

        return res;
    }

    public static String getNamesolarHoliday(String date) {
        String res = null;

        for(int i = 0; i < solarHolidays[0].length; i++) {
            if(solarHolidays[0][i].equals(date) == true) {
                res = solarHolidays[1][i];
                break;
            }
        }

        return res;
    }

    public static String getMondayDate(String date) {
        String startDate;

        int year = getYear(date);
        int month = getMonth(date);
        int day = getDate(date);

        int curDayOfWeek = getDayOfWeek(year, month, day);

        int startTmp = 2 - curDayOfWeek;

        if(startTmp == 0)
            return date;

        if(startTmp > 0) {
            startTmp = startTmp - 7;
        }

        Calendar tmpCalendar = Calendar.getInstance();

        tmpCalendar.set(year, month - 1, day);

        tmpCalendar.add(Calendar.DATE, startTmp);

        startDate = Integer.toString(tmpCalendar.get(Calendar.YEAR));

        if((tmpCalendar.get(Calendar.MONTH) + 1) < 10)
            startDate += "0";

        startDate += Integer.toString((tmpCalendar.get(Calendar.MONTH)) + 1);

        if(tmpCalendar.get(Calendar.DATE) < 10)
            startDate += "0";

        startDate += Integer.toString(tmpCalendar.get(Calendar.DATE));

        return startDate;
    }

    public static String[] getWeekDate(String date) {
        String[] weekDays = new String[7];
        weekDays[0] = getMondayDate(date);
        weekDays[1] = getWeekDays(weekDays[0], 1);
        weekDays[2] = getWeekDays(weekDays[0], 2);
        weekDays[3] = getWeekDays(weekDays[0], 3);
        weekDays[4] = getWeekDays(weekDays[0], 4);
        weekDays[5] = getWeekDays(weekDays[0], 5);
        weekDays[6] = getWeekDays(weekDays[0], 6);

        return weekDays;
    }

    public static String getSundayDate(String date) {
        String endDate;

        int year = getYear(date);
        int month = getMonth(date);
        int day = getDate(date);

        int curDayOfWeek = getDayOfWeek(year, month, day);

        int endTmp = 8 - curDayOfWeek;

        if(endTmp >= 7) {
            endTmp -= 7;
        }

        Calendar tmpCalendar = Calendar.getInstance();

        tmpCalendar.set(year, month - 1, day);
        tmpCalendar.add(Calendar.DATE, endTmp);

        endDate = Integer.toString(tmpCalendar.get(Calendar.YEAR));

        if((tmpCalendar.get(Calendar.MONTH) + 1) < 10)
            endDate += "0";

        endDate += Integer.toString((tmpCalendar.get(Calendar.MONTH)) + 1);

        if(tmpCalendar.get(Calendar.DATE) < 10)
            endDate += "0";

        endDate += Integer.toString(tmpCalendar.get(Calendar.DATE));

        return endDate;
    }

    public static String getSeqWeek(String date) {
        int seqWeek = 0;
        int curDate = getDate(date);
        int[][] month = getMonthDays(date);

        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                if(month[i][j] != 0) {
                    if(month[i][j] == curDate)
                        seqWeek = i + 1;
                }
            }
        }
        switch(seqWeek) {
            case 1:
                return "()";
            case 2:
                return "()";
            case 3:
                return "()";
            case 4:
                return "()";
            case 5:
                return "()";
            case 6:
                return "()";
            default:
                return "";
        }
    }

    /**
     *
     * @param <{Date}>
     * @return
     */
    public static String getPrevMonth(String Date) {

        int year = getYear(FormatUtil.deleteDash(Date));
        int month = getMonth(FormatUtil.deleteDash(Date));

        String endDate = "";

        Calendar tmpCalendar = Calendar.getInstance();

        tmpCalendar.set(year, month - 1, 1);

        tmpCalendar.add(tmpCalendar.MONTH, -1);

        endDate = Integer.toString(tmpCalendar.get(Calendar.YEAR));
        endDate += "-";
        if((tmpCalendar.get(Calendar.MONTH) + 1) < 10)
            endDate += "0";

        endDate += Integer.toString((tmpCalendar.get(Calendar.MONTH) + 1));

        return endDate;

    }

    /**
     *
     * @param <{Date}>
     * @return
     */
    public static String getNextMonth(String Date) {

        int year = getYear(FormatUtil.deleteDash(Date));
        int month = getMonth(FormatUtil.deleteDash(Date));

        String endDate = "";

        Calendar tmpCalendar = Calendar.getInstance();

        tmpCalendar.set(year, month - 1, 1);

        tmpCalendar.add(tmpCalendar.MONTH, 1);

        endDate = Integer.toString(tmpCalendar.get(Calendar.YEAR));
        endDate += "-";
        if((tmpCalendar.get(Calendar.MONTH) + 1) < 10)
            endDate += "0";

        endDate += Integer.toString((tmpCalendar.get(Calendar.MONTH) + 1));

        return endDate;
    }

}
