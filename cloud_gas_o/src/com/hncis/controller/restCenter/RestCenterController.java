package com.hncis.controller.restCenter;

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

import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.common.util.Submit;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.restCenter.manager.RestCenterManager;
import com.hncis.restCenter.vo.BgabGascrc01Dto;
import com.hncis.restCenter.vo.BgabGascrc02Dto;
import com.hncis.restCenter.vo.BgabGascrc03Dto;
import com.hncis.restCenter.vo.BgabGascrc04Dto;
import com.hncis.restCenter.vo.BgabGascrc05Dto;
import com.hncis.restCenter.vo.BgabGascrc06Dto;

@Controller
public class RestCenterController extends AbstractController{

	@Autowired
    @Qualifier("restCenterManagerImpl")
	private RestCenterManager restCenterManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/restCenter/doSearchRcToRestCenterCombo.do")
	public ModelAndView doSearchRcToRestCenterCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{

		BgabGascrc01Dto vo = (BgabGascrc01Dto)getJsonToBean(paramJson, BgabGascrc01Dto.class);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");

		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;

			List<BgabGascrc01Dto> codeList = null;

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

			codeList = restCenterManager.selectRcToRestCenterCombo(vo);

			for(BgabGascrc01Dto targetBean : codeList){
				json = new JSONBaseVO();
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getRc_code()));
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getRc_name()));

				jsonArr.add(json);
			}

			jso.put("RC_COMB", jsonArr);

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
	@RequestMapping(value="/hncis/restCenter/doSearchRcToRoomCombo.do")
	public ModelAndView doSearchRcToRoomCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws Exception{

		BgabGascrc03Dto vo = (BgabGascrc03Dto)getJsonToBean(paramJson, BgabGascrc03Dto.class);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");

		try{
	 		JSONBaseVO jso = new JSONBaseVO();
	 		JSONBaseVO json = null;
			JSONBaseArray  jsonArr = null;

			List<BgabGascrc03Dto> codeList = null;

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

			codeList = restCenterManager.selectRcToRoomCombo(vo);

			for(BgabGascrc03Dto targetBean : codeList){
				json = new JSONBaseVO();
				json.put("value", StringUtil.isNullToStrTrm(targetBean.getRm_code()));
				json.put("name", StringUtil.isNullToStrTrm(targetBean.getRm_name()));

				jsonArr.add(json);
			}

			jso.put("MRG_COMB", jsonArr);

			modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return modelAndView;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/restCenter/doSaveRcToRestCenterList.do")
	public ModelAndView doSaveRcToRestCenterList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascrc01Dto> iList = (List<BgabGascrc01Dto>) getJsonToList(iParams, BgabGascrc01Dto.class);
		List<BgabGascrc01Dto> uList = (List<BgabGascrc01Dto>) getJsonToList(uParams, BgabGascrc01Dto.class);

		//수정
		restCenterManager.saveRcToRestCenterList(iList, uList);

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

	@RequestMapping(value="/hncis/restCenter/doSearchRcListToRestCenter.do")
	public ModelAndView doSearchRcListToRestCenter(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascrc01Dto vo = (BgabGascrc01Dto)getJsonToBean(paramJson, BgabGascrc01Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(restCenterManager.selectRcListToRestCenter(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/restCenter/doSaveRcToCalList.do")
	public ModelAndView doSaveRcToCalList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascrc02Dto> iList = (List<BgabGascrc02Dto>) getJsonToList(iParams, BgabGascrc02Dto.class);
		List<BgabGascrc02Dto> uList = (List<BgabGascrc02Dto>) getJsonToList(uParams, BgabGascrc02Dto.class);

		//수정
		restCenterManager.saveRcToCalList(iList, uList);

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

	@RequestMapping(value="/hncis/restCenter/doSearchRcListToCal.do")
	public ModelAndView doSearchRcListToCal(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascrc02Dto vo = (BgabGascrc02Dto)getJsonToBean(paramJson, BgabGascrc02Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(restCenterManager.selectRcListToCal(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/restCenter/doSaveRcToRoomList.do")
	public ModelAndView doSaveRcToRoomList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascrc03Dto> iList = (List<BgabGascrc03Dto>) getJsonToList(iParams, BgabGascrc03Dto.class);
		List<BgabGascrc03Dto> uList = (List<BgabGascrc03Dto>) getJsonToList(uParams, BgabGascrc03Dto.class);

		//수정
		restCenterManager.saveRcToRoomList(iList, uList);

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

	@RequestMapping(value="/hncis/restCenter/doSearchRcListToRoom.do")
	public ModelAndView doSearchRcListToRoom(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascrc03Dto vo = (BgabGascrc03Dto)getJsonToBean(paramJson, BgabGascrc03Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(restCenterManager.selectRcListToRoom(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}


	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/restCenter/doSaveRcToRateList.do")
	public ModelAndView doSaveRcToRateList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{
		//조회조건 설정
		List<BgabGascrc04Dto> iList = (List<BgabGascrc04Dto>) getJsonToList(iParams, BgabGascrc04Dto.class);
		List<BgabGascrc04Dto> uList = (List<BgabGascrc04Dto>) getJsonToList(uParams, BgabGascrc04Dto.class);

		//수정
		restCenterManager.saveRcToRateList(iList, uList);

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

	@RequestMapping(value="/hncis/restCenter/doSearchRcListToRate.do")
	public ModelAndView doSearchRcListToRate(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascrc04Dto vo = (BgabGascrc04Dto)getJsonToBean(paramJson, BgabGascrc04Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(restCenterManager.selectRcListToRate(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/restCenter/doDeleteRcToRestCenterList.do")
	public ModelAndView doDeleteRcToRestCenterList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascrc01Dto> dList = (List<BgabGascrc01Dto>) getJsonToList(dParams, BgabGascrc01Dto.class);

		//수정
		restCenterManager.deleteRcToRestCenterList(dList);

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
	@RequestMapping(value="/hncis/restCenter/doDeleteRcToCalList.do")
	public ModelAndView doDeleteRcToCalList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascrc02Dto> dList = (List<BgabGascrc02Dto>) getJsonToList(dParams, BgabGascrc02Dto.class);

		//수정
		restCenterManager.deleteRcToCalList(dList);

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
	@RequestMapping(value="/hncis/restCenter/doDeleteRcToRoomList.do")
	public ModelAndView doDeleteRcToRoomList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascrc03Dto> dList = (List<BgabGascrc03Dto>) getJsonToList(dParams, BgabGascrc03Dto.class);

		//수정
		restCenterManager.deleteRcToRoomList(dList);

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
	@RequestMapping(value="/hncis/restCenter/doDeleteRcToRateList.do")
	public ModelAndView doDeleteRcToRateList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{
		//조회조건 설정
		List<BgabGascrc04Dto> dList = (List<BgabGascrc04Dto>) getJsonToList(dParams, BgabGascrc04Dto.class);

		//수정
		restCenterManager.deleteRcToRateList(dList);

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

	@RequestMapping(value="/hncis/restCenter/doSelectRcToRequestCountInfo.do")
	public ModelAndView doSelectRcToRequestCountInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		BgabGascrc05Dto dto = (BgabGascrc05Dto) getJsonToBean(paramJson, BgabGascrc05Dto.class);

		BgabGascrc05Dto rsReqDto = restCenterManager.selectRcToRequestCountInfo(dto);

		if(rsReqDto == null){
			rsReqDto = new BgabGascrc05Dto();
		}

		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/restCenter/doUpdateToRequestCountInfo.do")
	public ModelAndView doUpdateToRequestCountInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascrc05Dto dto = (BgabGascrc05Dto) getJsonToBean(paramJson, BgabGascrc05Dto.class);

		restCenterManager.updateToRequestCountInfo(dto);

		CommonMessage message = new CommonMessage();

		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@RequestMapping(value="/hncis/restCenter/doSearchRcToRemainDaysInfo.do")
	public ModelAndView doSearchRcToRemainDaysInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		BgabGascrc06Dto rsReqDto = restCenterManager.selectRcToRemainDaysInfo(dto);

		if(rsReqDto == null){
			rsReqDto = new BgabGascrc06Dto();
		}

		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/restCenter/doSearchRcToUseAmt.do")
	public ModelAndView doSearchRcToUseAmt(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		BgabGascrc06Dto rsReqDto = restCenterManager.selectRcToUseAmt(dto);

		if(rsReqDto == null){
			rsReqDto = new BgabGascrc06Dto();
		}

		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@RequestMapping(value="/hncis/restCenter/doSaveRcToRequestInfo.do")
	public ModelAndView doSaveRcToRequestInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{
		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		CommonMessage message = restCenterManager.isnertRcToRequestInfo(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	@RequestMapping(value="/hncis/restCenter/doSearchRcToRequestInfo.do")
	public ModelAndView doSearchRcToRequestInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = new ModelAndView();

		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		BgabGascrc06Dto rsReqDto = restCenterManager.selectRcToRequestInfo(dto);

		if(rsReqDto == null){
			rsReqDto = new BgabGascrc06Dto();
		}else{
			if(rsReqDto != null){
				rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

				if(!StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){

					CommonApproval commonApproval = new CommonApproval();
					commonApproval.setIf_id(rsReqDto.getIf_id());
					commonApproval.setSystem_code("RC");
					commonApproval.setCorp_cd(dto.getCorp_cd());

					rsReqDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
				}

				modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
			}
		}

		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/restCenter/doSearchRcToRequestInfoByIfId.do")
	public ModelAndView doSearchRcToRequestInfoByIfId(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = new ModelAndView();

		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		BgabGascrc06Dto rsReqDto = restCenterManager.selectRcToRequestInfoByIfId(dto);

		if(rsReqDto == null){
			rsReqDto = new BgabGascrc06Dto();
		}else{
			if(rsReqDto != null){
				rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

				if(!StringUtil.isNullToString(rsReqDto.getIf_id()).equals("")){

					CommonApproval commonApproval = new CommonApproval();
					commonApproval.setIf_id(rsReqDto.getIf_id());
					commonApproval.setSystem_code("RC");
					commonApproval.setCorp_cd(dto.getCorp_cd());

					rsReqDto.setCode(StringUtil.isNullToString(commonManager.getSelectApprovalInfo(commonApproval)));
				}

				modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
			}
		}

		rsReqDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));

		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/restCenter/doSearchRcToReqList.do")
	public ModelAndView doSearchRcToReqList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = restCenterManager.selectCountRcToReqList(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(restCenterManager.selectRcToReqList(dto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		return modelAndView;
	}

	@RequestMapping(value="/hncis/restCenter/doSearchRcToHistoryList.do")
	public ModelAndView doSearchRcToHistoryList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascrc06Dto vo = (BgabGascrc06Dto)getJsonToBean(paramJson, BgabGascrc06Dto.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(restCenterManager.selectRcToHistoryList(vo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/restCenter/doDeleteRcToRequest.do")
	public ModelAndView doDeleteRcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		message = restCenterManager.deleteRcToRequest(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	@RequestMapping(value="/hncis/restCenter/doApproveRcToRequest.do")
	public ModelAndView doApproveRcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		CommonApproval appInfo = new CommonApproval();
		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		message = restCenterManager.updateRcToRequestForApprove(dto, appInfo, message, req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	@RequestMapping(value="/hncis/restCenter/doApproveCancelRcToRequest.do")
	public ModelAndView doApproveCancelRcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		CommonApproval appInfo = new CommonApproval();
		dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		if("".equals(StringUtil.isNullToString(dto.getIf_id()))){
			message = restCenterManager.updateRcToRequestForApproveCancel(dto);
		}else{
			message = restCenterManager.setApprovalCancel(dto, appInfo, message, req);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	@RequestMapping(value="/hncis/restCenter/doConfirmRcToRequest.do")
	public ModelAndView doConfirmRcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		message = restCenterManager.updateRcToRequestForConfirm(dto);

		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String mode       = "confirm";
		String title      = HncisMessageSource.getMessage("rc");
		String corp_cd    = dto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);
		
		Submit.sendEmailConfirm(fromEeno, fromStepNm, mailAdr, mode, title, corp_cd);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	@RequestMapping(value="/hncis/restCenter/doRejectRcToRequest.do")
	public ModelAndView doRejectRcToRequest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson
			) throws Exception{

		CommonMessage message = new CommonMessage();
		BgabGascrc06Dto dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		message = restCenterManager.updateRcToRequestForReject(dto);

		//컨펌취소 메일 발송
		String fromEeno   = SessionInfo.getSess_name(req);
		String fromStepNm = SessionInfo.getSess_step_name(req);
		String toEeno     = dto.getEeno();
		String rtnText    = dto.getSnb_rson_sbc();
		String title      = HncisMessageSource.getMessage("rc");
		String corp_cd 	  = dto.getCorp_cd();

		BgabGascz002Dto bgabGascz002Dto = new BgabGascz002Dto();
		bgabGascz002Dto.setXusr_empno(toEeno);
		bgabGascz002Dto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String mailAdr = commonManager.selectUserMailAddr(bgabGascz002Dto);
		
		Submit.returnEmail(fromEeno, fromStepNm, mailAdr,"", title, rtnText, corp_cd);

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
	@RequestMapping(value="/hncis/restCenter/doExcelRestCenterList.excel")
	public ModelAndView doExcelRestCenterList(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascrc06Dto bgabGascrc06Dto = (BgabGascrc06Dto) getJsonToBean(paramJson, BgabGascrc06Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = restCenterManager.selectCountRcToReqList(bgabGascrc06Dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		bgabGascrc06Dto.setStartRow(1);
 		bgabGascrc06Dto.setEndRow(count);
 		//검색
 		list.setRows(restCenterManager.selectRcToReqList(bgabGascrc06Dto));

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
