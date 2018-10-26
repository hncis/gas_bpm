package com.hncis.controller.smartRooms;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.PageNavigation;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.controller.AbstractController;
import com.hncis.smartRooms.manager.SmartRoomsManager;
import com.hncis.smartRooms.vo.BgabGascsm01;
import com.hncis.smartRooms.vo.BgabGascsm02;
import com.hncis.smartRooms.vo.BgabGascsm03;
import com.hncis.smartRooms.vo.BgabGascsm04;
import com.hncis.smartRooms.vo.BgabGascsm05;
import com.hncis.smartRooms.vo.BgabGascsm06;
import com.hncis.smartRooms.vo.BgabGascsm07;
import com.hncis.smartRooms.vo.BgabGascsmDto;
import com.hncis.smartRooms.vo.XsmCommonVo;
import com.ibatis.common.logging.Log;
import com.ibatis.common.logging.LogFactory;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("unchecked")
@Controller
public class SmartRoomsController extends AbstractController{
	@Autowired
	@Qualifier("smartRoomsManagerImpl")
	private SmartRoomsManager smartRoomsManager;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	/**
	 * 회의실 공통 콤보 
	 * @param req
	 * @param res
	 * @param codknd
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
 	@RequestMapping(value="/hncis/smartRooms/doSearchComboByXsm.do")
	public ModelAndView doSearchComboByXsm(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="codknd" , required=true) String codknd) throws HncisException, SessionException{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = new JSONBaseVO();
		JSONBaseArray  jsonArr = new JSONBaseArray();

		String []codeStr = codknd.split(";");
		String []codePair = null;
		List<BgabGascsm05> codeList = new ArrayList<BgabGascsm05>();
		for(int i = 0; i < codeStr.length; i++){
			codePair = codeStr[i].split(":");
			if(codePair.length > 1){
				BgabGascsm05 code = new BgabGascsm05();
				jsonArr = new  JSONBaseArray();
				code.setCorp_cd(SessionInfo.getSess_corp_cd(req));
				code.setXsm_knd(codePair[1]);
				if("CORM".equals(codePair[1])){
					codeList = smartRoomsManager.searchAuthCormComboByXsm10(code);					
				}else{
					codeList = smartRoomsManager.searchComboByXsm(code);
				}
				if(codePair.length > 2){
					json = new JSONBaseVO();
					/**
					 * Z없음, S선택, 그외전체
					 */
					if(!"Z".equals(codePair[2])){
						json.put("value", "");
							if(codePair[2].equals("S")) json.put("name", HncisMessageSource.getMessage("select"));
						else
							if(codePair[2].equals("A")) json.put("name", HncisMessageSource.getMessage("total"));
						else
							if(codePair[2].equals("B")) json.put("name", "");
						
						jsonArr.add(json);
					}
				}

				for(BgabGascsm05 targetBean : codeList){
					json = new JSONBaseVO();
					json.put("name", StringUtil.isNullToStrTrm(targetBean.getXsm_hname()));
					json.put("value", StringUtil.isNullToStrTrm(targetBean.getXsm_code()));
					jsonArr.add(json);
				}
				jso.put(codePair[0], jsonArr);
			}else{
				break;
			}
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}
 	
	/**
	 *
	 * 장기예약담당 체크
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doSearchLoginTimeUser.do")
	public ModelAndView doSearchLoginTimeUser(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "paramJson", required = false) String paramJson) throws HncisException, SessionException{
		HashMap<String, String> rsMap = new HashMap<String, String>();
		String sessEmpno = SessionInfo.getSess_empno(req);
		String corpCd = SessionInfo.getSess_corp_cd(req);
		String isLongTimeCharge = smartRoomsManager.searchLoginTimeUser(sessEmpno, corpCd);
		
		rsMap.put("isEtcUser", isLongTimeCharge);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("data/data.json");
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject("data", JSONObject.fromObject(rsMap).toString());
		
		return modelAndView;
	}
 	
	/**
	 *
	 * 회의실 예약 선점입력
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doInsertConferenceRoom.do")
	public ModelAndView doInsertConferenceRoom(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "paramJson", required = false) String paramJson) throws HncisException, SessionException{
		
		ModelAndView modelAndView = new ModelAndView();
		BgabGascsm01 sm01Vo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		sm01Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		HashMap<String, String> rsMap = new HashMap<String, String>();
		Boolean isTrue = true;
		
		try{
			//정책검사
			sm01Vo.setCode5("reservUpdate");
			rsMap = reservePolicy(sm01Vo, req);
			isTrue = Boolean.valueOf(rsMap.get("isTrue"));
			if(isTrue){
				String docNo = StringUtil.getMakeDocNo();
				sm01Vo.setIpe_eeno(SessionInfo.getSess_empno(req));
				sm01Vo.setUpdr_eeno(SessionInfo.getSess_empno(req));
				sm01Vo.setDoc_no(docNo);
				sm01Vo.setCnf_dept_code(SessionInfo.getSess_dept_code(req));
				smartRoomsManager.insertConferenceRoom1(sm01Vo);
				smartRoomsManager.insertConferenceRoom2(sm01Vo);
				
				rsMap.put("doc_no", docNo);
				rsMap.put("result", "Y");
				rsMap.put("message", "예약선점 완료");
			}
			
			modelAndView.setViewName("data/data.json");
			modelAndView.addObject("uiType", "ajax");
			modelAndView.addObject("data", JSONObject.fromObject(rsMap).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	/**
	 *
	 * 회의실 예약 최종입력
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doUpdateConferenceRoom.do")
	public ModelAndView doUpdateConferenceRoom(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "keyNo", required=false) String keyNo,
			@RequestParam(value = "paramJson", required=true) String paramJson) throws HncisException, SessionException{
		
		ModelAndView modelAndView = new ModelAndView();
		BgabGascsm01 sm01Vo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		sm01Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		sm01Vo.setIpe_eeno(SessionInfo.getSess_empno(req));
		sm01Vo.setUpdr_eeno(SessionInfo.getSess_empno(req));
		sm01Vo.setCode1(keyNo);
		
		HashMap<String, String> rsMap = new HashMap<String, String>();
		Boolean isTrue = true;
		
		try{
			BgabGascsm01 rtnVo = new BgabGascsm01();
			//정책검사
			sm01Vo.setCode5("confrimUpdate");
			rsMap = reservePolicy(sm01Vo, req);
			isTrue = Boolean.valueOf(rsMap.get("isTrue"));
			if(isTrue){
				rtnVo = smartRoomsManager.updateConferenceRoom(req, sm01Vo);
	 	    	if("D".equals(rtnVo.getCode1())){
	 	    		if("0".equals( sm01Vo.getPgs_st_cd())){
	 	    			smartRoomsManager.deleteConferenceRoom2(sm01Vo);
	 	    			smartRoomsManager.deleteConferenceRoom1(sm01Vo);
	 	    		}
	 	    	}
	 	    }else{
	 	    	rtnVo.setCode1(rsMap.get("result"));
	 	    	rtnVo.setMessage(rsMap.get("message"));
	 	    }
			
		    modelAndView.setViewName("data/data.json");
		    modelAndView.addObject("uiType", "ajax");
		    modelAndView.addObject("data", JSONObject.fromObject(rtnVo).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	    return modelAndView;
	}
	
    /**
     *
     * 회의실 예약 삭제
     *
     * @param req
     * @param res
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/hncis/smartRooms/doDeleteConferenceRoom.do")
	public ModelAndView deleteConferenceRoom(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "keyNo", required=false) String keyNo,
			@RequestParam(value = "paramJson", required=true) String paramJson) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		BgabGascsm01 sm01Vo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		sm01Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		BgabGascsm01 rtnVo = new BgabGascsm01();
		
		try{
			System.out.println("--------- delete conferenceRoom ----------");
			System.out.println("docNo : " + sm01Vo.getDoc_no());
			System.out.println("keyNo : " + StringUtil.isNullToString(keyNo));
			
			if("".equals(StringUtil.isNullToString(sm01Vo.getDoc_no()))){
				if(!"".equals(StringUtil.isNullToString(keyNo))){	
					HashMap<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("key_no", keyNo.split("-")[0]);
					paramMap.put("cnf_ymd", keyNo.split("-")[1]);
					paramMap.put("cnf_time", keyNo.split("-")[2]);
					
					BgabGascsmDto xsmDto = smartRoomsManager.searchXsmByDocNo(paramMap);
					sm01Vo.setDoc_no(xsmDto.getDoc_no());
					sm01Vo.setSeq_no(xsmDto.getSeq_no());
				}
			}
			smartRoomsManager.deleteConferenceRoom2(sm01Vo);
			smartRoomsManager.deleteConferenceRoom1(sm01Vo);

			modelAndView.setViewName("data/data.json");
		    modelAndView.addObject("uiType", "ajax");
		    modelAndView.addObject("data", JSONObject.fromObject(rtnVo).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	    return modelAndView;
	} 
	
	/**
	 * 정책검사
	 * @param paramVo
	 * @param req 
	 * @return
	 * @throws Exception 
	 */
	private HashMap<String, String> reservePolicy(BgabGascsm01 paramVo, HttpServletRequest req) throws HncisException, SessionException {
		HashMap<String, Object> policyChk = new HashMap<String, Object>();
		HashMap<String, String> rsMap = new HashMap<String, String>();
		Boolean isTrue = true;
		
		try{
			//날짜 시간 체크
			long diffCnt1 = CurrentDateTime.diffOfDate(CurrentDateTime.getDate(), paramVo.getCnf_from_ymd());
			long diffCnt2 = CurrentDateTime.diffOfDate(CurrentDateTime.getDate(), paramVo.getCnf_to_ymd());
			long diffFromDate = Long.parseLong(paramVo.getCnf_from_ymd()+paramVo.getCnf_from_time());
			long diffToDate = Long.parseLong(paramVo.getCnf_to_ymd()+paramVo.getCnf_to_time());
			
			if(diffFromDate > diffToDate || diffCnt1 < 0 || diffCnt2 < 0){
				isTrue = false;
				rsMap.put("isTrue", "false");
				rsMap.put("result", "N");
				rsMap.put("message", "지난 일정은 예약 할 수 없습니다.");
			}
			   
			//마스터,담당자 체크(정책은 모든 사용자가 동일하게 적용)
			String corpCd = SessionInfo.getSess_corp_cd(req);
			String isMaster = SessionInfo.getSess_mstu_gubb(req);
			//예외사용자
			if(!"M".equals(isMaster)){
				String sessEmpno = SessionInfo.getSess_empno(req);
				String isLognTime = smartRoomsManager.searchLoginTimeUser(sessEmpno, corpCd);
				if("N".equals(isLognTime)){
					//회의실 정책 체크
					if(isTrue){
						HashMap<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("corp_cd", corpCd);
						paramMap.put("regn_cd", paramVo.getRegn_cd());
						paramMap.put("bd_cd", paramVo.getBd_cd());
						paramMap.put("corm_fl_cd", paramVo.getCorm_fl_cd());
						paramMap.put("corm_cd", paramVo.getCorm_cd());
						policyChk = smartRoomsManager.searchConferencePolicy(paramMap);
						int curTime = Integer.parseInt(CurrentDateTime.getTime().substring(0, 4));
						int cnfFromTime = Integer.parseInt(paramVo.getCnf_from_time(), 10);
						int cnfToTime = Integer.parseInt(paramVo.getCnf_to_time(), 10);
						int resFromTime = Integer.parseInt(StringUtil.isNullToString((String)policyChk.get("RES_FROM_TIME"),"0"), 10);
						int resToTime = Integer.parseInt(StringUtil.isNullToString((String)policyChk.get("RES_TO_TIME"),"0"), 10);
						int useFromTime = Integer.parseInt(StringUtil.isNullToString((String)policyChk.get("USE_FROM_TIME"),"0"), 10);
						int useToTime = Integer.parseInt(StringUtil.isNullToString((String)policyChk.get("USE_TO_TIME"),"0"), 10);
						String strLmtCnt = String.valueOf(policyChk.get("RES_LMT_CNT"));
						int resLmtCnt = Integer.parseInt(strLmtCnt);
						
						//정책이 입력이 안되어 있으면 정책을 안쓴다는 의미.
						boolean isTime = resFromTime == 0 || resToTime == 0;
						if(!isTime){
							//예약 선점시 체크를 하는데.. 예약완료시점에서는 체크를 할 필요는 없음.
							if(!"confrimUpdate".equals(StringUtil.isNullToString(paramVo.getCode5()))){
								if(isTrue){
									//예약가능시간 체크
									if(curTime < resFromTime){
										isTrue = false;
										rsMap.put("isTrue", "false");
										rsMap.put("result", "N");
										rsMap.put("message", "회의실 예약가능 시간이 아닙니다.");
							    	}else if(curTime > resToTime){
							    		rsMap.put("isTrue", "false");
							    		rsMap.put("result", "N");
							    		rsMap.put("message", "회의실 예약가능 시간이 지났습니다.");
							    	}
								}
							}
						}
						
						isTime = useFromTime == 0 || useToTime == 0;
						if(!isTime){
							//예약 선점시 체크를 하는데.. 예약완료시점에서는 체크를 할 필요는 없음.
							if(!"confrimUpdate".equals(StringUtil.isNullToString(paramVo.getCode5()))){
								if(isTrue){
									if(cnfFromTime < useFromTime || cnfToTime > useToTime){
										isTrue = false;
										rsMap.put("isTrue", "false");
										rsMap.put("result", "N");
										rsMap.put("message", "회의실 사용가능 시간이 아닙니다.");
									}
								}
							}
						}
						    
						isTime = resLmtCnt == 0;
						if(!isTime){
							//예약 선점시 체크를 하는데.. 예약완료시점에서는 체크를 할 필요는 없음.
							if(!"confrimUpdate".equals(StringUtil.isNullToString(paramVo.getCode5()))){
								//팀 당일 회의예약여부 건수 확인(사용일자(cnf_from_ymd) 기준으로 조회)
								if(isTrue){
									BgabGascsm01 teamCntVo = new BgabGascsm01();
									teamCntVo.setCnf_from_ymd(paramVo.getCnf_from_ymd());
									teamCntVo.setCnf_dept_code(SessionInfo.getSess_dept_code(req));
									int teamReservCnt = smartRoomsManager.searchTodayReservCnt(teamCntVo);
									if(teamReservCnt >= resLmtCnt){
										isTrue = false;
										rsMap.put("isTrue", "false");
										rsMap.put("result", "N");
										rsMap.put("message", "해당 팀은 해당 회의실에 당일 예약건수를 초과 하였습니다.");
									}
								}
							}
						}
						
						//최대예약시간 체크
						if(isTrue){
							long diffDate = CurrentDateTime.diffOfDate(paramVo.getCnf_from_ymd(), paramVo.getCnf_to_ymd());
							if(diffDate == 0){
								String diffFromTime = paramVo.getCnf_from_ymd()+paramVo.getCnf_from_time()+"00";
								String diffToTime = paramVo.getCnf_to_ymd()+paramVo.getCnf_to_time()+"59";
								SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
								Date beginDate = formatter.parse(diffFromTime);
								Date endDate = formatter.parse(diffToTime);
								long mills = endDate.getTime() - beginDate.getTime();
								long diffMin = (mills/(1000*60));    //분으로 변환
								long hours = (mills/(1000*60)+1)/60; //시간으로 변환
								long mins = (mills/(1000*60)+1)%60;  //분으로 환산
								String koTimeNm = "";
								if(mins > 0){
									koTimeNm = ""+ hours + "시간" + " "+ mins + "분";
								}else{
									koTimeNm = ""+ hours + "시간";
								}
								
								if(!"000".equals(StringUtil.isNullToString((String)policyChk.get("RES_MAX_MIN"), "000"))){
									String resMaxMinName = StringUtil.isNullToString((String)policyChk.get("RES_MAX_MIN_NAME"));
									long resMaxTime = Long.parseLong(StringUtil.isNullToString((String)policyChk.get("RES_MAX_MIN"), "000"));
									if(resMaxTime < diffMin){
										isTrue = false;
										rsMap.put("isTrue", "false");
										rsMap.put("result", "N");
										rsMap.put("message", "입력된 시간이("+koTimeNm+") \n설정된 예약 가능한 시간("+resMaxMinName+")보다 큽니다.");
									}
								}
							}
						}
						
						if(isTrue){
							rsMap.put("isTrue", "true");
							rsMap.put("result", "Y");	
						}
					}
				}else{
					rsMap.put("isTrue", "true");
					rsMap.put("result", "Y");				   
				}
			}else{
				rsMap.put("isTrue", "true");
				rsMap.put("result", "Y");
			}
		}catch(Exception e){
			rsMap.put("isTrue", "false");
			rsMap.put("result", "N");
			rsMap.put("message", "회의실 정책 체크 중 문제가 발생 하였습니다.");
			e.printStackTrace();
		}
		
		return rsMap;
	}
	
	/**
	 *
	 * 회의실 빠른예약 조회
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doSearchFastReservCormList.do")
	public ModelAndView doSearchFastReservCormList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "paramJson", required = false) String paramJson) throws Exception {
			
		BgabGascsm01 paramVo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		paramVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		paramVo.setDept_cd(SessionInfo.getSess_dept_code(req));
			
		/**
		 * 페이지 컨트롤 셋팅
		 */
		PageNavigation pn = new PageNavigation(12,10);
		String curPage = paramVo.getCur_page().equals("") ? "1" : paramVo.getCur_page();
		paramVo.setCur_page(curPage);
		paramVo.setPage_size(pn.getPageSize());
		paramVo.setDept_cd(SessionInfo.getSess_dept_code(req));
		
		int totalCount = Integer.parseInt(smartRoomsManager.searchFastReservCormTotalCount(paramVo));
		List<BgabGascsm01> rtnList = smartRoomsManager.searchFastReservCormList(paramVo);
		
		int pageNum = 0;
		if(curPage == null){
			pn.setCurPage(1);
			pn.init(totalCount);
		}else{
			pageNum = Integer.parseInt(curPage);
			pn.setCurPage(pageNum);
			pn.init(totalCount);
		}
		
		BgabGascsm01 list = new BgabGascsm01();
		list.setBlockStart(pn.getBlockStart());
		list.setBlockEnd(pn.getBlockEnd());
		list.setPrePageExists(pn.isPrePageExists());
		list.setNextPageExists(pn.isNextPageExists());
		list.setNumOfPage(pn.getNumOfPage());
		list.setList(rtnList);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("data/data.json");
		return modelAndView.addObject("data", JSONObject.fromObject(list).toString());
	}
	
	/**
	 *
	 * 회의실 예약 조회
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doSearchConferenceRoomListByXsm02.do")
	public ModelAndView doSearchConferenceRoomListByXsm02(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "paramJson", required=true) String paramJson) throws HncisException, SessionException {
		BgabGascsm02 paramVo = (BgabGascsm02) getJsonToBean(paramJson, BgabGascsm02.class);
		ModelAndView modelAndView = new ModelAndView();
		
		/**
		 * 페이지 컨트롤 셋팅
		 */
		PageNavigation pn = new PageNavigation(30,10);
		String curPage = paramVo.getCur_page().equals("") ? "1" : paramVo.getCur_page();
		paramVo.setCur_page(curPage);
		paramVo.setPage_size(pn.getPageSize());
		paramVo.setIpe_eeno(SessionInfo.getSess_empno(req));
		paramVo.setCnf_eeno(SessionInfo.getSess_empno(req));
		paramVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		List<BgabGascsm02> rtnList = smartRoomsManager.selectConferenceRoomList1(paramVo);
		int totalCount = Integer.parseInt(smartRoomsManager.selectConferenceRoomTotalCount(paramVo));
		List<BgabGascsm02> rtnList1 = smartRoomsManager.selectConferenceRoomList2(paramVo);
		
		int pageNum = 0;
		if(curPage == null){
			pn.setCurPage(1);
			pn.init(totalCount);
		}else{
			pageNum = Integer.parseInt(curPage);
			pn.setCurPage(pageNum);
			pn.init(totalCount);
		}
		
		BgabGascsm02 list = new BgabGascsm02();
		list.setBlockStart(pn.getBlockStart());
		list.setBlockEnd(pn.getBlockEnd());
		list.setPrePageExists(pn.isPrePageExists());
		list.setNextPageExists(pn.isNextPageExists());
		list.setNumOfPage(pn.getNumOfPage());
		list.setList(rtnList);
		list.setList1(rtnList1);
		
		modelAndView.setViewName("data/data.json");
		modelAndView.addObject("data", JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}
	
	/**
	 *
	 * 회의실 주간,월간 조회
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doSearchReservationList.do")
	public ModelAndView doSearchReservationList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "paramJson", required = false) String paramJson) throws Exception {
		
		BgabGascsm01 paramVo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		paramVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		List<BgabGascsmDto> rsvMergeList = new ArrayList<BgabGascsmDto>();
		List<BgabGascsmDto> rsvList = new ArrayList<BgabGascsmDto>();

		BgabGascsm01 list = new BgabGascsm01();
		if("W".equals(paramVo.getMode())){
			String gubun = paramVo.getCode1();
			list.setCode1(paramVo.getCnf_from_ymd());
			rsvList = smartRoomsManager.searchReservationList(paramVo);
		}else if("M".equals(paramVo.getMode())){
			rsvList = smartRoomsManager.searchReservationList(paramVo);
		}

		for(BgabGascsmDto rsVo : rsvList){
			String fromTime = rsVo.getCnf_from_time().substring(0,2)+":"+rsVo.getCnf_from_time().substring(2,4);
			String toTime   = rsVo.getCnf_to_time().substring(0,2)+":"+rsVo.getCnf_to_time().substring(2,4);

			//오늘일자 기준으로 ToYmd까지의 일수(지난일정구분)
			long schGab     = CurrentDateTime.diffOfDate(CurrentDateTime.getDate(), rsVo.getCnf_to_ymd().replaceAll("/", ""));

			//오늘일자 기준으로 InpYmd의 일수(신규일정구분)
			long newContGab = CurrentDateTime.diffOfDate(rsVo.getPtt_ymd(), CurrentDateTime.getDate());
			String cnfEeno = rsVo.getCnf_eeno();
			String sessEeno = SessionInfo.getSess_empno(req);
			
			StringBuffer sb = new StringBuffer();
			sb.append(fromTime + " ~ " + toTime);

			rsVo.setTitle(sb.toString());
			rsVo.setStart(rsVo.getCnf_from_ymd().replaceAll("/", "-")+"T"+fromTime+":00");
			rsVo.setEnd(rsVo.getCnf_to_ymd().replaceAll("/", "-")+"T"+toTime+":00");
			
			//본인이면 파란 파스텔톤, 타인이면 노란 파스텔톤
			if(sessEeno.equals(cnfEeno)){
				rsVo.setMode("1");
				rsVo.setClassName("agendBord1");
				rsVo.setColor("#C1D82E");
				rsVo.setTextColor("#000");
			}else{
				rsVo.setMode("2");
				rsVo.setClassName("agendBord2");
				if("1".equals(rsVo.getRow_idx())){
					rsVo.setColor("#68C8C6");
					rsVo.setTextColor("#000");
				}else if("2".equals(rsVo.getRow_idx())){
					rsVo.setColor("#007A85");
					rsVo.setTextColor("#FFF");
				}else{
					rsVo.setColor("#7EB0CB");
					rsVo.setTextColor("#000");
				}
			}
			
			rsvMergeList.add(rsVo);
		}

		list.setList1(rsvMergeList);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("data/data.json");
		modelAndView.addObject("data", JSONObject.fromObject(list).toString());
		
		return modelAndView;
	}
	
	/**
	 *
	 * 회의실 정보 조회
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doSearchConferenceInfo.do")
	public ModelAndView doSearchConferenceInfo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "viewType", required=false) String viewType,
			@RequestParam(value = "keyNo", required=false) String keyNo,
			@RequestParam(value = "docNo", required=false) String docNo,
			@RequestParam(value = "paramJson", required=true) String paramJson) throws HncisException, SessionException {
		
		ModelAndView modelAndView = new ModelAndView();
		HashMap<String, Object> rsMap = new HashMap<String, Object>();
		BgabGascsm01 sm01Vo = new BgabGascsm01();
		BgabGascsm01 rtn01 = new BgabGascsm01();
		sm01Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		try{
			//조회모드
			if("S".equals(viewType)){
				if("".equals(StringUtil.isNullToString(docNo))){
					if(!"".equals(StringUtil.isNullToString(keyNo))){
						HashMap<String, String> paramMap = new HashMap<String, String>();
						paramMap.put("key_no", keyNo.split("-")[0]);
						paramMap.put("cnf_ymd", keyNo.split("-")[1]);
						paramMap.put("cnf_time", keyNo.split("-")[2]);
						BgabGascsmDto xsmDto = new BgabGascsmDto();
						xsmDto = smartRoomsManager.searchXsmByDocNo(paramMap);
						if(xsmDto != null){
							sm01Vo.setDoc_no(xsmDto.getDoc_no());
							sm01Vo.setSeq_no(xsmDto.getSeq_no());
						}
					}
				}else{
					sm01Vo.setDoc_no(docNo);
				}
				rtn01 = smartRoomsManager.searchConferenceReservInfo(sm01Vo);
				if(rtn01 == null){
					rtn01 = new BgabGascsm01();
				}
				rsMap.put("info2", rtn01);
			}
			
			BgabGascsm04 sm04Vo = (BgabGascsm04) getJsonToBean(paramJson, BgabGascsm04.class);
			sm04Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			BgabGascsm04 rtn04 = new BgabGascsm04();
			rtn04 = smartRoomsManager.searchConferenceInfo(sm04Vo);
			if(rtn04 == null){
				rtn04 = new BgabGascsm04();
			}else{
				String orgFile1 = StringUtil.isNullToString(rtn04.getOrg_file_1());
				String orgFile2 = StringUtil.isNullToString(rtn04.getOrg_file_2());
				String img1 = StringUtil.isNullToString(rtn04.getFile_img_1());
				String img2 = StringUtil.isNullToString(rtn04.getFile_img_2());
				String url = "http://localhost:8080/gasc/upload/xs/";
				
				if(!"".equals(img1)){
					rtn04.setFile_img_1(url+img1);
					rtn04.setOrg_file_1(orgFile1);
				}
				if(!"".equals(img2)){
					rtn04.setFile_img_2(url+img2);
					rtn04.setOrg_file_2(orgFile2);
				}
			}
			rsMap.put("info1", rtn04);
			
			modelAndView.setViewName("data/data.json");
			modelAndView.addObject("uiType", "ajax");
			modelAndView.addObject("data", JSONObject.fromObject(rsMap).toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return modelAndView;
	}
	
	/**
	 *
	 * 회의실 나의 예약조회
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doSearchMyReservationList.do")
	public ModelAndView doSearchMyReservationList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "paramJson", required = false) String paramJson) throws HncisException, SessionException {
		
		String sessEmpno = SessionInfo.getSess_empno(req);
		BgabGascsm01 paramVo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		paramVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		paramVo.setEeno(sessEmpno);
		
		/**
		 * 페이지 컨트롤 셋팅
		 */
		PageNavigation pn = new PageNavigation(15,10);
		String curPage = paramVo.getCur_page().equals("") ? "1" : paramVo.getCur_page();
		paramVo.setCur_page(curPage);
		paramVo.setPage_size(pn.getPageSize());
		
		int totalCount = Integer.parseInt(smartRoomsManager.searchMyReservationTotalCount(paramVo));
		List<BgabGascsm01> rtnList1 = smartRoomsManager.searchMyReservationList(paramVo);
		
		int pageNum = 0;
		if(curPage == null){
			pn.setCurPage(1);
			pn.init(totalCount);
       }else{
    	   pageNum = Integer.parseInt(curPage);
    	   pn.setCurPage(pageNum);
    	   pn.init(totalCount);
       }
		
		List<BgabGascsm01> finalRsList = new ArrayList<BgabGascsm01>();
		for(BgabGascsm01 trVo : rtnList1){
			String cnfEeno = StringUtil.isNullToString(trVo.getCnf_eeno());
			String inpEeno = StringUtil.isNullToString(trVo.getInp_eeno());
			String cnfAttdeEeno = StringUtil.isNullToString(trVo.getCnf_attde_eeno());
			
			//참석자구분
			if(sessEmpno.equals(cnfEeno)){
				trVo.setGubun("회의주관자");
			}else if(sessEmpno.equals(inpEeno)){
				trVo.setGubun("신청자");
			}else if(!"".equals(cnfAttdeEeno)){
				if(cnfAttdeEeno.contains(sessEmpno)){
					trVo.setGubun("참석자");
				}
			}
			//회의상태
			if("1".equals(trVo.getCnf_st_cd())){
				trVo.setCnf_st_nm("회의예정");
			}else if("2".equals(trVo.getCnf_st_cd())){
				trVo.setCnf_st_nm("회의종료");
			}else if("5".equals(trVo.getCnf_st_cd())){
				trVo.setCnf_st_nm("회의중");
			}else{
				trVo.setCnf_st_nm("");
			}
			finalRsList.add(trVo);
		}
		
		BgabGascsm01 list = new BgabGascsm01();
		list.setBlockStart(pn.getBlockStart());
		list.setBlockEnd(pn.getBlockEnd());
		list.setPrePageExists(pn.isPrePageExists());
		list.setNextPageExists(pn.isNextPageExists());
		list.setNumOfPage(pn.getNumOfPage());
  	    list.setList(finalRsList);

  	    ModelAndView modelAndView = new ModelAndView();
  	    modelAndView.setViewName("data/data.json");
  	    modelAndView.addObject("data", JSONObject.fromObject(list).toString());
  	    
  	    return modelAndView;
	}
	
	/**
	 * 나의 예약현황 엑셀
	 * @param req
	 * @param res
	 * @param fileName
	 * @param header
	 * @param headerName
	 * @param fomatter
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="/hncis/smartRooms/doSearchMyReservationExcel.excel")
	public ModelAndView doSearchMyReservationExcel(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		
		
		BgabGascsm01 smParamVo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		String sessEmpno = SessionInfo.getSess_empno(req);
		
		CommonList list = new CommonList();
		List<BgabGascsm01> rtnList = smartRoomsManager.searchMyReservationExcel(smParamVo);
		List<BgabGascsm01> finalRsList = new ArrayList<BgabGascsm01>();
		for(BgabGascsm01 trVo : rtnList){
			String cnfEeno = StringUtil.isNullToString(trVo.getCnf_eeno());
			String inpEeno = StringUtil.isNullToString(trVo.getInp_eeno());
			String cnfAttdeEeno = StringUtil.isNullToString(trVo.getCnf_attde_eeno());
			trVo.setCorm_nm(trVo.getRegn_nm()+"/"+trVo.getBd_nm()+"/"+trVo.getCorm_fl_cd()+"/"+trVo.getCorm_nm());
			
			//참석자구분
			if(sessEmpno.equals(cnfEeno)){
				trVo.setGubun("회의주관자");
			}else if(sessEmpno.equals(inpEeno)){
				trVo.setGubun("신청자");
			}else if(!"".equals(cnfAttdeEeno)){
				if(cnfAttdeEeno.contains(sessEmpno)){
					trVo.setGubun("참석자");
				}
			}
			
			if("4".equals(trVo.getCnf_st_cd())){
				trVo.setCnf_st_nm("");
			}else{
				if("1".equals(trVo.getCnf_st_cd())){
					trVo.setCnf_st_nm("회의예정");
				}else if("2".equals(trVo.getCnf_st_cd())){
					trVo.setCnf_st_nm("회의종료");
				}else if("5".equals(trVo.getCnf_st_cd())){
					trVo.setCnf_st_nm("회의중");
				}else{
					trVo.setCnf_st_nm("");
				}
			}
			finalRsList.add(trVo);
		}
		list.setRows(finalRsList);
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
	
	/**
	 *
	 * 나의 회의실 예약 삭제
	 *
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doDeleteMyReservationList.do")
   public ModelAndView doDeleteMyReservationList(HttpServletRequest req, HttpServletResponse res,
		   @RequestParam(value = "paramJson", required=true) String paramJson) throws Exception {

	   List<BgabGascsm01> paramList = (List<BgabGascsm01>) getJsonToList(paramJson, BgabGascsm01.class);
	   XsmCommonVo message = new XsmCommonVo();

	   String sessEmpno = SessionInfo.getSess_empno(req);
	   boolean stFlag = true;
	   for(BgabGascsm01 xsm01Vo : paramList){
		   xsm01Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		   List<BgabGascsm01> list = smartRoomsManager.searchMyReservationList(xsm01Vo);
		   for(BgabGascsm01 rsVo : list){
	    	   String cnfEeno = StringUtil.isNullToString(rsVo.getCnf_eeno());
	    	   String ipeEeno = StringUtil.isNullToString(rsVo.getIpe_eeno());
	    	   
			   if(!sessEmpno.equals(cnfEeno) && !sessEmpno.equals(ipeEeno)){
				   message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0059"));
				   stFlag = false;
				   break;
	    	   }else if(!sessEmpno.equals(ipeEeno)){
				   message.setMessage(HncisMessageSource.getMessage("MSG.VAL.0060"));
				   stFlag = false;
				   break;
	    	   }
		   }

		   if(stFlag){
			   smartRoomsManager.deleteConferenceRoom2(xsm01Vo);
			   smartRoomsManager.deleteConferenceRoom1(xsm01Vo);
			   stFlag = true;
		   }else{
			   message.setCode1("N");
			   stFlag = false;
			   break;
		   }
	   }

	   if(stFlag){
		   message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		   message.setCode1("Y");
	   }

	   ModelAndView modelAndView = new ModelAndView();
	   modelAndView.setViewName("data/data.json");
	   modelAndView.addObject("uiType", "ajax");
	   modelAndView.addObject("data", JSONObject.fromObject(message).toString());

	   return modelAndView;
	}
	
	/**
	 * 예약현황 조회
	 * @param req
	 * @param res
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doSearchByXsm04.do")
	public ModelAndView doSearchByXsm04(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		BgabGascsm01 smParamVo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		smParamVo.setIpe_eeno(SessionInfo.getSess_empno(req));
		
		String authCd = "";
		String master  = SessionInfo.getSess_mstu_gubb(req);
		String damdang = SessionInfo.getSess_work_auth(req);
		if("M".equals(master) || "5".equals(damdang)){
			smParamVo.setIsAuth("Y");
		}else{
			List<BgabGascsmDto> authList = new ArrayList<BgabGascsmDto>();
			authList = smartRoomsManager.searchXsmConfirmDamdang(smParamVo);
			int i = 0;
			for(BgabGascsmDto tgVo : authList){
				if(i == 0){
					authCd += tgVo.getAuth_cd();
				}else{
					authCd += "|"+tgVo.getAuth_cd();
				}
				i++;
			}
			smParamVo.setGubun(authCd);
			smParamVo.setIsAuth("N");
		}
		
		if("".equals(StringUtil.isNullToString(pageNumber))){ pageNumber = "1"; }
		if("".equals(StringUtil.isNullToString(pageSize))){ pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = smartRoomsManager.searchCountByXsm04(smParamVo);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		smParamVo.setStartRow(startRow);
		smParamVo.setEndRow(endRow);
		List<BgabGascsm01> rslist = smartRoomsManager.searchByXsm04(smParamVo);
		List<BgabGascsm01> finalRsList = new ArrayList<BgabGascsm01>();
		for(BgabGascsm01 trVo : rslist){
			if("4".equals(trVo.getPgs_st_cd())){
				trVo.setCnf_st_nm("");
			}else{
				//회의상태
				if("1".equals(trVo.getCnf_st_cd())){
					trVo.setCnf_st_nm("회의예정");
				}else if("2".equals(trVo.getCnf_st_cd())){
					trVo.setCnf_st_nm("회의종료");
				}else if("5".equals(trVo.getCnf_st_cd())){
					trVo.setCnf_st_nm("회의중");
				}else{
					trVo.setCnf_st_nm("");
				}
			}
			finalRsList.add(trVo);
		}
		list.setRows(finalRsList);
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	/**
	 * 예약현황 엑셀
	 * @param req
	 * @param res
	 * @param fileName
	 * @param header
	 * @param headerName
	 * @param fomatter
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="/hncis/smartRooms/doSearchExcelByXsm04.excel")
	public ModelAndView doSearchExcelByXsm04(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="fileName", required=false) String fileName,
		@RequestParam(value="header", required=false) String header,
		@RequestParam(value="headerName", required=false) String headerName,
		@RequestParam(value="fomatter", required=false) String fomatter,
		@RequestParam(value="page", required = false) String pageNumber,
		@RequestParam(value="rows", required = false) String pageSize,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGascsm01 smParamVo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		smParamVo.setIpe_eeno(SessionInfo.getSess_empno(req));
		
		String authCd = "";
		String master  = SessionInfo.getSess_mstu_gubb(req);
		String damdang = SessionInfo.getSess_work_auth(req);
		if("M".equals(master) || "5".equals(damdang)){
			smParamVo.setIsAuth("Y");
		}else{
			List<BgabGascsmDto> authList = new ArrayList<BgabGascsmDto>();
			authList = smartRoomsManager.searchXsmConfirmDamdang(smParamVo);
			int i = 0;
			for(BgabGascsmDto tgVo : authList){
				if(i == 0){
					authCd += tgVo.getAuth_cd();
				}else{
					authCd += "|"+tgVo.getAuth_cd();
				}
				i++;
			}
			smParamVo.setGubun(authCd);
			smParamVo.setIsAuth("N");
		}
		
		CommonList list = new CommonList();
		List<BgabGascsm01> rslist = smartRoomsManager.searchExcelByXsm04(smParamVo);
		List<BgabGascsm01> finalRsList = new ArrayList<BgabGascsm01>();
		for(BgabGascsm01 trVo : rslist){
			trVo.setCorm_nm(trVo.getRegn_nm()+"/"+trVo.getBd_nm()+"/"+trVo.getCorm_fl_cd()+"/"+trVo.getCorm_nm());
			
			if("4".equals(trVo.getPgs_st_cd())){
				trVo.setCnf_st_nm("");
			}else{
				//회의상태
				if("1".equals(trVo.getCnf_st_cd())){
					trVo.setCnf_st_nm("회의예정");
				}else if("2".equals(trVo.getCnf_st_cd())){
					trVo.setCnf_st_nm("회의종료");
				}else if("5".equals(trVo.getCnf_st_cd())){
					trVo.setCnf_st_nm("회의중");
				}else{
					trVo.setCnf_st_nm("");
				}
			}
			finalRsList.add(trVo);
		}
		list.setRows(finalRsList);
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
	
	/**
	 * 예약현황 삭제
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doDeleteByXsm04.do")
	public ModelAndView doDeleteByXsm04(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsD", required=true) String paramsD) throws HncisException, SessionException{
		
		List<BgabGascsm01> smDelList = (List<BgabGascsm01>) getJsonToList(paramsD, BgabGascsm01.class);
		int rs = 0;
		for(BgabGascsm01 sm01Vo : smDelList){
			sm01Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			rs += smartRoomsManager.deleteConferenceRoom2(sm01Vo);
			rs += smartRoomsManager.deleteConferenceRoom1(sm01Vo);
		}
		
		CommonMessage message = new CommonMessage();
		if(rs > 0){
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 예약현황 승인
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doUpdateUseApproveByXsm04.do")
	public ModelAndView doUpdateUseApproveByXsm04(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException, SessionException{
		
		List<BgabGascsm01> smDelList = (List<BgabGascsm01>) getJsonToList(paramsU, BgabGascsm01.class);
		int rs = 0;
		for(BgabGascsm01 sm01Vo : smDelList){
			sm01Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			sm01Vo.setUpdr_eeno(SessionInfo.getSess_empno(req));
			rs += smartRoomsManager.updateUseApproveByXsm04(sm01Vo);
		}
		
		CommonMessage message = new CommonMessage();
		if(rs > 0){
			message.setMessage(HncisMessageSource.getMessage("APPROVAL.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("APPROVAL.0002"));
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 예약현황 승인 취소
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doUpdateNotUseApproveByXsm04.do")
	public ModelAndView doUpdateNotUseApproveByXsm04(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException, SessionException{
		
		List<BgabGascsm01> smDelList = (List<BgabGascsm01>) getJsonToList(paramsU, BgabGascsm01.class);
		int rs = 0;
		for(BgabGascsm01 sm01Vo : smDelList){
			sm01Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			sm01Vo.setUpdr_eeno(SessionInfo.getSess_empno(req));
			rs += smartRoomsManager.updateNotUseApproveByXsm04(sm01Vo);
		}
		
		CommonMessage message = new CommonMessage();
		if(rs > 0){
			message.setMessage(HncisMessageSource.getMessage("APPROVAL.0001"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("APPROVAL.0003"));
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 건물 콤보 
	 * @param req
	 * @param res
	 * @param codknd
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
 	@RequestMapping(value="/hncis/smartRooms/selectBuildingComboByXsm05.do")
	public ModelAndView doSearchBuildingComboByXsm05(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="codknd" , required=true) String codkndJson) throws HncisException, SessionException{

 		BgabGascsm03 smParamVo = (BgabGascsm03) getJsonToBean(codkndJson, BgabGascsm03.class);
 		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
 		
 		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseArray  jsonArr = new  JSONBaseArray();
 		
		/**
		 * Z없음, S선택, 그외전체
		 */
		if(!"Z".equals(smParamVo.getCode2())){
			JSONBaseVO addJson = new JSONBaseVO();
			addJson.put("value", "");
			if("S".equals(smParamVo.getCode2())) addJson.put("name", HncisMessageSource.getMessage("select"));
			else
			if("A".equals(smParamVo.getCode2())) addJson.put("name", HncisMessageSource.getMessage("total"));
			
			jsonArr.add(addJson);
		}
		
		List<CommonCode> codeList = smartRoomsManager.searchBuildingComboByXsm05(smParamVo);		
		for(CommonCode targetBean : codeList){
			JSONBaseVO addJson = new JSONBaseVO();
			addJson.put("key", StringUtil.isNullToStrTrm(targetBean.getKey()));
			addJson.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
			addJson.put("value", StringUtil.isNullToStrTrm(targetBean.getValue()));
			jsonArr.add(addJson);
		}
		jso.put(smParamVo.getCode1(), jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}
	
	/**
	 * 건물 관리 조회
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException 
	 */
	@RequestMapping(value="/hncis/smartRooms/doSearchBuildingMgmtByXsm05.do")
	public ModelAndView doSearchBuildingMgmtByXsm05(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		BgabGascsm03 smParamVo = (BgabGascsm03) getJsonToBean(paramJson, BgabGascsm03.class);
		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		if("".equals(StringUtil.isNullToString(pageNumber))){ pageNumber = "1"; }
		if("".equals(StringUtil.isNullToString(pageSize))){ pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = smartRoomsManager.searchBuildingMgmtCountByXsm05(smParamVo);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		smParamVo.setStartRow(startRow);
		smParamVo.setEndRow(endRow);
		list.setRows(smartRoomsManager.searchBuildingMgmtByXsm05(smParamVo));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	/**
	 * 건물 관리 - 입력/수정
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doSaveBuildingMgmtByXsm05.do")
	public ModelAndView doSaveBuildingMgmtByXsm05(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsI", required=true) String paramsI,
		@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException, SessionException{
		
		List<BgabGascsm03> smSaveList = (List<BgabGascsm03>) getJsonToList(paramsI, BgabGascsm03.class);
		List<BgabGascsm03> smModifyList = (List<BgabGascsm03>) getJsonToList(paramsU, BgabGascsm03.class);
		
		for(int i=0; i<smSaveList.size(); i++){
			smSaveList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
			smSaveList.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			smSaveList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		
		for(int i=0; i<smModifyList.size(); i++){
			smModifyList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
			smModifyList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		
		smartRoomsManager.saveBuildingMgmtByXsm05(smSaveList, smModifyList);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 건물 관리 - 삭제
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doDeleteBuildingMgmtByXsm05.do")
	public ModelAndView doDeleteBuildingMgmtByXsm05(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsD", required=true) String paramsD) throws HncisException, SessionException{
		
		List<BgabGascsm03> smDelList = (List<BgabGascsm03>) getJsonToList(paramsD, BgabGascsm03.class);
		for(int i=0; i<smDelList.size(); i++){
			smDelList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
		}
		
		smartRoomsManager.deleteBuildingMgmtByXsm05(smDelList);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 회의실 콤보 
	 * @param req
	 * @param res
	 * @param codknd
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doSearchConferenceComboByXsm06.do")
	public ModelAndView doSearchConferenceComboByXsm06(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="codknd" , required=true) String codkndJson) throws HncisException, SessionException{
 		
 		BgabGascsm04 smParamVo = (BgabGascsm04) getJsonToBean(codkndJson, BgabGascsm04.class);
 		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
 		
 		JSONBaseVO jso = new JSONBaseVO();
		JSONBaseArray  jsonArr = new  JSONBaseArray();
 		
		/**
		 * Z없음, S선택, 그외전체
		 */
		if(!"Z".equals(smParamVo.getCode2())){
			JSONBaseVO addJson = new JSONBaseVO();
			addJson.put("value", "");
			if("S".equals(smParamVo.getCode2())) addJson.put("name", HncisMessageSource.getMessage("select"));
			else
			if("A".equals(smParamVo.getCode2())) addJson.put("name", HncisMessageSource.getMessage("total"));
			
			jsonArr.add(addJson);
		}
		
 		List<CommonCode> codeList = null;
 		if("FL".equals(smParamVo.getCode3())){
 			codeList = smartRoomsManager.searchConferenceFlComboByXsm06(smParamVo);
 		}else if("CD".equals(smParamVo.getCode3())){
 			codeList = smartRoomsManager.searchConferenceComboByXsm06(smParamVo);
 		}
		for(CommonCode targetBean : codeList){
			JSONBaseVO addJson = new JSONBaseVO();
			addJson.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
			addJson.put("value", StringUtil.isNullToStrTrm(targetBean.getValue()));
			jsonArr.add(addJson);
		}
		jso.put(smParamVo.getCode1(), jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}
	
	/**
	 * 회의실 관리 조회
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException 
	 */
	@RequestMapping(value="/hncis/smartRooms/doSearchConferenceRoomMgmtByXsm06.do")
	public ModelAndView doSearchConferenceRoomMgmtByXsm06(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		BgabGascsm04 smParamVo = (BgabGascsm04) getJsonToBean(paramJson, BgabGascsm04.class);
		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		if("".equals(StringUtil.isNullToString(pageNumber))){ pageNumber = "1"; }
		if("".equals(StringUtil.isNullToString(pageSize))){ pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = smartRoomsManager.searchConferenceRoomMgmtCountByXsm06(smParamVo);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		smParamVo.setStartRow(startRow);
		smParamVo.setEndRow(endRow);
		list.setRows(smartRoomsManager.searchConferenceRoomMgmtByXsm06(smParamVo));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	/**
	 * 회의실 관리 정책 조회
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException 
	 */
	@RequestMapping(value="/hncis/smartRooms/doSearchConferenceRoomMgmtPolicyByXsm06.do")
	public ModelAndView doSearchConferenceRoomMgmtPolicyByXsm06(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		BgabGascsm04 smParamVo = (BgabGascsm04) getJsonToBean(paramJson, BgabGascsm04.class);
		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		if("".equals(StringUtil.isNullToString(pageNumber))){ pageNumber = "1"; }
		if("".equals(StringUtil.isNullToString(pageSize))){ pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = smartRoomsManager.searchConferenceRoomMgmtCountByXsm06(smParamVo);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		smParamVo.setStartRow(startRow);
		smParamVo.setEndRow(endRow);
		list.setRows(smartRoomsManager.searchConferenceRoomMgmtByXsm06(smParamVo));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	/**
	 * 회의실 관리 - 입력/수정
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doSaveConferenceRoomMgmtByXsm06.do")
	public ModelAndView doSaveConferenceRoomMgmtByXsm06(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsI", required=true) String paramsI,
		@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException, SessionException{
		
		List<BgabGascsm04> smSaveList = (List<BgabGascsm04>) getJsonToList(paramsI, BgabGascsm04.class);
		List<BgabGascsm04> smModifyList = (List<BgabGascsm04>) getJsonToList(paramsU, BgabGascsm04.class);
		
		for(int i=0; i<smSaveList.size(); i++){
			smSaveList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
			smSaveList.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			smSaveList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		
		for(int i=0; i<smModifyList.size(); i++){
			smModifyList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
			smModifyList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		
		smartRoomsManager.saveConferenceRoomMgmtByXsm06(smSaveList, smModifyList);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 회의실 관리 - 삭제
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doDeleteConferenceRoomMgmtByXsm06.do")
	public ModelAndView doDeleteConferenceRoomMgmtByXsm06(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsD", required=true) String paramsD) throws HncisException, SessionException{
		
		List<BgabGascsm04> smModifyList = (List<BgabGascsm04>) getJsonToList(paramsD, BgabGascsm04.class);
		
		int rsCnt = smartRoomsManager.deleteConferenceRoomMgmtByXsm06(smModifyList);
		
		CommonMessage message = new CommonMessage();
		if(rsCnt > 0){
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 회의실 관리 - 정책 수정
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doSaveConferenceRoomMgmtPolicyByXsm06.do")
	public ModelAndView doSaveConferenceRoomMgmtPolicyByXsm06(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException, SessionException{
		
		List<BgabGascsm04> smModifyList = (List<BgabGascsm04>) getJsonToList(paramsU, BgabGascsm04.class);
		
		for(int i=0; i<smModifyList.size(); i++){
			smModifyList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
			smModifyList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		
		int rsCnt = smartRoomsManager.saveConferenceRoomMgmtPolicyByXsm06(smModifyList);
		
		CommonMessage message = new CommonMessage();
		if(rsCnt > 0){
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("SAVE.0001"));
		}
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 회의실코드 관리 - 조회
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doSearchConferenceRoomCodeMgmtByXsm07.do")
	public ModelAndView doSearchConferenceRoomCodeMgmtByXsm07(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "page", required = false) String pageNumber,
			@RequestParam(value = "rows", required = false) String pageSize,
			@RequestParam(value="paramJson" , required=true) String paramJson) throws Exception{

		BgabGascsm05 smParamVo = (BgabGascsm05) getJsonToBean(paramJson, BgabGascsm05.class);

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSizeSystem; }

 		int currentPage = Integer.parseInt(pageNumber);
 		int startRow = (currentPage - 1)* Integer.parseInt(pageSize) +1;
 		int endRow = currentPage * Integer.parseInt(pageSize);
 		int count = smartRoomsManager.searchConferenceRoomCodeMgmtCountByXsm07(smParamVo);
 		
 		CommonList list = new CommonList();
 		list.setPage(pageNumber);
 		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
 		list.setRecords(Integer.toString(count));

 		smParamVo.setStartRow(startRow);
 		smParamVo.setEndRow(endRow);
 		list.setRows(smartRoomsManager.searchConferenceRoomCodeMgmtByXsm07(smParamVo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	/**
	 * 회의실코드 관리 - 저장/수정
	 * @param req
	 * @param res
	 * @param paramsI
	 * @param paramsU
	 * @return
	 * @throws HncisException
	 * @throws SessionException 
	 */
	@RequestMapping(value="/hncis/smartRooms/doSaveConferenceRoomCodeMgmtByXsm07.do")
	public ModelAndView doSaveConferenceRoomCodeMgmtByXsm07(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramsI", required=true) String paramsI,
			@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException, SessionException{

		List<BgabGascsm05> smSaveList = (List<BgabGascsm05>)getJsonToList(paramsI, BgabGascsm05.class);
		List<BgabGascsm05> smModifyList = (List<BgabGascsm05>)getJsonToList(paramsU, BgabGascsm05.class);

		smartRoomsManager.saveConferenceRoomCodeMgmtByXsm07(smSaveList, smModifyList);

		for(int i=0; i<smSaveList.size(); i++){
			smSaveList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
			smSaveList.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			smSaveList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		
		for(int i=0; i<smModifyList.size(); i++){
			smModifyList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
			smModifyList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * 회의실코드 관리 - 삭제
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException 
	 */
	@RequestMapping(value="/hncis/smartRooms/doDeleteConferenceRoomCodeMgmtByXsm07.do")
	public ModelAndView doDeleteConferenceRoomCodeMgmtByXsm07(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		List<BgabGascsm05> smDelList = (List<BgabGascsm05>)getJsonToList(paramJson, BgabGascsm05.class);
		for(int i=0; i<smDelList.size(); i++){
			smDelList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
		}
		
		smartRoomsManager.deleteConferenceRoomCodeMgmtByXsm07(smDelList);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}
	
	/**
	 * 회의실코드 관리 - 코드종류 콤보박스
	 * @param req
	 * @param res
	 * @throws Exception
	 */
	@RequestMapping(value="/hncis/smartRooms/doSearchCodeKndCombo.do")
	public ModelAndView doSearchCodeKndCombo(HttpServletRequest req, HttpServletResponse res) throws Exception{
 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = new JSONBaseVO();
		JSONBaseArray jsonArr = new JSONBaseArray();
		BgabGascsm05 sm05Vo = new BgabGascsm05();
		sm05Vo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		List<BgabGascsm05> codeList = new ArrayList<BgabGascsm05>();
		codeList = smartRoomsManager.searchCodeKndCombo(sm05Vo);
		json.put("name", HncisMessageSource.getMessage("total"));
		json.put("value", "");
		jsonArr.add(json);
		
		for(BgabGascsm05 targetBean : codeList){
			json = new JSONBaseVO();
			json.put("value", StringUtil.isNullToStrTrm(targetBean.getXsm_code()));
			json.put("name", StringUtil.isNullToStrTrm(targetBean.getXsm_code() + " - " + targetBean.getXsm_hname()));
			jsonArr.add(json);
		}
		jso.put("CODE_LIST", jsonArr);
		
		ModelAndView modelAndView = new ModelAndView();		
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());
		
		return modelAndView;
	}
	
	/**
	 * 상용구 제목
	 * @param req
	 * @param res
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doSearchTitleByXsm.do")
	public ModelAndView doSearchTitleByXsm(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		ModelAndView modelAndView = null;
		BgabGascsm01 smParamVo = (BgabGascsm01) getJsonToBean(paramJson, BgabGascsm01.class);
		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		if("".equals(StringUtil.isNullToString(pageNumber))){ pageNumber = "1"; }
		if("".equals(StringUtil.isNullToString(pageSize))){ pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = smartRoomsManager.searchTitleCountByXsm(smParamVo);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		smParamVo.setStartRow(startRow);
		smParamVo.setEndRow(endRow);
		List<BgabGascsm01> rslist = smartRoomsManager.searchTitleByXsm(smParamVo);
		list.setRows(rslist);
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	/**
	 * 권한 관리 조회
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException 
	 */
	@RequestMapping(value="/hncis/smartRooms/doSearchAuthMgmtByXsm10.do")
	public ModelAndView doSearchAuthMgmtByXsm10(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{
		
		ModelAndView modelAndView = null;
		BgabGascsm06 smParamVo = (BgabGascsm06) getJsonToBean(paramJson, BgabGascsm06.class);
		smParamVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		if("".equals(StringUtil.isNullToString(pageNumber))){ pageNumber = "1"; }
		if("".equals(StringUtil.isNullToString(pageSize))){ pageSize = Constant.pageSize; }
		
		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = smartRoomsManager.searchAuthMgmtCountByXsm10(smParamVo);
		
		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));
		
		smParamVo.setStartRow(startRow);
		smParamVo.setEndRow(endRow);
		list.setRows(smartRoomsManager.searchAuthMgmtByXsm10(smParamVo));
		
		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	
	/**
	 * 권한 관리 - 입력/수정
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doSaveAuthMgmtByXsm10.do")
	public ModelAndView doSaveAuthMgmtByXsm10(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsI", required=true) String paramsI,
		@RequestParam(value="paramsU", required=true) String paramsU) throws HncisException, SessionException{
		
		List<BgabGascsm06> smSaveList = (List<BgabGascsm06>) getJsonToList(paramsI, BgabGascsm06.class);
		List<BgabGascsm06> smModifyList = (List<BgabGascsm06>) getJsonToList(paramsU, BgabGascsm06.class);
		
		for(int i=0; i<smSaveList.size(); i++){
			smSaveList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
			smSaveList.get(i).setIpe_eeno(SessionInfo.getSess_empno(req));
			smSaveList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		
		for(int i=0; i<smModifyList.size(); i++){
			smModifyList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
			smModifyList.get(i).setUpdr_eeno(SessionInfo.getSess_empno(req));
		}
		
		smartRoomsManager.saveAuthMgmtByXsm10(smSaveList, smModifyList);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 권한 관리 - 삭제
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/smartRooms/doDeleteAuthMgmtByXsm10.do")
	public ModelAndView doDeleteAuthMgmtByXsm10(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramsD", required=true) String paramsD) throws HncisException, SessionException{
		
		List<BgabGascsm06> smDelList = (List<BgabGascsm06>) getJsonToList(paramsD, BgabGascsm06.class);
		for(int i=0; i<smDelList.size(); i++){
			smDelList.get(i).setCorp_cd(SessionInfo.getSess_corp_cd(req));
		}
		
		smartRoomsManager.deleteAuthMgmtByXsm10(smDelList);
		
		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");
		
		return modelAndView;
	}
	
	/**
	 * 통계 현황
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hncis/smartRooms/doSearchListByXsm11.do")
	public ModelAndView doSearchListByXsm11(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "paramJson", required = false) String paramJson) throws HncisException, SessionException{
		
		BgabGascsm07 paramVo = (BgabGascsm07) getJsonToBean(paramJson, BgabGascsm07.class);
		paramVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		
		List<String> dayList = new ArrayList<String>();
		HashMap<String, Integer> dayMappingMap = new HashMap<String, Integer>();

		String[] arrDay = CurrentDateTime.getDiffDays(paramVo.getCnf_from_ymd(), paramVo.getCnf_to_ymd());
		List<BgabGascsm07> conferenceRoomList = smartRoomsManager.searchConferenceRoomListByXsm11(paramVo);

		int i = 0;
		for (String day : arrDay) {
			dayList.add(day);
			dayMappingMap.put(day, i++);
		}

		int maxRow = conferenceRoomList.size();
		int maxCol = (dayList.size()*5)+6;
		String[][] dataArr = new String[maxRow+1][maxCol];
		List<BgabGascsm07> list = smartRoomsManager.searchListByXsm11(paramVo);
		
		HashMap<String, Integer> conferenceRoomListMappingMap = new HashMap<String, Integer>();

		i=-1;
		for (BgabGascsm07 vo : conferenceRoomList) {
			String a = StringUtil.isNullToString(vo.getRegn_cd());
			String b = StringUtil.isNullToString(vo.getBd_cd());
			String c = StringUtil.isNullToString(vo.getCorm_fl_cd());
			String d = StringUtil.isNullToString(vo.getCorm_cd());
			conferenceRoomListMappingMap.put(a+b+c+d, ++i);
			
			String regnNm = StringUtil.isNullToString(vo.getRegn_nm());
			String bdNm = StringUtil.isNullToString(vo.getBd_nm());
			String cormFlNm = StringUtil.isNullToString(vo.getCorm_fl_cd())+"층";
			String cormNm = StringUtil.isNullToString(vo.getCorm_nm());
			dataArr[i][0] = regnNm+"/"+bdNm+"/"+cormFlNm+"/"+cormNm;
		}
		
		for (BgabGascsm07 vo : list) {
			String a = StringUtil.isNullToString(vo.getRegn_cd());
			String b = StringUtil.isNullToString(vo.getBd_cd());
			String c = StringUtil.isNullToString(vo.getCorm_fl_cd());
			String d = StringUtil.isNullToString(vo.getCorm_cd());
			
			int rowNum = conferenceRoomListMappingMap.get(a+b+c+d);
			int colNum = dayMappingMap.get(StringUtil.isNullToString(vo.getFrom_ymd()));
			boolean flag = vo.getRes_cnt() == null || ("").equals(StringUtil.isNullToString(vo.getRes_cnt()));
			dataArr[rowNum][(colNum*5)+6]  = flag ? null : String.valueOf(Double.parseDouble(vo.getRes_cnt()    ));
			dataArr[rowNum][(colNum*5)+7]  = flag ? null : String.valueOf(Double.parseDouble(vo.getUse_time()   ));
			dataArr[rowNum][(colNum*5)+8]  = flag ? null : String.valueOf(Double.parseDouble(vo.getNo_use_time()));
			dataArr[rowNum][(colNum*5)+9]  = flag ? null : String.valueOf(Double.parseDouble(vo.getUse_rate()   ));
			dataArr[rowNum][(colNum*5)+10] = flag ? null : String.valueOf(Double.parseDouble(vo.getCancle_cnt() ));
		}

		for (int j = 0; j < maxRow; j++) {
			double val1 = 0;
			double val2 = 0;
			double val3 = 0;
			double val4 = 0;
			for (int k = 0; k < dayList.size(); k++) {
				//데이터 없으면 0 집어넣는 부분 시작
				if(dataArr[j][(k*5)+6 ] == null){ dataArr[j][(k*5)+6 ] = "0"; }
				if(dataArr[j][(k*5)+7 ] == null){ dataArr[j][(k*5)+7 ] = "0"; }
				if(dataArr[j][(k*5)+8 ] == null){ dataArr[j][(k*5)+8 ] = "0"; }
				if(dataArr[j][(k*5)+9 ] == null){ dataArr[j][(k*5)+9 ] = "0"; }
				if(dataArr[j][(k*5)+10] == null){ dataArr[j][(k*5)+10] = "0"; }
				
				val1 += Double.parseDouble(dataArr[j][(k*5)+6 ]);
				val2 += Double.parseDouble(dataArr[j][(k*5)+7 ]);
				val3 += Double.parseDouble(dataArr[j][(k*5)+8 ]);
				val4 += Double.parseDouble(dataArr[j][(k*5)+10]);			
			}
			
			dataArr[j][1] = String.valueOf(val1);
			dataArr[j][2] = String.valueOf(val2);
			dataArr[j][3] = String.valueOf(val3);
			dataArr[j][4] = ""+Math.round((val2/(val2+val3))*100); //합계률 재계산
			dataArr[j][5] = String.valueOf(val4);

		}

		dataArr[maxRow][0] = "합계";
		for(int k = 0; k< dayList.size()+1; k++){
			double val1 = 0;
			double val2 = 0;
			double val3 = 0;
			double val4 = 0;
			for(int j = 0; j< maxRow; j++){
				val1 += Double.parseDouble(dataArr[j][(k*5)+1]);
				val2 += Double.parseDouble(dataArr[j][(k*5)+2]);
				val3 += Double.parseDouble(dataArr[j][(k*5)+3]);
				val4 += Double.parseDouble(dataArr[j][(k*5)+5]);	
			}
			dataArr[maxRow][(k*5)+1] = String.valueOf(val1);
			dataArr[maxRow][(k*5)+2] = String.valueOf(val2);
			dataArr[maxRow][(k*5)+3] = String.valueOf(val3);
			dataArr[maxRow][(k*5)+4] = ""+Math.round((val2/(val2+val3))*100); //합계률 재계산
			dataArr[maxRow][(k*5)+5] = String.valueOf(val4);	
		}
		
		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
		rtnMap.put("dataArr", dataArr);
		rtnMap.put("dayList", dayList);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("data/data.json");
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject("data", JSONObject.fromObject(rtnMap).toString());
		
		return modelAndView;

	}
	
	@RequestMapping(value = "/hncis/smartRooms/doSearchExcelListXsm11.do")
	public void doSearchExcelListXsm11(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value = "paramJson", required = false) String paramJson) throws Exception {

		req.setCharacterEncoding("euc-kr");
		try {
			BgabGascsm07 paramVo = (BgabGascsm07) getJsonToBean(paramJson, BgabGascsm07.class);
			paramVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			
			List<String> dayList = new ArrayList<String>();
			HashMap<String, Integer> dayMappingMap = new HashMap<String, Integer>();

			String[] arrDay = CurrentDateTime.getDiffDays(paramVo.getCnf_from_ymd(), paramVo.getCnf_to_ymd());
			List<BgabGascsm07> conferenceRoomList = smartRoomsManager.searchConferenceRoomListByXsm11(paramVo);

			int i = 0;
			for (String day : arrDay) {
				dayList.add(day);
				dayMappingMap.put(day, i++);
			}

			int maxRow = conferenceRoomList.size();
			int maxCol = (dayList.size()*5)+6;
			String[][] dataArr = new String[maxRow+1][maxCol];
			List<BgabGascsm07> list = smartRoomsManager.searchListByXsm11(paramVo);
			
			HashMap<String, Integer> conferenceRoomListMappingMap = new HashMap<String, Integer>();

			i=-1;
			for (BgabGascsm07 vo : conferenceRoomList) {
				String a = StringUtil.isNullToString(vo.getRegn_cd());
				String b = StringUtil.isNullToString(vo.getBd_cd());
				String c = StringUtil.isNullToString(vo.getCorm_fl_cd());
				String d = StringUtil.isNullToString(vo.getCorm_cd());
				conferenceRoomListMappingMap.put(a+b+c+d, ++i);
				
				String regnNm = StringUtil.isNullToString(vo.getRegn_nm());
				String bdNm = StringUtil.isNullToString(vo.getBd_nm());
				String cormFlNm = StringUtil.isNullToString(vo.getCorm_fl_cd())+"층";
				String cormNm = StringUtil.isNullToString(vo.getCorm_nm());
				dataArr[i][0] = regnNm+"/"+bdNm+"/"+cormFlNm+"/"+cormNm;
			}
			
			for (BgabGascsm07 vo : list) {
				String a = StringUtil.isNullToString(vo.getRegn_cd());
				String b = StringUtil.isNullToString(vo.getBd_cd());
				String c = StringUtil.isNullToString(vo.getCorm_fl_cd());
				String d = StringUtil.isNullToString(vo.getCorm_cd());
				
				int rowNum = conferenceRoomListMappingMap.get(a+b+c+d);
				int colNum = dayMappingMap.get(StringUtil.isNullToString(vo.getFrom_ymd()));
				boolean flag = vo.getRes_cnt() == null || ("").equals(StringUtil.isNullToString(vo.getRes_cnt()));
				dataArr[rowNum][(colNum*5)+6]  = flag ? null : String.valueOf(Double.parseDouble(vo.getRes_cnt()    ));
				dataArr[rowNum][(colNum*5)+7]  = flag ? null : String.valueOf(Double.parseDouble(vo.getUse_time()   ));
				dataArr[rowNum][(colNum*5)+8]  = flag ? null : String.valueOf(Double.parseDouble(vo.getNo_use_time()));
				dataArr[rowNum][(colNum*5)+9]  = flag ? null : String.valueOf(Double.parseDouble(vo.getUse_rate()   ));
				dataArr[rowNum][(colNum*5)+10] = flag ? null : String.valueOf(Double.parseDouble(vo.getCancle_cnt() ));
			}

			for (int j = 0; j < maxRow; j++) {
				double val1 = 0;
				double val2 = 0;
				double val3 = 0;
				double val4 = 0;
				for (int k = 0; k < dayList.size(); k++) {
					//데이터 없으면 0 집어넣는 부분 시작
					if(dataArr[j][(k*5)+6 ] == null){ dataArr[j][(k*5)+6 ] = "0"; }
					if(dataArr[j][(k*5)+7 ] == null){ dataArr[j][(k*5)+7 ] = "0"; }
					if(dataArr[j][(k*5)+8 ] == null){ dataArr[j][(k*5)+8 ] = "0"; }
					if(dataArr[j][(k*5)+9 ] == null){ dataArr[j][(k*5)+9 ] = "0"; }
					if(dataArr[j][(k*5)+10] == null){ dataArr[j][(k*5)+10] = "0"; }
					
					val1 += Double.parseDouble(dataArr[j][(k*5)+6 ]);
					val2 += Double.parseDouble(dataArr[j][(k*5)+7 ]);
					val3 += Double.parseDouble(dataArr[j][(k*5)+8 ]);
					val4 += Double.parseDouble(dataArr[j][(k*5)+10]);			
				}
				
				dataArr[j][1] = String.valueOf(val1);
				dataArr[j][2] = String.valueOf(val2);
				dataArr[j][3] = String.valueOf(val3);
				dataArr[j][4] = ""+Math.round((val2/(val2+val3))*100); //합계률 재계산
				dataArr[j][5] = String.valueOf(val4);

			}

			dataArr[maxRow][0] = "합계";
			for(int k = 0; k< dayList.size()+1; k++){
				double val1 = 0;
				double val2 = 0;
				double val3 = 0;
				double val4 = 0;
				for(int j = 0; j< maxRow; j++){
					val1 += Double.parseDouble(dataArr[j][(k*5)+1]);
					val2 += Double.parseDouble(dataArr[j][(k*5)+2]);
					val3 += Double.parseDouble(dataArr[j][(k*5)+3]);
					val4 += Double.parseDouble(dataArr[j][(k*5)+5]);	
				}
				dataArr[maxRow][(k*5)+1] = String.valueOf(val1);
				dataArr[maxRow][(k*5)+2] = String.valueOf(val2);
				dataArr[maxRow][(k*5)+3] = String.valueOf(val3);
				dataArr[maxRow][(k*5)+4] = ""+Math.round((val2/(val2+val3))*100); //합계률 재계산
				dataArr[maxRow][(k*5)+5] = String.valueOf(val4);	
			}
			
			HashMap<String, Object> rtnMap = new HashMap<String, Object>();
			req.setAttribute("dayList", dayList);
			req.setAttribute("dataArr", dataArr);

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{ req.getRequestDispatcher("/hncis/smartRooms/xsm11_excel.gas").forward(req, res); }catch(Exception e){ e.printStackTrace(); }
		}
	}
}
