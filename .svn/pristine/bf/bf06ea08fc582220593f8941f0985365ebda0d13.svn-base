package com.hncis.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.io.ClassPathResource;

import com.hncis.common.application.CommonGasc;
import com.hncis.common.application.SessionInfo;

/**
 *  Class Name  : StringUtil.java<br/>
 *  Description : 문자열과 관련된 메서드를 정의한 공통 Util Class.<br/><br/>
 *
 *  Modification Information<br/>
 *
 *  <pre>
 *    수정일         수정자                        수정내용
 *   -------      --------    --------------------------------------------------
 *   2006.02.06   송명환       최초 생성
 *   2006.02.13   송명환       고도화 요구사항에 따라 메서드 추가
 *  </pre>
 *
 *  @since 2006. 2. 06
 *  @version 1.0
 *  @author 통합처리공통지원 시스템 - 공통개발1팀
 *  <br/><br/>
 */

public class StringUtil {

    /**
     * 빈 문자열 <code>""</code>.
     */
    public static final String    EMPTY     = "";

    /**
     * <p>The maximum size to which the padding constant(s) can expand.</p>
     */
    private static final int      PAD_LIMIT = 8192;

    /**
     * <p>An array of <code>String</code>s used for padding.</p>
     *
     * <p>Used for efficient space padding. The length of each String expands as needed.</p>
     */
    private static final String[] PADDING   = new String[Character.MAX_VALUE];

    static {
        // space padding is most common, start with 64 chars
        PADDING[32] = "                                                                ";
    }

    /**
     * String 문자열을 원하는 위치를 한글과 영문을 구분해서 갯수별로
     * 잘라내고 뒤에 구분자 붙여주는 메소드.
     * @param String s
     * @param int len
     * @param String tail
     * @return String
     */
    public static String truncateNicely(String s, int len, String tail) {
        if(s == null)
            return null;

        int srcLen = realLength(s);
        if(srcLen < len)
            return s;

        String tmpTail = tail;
        if(tail == null)
            tmpTail = "";

        int tailLen = realLength(tmpTail);
        if(tailLen > len)
            return "";

        char a;
        int i = 0;
        int realLen = 0;
        for(i = 0; i < len - tailLen && realLen < len - tailLen; i++) {
            a = s.charAt(i);
            if((a & 0xFF00) == 0)
                realLen += 1;
            else
                realLen += 2;
        }

        while(realLength(s.substring(0, i)) > len - tailLen) {
            i--;
        }

        return s.substring(0, i) + tmpTail;
    }

    public static int realLength(String s) {
        return s.getBytes().length;
    }

    /**
     * 원본 문자열의 포함된 특정 문자열을 새로운 문자열로 변환하는 메서드
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replace(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        while(source.indexOf(subject) >= 0) {
            preStr = source.substring(0, source.indexOf(subject));
            nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
            source = nextStr;
            rtnStr.append(preStr).append(object);
        }
        rtnStr.append(nextStr);
        return rtnStr.toString();
    }

    /**
     * 원본 문자열의 포함된 특정 문자열 첫번째 한개만 새로운 문자열로 변환하는 메서드
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열 / source 특정문자열이 없는 경우 원본 문자열
     */
    public static String replaceOnce(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        if(source.indexOf(subject) >= 0) {
            preStr = source.substring(0, source.indexOf(subject));
            nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
            rtnStr.append(preStr).append(object).append(nextStr);
            return rtnStr.toString();
        } else {
            return source;
        }
    }

    /**
     * <code>subject</code>에 포함된 각각의 문자를 object로 변환한다.
     *
     * @param source 원본 문자열
     * @param subject 원본 문자열에 포함된 특정 문자열
     * @param object 변환할 문자열
     * @return sb.toString() 새로운 문자열로 변환된 문자열
     */
    public static String replaceChar(String source, String subject, String object) {
        StringBuffer rtnStr = new StringBuffer();
        String preStr = "";
        String nextStr = source;
        char chA;

        for(int i = 0; i < subject.length(); i++) {
            chA = subject.charAt(i);

            if(source.indexOf(chA) >= 0) {
                preStr = source.substring(0, source.indexOf(chA));
                nextStr = source.substring(source.indexOf(chA) + 1, source.length());
                source = rtnStr.append(preStr).append(object).append(nextStr).toString();
            }
        }
        return source;
    }

    /**
     * 문자열의 CharSet을 unicode(8859_1)로 바꾸는 메서드
     * @param source 원본 문자열
     * @return result 유니코드로 변환된 문자열
     */
    public static String toUnicode(String source) {
        String ret = null;
        if(source == null)
            return null;
        try {
            ret = new String(source.getBytes(), "8859_1");
        } catch(UnsupportedEncodingException e) {
            ret = null;
        }
        return ret;
    }

    /**
     * 문자열의 CharSet을 EUC-KR(KSC5601)로 바꾸는 메서드.
     * @param source 원본 문자열
     * @return result EUC-KR로 변환된 문자열
     */
    public static String toEuckr(String source) {
        String ret = null;
        if(source == null)
            return null;
        try {
            ret = new String(source.getBytes(), "KSC5601");
        } catch(UnsupportedEncodingException e) {
            ret = null;
        }
        return ret;
    }

    /**
     * 문자열의 unicode(8859_1) CharSet을 넘겨 받아 EUC-KR(KSC5601)로 바꾸는 메서드.
     * @param source unicode(8859_1) 원본 문자열
     * @return result EUC-KR로 변환된 문자열
     */
    public static String toEuckr2(String source) {
        String ret = null;
        if(source == null)
            return null;
        try {
            ret = new String(source.getBytes("8859_1"), "KSC5601");
        } catch(UnsupportedEncodingException e) {
            ret = null;
        }
        return ret;
    }

    /**
     * 문자열을 원하는 형태의 CharSet으로 바꾸는 메서드.
     * @param source 원본 문자열
     * @param charset CharSet
     * @return result 지정 charset으로 변환된 문자열
     */
    public static String toCharset(String source, String charset) {
        String ret = null;
        if(source == null)
            return null;
        try {
            ret = new String(source.getBytes("KSC5601"), charset);
        } catch(UnsupportedEncodingException e) {
            ret = null;
        }
        return ret;
    }

    /**
     * DB에 넣기 위해 single quotation 입력을 처리하는 메서드
     * @param source 원본 문자열
     * @return single quotation 처리된 문자열
     */
    public static String procQuotation(String source) {
        String ret = null;
        if(source == null)
            return null;
        ret = replace(source, "'", "''");
        return ret;
    }

    /**
     * single quotation 입력을 처리하고 앞뒤로 single quotation을 추가하는 메서드
     * @param source 원본 문자열
     * @return single quotation 처리된 문자열
     */
    public static String autoQuotation(String source) {
        return "'" + procQuotation(source) + "'";
    }

    /**
     * String의 공백을 제거한다.
     * @param source 원본 문자열
     * @return null이 아닌 경우 공백 제거, null인 경우 empty String 리턴
     */
    public static String isNullTrim(String source) {
        if(source != null) {
            return source.trim();
        } else {
            return "";
        }
    }

    /**
     * 문자열이 null인지 확인하고 null인 경우 지정된 문자열로 바꾸는 메서드
     * @param source 원본 문자열
     * @param value null일 경우 바뀔 문자열
     * @return resultVal 문자열
     */
    public static String isNullToString(String source, String value) {
        String retVal;
        if(source == null || source.length() < 1) {
            retVal = value;
        } else {
            retVal = source;
        }
        retVal = replace(retVal, "'", "`"); // add by khko 20030908 '를 `로 바꾼다
        return retVal;
    }

    /**
     * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
     * @param object 원본 객체
     * @return resultVal 문자열
     */
    public static String isNullToString(Object object) {
        String string = "";
        if(object != null) {
            string = object.toString();
        }
        string = replace(string, "null", "");
        return string;
    }

    /**
     * 문자배열이 null인지 확인하고 null인 경우 지정된 문자열로 바꾸는 메서드
     * @param source 원본 문자배열
     * @param value null일 경우 바뀔 문자열
     * @return resultVal 문자열
     */
    public static String isNull(String[] source, String value) {
        String retVal;
        if(source == null) {
            retVal = value;
        } else {
            retVal = source[0];
        }
        return retVal;
    }

    /**
     * 문자열이 null인지 확인하고 null인 경우 지정된 문자열로 바꾸는 메서드
     * @param source 원본 문자배열
     * @param value null일 경우 바뀔 문자열
     * @return resultVal 문자열
     */
    public static String isNull2(String source, String value) {
        String str;
        char ch;
        String Result = "";
        int i;
        if(source == null) {
            str = value;
        } else {
            str = source;
        }
        try {
            for(i = 0; i < str.length(); i++) {
                ch = str.charAt(i);
                if(ch == '"') {
                    Result += "\"";
                } else if(ch == '\'') {
                    Result += "\"";
                } else {
                    Result += ch;
                }
            }
            return Result;
        } catch(Exception e) {
            e.printStackTrace();
            return Result;
        }
    }

    /**
     * 문자열이 null인지 확인하고 null인 경우 지정된 문자열로 바꾸는 함수. ',' 문자가 있는경우 제거한다.
     * @param source 원본 문자열
     * @param value null일경우 바뀔 문자열
     * @return 문자열
     */
    public static String isNull3(String source, String value) {
        String retVal;
        if(source == null || source.length() < 1) {
            retVal = value;
        } else {
            retVal = source;
        }
        retVal = replace(retVal, ",", ""); // add by khko 20030916 ','를 제거한다
        return retVal;
    }

    /**
     * 문자열을 int형으로 변환하고, null인지 확인하여 null인 경우 지정된 int로 바꾸는 메서드
     * @param source 원본 문자열
     * @param value null일 경우 바뀔 정수값
     * @return resultVal 변환된 정수
     */
    public static int isNullToInt(String source, int value) {
        int resultVal = 0;
        try {
            if(source == null || source.length() < 1) {
                resultVal = value;
            } else {
                resultVal = Integer.parseInt(source);
            }
        } catch(Exception e) {
            resultVal = value;
        }
        return resultVal;
    }

