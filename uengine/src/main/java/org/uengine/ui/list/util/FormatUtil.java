package org.uengine.ui.list.util;

import java.util.*;
import java.text.*;
import java.sql.Date;
import java.sql.Timestamp;
//import com.inforb.eac.debug.*;
import java.util.regex.*;

import org.apache.log4j.Logger;

/**
 * <p>FormatUtil</p>
 */
public class FormatUtil
{
	private static Logger logger = Logger.getLogger( FormatUtil.class.
			getName() );
	
	public static String getNumberFormat( Object objNumValue,
										  String strNumFormat )
	{
		String strTemp = null;
		if ( objNumValue != null )
		{
			java.text.DecimalFormat df = new java.text.DecimalFormat(
				strNumFormat );
			try
			{
				strTemp = df.format( objNumValue );
			}
			catch ( NumberFormatException e )
			{
				logger.error("getNumberFormat(Object, String)", e); //$NON-NLS-1$
			}
		}

		return strTemp;
	}

	public static Number getParseNumberFormat( String strNumValue,
										  String strNumFormat )
	{
		Number numTemp = null;
		if ( strNumValue != null )
		{
			java.text.DecimalFormat df = new java.text.DecimalFormat(
				strNumFormat );
			try
			{
				numTemp = df.parse( strNumValue, new ParsePosition(0));
//				Object obj = df.parseObject( strNumValue );
//				System.out.println("$$$$$$$$$$$$$:["+obj+"]");
			}
			catch ( Exception e )
			{
				logger.error("getParseNumberFormat(String, String)", e); //$NON-NLS-1$
			}
		}

		return numTemp;
	}


	public static String getDateFormat( String strDateValue,
										String strDateFormat )
	{
		return getDateFormat(StrToDate( strDateValue ), strDateFormat);
	}
	
	public static String getDateFormat( java.sql.Date dtTemp,
										String strDateFormat )
	{
		String strTemp = null;
		if ( dtTemp != null )
		{
			java.text.DateFormat df = new java.text.SimpleDateFormat(
				strDateFormat );
			strTemp = df.format( dtTemp );
		}

		return strTemp;
	}
	
	public static String getDateFormat( java.sql.Timestamp dtTemp,
										String strDateFormat )
	{
		String strTemp = null;
		if ( dtTemp != null )
		{
			java.text.DateFormat df = new java.text.SimpleDateFormat(
				strDateFormat );
			strTemp = df.format( dtTemp );
		}

		return strTemp;
	}

	public static String getStringFormat( String strMsgValue,
										   String strMsgFormat )
	{
		String strTemp = null;
		if ( strMsgValue != null && strMsgFormat != null )
		{
			// "\\x24" == "$"
			strTemp = strMsgFormat.replaceAll( "\\x24", strMsgValue );
		}

		return strTemp;
	}

	public static String getParseStringFormat( String strMsgValue,
										   String strMsgFormat )
	{
		String strTemp = null;
		if ( strMsgValue != null && strMsgFormat != null )
		{
			// "\\x24" == "$"
			strTemp = strMsgFormat.replaceAll( "\\x24", strMsgValue );
		}

		return strTemp;
	}

	public static Date getSqlDateFormat( String s )
	{
		try
		{
			return java.sql.Date.valueOf( s );
		}
		catch ( Exception ex )
		{
		}
		return null;
	}

	public static String getCommaStr( String value )
	{
		if ( value != null )
		{
			String temp;
			java.text.DecimalFormat df = new java.text.DecimalFormat(
				"#,###.###" );

			try
			{
				temp = df.format( new Double( value ) );
			}
			catch ( NumberFormatException e )
			{
				temp = "";
			}
			return temp;
		}

		return "";
	}

	public static String getSaupStr( String value )
	{
		if ( value != null )
		{
			try
			{
				return new String( value.substring( 0, 3 ) + "-" +
								   value.substring( 3, 5 ) + "-" +
								   value.substring( 5, 10 ) );
			}
			catch ( Exception ex )
			{
				return value;
			}

		}

		return "";
	}

	public static String getCommaStr( int value )
	{
		String temp;
		java.text.DecimalFormat df = new java.text.DecimalFormat( "#,###.###" );

		try
		{
			temp = df.format( new Integer( value ) );
		}
		catch ( NumberFormatException e )
		{
			temp = "";
		}
		return temp;
	}

	public static String getCommaStr( long value )
	{
		String temp;
		java.text.DecimalFormat df = new java.text.DecimalFormat( "#,###.###" );

		try
		{
			temp = df.format( new Long( value ) );
		}
		catch ( NumberFormatException e )
		{
			temp = "";
		}
		return temp;
	}

	public static String getCommaStr( double value )
	{
		String temp;
		java.text.DecimalFormat df = new java.text.DecimalFormat( "#,###.###" );

		try
		{
			temp = df.format( new Double( value ) );
		}
		catch ( NumberFormatException e )
		{
			temp = "";
		}
		return temp;
	}

