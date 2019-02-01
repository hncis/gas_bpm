package com.hncis.roomsMeals.manager.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.application.SessionInfo;
import com.hncis.common.dao.CommonJobDao;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.HncisCommon;
import com.hncis.roomsMeals.dao.RoomsMealsDao;
import com.hncis.roomsMeals.manager.RoomsMealsManager;
import com.hncis.roomsMeals.util.RoomsMealsUtil;
import com.hncis.roomsMeals.vo.BgabGascrm01Dto;
import com.hncis.roomsMeals.vo.BgabGascrm02Dto;
import com.hncis.roomsMeals.vo.BgabGascrm03Dto;
import com.hncis.roomsMeals.vo.BgabGascrm04Dto;
import com.hncis.system.dao.SystemDao;

@Service("roomsMealsManagerImpl")
public class RoomsMealsManagerImpl implements RoomsMealsManager{
    private transient Log logger = LogFactory.getLog(getClass());

    private static final String strUpdate = "update";
    private static final String strpCode = "P-E-004";
    private static final String strsCode = "GASBZ01540010";
    
	@Autowired
	public RoomsMealsDao roomsMealsDao;

	@Autowired
	public CommonJobDao commonJobDao;

	@Autowired
	public SystemDao systemDao;

	@Override
	public List<BgabGascrm03Dto> selectRmToMultiComboPlant(BgabGascrm03Dto dto) {
		return roomsMealsDao.selectRmToMultiComboPlant(dto);
	}
	@Override
	public List<BgabGascrm04Dto> selectRmToMultiComboMeal(BgabGascrm04Dto dto) {
		return roomsMealsDao.selectRmToMultiComboMeal(dto);
	}

	@Override
	public BgabGascrm03Dto selectRmToRoomAuthInfo(BgabGascrm03Dto dto) {

		List<BgabGascrm03Dto> list = roomsMealsDao.selectRmToRoomAuthInfo(dto);

		String roomAuth = "";
		for(int i=0 ; i < list.size() ; i++){
			if(roomAuth.equals("")){
				roomAuth = list.get(i).getRoom_code();
			}else{
				roomAuth += ","+list.get(i).getRoom_code();
			}
		}

		BgabGascrm03Dto rtnDto = new BgabGascrm03Dto();
		rtnDto.setRoom_code(roomAuth);
		return rtnDto;
	}

