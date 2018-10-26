package com.hncis.controller.postOffice;

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

import com.hncis.businessCard.vo.BgabGascba02;
import com.hncis.common.Constant;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.postOffice.manager.PostOfficeManager;
import com.hncis.postOffice.vo.BgabGascpo01Dto;

@Controller
public class PostOfficeController extends AbstractController{

	@Autowired
    @Qualifier("postOfficeManagerImpl")
	private PostOfficeManager postOfficeManager;
	
	/*************************************************************/
	/**Post Office request page		                            **/
	/*************************************************************/
	/**
	 * Post Office request save
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/postOffice/doSavePoToRequest.do")
	public ModelAndView doSavePoToRequest(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);

		CommonMessage message = postOfficeManager.savePoToRequest(req, dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * Post Office request Request
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/postOffice/doUpdatePoToRequestForRequest.do")
	public ModelAndView doUpdatePoToRequestForRequest(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);

		CommonMessage message = postOfficeManager.updatePoToRequestForRequest(req, dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * Post Office request Request cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/postOffice/doUpdatePoToRequestForRequestCancel.do")
	public ModelAndView doUpdatePoToRequestForRequestCancel(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);

		CommonMessage message = postOfficeManager.updatePoToRequestForRequestCancel(req, dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * Post Office request Confirm
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/postOffice/doUpdatePoToRequestForConfirm.do")
	public ModelAndView doUpdatePoToRequestForConfirm(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);

		CommonMessage message = postOfficeManager.updatePoToRequestForConfirm(req, dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * Post Office request Request cancel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/postOffice/doUpdatePoToRequestForConfirmCancel.do")
	public ModelAndView doUpdatePoToRequestForConfirmCancel(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);

		CommonMessage message = postOfficeManager.updatePoToRequestForConfirmCancel(req, dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	@RequestMapping(value="/hncis/postOffice/doDeletePoToRequest.do")
	public ModelAndView doDeletePoToRequest(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);

		CommonMessage message = postOfficeManager.deletePoToRequest(req, dto);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * Post Office request search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/postOffice/doSearchInfoPoToRequest.do")
	public ModelAndView doSearchInfoPoToRequest(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);
		
		BgabGascpo01Dto rsReqDto = new BgabGascpo01Dto();
		rsReqDto = postOfficeManager.selectInfoPoToRequest(dto);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/*************************************************************/
	/**Post Office list page		                            **/
	/*************************************************************/
	/**
	 * Post Office list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/postOffice/doSearchGridPoToList.do")
	public ModelAndView doSearchGridPoToList(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);
 		
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }
 		
 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = postOfficeManager.selectCountPoToList(dto);
 		
 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));
 		
 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow); 		
 		list.setRows(postOfficeManager.selectListPoToList(dto));
 
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	/*************************************************************/
	/**Post Office list page		                            **/
	/*************************************************************/
	/**
	 * Post Office list search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/postOffice/doSearchGridPoToConfirm.do")
	public ModelAndView doSearchGridPoToConfirm(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);
 		
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }
 		
 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = postOfficeManager.selectCountPoToConfirm(dto);
 		
 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));
 		
 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow); 		
 		list.setRows(postOfficeManager.selectListPoToConfirm(dto));
 
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	
	/**
	 * business Travel report save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/postOffice/doSavePoToFile.do")
	public void doSavePoToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{
		
		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			postOfficeManager.savePoToFile(req, res, bgabGascZ011Dto);
		}
	}
	
	/**
	 * business Travel report save
	 * @param reportInfoI
	 * @param reportInfoU
	 * @return ModelAndView
	 * @throws HncisException 
	 */
	@RequestMapping(value="/hncis/postOffice/doSearchPoToFile.do")
	public ModelAndView doSearchPoToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(paramJson, BgabGascZ011Dto.class);
		
		CommonList list = new CommonList();
		list.setRows(postOfficeManager.getSelectPoToFile(dto));
		
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
	@RequestMapping(value="/hncis/postOffice/doFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		BgabGascZ011Dto bgabGascZ011Dto = postOfficeManager.getSelectPoToFileInfo(dto);
		
		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName",   bgabGascZ011Dto.getOgc_fil_nm());
		mpfileData.put("fileName",   bgabGascZ011Dto.getFil_nm());
		mpfileData.put("folderName",   "postOffice");
		
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
	@RequestMapping(value="/hncis/postOffice/doDeletePoToFile.do")
	public ModelAndView doDeletePoToFile(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{
		
		List<BgabGascZ011Dto> dto = (List<BgabGascZ011Dto>) getJsonToList(fileInfo, BgabGascZ011Dto.class);
		
		postOfficeManager.deletePoToFile(dto);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * Post Office request Request
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/postOffice/doRejectToRequestForRequest.do")
	public ModelAndView doRejectToRequestForRequest(HttpServletRequest req, HttpServletResponse res, 
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		
		BgabGascpo01Dto dto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);

		postOfficeManager.rejectPoToRequest(dto);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * post office confirm excel
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/hncis/postOffice/doExcelToConfirm.excel")
	public ModelAndView doExcelToConfirm(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		
		BgabGascpo01Dto keyParamDto = (BgabGascpo01Dto) getJsonToBean(paramJson, BgabGascpo01Dto.class);
		
		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int count       = postOfficeManager.selectCountPoToConfirm(keyParamDto);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(count);
		list.setRows(postOfficeManager.selectListPoToConfirm(keyParamDto));
		
		JSONArray gridData = JSONArray.fromObject(list.getRows());
		String[] headerTitleArray = header.replace("[","").replace("]","").split(",");
		String[] headerNameArray  = headerName.replace("[","").replace("]","").split(",");
		String[] fomatterArray    = fomatter.replace("[","").replace("]","").split(",");
		
		Map mpExcelData = new HashMap();
		mpExcelData.put("fileName",   fileName);
		mpExcelData.put("header",     headerTitleArray);
		mpExcelData.put("headerName", headerNameArray);
		mpExcelData.put("fomatter",   fomatterArray);
		mpExcelData.put("gridData",   gridData);
		
		return new ModelAndView("GridExcelView", "excelData", mpExcelData);
	}
}
