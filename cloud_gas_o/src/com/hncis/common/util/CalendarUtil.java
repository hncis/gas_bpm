package com.hncis.common.util;

import java.text.SimpleDateFormat;
import java.util.*;
/**
 * 달력 유틸
 * @author developer
 *
 */
public  class CalendarUtil {

	
	//Calendar cal=Calendar.getInstance() ;
	public static Calendar cal(){
		return Calendar.getInstance();
	}
	
	
	
	/**
	 * 
	 * @return 현재 년도
	 */
	public static int getCurrentYear(){
		return cal().get(Calendar.YEAR);
	}
	
	/**
	 * 
	 * @return 현재 월
	 */
	public static int getCurrentMonth(){
		return cal().get(Calendar.MONTH)+1;	
	}
	
	/**
	 * 
	 * @return 현재 일
	 */
	public static int getCurrentDay(){
		return cal().get(Calendar.DATE);
	}
	
	/**
	 * 
	 * @param year  요구할 년도
	 * @param month 월 
	 * @return 요구 년도
	 */
	public static int getYear(int year, int month){
		Calendar cal = cal();
		cal.set(year, month-1, 1);
		return cal.get(Calendar.YEAR);
	}
	
	/**
	 * 
	 * @param year 
	 * @param month 
	 * @return 요구한 월
	 */
	public static int getMonth(int year, int month){
		Calendar cal = cal();
		cal.set(year, month-1, 1);
		return cal.get(Calendar.MONTH)+1;
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @return 요구한 첫달 첫요일
	 */
	public static int getWeek(int year, int month){
		Calendar cal = cal();
		cal.set(year, month-1, 1);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @return 마지막날짜
	 */
	public static int getLastDay(int year,int month){
		Calendar cal = cal();
			cal.set(year, month-1,1);
		return cal.getActualMaximum(Calendar.DATE);
	}
	
	public static Date getDataTimes(int year,int month,int day){
		Calendar cal = cal();
		cal.set(year, month-1, day);
		System.out.println(cal.getTime());
		return cal.getTime();
	}
	
	/**
	 * 
	 * @param format
	 * @return
	 */
	public static String dateFormet(String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		
		String result = formatter.format(cal().getTime());
		return result;
	}
	public static String dateFormet(String format,Date a){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		
		String result = formatter.format(a);
		return result;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
       //System.out.println(CalendarUtil.dateFormet("\\yyyyMM"));
       System.out.println(Calendar.SUNDAY);
       System.out.println( CalendarUtil.dateFormet("yyyyMMdd", CalendarUtil.getDataTimes(2010,5,22)));
	}

}
