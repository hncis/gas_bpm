package com.hncis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.hncis.common.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * 
 * 컨트롤에서 공통적으로 사용될 메소드를 정의해 놓은 클래스
 * 
 * @author 6804401
 *
 */
public class AbstractController extends CommonsMultipartResolver{
	
	/**
	 * view 페이지 경로
	 */
	protected final static String VIEW_PATH = "view";
	
	/**
	 * data 페이지 경로
	 */
	protected final static String DATA_PATH = "data";
	
	/**
	 * Json 데이터 페이지 명
	 */
	protected final static String DATA_JSON_PAGE = DATA_PATH + "/" + "data.json";
	
	/**
	 * Json 데이터 페이지 명
	 */
	protected final static String DATA_JSON_IFRAME_PAGE = DATA_PATH + "/" + "data.json.iframe";
	
	/**
	 * Json 데이터 Key 
	 */
	protected final static String JSON_DATA_KEY = "data";
	
	/**
	 * 기본 Json 데이터
	 */
	protected final static String DEFAULT_SUCCESS_JSON_DATA = "{'success':true,'errmsg':'','data':[]}";//액션 성공시 기본 Json 데이터
	
	/*private void setOperatorId(List<?> objs, String operatorId) {
		for(Object obj : objs) {
			Common common = (Common)obj;
			common.setOperatorId(operatorId);
		}
	}*/
/**
	 * 
	 * '[{' 시작하여 '}]' 종료되는 Json 데이터를 파싱하여 List 로 리턴한다.
	 * 이 때 지정한 Class로 bean이 생성된다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object가 추가된 List Object
	 */
/*	protected List<?> getJsonToList(String jsonString, Class<?> c) {

		return (List<?>) JSONArray.toCollection(JSONArray.fromObject( jsonString), c);
	}*/
/**
	 * 
	 * '[{' 시작하여 '}]' 종료되는 Json 데이터를 파싱하여 List 로 리턴한다.
	 * 이 때 지정한 Class로 bean이 생성된다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object가 추가된 List Object
	 */
	protected List<?> getJsonToList(String jsonString, Class<?> c) {
		String safe = Jsoup.clean(StringUtil.tagRemove(jsonString), Whitelist.basicWithImages());
		System.out.println("jsonString: "+jsonString);
		System.out.println("safe: "+safe);
		return (List<?>) JSONArray.toCollection(JSONArray.fromObject( safe.replace("&quot;", "\"")), c);
	}
/**
	 * 
	 * ['1','2','3'] 형태의 Json 데이터를 파싱하여 Array 로 리턴한다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object 의 배열
	 */
/*	protected Object[] getJsonToArray(String jsonString, Class<?> c){
		return (Object[]) JSONArray.toArray(JSONArray.fromObject(jsonString), c);
	}*/
	
/**
	 * 
	 * ['1','2','3'] 형태의 Json 데이터를 파싱하여 Array 로 리턴한다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object 의 배열
	 */
	protected Object[] getJsonToArray(String jsonString, Class<?> c){
		String safe = Jsoup.clean(StringUtil.tagRemove(jsonString), Whitelist.basicWithImages());
		return (Object[]) JSONArray.toArray(JSONArray.fromObject(safe.replace("&quot;", "\"")), c);
	}
	
/**
	 * 
	 * {a='1'} 형태의 Json 데이터를 파싱하여 html을 제거한후 Object 로 리턴한다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object
	 */
	protected Object getJsonToBean(String jsonString, Class<?> c) {
		String safe = Jsoup.clean(jsonString, Whitelist.basicWithImages());
		System.out.println("jsonString: "+jsonString);
		System.out.println("safe: "+safe);
		return JSONObject.toBean(JSONObject.fromObject(safe.replace("&quot;", "\"")), c);
	}	
	
/**
	 * 
	 * {a='1'} 형태의 Json 데이터를 파싱하여 Object 로 리턴한다.
	 * @param jsonString 원본 Json 데이터
	 * @param c 생성할 Bean Object
	 * @return 생성된 Bean Object
	 */
/*	protected Object getJsonToBean(String jsonString, Class<?> c) {
		return JSONObject.toBean(JSONObject.fromObject(jsonString), c);
	}*/
	
	/**
	 * 
	 * 인스턴스의 타입이 List 인지 여부를 리턴한다.
	 * @param ojb Object
	 * @return 입력받은 Object 의 인스턴스가 List 인지에 대한 여부(true or false)
	 */
	protected boolean isInstanceOfList(Object ojb) {
		if(ojb instanceof List<?>) return true;
		else return false;
	}
	
	/**
	 * 
	 * 세션정보를 Return 한다.
	 * @param request HttpServletRequest
	 * @return HttpSession
	 */
	protected HttpSession getSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session == null) {
			session = request.getSession();
		}
		return session;
	}
	
	/**
	 * 
	 * 세션정보를  remove 시킨다.
	 * @param request HttpServletRequest
	 * @return HttpSession
	 */
	protected void removeSession(HttpServletRequest request, String sessionKey)	{
		HttpSession session = request.getSession(false);
		if (session != null)	{
			session.removeAttribute("_RSA_WEB_Key_");
			session.removeAttribute(sessionKey);
			session.invalidate();
		}
		
	}
}