	public static String getTrim( String value )
	{
		if ( value != null )
		{
			return value.trim();
		}

		return "";
	}

	public static String uniToLocal( String unicode )
	{
		if ( unicode == null )
		{
			return null;
		}
		String returnStr = null;
		try
		{
			returnStr = new String( unicode.getBytes( "8859_1" ), "EUC-KR" );
		}
		catch ( Exception e )
		{
			logger.error( e.toString() );
		}

		return returnStr;
	}

	public static String localToUni( String unicode )
	{
		if ( unicode == null )
		{
			return null;
		}
		String returnStr = null;
		try
		{
			returnStr = new String( unicode.getBytes( "KSC5601" ), "EUC-KR" );
		}
		catch ( Exception e )
		{
			logger.error( e.toString() );
		}

		return returnStr;
	}

	public static String deleteChar( String source, String deleteStr )
	{
		if ( source == null || "".equals( source.trim() ) )
		{
			return "";
		}
		StringBuffer temp = new StringBuffer( "" );
		StringTokenizer st = new StringTokenizer( source, deleteStr );

		while ( st.hasMoreTokens() )
		{
			temp.append( st.nextToken() );
		}

		return temp.toString();
	}

	public static String deleteComma( String value )
	{
		return deleteChar( value, "," );
	}

	public static String deleteDash( String value )
	{

		return deleteChar( value, "-" );
	}

	public static String deleteSlash( String value )
	{
		return deleteChar( value, "/" );
	}

	public static String getPostCodeStr( String value )
	{
		try
		{
			return new String( value.substring( 0, 3 ) + "-" +
							   value.substring( 3, 6 ) );
		}
		catch ( Exception ex )
		{
			return value;
		}
	}

	public static String getTimeStr( String time )
	{
		if ( time != null && !time.equals( "" ) )
		{
			return time.substring( 0, 2 ) + ":" + time.substring( 2, 4 );
		}
		return null;
	}

	public static String getCurrencySymbol()
	{
		DecimalFormatSymbols d = new DecimalFormatSymbols( Locale.getDefault() );
		return d.getCurrencySymbol();
	}

	public static String dateToStr( Date date )
	{
		if ( date != null )
		{
			DateFormat df = new SimpleDateFormat( "yyyyMMdd" );
			return df.format( date );
		}

		return "";
	}

	public static String getYear( Date date )
	{
		if ( date != null )
		{
			DateFormat df = new SimpleDateFormat( "yyyy" );
			return df.format( date );
		}

		return "";
	}

	public static Date StrToDate( String str )
	{
		if ( str != null )
		{
			return java.sql.Date.valueOf( str );
		}

		return null;
	}

	public static String uni12To20( String uni12 )
	{
		if ( uni12 == null )
		{
			return null;
		}
		int len = uni12.length();
		char[] out = new char[len];
		byte[] ksc = new byte[2];
		for ( int i = 0; i < len; i++ )
		{
			char c = uni12.charAt( i );
			if ( c < 0x3400 || 0x4dff < c )
			{
				out[i] = c;
			}
			else if ( 0x3d2e <= c )
			{ 
				out[i] = '\ufffd';
			}
			else
			{
				try
				{
					ksc[0] = ( byte ) ( ( c - 0x3400 ) / 94 + 0xb0 );
					ksc[1] = ( byte ) ( ( c - 0x3400 ) % 94 + 0xa1 );
					out[i] = new String( ksc, "KSC5601" ).charAt( 0 );
				}
				catch ( Exception ex )
				{
					return null;
				}
			}
		}
		return new String( out );
	}

	public static String uni20To12( String uni20 )
	{
		if ( uni20 == null )
		{
			return "";
		}

		int len = uni20.length();
		char[] out = new char[len];
		for ( int i = 0; i < len; i++ )
		{
			char c = uni20.charAt( i );
			if ( c < 0xac00 || 0xd7a3 < c )
			{
				out[i] = c;
			}
			else
			{ 
				try
				{
					byte[] ksc = String.valueOf( c ).getBytes( "KSC5601" );
					if ( ksc.length != 2 )
					{
						out[i] = '\ufffd';
					}
					else
					{
						out[i] = ( char ) ( 0x3400
											+ ( ( ksc[0] & 0xff ) - 0xb0 ) * 94 +
											( ksc[1] & 0xff ) - 0xa1 );
					}
				}
				catch ( java.io.UnsupportedEncodingException ex )
				{
					logger.error(
						"Fatal Error: KSC5601 encoding is not supported." );
				}
			}
		}
		return new String( out );
	}

	/**
	 *
	 * @param src String.
	 * @return String.
	 */
	public static String getUploadTime( String src )
	{
		StringBuffer sb = new StringBuffer( "" );
		String re = "";
		int k = 0;

		StringTokenizer tk = new StringTokenizer( src, "/" );
		while ( tk.hasMoreTokens() )
		{
			k++;
			re = tk.nextToken();
			if ( k == 4 )
			{
				sb.append( re );
				sb.append( "/" );
			}
			if ( k == 5 )
			{
				sb.append( re );
				sb.append( "/" );
			}
			if ( k == 6 )
			{
				sb.append( re );
			}
		}

		return sb.toString();
	}