	@Override
	public BgabGascrm01Dto saveRmToRequest(HttpServletRequest req, BgabGascrm01Dto dto) {
		
		List<BgabGascrm02Dto> dtoDtl = new ArrayList<BgabGascrm02Dto>();
		BgabGascrm02Dto rm2Dto = null;
		BgabGascrm01Dto rtnDto = new BgabGascrm01Dto();

		String docNo = "";

		if(dto.getDoc_no().equals("")){
			docNo = StringUtil.getDocNo();
			dto.setDoc_no(docNo);
			dto.setMode("insert");
		}else{
			docNo = dto.getDoc_no();
			dto.setMode(strUpdate);
		}

		try{

			List<String> dateList = new ArrayList<String>();

			List<String> dateArr = CurrentDateTime.getDateFrToList(dto.getFr_ymd(),dto.getTo_ymd());

			if(!StringUtil.isNullToString(dto.getRsvt_day()).equals("")){
				for(int i=0 ; i< dateArr.size() ; i++){
					int dayWeek = CurrentDateTime.getDayOfWeek(dateArr.get(i));

					if(dayWeek == Integer.parseInt(dto.getRsvt_day())){
						dateList.add(dateArr.get(i));
					}

				}
			}



			rtnDto = rommsMealsFeildCheck(req,dto, dateList);

			rtnDto.setDoc_no(dto.getDoc_no());

			if(!rtnDto.isErrYn()){
				int cnt = roomsMealsDao.insertRmToRequest(dto);

				int cnt1 = roomsMealsDao.deleteRmToReqList(dto);



				if(!StringUtil.isNullToString(dto.getRsvt_day()).equals("")){
					int dtCnt = 0;
					for(int i = 0 ; i < dateList.size() ; i++){

						rm2Dto = new BgabGascrm02Dto();
						rm2Dto.setDoc_no(docNo);
						rm2Dto.setSply_ymd(dateList.get(i));
						rm2Dto.setIpe_eeno(dto.getIpe_eeno());
						rm2Dto.setUpdr_eeno(dto.getUpdr_eeno());
						rm2Dto.setCorp_cd(dto.getCorp_cd());
						dtoDtl.add(rm2Dto);
					}
					dtCnt = roomsMealsDao.insertRmToReqList(dtoDtl);
				}
				else{
					int dateTerm = (int) CurrentDateTime.diffOfDate(dto.getFr_ymd(),dto.getTo_ymd())+1;

					int dtCnt = 0;
					for(int i = 0 ; i < dateTerm ; i++){

						rm2Dto = new BgabGascrm02Dto();
						rm2Dto.setDoc_no(docNo);
						rm2Dto.setSply_ymd(CurrentDateTime.getDate(dto.getFr_ymd(), i));
						rm2Dto.setIpe_eeno(dto.getIpe_eeno());
						rm2Dto.setUpdr_eeno(dto.getUpdr_eeno());
						rm2Dto.setCorp_cd(dto.getCorp_cd());
						dtoDtl.add(rm2Dto);
					}
					dtCnt = roomsMealsDao.insertRmToReqList(dtoDtl);
				}


				if(cnt !=1){
					rtnDto.setErrYn(true);
					rtnDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
			}
			
			// BPM API호출
			String processCode = strpCode; 	//프로세스 코드 (도서 프로세스) - 프로세스 정의서 참조
			String bizKey = dto.getDoc_no();	//신청서 번호
			String statusCode = strsCode;	//액티비티 코드 (도서신청서작성) - 프로세스 정의서 참조
			String loginUserId = dto.getIpe_eeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = "GASROLE01540030";   //도서 담당자 역할코드
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			managerList.add("10000001");

			String bpmSaveMsg = BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
			logger.info("BPM 저장 메시지 : " + bpmSaveMsg);


		} catch (Exception e) {
			logger.error(e);
			rtnDto.setErrYn(true);
			rtnDto.setErrMsg(HncisMessageSource.getMessage("SAVE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		return rtnDto;
	}

	@Override
	public CommonMessage deleteRmToRequest(HttpServletRequest req, BgabGascrm01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = roomsMealsDao.deleteRmToRequest(dto);
			if(cnt > 0){
				
				// BPM API호출
				String processCode = strpCode; 	//프로세스 코드 (명함 프로세스) - 프로세스 정의서 참조
				String bizKey = dto.getDoc_no();	//신청서 번호
				String statusCode = strsCode;	//액티비티 코드 (명함 신청서작성) - 프로세스 정의서 참조
				String loginUserId = dto.getEeno();	//로그인 사용자 아이디
				
				BpmApiUtil.sendDeleteAndRejectTask(processCode, bizKey, statusCode, loginUserId);
			}
			message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));
			message.setCode1("Y");
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("DELETE.0001"));
			message.setCode1("N");
		}
		return message;
	}

