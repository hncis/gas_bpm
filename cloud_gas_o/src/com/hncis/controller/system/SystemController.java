package com.hncis.controller.system;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonUserInfo;
import com.hncis.controller.AbstractController;
import com.hncis.roomsMeals.manager.RoomsMealsManager;
import com.hncis.roomsMeals.vo.BgabGascrm01Dto;
import com.hncis.system.manager.SystemManager;
import com.hncis.system.vo.BatchInfo;
import com.hncis.system.vo.BatchProcess;
import com.hncis.system.vo.BgabGasckcalDto;
import com.hncis.system.vo.BgabGascz004Dto;
import com.hncis.system.vo.BgabGascz006Dto;
import com.hncis.system.vo.BgabGascz007Dto;
import com.hncis.system.vo.BgabGascz008Dto;
import com.hncis.system.vo.BgabGascz013Dto;
import com.hncis.system.vo.BgabGascz014Dto;
import com.hncis.system.vo.BgabGascz015Dto;
import com.hncis.system.vo.BgabGascz016Dto;
import com.hncis.system.vo.BgabGascz019Dto;
import com.hncis.system.vo.BgabGascz030Dto;
import com.hncis.system.vo.BgabGascz033Dto;
import com.hncis.system.vo.BgabGascz035Dto;
import com.hncis.system.vo.DashBoard;
import com.hncis.system.vo.TableInfo;
import com.hncis.training.manager.TrainingManager;
import com.hncis.training.vo.BgabGasctr01;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class SystemController. - 메뉴 System controller
 */
@Controller
public class SystemController extends AbstractController{
    private transient Log logger = LogFactory.getLog(getClass());

	/** The system manager. - system 비지니스 로직 class*/
	@Autowired
	@Qualifier("systemManagerImpl")
	private SystemManager systemManager;

	@Autowired
    @Qualifier("roomsMealsManagerImpl") 
	private RoomsMealsManager roomsMealsManager;

