package com.hncis.common.util;

/**
 * 유형 설명을 삽입하십시오.
 * 작성 날짜: (2001-11-16 오전 11:54:02)
 * @author: Administrator
 */
import java.sql.Date;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FormatHelper {
    private transient static Log logger = LogFactory.getLog(FormatHelper.class.getClass());

    private static final String zero3 = "000";
    private static final String zero4 = "0000";
    
	private static NumberFormat nf = NumberFormat.getInstance(Locale.KOREAN);
	private static final int MAX_SCALE = 4;

	/**
	 * Packed-Decimal을 숫자형식의 String으로 변환한다.
	 *
	 * @param src 숫자형식의 String으로 변환할 byte배열 @param start byte배열에서 변환하고자 하는
	 * packet의 시작 인덱스 @param len byte배열에서 변환하고자 하는 packet의 길이
	 *
	 * @return String 숫자형식의 String @exception
	 */
	public static String getConvertedPackDecimal(byte[] src, int start, int len) {

		boolean amIMinus = false;
		StringBuffer sf = new StringBuffer();
		int end = start + len;

		for (int i = start; i < end; i++) {
			byte b = src[i];
			int lower = b & 0x0f;
			int upper = (b >> 4) & 0x0f;
			if (i == end - 1) {
				sf.append(upper);

				if (lower == 13) { // 음수
					amIMinus = true;
				}
			} else {
				sf.append((upper + "" + lower));
			}
		}

		String str = sf.toString();
		if (amIMinus) {
			str = "-" + str;
		}

		return str;
	}

	// 소수점 이하 자리에서 반올림하기..
	public static double getRound(double s, int n) {
		double t = 1;
		for (int i = 0; i < n; i++) {
			t *= 10;
		}

		return ((long) Math.round(s * t)) / t;
	}

	// 소수점 이하 자리에서 자르기..
	public static double getTrunc(double s, int n) {
		double t = 1;
		for (int i = 0; i < n; i++) {
			t *= 10;
		}
		return ((long) (s * t)) / t;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오전 11:54:45)
	 * 
	 * @param args
	 *            java.lang.String[]
	 */
	public static void main(String[] args) {
		// getCellPhoneNumber("010-3434=3411");
		// getCellPhoneNumber("0101231234");
		// getCellPhoneNumber("01012341234");
	}

	/**
	 * 어떤 형식의 핸드폰 번호라도 분리하여 배열로 만듬
	 * 
	 * @param hp_no
	 *            ex) 010-2343-1234
	 * @return 분리된 핸드폰 번호 배열
	 */
	public static String[] getCellPhoneNumber(String telNo) {

		String hpNo[] = new String[3];

		byte telStr[] = telNo.getBytes();

		int i, j = 0;
		int start = 0, finish = 0;

		try {
			if (telNo.length() >= 12) {
				for (i = 0; i < telStr.length; i++) {
					if (telStr[i] < 48 || telStr[i] > 57) {
						finish = i;
						hpNo[j] = telNo.substring(start, finish);
						start = i + 1;
						j++;
					}
				}
				hpNo[j] = telNo.substring(start, i);
			} else if (telNo.length() == 11) {
				hpNo[0] = telNo.substring(0, 3);
				hpNo[1] = telNo.substring(3, 7);
				hpNo[2] = telNo.substring(7, 11);
			} else if (telNo.length() == 10) {
				hpNo[0] = telNo.substring(0, 3);
				hpNo[1] = telNo.substring(3, 6);
				hpNo[2] = telNo.substring(6, 10);
			} else {
				hpNo[0] = zero3;
				hpNo[1] = zero4;
				hpNo[2] = zero4;
			}

		} catch (Exception e) {
			logger.error("messege", e);
			hpNo[0] = zero3;
			hpNo[1] = zero4;
			hpNo[2] = zero4;
		}

		return hpNo;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오전 11:56:37) kkw int -> String
	 * yyyymmdd -> yyyy*mm*dd
	 */
	public static String strDate(int iyyyymmdd, String separator) {
		String rtnDate = "";
		String yyyymmdd = String.valueOf(iyyyymmdd);

		if (yyyymmdd.length() == 3) {
			yyyymmdd = zero3 + yyyymmdd;
		} else if (yyyymmdd.length() == 4) {
			yyyymmdd = "00" + yyyymmdd;
		} else if (yyyymmdd.length() == 5) {
			yyyymmdd = "0" + yyyymmdd;
		}

		if (yyyymmdd.length() == 6) {
			if (Integer.parseInt(yyyymmdd.substring(0, 2)) > 50) {
				yyyymmdd = "19" + yyyymmdd;
			} else {
				yyyymmdd = "20" + yyyymmdd;
			}
		}

		if (yyyymmdd.length() == 6) {
			rtnDate = yyyymmdd.substring(0, 4) + separator + yyyymmdd.substring(4, 6);
		} else if (yyyymmdd.length() == 8) {
			rtnDate = yyyymmdd.substring(0, 4) + separator + yyyymmdd.substring(4, 6) + separator
					+ yyyymmdd.substring(6, 8);
		} else {
			rtnDate = yyyymmdd;
		}

		if (rtnDate.equals("0")) {
			rtnDate = "";
		}
		return rtnDate;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오전 11:56:37) kkw long -> String
	 * yyyymmdd -> yyyy*mm*dd
	 */
	public static String strDate(long lyyyymmdd, String separator) {
		String rtnDate = "";
		String yyyymmdd = String.valueOf(lyyyymmdd);

		if (yyyymmdd.length() == 3) {
			yyyymmdd = zero3 + yyyymmdd;
		} else if (yyyymmdd.length() == 4) {
			yyyymmdd = "00" + yyyymmdd;
		} else if (yyyymmdd.length() == 5) {
			yyyymmdd = "0" + yyyymmdd;
		}

		if (yyyymmdd.length() == 6 && (!yyyymmdd.substring(0, 2).equals("19") || !yyyymmdd.substring(0, 2).equals("20"))) {
			if (Integer.parseInt(yyyymmdd.substring(0, 2)) > 50) {
				yyyymmdd = "19" + yyyymmdd;
			} else {
				yyyymmdd = "20" + yyyymmdd;
			}
		}

		if (yyyymmdd.length() == 6) {
			rtnDate = yyyymmdd.substring(0, 4) + separator + yyyymmdd.substring(4, 6);
		} else if (yyyymmdd.length() == 8) {
			rtnDate = yyyymmdd.substring(0, 4) + separator + yyyymmdd.substring(4, 6) + separator
					+ yyyymmdd.substring(6, 8);
		} else {
			rtnDate = yyyymmdd;
		}

		if (rtnDate.equals("0")) {
			rtnDate = "";
		}
		return rtnDate;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오전 11:56:37) kkw String -> String
	 * yyyy-mm-dd -> yyyy*mm*dd yyyymmdd -> yyyy*mm*dd
	 */
	public static String strDate(String yyyymmdd, String separator) {
		if (yyyymmdd.length() == 6){
			return yyyymmdd.substring(0, 4) + separator + yyyymmdd.substring(4, 6);
		}else if (yyyymmdd.length() == 8){
			return yyyymmdd.substring(0, 4) + separator + yyyymmdd.substring(4, 6) + separator
					+ yyyymmdd.substring(6, 8);
		}else if (yyyymmdd.length() == 10) {
			return yyyymmdd.substring(0, 4) + separator + yyyymmdd.substring(5, 7) + separator
					+ yyyymmdd.substring(8, 10);
		} else
			return yyyymmdd;
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오전 11:56:37) kkw java.sql.Date ->
	 * String yyyy-mm-dd -> yyyy*mm*dd
	 */
	public static String strDate(Date dyyyymmdd, String separator) {
		String yyyymmdd = String.valueOf(dyyyymmdd);

		if (yyyymmdd.length() == 10){
			return yyyymmdd.substring(0, 4) + separator + yyyymmdd.substring(5, 7) + separator
					+ yyyymmdd.substring(8, 10);
		}else{
			return yyyymmdd;
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오후 12:58:56)
	 * 
	 * @return java.lang.String kkw Double -> String 24783456.21 ->
	 *         24,783,456.21 123456789012.1234 => 123,456,789,012.1234
	 */
	public static String strNum(double number) {
		nf.setMaximumFractionDigits(MAX_SCALE);
		return nf.format(number);
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.Double
	 * @param format
	 *            java.lang.String
	 * @exception java.lang.Exception
	 *                예외 설명.
	 */
	public static String strNum(double str, String format) throws java.lang.Exception {
		NumberFormat df = new java.text.DecimalFormat(format);
		return df.format(str);
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오후 12:58:56)
	 * 
	 * @return java.lang.String kkw float -> String 24783456.21 -> 24,783,456.21
	 *         24783456.2134 -> 24,783,456.2134
	 * 
	 */
	public static String strNum(float number) {
		nf.setMaximumFractionDigits(MAX_SCALE);
		return nf.format(number);
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param str
	 *            float
	 * @param format
	 *            java.lang.String
	 * @exception java.lang.Exception
	 *                예외 설명.
	 */
	public static String strNum(float str, String format) throws java.lang.Exception {
		return strNum(String.valueOf(str), format);
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오후 12:58:56)
	 * 
	 * @return java.lang.String kkw long -> String 24783456 -> 24,783,456
	 * 
	 */
	public static String strNum(int number) {
		nf.setMaximumFractionDigits(MAX_SCALE);
		return nf.format(number);
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param str
	 *            int
	 * @param format
	 *            java.lang.String
	 * @exception java.lang.Exception
	 *                예외 설명.
	 */
	public static String strNum(int str, String format) throws java.lang.Exception {
		return strNum(String.valueOf(str), format);
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오후 12:58:56)
	 * 
	 * @return java.lang.String kkw long -> String 24783456 -> 24,783,456
	 * 
	 */
	public static String strNum(long number) {
		nf.setMaximumFractionDigits(MAX_SCALE);
		return nf.format(number);
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param str
	 *            int
	 * @param format
	 *            java.lang.String
	 * @exception java.lang.Exception
	 *                예외 설명.
	 */
	public static String strNum(long str, String format) throws Exception {
		return strNum(String.valueOf(str), format);
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2001-11-16 오후 12:58:56)
	 * 
	 * @return java.lang.String kkw String -> String "24783456" -> 24,783,456
	 *         "24783456.21" -> 24,783,456.21 "24783456.2134" -> 24,783,456.2134
	 */
	public static String strNum(String number) {
		if (number == null || number.equals("")) {
			return "";
		}
		nf.setMaximumFractionDigits(MAX_SCALE);
		return nf.format(Double.parseDouble(number));
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param str
	 *            java.lang.String
	 * @param format
	 *            java.lang.String
	 * @exception java.lang.Exception
	 *                예외 설명.
	 */
	public static String strNum(String str, String format) throws java.lang.Exception {
		try {
			if (str.trim() == null || str.trim().equals("")) {
				str = "0";
			}
			str = trimChar(str, ",").trim();
			if (Double.parseDouble(str) == 0 && format.indexOf("0") == -1) {
				return "";
			} else {
				return strNum(new java.math.BigDecimal(str).doubleValue(), format);
			}
		} catch (Exception e) {
			throw e;
		}

	}

	/**
	 * Function : ch 에 해당하는 문자를 제거한다. Write date : (2001-08-27 오후 5:07:23)
	 * VAJ@author: 곽주용
	 * 
	 * @return java.lang.String
	 * @param date
	 *            java.lang.String
	 * @param ch
	 *            java.lang.String
	 */
	public static String trimChar(String date, String ch) {

		String _temp = "";
		try {
			for (int i = 0; i < date.length(); i++) {
				if (!date.substring(i, i + 1).equals(ch)) {
					_temp = _temp + date.substring(i, i + 1);
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			throw e;
		}
		return _temp;
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param param
	 *            double
	 * @exception java.lang.Exception
	 *                예외 설명.
	 */
	public static String strNumUsage(double param) throws java.lang.Exception {
		return strNumUsage(String.valueOf(param));
	}

	/**
	 * 
	 * @return java.lang.String
	 * @param param
	 *            java.lang.String
	 * @exception java.lang.Exception
	 *                예외 설명.
	 */
	public static String strNumUsage(String param) throws java.lang.Exception {

		double num = Double.parseDouble(param);

		if (num == 0) {
			return "";
		} else if (param.indexOf(".") == -1) {
			return strNum(param, "###,##0");
		} else {
			return strNum(param.substring(0, param.indexOf(".")), "###,##0");
		}
	}

	/**
	 * 메소드 설명을 삽입하십시오. 작성 날짜: (2002-05-30) 문기훈 String -> String hhmmss ->
	 * hh*mm*ss hhmmss -> hh*mm*ss
	 */
	public static String strTime(String hhmmss, String separator) {
		if (hhmmss.length() == 4){
			return hhmmss.substring(0, 2) + separator + hhmmss.substring(2, 4);
		}else if (hhmmss.length() == 6){
			return hhmmss.substring(0, 2) + separator + hhmmss.substring(2, 4) + separator + hhmmss.substring(4, 6);
		}else if (hhmmss.length() == 8){
			return hhmmss.substring(0, 2) + separator + hhmmss.substring(3, 5) + separator + hhmmss.substring(6, 8);
		}else{
			return hhmmss;
		}
	}

	public static long getTrunc(String s, int n) {
		double ss = Double.parseDouble(s);
		long t = 1;
		for (int i = 0; i < n; i++) {
			t *= 10;
		}
		return ((long) (ss * t)) / t;
	}
}