	private BgabGascrm01Dto rommsMealsFeildCheck(HttpServletRequest req,BgabGascrm01Dto roomsDto, List<String> dateArr) throws Exception {

		BgabGascrm01Dto reqDto =new BgabGascrm01Dto();
		BgabGascrm01Dto dto =new BgabGascrm01Dto();

		String doc_no 				= StringUtil.isNullTrim(roomsDto.getDoc_no());
		String room_plant			=StringUtil.isNullTrim(roomsDto.getRoom_plant());
		String room_place			=StringUtil.isNullTrim(roomsDto.getRoom_place());
		String ptt_ymd 				= StringUtil.isNullTrim(roomsDto.getPtt_ymd());
		String fr_ymd				=StringUtil.isNullTrim(roomsDto.getFr_ymd());
		String fr_time				=StringUtil.isNullTrim(roomsDto.getFr_time());
		String to_ymd 				= StringUtil.isNullTrim(roomsDto.getTo_ymd());
		String to_time				=StringUtil.isNullTrim(roomsDto.getTo_time());
		String tim_info_sbc			= StringUtil.isNullTrim(roomsDto.getTim_info_sbc());

		String pgs_st_cd 			= StringUtil.isNullTrim(roomsDto.getPgs_st_cd());
		String ipe_eeno  			= SessionInfo.getSess_empno(req);
		String updr_eeno 			=  SessionInfo.getSess_empno(req);
		String req_eeno 			= StringUtil.isNullTrim(roomsDto.getReq_eeno());
		String room_place_h 		= StringUtil.isNullTrim(roomsDto.getRoom_place_h());
		String auth_type 			= StringUtil.isNullTrim(roomsDto.getAuth_type());
		String rsvtDay 				= StringUtil.isNullTrim(roomsDto.getRsvt_day());
		
		dto.setCorp_cd(roomsDto.getCorp_cd());
		
		int tmp_fr_num = 0;
		if( "30".equals(fr_time.substring(2,4))){
			tmp_fr_num  = 1;
		}
		int fr_num = Integer.parseInt(fr_time.substring(0,2)) * 2 + tmp_fr_num;
		int tmp_to_num = 0;
		if( "30".equals(to_time.substring(2,4))){
			tmp_to_num  = 1;
		}
		int to_num = Integer.parseInt(to_time.substring(0,2)) * 2 + tmp_to_num;

		String shtm_lnd_trm_dct	="";
		if(!fr_ymd.equals(to_ymd)){
			shtm_lnd_trm_dct = (CurrentDateTime.diffOfDate(fr_ymd, to_ymd) + 1L)+"";
		}else{
			shtm_lnd_trm_dct = "1";
		}
		String EMPNO = SessionInfo.getSess_empno(req);
		String mode = StringUtil.isNullTrim(roomsDto.getMode());
		String msg = "";
		boolean errYn	=	false;
		int workAuth = 0;
		int sndType = 0;
		if(mode.equals(strUpdate) || mode.equals("delete")){

			HncisCommon hmcCommon = new HncisCommon();
			String scrnId="XRM01";
			hmcCommon.setEeNo(EMPNO);
			hmcCommon.setScrn_id(scrnId);
			hmcCommon.setXcod_code(scrnId.substring(1, 3));
			hmcCommon.setLocale(req.getSession().getAttribute("reqLocale").toString());
			try{
				HncisCommon scrnInfo = commonJobDao.getScrnInfo(hmcCommon);

			sndType = Integer.parseInt(scrnInfo.getMenu_mgrp_cd().trim());
			hmcCommon.setSndType(sndType);
			workAuth = Integer.parseInt(commonJobDao.getAuthority(hmcCommon).trim());
			}catch (Exception ee) {
				logger.error(ee);
				workAuth = 0;
			}
			if(!(workAuth > 4 || SessionInfo.getSess_mstu_gubb(req).equals("M") || auth_type.indexOf(room_place_h) > -1)){
				if(req_eeno.equals(EMPNO)){
				}
				else{
					errYn = true;
					if(mode.equals(strUpdate)){
						msg   = "Can not edit data.";
					}
					else{
						msg   = "Can not delete data.";
					}

				}
			}
		}
		if(!errYn){
			if(mode.equals("insert") || mode.equals(strUpdate)){

				String bTime = tim_info_sbc;
				int st = 0;
				int et = 0;
				boolean fsYn = true;
				for(int i=0;i<bTime.length();i++){
					if(bTime.substring(i,i+1).equals("Y")){
						if(fsYn == true){
							st = i;
							fsYn = false;
						}
						et++;
					}
				}

				boolean used = false;


				if(rsvtDay.equals("")){
					dto.setBtisSt(st+1);
					dto.setBtisEd(et);
					if(!fr_ymd.equals(to_ymd)){
						dto.setBtisSt(0);
						dto.setBtisEd(48);
					}
					dto.setDoc_no(doc_no);
					dto.setRoom_plant(room_plant);
					dto.setRoom_place(room_place);
					dto.setPtt_ymd(ptt_ymd);
					dto.setFr_ymd(fr_ymd);
					dto.setTo_ymd(to_ymd);
					dto.setMode(mode);

					List<String> times = roomsMealsDao.getSelectRmToCheckUseTime2(dto);

					if("N".equals(roomsDto.getChkFlag())){
						if(times.size() > 0){
							for(int i=0;i<times.size();i++){
								for(int j=0;j<times.get(i).length();j++){
									if(times.get(i).substring(j,j+1).equals("Y")){
										used = true; break;
									}
								}
							}
						}
					}else{
						if(times.size() > 0){
							for(int i=0;i<times.size();i++){
								for(int j=0;j<times.get(i).length();j++){
									if(times.get(i).substring(j,j+1).equals("Y") && j >= fr_num && j <= to_num){
										used = true; break;
									}
								}
							}
						}
					}

				}else{
					for(int n=0; n < dateArr.size() ; n++){
						dto.setBtisSt(st+1);
						dto.setBtisEd(et);
						dto.setDoc_no(doc_no);
						dto.setRoom_plant(room_plant);
						dto.setRoom_place(room_place);
						dto.setPtt_ymd(ptt_ymd);
						dto.setSply_ymd(dateArr.get(n));
						dto.setMode(mode);
						//dto.setUser_eeno(user_eeno);

						List<String> times = roomsMealsDao.getSelectRmToCheckUseTime(dto);

						if(times.size() > 0){
							for(int i=0;i<times.size();i++){
								for(int j=0;j<times.get(i).length();j++){
									if(times.get(i).substring(j,j+1).equals("Y")){
										used = true; break;
									}
								}
							}
						}
					}
				}


				if(used){
					errYn = true;
					msg   = "이미 신청된 예약시간입니다.";
				}

//				if(!errYn){
//					if(!(workAuth > 4 || SessionInfo.getSess_mstu_gubb(req).equals("M"))){
//						int chkCnt = roomsMealsDao.getSelectPcToCheckUsrEeno(dto);
//						if(chkCnt > 0){
//							errYn = true;
//							msg   = "Requested data already exists on the date requested.";
//						}
//					}
//				}

			}
		}

		reqDto.setErrYn(errYn);
		reqDto.setErrMsg(msg);
		return reqDto;
	}

