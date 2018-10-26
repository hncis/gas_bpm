package com.hncis.controller.books;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hncis.books.manager.BooksManager;
import com.hncis.books.vo.BgabGascbk01Dto;
import com.hncis.books.vo.BgabGascbk02Dto;
import com.hncis.books.vo.BgabGascbk03Dto;
import com.hncis.books.vo.BgabGascbk04Dto;
import com.hncis.common.Constant;
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.postOffice.vo.BgabGascpo01Dto;
import com.hncis.restCenter.vo.BgabGascrc06Dto;
import com.hncis.roomsMeals.vo.BgabGascrm01Dto;
import com.hncis.taxi.vo.BgabGasctx01;
import com.hncis.taxi.vo.BgabGasctx03;
import com.hncis.uniform.vo.BgabGascaf02Dto;

@Controller
public class BooksController extends AbstractController{

	@Autowired
	@Qualifier("booksManagerImpl")
	private BooksManager booksManager;
	
	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;
	
	
	
	/**
	 *  대분류 콤보 데이터 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doSearchBkToLrgCombo.do")
	public ModelAndView doSearchBkToLrgCombo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		BgabGascbk03Dto vo = (BgabGascbk03Dto)getJsonToBean(paramJson, BgabGascbk03Dto.class);
 		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		
		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;
			
			List<BgabGascbk03Dto> codeList = null;
			
			jsonArr = new JSONBaseArray();
			
			if(StringUtil.isNullToStrTrm(vo.getS_type()).equals("A")){
				json = new JSONBaseVO();
				json.put("name", HncisMessageSource.getMessage("total"));
				json.put("value", "");
				jsonArr.add(json);
			}else if(StringUtil.isNullToStrTrm(vo.getS_type()).equals("S")){
				json = new JSONBaseVO();
				json.put("name", HncisMessageSource.getMessage("select"));
				json.put("value", "");
				jsonArr.add(json);
			}
			
			codeList = booksManager.selectBkToLrgCombo(vo);
			
			for(BgabGascbk03Dto targetBean : codeList){
				json = new JSONBaseVO();
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getLrg_cd()));
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getLrg_nm()));
				
				jsonArr.add(json);
			}
			
			jso.put("LRG_COMB", jsonArr);
			
			modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	/**
	 *  대분류 콤보 데이터 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doSearchBkToMrgCombo.do")
	public ModelAndView doSearchBkToMrgCombo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{
		
		BgabGascbk04Dto vo = (BgabGascbk04Dto)getJsonToBean(paramJson, BgabGascbk04Dto.class);
 		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		
		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;
			
			List<BgabGascbk04Dto> codeList = null;
			
			jsonArr = new JSONBaseArray();
			
			if(StringUtil.isNullToStrTrm(vo.getS_type()).equals("A")){
				json = new JSONBaseVO();
				json.put("name", HncisMessageSource.getMessage("total"));
				json.put("value", "");
				jsonArr.add(json);
			}else if(StringUtil.isNullToStrTrm(vo.getS_type()).equals("S")){
				json = new JSONBaseVO();
				json.put("name", HncisMessageSource.getMessage("select"));
				json.put("value", "");
				jsonArr.add(json);
			}
			
			codeList = booksManager.selectBkToMrgCombo(vo);
			
			for(BgabGascbk04Dto targetBean : codeList){
				json = new JSONBaseVO();
				//json.put("key", StringUtil.isNullToStrTrm(targetBean.getLrg_cd()));
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getMrg_cd()));
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getMrg_nm()));
				
				jsonArr.add(json);
			}
			
			jso.put("MRG_COMB", jsonArr);
			
			modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}
	
	
	@RequestMapping(value="/hncis/books/doSearchBkToBookList.do")
	public ModelAndView doSearchBkToBookList(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbk01Dto dto = (BgabGascbk01Dto) getJsonToBean(paramJson, BgabGascbk01Dto.class);
		
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = booksManager.selectCountBkToBookList(dto);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(booksManager.selectBkToBookList(dto));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
			
		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/books/doSearchBkToBookRentList.do")
	public ModelAndView doSearchBkToBookRentList(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		ModelAndView modelAndView = null;
		BgabGascbk02Dto dto = (BgabGascbk02Dto) getJsonToBean(paramJson, BgabGascbk02Dto.class);
		
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = booksManager.selectCountBkToBookRentList(dto);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(booksManager.selectBkToBookRentList(dto));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doRentListToRequestCancel.do")
	public ModelAndView doRentListToRequestCancel(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="iParams" , required=true) String iParams) throws Exception{
		List<BgabGascbk02Dto> dtoList = (List<BgabGascbk02Dto>) getJsonToList(iParams, BgabGascbk02Dto.class);

		int rs = booksManager.deleteRentListToRequestCancel(dtoList);
		if(rs > 0){
			
			for(BgabGascbk02Dto dto : dtoList) {
				// BPM API호출
				String processCode = "P-B-004"; 	//프로세스 코드 (도서 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = "GASBZ01240010";	//액티비티 코드 (도서신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
		
				BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
			}
		}
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("APPLY.0001"));
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/books/doRequestBkToBook.do")
	public ModelAndView doRequestBkToBook(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascbk02Dto dto = (BgabGascbk02Dto) getJsonToBean(paramJson, BgabGascbk02Dto.class);

		CommonMessage message = booksManager.updateBkToBookRequest(dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/books/doReturnBkToBook.do")
	public ModelAndView doReturnBkToBook(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascbk02Dto dto = (BgabGascbk02Dto) getJsonToBean(paramJson, BgabGascbk02Dto.class);

		CommonMessage message = booksManager.updateBkToBookReturn(dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doRentBkToBookList.do")
	public ModelAndView doRentBkToBookList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		List<BgabGascbk02Dto> dtoList = (List<BgabGascbk02Dto>)getJsonToList(paramJson, BgabGascbk02Dto.class);
		
		int rs = booksManager.updateBkToBookRent(dtoList);
		if(rs>0){
			for(BgabGascbk02Dto dto : dtoList) {
				
				// BPM API호출
				String processCode = "P-B-004"; 	//프로세스 코드 (도서 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = "GASBZ01240030";	//액티비티 코드 (도서 담당자확인) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
				String comment = null;
		
				BpmApiUtil.sendFinalCompleteTask(processCode, bizKey, statusCode, loginUserId);
						
			}
		}
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("RENT.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doRentCancelBkToBookList.do")
	public ModelAndView doRentCancelBkToBookList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		List<BgabGascbk02Dto> dtoList = (List<BgabGascbk02Dto>)getJsonToList(paramJson, BgabGascbk02Dto.class);
		
		int rs = booksManager.updateBkToBookRent(dtoList);
		if(rs>0){
			for(BgabGascbk02Dto dto : dtoList) {
				// BPM API호출
				String processCode = "P-B-004"; 	//프로세스 코드 (도서 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = "GASBZ01240030";	//액티비티 코드 (도서 담당자확인) - 프로세스 정의서 참조
				String loginUserId = dto.getUpdr_eeno();	//로그인 사용자 아이디
		
				BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
			}
		}
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("RETURN.0004"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doReturnBkToBookList.do")
	public ModelAndView doReturnBkToBookList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		List<BgabGascbk02Dto> dtoList = (List<BgabGascbk02Dto>)getJsonToList(paramJson, BgabGascbk02Dto.class);
		
		CommonMessage message = booksManager.updateBkToBookReturnList(dtoList);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 도서 대분류 저장
	 *
	 * @param req the req
	 * @param res the res
	 * @param iParams, uParams the param json array - 조건
	 * @return ModelAndView
	 * @throws hncisException the hncis exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doSaveBkToLrgList.do")
	public ModelAndView doSaveBkToLrgList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascbk03Dto> iList = (List<BgabGascbk03Dto>) getJsonToList(iParams, BgabGascbk03Dto.class);
		List<BgabGascbk03Dto> uList = (List<BgabGascbk03Dto>) getJsonToList(uParams, BgabGascbk03Dto.class);
		
		//수정
		booksManager.saveBkToLrgList(iList, uList);
		
		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/books/doSearchBkListToLrgInfo.do")
	public ModelAndView doSearchBkListToLrgInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		BgabGascbk03Dto vo = (BgabGascbk03Dto)getJsonToBean(paramJson, BgabGascbk03Dto.class);
		
		CommonList list = new CommonList();
		//검색
		list.setRows(booksManager.selectBkListToLrgInfo(vo));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doSaveBkToMrgList.do")
	public ModelAndView doSaveBkToMrgList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascbk04Dto> iList = (List<BgabGascbk04Dto>) getJsonToList(iParams, BgabGascbk04Dto.class);
		List<BgabGascbk04Dto> uList = (List<BgabGascbk04Dto>) getJsonToList(uParams, BgabGascbk04Dto.class);
		
		//수정
		booksManager.saveBkToMrgList(iList, uList);
		
		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/books/doSearchBkListToMrgInfo.do")
	public ModelAndView doSearchBkListToMrgInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		BgabGascbk04Dto vo = (BgabGascbk04Dto)getJsonToBean(paramJson, BgabGascbk04Dto.class);
		
		CommonList list = new CommonList();
		//검색
		list.setRows(booksManager.selectBkListToMrgInfo(vo));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}
	
	/**
	 * 도서 대분류 삭제
	 *
	 * @param req the req
	 * @param res the res
	 * @param iParams, uParams the param json array - 조건
	 * @return ModelAndView
	 * @throws hncisException the hncis exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doDeleteBkToLrgList.do")
	public ModelAndView doDeleteBkToLrgList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascbk03Dto> dList = (List<BgabGascbk03Dto>) getJsonToList(dParams, BgabGascbk03Dto.class);
		
		//수정
		booksManager.deleteBkToLrgList(dList);
		
		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doDeleteBkToMrgList.do")
	public ModelAndView doDeleteBkToMrgList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascbk04Dto> dList = (List<BgabGascbk04Dto>) getJsonToList(dParams, BgabGascbk04Dto.class);
		
		//수정
		booksManager.deleteBkToMrgList(dList);
		
		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	
	@RequestMapping(value="/hncis/books/doSearchBkToBookMgmtList.do")
	public ModelAndView doSearchBkToBookMgmtList(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascbk01Dto dto = (BgabGascbk01Dto) getJsonToBean(paramJson, BgabGascbk01Dto.class);
		
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = booksManager.selectCountBkToBookMgmtList(dto);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(booksManager.selectBkToBookMgmtList(dto));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
			
		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/books/doSaveBkToBookInfo.do")
	public ModelAndView doSaveBkToBookInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascbk01Dto dto = (BgabGascbk01Dto) getJsonToBean(paramJson, BgabGascbk01Dto.class);

		CommonMessage message = booksManager.isnertBkToBookInfo(dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/books/doDeleteBkToBookInfo.do")
	public ModelAndView doDeleteBkToBookInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascbk01Dto dto = (BgabGascbk01Dto) getJsonToBean(paramJson, BgabGascbk01Dto.class);

		CommonMessage message = booksManager.deleteBkToBookInfo(dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/books/doSearchInfoBkToBookInfo.do")
	public ModelAndView doSearchInfoBkToBookInfo(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascbk01Dto dto = (BgabGascbk01Dto) getJsonToBean(paramJson, BgabGascbk01Dto.class);
		
		BgabGascbk01Dto rsReqDto = new BgabGascbk01Dto();
		rsReqDto = booksManager.selectInfoBkToBookInfo(dto);
		
		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * business Travel report save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/books/doSaveBkToFile.do")
	public void doSaveBkToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{
		
		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			booksManager.saveBkToFile(req, res, bgabGascZ011Dto);
		}
	}
	
	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException 
	 */
	@RequestMapping(value="/hncis/books/doSearchBkToFile.do")
	public ModelAndView doSearchBkToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);
		
		CommonList list = new CommonList();
		list.setRows(booksManager.getSelectBkToFile(dto));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}
	
	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = booksManager.getSelectBkToFileInfo(dto);
		
		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "books");
		
		return new ModelAndView("download", "fileData", mpfileData);
	}
	
	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/books/doDeleteBkToFile.do")
	public ModelAndView doDeleteBkToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{
		
		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);
		
		booksManager.deleteBkToFile(dto);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * accept excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/books/doExcelBookList.excel")
	public ModelAndView doExcelBookList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascbk02Dto bgabGascbk02Dto = (BgabGascbk02Dto) getJsonToBean(paramJson, BgabGascbk02Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = booksManager.selectCountBkToBookRentList(bgabGascbk02Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascbk02Dto.setStartRow(1);
 		bgabGascbk02Dto.setEndRow(count);
 		//검색
 		list.setRows(booksManager.selectBkToBookRentList(bgabGascbk02Dto));

		JSONArray gridData = JSONArray.fromObject(list.getRows());
		String[] headerTitleArray = header.replace("[","").replace("]","").split(",");
		String[] headerNameArray  = headerName.replace("[","").replace("]","").split(",");
		String[] fomatterArray    = fomatter.replace("[","").replace("]","").split(",");

		Map mpExcelData = new HashMap();
		mpExcelData.put("fileName",   fileName);
		mpExcelData.put("jobName",   "BT");
		mpExcelData.put("header",     headerTitleArray);
		mpExcelData.put("headerName", headerNameArray);
		mpExcelData.put("fomatter",   fomatterArray);
		mpExcelData.put("gridData",   gridData);

		return new ModelAndView("GridExcelView", "excelData", mpExcelData);
	}
}
