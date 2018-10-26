package com.hncis.common.util;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 *  Class Name  : VoUtil.java<br/>
 *  Description : VO관련 공통 Util Class.<br/><br/>
 *
 *  Modification Information<br/>
 *
 *  <pre>
 *    수정일         수정자                        수정내용
 *   -------      --------    --------------------------------------------------
 *   2016-03-17   박진석      최초 생성
 *  </pre>
 *
 *  @since 2016-03-16
 *  @version 1.0
 *  @author 총무솔루션
 *  <br/><br/>
 */

public class VoUtil {


	public static Object copyMapToVo(Map map, Object obj) throws Exception {
		if(map != null){
			Set entries = map.entrySet();
            Iterator it = entries.iterator();
            Class oClass = obj.getClass();
            Method[] methods = oClass.getDeclaredMethods();

            while (it.hasNext()){
                Map.Entry entry = (Map.Entry) it.next();
                String sKey = (String)entry.getKey();
                String sValue = (String)entry.getValue();

                try {
                    Method method = oClass.getDeclaredMethod("set"+sKey.substring(0,1).toUpperCase()+sKey.substring(1), new Class[]{new String().getClass()});
                    method.invoke(obj, new Object[]{entry.getValue()});
                } catch(Exception ex) {
                   System.out.println("getMethod error : " + ex.toString());
                }
            }
        }
        return obj;
    }
}