	public static String getSeqTypeNum( int num, int size )
	{
		String numStr = Integer.toString( num );
		int numSize = numStr.length();
		int betSize = size - numSize;
		StringBuffer ret = new StringBuffer();

		for ( int i = 0; i < betSize; i++ )
		{
			ret.append( "0" );
		}

		return ret.append( numStr ).toString();
	}

	public static String getJuminStr( String str )
	{
		if ( str == null || str.trim().equals( "" ) )
		{
			return "";
		}
		else if ( str.length() == 13 )
		{
			return str.substring( 0, 6 ) + "-" + str.substring( 6, 13 );
		}
		return "";
	}

	public static String getBizStr( String str )
	{
		if ( str == null || str.trim().equals( "" ) )
		{
			return "";
		}
		else if ( str.length() == 10 )
		{
			return str.substring( 0, 3 ) + "-" + str.substring( 3, 5 ) + "-" +
				str.substring( 5, 10 );
		}
		return "";
	}

	public static String getTelNoStr( String no1, String no2, String no3 )
	{
		if ( no1 == null || no1.trim().equals( "" ) )
		{
			if ( no2 != null && !no2.trim().equals( "" )
				 && no3 != null && !no3.trim().equals( "" ) )
			{
				return no2.trim() + "-" + no3.trim();
			}
		}
		else
		{
			if ( no2 != null && !no2.trim().equals( "" )
				 && no3 != null && !no3.trim().equals( "" ) )
			{
				return no1.trim() + "-" + no2.trim() + "-" + no3.trim();
			}
		}
		return "";
	}

	public static String getBRStr( String str )
	{

		if ( str == null )
		{
			return "";
		}

		StringBuffer out = new StringBuffer();
		StringTokenizer st = new StringTokenizer( str.toString(), "\n" );
		while ( st.hasMoreTokens() )
		{
			out.append( st.nextToken() + "<br>" );
		}
		return out.toString();

	}

	public static boolean hasEnglish( String str )
	{

		if ( str != null )
		{
			for ( int i = 0; i < str.length(); i++ )
			{
				char a = str.charAt( i );
				if ( ( ( int )a >= 65 && ( int )a <= 90 )
					 || ( ( int )a >= 97 && ( int )a <= 122 ) )
				{
					return true;
				}
			}
		}
		return false;

	}

	public static String getTrimStr( String str )
	{

		if ( str == null )
		{
			return "";
		}

		StringBuffer sb = new StringBuffer();

		StringTokenizer st = new StringTokenizer( str, " " );
		while ( st.hasMoreTokens() )
		{
			sb.append( st.nextToken() );
		}
		return sb.toString();

	}

	public static String transMinus( String str )
	{
		String tmp = getTrim( str );
		int index = tmp.indexOf( "-" );
		if ( index > 0 )
		{
			tmp = "-" + tmp.substring( 0, index );
		}
		return tmp;
	}

	public static void main( String[] args )
	{

		String inputStr = "";
		String patternStr = "BJ_$_$";

		String strResult = getStringFormat(inputStr, patternStr);

		if (logger.isInfoEnabled()) {
			logger
					.info("main(String[]) - ###### strResult:[" + strResult + "]"); //$NON-NLS-1$ //$NON-NLS-2$
		}


//		String patternStr = "([+]*((\\QParam\\E|\\QObject\\E).(\\w+).value)[+]*)";
		patternStr = "((BJ_*)_*)";
		// Compile regular expression
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(strResult);

		// Replace all occurrences of pattern in input
		StringBuffer buf = new StringBuffer();
		boolean found = false;

	        int iLength = matcher.groupCount();
		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - iLength=" + iLength); //$NON-NLS-1$
		}
		    for(int i=0; i< iLength; i++)
			{
				if( matcher.find(i) )
				{
					// Get the match result
					String replaceStr = matcher.group( i );
				if (logger.isInfoEnabled()) {
					logger
							.info("main(String[]) - replaceStr_org=" + replaceStr); //$NON-NLS-1$
				}

					// Convert to uppercase
					//replaceStr = replaceStr.toUpperCase();
					replaceStr = "x";

					// Insert replacement
					matcher.appendReplacement(buf, replaceStr);
				}
			}


//		while ((found = matcher.find())) {
//
//			// Get the match result
//			String replaceStr = matcher.group();
//			System.out.println("replaceStr_org="+replaceStr);
//
//			// Convert to uppercase
//			//replaceStr = replaceStr.toUpperCase();
//			replaceStr = "x";
//
//			// Insert replacement
//			matcher.appendReplacement(buf, replaceStr);
//		}
		matcher.appendTail(buf);

		// Get result
		String result = buf.toString();
		if (logger.isInfoEnabled()) {
			logger.info("main(String[]) - result=" + result); //$NON-NLS-1$
		}

	}
}
