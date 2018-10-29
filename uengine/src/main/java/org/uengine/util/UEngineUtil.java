package org.uengine.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Point;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.CharBuffer;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.namespace.QName;

import org.apache.commons.io.IOUtils;
import org.uengine.kernel.Activity;
import org.uengine.kernel.BeanPropertyResolver;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariableValue;
import org.uengine.kernel.UEngineException;
import org.uengine.processmanager.ProcessDefinitionRemote;

/**
 * @author Jinyoung Jang
 */

public class UEngineUtil{
	
	static public Map<String, String> convertParameterListToMap(List<Map<String,String>> parameterList){
		Map parameterMap = new HashMap<String, String>();
		for(Iterator<Map<String,String>> i = parameterList.iterator(); i.hasNext();){
			Map paramMap = i.next();
			if ( paramMap.get("name") != null){
				parameterMap.put(paramMap.get("name"),paramMap.get("value"));
			}
		}
		return parameterMap;
	}
	
	static public String getClassNameOnly(Class activityCls){
		return getClassNameOnly(activityCls.getName());
	}
	
	static public String getClassNameOnly(String clsName){
		return clsName.substring( clsName.lastIndexOf(".")+1);
	}

	
	static public Object addArrayElement(Object array, Object newElement){
		int length = array!=null ? Array.getLength(array) : 0;
		Object newArray = Array.newInstance(newElement.getClass(), length+1);
		
		if(length>0)
			System.arraycopy(array, 0, newArray, 0, length);

		Array.set(newArray, length, newElement);		

		return newArray;
	}
	
    /**
     * Gets a named property from a JavaBean and returns its value as a String,
     * possibly null.
     */
    public static String beanPropertyValue(Object bean, String name)
            throws Exception {
        Object value = beanPropertyValueObject(bean, name);
        return (value != null ? value.toString() : null);
    }

    /**
     * Gets a named property (possibly an array property) from a JavaBean and
     * returns its values as an array of Strings, possibly null. If the property
     * is not an array property, an array of size 1 is returned.
     */
    public static String[] beanPropertyValues(Object bean, String name)
            throws Exception {
        Object value = beanPropertyValueObject(bean, name);
        if (value != null) {
            // Check if the value is an array object
            if (value.getClass().isArray()) {
                // Convert to an array of Strings
                int n = java.lang.reflect.Array.getLength(value);
                String[] strs = new String[n];
                for (int i = 0; i < n; i++) {
                    Object o = java.lang.reflect.Array.get(value, i);
                    strs[i] = (o != null ? o.toString() : null);
                }
                return strs;
            }
            // If not an array, just convert the object to a String in an array
            // and return
            else {
                return new String[] { value.toString() };
            }
        } else {
            return null;
        }
    }

    /**
     * Gets a named property from a JavaBean and returns its value as an Object,
     * possibly null.
     */
    public static Object beanPropertyValueObject(Object bean, String name)
            throws Exception {
        if (bean != null) {
            Method reader = null;
            Object[] params = null;

            // Try to find a reader method for the named property
            try {
                PropertyDescriptor prop = new PropertyDescriptor(name, bean
                        .getClass());
                reader = prop.getReadMethod();
            } catch (IntrospectionException e) {
                // No property exists with that name, try a generic get method
                // Object get( Object key )
                try {
                    reader = bean.getClass().getMethod("get",
                            new Class[] { Object.class });
                    params = new Object[] { name };
                } catch (NoSuchMethodException f) {
                    // Try an Object get( String key) method
                    try {
                        reader = bean.getClass().getMethod("get",
                                new Class[] { String.class });
                        params = new Object[] { name };
                    } catch (NoSuchMethodException g) {
                        // Give up
                    }
                }
            }

            // If a reader method has been found
            if (reader != null) {
                try {
                    return reader.invoke(bean, params);
                } catch (IllegalAccessException e) {
                } catch (IllegalArgumentException e) {
                } catch (InvocationTargetException e) {
                    throw new Exception("Exception getting property \""
                            + name + "\" from bean "
                            + bean.getClass().getName() + ": "
                            + e.getTargetException());
                }
            }
        }

        return null;
    }

	