	/** The common manager. - 공통 로직 class*/
	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/**
	 * Do search to user management by user info. - 조회조건에서 사용자 정보를 찾는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조회조건
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchToUserManagementByUserInfo.do")
	public ModelAndView doSearchToUserManagementByUserInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

		//조회조건 설정
		CommonUserInfo userDto = (CommonUserInfo) getJsonToBean(paramJson, CommonUserInfo.class);
		//검색
		CommonUserInfo userInfo = commonManager.getSelectUserInfo(userDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(userInfo).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do search to user management by user detail. - 사용자 정보 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchToUserManagementByUserDetail.do")
	public ModelAndView doSearchToUserManagementByUserDetail(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{
		//조회조건 설정
		BgabGascz002Dto userDto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);
		//검색
		userDto.setLocale(req.getSession().getAttribute("reqLocale").toString());
		BgabGascz002Dto userDetailInfo = commonManager.getSelectUserInfoDetail(userDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(userDetailInfo != null){
			//화면의 하단 메시지 설정
			userDetailInfo.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			//조회한 데이터를 string으로 해서 넣어줌.
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(userDetailInfo).toString());
		}else{
			CommonMessage message = new CommonMessage();
			//화면의 하단 메시지 설정
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			message.setCode("noData");
			//메시지를 string으로 해서 넣어줌.
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system userManagement menu search. - 화면 User Mgmt에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchToUserManagementByMenu.do")
	public ModelAndView doSearchToUserManagementByMenu(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		CommonList list = new CommonList();

		BgabGascz002Dto dto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);
		//검색
		list.setRows(systemManager.getSelectMenuListToUserManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system userManagement modify. - 화면 Menu Mgmt에서 modify 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doModifyToUserManagement.do")
	public ModelAndView doModifyToUserManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		//조회조건 설정
		BgabGascz002Dto usrDto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);
		//수정
		systemManager.updateUserInfoToUserManagement(usrDto);

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

	/**
	 * system userManagement dept search. - 검색 조건의 부서 검색
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchToUserManagementByDeptInfo.do")
	public ModelAndView doSearchToUserManagementByDeptInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{
		//조회조건 설정
		BgabGascz003Dto deptDto = (BgabGascz003Dto) getJsonToBean(paramJson, BgabGascz003Dto.class);
		//검색
		BgabGascz003Dto deptInfo = commonManager.getSelectDeptInfo(deptDto);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("EDIT.0000"));
		if(deptInfo != null){
			//화면에서 따로 사용하기 위해 설정.
			message.setCode(StringUtil.isNullToString(deptInfo.getXorg_orga_e()));
			message.setCode1(StringUtil.isNullToString(deptInfo.getXorg_plac_c()));
		}
		else{
			message.setCode("");
		}


		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system system common combo. - 메뉴 system에서 조회조건/grid의 combobox로 사용하기 위해 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param codknd the codknd - 코드종류
	 * @return the common combo - 코드 데이터
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doSearchToSystemCombo.do")
	public ModelAndView getCommonCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

		String codeKnd[];

		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseArray  jsonArr = new JSONBaseArray();
		CommonCode code = new CommonCode();

		String comboVal				 = "";
		String comboName			 = "";
		String comboTable			 = "";
		String comboWhereCondition	 = "";
		String comboGroupBy			 = "";
		String comboorderBy			 = "";

		String []codeKnds = codknd.split(";");

		//코드 종류에 따라 sql 만듦
		for (int i =0; i < codeKnds.length; i++){

			codeKnd = codeKnds[i].split(":");

			if(codeKnd[0].equals("menu_mgrp_cd")){
				comboVal = "MENU_MGRP_CD";
				comboName = "SCRN_NM";
				comboTable = "BGAB_GASCZ004";
				comboWhereCondition  = "MENU_LGRP_CD = '"+codeKnd[2]+"' AND MENU_SGRP_CD = '000' AND USE_YN = '1'"
						+" and locale = '"+req.getSession().getAttribute("reqLocale").toString()+"'";
				comboGroupBy = "";
				comboorderBy = "MENU_MGRP_CD";
			}else if(codeKnd[0].equals("menu_sgrp_cd")){
				comboVal = "MENU_SGRP_CD";
				comboName = "SCRN_NM";
				comboTable = "BGAB_GASCZ004";
				comboWhereCondition  = "MENU_MGRP_CD = '"+codeKnd[2]+"' AND MENU_MGRP_CD != '000' AND USE_YN = '1'"
						+" and locale = '"+req.getSession().getAttribute("reqLocale").toString()+"'";
				comboGroupBy = "";
				comboorderBy = "SCRN_IDC_RNKG";
			}else if(codeKnd[0].equals("batch_name")){
				comboVal = "JOB_ID";
				comboName = "JOB_NAME";
				comboTable = "HMB_JOB_SCHEDULE";
				comboWhereCondition  = "";
				comboGroupBy = "";
				comboorderBy = "CAST(ID AS DECIMAL), JOB_NAME";
			}else if(codeKnd[0].equals("approve_type")){
				comboVal = "MENU_MGRP_CD";
				comboName = "SCRN_NM";
				comboTable = "BGAB_GASCZ004";
				comboWhereCondition  = "MENU_SGRP_CD = '000' AND USE_YN = '1'"
						+" and locale = '"+req.getSession().getAttribute("reqLocale").toString()+"'";
				comboGroupBy = "";
				comboorderBy = "MENU_MGRP_CD";
			}

			code.setValue(comboVal);
			code.setName(comboName);
			code.setTableName(comboTable);
			code.setWhereCondition(comboWhereCondition);
			code.setGroupBy(comboGroupBy);
			code.setOrderBy(comboorderBy);
			code.setComboType(codeKnd.length > 1 ? codeKnd[1]:"N");

			//codeList = commonManager.getDataComboList(code);
			jsonArr = commonManager.getDataCombo(code);
			jso.put(codeKnd[0], jsonArr);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * system userManagement menu search. -화면 Menu Mgmt.에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridMenuInfoToMenuManagement.do")
	public ModelAndView doSearchGridMenuInfoToMenuManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascz004Dto menuDto = (BgabGascz004Dto) getJsonToBean(paramJson, BgabGascz004Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){
 			pageNumber = "1";
		}

 		if(StringUtil.isNullToString(pageSize).equals("")){
 			pageSize = Constant.pageSizeSystem;
		}

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = systemManager.getSelectMenuCountToMenuManagement(menuDto);

 		if(StringUtil.isNullToString(pageSize).equals("1000000")){
 			endRow = count;
		}

		CommonList list = new CommonList();
		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		menuDto.setStartRow(startRow);
 		menuDto.setEndRow(endRow);
 		//검색
		list.setRows(systemManager.getSelectMenuListToMenuManagement(menuDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system menuManagement insert. - 화면 Menu Mgmt.에서 save 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doSaveToMenuManagement.do")
	public ModelAndView doSaveToMenuManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{

		List<BgabGascz004Dto> iMenuListDto = (List<BgabGascz004Dto>)getJsonToList(iParams, BgabGascz004Dto.class);
		List<BgabGascz004Dto> uMenuListDto = (List<BgabGascz004Dto>)getJsonToList(uParams, BgabGascz004Dto.class);

		systemManager.saveListToMenuManagement(iMenuListDto, uMenuListDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system menuManagement modify.- 화면 Menu Mgmt.에서 modify 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json- 조건
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doModifyToMenuManagement.do")
	public ModelAndView doModifyToMenuManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson){

		//조회조건 설정
		List<BgabGascz004Dto> menuListDto = (List<BgabGascz004Dto>)getJsonToList(paramJson, BgabGascz004Dto.class);

		//수정
		systemManager.updateListToMenuManagement(menuListDto);
		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("EDIT.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system menuManagement delete. - 화면 Menu Mgmt.에서 delete 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return the model and view
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteToMenuManagement.do")
	public ModelAndView doDeleteToMenuManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson){

		//조회조건 설정
		List<BgabGascz004Dto> menuListDto = (List<BgabGascz004Dto>)getJsonToList(paramJson, BgabGascz004Dto.class);

		//삭제
		systemManager.deleteListToMenuManagement(menuListDto);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system tableInformation tableInfo search. - 화면 Table Information 에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridTableInfoToTableInformation.do")
	public ModelAndView doSearchGridTableInfoToTableInformation(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		TableInfo tableDto = (TableInfo) getJsonToBean(paramJson, TableInfo.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(systemManager.getSelectTableListToTableInformation(tableDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system tableInformation Attribute search. - 화면 Table Information 에서 좌측 테이블 row 더블 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridAttributeToTableInformation.do")
	public ModelAndView doSearchGridAttributeToTableInformation(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		TableInfo tableDto = (TableInfo) getJsonToBean(paramJson, TableInfo.class);

		CommonList list = new CommonList();

		//검색
		list.setRows(systemManager.getSelectAttributeListToTableInformation(tableDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system tableInformation Attribute search. - 화면 Table Information 에서 좌측 테이블 row 더블 클릭 시 후 우상측 데이터 조회 후 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridIndexToTableInformation.do")
	public ModelAndView doSearchGridIndexToTableInformation(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		TableInfo tableDto = (TableInfo) getJsonToBean(paramJson, TableInfo.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(systemManager.getSelectIndexListToTableInformation(tableDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system batchManagement Batch search.- 화면 Batch Mgmt. 에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json - 조회조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridBatchToBatchManagement.do")
	public ModelAndView doSearchGridBatchToBatchManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BatchInfo batchDto = (BatchInfo) getJsonToBean(paramJson, BatchInfo.class);

		CommonList list = new CommonList();
		//검색
		list.setRows(systemManager.getSelectListToBatchManagement(batchDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system batchManagement parameter search.- 화면 Batch Mgmt. 에서 search 버튼 클릭 후 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridParameterToBatchManagement.do")
	public ModelAndView doSearchGridParameterToBatchManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BatchInfo batchDto = (BatchInfo) getJsonToBean(paramJson, BatchInfo.class);

		CommonList list = new CommonList();

		//검색
		list.setRows(systemManager.getSelectParameterListToBatchManagement(batchDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * **********************************************************.
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	/** Dept Change Management                                   */
	/*************************************************************/
	/**
	 * Dept Change search - 화면 Dept Change Mgmt. 에서 search 버튼 클릭 시 실행되는 메서드
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 */
	@RequestMapping(value="/hncis/system/doSearchGridToDeptChangeManagement.do")
	public ModelAndView doSearchGridToDeptChangeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascz006Dto dto = (BgabGascz006Dto) getJsonToBean(paramJson, BgabGascz006Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = systemManager.getSelectCountToDeptChangeManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(systemManager.getSelectListToDeptChangeManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Dept Change insert. - 화면 Dept Change Mgmt. 에서 save 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json- 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertToDeptChangeManagement.do")
	public ModelAndView doInsertToDeptChangeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{

		List<BgabGascz006Dto> iList = (List<BgabGascz006Dto>)getJsonToList(iParams, BgabGascz006Dto.class);
		List<BgabGascz006Dto> uList = (List<BgabGascz006Dto>)getJsonToList(uParams, BgabGascz006Dto.class);

		int cnt = systemManager.insertToDeptChangeManagement(iList, uList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Dept Change delete.- 화면 Dept Change Mgmt. 에서 delete 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json- 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteToDeptChangeManagement.do")
	public ModelAndView doDeleteToDeptChangeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조건 설정
		List<BgabGascz006Dto> list = (List<BgabGascz006Dto>)getJsonToList(paramJson, BgabGascz006Dto.class);
		//삭제
		int cnt = systemManager.deleteToDeptChangeManagement(list);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * **********************************************************.
	 *
	 * @param req the req
	 * @param res the res
	 * @param codknd the codknd
	 * @return the model and view
	 * @throws Exception the exception
	 */
	/** Manager Management                                   */
	/*************************************************************/
	/**
	 * Manager search- 화면 Manager Mgmt. 에서 검색조건 combo 데이터 가져오는 메서드
	 * @param req
	 * @param res
	 * @param codknd- 코드 종류
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doSearchMenuToMultiCombo.do")
	public ModelAndView doSearchMenuToMultiCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd,
			@RequestParam(value="corp_cd" , required=true) String corp_cd,
			@RequestParam(value="locale" , required=true) String locale) throws Exception{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		List<CommonCode> codeList = null;

		CommonCode code = new CommonCode();
		code.setCodknd(codknd);
		code.setLocale(locale);
		code.setCorp_cd(corp_cd);
		//검색
		codeList = systemManager.getSelectMenuToComboList(code);
		jsonArr = new JSONBaseArray();
		for(CommonCode targetBean : codeList){

			json = new JSONBaseVO();
			json.put("key", StringUtil.isNullToStrTrm(targetBean.getKey()));
			json.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
			json.put("value", StringUtil.isNullToStrTrm(targetBean.getValue()));

			jsonArr.add(json);
		}
		jso.put("MENU", jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * Do search grid to manager management.- 화면 Manager Mgmt. 에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json- 조회조건
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridToManagerManagement.do")
	public ModelAndView doSearchGridToManagerManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		//조회조건 설정
		BgabGascz007Dto dto = (BgabGascz007Dto) getJsonToBean(paramJson, BgabGascz007Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = systemManager.getSelectCountToManagerManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(systemManager.getSelectListToManagerManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Manager Management insert.- 화면 Manager Mgmt. 에서 save 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json- 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doSaveToManagerManagement.do")
	public ModelAndView doInsertToManageManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{

		List<BgabGascz007Dto> iList = (List<BgabGascz007Dto>)getJsonToList(iParams, BgabGascz007Dto.class);
		List<BgabGascz007Dto> uList = (List<BgabGascz007Dto>)getJsonToList(uParams, BgabGascz007Dto.class);

		int cnt = systemManager.saveToManagerManagement(iList, uList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Manager Management Update.- 화면 Manager Mgmt. 에서 edit 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json- 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doUpdateToManagerManagement.do")
	public ModelAndView doUpdateToManageManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		//조건 설정
		List<BgabGascz007Dto> list = (List<BgabGascz007Dto>)getJsonToList(paramJson, BgabGascz007Dto.class);

		BgabGascz002Dto xusr = new BgabGascz002Dto();

		//조건 설정
		for(BgabGascz007Dto vo : list){
			vo.setMenu_mgrp_cd(vo.getXdsm_gubn2().substring(0,3));
			//vo.setXdsm_gubn2(vo.getXdsm_gubn2().substring(3));
		}
		//수정
		int cnt = systemManager.updateToManagerManagement(list);

		int cnt1 = 0;
		int cnt2 = 0;

//		for(int i = 0; i < list.size(); i++){
//			String auth = systemManager.getSelectInfoToManagerManagementAuth(list.get(i).getXdsm_empno());
//			String idcYn = "Y";
//			String mgrpCd = systemManager.getSelectInfoToManagerManagementMenuMgrpCd(list.get(i));
//
//			//xocxusr 권한 변경
//			xusr.setXusr_old_auth(getAuth2(auth, idcYn, mgrpCd, "OLD"));
//			xusr.setXusr_empno(list.get(i).getXdsm_empno());
//			xusr.setXusr_updt_empno(list.get(i).getUpt_empno());
//			cnt1 = systemManager.updateToManagerManagementXusrAuth(xusr);
//
//		}

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("EDIT.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Manager Management delete.- 화면 Manager Mgmt. 에서 delete 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json- 조건
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteToManagerManagement.do")
	public ModelAndView doDeleteToManageManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조건 설정
		List<BgabGascz007Dto> list = (List<BgabGascz007Dto>)getJsonToList(paramJson, BgabGascz007Dto.class);

		BgabGascz002Dto xusr = new BgabGascz002Dto();
		//조건 설정
		for(BgabGascz007Dto vo : list){
			vo.setMenu_mgrp_cd(vo.getXdsm_gubn2().substring(0,3));
			//vo.setXdsm_gubn2(vo.getXdsm_gubn2().substring(3));
		}
		//삭제
		int cnt = systemManager.deleteToManagerManagement(list);

		int cnt1 = 0;
		int cnt2 = 0;

//		for(int i = 0; i < list.size(); i++){
//			String auth = systemManager.getSelectInfoToManagerManagementAuth(list.get(i).getXdsm_empno());
//			String idcYn = "N";
//			String mgrpCd = systemManager.getSelectInfoToManagerManagementMenuMgrpCd(list.get(i));
//
//			//xocxusr 권한 변경
//			xusr.setXusr_old_auth(getAuth2(auth, idcYn, mgrpCd, "OLD"));
//			xusr.setXusr_empno(list.get(i).getXdsm_empno());
//			xusr.setXusr_updt_empno(list.get(i).getUpt_empno());
//			cnt1 = systemManager.updateToManagerManagementXusrAuth(xusr);
//
//		}

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Gets the auth2.
	 *
	 * @param workAuth the work auth
	 * @param idcYn the idc yn
	 * @param menuMgrpCd the menu mgrp cd
	 * @param type the type
	 * @return the auth2
	 * @throws HmbException the hmb exception
	 */
	/*
	public String getAuth2(String workAuth, String idcYn, String menuMgrpCd, String type) throws HmbException{
		StringBuffer result     = new StringBuffer() ;
		StringBuffer auth = new StringBuffer();
		String yn = "";

		if(idcYn.equals("Y")){ yn = "5";}
		else if(idcYn.equals("N")){ yn = "4"; }

		auth.append(workAuth);
		auth.replace(Integer.parseInt(menuMgrpCd)-1, Integer.parseInt(menuMgrpCd), yn);

		if(type.equals("OLD")){
			result.append(auth.substring(0, 50)) ;
		}

		return result.toString() ;
    }
	*/
	/**
	 * **********************************************************.
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	/** Dept Change Management                                   */
	/*************************************************************/

	/**
	 * Batch Process search - 화면 Batch Process result 에서 search 버튼 클릭 시 실행되는 메서드
	 * @param req
	 * @param res
	 * @param paramJson - 조회조건
	 * @return
	 */
	@RequestMapping(value="/hncis/system/doSearchGridToBatchProcessResult.do")
	public ModelAndView doSearchGridToBatchProcessResult(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{
		//조건 설정
		BatchProcess dto = (BatchProcess) getJsonToBean(paramJson, BatchProcess.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		//검색
 		int count = systemManager.getSelectCountToBatchProcessResult(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		//검색
 		list.setRows(systemManager.getSelectListToBatchProcessResult(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system batchManagement update.- 화면 Batch Mgmt. 에서 edit 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param batch the batch - 조건
	 * @param parameter the parameter - 조건
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doModifyToBatchManagement.do")
	public ModelAndView doModifyToBatchManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="batch", required=true) String batch,
			@RequestParam(value="parameter", required=true) String parameter) throws HncisException{

		//조건 설정
		List<BatchInfo> batchListDto = (List<BatchInfo>)getJsonToList(batch, BatchInfo.class);
		//조건 설정
		List<BatchInfo> parameterListDto = (List<BatchInfo>)getJsonToList(parameter, BatchInfo.class);
		//수정
		systemManager.updateBatchInfoToBatchManagement(batchListDto);
		//수정
		systemManager.updateParameterToBatchManagement(parameterListDto);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system batchManagement execute.- 화면 Batch Mgmt. 에서 excute 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doExecuteToBatchManagement.do")
	public ModelAndView doExecuteToBatchManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조건 설정
		List<BatchInfo> batchListDto = (List<BatchInfo>)getJsonToList(paramJson, BatchInfo.class);

		//배치 실행 후 수정
		systemManager.updateBatchInfoToBatchManagementByExecute(batchListDto);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("EXECUTE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Calendar Management search.
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 */

	@RequestMapping(value="/hncis/system/doSearchGridToCalendarManagement.do")
	public ModelAndView doSearchGridToCalendarManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGasckcalDto dto = (BgabGasckcalDto) getJsonToBean(paramJson, BgabGasckcalDto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizePopUp; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);

		CommonList list = new CommonList();
		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(systemManager.getSelectListToCalendarManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		return modelAndView;
	}

	/**
	 * Calendar Management Insert.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertToCalendarManagement.do")
	public ModelAndView doInsertToCalendarManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGasckcalDto> dto = (List<BgabGasckcalDto>)getJsonToList(paramJson, BgabGasckcalDto.class);


		CommonMessage message = new CommonMessage();

		int cnt = systemManager.insertToCalendarManagement(dto);

		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Calendar Management update.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HmbException the hmb exception
	 */
	/*
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doUpdateToCalendarManagement.do")
	public ModelAndView doUpdateToCalendarManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HmbException{

		List<BgabGasckcalDto> list = (List<BgabGasckcalDto>)getJsonToList(paramJson, BgabGasckcalDto.class);


		int cnt = systemManager.updateToCalendarManagement(list);

		CommonMessage message = new CommonMessage();
		message.setMessage(hmbMessageSource.getMessage("EDIT.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	*/

	/**
	 * My Approval search. - 내 결재 정보 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return ModelAndView
	 * @throws HncisException the hmb exception
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/system/doSearchGridToMyApproval.do")
	public ModelAndView doSearchGridToMyApproval(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		CommonApproval dto = (CommonApproval) getJsonToBean(paramJson, CommonApproval.class);

		dto.setRdcs_eeno(SessionInfo.getSess_empno(req));
		dto.setRdcs_dcd (SessionInfo.getSess_dept_code(req));

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizePopUp; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = systemManager.getSelectCountToMyApproval(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(systemManager.getSelectListToMyApproval(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Approve To My Approval By Approve. - 결재 approve 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doApproveToMyApprovalByApprove.do")
	public ModelAndView doApprovalOkToRequestByApprove(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException, SessionException{

		List<CommonApproval> approvalList = (List<CommonApproval>) getJsonToList(paramJson, CommonApproval.class);

		CommonApproval commonApproval= systemManager.doApproveToMyApproval(approvalList,req);

		CommonMessage message = new CommonMessage();
		if(commonApproval.getResult().equals("E")){
			message.setMessage(commonApproval.getMessage());
		}
		else{
			message.setMessage(HncisMessageSource.getMessage("APPROVE.0004"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Approve To My Approval By Approve. - 결재 approve 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doApproveToMyApprovalByConfirm.do")
	public ModelAndView doApproveToMyApprovalByConfirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="uParams" , required=true) String uParams) throws HncisException{

		List<BgabGascrm01Dto> approvalList = (List<BgabGascrm01Dto>) getJsonToList(uParams, BgabGascrm01Dto.class);

		CommonMessage message = roomsMealsManager.doApproveToMyConfirm(approvalList);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Approve To My Approval By Reject.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doApproveToMyApprovalByReject.do")
	public ModelAndView doApproveToMyApprovalByReject(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException, SessionException{

		List<CommonApproval> approvalList = (List<CommonApproval>) getJsonToList(paramJson, CommonApproval.class);

		CommonApproval commonApproval= systemManager.doApproveToMyApproval(approvalList,req);

		CommonMessage message = new CommonMessage();
		if(commonApproval.getResult().equals("E")){
			message.setMessage(commonApproval.getMessage());
		}
		else{
			message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * My Approval Depute combo.
	 *
	 * @param req the req
	 * @param res the res
	 * @param codknd the codknd
	 * @param depute_dept the depute_dept
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doSearchForDeptEeno.do")
	public ModelAndView doSearchForDeptEeno(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd,
			@RequestParam(value="depute_dept" , required=true) String depute_dept) throws Exception{


		String codeKnd[];

		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseArray  jsonArr = new JSONBaseArray();
		CommonCode code = new CommonCode();

		String comboVal				 = "";
		String comboName			 = "";
		String comboTable			 = "";
		String comboWhereCondition	 = "";
		String comboGroupBy			 = "";
		String comboorderBy			 = "";

		String []codeKnds = codknd.split(";");

		for (int i =0; i < codeKnds.length; i++){

			codeKnd = codeKnds[i].split(":");

			String sess_empno = SessionInfo.getSess_empno(req);
			String sess_dept = SessionInfo.getSess_dept_code(req);

			if(codeKnd[0].equals("depute_dept")){
				comboVal = "orga_c";
				comboName = "orga_e ||'('||orga_c||')'";
				comboTable = "bgab_gascz008" + SessionInfo.getSess_corp_cd(req);
				comboWhereCondition = "empno_org ='"+sess_empno+"'";
				comboGroupBy = "";
				comboorderBy = "orga_e";
			}
			else if(codeKnd[0].equals("depute_eeno")){
				comboVal = "xusr_empno";
				comboName = "xusr_name||'('||xusr_empno||')'";
				comboTable = "bgab_gascz002" + SessionInfo.getSess_corp_cd(req);
				comboWhereCondition = "xusr_dept_code='"+depute_dept+"' and xusr_empno !='"+sess_empno+"'";
				comboGroupBy = "";
				comboorderBy = "xusr_name";
			}


			code.setValue(comboVal);
			code.setName(comboName);
			code.setTableName(comboTable);
			code.setWhereCondition(comboWhereCondition);
			code.setGroupBy(comboGroupBy);
			code.setOrderBy(comboorderBy);
			code.setComboType(codeKnd.length > 1 ? codeKnd[1]:"N");
			code.setComboName(codeKnd[0]);

			jsonArr = commonManager.getDataCombo(code);

			jso.put(codeKnd[0], jsonArr);
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * Approve To My Approval Change. -위임 시 실행되는 메서드 , 현재 사용하지 않음
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doModifyToMyApprovalForDepute.do")
	public ModelAndView doModifyToMyApprovalForDepute(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascz008Dto vo = (BgabGascz008Dto) getJsonToBean(paramJson, BgabGascz008Dto.class);

		int cnt = systemManager.updateToMyApprovalForDepute(vo);

		CommonMessage message = new CommonMessage();
		if(cnt == 1){
			message.setMessage(HncisMessageSource.getMessage("EDIT.0000"));
		}
		else{
			message.setMessage(HncisMessageSource.getMessage("EDIT.0001"));
		}


		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Approve To My Approval Restore. -위임 복원 시 실행되는 메서드 , 현재 사용하지 않음
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doModifyToMyApprovalForDeputeByRestore.do")
	public ModelAndView doModifyToMyApprovalForDeputeByRestore(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		BgabGascz008Dto vo = (BgabGascz008Dto) getJsonToBean(paramJson, BgabGascz008Dto.class);

		int cnt = systemManager.updateToMyApprovalForDeputeByRestore(vo);

		CommonMessage message = new CommonMessage();
		if(cnt == 1){
			message.setMessage(HncisMessageSource.getMessage("RESTORE.0000"));
		}
		else{
			message.setMessage(HncisMessageSource.getMessage("RESTORE.0001"));
		}


		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * My Approval User Info.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/system/doSearchMyApprovalUserInfo.do")
	public ModelAndView doSearchMyApprovalUserInfo(HttpServletRequest req, HttpServletResponse res,
@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascz008Dto vo = (BgabGascz008Dto) getJsonToBean(paramJson, BgabGascz008Dto.class);
		vo.setEmpno_org(SessionInfo.getSess_empno(req));
		vo.setOrga_c(SessionInfo.getSess_dept_code(req));
		BgabGascz008Dto bgabGascz008Dto = new BgabGascz008Dto();

		BgabGascz008Dto rsReqDto = systemManager.getSelectToMyApprovalUserInfo(vo);



		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsReqDto == null){
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(bgabGascz008Dto).toString());
		}
		else{
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsReqDto).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do search dept code.
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridToReaderManagement")
	public ModelAndView doSearchDeptCode(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascz008Dto paramDto = (BgabGascz008Dto) getJsonToBean(paramJson, BgabGascz008Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = systemManager.getSelectCountReaderManagement(paramDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		paramDto.setStartRow(startRow);
		paramDto.setEndRow(endRow);
		list.setRows(systemManager.getSelectReaderManagement(paramDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Do update to reader management.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doUpdateToReaderManagement.do")
	public ModelAndView doUpdateToReaderManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascz008Dto> readerListDto = (List<BgabGascz008Dto>)getJsonToList(paramJson, BgabGascz008Dto.class);

		systemManager.updateReaderManagement(readerListDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do search code management. - Code Mgmt. 화면에서 search 버튼 클릭 시 실행되는 메서드
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws Exception the exception
	 */
	@RequestMapping(value="/hncis/system/doSearchCodeManagement.do")
	public ModelAndView doSearchCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascz005Dto dto = (BgabGascz005Dto) getJsonToBean(paramJson, BgabGascz005Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = systemManager.getSelectCountCodeManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(systemManager.getSelectCodeManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Do insert code management.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertCodeManagement.do")
	public ModelAndView doInsertCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException{

		List<BgabGascz005Dto> dtoIList = (List<BgabGascz005Dto>)getJsonToList(paramsI, BgabGascz005Dto.class);
		List<BgabGascz005Dto> dtoUList = (List<BgabGascz005Dto>)getJsonToList(paramsU, BgabGascz005Dto.class);

		systemManager.insertListToCodeManagement(dtoIList, dtoUList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do modify code management.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doModifyCodeManagement.do")
	public ModelAndView doModifyCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascz005Dto> dtoList = (List<BgabGascz005Dto>)getJsonToList(paramJson, BgabGascz005Dto.class);

		systemManager.updateListToCodeManagement(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do delete code management.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteCodeManagement.do")
	public ModelAndView doDeleteCodeManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascz005Dto> dtoList = (List<BgabGascz005Dto>)getJsonToList(paramJson, BgabGascz005Dto.class);

		systemManager.deleteListToCodeManagement(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system Background Image search - 등록된 이미지 파일 조회
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doSearchToBackgroundImageAll.do")
	public ModelAndView doSearchToBackgroundImageAll(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조회조건 설정
		BgabGascz005Dto vo = (BgabGascz005Dto) getJsonToBean(paramJson, BgabGascz005Dto.class);

		CommonList list;
		list = new CommonList();

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystemBackground; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow = currentPage * Integer.parseInt(pageSize);
		//검색
		int count = systemManager.getSelectCountBackgroundImageAll(vo);

		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		vo.setStartRow(startRow);
		vo.setEndRow(endRow);
		//검색
		list.setRows(systemManager.getSelectBackgroundImageAll(vo));

		//int cnt = systemManager.updateToBackgroundImage(vo);

		//CommonMessage message = new CommonMessage();
		//message.setMessage(HmbMessageSource.getMessage("SEARCH.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//조회한 데이터를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		//modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system Background Image save. - 업로드 한 이미지 저장.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doInsertToBackgroundImage.do")
	public ModelAndView doInsertToBackgroundImage(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조건 설정
		BgabGascz005Dto vo = (BgabGascz005Dto) getJsonToBean(paramJson, BgabGascz005Dto.class);

		int cnt = systemManager.updateToBackgroundImage(vo);

		//파일 업로드 및 db table에 파일명 등을 저장함.
		//int cnt = systemManager.insertToBackgroundImage(vo, req, res);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		return modelAndView;
	}

	/**
	 * system Background Image apply. - 선택한 이미지 파일을 메인 이미지로 적용함.
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doApplyToBackgroundImage.do")
	public ModelAndView doApplyToBackgroundImage(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조건 설정
		BgabGascz005Dto vo = (BgabGascz005Dto) getJsonToBean(paramJson, BgabGascz005Dto.class);

		//선택한 이미지 파일을 메인 이미지로 적용함.
		int cnt = systemManager.updateToBackgroundImageApply(vo);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("APPLY.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		return modelAndView;
	}

	/**
	 * system Background Image delete. - 선택한 이미지 삭제
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteToBackgroundImage.do")
	public ModelAndView doDeleteToBackgroundImage(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		//조건 설정
		BgabGascz005Dto vo = (BgabGascz005Dto) getJsonToBean(paramJson, BgabGascz005Dto.class);

		//선택한 이미지 파일을 삭제
		int cnt = systemManager.deleteToBackgroundImageApply(vo);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		return modelAndView;
	}

	@RequestMapping(value="/hncis/system/doSearchSwitchToBudgetCheck.do")
	public ModelAndView doSearchSwitchToBudgetCheck(HttpServletRequest req, HttpServletResponse res) throws HncisException{
		ModelAndView modelAndView = null;

		List<BgabGascz005Dto> budgetList = systemManager.getSelectSwitchToBudgetCheckList();

		JSONBaseVO     jso     = new JSONBaseVO();
 		JSONBaseVO     json    = new JSONBaseVO();
		JSONBaseArray  jsonArr = new JSONBaseArray();

		for(BgabGascz005Dto budget : budgetList){
			json = new JSONBaseVO();
			json.put("budget_code"  , budget.getXcod_code());
			json.put("budget_name"  , budget.getXcod_ename());
			json.put("budget_switch", budget.getXcod_hname());

			jsonArr.add(json);
		}

		jso.put("BUDGET", jsonArr);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(jso).toString());
		modelAndView.addObject("uiType", "ajax");

		System.out.println(JSONObject.fromObject(jso).toString());

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doSaveSwitchToBudgetCheck.do")
	public ModelAndView doSaveSwitchToBudgetCheck(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="params", required=true) String params) throws HncisException{

		List<BgabGascz005Dto> bgabGascz005Dto = (List<BgabGascz005Dto>) getJsonToList(params, BgabGascz005Dto.class);

		systemManager.saveSwitchToBudgetCheck(bgabGascz005Dto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do search chart to dash board.
	 *
	 * @param req the req
	 * @param res the res
	 * @param key_year the key_year
	 * @return the model and view
	 * @throws Exception the exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doSearchChartToDashBoard.do")
	public ModelAndView doSearchChartToDashBoard(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="key_year" , required=true) String key_year) throws Exception{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		DashBoard code = new DashBoard();
		code.setKey_year(key_year);

		List<DashBoard>codeList1 = systemManager.getSelectChartToDashBoardEm(code);

		for(DashBoard targetBean : codeList1){

			json = new JSONBaseVO();
			jsonArr = new JSONBaseArray();

			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM01()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM02()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM03()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM04()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM05()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM06()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM07()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM08()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM09()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM10()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM11()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM12()));
			jso.put(targetBean.getBiz_type(), jsonArr);
		}
		List<DashBoard>codeList2 = systemManager.getSelectChartToDashBoardBt(code);

		for(DashBoard targetBean : codeList2){

			json = new JSONBaseVO();
			jsonArr = new JSONBaseArray();

			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM01()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM02()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM03()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM04()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM05()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM06()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM07()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM08()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM09()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM10()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM11()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM12()));
			jso.put(targetBean.getBiz_type(), jsonArr);
		}
		List<DashBoard>codeList3 = systemManager.getSelectChartToDashBoardBv(code);

		for(DashBoard targetBean : codeList3){

			json = new JSONBaseVO();
			jsonArr = new JSONBaseArray();

			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM01()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM02()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM03()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM04()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM05()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM06()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM07()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM08()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM09()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM10()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM11()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM12()));
			jso.put(targetBean.getBiz_type(), jsonArr);
		}
		List<DashBoard>codeList4 = systemManager.getSelectChartToDashBoardPs(code);

		for(DashBoard targetBean : codeList4){

			json = new JSONBaseVO();
			jsonArr = new JSONBaseArray();

			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM01()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM02()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM03()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM04()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM05()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM06()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM07()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM08()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM09()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM10()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM11()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM12()));
			jso.put(targetBean.getBiz_type(), jsonArr);
		}


		jsonArr = new JSONBaseArray();

		jsonArr.add(HncisMessageSource.getMessage("SEARCH.0000"));

		jso.put("MSG",jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}
	/**
	 * Coordinator mgmt. search
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridToCoordiManagement.do")
	public ModelAndView doSearchGridToCoordiManagement(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascz003Dto paramDto = (BgabGascz003Dto) getJsonToBean(paramJson, BgabGascz003Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = systemManager.getSelectCountCoordiManagement(paramDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		paramDto.setStartRow(startRow);
		paramDto.setEndRow(endRow);
		list.setRows(systemManager.getSelectCoordiManagement(paramDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Coordinator mgmt. save
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doUpdateToCoordiManagement.do")
	public ModelAndView doUpdateToCoordiManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascz003Dto> list = (List<BgabGascz003Dto>)getJsonToList(paramJson, BgabGascz003Dto.class);

		CommonMessage message = systemManager.updateCoordiManagement(list,req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}


	/**
	 * Coordinator mgmt. search
	 *
	 * @param req the req
	 * @param res the res
	 * @param pageNumber the page number
	 * @param pageSize the page size
	 * @param paramJson the param json
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@RequestMapping(value="/hncis/system/doSearchGridToApproveLevelManagement.do")
	public ModelAndView doSearchGridToApproveLevelManagement(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascz013Dto paramDto = (BgabGascz013Dto) getJsonToBean(paramJson, BgabGascz013Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = systemManager.selectCountToApproveLevelManagement(paramDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		paramDto.setStartRow(startRow);
		paramDto.setEndRow(endRow);
		list.setRows(systemManager.selectListToApproveLevelManagement(paramDto));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system Background Image delete. - 선택한 이미지 삭제
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doSaveToApproveLevelManagement.do")
	public ModelAndView doSaveToApproveLevelManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{

		//조건 설정
		List<BgabGascz013Dto> iList = (List<BgabGascz013Dto>) getJsonToList(iParams, BgabGascz013Dto.class);
		List<BgabGascz013Dto> uList = (List<BgabGascz013Dto>) getJsonToList(uParams, BgabGascz013Dto.class);

		//선택한 이미지 파일을 삭제
		int cnt = systemManager.saveListToApproveLevelManagement(iList, uList);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		return modelAndView;
	}

	/**
	 * system Background Image delete. - 선택한 이미지 삭제
	 *
	 * @param req the req
	 * @param res the res
	 * @param paramJson the param json - 조건
	 * @return the model and view
	 * @throws HncisException the hmb exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteToApproveLevelManagement.do")
	public ModelAndView doDeleteToApproveLevelManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="dParams", required=true) String dParams) throws HncisException{

		//조건 설정
		List<BgabGascz013Dto> dList = (List<BgabGascz013Dto>) getJsonToList(dParams, BgabGascz013Dto.class);

		//선택한 이미지 파일을 삭제
		int cnt = systemManager.deleteListToApproveLevelManagement(dList);

		CommonMessage message = new CommonMessage();
		//화면의 하단 메시지 설정
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		//메시지를 string으로 해서 넣어줌.
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		return modelAndView;
	}

	/**
	 * Do search vendor management.
	 */
	@RequestMapping(value="/hncis/system/doSearchVendorManagement.do")
	public ModelAndView doSearchVendorManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascz014Dto dto = (BgabGascz014Dto) getJsonToBean(paramJson, BgabGascz014Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = systemManager.getSelectCountVendorManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(systemManager.getSelectVendorManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Do insert vendor management.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertVendorManagement.do")
	public ModelAndView doInsertVendorManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException{

		List<BgabGascz014Dto> dtoIList = (List<BgabGascz014Dto>)getJsonToList(paramsI, BgabGascz014Dto.class);
		List<BgabGascz014Dto> dtoUList = (List<BgabGascz014Dto>)getJsonToList(paramsU, BgabGascz014Dto.class);

		int cnt = systemManager.insertListToVendorManagement(dtoIList, dtoUList);

		CommonMessage message = new CommonMessage();
		if(cnt == 0){
			message.setMessage(HncisMessageSource.getMessage("SAVE.0002"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do delete vendor management.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteVendorManagement.do")
	public ModelAndView doDeleteVendorManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascz014Dto> dtoList = (List<BgabGascz014Dto>)getJsonToList(paramJson, BgabGascz014Dto.class);

		systemManager.deleteListToVendorManagement(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do search PurchaseOrder management.
	 */
	@RequestMapping(value="/hncis/system/doSearchPurchaseOrderManagement.do")
	public ModelAndView doSearchPurchaseOrderManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascz015Dto dto = (BgabGascz015Dto) getJsonToBean(paramJson, BgabGascz015Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = systemManager.getSelectCountPurchaseOrderManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(systemManager.getSelectPurchaseOrderManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Do insert PurchaseOrder management.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertPurchaseOrderManagement.do")
	public ModelAndView doInsertPurchaseOrderManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException{

		List<BgabGascz015Dto> dtoIList = (List<BgabGascz015Dto>)getJsonToList(paramsI, BgabGascz015Dto.class);
		List<BgabGascz015Dto> dtoUList = (List<BgabGascz015Dto>)getJsonToList(paramsU, BgabGascz015Dto.class);

		systemManager.insertListToPurchaseOrderManagement(dtoIList, dtoUList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do delete PurchaseOrder management.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeletePurchaseOrderManagement.do")
	public ModelAndView doDeletePurchaseOrderManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascz015Dto> dtoList = (List<BgabGascz015Dto>)getJsonToList(paramJson, BgabGascz015Dto.class);

		systemManager.deleteListToPurchaseOrderManagement(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/getPurchaseOrderCombo.do")
	public ModelAndView getPurchaseOrderCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws HncisException{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;

		CommonCode code = null;
		List<CommonCode> codeList = null;

		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");

			if( codePair.length > 1)
			{
				code = new CommonCode();
				jsonArr = new JSONBaseArray();

				code.setCodknd(codePair[1]);
				codeList = systemManager.getSelectPurchaseOrderCombo(code);

				if(codePair.length > 2){
					json = new JSONBaseVO();
					if(!codePair[2].equals("Z")){
						if(codePair[2].equals("S")){
							json.put("name", HncisMessageSource.getMessage("select"));
						}else{
							json.put("name", HncisMessageSource.getMessage("total"));
						}
						json.put("value", "");

						jsonArr.add(json);
					}
				}

				for(CommonCode targetBean : codeList){

					json = new JSONBaseVO();
					json.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
					json.put("value", StringUtil.isNullToStrTrm(targetBean.getValue()));

					jsonArr.add(json);
				}
				jso.put(codePair[0], jsonArr);
			}else{
				break;
			}
		}
		//System.out.println(jso.toString());
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}//end method

	/**
	 * system - restrant menu(date)
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/system/doSearchByDateMenu.do")
	public ModelAndView doSearchByDateMenu(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascz019Dto param = (BgabGascz019Dto) getJsonToBean(paramJson, BgabGascz019Dto.class);

		CommonList list = new CommonList();
		list.setRows(systemManager.doSearchByDateMenu(param));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system - restrant menu(brasil)
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/system/doSearchByMenu1.do")
	public ModelAndView doSearchByMenu1(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascz019Dto param = (BgabGascz019Dto) getJsonToBean(paramJson, BgabGascz019Dto.class);

		CommonList list = new CommonList();
		list.setRows(systemManager.doSearchByBrasilanMenu(param));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system - restrant menu(korea)
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/system/doSearchByMenu2.do")
	public ModelAndView doSearchByMenu2(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascz019Dto param = (BgabGascz019Dto) getJsonToBean(paramJson, BgabGascz019Dto.class);

		CommonList list = new CommonList();
		list.setRows(systemManager.doSearchByKoreanMenu(param));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system - restrant menu(coffee)
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 */
	@RequestMapping(value="/hncis/system/doSearchByCoffee.do")
	public ModelAndView doSearchByCoffee(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		ModelAndView modelAndView = null;
		BgabGascz019Dto param = (BgabGascz019Dto) getJsonToBean(paramJson, BgabGascz019Dto.class);

		CommonList list = new CommonList();
		list.setRows(systemManager.doSearchByCoffee(param));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * system - restrant menu(brasilan)
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertByMenu1.do")
	public ModelAndView doInsertByMenu1(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsG", required=false) String strDate,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascz019Dto> gsSaveVo = (List<BgabGascz019Dto>) getJsonToList(saveInfo, BgabGascz019Dto.class);
		List<BgabGascz019Dto> gsModifyVo = (List<BgabGascz019Dto>) getJsonToList(modifyInfo, BgabGascz019Dto.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setB_ptt_ymd(strDate);
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		systemManager.doInsertByBrasilanMenu(gsSaveVo, gsModifyVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system - restrant menu(korean)
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertByMenu2.do")
	public ModelAndView doInsertByMenu2(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsG", required=false) String strDate,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascz019Dto> gsSaveVo = (List<BgabGascz019Dto>) getJsonToList(saveInfo, BgabGascz019Dto.class);
		List<BgabGascz019Dto> gsModifyVo = (List<BgabGascz019Dto>) getJsonToList(modifyInfo, BgabGascz019Dto.class);

		for(int i=0; i<gsSaveVo.size(); i++){
			gsSaveVo.get(i).setK_ptt_ymd(strDate);
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		systemManager.doInsertByKoreanMenu(gsSaveVo, gsModifyVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system - restrant menu(korean)
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertByCoffee.do")
	public ModelAndView doInsertByCoffee(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsI", required=false) String saveInfo,
		@RequestParam(value="paramsU", required=false) String modifyInfo) throws HncisException, SessionException{

		List<BgabGascz019Dto> gsSaveVo = (List<BgabGascz019Dto>) getJsonToList(saveInfo, BgabGascz019Dto.class);
		List<BgabGascz019Dto> gsModifyVo = (List<BgabGascz019Dto>) getJsonToList(modifyInfo, BgabGascz019Dto.class);

		for(int i=0; i<gsSaveVo.size(); i++){
//			String docNo = StringUtil.getDocNo();
//			gsSaveVo.get(i).setDoc_no(docNo);
			gsSaveVo.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			gsSaveVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		for(int i=0; i<gsModifyVo.size(); i++){
			gsModifyVo.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}

		systemManager.doInsertByCoffee(gsSaveVo, gsModifyVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system - restrant menu(brasilan)
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteByMenu1.do")
	public ModelAndView doDeleteByMenu1(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascz019Dto> gsDelVo = (List<BgabGascz019Dto>) getJsonToList(paramJson, BgabGascz019Dto.class);

		systemManager.doDeleteByBrasilanMenu(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system - restrant menu(korean)
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteByMenu2.do")
	public ModelAndView doDeleteByMenu2(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascz019Dto> gsDelVo = (List<BgabGascz019Dto>) getJsonToList(paramJson, BgabGascz019Dto.class);

		systemManager.doDeleteByKoreanMenu(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * system - restrant menu(korean)
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteByCoffee.do")
	public ModelAndView doDeleteByCoffee(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException{

		List<BgabGascz019Dto> gsDelVo = (List<BgabGascz019Dto>) getJsonToList(paramJson, BgabGascz019Dto.class);

		systemManager.doDeleteByCoffee(gsDelVo);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/getVendorCodeCombo.do")
	public ModelAndView doSearchBTToMultiComboByReport(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="codknd" , required=true) String codknd) throws Exception{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		List<CommonCode> codeList = null;

		CommonCode code = new CommonCode();
		code.setCodknd(codknd);

		codeList = systemManager.getSelectVendorCodeCombo(code);
		jsonArr = new JSONBaseArray();

//		json = new JSONBaseVO();
//		json.put("key", "");
//		json.put("name", "");
//		json.put("value", "");
//
//		jsonArr.add(json);

		for(CommonCode targetBean : codeList){

			json = new JSONBaseVO();
			json.put("key", StringUtil.isNullToStrTrm(targetBean.getKey()));
			json.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
			json.put("value", StringUtil.isNullToStrTrm(targetBean.getCode()));

			jsonArr.add(json);
		}
		jso.put("vendor", jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * Do search Material management.
	 */
	@RequestMapping(value="/hncis/system/doSearchMaterialManagement.do")
	public ModelAndView doSearchMaterialManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascz016Dto dto = (BgabGascz016Dto) getJsonToBean(paramJson, BgabGascz016Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = systemManager.getSelectCountMaterialManagement(dto);

 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		dto.setStartRow(startRow);
 		dto.setEndRow(endRow);
 		list.setRows(systemManager.getSelectMaterialManagement(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Do insert Material management.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertMaterialManagement.do")
	public ModelAndView doInsertMaterialManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException{

		List<BgabGascz016Dto> dtoIList = (List<BgabGascz016Dto>)getJsonToList(paramsI, BgabGascz016Dto.class);
		List<BgabGascz016Dto> dtoUList = (List<BgabGascz016Dto>)getJsonToList(paramsU, BgabGascz016Dto.class);

		int cnt = systemManager.insertListToMaterialManagement(dtoIList, dtoUList);

		CommonMessage message = new CommonMessage();
		if(cnt == 0){
			message.setMessage(HncisMessageSource.getMessage("SAVE.0002"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do delete Material management.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteMaterialManagement.do")
	public ModelAndView doDeleteMaterialManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascz016Dto> dtoList = (List<BgabGascz016Dto>)getJsonToList(paramJson, BgabGascz016Dto.class);

		systemManager.deleteListToMaterialManagement(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * excel file upload
	 * @param fileInfo
	 * @throws HncisException, IOException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/popup/doCommonExcelUpload.do")
	public void doCommonExcelUpload(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException, SessionException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascZ011Dto bgabGascZ011Dto = (BgabGascZ011Dto)getJsonToBean(fileInfo, BgabGascZ011Dto.class);
			commonManager.saveExcelUpload(req, res, bgabGascZ011Dto);
		}
	}


	@RequestMapping(value="/hncis/system/doSearchToUserInfoPopup.do")
	public ModelAndView doSearchToUserInfoPopup(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

		BgabGascz002Dto userDto = (BgabGascz002Dto) getJsonToBean(paramJson, BgabGascz002Dto.class);
		BgabGascz002Dto userDetailInfo = systemManager.getSelectUserInfoDetailPopup(userDto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(userDetailInfo != null){
			userDetailInfo.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(userDetailInfo).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/system/doSearchXst30InfoList.do")
	public ModelAndView doSearchXst30InfoList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascz030Dto dto = (BgabGascz030Dto) getJsonToBean(paramJson, BgabGascz030Dto.class);
		dto.setLocale(req.getSession().getAttribute("reqLocale").toString());

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow = currentPage * Integer.parseInt(pageSize);
		int count = systemManager.selectXst30InfoListCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(systemManager.selectXst30InfoList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	@RequestMapping(value="/hncis/system/doSearchMenuInfo.do")
	public ModelAndView doSearchMenuInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascz030Dto dto = (BgabGascz030Dto) getJsonToBean(paramJson, BgabGascz030Dto.class);
		dto.setLocale(req.getSession().getAttribute("reqLocale").toString());

		BgabGascz030Dto rsDto = systemManager.selectMenuInfo(dto);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsDto != null){
			rsDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsDto).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doUpdateXst30IncomeCheck.do")
	public ModelAndView doUpdateXst30IncomeCheck(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="params", required=true) String params) throws HncisException{

		List<BgabGascz030Dto> dtoList = (List<BgabGascz030Dto>)getJsonToList(params, BgabGascz030Dto.class);

		CommonMessage message = systemManager.doUpdateXst30IncomeCheck(dtoList, req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doUpdateXst30Confirm.do")
	public ModelAndView doUpdateXst30Confirm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="params", required=true) String params) throws HncisException{

		List<BgabGascz030Dto> dtoList = (List<BgabGascz030Dto>)getJsonToList(params, BgabGascz030Dto.class);

		CommonMessage message = systemManager.doUpdateXst30Confirm(dtoList, req);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@RequestMapping(value="/hncis/system/doSearchGridToDeptInfoManagement.do")
	public ModelAndView doSearchGridToDeptInfoManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascz003Dto dto = (BgabGascz003Dto) getJsonToBean(paramJson, BgabGascz003Dto.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow = currentPage * Integer.parseInt(pageSize);
		int count = systemManager.selectGridToDeptInfoManagementCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(systemManager.selectGridToDeptInfoManagementList(dto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * Do insert Material management.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertToDeptInfoManagement.do")
	public ModelAndView doInsertToDeptInfoManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="iParams", required=true) String iParams,
			@RequestParam(value="uParams", required=true) String uParams) throws HncisException{

		List<BgabGascz003Dto> dtoIList = (List<BgabGascz003Dto>)getJsonToList(iParams, BgabGascz003Dto.class);
		List<BgabGascz003Dto> dtoUList = (List<BgabGascz003Dto>)getJsonToList(uParams, BgabGascz003Dto.class);

		int cnt = systemManager.insertListToDeptInfoManagement(dtoIList, dtoUList, req);

		CommonMessage message = new CommonMessage();
		if(cnt == 0){
			message.setMessage(HncisMessageSource.getMessage("SAVE.0002"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Do delete Material management.
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doDeleteToDeptInfoManagement.do")
	public ModelAndView doDeleteToDeptInfoManagement(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{

		List<BgabGascz003Dto> dtoList = (List<BgabGascz003Dto>)getJsonToList(paramJson, BgabGascz003Dto.class);

		systemManager.deleteListToDeptInfoManagement(dtoList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}





	@RequestMapping(value="/hncis/system/doSearchToLogoFile.do")
	public ModelAndView doSearchToLogoFile(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws HncisException{

		BgabGascz033Dto param = (BgabGascz033Dto) getJsonToBean(paramJson, BgabGascz033Dto.class);
		BgabGascz033Dto fileInfo = systemManager.getSelectLogoToFile(param);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(fileInfo != null){
			fileInfo.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(fileInfo).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * logo mngr file save
	 * @param fileInfo
	 * @throws HncisException, IOException
	 */
	@RequestMapping(value="/hncis/system/doSaveLogoToFile.do")
	public void doSaveLogoToFile(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileInfo", required=true) String fileInfo)throws HncisException, IOException{

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGascz033Dto bgabGascZ033Dto = (BgabGascz033Dto)getJsonToBean(fileInfo, BgabGascz033Dto.class);
			systemManager.saveLogoToFile(req, res, bgabGascZ033Dto);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doTemplateFileDown.do")
	public ModelAndView doFileDown(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		
		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName", dto.getOgc_fil_nm());
		mpfileData.put("fileName",   dto.getFil_nm());
		mpfileData.put("folderName",   "template");
		
		return new ModelAndView("download", "fileData", mpfileData);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/doShortCutFileDown.do")
	public ModelAndView doShortCutFileDown(HttpServletRequest req, HttpServletResponse res, 
		@RequestParam(value="fileInfo", required=true) String fileInfo) throws HncisException{

		BgabGascZ011Dto dto = (BgabGascZ011Dto) getJsonToBean(fileInfo, BgabGascZ011Dto.class);
		
		Map mpfileData = new HashMap();
		mpfileData.put("fileRealName", dto.getOgc_fil_nm());
		mpfileData.put("fileName",   dto.getFil_nm());
		mpfileData.put("folderName", "shortcut");
		
		return new ModelAndView("download", "fileData", mpfileData);
	}
	
	/**
	 * 라이선스 구매이력 저장
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/system/doInsertXst35Hist.do")
	public ModelAndView doSaveXst35Hist(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SQLException{

		BgabGascz035Dto inDto = (BgabGascz035Dto)getJsonToBean(paramJson, BgabGascz035Dto.class);
		inDto.setBuy_date(inDto.getBuy_date().replace("-", ""));
		
		int rs = systemManager.insertXst35Hist(inDto);

		CommonMessage message = new CommonMessage();
		if (rs > 0) {
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		} else {
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * 라이선스 구매이력 조회
	 */
	@RequestMapping(value="/hncis/system/doSearchXst35List.do")
	public ModelAndView doSearchXst35List(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascz035Dto dto = (BgabGascz035Dto) getJsonToBean(paramJson, BgabGascz035Dto.class);
		dto.setLocale(req.getSession().getAttribute("reqLocale").toString());

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow = currentPage * Integer.parseInt(pageSize);
		int count = systemManager.selectXst35ListCount(dto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		dto.setStartRow(startRow);
		dto.setEndRow(endRow);
		list.setRows(systemManager.doSearchXst35List(dto));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(count > 0){
			dto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0002"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}

		return modelAndView;
	}


	@Autowired
	@Qualifier("trainingManagerImpl")
	private TrainingManager trainingManager;
	@RequestMapping(value="/hncis/system/doSystemTest.do")
	public ModelAndView doSystemTest(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException{
		logger.info("Test Start");
		ModelAndView modelAndView = null;
		CommonMessage message = new CommonMessage();

		BgabGasctr01 cgabGasctr01 = (BgabGasctr01)getJsonToBean(bsicInfo, BgabGasctr01.class);


		String doc_no = StringUtil.getDocNo();
		cgabGasctr01.setDoc_no(doc_no);

		Integer cnt = (Integer)trainingManager.insertInfoTRToRequest(cgabGasctr01);

		if(cnt > 0){
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
			message.setCode(cgabGasctr01.getDoc_no());
			
			// BPM API호출
			String processCode = "P-B-005"; 	//프로세스 코드 (교육신청 프로세스) - 프로세스 정의서 참조
			String bizKey = cgabGasctr01.getDoc_no();	//신청서 번호
			String statusCode = "GASBZ01250010";	//액티비티 코드 (교육신청신청서작성) - 프로세스 정의서 참조
			String loginUserId = cgabGasctr01.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = "GASROLE01250030";  //교육신청 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add("10000001");

			BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
		
		}else{
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
		}

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		logger.info("Test End");
		
		return modelAndView;
	}
}