	@Override
	public BgabGascrm01Dto selectInfoRmToRequest(BgabGascrm01Dto dto) {
		return roomsMealsDao.selectInfoRmToRequest(dto);
	}

	@Override
	public List<BgabGascrm01Dto> selectInfoRmToReqList(BgabGascrm01Dto dto, HttpServletRequest req) {

		RoomsMealsUtil rmUtil = new RoomsMealsUtil();

		List<BgabGascrm01Dto> placeList = roomsMealsDao.selectInfoRmToPlaceList(dto);

		List<BgabGascrm01Dto> list = null;
		List<BgabGascrm01Dto> pttList = roomsMealsDao.selectInfoRmToReqList(dto);
		list = rmUtil.getRequstRoomByPlace(pttList , placeList, dto, req);

		return list;
	}

	@Override
	public CommonMessage setApproval(BgabGascrm01Dto reqDto,HttpServletRequest req) {
		CommonMessage message = new CommonMessage();
		
		try {
			roomsMealsDao.updateRmToApprveInfo(reqDto);
			message.setMessage(HncisMessageSource.getMessage("APPROVE.0000"));
			
			// BPM API호출
			String processCode = strpCode; 	//프로세스 코드 (회의실 프로세스) - 프로세스 정의서 참조
			String bizKey = reqDto.getDoc_no();	//신청서 번호
			String statusCode = strsCode;	//액티비티 코드 (회의실 신청서작성) - 프로세스 정의서 참조
			String loginUserId = reqDto.getEeno();	//로그인 사용자 아이디
			String comment = null;
			String roleCode = "GASBZ01540030";   //휴양소 담당자 역할코드
			
			//역할정보
			List<String> approveList = new ArrayList<String>();
			List<String> managerList = new ArrayList<String>();
			approveList.add("10000001");
			managerList.add("10000001");

			BpmApiUtil.sendCompleteTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList);
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			message.setErrorCode("E");
			message.setMessage(HncisMessageSource.getMessage("APPROVE.0001"));
			logger.error(e);
		}

