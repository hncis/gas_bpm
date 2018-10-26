package com.hncis.common.util;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;

public class JsonUtil {
	/**
	 * 
	 * '[{' 시작하여 '}]' 종료되는 Json 데이터를 파싱하여 List 로 리턴한다.
	 * 이 때 지정한 Class로 bean이 생성된다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object가 추가된 List Object
	 */
	public static List<?> getJsonToList(String jsonString, Class<?> c) {
		return (List<?>) JSONArray.toCollection(JSONArray.fromObject( jsonString), c);
	}
	
	/**
	 * 
	 * ['1','2','3'] 형태의 Json 데이터를 파싱하여 Array 로 리턴한다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object 의 배열
	 */
	public static Object[] getJsonToArray(String jsonString, Class<?> c){
		return (Object[]) JSONArray.toArray(JSONArray.fromObject( jsonString), c);
	}
	
	/**
	 * 
	 * {a='1'} 형태의 Json 데이터를 파싱하여 Object 로 리턴한다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object
	 */
	public static Object getJsonToBean(String jsonString, Class<?> c) {
		return JSONObject.toBean(JSONObject.fromObject( jsonString), c);
	}	
	
	/**
	 * Hashmap을 {"pair":[{"target":"firstTarget", "value":"firstValue"}, {"target":"secondTarget", "value":"secondValue"},,,]} 의 형태로 변환  
	 * @param returnDataMap
	 * @return JSONBaseVO
	 */
	@SuppressWarnings("unchecked")
	public static void getJsonPair(HashMap<String,String> returnDataMap, JSONBaseVO jso ){
		JSONBaseVO json = new JSONBaseVO();
		JSONBaseArray  jsonArr	= new JSONBaseArray();
		
		Set<String> keySet = returnDataMap.keySet();
		Object keyArr[] =(Object[]) keySet.toArray();
		
		for(int i = 0 ; i <keyArr.length;i++){
			json = new JSONBaseVO();
			json.put("target", (String)keyArr[i]);
			json.put("value", (String)returnDataMap.get((String)keyArr[i]));
			jsonArr.add(json);
		}
		
		jso.put("pair", jsonArr);
		
	}
}
