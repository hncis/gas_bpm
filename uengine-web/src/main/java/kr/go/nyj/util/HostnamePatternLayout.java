package kr.go.nyj.util; 

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.log4j.PatternLayout;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 실제 사용하기는 properties 방식은 다음과 같고, 
	log4j.appender.stdout=org.apache.log4j.ConsoleAppender
	log4j.appender.stdout.layout=com.semco.util.HostnamePatternLayout
	
	
 * XML 방식은 다음과 같다. 
	<appender name="consoleAppender" class="org.apache.log4j.ConsoleAppender">
	    <layout class="com.semco.util.HostnamePatternLayout">
	        <param name="ConversionPattern" value="%d %p %h  [%c] - %m %n"/>
	    </layout>
	</appender>
*/

public class HostnamePatternLayout extends PatternLayout {

	/** log4j For host pattern Delimeter Character */
	public static final char HOSTNAME_PATTERN_CHAR = 'h';
	protected static final String HOSTNAME;
	static {
		HOSTNAME = getHostname();
	}

	protected static String getHostname() {
		try {
			InetAddress addr = InetAddress.getLocalHost();
			return addr.getHostName();
		} catch (UnknownHostException ex) {
			return "unknown";
		}
	}

	@Override
	protected PatternParser createPatternParser(String pattern) {
		return new PatternParser(pattern) {
		@Override
		protected void finalizeConverter(char c) {
			PatternConverter pc = null;
			switch (c) { // NOPMD - brief to logic
				case HOSTNAME_PATTERN_CHAR:
					pc = new PatternConverter() {
						@Override
						protected String convert(LoggingEvent event) {
							return HOSTNAME;
						}
					};
					break;
				}	
				if (pc == null) {
					super.finalizeConverter(c);
				} else {
					addConverter(pc);
				}
			}
		};
	}
}