    /**
     * 문자열을 지정한 분리자에 의해 배열로 리턴하는 메서드.
     * @param source 원본 문자열
     * @param separator 분리자
     * @return result 분리자로 나뉘어진 문자열 배열
     */
    public static String[] split(String source, String separator) throws NullPointerException {
        String[] returnVal = null;
        int cnt = 1;

        int index = source.indexOf(separator);
        int index0 = 0;
        while(index >= 0) {
            cnt++;
            index = source.indexOf(separator, index + 1);
        }
        returnVal = new String[cnt];
        cnt = 0;
        index = source.indexOf(separator);
        while(index >= 0) {
            returnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = source.substring(index0);
        return returnVal;
    }

    /**
     * 문자열을 지정한 분리자에 의해 지정된 길이의 배열로 리턴하는 메서드.
     * @param source 원본 문자열
     * @param separator 분리자
     * @param arraylength 배열 길이
     * @return 분리자로 나뉘어진 문자열 배열
     */
    public static String[] split(String source, String separator, int arraylength) throws NullPointerException {
        String[] returnVal = new String[arraylength];
        int cnt = 0;
        int index0 = 0;
        int index = source.indexOf(separator);
        while(index >= 0 && cnt < (arraylength - 1)) {
            returnVal[cnt] = source.substring(index0, index);
            index0 = index + 1;
            index = source.indexOf(separator, index + 1);
            cnt++;
        }
        returnVal[cnt] = source.substring(index0);
        if(cnt < (arraylength - 1)) {
            for(int i = cnt + 1; i < arraylength; i++) {
                returnVal[i] = "";
            }
        }
        return returnVal;
    }

    /**
     * 문자열배열을 지정한 분리자에 의해 합한 문자열로 리턴하는 메서드.
     * @param source 원본 문자열 배열
     * @param separator 분리자
     * @return 분리자 합친 문자열
     */
    public static String patch(String[] source, String separator) throws NullPointerException {
        return patch(source, separator, false);
    }

    /**
     * 문자열배열을 지정한 분리자에 의해 합한 문자열로 리턴하는 메서드.
     * @param source 원본 문자열 배열
     * @param separator 분리자
     * @param isblankinsert 빈값에서 분리자를 표시할 지 여부
     * @return 분리자 합친 문자열
     */
    public static String patch(String[] source, String separator, boolean isblankinsert) throws NullPointerException {
        StringBuffer returnVal = new StringBuffer();
        int cnt = 0;
        if(source != null) {
            for(int i = 0; i < source.length; i++) {
                if(!isblankinsert) {
                    if(cnt != 0)
                        returnVal.append(separator);
                    returnVal.append(isNullToString(source[i], ""));
                    cnt++;
                } else {
                    if(isNullToString(source[i], "").length() > 0) {
                        if(cnt != 0)
                            returnVal.append(separator);
                        returnVal.append(source[i]);
                        cnt++;
                    }
                }
            }
        }
        return returnVal.toString();
    }

    /**
     * 문자열이 지정한 길이를 초과했을때 지정한길이에다가 해당 문자열을 붙여주는 메서드.
     * @param source 원본 문자열 배열
     * @param output 더할문자열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, String output, int slength) {
        String returnVal = null;
        if(source != null) {
            if(source.length() > slength) {
                returnVal = source.substring(0, slength) + output;
            } else
                returnVal = source;
        }
        return returnVal;
    }

    /**
     * 문자열이 지정한 길이를 초과했을때 해당 문자열을 삭제하는 메서드
     * @param source 원본 문자열 배열
     * @param slength 지정길이
     * @return 지정길이로 잘라서 더할분자열 합친 문자열
     */
    public static String cutString(String source, int slength) {
        String result = null;
        if(source != null) {
            if(source.length() > slength) {
                result = source.substring(0, slength);
            } else
                result = source;
        }
        return result;
    }

    /**
     * 넘겨받은 문자열의 한글 문자열 포함 여부를 체크하는 메서드
     * @param uni20 체크할 문자열
     * @return check 한글 문자열 포함 여부
     */
    public static boolean checkHan(String uni20) {
        boolean check = false;
        if(uni20 == null || uni20.equals("")) {
            return check;
        } else {
            int len = uni20.length();
            char c;
            for(int i = 0; i < len; i++) {
                c = uni20.charAt(i);
                if(c < 0xac00 || 0xd7a3 < c) {
                    check = false;
                } else {
                    check = true;
                    break;
                }
            }
        }
        return check;
    }

    /**
     * 넘겨받은 두개의 문자열을 숫자로 변환하여 곱한 값을 반환하는 메서드
     * @param a 첫번째 문자열
     * @param b 두번째 문자열
     * @return 두개의 문자열을 숫자로 변환하여 곱한 값
     */
    public static int calString(String a, String b) {
        int result = 0;
        if(a == null || a.equals("") || b == null || b.equals("")) {
            result = 0;
        } else {
            result = Integer.parseInt(isNullTrim(a)) * Integer.parseInt(isNullTrim(b));
        }
        return result;
    }

    /**
     * 숫자형 스트링을 Locale.KOREA 의 숫자형식으로 표시한다.
     *
     * @param number
     * @return 숫자형식의 문자열
     */
    public static String formatComma(String number) {
        return formatComma(Double.parseDouble(number));
    }

    /**
     * double 형 숫자를 Locale.KOREA 의 숫자형식으로 표시한다.(콤마표시)(김용훈 대리 요청)
     * 2005/06/18 다시 추가
     * @param number
     * @return 숫자형식의 문자열
     */
    public static String formatComma(double number) {
        return formatComma(number, Locale.KOREA);
    }

    /**
     * 숫자형 스트링을 locale 의 숫자형식으로 표시한다.(콤마표시)(김용훈 대리 요청)
     * 2005/06/18 다시 추가
     * @param number
     * @param locale
     * @return 숫자형식의 문자열
     */
    public static String formatComma(String number, Locale locale) {
        return formatComma(Double.parseDouble(number), locale);
    }

    /**
     * double 형 숫자를 locale 의 숫자형식으로 표시한다.(콤마표시)(김용훈 대리 요청)
     * 2005/06/18 다시 추가
     * @param number
     * @param locale
     * @return 숫자형식의 문자열
     */
    public static String formatComma(double number, Locale locale) {
        NumberFormat form = NumberFormat.getInstance(locale);
        return form.format(number);
    }

    /**
     * 더블형 타입을 pattern 에 맞게 나타나게 한다.
     * (예 : #,##0.00 )
     * @param obj
     * @param pattern
     * @return pattern 의 형식으로 리턴한다.
     */
    public static String formatDecimal(double obj, String pattern) {
        DecimalFormat form = new DecimalFormat(pattern);
        return form.format(obj);
    }

    /**
     * 자리수만큼 0을 채워준다.
     * @param length - 자리수
     * @param contents - 내용
     * @return 자리 수 만큼 0을 채운 문자열
     */
    public static String fixNumericLength(int length, String contents) {
        if(contents == null)
            contents = "";
        StringBuffer sb = new StringBuffer();
        int gap = length - contents.getBytes().length;
        for(int i = 0; i < gap; i++)
            sb.append("0");
        return sb.append(contents).toString();
    }

    // 2006. 02. 13. 시군구고도화 공통1팀(통합처리공통지원)
    // 박규태 추가
    // -----------------------------------------------------------------------
    /**
     * <p>
     * String이 비었거나("") 혹은 null인지 검증한다.
     * </p>
     *
     * <pre>
     *  StringUtil.isEmpty(null)      = true
     *  StringUtil.isEmpty(&quot;&quot;)        = true
     *  StringUtil.isEmpty(&quot; &quot;)       = false
     *  StringUtil.isEmpty(&quot;bob&quot;)     = false
     *  StringUtil.isEmpty(&quot;  bob  &quot;) = false
     * </pre>
     *
     * @param str
     *            the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>
     * String 객체의 첫번째 캐릭터를 char 원시타입으로 반환한다. 비어 있는 String
     * 객체일 때에는 Exception을 던진다.
     * </p>
     *
     * <pre>
     *    StringUtil.toChar(null) = IllegalArgumentException
     *    StringUtil.toChar(&quot;&quot;)   = IllegalArgumentException
     *    StringUtil.toChar(&quot;A&quot;)  = 'A'
     *    StringUtil.toChar(&quot;BA&quot;) = 'B'
     * </pre>
     *
     * @param str
     *            the character to convert
     * @return the char value of the first letter of the String
     * @throws IllegalArgumentException
     *             if the String is empty
     */
    public static char toChar(String str) {
        if(isEmpty(str)) {
            throw new IllegalArgumentException("The String must not be empty");
        }
        return str.charAt(0);
    }

    /**
     * <p>String 객체의 첫번째 캐릭터를 char 원시타입으로 반환하며, <code>null</code>
     * 혹은 빈 문자열("")일 경우에는 defaultValue를 반환.
     *
     * <pre>
     *   StringUtil.toChar(null, 'X') = 'X'
     *   StringUtil.toChar("", 'X')   = 'X'
     *   StringUtil.toChar("A", 'X')  = 'A'
     *   StringUtil.toChar("BA", 'X') = 'B'
     * </pre>
     *
     * @param str  the character to convert
     * @param defaultValue  the value to use if the  Character is null
     * @return the char value of the first letter of the String or the default if null
     */
    public static char toChar(String str, char defaultValue) {
        if(StringUtil.isEmpty(str)) {
            return defaultValue;
        }
        return str.charAt(0);
    }

    /**
     * <p>{@link String#toLowerCase()}를 이용하여 소문자로 변환한다.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtil.lowerCase(null)  = null
     * StringUtil.lowerCase("")    = ""
     * StringUtil.lowerCase("aBc") = "abc"
     * </pre>
     *
     * @param str  the String to lower case, may be null
     * @return the lower cased String, <code>null</code> if null String input
     */
    public static String lowerCase(String str) {
        if(str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    /**
     * <p>{@link String#toUpperCase()}를 이용하여 대문자로 변환한다.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtil.upperCase(null)  = null
     * StringUtil.upperCase("")    = ""
     * StringUtil.upperCase("aBc") = "ABC"
     * </pre>
     *
     * @param str  the String to upper case, may be null
     * @return the upper cased String, <code>null</code> if null String input
     */
    public static String upperCase(String str) {
        if(str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    /**
     * <p>입력된 String의 앞쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.
     * An empty string ("") input returns the empty string.</p>
     *
     * <p>If the stripChars String is <code>null</code>, whitespace is
     * stripped as defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtil.stripStart(null, *)          = null
     * StringUtil.stripStart("", *)            = ""
     * StringUtil.stripStart("abc", "")        = "abc"
     * StringUtil.stripStart("abc", null)      = "abc"
     * StringUtil.stripStart("  abc", null)    = "abc"
     * StringUtil.stripStart("abc  ", null)    = "abc  "
     * StringUtil.stripStart(" abc ", null)    = "abc "
     * StringUtil.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     *
     * @param str  the String to remove characters from, may be null
     * @param stripChars  the characters to remove, null treated as whitespace
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if(str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if(stripChars == null) {
            while((start != strLen) && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if(stripChars.length() == 0) {
            return str;
        } else {
            while((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
                start++;
            }
        }
        return str.substring(start);
    }

    /**
     * <p>입력된 String의 뒤쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.
     * An empty string ("") input returns the empty string.</p>
     *
     * <p>If the stripChars String is <code>null</code>, whitespace is
     * stripped as defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtil.stripEnd(null, *)          = null
     * StringUtil.stripEnd("", *)            = ""
     * StringUtil.stripEnd("abc", "")        = "abc"
     * StringUtil.stripEnd("abc", null)      = "abc"
     * StringUtil.stripEnd("  abc", null)    = "  abc"
     * StringUtil.stripEnd("abc  ", null)    = "abc"
     * StringUtil.stripEnd(" abc ", null)    = " abc"
     * StringUtil.stripEnd("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str  the String to remove characters from, may be null
     * @param stripChars  the characters to remove, null treated as whitespace
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if(str == null || (end = str.length()) == 0) {
            return str;
        }

        if(stripChars == null) {
            while((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if(stripChars.length() == 0) {
            return str;
        } else {
            while((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    /**
     * <p>입력된 String의 앞과 뒤에서 공백문자(whitespace)를 모두 제거한다.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.</p>
     *
     * <pre>
     * StringUtil.strip(null)     = null
     * StringUtil.strip("")       = ""
     * StringUtil.strip("   ")    = ""
     * StringUtil.strip("abc")    = "abc"
     * StringUtil.strip("  abc")  = "abc"
     * StringUtil.strip("abc  ")  = "abc"
     * StringUtil.strip(" abc ")  = "abc"
     * StringUtil.strip(" ab c ") = "ab c"
     * </pre>
     *
     * @param str  the String to remove whitespace from, may be null
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String strip(String str) {
        return strip(str, null);
    }

    /**
     * <p>입력된 String의 앞, 뒤에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
     *
     * <p>This is similar to {@link String#trim()} but allows the characters
     * to be stripped to be controlled.</p>
     *
     * <p>A <code>null</code> input String returns <code>null</code>.
     * An empty string ("") input returns the empty string.</p>
     *
     * <p>If the stripChars String is <code>null</code>, whitespace is
     * stripped as defined by {@link Character#isWhitespace(char)}.
     * Alternatively use {@link #strip(String)}.</p>
     *
     * <pre>
     * StringUtil.strip(null, *)          = null
     * StringUtil.strip("", *)            = ""
     * StringUtil.strip("abc", null)      = "abc"
     * StringUtil.strip("  abc", null)    = "abc"
     * StringUtil.strip("abc  ", null)    = "abc"
     * StringUtil.strip(" abc ", null)    = "abc"
     * StringUtil.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     *
     * @param str  the String to remove characters from, may be null
     * @param stripChars  the characters to remove, null treated as whitespace
     * @return the stripped String, <code>null</code> if null String input
     */
    public static String strip(String str, String stripChars) {
        if(isEmpty(str)) {
            return str;
        }
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }

    /**
     * <p>입력된 String의 앞과 뒤에서 공백문자(whitespace)를 모두 제거한다. 입력 값이
     * <code>null</code>이면 빈문자열("")을 반환한다.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtil.strip(null)     = ""
     * StringUtil.strip("")       = ""
     * StringUtil.strip("   ")    = ""
     * StringUtil.strip("abc")    = "abc"
     * StringUtil.strip("  abc")  = "abc"
     * StringUtil.strip("abc  ")  = "abc"
     * StringUtil.strip(" abc ")  = "abc"
     * StringUtil.strip(" ab c ") = "ab c"
     * </pre>
     *
     * @param str  the String to be stripped, may be null
     * @return the trimmed String, or an empty String if <code>null</code> input
     */
    public static String stripToEmpty(String str) {
        return str == null ? EMPTY : strip(str, null);
    }

    /**
     * <p>입력된 String의 앞과 뒤에서 공백문자(whitespace)를 모두 제거한다. 공백을
     * 제거한 후의 값이 빈문자열("")이면 <code>null</code>을 반환한다.</p>
     *
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     *
     * <pre>
     * StringUtil.strip(null)     = null
     * StringUtil.strip("")       = null
     * StringUtil.strip("   ")    = null
     * StringUtil.strip("abc")    = "abc"
     * StringUtil.strip("  abc")  = "abc"
     * StringUtil.strip("abc  ")  = "abc"
     * StringUtil.strip(" abc ")  = "abc"
     * StringUtil.strip(" ab c ") = "ab c"
     * </pre>
     *
     * @param str  the String to be stripped, may be null
     * @return the stripped String,
     *  <code>null</code> if whitespace, empty or null String input
     */
    public static String stripToNull(String str) {
        if(str == null) {
            return null;
        }
        str = strip(str, null);
        return str.length() == 0 ? null : str;
    }

    /**
     * <p>Returns padding using the specified delimiter repeated
     * to a given length.</p>
     *
     * <pre>
     * StringUtil.padding(0, 'e')  = ""
     * StringUtil.padding(3, 'e')  = "eee"
     * StringUtil.padding(-2, 'e') = IndexOutOfBoundsException
     * </pre>
     *
     * @param repeat  number of times to repeat delim
     * @param padChar  character to repeat
     * @return String with repeated character
     * @throws IndexOutOfBoundsException if <code>repeat &lt; 0</code>
     */
    private static String padding(int repeat, char padChar) {
        // be careful of synchronization in this method
        // we are assuming that get and set from an array index is atomic
        String pad = PADDING[padChar];
        if(pad == null) {
            pad = String.valueOf(padChar);
        }
        while(pad.length() < repeat) {
            pad = pad.concat(pad);
        }
        PADDING[padChar] = pad;
        return pad.substring(0, repeat);
    }

    /**
     * <p>문자열의 우측(끝)에 공백(' ')을 덧붙인다.</p>
     *
     * <p>The String is padded to the size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtil.rightPad(null, *)   = null
     * StringUtil.rightPad("", 3)     = "   "
     * StringUtil.rightPad("bat", 3)  = "bat"
     * StringUtil.rightPad("bat", 5)  = "bat  "
     * StringUtil.rightPad("bat", 1)  = "bat"
     * StringUtil.rightPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return right padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String rightPad(String str, int size) {
        return rightPad(str, size, ' ');
    }

    /**
     * <p>문자열의 우측(끝)에 지정한 문자(character)를 덧붙인다.</p>
     *
     * <p>The String is padded to the size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtil.rightPad(null, *, *)     = null
     * StringUtil.rightPad("", 3, 'z')     = "zzz"
     * StringUtil.rightPad("bat", 3, 'z')  = "bat"
     * StringUtil.rightPad("bat", 5, 'z')  = "batzz"
     * StringUtil.rightPad("bat", 1, 'z')  = "bat"
     * StringUtil.rightPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return right padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String rightPad(String str, int size, char padChar) {
        if(str == null) {
            return null;
        }
        int pads = size - str.length();
        if(pads <= 0) {
            return str; // returns original String when possible
        }
        if(pads > PAD_LIMIT) {
            return rightPad(str, size, String.valueOf(padChar));
        }
        return str.concat(padding(pads, padChar));
    }

    /**
     * <p>문자열의 우측(끝)에 지정한 문자열(String)을 덧붙인다.</p>
     *
     * <p>The String is padded to the size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtil.rightPad(null, *, *)      = null
     * StringUtil.rightPad("", 3, "z")      = "zzz"
     * StringUtil.rightPad("bat", 3, "yz")  = "bat"
     * StringUtil.rightPad("bat", 5, "yz")  = "batyz"
     * StringUtil.rightPad("bat", 8, "yz")  = "batyzyzy"
     * StringUtil.rightPad("bat", 1, "yz")  = "bat"
     * StringUtil.rightPad("bat", -1, "yz") = "bat"
     * StringUtil.rightPad("bat", 5, null)  = "bat  "
     * StringUtil.rightPad("bat", 5, "")    = "bat  "
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padStr  the String to pad with, null or empty treated as single space
     * @return right padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String rightPad(String str, int size, String padStr) {
        if(str == null) {
            return null;
        }
        if(isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if(pads <= 0) {
            return str; // returns original String when possible
        }
        if(padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad(str, size, padStr.charAt(0));
        }

        if(pads == padLen) {
            return str.concat(padStr);
        } else if(pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for(int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return str.concat(new String(padding));
        }
    }

    /**
     * <p>문자열의 좌측에 공백(' ')을 덧붙인다.</p>
     *
     * <p>The String is padded to the size of <code>size<code>.</p>
     *
     * <pre>
     * StringUtil.leftPad(null, *)   = null
     * StringUtil.leftPad("", 3)     = "   "
     * StringUtil.leftPad("bat", 3)  = "bat"
     * StringUtil.leftPad("bat", 5)  = "  bat"
     * StringUtil.leftPad("bat", 1)  = "bat"
     * StringUtil.leftPad("bat", -1) = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return left padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String leftPad(String str, int size) {
        return leftPad(str, size, ' ');
    }

    /**
     * <p>문자열의 좌측에 지정한 문자(character)를 덧붙인다.</p>
     *
     * <p>Pad to a size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtil.leftPad(null, *, *)     = null
     * StringUtil.leftPad("", 3, 'z')     = "zzz"
     * StringUtil.leftPad("bat", 3, 'z')  = "bat"
     * StringUtil.leftPad("bat", 5, 'z')  = "zzbat"
     * StringUtil.leftPad("bat", 1, 'z')  = "bat"
     * StringUtil.leftPad("bat", -1, 'z') = "bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return left padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     * @since 2.0
     */
    public static String leftPad(String str, int size, char padChar) {
        if(str == null) {
            return null;
        }
        int pads = size - str.length();
        if(pads <= 0) {
            return str; // returns original String when possible
        }
        if(pads > PAD_LIMIT) {
            return leftPad(str, size, String.valueOf(padChar));
        }
        return padding(pads, padChar).concat(str);
    }

    /**
     * <p>문자열의 좌측에 지정한 문자열(String)을 덧붙인다..</p>
     *
     * <p>Pad to a size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtil.leftPad(null, *, *)      = null
     * StringUtil.leftPad("", 3, "z")      = "zzz"
     * StringUtil.leftPad("bat", 3, "yz")  = "bat"
     * StringUtil.leftPad("bat", 5, "yz")  = "yzbat"
     * StringUtil.leftPad("bat", 8, "yz")  = "yzyzybat"
     * StringUtil.leftPad("bat", 1, "yz")  = "bat"
     * StringUtil.leftPad("bat", -1, "yz") = "bat"
     * StringUtil.leftPad("bat", 5, null)  = "  bat"
     * StringUtil.leftPad("bat", 5, "")    = "  bat"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padStr  the String to pad with, null or empty treated as single space
     * @return left padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String leftPad(String str, int size, String padStr) {
        if(str == null) {
            return null;
        }
        if(isEmpty(padStr)) {
            padStr = " ";
        }
        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;
        if(pads <= 0) {
            return str; // returns original String when possible
        }
        if(padLen == 1 && pads <= PAD_LIMIT) {
            return leftPad(str, size, padStr.charAt(0));
        }

        if(pads == padLen) {
            return padStr.concat(str);
        } else if(pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();
            for(int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }
            return new String(padding).concat(str);
        }
    }

    /**
     * <p>문자열에서 {@link Character#isWhitespace(char)}에 정의된
     * 모든 공백문자를 제거한다.</p>
     *
     * <pre>
     * StringUtil.removeWhitespace(null)         = null
     * StringUtil.removeWhitespace("")           = ""
     * StringUtil.removeWhitespace("abc")        = "abc"
     * StringUtil.removeWhitespace("   ab  c  ") = "abc"
     * </pre>
     *
     * @param str  the String to delete whitespace from, may be null
     * @return the String without whitespaces, <code>null</code> if null String input
     */
    public static String removeWhitespace(String str) {
        if(isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for(int i = 0; i < sz; i++) {
            if(!Character.isWhitespace(str.charAt(i))) {
                chs[count++] = str.charAt(i);
            }
        }
        if(count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }

    /**
     * <p>입력된 문자열 값이 <code>null</code>인 경우, 빈문자열("")을 반환.</p>
     *
     * <pre>
     * StringUtil.defaultString(null)  = ""
     * StringUtil.defaultString("")    = ""
     * StringUtil.defaultString("bat") = "bat"
     * </pre>
     *
     * @see String#valueOf(Object)
     * @param str  the String to check, may be null
     * @return the passed in String, or the empty String if it
     *  was <code>null</code>
     */
    public static String defaultString(String str) {
        return str == null ? EMPTY : str;
    }

    /**
     * <p>입력된 문자열 값이 <code>null</code>인 경우, 두번째 인자 값을 반환한다.
     * <code>null</code>아닌 경우 그대로 반환.</p>
     *
     * <pre>
     * StringUtil.defaultString(null, "NULL")  = "NULL"
     * StringUtil.defaultString("", "NULL")    = ""
     * StringUtil.defaultString("bat", "NULL") = "bat"
     * </pre>
     *
     * @see String#valueOf(Object)
     * @param str  the String to check, may be null
     * @param defaultStr  the default String to return
     *  if the input is <code>null</code>, may be null
     * @return the passed in String, or the default if it was <code>null</code>
     */
    public static String defaultString(String str, String defaultStr) {
        return str == null ? defaultStr : str;
    }

    /**
     * <p>기준 문자열에 포함된 모든 대상 문자(char)를 제거한다.</p>
     *
     * <p>A <code>null</code> source string will return <code>null</code>.
     * An empty ("") source string will return the empty string.</p>
     *
     * <pre>
     * StringUtil.remove(null, *)       = null
     * StringUtil.remove("", *)         = ""
     * StringUtil.remove("queued", 'u') = "qeed"
     * StringUtil.remove("queued", 'z') = "queued"
     * </pre>
     *
     * @param str  the source String to search, may be null
     * @param remove  the char to search for and remove, may be null
     * @return the substring with the char removed if found,
     *  <code>null</code> if null String input
     */
    public static String remove(String str, char remove) {
        if(isEmpty(str) || str.indexOf(remove) == -1) {
            return str;
        }
        char[] chars = str.toCharArray();
        int pos = 0;
        for(int i = 0; i < chars.length; i++) {
            if(chars[i] != remove) {
                chars[pos++] = chars[i];
            }
        }
        return new String(chars, 0, pos);
    }

    /**
     * <p>문자열 내부의 콤마 character(,)를 모두 제거한다.</p>
     *
     * <p>A <code>null</code> source string will return <code>null</code>.
     * An empty ("") source string will return the empty string.</p>
     *
     * <pre>
     * StringUtil.removeCommaChar(null)       = null
     * StringUtil.removeCommaChar("")         = ""
     * StringUtil.removeCommaChar("asdfg,qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str  the source String to search, may be null
     * @return the substring with the ',' removed if found,
     *  <code>null</code> if null String input
     */
    public static String removeCommaChar(String str) {
        return remove(str, ',');
    }

    /**
     * <p>문자열 내부의 마이너스 character(-)를 모두 제거한다.</p>
     *
     * <p>A <code>null</code> source string will return <code>null</code>.
     * An empty ("") source string will return the empty string.</p>
     *
     * <pre>
     * StringUtil.removeMinusChar(null)       = null
     * StringUtil.removeMinusChar("")         = ""
     * StringUtil.removeMinusChar("a-sdfg-qweqe") = "asdfgqweqe"
     * </pre>
     *
     * @param str  the source String to search, may be null
     * @return the substring with the ',' removed if found,
     *  <code>null</code> if null String input
     */
    public static String removeMinusChar(String str) {
        return remove(str, '-');
    }

    /**
     * <p>문자열에서 숫자만 추출한다.</p>
     *
     * <pre>
     * StringUtil.keepNumberChar(null)       = null
     * StringUtil.keepNumberChar("")         = ""
     * StringUtil.keepNumberChar("1한글5과숫자1234") = "151234"
     * StringUtil.keepNumberChar("abc한글과123영문과숫자423098") = "123423098"
     * </pre>
     *
     * @param str  the source String to search, may be null
     * @return 0~9 character로만 구성된 문자열,
     *  <code>null</code> if null String input
     */
    public static String keepNumberChar(String str) {
        if(str == null) {
            return null;
        }
        StringBuffer buffer = new StringBuffer(str.length());
        char[] chrs = str.toCharArray();
        int sz = chrs.length;
        for(int i = 0; i < sz; i++) {
            if(47 < chrs[i] && chrs[i] < 58) {
                buffer.append(chrs[i]);
            }
        }
        return buffer.toString();
    }

    /**
     * <p>주민등록번호에 분리자('-') 추가.</p>
     *
     * <pre>
     * StringUtil.insertMinusCharInSsn(null) = null
     * StringUtil.insertMinusCharInSsn("8001291018717") = "800129-1018717"
     * StringUtil.insertMinusCharInSsn("8001012017212") = "800101-2017212"
     * StringUtil.insertMinusCharInSsn("7504011010")    = IllegalArgumentException
     * </pre>
     *
     * @param ssn  구분자가 없이 숫자만으로 이루어진 주민등록번호 문자열
     * @return '-' 구분자가 삽입된 주민등록번호 문자열,
     *  <code>null</code> if null String input
     */
    public static String insertMinusCharInSsn(String ssn) throws IllegalArgumentException {
        if(ssn == null || "".equals(ssn)) {
            return ssn;
        }
        if(ssn.trim().length() != 13) {
            throw new IllegalArgumentException("Invalid SSN: " + ssn + " Length=" + ssn.trim().length());
        }
        return new StringBuffer(ssn).insert(6, '-').toString();
    }
    public static String insertMinusCharInSsn(String ssn,boolean errSkipYn) throws IllegalArgumentException {
    	try{
    		ssn =  insertMinusCharInSsn(ssn);
    	
    	}catch(IllegalArgumentException ie){
    		if(errSkipYn){
    			
    		}else{
    			throw new IllegalArgumentException("Invalid SSN: " + ssn + " Length=" + ssn.trim().length());
    		}
    	}
    	
    	return ssn;
    }
    /**
     * <p>주민등록번호에 분리자('-') 및 마스킹처리 추가.</p>
     * @author ksh 20110711
     * <pre>
     * StringUtil.insertMinusCharInSsn(null) = null
     * StringUtil.insertMinusCharInSsn("8001291018717") = "800129-1018717"
     * StringUtil.insertMinusCharInSsn("8001012017212") = "800101-2017212"
     * StringUtil.insertMinusCharInSsn("7504011010")    = IllegalArgumentException
     * </pre>
     *
     * @param ssn  구분자가 없이 숫자만으로 이루어진 주민등록번호 문자열
     * 		  maskStr  mask표시 문자
     * 		  maskInt  주민번호 뒷자리 첫문자를 시작으로  mask 처리할 개수 
     * @return '-' 구분자가 삽입된 주민등록번호 문자열,
     *  <code>null</code> if null String input
     */
    public static String getMaskRrn(String ssn,String maskStr,int maskCnt) {

    	
    	if(ssn == null || "".equals(ssn)) {
            return ssn;
        }else{
        	ssn = ssn.replaceAll("-", "");
        }
    	
    	if(maskStr == null || maskStr.equals("")){
    		maskStr = "●";
    	}
        
    	String replaceStr = "";
    	for(int i = 0 ; i < maskCnt ; i++){
    		replaceStr +=maskStr;
    	}
    	
     	ssn = new StringBuffer(ssn).replace(6, 6+maskCnt, replaceStr).toString();
        
     	if(ssn.trim().length() != 13) {
            throw new IllegalArgumentException("Invalid SSN: " + ssn + " Length=" + ssn.trim().length());
        }
        
     	return new StringBuffer(ssn).insert(6, '-').toString();
    }
    
    /**
     * <p>주민등록번호에 분리자('-') 및 마스킹처리 추가.</p>
     * @author ksh 20110711
     * <pre>
     * StringUtil.insertMinusCharInSsn(null) = null
     * StringUtil.insertMinusCharInSsn("8001291018717") = "800129-1018717"
     * StringUtil.insertMinusCharInSsn("8001012017212") = "800101-2017212"
     * StringUtil.insertMinusCharInSsn("7504011010")    = IllegalArgumentException
     * </pre>
     *
     * @param ssn  구분자가 없이 숫자만으로 이루어진 주민등록번호 문자열
     * 		  maskStr  mask표시 문자
     * 		  maskInt  주민번호 뒷자리 첫문자를 시작으로  mask 처리할 개수 
     * @return '-' 구분자가 삽입된 주민등록번호 문자열,
     *  <code>null</code> if null String input
     */
    public static String getMaskRrn1(String ssn,String maskStr,int maskCnt) {
    	
    	if(ssn == null || "".equals(ssn)) {
            return ssn;
        }else{
        	ssn = ssn.replaceAll("-", "");
        }
    	
    	if(maskStr == null || maskStr.equals("")){
    		maskStr = "●";
    	}
        
    	String replaceStr = "";
    	for(int i = 0 ; i < maskCnt ; i++){
    		replaceStr +=maskStr;
    	}
    	
     	ssn = new StringBuffer(ssn).replace(7, 7+maskCnt, replaceStr).toString();
        
     	if(ssn.trim().length() != 13) {
            throw new IllegalArgumentException("Invalid SSN: " + ssn + " Length=" + ssn.trim().length());
        }
        
     	return new StringBuffer(ssn).insert(6, '-').toString();
    }
    /**
     * <p>주민등록번호에 분리자('-') 및 마스킹처리 추가.</p>
	 * errSkipYn 을 true로 넘길시에 ssn이 13자리에 맞지 않으면 마스킹 처리되지 않은 ssn을 리턴. 
     */
    public static String getMaskRrn(String ssn,String maskStr,int maskCnt ,boolean errSkipYn) {
    	
    	
    	try{
    		ssn = getMaskRrn(ssn,maskStr,maskCnt);
    	}catch(IllegalArgumentException ie){
    		if(true){
    			
    		}else{
    			 throw new IllegalArgumentException("Invalid SSN: " + ssn + " Length=" + ssn.trim().length());
    		}
    	}
    	
    	return ssn;
    }
    
    /**
     * 뒷주민번호 첫 자리까지 노출
     * <p>주민등록번호에 분리자('-') 및 마스킹처리 추가.</p>
     * errSkipYn 을 true로 넘길시에 ssn이 13자리에 맞지 않으면 마스킹 처리되지 않은 ssn을 리턴. 
     */
    public static String getMaskRrn1(String ssn,String maskStr,int maskCnt ,boolean errSkipYn) {
    	
    	
    	try{
    		ssn = getMaskRrn1(ssn,maskStr,maskCnt);
    	}catch(Exception e){
    		if(errSkipYn){
    			
    		}else{
    			throw new IllegalArgumentException("Invalid SSN: " + ssn + " Length=" + ssn.trim().length());
    		}
    	}
    	
    	return ssn;
    }
    
    /**
     * <p>입력된 문자열을 <code>repeat</code>에 지정한 횟수만큼 반복해서 붙인 후
     * 반환한다.</p>
     *
     * <pre>
     * StringUtil.repeat(null, 2) = null
     * StringUtil.repeat("", 0)   = ""
     * StringUtil.repeat("", 2)   = ""
     * StringUtil.repeat("a", 3)  = "aaa"
     * StringUtil.repeat("ab", 2) = "abab"
     * StringUtil.repeat("a", -2) = ""
     * </pre>
     *
     * @param str  the String to repeat, may be null
     * @param repeat  number of times to repeat str, negative treated as zero
     * @return a new String consisting of the original String repeated,
     *  <code>null</code> if null String input
     */
    public static String repeat(String str, int repeat) {
        if(str == null) {
            return null;
        }
        if(repeat <= 0) {
            return EMPTY;
        }
        int inputLength = str.length();
        if(repeat == 1 || inputLength == 0) {
            return str;
        }
        if(inputLength == 1 && repeat <= PAD_LIMIT) {
            return padding(repeat, str.charAt(0));
        }

        int outputLength = inputLength * repeat;
        switch(inputLength) {
            case 1 :
                char ch = str.charAt(0);
                char[] output1 = new char[outputLength];
                for(int i = repeat - 1; i >= 0; i--) {
                    output1[i] = ch;
                }
                return new String(output1);
            case 2 :
                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                char[] output2 = new char[outputLength];
                for(int i = repeat * 2 - 2; i >= 0; i--, i--) {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }
                return new String(output2);
            default :
                StringBuffer buf = new StringBuffer(outputLength);
                for(int i = 0; i < repeat; i++) {
                    buf.append(str);
                }
                return buf.toString();
        }
    }

    /**
     * <p>지정한 <code>len</code> 숫자만큼 우측에서 문자열을 추출한다.<p>
     *
     * <p>If <code>len</code> characters are not available, or the String
     * is <code>null</code>, the String will be returned without an
     * an exception. An exception is thrown if len is negative.</p>
     *
     * <pre>
     * StringUtil.right(null, *)    = null
     * StringUtil.right(*, -ve)     = ""
     * StringUtil.right("", *)      = ""
     * StringUtil.right("abc", 0)   = ""
     * StringUtil.right("abc", 2)   = "bc"
     * StringUtil.right("abc", 4)   = "abc"
     * </pre>
     *
     * @param str  the String to get the rightmost characters from, may be null
     * @param len  the length of the required String, must be zero or positive
     * @return the rightmost characters, <code>null</code> if null String input
     */
    public static String right(String str, int len) {
        if(str == null) {
            return null;
        }
        if(len < 0) {
            return EMPTY;
        }
        if(str.length() <= len) {
            return str;
        } else {
            return str.substring(str.length() - len);
        }
    }

    /**
     * <p>문자열에서 newline character(\n, \r)를 모두 제거한다.</p>
     *
     * <pre>
     * StringUtil.removeEnterChar(null) = null
     * StringUtil.removeEnterChar("") = ""
     * StringUtil.removeEnterChar("\r\n\n\n\r\r\n") = ""
     * StringUtil.removeEnterChar("\n\u00A0\n\r\u202F\r") = "\u00A0\u202F"
     * StringUtil.removeEnterChar("\n\r한글\r") = "한글"
     * StringUtil.removeEnterChar("소나기가 \r\n내리는 \r\n밤이었습니다") = "소나기가 내리는 밤이었습니다"
     * StringUtil.removeEnterChar("소나기가 \n내리는 \n밤이었습니다") = "소나기가 내리는 밤이었습니다"
     * </pre>
     *
     * @param str  the String to delete newline from, may be null
     * @return the String without newline, <code>null</code> if null String input
     */
    public static String removeEnterChar(String str) {
        if(isEmpty(str)) {
            return str;
        }
        int sz = str.length();
        char[] chs = new char[sz];
        int count = 0;
        for(int i = 0; i < sz; i++) {
            if(str.charAt(i) != '\n' && str.charAt(i) != '\r') {
                chs[count++] = str.charAt(i);
            }
        }
        if(count == sz) {
            return str;
        }
        return new String(chs, 0, count);
    }

    /**
     * <p><code>str</code> 중 <code>searchStr</code>의 시작(index) 위치를 반환.</p>
     *
     * <p>입력값 중 <code>null</code>이 있을 경우 <code>-1</code>을 반환.</p>
     *
     * <pre>
     * StringUtil.indexOf(null, *)          = -1
     * StringUtil.indexOf(*, null)          = -1
     * StringUtil.indexOf("", "")           = 0
     * StringUtil.indexOf("aabaabaa", "a")  = 0
     * StringUtil.indexOf("aabaabaa", "b")  = 2
     * StringUtil.indexOf("aabaabaa", "ab") = 1
     * StringUtil.indexOf("aabaabaa", "")   = 0
     * </pre>
     *
     * @param str  the String to check, may be null
     * @param searchStr  the String to find, may be null
     * @return the first index of the search String,
     *  -1 if no match or <code>null</code> string input
     */
    public static int indexOf(String str, String searchStr) {
        if(str == null || searchStr == null) {
            return -1;
        }
        return str.indexOf(searchStr);
    }

    /**
     * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
     * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
     * <code>returStr</code>을 반환하며, 다르면  <code>defaultStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo", "bar")= "foo"
     * StringUtil.decode("", null, "foo", "bar") = "bar"
     * StringUtil.decode(null, "", "foo", "bar") = "bar"
     * StringUtil.decode("하이", "하이", null, "bar") = null
     * StringUtil.decode("하이", "하이  ", "foo", null) = null
     * StringUtil.decode("하이", "하이", "foo", "bar") = "foo"
     * StringUtil.decode("하이", "하이  ", "foo", "bar") = "bar"
     * </pre>
     *
     * @param sourceStr 비교할 문자열
     * @param compareStr 비교 대상 문자열
     * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @param defaultStr sourceStr와 compareStr의 값이 다를 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
     *         <br/>다르면 defaultStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr, String returnStr, String defaultStr) {
        if(sourceStr == null && compareStr == null) {
            return returnStr;
        }

        if(sourceStr == null && compareStr != null) {
            return defaultStr;
        }

        if(sourceStr.trim().equals(compareStr)) {
            return returnStr;
        }

        return defaultStr;
    }

    /**
     * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
     * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
     * <code>returStr</code>을 반환하며, 다르면  <code>sourceStr</code>을 반환한다.
     * </p>
     *
     * <pre>
     * StringUtil.decode(null, null, "foo") = "foo"
     * StringUtil.decode("", null, "foo") = ""
     * StringUtil.decode(null, "", "foo") = null
     * StringUtil.decode("하이", "하이", "foo") = "foo"
     * StringUtil.decode("하이", "하이 ", "foo") = "하이"
     * StringUtil.decode("하이", "바이", "foo") = "하이"
     * </pre>
     *
     * @param sourceStr 비교할 문자열
     * @param compareStr 비교 대상 문자열
     * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
     * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
     *         <br/>다르면 sourceStr을 반환한다.
     */
    public static String decode(String sourceStr, String compareStr, String returnStr) {
        return decode(sourceStr, compareStr, returnStr, sourceStr);
    }

    /**
     * <p>입력 문자열의 크기를 조정한다. 문자열이 주어진 <code>size</code> 보다 길면
     * 자르고, 작으면 공백(' ')을 뒤에 추가한다
     * (2byte 문자(한글)의 경우 길이를 2로 간주한다).</p>
     *
     * <p>2byte 문자가 1byte만 잘려야 할 경우 해당 문자를 버린다.</p>
     *
     * <pre>
     * StringUtil.rightPadOrSubsting("abc영어공부", 4)  = "abc"
     * StringUtil.rightPadOrSubsting("abc영어공부", 5)  = "abc영"
     * StringUtil.rightPadOrSubsting("abc영어공부", 9)  = "abc영어공"
     * StringUtil.rightPadOrSubsting("abc영어공부", 11) = "abc영어공부"
     * StringUtil.rightPadOrSubsting("abc영어공부", 12) = "abc영어공부 "
     * StringUtil.rightPadOrSubsting("abc영어공부", 16) = "abc영어공부     "
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return 문자열을 주어진 길이로 자르거나, 뒤에 공백을 붙여서 반환한다. 문자열 길이와
     * 주어진 길이가 같을 경우 주어진 문자열을 반환한다.
     * <code>null</code> if null String input
     */
    public static String rightPadOrSubsting(String str, int size) {
        if(str == null) {
            return null;
        }
        int strLen = str.getBytes().length;
        int pads = size - strLen;
        if(pads == 0) {
            return str;
        } else if(pads > 0) {
            return rightPad2(str, size, ' ');
        } else {
            return substring2ByteString(str, size);
        }
    }

    /**
     * <p>지정한 <code>size</code>가 될 때까지 <code>padStr</code>을 <code>str</code>의
     * 우측에 붙인다(2byte 문자(한글)의 경우 길이를 2로 간주한다).<br>
     * <code>padStr</code>가 <code>null</code>이거나 빈 문자열("")이면 공백문자(' ')를
     * 사용한다.</p>
     *
     * <p>2byte 문자가 1byte만 잘려야 할 경우 해당 문자를 버린다.</p>
     *
     * <pre>
     * StringUtil.rightPad2("한글과 abc", 4, null) = "한글과 abc"
     * StringUtil.rightPad2("한글과 abc", 4, "") = "한글과 abc"
     * StringUtil.rightPad2("한글과 abc", 11, null) = "한글과 abc "
     * StringUtil.rightPad2("한글과 abc", 11, "") = "한글과 abc "
     * StringUtil.rightPad2("한글과 abc", 12, "a") = "한글과 abcaa"
     * StringUtil.rightPad2("한글과 abc", 12, "한글") = "한글과 abc한"
     * StringUtil.rightPad2("한글과 abc", 13, "한글") = "한글과 abc한"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padStr  the String to pad with, null or empty treated as single space
     * @return right padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String rightPad2(String str, int size, String padStr) {
        if(str == null) {
            return null;
        }
        if(isEmpty(padStr)) {
            padStr = " ";
        }

        // 2byte character는 2자리로 계산해야하기 때문...
        int padLen = padStr.getBytes().length;
        int strLen = str.getBytes().length;
        int pads = size - strLen;
        if(pads <= 0) {
            return str; // returns original String when possible
        }
        // 2byte character는 1이 될 수 없음...
        if(padLen == 1 && pads <= PAD_LIMIT) {
            return rightPad2(str, size, padStr.charAt(0));
        }

        if(pads == padLen) {
            return str.concat(padStr);
        } else if(pads < padLen) {
            return str.concat(substring2ByteString(padStr, pads));
        } else {
            int gap = padLen - padStr.length();
            char[] padChars = padStr.toCharArray();
            char[] padding = new char[pads];
            if(gap == 0) {
                for(int i = 0; i < pads; i++) {
                    padding[i] = padChars[i % padLen];
                }
                return str.concat(new String(padding));
            } else {
                int padLen2 = padStr.length();
                for(int i = 0; i < pads - gap; i++) {
                    padding[i] = padChars[i % padLen2];
                }
                return substring2ByteString(str.concat(new String(padding)), size);
            }
        }
    }

    /**
     * <p>공백문자(' ')를 지정한 길이가 될 때까지 우측에 덧붙인다
     * (2byte 문자(한글)의 경우 길이를 2로 간주한다).</p>
     *
     * <p>The String is padded to the size of <code>size</code>.</p>
     *
     * <pre>
     * StringUtil.rightPad2("한글", 0)   = "한글"
     * StringUtil.rightPad2("한글", 1)   = "한글"
     * StringUtil.rightPad2("한글", 4)   = "한글"
     * StringUtil.rightPad2("한글", 5)   = "한글 "
     * StringUtil.rightPad2("한글", 10)  = "한글      "
     * StringUtil.rightPad2("한글a", 10) = "한글a     "
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @return right padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String rightPad2(String str, int size) {
        return rightPad2(str, size, ' ');
    }

    /**
     * <p>지정한 <code>size</code>가 될 때까지 <code>padChar</code>를 <code>str</code>의
     * 우측에 붙인다(2byte 문자(한글)의 경우 길이를 2로 간주한다).<br>
     * <code>padChar</code>가 <code>null</code>이거나 빈 문자열("")이면 공백문자(" ")를
     * 사용한다.</p>
     *
     * <pre>
     * StringUtil.rightPad2("한글", 3, null) = "한글"
     * StringUtil.rightPad2("한글", 4, null) = "한글"
     * StringUtil.rightPad2("한글", 5, null) = "한글 "
     * StringUtil.rightPad2("한글", 5, ' ') = "한글 "
     * StringUtil.rightPad2("한글", 5, 'a') = "한글a"
     * StringUtil.rightPad2("한글", 5, '\n') = "한글\n"
     * StringUtil.rightPad2("abc와 한글", 11, 'z') = "abc와 한글z"
     * StringUtil.rightPad2("abc와 한글", 15, 'z') = "abc와 한글zzzzz"
     * </pre>
     *
     * @param str  the String to pad out, may be null
     * @param size  the size to pad to
     * @param padChar  the character to pad with
     * @return right padded String or original String if no padding is necessary,
     *  <code>null</code> if null String input
     */
    public static String rightPad2(String str, int size, char padChar) {
        if(str == null) {
            return null;
        }
        int pads = size - str.getBytes().length;
        if(pads <= 0) {
            return str; // returns original String when possible
        }
        if(pads > PAD_LIMIT) {
            return rightPad2(str, size, String.valueOf(padChar));
        }
        return str.concat(padding2(pads, padChar));
    }

    private static String padding2(int repeat, char padChar) {
        // be careful of synchronization in this method
        // we are assuming that get and set from an array index is atomic
        String pad = PADDING[padChar];
        if(pad == null) {
            pad = String.valueOf(padChar);
        }
        while(pad.length() < repeat) {
            pad = pad.concat(pad);
        }
        PADDING[padChar] = pad;
        return substring2ByteString(pad, repeat);
    }

    private static String substring2ByteString(String str, int endIndex) {
        if(str == null) {
            return null;
        }

        byte[] strByte = str.getBytes();
        int i, strLen;

        strLen = strByte.length;

        if(strLen <= endIndex) {
            return str;
        }

        int cnt = 0;
        for(i = 0; i < endIndex; i++) {
            if((((int) strByte[i]) & 0xff) > 0x80) {
                cnt++;
            }
        }
        if((cnt % 2) == 1) {
            i--;
        }

        return new String(strByte, 0, i);
    }

    public static String changeHtmlToText(String strString) {
        String strNew = "";

        try {
            StringBuffer result = new StringBuffer("");
            char chrBuff;
            int len = 0;
            int i = 0;

            len = strString.length();

            for(i = 0; i < len; i++) {
                chrBuff = (char) strString.charAt(i);
                switch(chrBuff) {
                	case '<':
	                	result.append("&lt;");
	                	break;
                	case '>':
	                	result.append("&gt;");
	                	break;
                	case '"':
	                	result.append("&quot;");
	                	break;
                	case '\'':
	                	result.append("&#39;");
	                	break;
                	case '%':
	                	result.append("&#37;");
	                	break;
                	case ';':
	                	result.append("&#59;");
	                	break;
                	case '(':
	                	result.append("&#40;");
	                	break;
                	case ')':
	                	result.append("&#41;");
	                	break;
                	case '&':
	                	result.append("&amp;");
	                	break;
                	case '+':
                		result.append("&#43;");
                		break;
                    default :
                    	result.append(chrBuff);
                }//switch
            }//for

            strNew = result.toString();

        } catch(Exception ex) {
        }

        return strNew;
    }

    public static String toHtmlString(String str) {
        if(str == null)
            return "";
        if("".equals(str))
            return "-";

        String tempStr = replace(str, "\n", "<br>");

        tempStr = StringUtil.replaceString(tempStr, "<a href", "<ahref");
        tempStr = StringUtil.replace(tempStr, " ", "&nbsp;");
        tempStr = StringUtil.replaceString(tempStr, "<ahref", "<a href");
        return tempStr;
    }

    public static String toHtmlString(String str, String clsName) {
        if(str == null)
            return "";
        if("".equals(str))
            return "-";

        String tempStr = replace(str, "\n", "<br>");

        tempStr = StringUtil.replaceString(tempStr, "<a href", "<ahref");
        tempStr = StringUtil.replace(tempStr, " ", "&nbsp;");
        tempStr = StringUtil.replaceString(tempStr, "<ahref", "<a class='" + clsName + "' href");
        return tempStr;
    }

    public static String tagRemove(String s) {
        StringBuffer strip_html = new StringBuffer();
        char[] buf = s.toCharArray();
        int j = 0;
        for(; j < buf.length; j++) {
            if(buf[j] == '<') {
                for(; j < buf.length; j++) {
                    if(buf[j] == '>') {
                        break;
                    }
                }

            } else {
                strip_html.append(buf[j]);
            }
        }
        return strip_html.toString();
    }

    public static String replaceDang(String str) {
        if(str != null && !str.equals("")) {
            str = StringUtil.null2space(str);
            str = StringUtil.replaceString(str, "'", "&#39;");
            str = StringUtil.replaceString(str, "\"", "&quot;");
        }
        return str;
    }

    public static String replaceDangRev(String str) {
        if(str != null && !str.equals("")) {
            str = StringUtil.replaceString(str, "&#39;", "'");
            str = StringUtil.replaceString(str, "&quot;", "\"");
        }
        return str;
    }

    public static String replaceAmp(String str) {
        if(str != null && !str.equals("")) {
            str = StringUtil.null2space(str);
            str = StringUtil.replaceString(str, "&", "&amp;");
        }
        return str;
    }

    public static String replaceAmpRev(String str) {
        if(str != null && !str.equals("")) {
            str = StringUtil.replaceString(str, "&amp;", "&");

        }
        return str;
    }

    public static String checkHtmlView(String strString) {
        String strNew = "";

        try {
            StringBuffer strTxt = new StringBuffer("");

            char chrBuff;
            int len = strString.length();

            for(int i = 0; i < len; i++) {
                chrBuff = (char) strString.charAt(i);

                switch(chrBuff) {
                    case '<' :
                        strTxt.append("&lt;");
                        break;
                    case '>' :
                        strTxt.append("&gt;");
                        break;
                    case '"' :
                        strTxt.append("&quot;");
                        break;
                    case 10 :
                        strTxt.append("<br>");
                        break;
                    //case 13 :
                    //strTxt.append("<br>");
                    //break;
                    case ' ' :
                        strTxt.append("&nbsp;");
                        break;
                    //case '&' :
                    //strTxt.append("&amp;");
                    //break;
                    default :
                        strTxt.append(chrBuff);
                }
            }

            strNew = strTxt.toString();

        } catch(Exception ex) {
        }

        return strNew;
    }

    public static String checkHtmlEdit(String strString) {
        String strNew = "";

        try {
            StringBuffer strTxt = new StringBuffer("");

            char chrBuff;
            int len = strString.length();

            for(int i = 0; i < len; i++) {
                chrBuff = (char) strString.charAt(i);

                switch(chrBuff) {
                    case '<' :
                        strTxt.append("&lt;");
                        break;
                    case '>' :
                        strTxt.append("&gt;");
                        break;
                    case '"' :
                        strTxt.append("&quot;");
                        break;
                    //case '&' :
                    //strTxt.append("&amp;");
                    //break;
                    default :
                        strTxt.append(chrBuff);
                }
            }

            strNew = strTxt.toString();

        } catch(Exception ex) {
        }

        return strNew;
    }

    public static String byteCutLine(String str, int cut, String pushChar) {
        if(str == null)
            return null;

        int kk = str.lastIndexOf("&nbsp;");
        String nbstr = "";
        if(kk != (-1)) {
            str.substring(0, kk + 6);
            str = str.substring(kk + 6);
        }

        byte[] bl = str.getBytes();
        if(bl.length <= cut)
            return str;

        int size = (int) (Math.ceil((float) bl.length / (float) cut));

        StringBuffer reVal = new StringBuffer();
        String tempStr = str;
        for(int i = 0; i < size - 1; i++) {
            byte[] temp1 = new byte[cut];
            System.arraycopy(tempStr.getBytes(), 0, temp1, 0, cut);

            String val = new String(temp1);
            int idx = val.length();

            reVal.append(tempStr.substring(0, idx + 1) + "<br>" + pushChar);
            tempStr = tempStr.substring(idx + 1, tempStr.length());
        }

        reVal.append(tempStr);

        return nbstr + reVal.toString();

    }

    public static String replaceString(String str, String src, String tgt) {
        StringBuffer ret = new StringBuffer();

        if(str == null)
            return null;
        if(str.equals(""))
            return "";

        int start = 0;
        int found = str.indexOf(src);
        while(found >= 0) {
            if(found > start)
                ret.append(str.substring(start, found));

            if(tgt != null)
                ret.append(tgt);

            start = found + src.length();
            found = str.indexOf(src, start);
        }

        if(str.length() > start)
            ret.append(str.substring(start, str.length()));

        return ret.toString();
    }

    public static String null2space(String s) {
        if(s == null || s.length() == 0)
            return "";
        else
            return s.trim();
    }

    public static String getSum(String arrString, int scale) {
        if(arrString == null)
            return "0";
        String[] val = arrString.split(",");
        BigDecimal sum = new BigDecimal(0);
        BigDecimal denominator = new BigDecimal(1);

        for(int i = 0; i < val.length; i++) {
            if(val[i] != null) {
                BigDecimal calVal = new BigDecimal(val[i]);
                sum = sum.add(calVal);
            }
        }
        sum = sum.divide(denominator, scale, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(sum);
    }

    public static String getAverage(String arrString, int scale) {
        if(arrString == null)
            return "0";
        String[] val = arrString.split(",");
        BigDecimal sum = new BigDecimal(0);
        BigDecimal denominator = new BigDecimal(val.length);

        for(int i = 0; i < val.length; i++) {
            if(val[i] != null) {
                BigDecimal calVal = new BigDecimal(val[i]);
                sum = sum.add(calVal);
            }
        }
        sum = sum.divide(denominator, scale, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(sum);
    }

    public static String getAverage(String arrString, float deno, int scale) {
        if(arrString == null)
            return "0";
        String[] val = arrString.split(",");
        BigDecimal sum = new BigDecimal(0);
        BigDecimal denominator = new BigDecimal(deno);

        for(int i = 0; i < val.length; i++) {
            if(val[i] != null) {
                BigDecimal calVal = new BigDecimal(val[i]);
                sum = sum.add(calVal);
            }
        }
        sum = sum.divide(denominator, scale, BigDecimal.ROUND_HALF_UP);
        return String.valueOf(sum);
    }

    public static String UTF8Decode(byte in[], int offset, int length) {
        StringBuffer buff = new StringBuffer();
        int max = offset + length;
        for(int i = offset; i < max; i++) {
            char c = 0;
            if((in[i] & 0x80) == 0) {
                c = (char) in[i];
            } else if((in[i] & 0xe0) == 0xc0) // 11100000
            {
                c |= ((in[i] & 0x1f) << 6); // 00011111
                i++;
                c |= ((in[i] & 0x3f) << 0); // 00111111
            } else if((in[i] & 0xf0) == 0xe0) // 11110000
            {
                c |= ((in[i] & 0x0f) << 12); // 00001111
                i++;
                c |= ((in[i] & 0x3f) << 6); // 00111111
                i++;
                c |= ((in[i] & 0x3f) << 0); // 00111111
            } else if((in[i] & 0xf8) == 0xf0) // 11111000
            {
                c |= ((in[i] & 0x07) << 16); // 00000111
                i++;
                c |= ((in[i] & 0x3f) << 12); // 00111111
                i++;
                c |= ((in[i] & 0x3f) << 6); // 00111111
                i++;
                c |= ((in[i] & 0x3f) << 0); // 00111111
            } else {
                c = '?';
            }
            buff.append(c);
        }
        return buff.toString();
    }
    
	/**
	 * 날짜 포맷
	 * @param	str		입력문자
	 * @return	String	변환문자
	 */
	public static String getMaskDate(String str, String mask){
		StringBuffer strBuf = new StringBuffer("");
		str = str.trim();
		if (str.equals("") || str.equals("0") || str.length() != 8){
			return str;
		}
		str = MaskClear(str);
		strBuf.append(str.substring(0,4));
		strBuf.append(mask).append(str.substring(4,6)).append(mask);
		strBuf.append(str.substring(6,8));
		
		return strBuf.toString();
	}
	
	/**
	 * 시간 포맷
	 * @param	str		입력문자
	 * @return	String	변환문자
	 */
	public static String getMaskTime(String str, String mask){
		StringBuffer strBuf = new StringBuffer("");
		str = str.trim();
		if (str.equals("") || str.equals("0") || str.length() != 4){
			return str;
		}
		str = MaskClear(str);
		strBuf.append(str.substring(0,2));
		strBuf.append(mask).append(str.substring(2,4));
		
		return strBuf.toString();
	}

	/**
	 * 시간 포맷
	 * @param	str		입력문자
	 * @return	String	변환문자
	 */
	public static String getMaskTimeSec(String str, String mask){
		StringBuffer strBuf = new StringBuffer("");
		str = str.trim();
		if (str.equals("") || str.equals("0") || str.length() != 6){
			return str;
		}
		str = MaskClear(str);
		strBuf.append(str.substring(0,2));
		strBuf.append(mask).append(str.substring(2,4));
		strBuf.append(mask).append(str.substring(4,6));
		
		return strBuf.toString();
	}
	
	/**
	 * 우편번호 포맷
	 * @param	str		입력문자
	 * @return	String	변환문자
	 */
	public static String getMaskPost(String str){
		StringBuffer strBuf = new StringBuffer("");
		String mask = "-";
		str = MaskClear(str).trim();
		if (str.equals("") || str.equals("0") || str.length() != 6){
			return str;
		}
		
		strBuf.append(str.substring(0,3));
		strBuf.append(mask).append(str.substring(3,6));
		
		return strBuf.toString();
	}
	
	/**
	 * mask 제거
	 * @param	str
	 * @return	
	 * @exception	
	 */
	public synchronized static final  String MaskClear(String str ){
		boolean Eumsu = false;
		StringBuffer strBuffer = new StringBuffer("");
		String tempStr = new String("");
		if (str.substring(0,1).equals("-")){
			if (str.length() == 1) {
				return new String("0");
			}
			str = str.substring(1, str.length());
			Eumsu = true;
		}
		for (int i = 0; i < str.length(); i++ ){
			char c = str.charAt(i);
			if (Character.isDigit(c)){
				strBuffer.append(c);
			}
		}
		tempStr = strBuffer.toString();
		if(Eumsu){
			tempStr = "-" + strBuffer.toString();
		} 
		else if(tempStr.equals("")){
			tempStr = "0";
		}
		
		return tempStr ;
	}
	
	/**
	 * 메시지 포맷
	 * @param	msgStr	메시지명
	 * @param	msgCode	메시지코드
	 * @param	msgArg	구분자
	 * @return	String
	 */
	public static String getMessageFormat(String msgStr, String msgCode, String msgArg){
		String message = "";
		StringTokenizer st = null;
		String temp = "";
		msgStr = StringUtil.isNullTrim(msgStr);
		
		if(!msgStr.equals("")){
			int maxtoken = 0;
			int idx = 0;
			temp = msgStr;
			st = new StringTokenizer(msgArg,"@");
			maxtoken = st.countTokens();
			for (int i=0; i < temp.length(); i++) {
				if (temp.substring(i,i+1).equals("@")) {
					if (maxtoken > idx) {
						message += st.nextToken();
						idx++;
					}
				} else {
					message += temp.substring(i,i+1);
				}
			}
			message = "[ " + msgCode + " ]" + message;
		}else{
			message = "[ " + msgCode + " ]";
		}
		
		return message.toString();
	}
	
	/**
	 * 이메일 메시지 포맷
	 * @param	msgStr	메시지명
	 * @param	msgCode	메시지코드
	 * @param	msgArg	구분자
	 * @return	String
	 */
	public static String getEmailMessageFormat(String msgStr, String msgArg){
		String message = "";
		StringTokenizer st = null;
		String temp = "";
		
		if(!msgStr.equals("")){
			int maxtoken = 0;
			int idx = 0;
			temp = msgStr;
			st = new StringTokenizer(msgArg,"@");
			maxtoken = st.countTokens();
			for (int i=0; i < temp.length(); i++) {
				if (temp.substring(i,i+1).equals("@")) {
					if (maxtoken > idx) {
						message += st.nextToken();
						idx++;
					}
				} else {
					message += temp.substring(i,i+1);
				}
			}
		}else{
			message = "";
		}
		
		return message.toString();
	}
	
	public static String ynToBoolString(String str){
		return str.equals("Y") ? "true" : "false";
	}
	
	/**
     * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸고 null이 아니면 trim 까지 수행 메서드 
     * @param object 원본 객체
     * @return resultVal 문자열
     */
    public static String isNullToStrTrm(Object object) {
        String string = "";
        if(object != null) {
            string = object.toString();
        }
        return string.trim();
    }
	
	/**
	 * 글크기 비교
	 * 작성 날짜: (2005-04-21 오전 10:33:43)
	 * @return String
	 * @param str1 	java.lang.String 	비교대상1
	 * @param str2 	java.lang.String 	비교대상2
	 */	 
	public static String compareCharacter(String str1, String str2){
	
		int maxLength = 0;
		int minLength = 0;
		
		String rtnValue = "E";
		try{
			if(str1.length() >= str2.length()){
				maxLength = str1.length();
				minLength = str2.length();
				rtnValue = "L";
			}else{
				maxLength = str2.length();
				minLength = str1.length();
				rtnValue = "R";
			}
			for(int i=0;i<minLength;i++){
				if(str1.charAt(i) > str2.charAt(i)){
					return "L";
				}else if(str1.charAt(i) < str2.charAt(i)){
					return "R";
				}
			}
			
			if(maxLength == minLength){
				return "E";
			}else{
				return rtnValue;
			}
		}catch(Exception e){
			return "ERROR";
		}
	}
	
	/**
	 * mask문자를 제거하고 숫자만 남긴다.
	 * @param   data    mask 제거 할 문자
	 * @return
	 */
	public static  String exceptMask(String data) {
		String lsReturn = "", lsTemp = isNullTrim(data);
		char lcChar ;
		for ( int i = 0; i < lsTemp.length(); i ++ ){
			lcChar = lsTemp.charAt(i);
			if ( Character.isDigit(lcChar) )
				lsReturn += lcChar ;
		}
		
		return lsReturn;
	}

	/**
	 * sms발송시 넘어온 byte 로 잘라서  배열로 리턴
	 * @param   data    mask 제거 할 문자
	 * @return
	 */
	public static String[] parseStringBytes(String raw,int len,String encoding){
		
		if(raw == null) return null;
		String [] ary = null;
		
		try{
			//raq 의 byte
			byte[] rawBytes = raw.getBytes(encoding);
			int rawLength = rawBytes.length;
			
			int index = 0;
			int minus_byte_num=0;
			int offset=0;
			int hangul_byte_num = encoding.equals("UTF-8") ? 3: 2;
			
			if(rawLength > len){
				
				double doubleAryLeng = rawLength / (len*1.0);
				
				int aryLength = (int)Math.ceil(doubleAryLeng);
				ary = new String [aryLength];
				
				for(int i =0; i < aryLength; i++){
					minus_byte_num = 0;
					offset = len;
					if(index + offset > rawBytes.length){
						offset = rawBytes.length - index;
					}
					for (int j = 0; j < offset; j++) {
						if(((int)rawBytes[index+j] & 0x80) != 0){
							minus_byte_num ++; 
						}
					}
					
					if(minus_byte_num % hangul_byte_num !=0){
						offset -= minus_byte_num % hangul_byte_num;
					}
					ary[i] =  new String (rawBytes,index,offset,encoding);
					
					index += offset;
				}
			}else{
				ary = new String[] {raw};
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return ary;
	
	}
	/**
	 * 문자값  " --> ' 으로 변경
	 * @param str
	 * @return
	 */
	public static String replaceStr(String str){
		boolean check = true;
		String rtn = "";
		if(str.equals("")) check = false;
		
		if(check){
			rtn = str.trim();
			rtn = rtn.replaceAll("\"", "\'");
			rtn = rtn.replaceAll("<br>", " ");
			rtn = rtn.replaceAll("\r\n", " ");
			rtn = rtn.replaceAll("\'", "\"");
			rtn = rtn.replaceAll("<script>", " ");
			rtn = rtn.replaceAll("</script>", " ");
		}
		
		return rtn;
	}
	
    /**
     * Function :  ch 에 해당하는 문자를 제거한다.
     * Write date : (2001-08-27 오후 5:07:23)
     * VAJ@author: 곽주용
     * @return java.lang.String
     * @param date java.lang.String
     * @param ch java.lang.String
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
	 * 문자열에서 코볼를 제거후 대문자로 변환 후 문자열 리턴
	 * getReqStr() 과 동일하나 값을 대문자로 변환하는것이 틀림(보통 이걸 많이 씀)
	 * getReqStrUp("변수값", "기본값")
	 * 예제) String 변수 = 콤라이브인스턴스.getReqStrUp(request.getParameter("COMH_FUNC"), "IQ")
	 * @param str 문자열
	 * @param default_str - str 인자값이 값이 없을 경우 대체 문자열
	 * @return java.lang.Integer
	 */
	public static String getReqStrUp(String str, String default_str)
	{
		str = getTrimStr(str);
		if (str.equals(""))
		{
			return default_str.toUpperCase();
		}
		return str.toUpperCase();
	}

	/**
	 * 문자열에서 코볼특수문자를 제거후 문자열 리턴
	 * getTrimStr("변수값")
	 * 예제) String 변수 = 콤라이브인스턴스.getTrimStr(request.getParameter("COMH_FUNC"))
	 * @param str 문자열
	 * @return java.lang.String
	 */
	public static String getTrimStr(String str_jun)
	{
		
		int end_str = 0;
		int count = 0;

		if(str_jun==null){
			str_jun = "";
		}else{
				str_jun = str_jun.trim();

				for(int i=str_jun.length()-1; i>-1; i--){
					char c=str_jun.charAt(i);		
					if(c!='　'){
						end_str = i;
						count++;			
						break;
					}		
				}// end for
		}
		if(count==0){
			return "";
		}else{
			return str_jun.substring(0,end_str+1);
		}
	}
	
	 /**
     * lpad 함수
     *
     * @param str   대상문자열, len 길이, addStr 대체문자
     * @return      문자열
     */

    public static String lpad(String str, int len, String addStr) {
        String result = str;
        int templen   = len - result.length();

        for (int i = 0; i < templen; i++){
              result = addStr + result;
        }
        
        return result;
    }

    
    /** 메일 발송시 사번이 담긴 ArrayList를 |를 구분으로 가진 String형식으로 변환.
     * 
     */
    public static String makeSendMailEenoListStr(List<String> eenoList){
    	String returnStr = "";
    	if(eenoList.size() > 0){
    		for(int i = 0; i < eenoList.size() ; i ++){
    			
    			if( i == (eenoList.size() -1)){
    				
    				returnStr += eenoList.get(i);
    				break;
    			}
    			returnStr += eenoList.get(i) + "@";
    			
    		}
    		
    	}
    	return returnStr;
    	
    }
    
	public static String fileExtention(String fileName) {
    	String ext = "";
    	int i = fileName.lastIndexOf('.');
    	if(i > 0 && i < fileName.length() -1){
    		ext = fileName.substring(i + 1).toLowerCase();
    	}
    	return ext;
	}
	
	/**
	 * 주민번호를 앞자리 또는 뒷자리만 가져오도록 한다.
	 * @param ssn
	 * @param fronOrBack_gubun (1:앞자리,2:뒷자리)
	 * @return
	 */
    public static String getCutRrn(String ssn,int fronOrBack_gubun) {

    	
    	if(ssn == null || "".equals(ssn)) {
            return "";
        }else{
        	ssn = ssn.replaceAll("-", "");
        	ssn = ssn.trim();
        }
    	
    	if(ssn.length() != 13){
    		 return ssn;
    	}
    	
    	if(fronOrBack_gubun == 1){
    		return ssn.substring(0,6);
    	}else{
    		return ssn.substring(6,13);
    	}
   }
    
	/**
	 * 주민번호를  이용하여 생년월일 반환한다. ( yyyymmdd 형식으로 반환)
	 * @param String rrn(주민번호)
	 * @return
	 */
    public static String getBirthDay_byRrn(String rrn) {

    	rrn  = keepNumberChar(rrn);
    	
    	if(rrn.length() < 13){
    		return rrn;
    	}
    	String yy = rrn.substring(0,2);
    	String mm = rrn.substring(2,4);
    	String dd = rrn.substring(4,6);
    	
    	String yyyy = "";
    	
    	int sex_gubn = Integer.parseInt(rrn.substring(6,7));
    	
    	switch(sex_gubn){
    		case 9 :  yyyy = "18"+yy;break;		//1800년대
    		case 0 :  yyyy = "18"+yy;break;		//1800년대
    		case 1 :  yyyy = "19"+yy;break;		//1900년대
    		case 2 :  yyyy = "19"+yy;break;		//1900년대
    		case 3 :  yyyy = "20"+yy;break;		//2000년대
    		case 4 :  yyyy = "20"+yy;break;		//2000년대
    		case 5 :  yyyy = "19"+yy;break;		//1900년대 외국인
    		case 6 :  yyyy = "19"+yy;break;		//1900년대 외국인
    		case 7 :  yyyy = "20"+yy;break;		//2000년대 외국인
    		case 8 :  yyyy = "20"+yy;break;		//2000년대 외국인
    	}
    	return yyyy+mm+dd;
   }
    
	/**
	 * 자릿수에 0을 추가한다.. (예) 231 을 6자리 형식으로--> 000231
	 * @param String seq (바꿀 숫자) , int length (자릿수)
	 * @return
	 */
    public static String setFormatNumber(String seq ,int length) {

    	int loopCount = length - seq.length();
    	
    	String plus_0 ="";
    	
    	for(int i =1; i <= loopCount ;i++){
    		plus_0 += "0";
    	}
    	return plus_0 + seq;
   }
    
	/**
	 * 주민번호를  이용하여 나이를  반환한다. (단순 년도 계산으로 .. 현재년도 - 주민번호 출생년도) 
	 * @param String rrn(주민번호)
	 * @return
	 */
    public static int getOldByRrn(String rrn) {

    	rrn  = keepNumberChar(rrn);
    	
    	if(rrn.length() < 13){
    		return 0;
    	}
    	String yy = rrn.substring(0,2);
    	
    	String yyyy = "";
    	
    	int sex_gubn = Integer.parseInt(rrn.substring(6,7));
    	int old = 0;
    	switch(sex_gubn){
    		case 9 :  yyyy = "18"+yy;break;		//1800년대
    		case 0 :  yyyy = "18"+yy;break;		//1800년대
    		case 1 :  yyyy = "19"+yy;break;		//1900년대
    		case 2 :  yyyy = "19"+yy;break;		//1900년대
    		case 3 :  yyyy = "20"+yy;break;		//2000년대
    		case 4 :  yyyy = "20"+yy;break;		//2000년대
    		case 5 :  yyyy = "19"+yy;break;		//1900년대 외국인
    		case 6 :  yyyy = "19"+yy;break;		//1900년대 외국인
    		case 7 :  yyyy = "20"+yy;break;		//2000년대 외국인
    		case 8 :  yyyy = "20"+yy;break;		//2000년대 외국인
    	}
    	
    	old = Integer.parseInt(CurrentDateTime.getYear()) - Integer.parseInt(yyyy);
    	return  old;
   }
    
    public static String getDocNo(){
    	
    	Random randomGenerator = new Random();
    	return CurrentDateTime.getDate() + CurrentDateTime.getTime() +  randomGenerator.nextInt(10);
    }
    
    public static String getFileNm(){
    	
    	Random randomGenerator = new Random();
    	return CurrentDateTime.getDate() + CurrentDateTime.getTime() +  randomGenerator.nextInt(10);
    }
    
    public static String uniDecode(String unicode){
    	  StringBuffer str = new StringBuffer();
    	  unicode = StringUtil.isNullToString(unicode);
    	  char ch = 0;
    	  for( int i= unicode.indexOf("\\u"); i > -1; i = unicode.indexOf("\\u") ){
    	   ch = (char)Integer.parseInt( unicode.substring( i + 2, i + 6 ) ,16);
    	   str.append( unicode.substring(0, i) );
    	   str.append( String.valueOf(ch) );
    	   unicode = unicode.substring(i + 6);
    	  }
    	  str.append( unicode );


    	  return str.toString();
    	 }
    	 
    	 public static String uniEncode(String unicode){
    	     StringBuffer str = new StringBuffer();
    	     
    	     for (int i = 0; i < unicode.length(); i++) {
    	      if(((int) unicode.charAt(i) == 32)) {
    	        str.append(" ");
    	       continue;
    	      }
    	       str.append("\\u");
    	       str.append(Integer.toHexString((int) unicode.charAt(i)));
    	      
    	     }
    	     
    	     return str.toString();


    	 }
    	 
	 public static String replaceParameter(String s) {
		 s = StringUtil.replaceString(s, "<", "");
		 s = StringUtil.replaceString(s, ">", "");
		 s = StringUtil.replaceString(s, "'", "");
		 return s;
	 }	 
	 
	 public static String replaceComma(String amt) {
		 
		 if(Integer.parseInt(trimChar(trimChar(amt, ","), ".")) == 0){
			 amt = "0";
		 }else{
			 amt = amt.replace(".", "#");
			 amt = amt.replace(",", ".");
			 amt = amt.replace("#", ",");
		 }
		 return amt;
   }
	 
	 public static String getSystemArea(){
		 Properties properties = new Properties();
			
		try {
			properties.load(new ClassPathResource("systemArea.properties").getInputStream());
		} catch (Exception e) {
		}
		
		return properties.getProperty("SYSTEM_AREA");
	 }
	
	 public static String getDbType(){
		 Properties properties = new Properties();
			
		try {
			properties.load(new ClassPathResource("systemArea.properties").getInputStream());
		} catch (Exception e) {
		}
		
		return properties.getProperty("DB_TYPE");
	 }
	 
	public static boolean getMenuAuthManagerFlag(HttpServletRequest req){
		boolean rtVal = false;
		String menuId = req.getRequestURI();
		String scrnId = menuId.substring(menuId.lastIndexOf("/") + 1, menuId.lastIndexOf(".gas")).toUpperCase();
		String authVal = "4";
		
		try{
			authVal = CommonGasc.getScnAuth(SessionInfo.getSess_empno(req), scrnId, req);
			if("".equals(authVal)){
				authVal = "4";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		if(Integer.parseInt(authVal) < 4){
			rtVal = true;
		}
				 
		return rtVal;
	}
	
	public static String[] getQuizSeq(){
		String[] quizSeqArr = new String[2];
		int   arrIdx     = 0;
		int   nextSeq    = 0;
		int   dupCnt     = 0;
		
		SecureRandom sr = null;
		
		while(arrIdx < quizSeqArr.length){
			try{
				sr = SecureRandom.getInstance("SHA1PRNG");
			}catch(Exception e){
				e.printStackTrace();
			}
			nextSeq = sr.nextInt(9);
			if(nextSeq > 0 && nextSeq < 21){
				for(int i=0; i<quizSeqArr.length; i++){
					if(String.valueOf(nextSeq).equals(quizSeqArr[i])){
						dupCnt++;
					}
				}
				
				if(dupCnt == 0){
					quizSeqArr[arrIdx] = String.valueOf(nextSeq);
					arrIdx++;
				}
			}
		}
		
//		String quizSeq = "";
//		for(int i=0; i<quizSeqArr.length; i++){
//			if(i == 0){
//				quizSeq =  String.valueOf(quizSeqArr[i]);
//			}else{
//				quizSeq += "," + String.valueOf(quizSeqArr[i]);
//			}
//		}
		
		return quizSeqArr;
	}
	
	/**
	 * 문서번호 생성
	 * @return
	 */
	public static String getMakeDocNo() {
		SecureRandom rn = new SecureRandom();
		String docNo = CurrentDateTime.getDate()+CurrentDateTime.getTime()+rn.nextInt(1000000);
		return docNo;
	}
	
	public static String getStepHelp(String str){
		String stepHelpUrl      = "";
		if(!"".equals(str)){
			String[] tempUrl = str.split("\\.");
			stepHelpUrl = tempUrl[0]+"_help.gas";
		}
		
		System.out.println("stepHelpUrl = "+stepHelpUrl);
		return stepHelpUrl;
	}
}