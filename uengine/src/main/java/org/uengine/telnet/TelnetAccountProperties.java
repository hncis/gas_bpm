package org.uengine.telnet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.uengine.kernel.GlobalContext;

public class TelnetAccountProperties {

	private static final String PROPERTIES_FILE = "org/uengine/telnet/telnetAccount.properties";
	private static Properties properties;

	public static URL getResourceURL(String path) {
		URL url = null;

		try {
			url = GlobalContext.getClassLoader().getResource(path);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return url;
	}

	static String getProperty(String keyName) {
		URL url = null;
		InputStream is = null;
		String value = null;

		try {
			url = getResourceURL(PROPERTIES_FILE);

			if (url != null) {
				is = url.openStream();
				properties = new Properties();
				properties.load(is);
				is.close();

				value = properties.getProperty(keyName).trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
				}
		}

		return value;
	}

	public static void main(String args[]) {
		System.out.println(TelnetAccountProperties.getProperty("fep03"));
	}

}
