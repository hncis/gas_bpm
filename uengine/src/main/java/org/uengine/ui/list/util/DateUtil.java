/*
 * @(#)DateUtil.java
 *
 * Copyright (c) 2003 CNM Technologies, Inc.
 * All Rights Reserved.
 *
 */
package org.uengine.ui.list.util;

import org.apache.log4j.Logger;

import java.util.*;
import java.text.*;
import java.sql.Time;

import org.uengine.ui.list.datamodel.Constants;

/**
 * ��¥ /ƿ��Ƽ
 */
public class DateUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DateUtil.class);

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    public static SimpleDateFormat dateFormatDB = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat logFormat = new SimpleDateFormat("MMddHHmm");
    public static SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
    public static SimpleDateFormat yearMonthFormat = new SimpleDateFormat(Constants.MONTHFORMAT);
    public static SimpleDateFormat defaultDateFormat = new SimpleDateFormat(Constants.DATEFORMAT);
    public static SimpleDateFormat dateSqlFormat_dash = new SimpleDateFormat("yyyy-MM-dd");

    public static Date getDate(String yyyymmdd) {
        if (yyyymmdd != null && yyyymmdd.length() == 8 && isDigit(yyyymmdd)) {
            Calendar cal = Calendar.getInstance();
            cal.set(Integer.parseInt(yyyymmdd.substring(0, 4)),
                    Integer.parseInt(yyyymmdd.substring(4, 6)) - 1,
                    Integer.parseInt(yyyymmdd.substring(6, 8)));

            return cal.getTime();
        }

        return null;
    }

    public static Date getDateYYYYMM(String yyyymm) {
        if (yyyymm != null && yyyymm.length() == 6 && isDigit(yyyymm)) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, Integer.parseInt(yyyymm.substring(0, 4)));
            cal.set(Calendar.MONTH, Integer.parseInt(yyyymm.substring(4, 6)) - 1);

            return cal.getTime();
        }

        return null;
    }

    public static Date getDateWithDelimiter(String yyyymmdd) {
        return getDateWithDelimiter(yyyymmdd, '\0');
    }

    public static Date getDateWithDelimiterYYYYMM(String yyyymm) {
        return getDateWithDelimiterYYYYMM(yyyymm, '\0');
    }

    public static Date getDateWithDelimiter(String yyyymmdd, char delimiter) {
        if (yyyymmdd != null && !yyyymmdd.equals("")) {
            StringTokenizer st = new StringTokenizer(yyyymmdd, String.valueOf(delimiter) + "/.-");
            String yyyy = st.nextToken();
            String mm = st.nextToken();
            String dd = st.nextToken();

            if (mm.length() < 2)
                mm = "0" + mm;
            if (dd.length() < 2)
                dd = "0" + dd;

            String temp = yyyy + mm + dd;
            if (isDigit(temp))
                return getDate(temp);
        }

        return null;
    }

    public static Date getDateWithDelimiterYYYYMM(String yyyymm, char delimiter) {
        if (yyyymm != null && !yyyymm.equals("")) {
            StringTokenizer st = new StringTokenizer(yyyymm, String.valueOf(delimiter) + "/.-");
            String yyyy = st.nextToken();
            String mm = st.nextToken();

            if (mm.length() < 2)
                mm = "0" + mm;

            String temp = yyyy + mm;
            if (isDigit(temp))
                return getDateYYYYMM(temp);
        }

        return null;
    }

    public static String getDateStr(Date date, char delimiter) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat("yyyy" + delimiter + "MM" + delimiter + "dd");
            return df.format(date);
        }

        return "";
    }

    public static String getDateStrYYYYMM(Date date, char delimiter) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat("yyyy" + delimiter + "MM");
            return df.format(date);
        }

        return "";
    }

    public static String getDateStrWeekly(Date date, char delimiter) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat("(E)MM" + delimiter + "dd");
            return df.format(date);
        }

        return "";
    }

    public static String getDateStrMonthly(Date date, char delimiter) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat("MM" + delimiter + "dd");
            return df.format(date);
        }

        return "";
    }

    public static Date getTime(String hhmm) {
        if (hhmm != null && hhmm.length() == 4 && isDigit(hhmm)) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hhmm.substring(0, 2)));
            cal.set(Calendar.MINUTE, Integer.parseInt(hhmm.substring(2, 4)));

            return cal.getTime();
        }

        return null;
    }

    public static Date getTimeWithDelimiter(String hhmm) {
        return getTimeWithDelimiter(hhmm, '\0');
    }

    public static Date getTimeWithDelimiter(String hhmm, char delimiter) {
        if (hhmm != null) {
            StringTokenizer st = new StringTokenizer(hhmm, String.valueOf(delimiter) + ":-");
            String hh = st.nextToken();
            String mm = st.nextToken();

            if (hh.length() < 2)
                hh = "0" + hh;
            if (mm.length() < 2)
                mm = "0" + mm;

            String temp = hh + mm;
            if (isDigit(temp))
                return getTime(temp);
        }

        return null;
    }


    public static String getTimeWithDel(String hhmm, char delimiter) {
        if (hhmm != null) {
            StringTokenizer st = new StringTokenizer(hhmm, String.valueOf(delimiter));
            String hh = st.nextToken();
            String mm = st.nextToken();

            if (hh.length() < 2)
                hh = "0" + hh;
            if (mm.length() < 2)
                mm = "0" + mm;

            String temp = hh + mm;

            return temp;
        }

        return null;
    }

    public static String getTimeStr(Date date, char delimiter) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat("HH" + delimiter + "mm");
            return df.format(date);
        }

        return "";
    }

    private static boolean isDigit(String digitStr) {
        if (digitStr != null) {
            for (int i = 0; i < digitStr.length(); i++)
                if (!Character.isDigit(digitStr.charAt(i)))
                    return false;
        }
        return true;
    }

    public static String getFormattedDateStr(String yyyyMMdd) {
        String formattedDate = "";
        if (yyyyMMdd != null && !yyyyMMdd.equals("")) {
            Date date = DateUtil.getDate(yyyyMMdd);
            formattedDate = defaultDateFormat.format(date);
        }
        return formattedDate;
    }

    public static String getUnFormattedDateStr(String formattedDate) {
        String unFormattedDate = "";
        try {

            if (formattedDate != null && !formattedDate.equals("")) {
                if (formattedDate.length() == 8) {
                    unFormattedDate = formattedDate;
                } else {
                    Date date = defaultDateFormat.parse(formattedDate);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
                    unFormattedDate = formatter.format(date);
                }
            }
        } catch (Exception e) {
			logger.error("getUnFormattedDateStr(String)", e); //$NON-NLS-1$
        }
		return unFormattedDate;
    }

    public static String getFormattedDate(Date date) {
        String formattedDate = "";
        if (date != null) {
            formattedDate = defaultDateFormat.format(date);
        }
        return formattedDate;
    }

    public static Date getUnFormattedDate(String formattedDate) {
        Date date = null;
        try {
            if (formattedDate != null && !formattedDate.equals("")) {
                date = defaultDateFormat.parse(formattedDate);
            }
        } catch (Exception e) {
			logger.error("getUnFormattedDate(String)", e); //$NON-NLS-1$
        }
        return date;
    }

    public static String getFormattedMonthStr(String yyyyMM) {
        String formattedDate = "";
        if (yyyyMM != null && !yyyyMM.equals("")) {
            if (yyyyMM.length() > 6) {
                yyyyMM = yyyyMM.substring(0, 6);
            }
            Date date = DateUtil.getDateYYYYMM(yyyyMM);
            formattedDate = yearMonthFormat.format(date);
        }

        return formattedDate;
    }

    public static String getUnFormattedMonthStr(String formmatedDate) {
        String unFormattedDate = "";
        try {
            if (formmatedDate != null && !formmatedDate.equals("")) {
                if (formmatedDate.length() == 4) {
                    unFormattedDate = formmatedDate;
                } else {
                    Date date = yearMonthFormat.parse(formmatedDate);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
                    unFormattedDate = formatter.format(date);
                }
            }
        } catch (Exception e) {
			logger.error("getUnFormattedMonthStr(String)", e); //$NON-NLS-1$
        }
        return unFormattedDate;
    }

    public static String getFormattedTimeStr(String HHmm) {
        String formattedDate = "";
        try {
            if (HHmm != null && !HHmm.equals("")) {
                java.text.SimpleDateFormat formatter = new SimpleDateFormat(Constants.TIMEFORMAT);
                Date date = new SimpleDateFormat("HHmm").parse(HHmm);
                formattedDate = formatter.format(date);
            }
        } catch (Exception e) {
			logger.error("getFormattedTimeStr(String)", e); //$NON-NLS-1$
        }
        return formattedDate;
    }

    public static String getUnFormattedTimeStr(String formmatedTime) {
        String unFormattedTime = "";
        try {
            if (formmatedTime != null && !formmatedTime.equals("")) {
				if( formmatedTime.length() == 4){
					unFormattedTime = formmatedTime;
				}else{
                	java.text.SimpleDateFormat formatter = new SimpleDateFormat(Constants.TIMEFORMAT);
                	Date date = formatter.parse(formmatedTime);
                	formatter = new SimpleDateFormat("HHmm");
                	unFormattedTime = formatter.format(date);
				}
            }
        } catch (Exception e) {
			logger.error("getUnFormattedTimeStr(String)", e); //$NON-NLS-1$
        }
        return unFormattedTime;
    }

    public static String getDateStr(Date date) {
        if (date != null) {
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            return df.format(date);
        }

        return null;
    }

    public static Date addDate(Calendar cal, int year, int month, int day) {
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);

        cal.set(y + year, m + month, d + day);

        return cal.getTime();
    }

    public static String getCurrentDate() {
        Date current = new Date(System.currentTimeMillis());
        return defaultDateFormat.format(current);
    }

    public static String getCurrentYear() {
        Date current = new Date(System.currentTimeMillis());
        return yearFormat.format(current);
    }

    public static String getCurrentMonth() {
        Date current = new Date(System.currentTimeMillis());
        return monthFormat.format(current);
    }

    public static String getCurrentYearMonth() {
        Date current = new Date(System.currentTimeMillis());
        return yearMonthFormat.format(current);
    }

    public static String getYearMonth(String activityDate) {
        java.sql.Date current = java.sql.Date.valueOf(activityDate);

        return yearMonthFormat.format(current);
    }

    public static String getPreYearMonthDay() {
        Date current = new Date(System.currentTimeMillis());
        String curDate = dateFormat.format(current);

        int month = Integer.parseInt(curDate.substring(4, 6));

        int preMonth = month - 1;

        String rMonth = "";
        if (preMonth < 10)
            rMonth = "0" + preMonth;
        else
            rMonth = String.valueOf(preMonth);

        if (rMonth.equals("00"))
            rMonth = "12";

        int day = Integer.parseInt(curDate.substring(6, 8));

        int lastDay = CalendarUtil.LastDateInMonth(Integer.parseInt(curDate.substring(0, 4)), Integer.parseInt(rMonth),
            day);

        String preDay = "";

        if (lastDay < (day + 1)) {
            preDay = "01";
        } else {
            if (day < 9)
                preDay = "0" + (day + 1);
            else
                preDay = String.valueOf( (day + 1));
        }

        String preYear = curDate.substring(0, 4);
        if (rMonth.equals("12"))
            preYear = String.valueOf(Integer.parseInt(preYear) - 1);

        String preYearMonthDay = preYear + rMonth + preDay;
        return preYearMonthDay;
    }

    public static String getNextYearMonthDay() {
        Date current = new Date(System.currentTimeMillis());
        String curDate = dateFormat.format(current);

        int month = Integer.parseInt(curDate.substring(4, 6));

        int nextMonth = month + 1;
        String rMonth = "";
        if (nextMonth < 10)
            rMonth = "0" + nextMonth;
        else
            rMonth = String.valueOf(nextMonth);

        int day = Integer.parseInt(curDate.substring(6, 8));
        int year = Integer.parseInt(curDate.substring(0, 4));

        if (Integer.parseInt(rMonth) > 12) {
            year = year + 1;
            rMonth = "01";
        }

        String nextDay = "";

        if ( (day - 1) < 1) {
            if (rMonth.equals("01"))
                day = CalendarUtil.LastDateInMonth(Integer.parseInt(curDate.substring(0, 4)), 12, day);
            else
                day = CalendarUtil.LastDateInMonth(Integer.parseInt(curDate.substring(0, 4)),
                    Integer.parseInt(rMonth) - 1, day);
        }

        if (day < 10)
            nextDay = "0" + (day - 1);
        else
            nextDay = String.valueOf( (day - 1));

        String nextYearMonthDay = year + rMonth + nextDay;
        return nextYearMonthDay;
    }

    public static String getFirstDayOfMonth(String activityDate) throws java.
        text.ParseException {

        Date date = defaultDateFormat.parse(activityDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        Date firstDay = cal.getTime();
        return defaultDateFormat.format(firstDay);
    }

    public static String getFirstDayOfMonth(String activityDate, String toFormat) throws java.
        text.ParseException {

        Date date = defaultDateFormat.parse(activityDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        Date firstDay = cal.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat(toFormat);

        return formatter.format(firstDay);
    }

    public static String getLastDayOfMonth(String activityDate) throws java.
        text.ParseException {

        Date date = defaultDateFormat.parse(activityDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);

        Date lastDay = cal.getTime();

        return defaultDateFormat.format(lastDay);
    }

    public static String getLastDayOfMonth(String activityDate, String toFormat) throws java.
        text.ParseException {

        Date date = defaultDateFormat.parse(activityDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);

        Date lastDay = cal.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat(toFormat);

        return formatter.format(lastDay);
    }

    public static String getBeforeDayOfMonth(String activityDate) throws java.
        text.ParseException {
        //Date date = new Date(activityDate);
        Date date = defaultDateFormat.parse(activityDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.add(Calendar.MONTH, -1);
        return defaultDateFormat.format(cal.getTime());
    }

    public static String addMonth(String activityDate, int month) throws java.
        text.ParseException {
        Date date =defaultDateFormat.parse(activityDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);

        cal.set(y, m + month, d);

        Date addMonth = cal.getTime();
        String addMonthStr = defaultDateFormat.format(addMonth);
        return addMonthStr;
    }

	public static String addMonthTimetamp(long lTimeStamp, String _pattern, int month)
	{
		Date date = new Date(lTimeStamp);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int y = cal.get(Calendar.YEAR);
		int m = cal.get(Calendar.MONTH);
		int d = cal.get(Calendar.DATE);

		cal.set(y, m + month, d);

		Date addMonth = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(_pattern);
		String addMonthStr = formatter.format( addMonth );
		return addMonthStr;
	}

    public static String addYear(String activityDate, int year) throws java.
        text.ParseException {
        Date date =defaultDateFormat.parse(activityDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE);

        cal.set(y + year, m, d);

        date = cal.getTime();
        return defaultDateFormat.format(date);
    }

    public static ArrayList listWeeklyDate(String currentDate) {
        Date searchDate = DateUtil.getDateWithDelimiter(currentDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(searchDate);

        int t = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_MONTH, - (t - 1));
        Date start = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 6);
        Date end = cal.getTime();

        ArrayList dateList = new ArrayList();
        while (start.getTime() <= end.getTime()) {
            dateList.add(DateUtil.getDateStrWeekly(start, Constants.DATE_DELIMITER));
            cal.setTime(start);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            start = cal.getTime();
        }

        return dateList;
    }

    public static ArrayList listMonthlyDate(String yearMonth) {
        ArrayList row = new ArrayList();
        ArrayList col = null;

        Date searchDate = DateUtil.getDateWithDelimiterYYYYMM(yearMonth);
        Calendar cal = Calendar.getInstance();
        cal.setTime(searchDate);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        int firstWeek = cal.get(Calendar.DAY_OF_WEEK);
        int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        col = new ArrayList();
        int i = 0;
        for (i = 1; i < firstWeek; i++)
            col.add("");
        for (i = 1; i <= 7 - (firstWeek - 1); i++)
            col.add("" + i);
        row.add(col);
        col = new ArrayList();
        for (int j = i, k = 1; j <= endDay; j++, k++) {
            if (k > 7) {
                row.add(col);
                k = 1;
                col = new ArrayList();
            }
            col.add("" + j);
        }
        row.add(col);

        return row;
    }

    public static String getMonthDay(String activityDate) throws java.text.ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("MM��dd��");
        SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd");
        Date date = formatter2.parse(activityDate);
        String monthDay = formatter.format(date);

        return monthDay;
    }

    public static String checkNullDate(String date) {
        if (date == null || date.trim().equals(""))
            return null;
        else
            return date;
    }

    public static String getTodayDate() {
        java.util.Date date = new java.util.Date();
        java.util.Date today = new java.sql.Timestamp(date.getTime());
        String todayDate = String.valueOf(today);
//            temp.substring(0,10)+"."+temp.substring(11,23)
        return todayDate.substring(0, 10) + "-" + todayDate.substring(11, 22);
    }

    public static int getMaxDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new java.util.Date());
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DATE, 1);
        cal.add(Calendar.DATE, -1);
        return cal.get(Calendar.DATE);
    }

    public static String formatCurTime(String _pattern) {
        SimpleDateFormat formatter = new SimpleDateFormat(_pattern);
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

	public static String formatTimeStamp( long lTimeStamp, String _pattern ) {
		SimpleDateFormat formatter = new SimpleDateFormat(_pattern);
		Date time = new Date( lTimeStamp );
		return formatter.format( time );
	}

    public static String getYYMM() {
        Date current = new Date(System.currentTimeMillis());
        return new SimpleDateFormat("yyMM").format(current);
    }

    public static String convert(String date) {
        int year = getYear(date);
        int month = getMonth(date);
        int day = getDay(date);

        String m = "";
        if (month < 10)
            m = "0" + month;
        else
            m = Integer.toString(month);
        String d = "";
        if (day < 10)
            d = "0" + day;
        else
            d = Integer.toString(day);

        String returnDate = Integer.toString(year) + "-" + m + "-" + d;

        return returnDate;
    }

    public static String getCurrentDateWithDash() {
        Date current = new Date(System.currentTimeMillis());
        return dateSqlFormat_dash.format(current);
    }

    public static String getDateToString(java.sql.Date date) {

        if (date == null || date.equals("")) {
            return getCurrentDate();
        }
        return defaultDateFormat.format(date);
    }

    public static String getCurrentPlusSomeDate(int _aFewDays) {

        long aFewDaysMillis = (long) _aFewDays * (long) 24 * (long) 60 * (long) 60 * (long) 1000;

        Date current = new Date(System.currentTimeMillis() + aFewDaysMillis);
        return dateFormat.format(current);
    }
    
    public static String getOneDayPlusSomeDate(String _oneDay, int _aFewDays) {

        _oneDay = getUnFormattedDateStr(_oneDay);
        Date now = new Date(System.currentTimeMillis());
        String currentDate = dateFormat.format(now);

        long interval = Long.parseLong(_oneDay) - Long.parseLong(currentDate);

        interval += _aFewDays;

        long intervalMillis = interval * (long) 24 * (long) 60 * (long) 60 * (long) 1000;

        Date current = new Date(System.currentTimeMillis() + intervalMillis);
        return defaultDateFormat.format(current);
    }
    public static String getPlusDay(String _oneDay, int _aFewDays) {

        _oneDay = FormatUtil.deleteDash(_oneDay);
        _oneDay = FormatUtil.deleteSlash(_oneDay);

        String res;

        int year = Integer.parseInt(_oneDay.substring(0, 4));
        int month = Integer.parseInt(_oneDay.substring(4, 6));
        int day = Integer.parseInt(_oneDay.substring(6, 8));

        Calendar wCal = Calendar.getInstance();
        wCal.set(year, month - 1, day);
        wCal.add(Calendar.DATE, _aFewDays);

        year = wCal.get(Calendar.YEAR);
        month = wCal.get(Calendar.MONTH) + 1;
        day = wCal.get(Calendar.DATE);

        res = Integer.toString(year);

        if (month < 10)
            res += "0" + Integer.toString(month);
        else
            res += Integer.toString(month);

        if (day < 10)
            res += "0";

        res += Integer.toString(day);

        return toDateFormat(res);
    }

    public static String getCurrentDateDBFormat() {
        Date current = new Date(System.currentTimeMillis());
        return dateFormatDB.format(current);
    }

    public static Date getCurrentDateFormat() {
        Date current = new Date(System.currentTimeMillis());
        return current;
    }

    public static java.sql.Date getCurrentDBDate() {
        java.sql.Date current = new java.sql.Date(System.currentTimeMillis());
        return current;
    }

    public static String getCurrentTime() {
        Date current = new Date(System.currentTimeMillis());
        return timeFormat.format(current);
    }

    public static String getLogCurrentTime() {
        Date current = new Date(System.currentTimeMillis());
        return logFormat.format(current);
    }

    public static String getCurrentHour() {
        Date current = new Date(System.currentTimeMillis());
        return timeFormat.format(current).substring(0, 2);
    }

    public static String getCurrentMinute() {
        Date current = new Date(System.currentTimeMillis());
        return timeFormat.format(current).substring(3, 5);
    }

    public static String getHour(java.sql.Date date) {
        return timeFormat.format(date).substring(0, 2);
    }

    public static String getMinute(java.sql.Date date) {
        return timeFormat.format(date).substring(3, 5);
    }

    public static String getAmPm(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        String res;

        if (hour >= 12)
            res = "PM";
        else
            res = "AM";

        return res;
    }

    public static String toDateFormat(String _srcDate) {

        if (_srcDate == null || _srcDate.length() != 8)
            return _srcDate;
        return _srcDate.substring(0, 4) + "-" + _srcDate.substring(4, 6) + "-" + _srcDate.substring(6);
    }

    public static String toTimeFormat(String _srcTime) {

        if (_srcTime == null || _srcTime.length() != 4)
            return _srcTime;
        return _srcTime.substring(0, 2) + ":" + _srcTime.substring(2, 4);

    }

    public static String getDiffTime(String _fromDate, String _fromTime, String _toDate, String _toTime) {
// 		logger.debug(" DateHandler.getDiffTime. _fromDate:["  + _fromDate + "] _fromTime:["  + _fromTime + "] _toDate:[" + _toDate + "] _toTime:[" + _toTime + "]");
        if (_fromDate == null || _fromTime == null || _toDate == null || _toTime == null
            || _fromDate.trim().length() != 8 || _fromTime.trim().length() != 6 || _toDate.trim().length() != 8 ||
            _toTime.trim().length() != 6)
            return null; 

        Calendar cal = new GregorianCalendar();
        int year = Integer.parseInt(_fromDate.substring(0, 4));
        int month = Integer.parseInt(_fromDate.substring(4, 6));
        int day = Integer.parseInt(_fromDate.substring(6));
        int hour = Integer.parseInt(_fromTime.substring(0, 2));
        int minute = Integer.parseInt(_fromTime.substring(2, 4));
        int second = Integer.parseInt(_fromTime.substring(4));
        cal.set(year, month, day, hour, minute, second);
        long fromTimeL = cal.getTime().getTime();

        year = Integer.parseInt(_toDate.substring(0, 4));
        month = Integer.parseInt(_toDate.substring(4, 6));
        day = Integer.parseInt(_toDate.substring(6));
        hour = Integer.parseInt(_toTime.substring(0, 2));
        minute = Integer.parseInt(_toTime.substring(2, 4));
        second = Integer.parseInt(_toTime.substring(4));
        cal.set(year, month, day, hour, minute, second);
        long toTimeL = cal.getTime().getTime();

        long KST_DIFF = 9 * 60 * 60 * 1000;
        return new Time(toTimeL - fromTimeL - KST_DIFF).toString();

    }

    public static int getDiffDay(String s, String s1) {
        try {
            SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
            Date end = formater.parse(s1);
            Date first = formater.parse(s);
            long diff = end.getTime() - first.getTime();
            return (int)(diff / 60 / 60 / 24 / 1000);
        } catch(java.text.ParseException e) {
			logger.error("getDiffDay(String, String)", e); //$NON-NLS-1$
            return 0;
        }
    }

    static long getMillis(String _cal) {

        int idx1 = _cal.indexOf("time");
        int idx2 = _cal.indexOf(",");
        return Long.parseLong(_cal.substring(idx1 + 5, idx2));
    }

    static public String toDisplayFormat(String _dbDate) {
        if (_dbDate == null || "".equals(_dbDate.trim()))
            return "";
        if (_dbDate.trim().equals("") || _dbDate.length() != 8)
            return _dbDate;
        return _dbDate.substring(0, 4) + "-" + _dbDate.substring(4, 6) + "-" + _dbDate.substring(6);
    }

    static public String toDisplayFormat(String _dbDate, String sep) {
        if (_dbDate == null)
            return null;
        if (_dbDate.trim().equals("") || _dbDate.length() != 8)
            return _dbDate;
        return _dbDate.substring(0, 4) + sep + _dbDate.substring(4, 6) + sep + _dbDate.substring(6);
    }

    static public String toDisplayFormat2(String _dbDate) {
        if (_dbDate.length() != 6)
            return _dbDate;
        return _dbDate.substring(0, 4) + "-" + _dbDate.substring(4);
    }


    static public String toDBFormat(String _displayDate) {
        if (_displayDate == null)
            return null;
        if (_displayDate.length() != 10)
            return _displayDate;
        return _displayDate.substring(0, 4) + _displayDate.substring(5, 7) + _displayDate.substring(8);
    }

    static public String toDBFormat2(String _displayDate) {
        if (_displayDate.length() != 7)
            return _displayDate;
        return _displayDate.substring(0, 4) + _displayDate.substring(5, 7);
    }

    public static boolean compareDate(java.sql.Date date1, java.sql.Date date2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        String str1 = formatter.format(date1);
        String str2 = formatter.format(date2);
        if (str1.equals(str2))
            return true;
        else
            return false;
    }

    public static String getTommorrow(String today) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.parseInt(today.substring(0, 4)), Integer.parseInt(today.substring(4, 6)) - 1,
                Integer.parseInt(today.substring(6, 8)));
        cal.add(Calendar.DATE, 1);

        String curMonth, curDay;
        int tmpMonth, tmpDay;

        tmpMonth = cal.get(Calendar.MONTH) + 1;
        tmpDay = cal.get(Calendar.DATE);

        if (tmpMonth < 10)
            curMonth = "0" + String.valueOf(tmpMonth);
        else
            curMonth = String.valueOf(tmpMonth);

        if (tmpDay < 10)
            curDay = "0" + String.valueOf(tmpDay);
        else
            curDay = String.valueOf(tmpDay);

        return (Integer.toString(cal.get(Calendar.YEAR)) + curMonth + curDay);
    }

    public static String getMonthFormat(String date) {
        date = FormatUtil.deleteDash(date);
        return (date.substring(0, 4) + "-" + date.substring(4, 6));
    }

    public static int getYear(String date) {
        date = FormatUtil.deleteSlash(date);
        date = FormatUtil.deleteDash(date);
        return Integer.parseInt(date.substring(0, 4));
    }

    public static int getMonth(String date) {
        date = FormatUtil.deleteSlash(date);
        date = FormatUtil.deleteDash(date);
        return Integer.parseInt(date.substring(4, 6));
    }

    public static int getDay(String date) {
        date = FormatUtil.deleteSlash(date);
        date = FormatUtil.deleteDash(date);
        return Integer.parseInt(date.substring(6, 8));
    }

    public static String getCurrentMonthOnly() {
        int month = getMonth(getCurrentDate());
        String result = "";
        if (month < 10)
            result = "0" + month;
        else
            result = Integer.toString(month);
        return result;
    }

    public static String getCurrentMonth(String date) {
        String currentMonth;
        date = FormatUtil.deleteSlash(date);

        int year = getYear(date);
        int month = getMonth(date);

        Calendar tmpCalendar = Calendar.getInstance();

        currentMonth = Integer.toString(tmpCalendar.get(Calendar.YEAR)) ;

        if ( (tmpCalendar.get(Calendar.MONTH) + 1) < 10)
            currentMonth += "0";

        currentMonth += Integer.toString( (tmpCalendar.get(Calendar.MONTH)) + 1);

        return currentMonth;
    }

    public static String getHour(String time) {
        int hour = Integer.parseInt(time.substring(0, 2));
        String ret_hour = null;

        if (hour > 12) {
            if (hour < 10)
                ret_hour = "0" + Integer.toString(hour - 12);
            else
                ret_hour = Integer.toString(hour - 12);
        } else {
            if (hour < 10)
                ret_hour = "0" + Integer.toString(hour);
            else
                ret_hour = time.substring(0, 2);
        }

        return ret_hour;
    }

    public static String getWeeklyStartDate(String date)  throws java.text.ParseException{
        Date tmpDate = defaultDateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpDate);

        cal.add(Calendar.DATE, 1 - cal.get(Calendar.DAY_OF_WEEK));

		Date firstDay = cal.getTime();
        return defaultDateFormat.format(firstDay);
    }

    public static String getWeeklyStartDate(String date, String toFormat)  throws java.text.ParseException{
        Date tmpDate = defaultDateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpDate);

        cal.add(Calendar.DATE, 1 - cal.get(Calendar.DAY_OF_WEEK));

		Date firstDay = cal.getTime();
		SimpleDateFormat formatter = new SimpleDateFormat(toFormat);

        return formatter.format(firstDay);
    }

    public static int getDayOfWeek(int _year, int _month, int _day) {
        int year, month, date;

        Calendar cal = Calendar.getInstance();
        cal.set(_year, _month - 1, _day);

        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static String getWeeklyEndDate(String date)  throws java.text.ParseException{
		Date tmpDate = defaultDateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpDate);

        cal.add(Calendar.DATE, 7 - cal.get(Calendar.DAY_OF_WEEK));

		Date endDate = cal.getTime();
        return defaultDateFormat.format(endDate);
    }

    public static String getWeeklyEndDate(String date, String toFormat)  throws java.text.ParseException{
		Date tmpDate = defaultDateFormat.parse(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(tmpDate);

        cal.add(Calendar.DATE, 7 - cal.get(Calendar.DAY_OF_WEEK));

		Date endDate = cal.getTime();

		SimpleDateFormat formatter = new SimpleDateFormat(toFormat);

        return formatter.format(endDate);
    }

    public static String getSatDate(String date) throws java.text.ParseException{
        return getPlusDay(getWeeklyEndDate(date), 1);
    }

    public static String getMonDate(String date) throws java.text.ParseException{
        return getPlusDay(getWeeklyStartDate(date), 1);
    }

    public static String getCurrentDate(String pattern, int amount) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        java.util.Date today = new java.util.Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, amount);
        java.util.Date tmpDay = cal.getTime();

        return sdf.format(tmpDay);
    }

    public static int getLastDayOfWeek(int year, int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 0);

        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

    public static String[] getStartAndEndWeekOfMonth(int year, int month, int week, String format) {

        SimpleDateFormat formater = new SimpleDateFormat(format);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DATE, 0);

        int lastWeek = calendar.get(Calendar.WEEK_OF_MONTH);
        if(week > lastWeek)
            return null;

        String lastDate = formater.format(calendar.getTime());

        String[] result = new String[2];
        calendar.set(Calendar.WEEK_OF_MONTH, week);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        if (week == 1)
            calendar.set(Calendar.DATE, 1);

        result[0] = formater.format(calendar.getTime());

        if(lastWeek == week){
            result[1] = lastDate;
        }else{
            calendar.set(Calendar.DAY_OF_WEEK, 7);
            result[1] = formater.format(calendar.getTime());
        }
        return result;
    }

    public static void main(String arg[]) {
//        ArrayList rows = DateUtil.listMonthlyDate("2004-09");
//        for(Iterator i = rows.iterator(); i.hasNext();){
//            ArrayList row = (ArrayList)i.next();
//            for(Iterator j = row.iterator(); j.hasNext();){
//                //logger.debug(j.next());
//            }
//        }
		String strDate = DateUtil.formatTimeStamp(System.currentTimeMillis(), "yyyyMMdd HH:mm:ss");
		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - strDate=" + strDate); //$NON-NLS-1$
		}
		
		String strOrg = "2002-09-20";
		Date dtNew = DateUtil.getDateWithDelimiter(strOrg, '-' );
		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - dtNew=" + dtNew.toString()); //$NON-NLS-1$
		}
    }
    
	public static String getDateToString(String yymmdd) {
		String yy_mm_dd = "";
		if (yymmdd.length() == 8){
			yy_mm_dd = yymmdd.substring(0,4) + "-" + yymmdd.substring(5,2) + "-" + yymmdd.substring(7,2);
		}
		return yy_mm_dd;
	}
}
