package com.hncis.controller.cooperator;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hncis.common.application.SessionInfo;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.cooperator.manager.CooperatorManager;
import com.hncis.cooperator.vo.BgabGascco01Dto;
import com.hncis.cooperator.vo.BgabGascco02Dto;

@Controller
public class CooperatorController extends AbstractController{

	@Autowired
	@Qualifier("cooperatorManagerImpl")
	private CooperatorManager cooperatorManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/*************************************************************/
	/** request page                                             */
	/*************************************************************/
	/**
	 * 협력업체 등록
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/cooperator/doInsertXcoToRequest.do")
	public ModelAndView doInsertXco01ToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo,
			@RequestParam(value="picInfo", required=true) String picInfo) throws HncisException, SessionException{

		BgabGascco01Dto co01Dto = (BgabGascco01Dto) getJsonToBean(bsicInfo, BgabGascco01Dto.class);
		List<BgabGascco02Dto> co02Dto = (List<BgabGascco02Dto>) getJsonToList(picInfo, BgabGascco02Dto.class);
		
		co01Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		co01Dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		co01Dto.setUpdr_eeno(SessionInfo.getSess_empno(req));

		String corpIdx = "";
		if("".equals(co01Dto.getCorp_idx())) {
			corpIdx = cooperatorManager.selectXcoCorpIdx(co01Dto); 
			co01Dto.setCorp_idx(corpIdx);
		}
		int rs = cooperatorManager.insertXcoToRequest(co01Dto, co02Dto);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		if("".equals(co01Dto.getCorp_idx())) {
			message.setCode(corpIdx);
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * 협력업체 삭제
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/cooperator/doDeleteXcoToRequest.do")
	public ModelAndView doDeleteXcoToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo) throws HncisException, SessionException{
		
		BgabGascco01Dto co01Dto = (BgabGascco01Dto) getJsonToBean(bsicInfo, BgabGascco01Dto.class);
		co01Dto.setIpe_eeno(SessionInfo.getSess_empno(req));
		
		co01Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		int rs1 = cooperatorManager.deleteXcoToRequest(co01Dto);
		
		BgabGascco02Dto co02Dto = new BgabGascco02Dto();
		co02Dto.setCorp_idx(co01Dto.getCorp_idx());
		co02Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		int rs2 = cooperatorManager.deleteXcoPicToRequest(co02Dto);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 협력업체 삭제(그리드)
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/cooperator/doDeleteXcoPicToList.do")
	public ModelAndView doDeleteXcoPicToList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		
		List<BgabGascco02Dto> co02ListDto = (List<BgabGascco02Dto>) getJsonToList(paramJson, BgabGascco02Dto.class);
		
		int rs = cooperatorManager.deleteXcoPicToList(co02ListDto);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 협력업체 조회
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/cooperator/doSelectInfoXcoToRequest.do")
	public ModelAndView doSelectInfoXcoToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascco01Dto co01Dto = (BgabGascco01Dto) getJsonToBean(paramJson, BgabGascco01Dto.class);
		
		BgabGascco01Dto rsDto = new BgabGascco01Dto();
		co01Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		rsDto = cooperatorManager.selectInfoXcoToRequest(co01Dto);
		
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if (rsDto != null) {
			rsDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsDto).toString());
		} else {
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * 협력업체 담당자 조회
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/cooperator/doSelectPicListXcoToRequest.do")
	public ModelAndView doSelectPicListXcoToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascco02Dto co01Dto  = (BgabGascco02Dto) getJsonToBean(paramJson, BgabGascco02Dto.class);

		CommonList list = new CommonList();
		list.setRows(cooperatorManager.selectPicListXcoToRequest(co01Dto));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	/**
	 * 협력업체 담당자 조회
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/cooperator/doSearchCOToList.do")
	public ModelAndView doSearchCOToList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascco01Dto co01Dto = (BgabGascco01Dto) getJsonToBean(paramJson, BgabGascco01Dto.class);
		
		CommonList list = new CommonList();
		list.setRows(cooperatorManager.selectXcoToList(co01Dto));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
}