	static public void initializeProperties(Object destination, Object[] props){
		Class destCls = destination.getClass();

		String propName;
		Object propData;
		for(int i=0; i<props.length; i+=2){
			propName = (String)props[i];
			propData = props[i+1];
			
			propName = propName.substring(0,1).toUpperCase() + propName.substring(1, propName.length());
System.out.println("propName = "+propName + " propData = " +  propData + " propData.class " + propData.getClass());
			
			try{
				Method m = destCls.getMethod("set" + propName, new Class[]{propData.getClass()});
				
System.out.println("method = "+m + "method.getParameters[0]" + m.getParameterTypes()[0] );

				m.invoke(destination, new Object[]{propData});
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}	
	
	static public String getSafeString(String src, String defaultStr){
		if(isNotEmpty(src)) return src;
		return defaultStr;
	}

	static public String getComponentClassName(Class cls, String compType){
		return getComponentClassName(cls, compType, false, false);
	}
	
	static public String getComponentClassName(Class cls, String compType, boolean isDefault, boolean overridesPackage){
		String pkgName = (overridesPackage ? GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE : cls.getPackage().getName());
		String clsName = getClassNameOnly(cls);
		
		return pkgName + "." + compType +(isDefault ? ".Default" : ".")+ clsName + compType.substring(0, 1).toUpperCase() + compType.substring(1, compType.length());		
	}
	
	public static String getProcessDefinitionXML(ProcessDefinition definition) throws Exception {
		return getProcessDefinitionXML(definition, "XPD");
	}
	
	public static String getProcessDefinitionXML(ProcessDefinition definition, String serializer) throws Exception {
		ByteArrayOutputStream bao = new ByteArrayOutputStream();		
		GlobalContext.serialize(definition, bao, serializer);
		return bao.toString(GlobalContext.DATABASE_ENCODING/*"ISO-8859-1"*/);		
	}
	
	static public Object getComponentByEscalation(Class activityCls, String compType){
		return getComponentByEscalation(activityCls, compType, null);
	}
	
	static public void copyStream(InputStream sourceInputStream, OutputStream targetOutputStream) throws Exception{
		int length = 1024;
		byte[] bytes = new byte[length]; 
		int c; 
		int total_bytes=0;
			
		while ((c = sourceInputStream.read(bytes)) != -1) { 
				total_bytes +=c; 
				targetOutputStream.write(bytes,0,c); 
		} 
		
		if (sourceInputStream != null) try { sourceInputStream.close(); } catch (Exception e) {}
		if (targetOutputStream != null) try { targetOutputStream.close(); } catch (Exception e) {}
	}
	
	static public void copyStreamAndDoNotCloseStream(InputStream sourceInputStream, OutputStream targetOutputStream) throws Exception{
		int length = 1024;
		byte[] bytes = new byte[length]; 
		int c; 
		int total_bytes=0;
			
		while((c=sourceInputStream.read(bytes)) != -1) 
		{ 
				total_bytes +=c; 
				targetOutputStream.write(bytes,0,c); 
		} 			
	}
	
	static public Object getComponentByEscalation(Class activityCls, String compType, Object defaultValue){
		Class componentClass = null;
		Class copyOfActivityCls = activityCls;
		
		//try to find proper component by escalation (prior to overriding package)
		String overridingPackageName = GlobalContext.ACTIVITY_DESCRIPTION_COMPONENT_OVERRIDER_PACKAGE;
		if(overridingPackageName!=null){
			do{
				String componentClsName = UEngineUtil.getComponentClassName(copyOfActivityCls, compType, false, true);
	
				try{
					componentClass = Class.forName(componentClsName);
				}catch(ClassNotFoundException e){
				}
				
				//try to find proper component by escalation (with original package)
				if(componentClass==null){
					componentClsName = UEngineUtil.getComponentClassName(copyOfActivityCls, compType);
					try{
						componentClass = Class.forName(componentClsName);
					}catch(ClassNotFoundException e){
					}
				}
				copyOfActivityCls = copyOfActivityCls.getSuperclass();
			}while(componentClass==null && copyOfActivityCls!=Activity.class);
		}
		
		if (componentClass == null) {
			copyOfActivityCls = activityCls;
			//try to find proper component by escalation (with original package)
			do{
				String componentClsName = UEngineUtil.getComponentClassName(copyOfActivityCls, compType);
	
				try{
					componentClass = Class.forName(componentClsName);
				}catch(ClassNotFoundException e){
				}
	
				copyOfActivityCls = copyOfActivityCls.getSuperclass();
			}while(componentClass==null && copyOfActivityCls!=Activity.class);
			
			//try default one
			if(componentClass==null){
				if(defaultValue!=null){
					return defaultValue;
				}else{
					try{
						componentClass = Class.forName(UEngineUtil.getComponentClassName(activityCls, compType, true, false));			
					}catch(ClassNotFoundException e){
					}
				}
			}
		}
		
		try{
			return componentClass.newInstance();
		}catch(Exception e){
			e.printStackTrace();
			
			return null;
		}
	}
	
	//TODO: Too tightly coupled
	static public synchronized void addActivityTypeComponent(Properties options) throws Exception{
		String componentPath = options.getProperty("componentPath");
		String componentFileName = (new File(componentPath)).getName();
		String uEngineHomePath = options.getProperty("uEngineHome");
		String activityTypesXMLPath = uEngineHomePath + "/settings/src/org/uengine/processdesigner/activitytypes.xml";
		//review: uengine-web directory path should be declared as a constant value  
		String signedJarListXMLPath = uEngineHomePath + "/was/server/default/deploy/ext.ear/portal-web-complete.war/html/uengine-web/processmanager/jarlist.xml"; 
		String webImageDir = uEngineHomePath + "/was/server/default/deploy/uengine-web.war/processmanager/images"; 
			
		JarFile jarfile = new JarFile(componentPath);
		ZipEntry entry = jarfile.getEntry("META-INF/activitytypes.xml");
		InputStream is = jarfile.getInputStream(entry);
		ArrayList compActList = (ArrayList)GlobalContext.deserialize(is, String.class);
		is.close();

		//add activity types		
		is = new FileInputStream(activityTypesXMLPath);
		ArrayList actList = (ArrayList)GlobalContext.deserialize(is, String.class);
		is.close();
		
		//TODO: ignore or overwrite if already contains
		actList.addAll(compActList);
		
		OutputStream os = new FileOutputStream(activityTypesXMLPath);
		GlobalContext.serialize(actList, os, String.class);
		os.close();

		//add jarfile location
		is = new FileInputStream(signedJarListXMLPath);
		ArrayList jarList = (ArrayList)GlobalContext.deserialize(is, String.class);
		is.close();
		
		jarList.add(componentFileName);
		
		os = new FileOutputStream(signedJarListXMLPath);
		GlobalContext.serialize(jarList, os, String.class);	
	}
	
	public static String QName2ClassName(QName qName){
		String clsName = "";
		clsName = QName2PackageName(qName.getNamespaceURI()) + "." + qName.getLocalPart();

		return clsName;
	}
	
	public static String QName2PackageName(String name){
		String stubPkgName = "";{
			String targetNS = name;
			
			try{
				URL url = new URL(targetNS);
				String host = url.getHost();
				String path = url.getPath();
				String file = url.getFile();
				
				String fullName = host;
				
				boolean bFirst = true;
				StringTokenizer stk = new StringTokenizer(fullName, ".");
				while(stk.hasMoreTokens()){
					String token = stk.nextToken();
					stubPkgName = token + (bFirst ? "":".") + stubPkgName;
					bFirst = false;
				}
					
				//jws.Purchase_Order_Receiver_v2_WebService.axis.localhost
					
				stubPkgName = stubPkgName + path.replace('.', '_').replace('/', '.');
				
			}catch(Exception e){
			//	e.printStackTrace();
			}
		}
		return stubPkgName;
	}
	
	public static boolean isNotEmpty(String value){
		boolean blnIsData = false;
		if (value != null) {
			value = value.trim();
			if (value.length() > 0 && !"null".equals(value.toLowerCase())) {
				blnIsData = true;
			}
		}
		
		return blnIsData;
	}
	
	
	public static boolean isTrue(Object obj) {
		boolean isTrue = false;
		if (null != obj && isNotEmpty(obj.toString())) {
			String strTrue = obj.toString().trim().toLowerCase();
			isTrue = !(
					"-1".equals(strTrue)
					|| "0".equals(strTrue)
					|| "false".equals(strTrue) 
					|| "no".equals(strTrue)
					|| "n".equals(strTrue)
					|| "undefined".equals(strTrue)
			);
		}
		
		return isTrue;
	}
	
/*	public static Object[] getProcessNameAndVersion(String definitionName){
		String nameOnly=null; int version=-1;{
			int versionPos = definitionName.lastIndexOf("v");
			if(versionPos>-1){
				nameOnly = definitionName.substring(0, versionPos).trim();
				String strversion = definitionName.substring(versionPos+1, definitionName.length()).trim();
				try{
					version = Integer.parseInt(strversion);
				}catch(Exception e){
					version = -1;
				}
			}

			if(version==-1){
				nameOnly = definitionName.trim();
				version = 1;
			}
		}

		return new Object[]{nameOnly, new Integer(version)};
	}*/

	public static String getWebServerBaseURL(){
		String host = System.getProperty("uEngineWebServerBaseURL");
		if ( host == null ) {
			host = "http://localhost";
		}
		return host;
	}
		
	public static String sendViaHttpPost(String urlStr, String[] keyAndValues)
		throws Exception {
		try {
			// Construct data

			StringBuffer sb = new StringBuffer();
			String sep = "";	
			
			for(int i=0; i<keyAndValues.length; i+=2){
				
				String key = keyAndValues[i];
				String value = keyAndValues[i+1];				
				
				if(value == null) continue;
				
				sb	.append(sep)
					.append(URLEncoder.encode(key, "UTF-8"))
					.append("=")
					.append(URLEncoder.encode(value, "UTF-8"));
					
				sep = "&";
			}
			
			String data = sb.toString();
    
			// Send data
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
			wr.close();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			sb = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				sb.append(line + "\n");
			}
			
			rd.close();
			
			return sb.toString();
		} catch (Exception e) {
			throw e;
		}
	}		

	public static InputStream getInputStreamFromHttpPost(String urlStr, String[] keyAndValues)
		throws Exception {
		try {
			// Construct data

			StringBuffer sb = new StringBuffer();
			String sep = "";	
			
			for(int i=0; i<keyAndValues.length; i+=2){
				
				String key = keyAndValues[i];
				String value = keyAndValues[i+1];				
				
				if(value == null) continue;
				
				sb	.append(sep)
					.append(URLEncoder.encode(key, "UTF-8"))
					.append("=")
					.append(URLEncoder.encode(value, "UTF-8"));
					
				sep = "&";
			}
			
			String data = sb.toString();
    
			// Send data
			URL url = new URL(urlStr);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();

			return conn.getInputStream();
		} catch (Exception e) {
			throw e;
		}
	}	

	public static String createInstanceId(ProcessDefinitionRemote pd){
		Calendar now = Calendar.getInstance();
		int y = now.get(Calendar.YEAR);
		int m = now.get(Calendar.MONTH) + 1;
		int d = now.get(Calendar.DATE);
		int h = now.get(Calendar.HOUR);
		int mi = now.get(Calendar.MINUTE);
		int s = now.get(Calendar.SECOND);
			
		return pd.getName().getText().replace(' ', '_') + "-" + y + (m<10?"0"+m:""+m) + (d<10?"0"+d:""+d) + (h<10?"0"+h:""+h) + (mi<10?"0"+mi:""+mi) + (s<10?"0"+s:""+s);
	}

	public static String createInstanceId(ProcessDefinition pd) throws Exception{
		return createInstanceId(new ProcessDefinitionRemote(pd, null));
	}
	
	public static String toOnlyFirstCharacterUpper(String src){
		if(src==null) return null;
		
		if(src.length() == 0) return "";
		
		String firstChar = src.substring(0,1);
		String remainder = src.length() > 1 ? src.substring(1) : "";
		
		return firstChar.toUpperCase() + remainder;
	}


	public static void main(String[] args) throws Exception{
		if(args[0].equals("addActivityTypeComponent")){
			Properties options = new Properties();
			options.setProperty("componentPath", args[1]);
			options.setProperty("uEngineHome", args[2]);

			addActivityTypeComponent(options);		
		}
	}
	
	
	////////////////
	
	

	/** decimalformat */
	private static DecimalFormat subslotFormat = new DecimalFormat("0000");

	/** 占쏙옙占쏙옙 占쌩삼옙; '占쏙옙 占쏙옙占쏙옙 */
	public static int countForRandom = 100;

	/**
	 * 占쏙옙/占쏙옙/占쏙옙/ 占쏙옙占썰리 占쏙옙v 占쏙옙환 
	 */
	public static String getCalendarDir() {
		Calendar calendar = Calendar.getInstance(Locale.KOREA);
		return calendar.get(Calendar.YEAR) + "/" +
			(calendar.get(Calendar.MONTH) + 1) + "/" +
			calendar.get(Calendar.DAY_OF_MONTH);
	}

	public static String getSubDir() {
		int frequency[] = new int[24];				// frequency占쏙옙 1占시곤옙; 占쏘개占쏙옙 占쏙옙占쏙옙 占쏙옙占싸곤옙占쏙옙 占쏙옙占쏙옙 d占쏙옙
		frequency[ 0] = 0;	frequency[12] = 3;		//
		frequency[ 1] = 0;	frequency[13] = 5;		// ex)
		frequency[ 2] = 0;	frequency[14] = 5;		// 占쌔댐옙 占시곤옙占쏙옙 frequency占쏙옙 0占쏙옙 占쏙옙占?
		frequency[ 3] = 0;	frequency[15] = 5;		// -> '9900' 占쏙옙占쏙옙
		frequency[ 4] = 0;	frequency[16] = 5;		//
		frequency[ 5] = 0;	frequency[17] = 5;		// 占쌔댐옙 占시곤옙占쏙옙 14占쏙옙占싱곤옙, 14占쏙옙占쏙옙 frequency占쏙옙 4占쏙옙 占쏙옙占?
		frequency[ 6] = 1;	frequency[18] = 4;		// -> 14占쏙옙  0占쏙옙~14占쏙옙 : '1400' 占쏙옙占쏙옙
		frequency[ 7] = 1;	frequency[19] = 3;		//    14占쏙옙 15占쏙옙~29占쏙옙 : '1401' 占쏙옙占쏙옙
		frequency[ 8] = 3;	frequency[20] = 1;		//    14占쏙옙 30占쏙옙~44占쏙옙 : '1402' 占쏙옙占쏙옙
		frequency[ 9] = 5;	frequency[21] = 1;		//    14占쏙옙 45占쏙옙~59占쏙옙 : '1403' 占쏙옙占쏙옙
		frequency[10] = 6;	frequency[22] = 1;		//
		frequency[11] = 5;	frequency[23] = 0;		// 占쏙옙 frequency占쏙옙 1~60 占쏙옙占싱뤄옙 占쏙옙d占쏙옙 占쏙옙

		Calendar calendar = Calendar.getInstance(Locale.KOREA);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int piece = 0;
		if (frequency[hour]==0) {
			hour = 99;
		} else {
			piece = minute / ( 60 / frequency[hour] );
		}

		String zero = "0000";
		String strHour = zero + Integer.toString(hour);
		strHour = strHour.substring(strHour.length()-2, strHour.length());
		String strPiece = zero + Integer.toString(piece);
		strPiece = strPiece.substring(strPiece.length()-2, strPiece.length());

		return strHour + strPiece;
	}

	/**
	 * 占쏙옙/占쏙옙/占쏙옙 + UniqueFileName + 占쏙옙占쏙옙확占쏙옙占쏙옙
	 */
	public static String getUniqueFile(String originalFileName) {
		return getUniqueFile(originalFileName, true);
	}
	

	public static String getUniqueFile(String originalFileName, boolean makeSubFolder) {
		if (makeSubFolder) {
			return getCalendarDir() + "/" + getSubDir() + "/" + getUniqID() + "." + getFileExt(originalFileName);
		} else {
			return getCalendarDir() + "/" + getUniqID() + "." + getFileExt(originalFileName);
		}
	}

	public static String getNamedExtFile(String namedFile, String ext) { 
		return namedFile.substring(0, namedFile.lastIndexOf('.'))+"."+ext;
	}

	
	/**
	 * String 諛곗뿴???대???媛믪씠 ?ㅼ뼱媛 ?덈뒗吏 寃?ы븳?? 鍮?媛믪씠 ?덈떎硫?false ?녿떎硫?true 瑜?諛섑솚?쒕떎.
	 * @param values
	 * @return
	 */
	public static boolean isNotEmpty(String[] values){
		boolean blnIsData = true;
		
		if (values != null) {
			for(int i=0; i<values.length; i++) {
				if (!isNotEmpty(values[i])) {
					blnIsData = false;
					break;
				}
			}
		} else {
			blnIsData = false;
		}
		return blnIsData;
	}
	

	/**
	 * Unique 占쏙옙占쏙옙 占싱몌옙 占쏙옙
	 */
	public static String getUniqID() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSS", Locale.KOREA);
		return sdf.format(new Date()) + "_" + getRandomStr();
	}