		return message;
	}

	@Override
	public CommonMessage setApprovalCancel(BgabGascrm01Dto regDto, HttpServletRequest req) {
		CommonMessage message = new CommonMessage();
		
		int cnt = roomsMealsDao.updateRmToApprveInfo(regDto);
			
		if(cnt > 0){
			// BPM API호출
			String processCode = strpCode; 	//프로세스 코드 (회의실 프로세스) - 프로세스 정의서 참조
			String bizKey = regDto.getDoc_no();	//신청서 번호
			String statusCode = strsCode;	//액티비티 코드 (회의실 신청서작성) - 프로세스 정의서 참조
			String loginUserId = regDto.getUpdr_eeno();	//로그인 사용자 아이디
	
			BpmApiUtil.sendRestoreTask(processCode, bizKey, statusCode, loginUserId);
			
			message.setMessage(HncisMessageSource.getMessage("APPROVE.0002"));
		}else{
			message.setMessage(HncisMessageSource.getMessage("APPROVE.0003"));
		}
		
		return message;
	}

	@Override
	public CommonMessage updateRmToRequestForConfirm(HttpServletRequest req, BgabGascrm01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = roomsMealsDao.updateRmToRequestForConfirm(dto);
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
			message.setCode1("Y");
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
			message.setCode1("N");
		}
		return message;
	}

	@Override
	public CommonMessage doApproveToMyConfirm(List<BgabGascrm01Dto> list){
		CommonMessage message = new CommonMessage();
		try{
			for(BgabGascrm01Dto dto : list){
				if("N".equals(roomsMealsDao.selectRmToApproveForVipRoomCheck(dto))){
					roomsMealsDao.updateRmToApproveForConfirm(dto);
				}
			}
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
			message.setCode1("Y");
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
			message.setCode1("N");
		}
		return message;
	}

	@Override
	public CommonMessage updateRmToRequestForConfirmCancel(HttpServletRequest req, BgabGascrm01Dto dto) {
		CommonMessage message = new CommonMessage();
		try{
			int cnt = roomsMealsDao.updateRmToRequestForConfirmCancel(dto);
			message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0000"));
			message.setCode1("Y");
		}catch (Exception e) {
			message.setMessage(HncisMessageSource.getMessage("CONFIRMCANCEL.0001"));
			message.setCode1("N");
		}
		return message;
	}

	@Override
	public List<BgabGascrm01Dto> selectInfoRmToListForMon(BgabGascrm01Dto dto, HttpServletRequest req) {

		RoomsMealsUtil rmUtil = new RoomsMealsUtil();

		List<BgabGascrm01Dto> list = null;
		List<BgabGascrm01Dto> pttList = roomsMealsDao.selectInfoRmToListForMon(dto);
		list = rmUtil.getRequstRoomByDay(pttList, dto, req);

		return list;
	}


	@Override
	public int selectCountRmToListDaily(BgabGascrm01Dto dto) {
		return roomsMealsDao.selectCountRmToListDaily(dto);
	}
	@Override
	public List<BgabGascrm01Dto> selectListRmToListDaily(BgabGascrm01Dto dto) {
		List<BgabGascrm01Dto> list = roomsMealsDao.selectListRmToListDaily(dto);
		String [] day_name_en = {"", "Sun", "Mon", "Tue", "Wen", "Thu", "Fri", "Sat" };
		String [] day_name_ko = {"", "일", "월", "화", "수", "목", "금", "토" };
		
		for(BgabGascrm01Dto dto1 : list){
			if(dto.getLocale().equals("ko")){
				dto1.setRsvt_day(day_name_ko[Integer.parseInt(StringUtil.isNullToString(dto1.getRsvt_day(), "0"))]);
			}else{
				dto1.setRsvt_day(day_name_en[Integer.parseInt(StringUtil.isNullToString(dto1.getRsvt_day(), "0"))]);
			}
		}

		return list;
	}

	@Override
	public BgabGascrm01Dto updateRmToListDailyForDone(HttpServletRequest req, List<BgabGascrm01Dto> dtoList) {
		BgabGascrm01Dto rtnDto = null;

		try {
			for(int i=0 ; i<dtoList.size() ; i++){
				BgabGascrm01Dto reqDto = roomsMealsDao.selectInfoRmToRequest(dtoList.get(i));

				if(reqDto.getPgs_st_cd().equals("0")){
					reqDto.setMode(strUpdate);
				}else{
					reqDto.setMode("insert");
				}

				reqDto.setFr_ymd(reqDto.getFr_ymd_h());
				reqDto.setTo_ymd(reqDto.getTo_ymd_h());

				rtnDto = new BgabGascrm01Dto();

				//rtnDto = rommsMealsFeildCheck(req,reqDto);

				rtnDto.setDoc_no(reqDto.getDoc_no());

				if(!rtnDto.isErrYn()){
					roomsMealsDao.updateRmToListDailyForDone(dtoList.get(i));
				}
				else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					rtnDto.setErrYn(true);
					break;
				}
			}

		} catch (Exception e) {
			logger.error(e);
			rtnDto.setErrYn(true);
			rtnDto.setErrMsg(HncisMessageSource.getMessage("DONE.0001"));
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return rtnDto;

	}

	@Override
	public void updateRmToListDailyForCancel(List<BgabGascrm01Dto> dtoList) {
		roomsMealsDao.updateRmToListDailyForCancel(dtoList);
	}

	@Override
	public int selectCountRmToRoomsManagement(BgabGascrm03Dto dto) {
		return roomsMealsDao.selectCountRmToRoomsManagement(dto);
	}

	@Override
	public List<BgabGascrm03Dto> selectListRmToRoomsManagement(BgabGascrm03Dto dto) {
		return roomsMealsDao.selectListRmToRoomsManagement(dto);
	}

	@Override
	public void saveRmToRoomsManagement(List<BgabGascrm03Dto> dtoIList, List<BgabGascrm03Dto> dtoUList) {
		roomsMealsDao.insertRmToRoomsManagement(dtoIList);
		roomsMealsDao.updateRmToRoomsManagement(dtoUList);

	}

	@Override
	public void deleteRmToRoomsManagement(List<BgabGascrm03Dto> dtoList) {
		roomsMealsDao.deleteRmToRoomsManagement(dtoList);
	}

	@Override
	public int selectCountRmToMealsManagement(BgabGascrm04Dto dto) {
		return roomsMealsDao.selectCountRmToMealsManagement(dto);
	}

	@Override
	public List<BgabGascrm04Dto> selectListRmToMealsManagement(BgabGascrm04Dto dto) {
		return roomsMealsDao.selectListRmToMealsManagement(dto);
	}

	@Override
	public void saveRmToMealsManagement(List<BgabGascrm04Dto> dtoIList, List<BgabGascrm04Dto> dtoUList) {
		roomsMealsDao.insertRmToMealsManagement(dtoIList);
		roomsMealsDao.updateRmToMealsManagement(dtoUList);

	}

	@Override
	public void deleteRmToMealsManagement(List<BgabGascrm04Dto> dtoList) {
		roomsMealsDao.deleteRmToMealsManagement(dtoList);
	}


	@Override
	public int selectCountRmToMealsMgmtList(BgabGascrm01Dto dto) {
		return roomsMealsDao.selectCountRmToMealsMgmtList(dto);
	}
	@Override
	public List<BgabGascrm01Dto> selectListRmToMealsMgmtList(BgabGascrm01Dto dto) {
		return roomsMealsDao.selectListRmToMealsMgmtList(dto);
	}

	@Override
	public void updateRmToMealsMgmtStatus(List<BgabGascrm01Dto> dtoList) {
		roomsMealsDao.updateRmToMealsMgmtStatus(dtoList);
	}

	@Override
	public List<BgabGascrm01Dto> selectListRmToConfirmList(BgabGascrm01Dto dto) {
		List<BgabGascrm01Dto> list = roomsMealsDao.selectListRmToConfirmList(dto);
		String [] day_name_en = {"", "Sun", "Mon", "Tue", "Wen", "Thu", "Fri", "Sat" };
		String [] day_name_ko = {"", "일", "월", "화", "수", "목", "금", "토" };
		
		for(BgabGascrm01Dto dto1 : list){
			if(dto.getLocale().equals("ko")){
				dto1.setRsvt_day(day_name_ko[Integer.parseInt(StringUtil.isNullToString(dto1.getRsvt_day(), "0"))]);
			}else{
				dto1.setRsvt_day(day_name_en[Integer.parseInt(StringUtil.isNullToString(dto1.getRsvt_day(), "0"))]);
			}
		}

		return list;
	}
	@Override
	public int selectCountRmToConfirmList(BgabGascrm01Dto dto) {
		return roomsMealsDao.selectCountRmToConfirmList(dto);
	}
	@Override
	public void updateRmToConfirmList(HttpServletRequest req, List<BgabGascrm01Dto> dtoList) {
		roomsMealsDao.updateRmToConfirmList(dtoList);
	}
	@Override
	public void updateRmToConfirmCancelList(HttpServletRequest req, List<BgabGascrm01Dto> dtoList) {
		roomsMealsDao.updateRmToConfirmCancelList(dtoList);
	}
	@Override
	public BgabGascrm01Dto selectInfoRmToRequestForAprv(BgabGascrm01Dto dto) {
		return roomsMealsDao.selectInfoRmToRequestForAprv(dto);
	}
	@Override
	public int updateInfoRmToReject(BgabGascrm01Dto dto, HttpServletRequest req) throws SessionException{
		return roomsMealsDao.updateInfoRmToReject(dto);
	}
}