	public static String getRandomStr() {
		long randomNo = 0;

		for(int i=0; i<100; i++) {
			String temp = "";
			for(int j=0; j<5; j++) {
				temp = temp + Integer.toString(countForRandom);
				countForRandom++;
				if (countForRandom>900) countForRandom = 100;
			}
			randomNo = randomNo + Long.parseLong(temp) + System.currentTimeMillis() + temp.hashCode();
		}

		String randomStr = Long.toString(randomNo);
		randomStr = randomStr.substring(randomStr.length()-12, randomStr.length());

		return randomStr;
	}

	public static void saveContents(String path, String contents) throws IOException {
		saveContents(path, contents, "UTF-8");
	}
	
	/**
	 * 占쏙옙占쏙옙占싶몌옙 properties 占쏙옙 占쏙옙d占쏙옙 占쏙옙占쌘듸옙 占쏙옙占승뤄옙 占쏙옙占쏙옙
	 */
	public static void saveContents(String path, String contents, String encoding) throws IOException {
		saveContents(new File(path), contents, encoding);
	}
		
	public static void saveContents(File file, String contents) throws IOException {
		saveContents(file, contents, "UTF-8");
	}	
	
	public static void saveContents(File file, String contents, String encoding) throws IOException {	
//		File file = new File(path);
		if ( ! file.getParentFile().exists() ) {
			file.getParentFile().mkdirs();
		}
		OutputStreamWriter writer = null;
		FileOutputStream outputStream = new FileOutputStream(file); 
		try {
			writer = new OutputStreamWriter(outputStream, encoding);
			writer.write(contents);
		} finally {
			if ( writer != null ) {
				try {
					writer.close();
				} catch (IOException e) {
				}
			}
		}			
	}
	

	
	/**
	 * CDATA 占쏙옙占쏙옙 
	 * @param data CDATA占쏙옙 占쏙옙占쏙옙占?XML 占쏙옙占쏙옙占쏙옙 
	 * @return CDATA占쏙옙 占쏙옙占?占쏙옙트占쏙옙 占쏙옙占쏙옙占쏙옙
	 */
	public static String getCDataSections(String data) {
		return data.substring(data.indexOf("<![CDATA[")+9,data.lastIndexOf("]]>"));
	}
	
	
	/**
	   * 占쏙옙트占쏙옙 占쏙옙크占쏙옙占쏙옙占쏙옙(占싸듸옙占쏙옙占쏙옙 占쏙옙큰; 占쏙옙환占싼댐옙)
	   * @param     strSrc  占쌉력쏙옙트占쏙옙
	   * @param     nCount  占쏙옙큰占싸듸옙占쏙옙
	   * @exception
	   * @return    占쏙옙큰占쏙옙
	   */
	public static String getTokenChar(String strSrc, int nCount)
								 throws Exception {
		String strRet = "";
		if (strSrc == null) return "";
		try {
			if (strSrc.trim().equals("")) return strRet;

			StringTokenizer tokenTemp = new StringTokenizer(strSrc, 27 + "");

			for (int i = 1; tokenTemp.hasMoreTokens() && (i <= nCount); i++) {
				String strTemp = tokenTemp.nextToken();
				if (i == nCount) strRet = strTemp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRet;
	}
	
	
	/**
	   * 占쏙옙占쌘울옙(strSrc)占쌩울옙占쏙옙 占쏙옙占쏙옙占쏙옙(key)占쏙옙 占싱울옙占쏙옙 占싸듸옙占쏙옙(nCount)占쏙옙 占쌔댐옙占싹댐옙 占쏙옙큰; 占쏙옙환占싼댐옙.
	   * @param     strSrc  占쏙옙占쌘울옙
	   * @param     nCount  占싸듸옙占쏙옙
	   * @param     key     占쏙옙占쏙옙占쏙옙
	   * @exception
	   * @return    占쌔댐옙 占쏙옙큰
	   */
	public static String getTokenString(String strSrc, int nCount, String key)
								 throws Exception {
		String strRet = "";

		if (strSrc == null) return "";

		try {
			if (strSrc.trim().equals("")) return strRet;

			StringTokenizer tokenTemp = new StringTokenizer(strSrc, key);

			for (int i = 1; tokenTemp.hasMoreTokens() && (i <= nCount); i++) {
				String strTemp = tokenTemp.nextToken();
				if (i == nCount) strRet = strTemp;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strRet;
	}
	
	public static String getTokenString(String str, int col)
								 throws Exception {
		StringTokenizer st = new StringTokenizer(str, "|");
		if (st.hasMoreTokens()) {
			if (col == 2) st.nextToken();
			if (st.hasMoreTokens()) return st.nextToken();
		}
		return null;
	}

	/**
	   * 占쌍억옙占쏙옙 占쏙옙占쌘울옙占쏙옙占쏙옙 parameter占쏙옙 占쏙옙占쏙옙占쏙옙 delemeter占쏙옙 占쏙옙크占쏙옙占쏙옙징 占쏙옙 占쏙옙占싹댐옙 占쏙옙占? 占쏙옙환占싼댐옙.
	   * @param     strSrc  占쏙옙占쌘울옙
	   * @param     nCount  占싸듸옙占쏙옙
	   * @param     delemeter 占쏙옙占쏙옙占쏙옙
	   * @exception
	   * @return    占쌔댐옙 占쏙옙큰
	   */
	public static String getToken(String strSrc, int nCount, String delemeter)
					throws Exception {
		String strRet = "";
		try {
			StringTokenizer tokenTemp = new StringTokenizer(strSrc, delemeter);

			for (int i = 1; tokenTemp.hasMoreTokens() && (i <= nCount); i++) {
				String strTemp = tokenTemp.nextToken();

				if (i == nCount) {
					strRet = strTemp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return strRet;
	}
	
	/**
	   * 占쏙옙占쌘울옙(strSrc)占쏙옙 특d占쏙옙占쏙옙(strKey); 占쌕몌옙 占쏙옙占쌘울옙(strKey)占쏙옙 占쏙옙占쏙옙占싼댐옙.
	   * @param     strSrc   占쏙옙 占쏙옙占쌘울옙
	   * @param     strKey   占쏙옙占쏙옙占쏙옙占?占쏙옙占쏙옙
	   * @param     strValue 占쏙옙占쏙옙占쏙옙 占쏙옙占쌘울옙
	   * @exception
	   * @return    占쏙옙占쏙옙占?占쏙옙~占쏙옙占쌘울옙
	   */
	public static String replaceString(String strSrc, String strKey, String strValue)
						 throws Exception {
		String strRet = "";
		String strTemp = "";
		try {
			StringTokenizer tokenTemp = new StringTokenizer(strSrc, strKey);
			int nCount = tokenTemp.countTokens();

			for (int i = 1; i <= nCount; i++) {
				if (i == nCount) {
					strRet += getTokenString(strSrc, i, strKey);
				} else {
					strRet += (getTokenString(strSrc, i, strKey) + strValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return strRet;
	}
	
	
	/**
	 * word占쏙옙占쏙옙 占쏙옙占쏜에듸옙占싶뤄옙 copy占쏙옙 占쌩삼옙풔占?<![endif]>占쏙옙 占쏙옙占쏙옙 占쌨소듸옙
	 * @param src 占쌉시뱄옙占쏙옙占싹울옙占쏙옙 占싻억옙占쏙옙占?占쌉시뱄옙占쏙옙占쏙옙 占쏙옙트占쏙옙
	 * @param key 占쏙옙占싹곤옙占쏙옙占싹댐옙 占쌓그듸옙 占쏙옙트占쏙옙 占쏙옙)<![endif]>
	 * @return strRet key占승그몌옙 占쏙옙占쏙옙 占쌉시뱄옙占쏙옙占쏙옙 占쏙옙트占쏙옙
	 */
	public static String deleteString(String src, String key)
						throws Exception { //word占쏙옙占쏙옙 占쏙옙占쏜에듸옙占싶뤄옙 copy占쏙옙 占쌩삼옙풔占?<![endif]>占쏙옙 占쏙옙占쏙옙 占쌨소듸옙

		String beforeTag = "";
		String afterTag = "";
		String strRet = "";

		try {
			int begin = 0;
			int end = 0;
			int nLen = key.length();
			boolean iscontinue = true;

			while (iscontinue) {
				if (src.indexOf(key) > -1) { //占쏙옙f占싹곤옙占쏙옙占싹댐옙 占쏙옙크占쏙옙 x占쏙옙占싹댐옙 占쏙옙占?

					end = src.indexOf(key);
					beforeTag = src.substring(begin, end);
					afterTag = src.substring(end + nLen);
					src = afterTag;
					strRet += beforeTag;

					continue;
				} else { //占쏙옙f占싹곤옙占쏙옙占싹댐옙 占승그곤옙 x占쏙옙占쏙옙占쏙옙 占십댐옙 占쏙옙占?
					strRet += src;
					iscontinue = false;

					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return strRet;
	}
	
	/**
	   * 占쏙옙트占쏙옙占쌩울옙 占싼깍옙占쏙옙 占쏙옙占쌉되억옙占쏙옙占?占실댐옙占싼댐옙.
	   * @param     uni20
	   * @exception
	   * @return    占싼깍옙占쏙옙 占쏙옙占쌉되억옙 占쏙옙占싱몌옙 true, 占싣니몌옙false占쏙옙 占쏙옙占쏙옙
	   */
	public boolean checkHan(String uni20) throws Exception {
		boolean nResult = false;

		try {
			if (uni20 == null) {
				return nResult;
			}

			int len = uni20.length();
			char[] carry = new char[len];

			for (int i = 0; i < len; i++) {
				char c = uni20.charAt(i);

				if ((c < 0xac00) || (0xd7a3 < c)) {
					carry[i] = c;
				} else {
					nResult = true;

					byte[] ksc = String.valueOf(c).getBytes("KSC5601");

					if (ksc.length != 2) {
						carry[i] = '\ufffd';

						//                    System.out.print("Warning : Some of Unicode 2.0 Hangul character was ignored");
					} else {
						carry[i] = (char) (0x3400 + (((ksc[0] & 0xff) - 0xb0) * 94) + 
								   (ksc[1] & 0xff) - 0xa1);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return nResult;
	}
	
	/**
     * 占쏙옙占쌘울옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 체크
     * @param digitStr String 占쏙옙占쌘뤄옙 占쏙옙占쏙옙占쏙옙 占쏙옙占쌘울옙
     * @return boolean  占쏙옙占쌘뤄옙 占쏙옙占쏙옙占실억옙 占쏙옙8占쏙옙 true, 占싣니몌옙 false
     */
    public static boolean isDigit(String digitStr) {
        if (digitStr != null) {
            for (int i = 0; i < digitStr.length(); i++)
                if (!Character.isDigit(digitStr.charAt(i)))
                    return false;
        }
        return true;
    }
    
	/**
	   * html 占쏙옙占쏙옙; 占쏙옙트占쏙옙占쏙옙占쏙옙占?占쏙옙환占싼댐옙.
	   * @param     docPath html占쏙옙占쏙옙 占쏙옙占?
	   * @exception
	   * @return    html 占쏙옙占쏙옙(占쏙옙트占쏙옙)
	   */
	public static String getStringFromHtmlFile(String docPath) 
								 throws Exception {
		String strRet = "";
		String tmpLine = "";

		if (docPath == null) {
			return "占쏙옙占쏙옙占?占쏙옙占쏙옙占쏙옙 占쏙옙f占실억옙킬占?占쏙옙占쏙옙占쏙옙 찾; 占쏙옙 占쏙옙4求占?!";
		}

		StringBuffer sb = new StringBuffer();

		File tmpFile = new File(docPath);

		if (!tmpFile.exists()) {
			return "占쏙옙占쏙옙占?占쏙옙占쏙옙占쏙옙 占쏙옙f占실억옙킬占?占쏙옙占쏙옙占쏙옙 찾; 占쏙옙 占쏙옙4求占?!";
		}

		BufferedReader br = null;

		try {
			//占쏙옙캣占쏙옙占쏙옙占?占쏙옙占싹뤄옙 占싻억옙쨈占?
			br = new BufferedReader(new FileReader(docPath));

			while ((tmpLine = br.readLine()) != null) {
				sb.append(tmpLine);
				sb.append("\n");

				//                  strRet += tmpLine + "\n";
			}

			if (br != null) {
				br.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		return sb.toString();
	}
	
	/**
     * NOTE : 占싼깍옙占싱놂옙 占싹븝옙占쏙옙占쏙옙 占쏙옙占?占쏙옙占쏙옙 크占썩가 占쏙옙占쏙옙占쏙옙 占쌨띰옙 占쏙옙占쌘쇽옙 占쌓놂옙 10占쌘뤄옙 
     * f占쏙옙占쏙옙 占쏙옙占?占쏙옙占쌘쇽옙 占쏙옙占쏙옙占싹댐옙 占쏙옙占쏙옙 占쌨띰옙占쏙옙占?占싫댐옙.
     * 占쏙옙占쏙옙占쏙옙 占싼깍옙占싱놂옙 占싹븝옙占쏙옙占?占쏙옙占쏙옙 ascii 占쏙옙; 占쏙옙占쏘나占쏙옙 占쏙옙占?占쏙옙 占쏙옙占쌘몌옙 
     * 占쏙옙 占쏙옙占쌘몌옙 占싸쏙옙占싹울옙 처占쏙옙占싹듸옙占쏙옙 占싼댐옙.
     */
   	public static String getLimitString(String str, int limitSize) {
		limitSize = limitSize--;
		StringBuffer sb = new StringBuffer();
		for (int i=0;i<str.length(); i++) {
			if ( i > limitSize ) {
				// 5占쏙옙占쌘몌옙 占쏙옙占싶듸옙 占쏙옙占쏙옙占쏙옙 9占쌘곤옙 display 占실댐옙 占싶븝옙占쏙옙 占싼깍옙占쏙옙
				// 占쏙옙치占싹댐옙 占쏙옙占쏙옙 占쏙옙 占싻댐옙. 占쏙옙占쏙옙占쏙옙 占쏙옙占쌘몌옙 占쏙옙 占쏙옙占싸댐옙.
				sb.deleteCharAt(sb.length()-1);
				sb.append("..");
				break;
			}
			int value = (int)str.charAt(i);
			if (value > 127) {
				limitSize--;
			}
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}
	
	
	/**
	   * 占쏙옙占쏙옙 full path占쏙옙 占쏙옙占싹? 占쏙옙환占싼댐옙.
	   * @param     strPath 占쏙옙占쏙옙占쏙옙 占쏙옙체占쏙옙占?
	   * @exception
	   * @return    占쏙옙占싹몌옙
	   */
	public static String getSimpleFileName(String strPath) {
		String strRet = " ";
		try {
			File fTemp = new File(strPath);
			if (fTemp.exists()) {
				strRet = fTemp.getName();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return strRet;
	}

	/**
	   * 占쏙옙占싹?占쏙옙 확占쏙옙占쌘몌옙 占쏙옙환占싼댐옙.
	   * @param     strPath 占쏙옙占싹곤옙占?
	   * @exception
	   * @return    占쏙옙占쏙옙占쏙옙 확占쏙옙占쏙옙
	   */
	public static String getFileExt(String strPath) {
		String strRet = "";
		try {
			int index = strPath.lastIndexOf(".") + 1;

			if (index > -1) {
				strRet = strPath.substring(index);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return strRet;
	}

	/**
	   * 占쏙옙占싹곤옙罐占?f占쏙옙占쏙옙 占쏙옙占쏙옙占싱몌옙占쏙옙 占쏙옙환
	   * @param     strPath 占쏙옙占싹곤옙占?
	   * @exception
	   * @return    占쏙옙占쏙옙占싱몌옙
	   */
	public static String getFileName(String strPath) {
		strPath = replace(strPath, "\\", "/");
		String strRet = "";
		try {
			int index = strPath.lastIndexOf('/') + 1;

			if (index > -1) {
				strRet = strPath.substring(index);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return strRet;
	}

	/**
	   * 占쏙옙占쏙옙 占쏙옙체占쏙옙恝占쏙옙占?占쏙옙占싹몌옙; f占쏙옙占쏙옙 占쏙옙罐占?占쏙옙환占싼댐옙.
	   * @param     strPath 占쏙옙占싹곤옙占?
	   * @exception
	   * @return    占쏙옙占쏙옙占쏙옙 확占쏙옙占쏙옙
	   */
	public static String getFilePath(String strPath) {
		strPath = replace(strPath, "\\", "/");
		String strRet = "";
		try {
			int index = strPath.lastIndexOf('/');

			if (index > -1) {
				strRet = strPath.substring(0, index);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return strRet;
	}	

	
	/**
	  * 8859_1占쏙옙 표占쏙옙占?占쏙옙占쌘울옙; 占쌉력듸옙 CharSet 타占쌉울옙 占승댐옙 占쏙옙占쌘울옙占쏙옙 占쌕뀐옙占쌔댐옙.
	  * @param     8859_1占쏙옙 표占쏙옙占?占쏙옙占쌘울옙
	  * @exception UnsupportedEncodingException
	  * @return    占쌔댐옙 占쏙옙占?CharacterSet 8占쏙옙 占쏙옙환占쏙옙 占쏙옙占쌘울옙
	  */
	public static String toEncode(String str) {
		if ( false ) {
			return str;
		}
		
		String encodedStr = null;
		if (str == null) return encodedStr;
		try {
			encodedStr = new String(str.getBytes("8859_1"), "UTF-8");
			if ( checkEncoding(encodedStr) && (encodedStr.length()!=0) ) {
//				System.out.println("UTF-8");
				return encodedStr;
			}
			encodedStr = new String(str.getBytes("8859_1"), "KSC5601");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String toEncode(String str, String encoding) {
		String encodedStr = null;
		if (str == null) return encodedStr;
		try {
			encodedStr = new String(str.getBytes("8859_1"), encoding);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}	
	
	public static String toURLEncode(String str) {
		try {
			return URLEncoder.encode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	public static String[] toEncode(String[] strs) {
		if ( false ) {
			return strs;
		}		
		
		if ( strs == null ) return null;
		String[] tempStrs = new String[strs.length];
		for(int i=0; i<strs.length; i++) {
			tempStrs[i] = toEncode(strs[i]);
		}
		return tempStrs;
	}
	
	public static String[] toEncode(String[] strs, String encoding) {
		if ( strs == null ) return null;
		String[] tempStrs = new String[strs.length];
		for(int i=0; i<strs.length; i++) {
			tempStrs[i] = toEncode(strs[i], encoding);
		}
		return tempStrs;
	}	
	
	/**
	 * 占쏙옙占쌘울옙占쏙옙 占쏙옙占쌉듸옙 캐占쏙옙占쏙옙 占쌩울옙 占쏙옙占쏙옙 占쏙옙占쌘곤옙 占쌍댐옙占쏙옙 占싯삼옙
	 */
	public static boolean checkEncoding(String uni20) throws Exception {
		boolean nResult = true;		
		try {
			if (uni20 == null) return nResult;			
			int cnt = 0;
			int len = uni20.length();
			char[] carry = new char[len];			
			for (int i = 0; i < len; i++) {
				char c = uni20.charAt(i);
				if ( Integer.toHexString(c).equals("fffd") ) cnt++;
				if ( cnt > 1 ) return false;
				//System.out.println(type + " c : " + c + ", " + Integer.toHexString(c));
			}
			if ( len == 1 && cnt == 1 ) nResult = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nResult;
	}    

    
    /**
	  * KSC5601 표占쏙옙占?占쏙옙占쌘울옙; 占싼깍옙 占쏙옙占쌘울옙占쏙옙 占쌕뀐옙占쌔댐옙.
	  * @param     占싼글뤄옙 표占쏙옙占?占쏙옙占쌘울옙
	  * @exception UnsupportedEncodingException
	  * @return    8859_1 占쏙옙占쌘울옙
	  */
	public static String fromEncode(String str) {
        if (str == null)
            return null;
        try {
			return new String(str.getBytes("UTF-8"), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
    }
	
    /**
	  * KSC5601 표占쏙옙占?占쏙옙占쌘울옙; 占싼깍옙 占쏙옙占쌘울옙占쏙옙 占쌕뀐옙占쌔댐옙.
	  * @param     占싼글뤄옙 표占쏙옙占?占쏙옙占쌘울옙
	  * @exception UnsupportedEncodingException
	  * @return    8859_1 占쏙옙占쌘울옙
	  */
	public static String fromEncode(String str, String encoding) {
       if (str == null)
           return null;
       try {
			return new String(str.getBytes(encoding), "8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
   }	
	
	
	/**
	 * NOTE : StringBuffer占쏙옙 占쏙옙占쏙옙 占쏙옙x占쏙옙 String 클占쏙옙占쏙옙占쏙옙 replace 占쌨소드를 占쏙옙占쏙옙求占?占쏙옙占쏙옙
	 * 50~60 占쏙옙 d占쏙옙 占쏙옙.
	 */
	public static String replace(String src, String oldstr, String newstr) {
		if (src == null)
			return null;		
		StringBuffer dest = new StringBuffer("");
		int  len = oldstr.length();
		int  srclen = src.length();
		int  pos = 0;
		int  oldpos = 0;
		
		while ((pos = src.indexOf(oldstr, oldpos)) >= 0) {
			dest.append(src.substring(oldpos, pos));
			dest.append(newstr);
			oldpos = pos + len;
		}
		
		if (oldpos < srclen)
			dest.append(src.substring(oldpos, srclen));
			
		return dest.toString();
	}
	
	
	/**
	  * 특d 占쏙옙큰; 占쏙옙占쏙옙占싼댐옙.
	  * @param     strSrc  占쏙옙占쏙옙占쏙옙 占쏙옙큰 占쏙옙占쌘울옙
	  * @param     nIndex  占쏙옙占쏙옙占쏙옙 占쏙옙큰占쏙옙 占싸듸옙占쏙옙
	  * @param     strToken  占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙
	  * @exception
	  * @return    String  占쏙옙占쏙옙占?占쏙옙占쌘울옙
	  */
	public static String replaceToken(String strSrc, int nIndex, String strToken)
						throws Exception {
		if (strSrc == null) {
			return strSrc;
		}

		try {
			StringTokenizer st = new StringTokenizer(strSrc, 27 + "");
			int nCount = st.countTokens();

			if (nCount < nIndex) {
				return strSrc;
			}

			StringBuffer sb = new StringBuffer();

			for (int i = 1; i < (nCount + 1); i++) {
				if (nIndex == i) {
					sb.append(strToken);
				} else {
					sb.append(getTokenChar(strSrc, i));
				}

				sb.append(27);
			}

			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}
	
	
	/**
	 * 占쌔쏙옙화占쏙옙 占싻쏙옙占쏙옙躍?占쏙옙쨈占?
	 */
	public static String getHashPassword (String str) {
		byte[] b;
		try {
			b = str.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			b = str.getBytes();
			System.out.println ("Warning: UTF8 not supported.");
		}
		
		MessageDigest currentAlgorithm = null;
		
		try {
			currentAlgorithm = MessageDigest.getInstance("MD5");
		} catch(Exception e) {
			System.out.println("No Such Algorithm");
			return "Failed";
		}
		currentAlgorithm.reset();
		currentAlgorithm.update(b);
		
		byte[] hash = currentAlgorithm.digest();
		
		byte[] password = new byte[8];
		
		for (int i=0; i<8; i++) {
			password[i] = (byte)((hash[i] ^ hash[i+8]) & 0x7f);
			if (password[i] == 0) password[i] = (byte) '0';
		}
		
		try {
			str = new String (password, "UTF8");
		} catch (UnsupportedEncodingException e) {
			str = new String (password);
			System.out.println ("Warining: UTF8 not supported.");
		}
		
		return str;
	}

	public static void copyToFile(File inFile, File outFile) throws IOException {
		if ( !outFile.getParentFile().exists() ) outFile.getParentFile().mkdirs();
		
		BufferedInputStream fin = null;
		BufferedOutputStream fout = null;
		
		try {
			fin = new BufferedInputStream(new FileInputStream(inFile), 2048);
			fout = new BufferedOutputStream(new FileOutputStream(outFile), 2048);
			
			synchronized (fin) {
				synchronized (fout) {
					int intReadByte = -1;
					while ((intReadByte=fin.read()) != -1) {
						fout.write(intReadByte);
					}
				}
			}
			//_copy(fin, fout);
		} finally {
			try {
				if ( fin != null ) fin.close();
			} catch (IOException e ) {
				e.printStackTrace(); 
			}
			try {
				if ( fout != null ) fout.close();
			} catch (IOException e ) { 
				e.printStackTrace(); 
			}
		}		
	}
	/**
	 * 占쏙옙占싹븝옙占쏙옙
	 */
	public static void copyToFile(String inFile, String outFile) throws IOException {
		copyToFile(new File(inFile), new File(outFile));
	}
	
	
	/**
	 * Temporary (only test)
	 */
	public static void _copy(InputStream in, OutputStream out) throws IOException {
		synchronized (in) {
			synchronized (out) {
				byte[] buffer = new byte[2048];
				while (true) {
					int bytesRead = in.read(buffer);
					if (bytesRead == -1) break;
					out.write(buffer, 0, bytesRead);
				}
			}
		}
	}
	
	
	//--------------------- from gwLibUtil
	public static String fillString(String strValue, char chVal, int intLength, int intGu) {
		String strReturn = "";
		for (int i = 0 ; i < (intLength - strValue.length() ) ; i++) {
			strReturn = chVal + strReturn;
		}
		if (intGu  == 0) {
			// strVal占쏙옙 占쌌울옙 chVal占쏙옙 채占쏙옙占?
			strReturn = strReturn + strValue;
		} else if(intGu ==1) {
			// strVal占쏙옙 占쌘울옙 chVal占쏙옙 채占쏙옙占?
			strReturn = strValue + strReturn ;
		} else {
			// 占쌓놂옙 占쌓댐옙占?占쏙옙占쏙옙占쏙옙
			strReturn = strValue;
		}
		return strReturn;
	}
	
	
	/**
	 * String str占쏙옙占쏙옙 userID,int in 占쏙옙占쏙옙 DB character占쏙옙 크占쏙옙
	 */
	public static String changeChar(String str,int in) {
		String _str=str;
		int counMe=str.length();
		while(in-counMe>0) {
			_str = _str + " ";
			counMe++;
		}
		return _str;
	}
	
	/**
	 *
	 */
	public static String createFileName(String strUserId) {
		java.util.Calendar caltmp = java.util.Calendar.getInstance();
		SimpleDateFormat formatter= new SimpleDateFormat ("yyyyMMddHHmmssSSS");
		return formatter.format(caltmp.getTime()) + strUserId;
	}
	
	public static boolean getBoolean(int param) {
		return param==1?true:false;
	}
	
	public static int getInt(boolean param) {
		int intReturn = 0;
		if(param) intReturn = 1;
		return intReturn;
	}
	
	public static String getString(String param) {
		if ( param == null ) return "";
		return param;
	}
	
	public static String getCDATAString(String param) {
		return "<![CDATA["+getString(param)+"]]>";
	}
	
	/** jdk1.2.2 version algorithms */
	private static int hashCode(String str)
    {
        int count = str.length();
        char[] value = new char[count];
        str.getChars(0, count, value, 0);

		int h = 0;
		//int off = offset;
        int off = 0;
		char val[] = value;
		int len = count;

		for (int i = 0; i < len; i++)
			h = 31*h + val[off++];

		return h;
    }
    
	/**
	 * 占쏙옙占쌘울옙占쏙옙占쏙옙 Scriptlet 占승그몌옙 占쏙옙f占싼댐옙.
	 * @param str 占쏙옙환占쏙옙 占쏙옙占쌘울옙..
	 * @return Scriptlet 占승그몌옙 占쏙옙f占쏙옙 占쏙옙占쌘울옙.
	 */
	public static String removeScriptletTag( String str )
	{
		if( str == null || str.indexOf("<%") == -1 )
		{
			return str;
		}
		return str.replaceAll("\\Q<%=\\E|\\Q<%\\E|\\Q%>\\E", "");
	}

	public static Object getBeanProperty(Object bean, String key) throws Exception{
		return getBeanProperty(bean, key,null, false);
	}
		
	public static Object getBeanProperty(Object bean, String key, boolean ignoreBeanPropertyResolver) throws Exception{
		return getBeanProperty(bean, key,null, ignoreBeanPropertyResolver);
	}
	
	public static Object getBeanProperty(Object bean, String key, ProcessInstance instance ,boolean ignoreBeanPropertyResolver) throws Exception{
		//resolve parts
		String [] wholePartPath = key.replace('.','@').split("@");
		
		if(wholePartPath.length == 0){
			wholePartPath = new String[]{null};
		}

		for(int i=0; i<wholePartPath.length; i++){
			String partName = wholePartPath[i];
			
			if(bean instanceof BeanPropertyResolver && !ignoreBeanPropertyResolver){					
				bean = ((BeanPropertyResolver)bean).getBeanProperty(partName);
			}else{
				if(partName==null){
					return bean;
				}else{
					try{
						String methodName = "get" + partName.substring(0,1).toUpperCase() + partName.substring(1);
						if(instance == null){
							Method getter = bean.getClass().getMethod(methodName, new Class[]{});
							bean = (Serializable) getter.invoke(bean, new Object[]{});
						}else{
							Method getter = bean.getClass().getMethod(methodName, new Class[]{ProcessInstance.class});
							bean = (Serializable) getter.invoke(bean, new Object[]{instance});
						}
					}catch (NoSuchMethodException e) {
						throw new UEngineException("No such bean property '" + partName + "' in object "+bean);
					}
				}
			}
		}
			
		return bean;
	}

	public static void setBeanProperty(Object bean, String key, Object propertyValue) throws Exception{
		setBeanProperty(bean, key, propertyValue,null, false);
	}

	public static void setBeanProperty(Object bean, String key, Object propertyValue,boolean ignoreBeanPropertyResolver) throws Exception{
		setBeanProperty(bean, key, propertyValue,null, ignoreBeanPropertyResolver);
	}
	
	private static Class getBeanValueClass(Object obj){
		Class cls=null;
		if(obj instanceof Calendar){
			cls = Calendar.class;
		}else{
			cls = obj.getClass();
		}
		
		return cls;
	}
	
	public static void setBeanProperty(Object bean, String key, Object propertyValue, ProcessInstance instance ,boolean ignoreBeanPropertyResolver) throws Exception{
		
		if(bean == null) throw new UEngineException("Bean object is null");
		
		//resolve parts
		String [] wholePartPath = key.replace('.','@').split("@");
		if(wholePartPath.length == 0){
			wholePartPath = new String[]{null};
		}
		
		for(int i=0; i<wholePartPath.length; i++){
			String partName = wholePartPath[i];
			
			if(i==wholePartPath.length-1){
				if(bean instanceof BeanPropertyResolver && !ignoreBeanPropertyResolver){
					((BeanPropertyResolver)bean).setBeanProperty(partName, propertyValue);
				}else{
					Object beanValue=null;
					if(propertyValue instanceof ProcessVariableValue)  beanValue =((ProcessVariableValue)propertyValue).getValue();
					else beanValue = propertyValue;

					//TODO: the logic getter getter method should be enhanced to list all the methods which starts with "get"
					String methodName = "set" + partName.substring(0,1).toUpperCase() + partName.substring(1);
					Class  beanValueClass = getBeanValueClass(beanValue);
					
					if(instance ==null){
						Method setter = bean.getClass().getMethod(methodName, new Class[]{beanValueClass});
						setter.invoke(bean, new Object[]{beanValue});
					}else{
						Method setter = bean.getClass().getMethod(methodName, new Class[]{ProcessInstance.class,beanValueClass});
						setter.invoke(bean, new Object[]{instance,beanValue});
					}
				}
			}else{
				if(bean instanceof BeanPropertyResolver && !ignoreBeanPropertyResolver){					
					bean = ((BeanPropertyResolver)bean).getBeanProperty(partName);
				}else{
					Method getter = bean.getClass().getMethod("get" + partName.substring(0,1).toUpperCase() + partName.substring(1), new Class[]{});
					bean = (Serializable) getter.invoke(bean, new Object[]{});
				}
			}
		}

	}
	
	public static Point getRelativeLocation(Container container, Component comp){
		
		Point location = comp.getLocation();
		while(comp.getParent() != container && comp.getParent()!=null){
			Point parentLocation = comp.getParent().getLocation();
			location.setLocation(location.getX() + parentLocation.x, location.getY() + parentLocation.getY());
			comp = comp.getParent();
		}
		
		if(comp.getParent()==null){
			throw new RuntimeException(new UEngineException("Couldn't find the container " + container + " from the parent stack."));
		}
		
		return location;
	}
	
	public static Object putMultipleEntryMap(Map map, Object key, Object value){
		if(map.containsKey(key)){
			
			Object existingValue = map.get(key);
			
			ArrayList multiValue;
			if(existingValue instanceof ArrayList){
				multiValue = (ArrayList)existingValue;
			}else{
				multiValue = new ArrayList();
				multiValue.add(existingValue);
			}
			
			multiValue.add(value);
			return map.put(key, multiValue);
		}else
			return map.put(key, value);
	}
	
	public static boolean boolValue(Boolean booleanValue){
		return (booleanValue!=null ? booleanValue.booleanValue() : false);
	}
	
	public static boolean isValidEmailAddress(String emailAddress) {
		Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
		Matcher m = p.matcher(emailAddress);
		boolean matchFound = m.matches();

		if (matchFound)
			return true;
		else
			return false;
	}
	
	public static String encodeUTF8(String s) {
		if (isNotEmpty(s)) {
			try {
				s = URLEncoder.encode(s, "UTF-8");
			} catch (UnsupportedEncodingException e) { }
		}
		return s;
	}
	
	/*
	 * SQL Injection 痍⑥빟?먯쓣 蹂댁셿?섍린 ?꾪븳 ?꾪꽣 硫붿꽌?? 
	 */
	public static String searchStringFilter(String s) {
		s = s.replaceAll("'", "''");
		s = s.replaceAll("\"", "\"\"");
//		s = s.replaceAll("\\", "\\\\");
		s = s.replaceAll(";", "");
		s = s.replaceAll("#", "");
		s = s.replaceAll("--", "");
//		s = s.replaceAll(" ", "");
		return s;
	}
	
	@Deprecated
	public static HashMap<String, Object> sessionToHashMap(HttpSession session) {
		if(session == null) return null;
		Enumeration enu = session.getAttributeNames();
		HashMap<String, Object> map = new HashMap<String, Object>();
		while(enu.hasMoreElements()) {
			String key = (String)enu.nextElement();
			Object value = session.getAttribute(key);
			map.put(key, value);
		}
		return map;
	}
	
	public static InputStream convertInputStreamReaderToInputStream(Reader reader) throws IOException {
		StringWriter sw = new StringWriter();
		IOUtils.copy(reader, sw);
		return new ByteArrayInputStream(sw.toString().getBytes("utf-8"));
	}
	
	public static String convertInputStreamReaderToString(Reader reader) throws IOException {
		StringWriter sw = new StringWriter();
		IOUtils.copy(reader, sw);
		return sw.toString();
	}
	
}