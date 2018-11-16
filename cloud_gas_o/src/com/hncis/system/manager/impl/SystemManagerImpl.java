
package com.hncis.system.manager.impl;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.hncis.common.Constant;
import com.hncis.common.application.ApprovalGasc;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.BpmApiUtil;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.SHA256Util;
import com.hncis.common.util.SendMail;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.BgabGascz003Dto;
import com.hncis.common.vo.BgabGascz005Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.system.dao.SystemDao;
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
import com.hncis.system.vo.BgabGascz031Dto;
import com.hncis.system.vo.BgabGascz033Dto;
import com.hncis.system.vo.BgabGascz035Dto;
import com.hncis.system.vo.DashBoard;
import com.hncis.system.vo.TableInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service("systemManagerImpl")
public class SystemManagerImpl  implements SystemManager{
    private transient Log logger = LogFactory.getLog(getClass());

	@Autowired
	public SystemDao systemDao;

	@Override
	public List<BgabGascz004Dto> getSelectMenuListToUserManagement(BgabGascz002Dto bgabGascz002Dto){
		return systemDao.getSelectMenuListToUserManagement(bgabGascz002Dto);
	}

	@Override
	public int updateUserInfoToUserManagement(BgabGascz002Dto bgabGascz002Dto){
		return systemDao.updateUserInfoToUserManagement(bgabGascz002Dto);
	}

	@Override
	public int getSelectMenuCountToMenuManagement(BgabGascz004Dto bgabGascMenu){
		return systemDao.getSelectMenuCountToMenuManagement(bgabGascMenu);
	}

	@Override
	public List<BgabGascz004Dto> getSelectMenuListToMenuManagement(BgabGascz004Dto bgabGascMenu){
		return systemDao.getSelectMenuListToMenuManagement(bgabGascMenu);
	}

	@Override
	public int saveListToMenuManagement(List<BgabGascz004Dto> iBgabGascMenu, List<BgabGascz004Dto> uBgabGascMenu){
		int iCnt = systemDao.insertListToMenuManagement(iBgabGascMenu);
		int uCnt = systemDao.updateListToMenuManagement(uBgabGascMenu);

		return iCnt + uCnt;
	}

	@Override
	public int updateListToMenuManagement(List<BgabGascz004Dto> bgabGascMenu){
		return systemDao.updateListToMenuManagement(bgabGascMenu);
	}

	@Override
	public int deleteListToMenuManagement(List<BgabGascz004Dto> bgabGascMenu){
		return systemDao.deleteListToMenuManagement(bgabGascMenu);
	}

	@Override
	public List<TableInfo> getSelectTableListToTableInformation(TableInfo tableInfo){
		return systemDao.getSelectTableListToTableInformation(tableInfo);
	}
	@Override
	public List<CommonCode> getSelectMenuToComboList(CommonCode code){
		return systemDao.getSelectMenuToComboList(code);
	}

	@Override
	public List<TableInfo> getSelectAttributeListToTableInformation(TableInfo tableInfo){
		return systemDao.getSelectAttributeListToTableInformation(tableInfo);
	}

	@Override
	public List<TableInfo> getSelectIndexListToTableInformation(TableInfo tableInfo){
		return systemDao.getSelectIndexListToTableInformation(tableInfo);
	}

	@Override
	public List<BatchInfo> getSelectListToBatchManagement(BatchInfo batchInfo){
		return systemDao.getSelectListToBatchManagement(batchInfo);
	}

	@Override
	public List<BatchInfo> getSelectParameterListToBatchManagement(BatchInfo batchInfo){
		return systemDao.getSelectParameterListToBatchManagement(batchInfo);
	}
	@Override
	public int getSelectCountToDeptChangeManagement(BgabGascz006Dto vo){
		return systemDao.getSelectCountToDeptChangeManagement(vo);
	}
	@Override
	public List<BgabGascz006Dto> getSelectListToDeptChangeManagement(BgabGascz006Dto vo){
		return systemDao.getSelectListToDeptChangeManagement(vo);
	}
	@Override
	public int insertToDeptChangeManagement(List<BgabGascz006Dto> iList, List<BgabGascz006Dto> uList){
		int iCnt = systemDao.insertToDeptChangeManagement(iList);
		int uCnt = systemDao.updateToDeptChangeManagement(uList);

		if(iCnt > 0){
			systemDao.updateToInsaDeptChangeManagement(iList);
		}

		if(uCnt > 0){
			systemDao.updateToInsaDeptChangeManagement(uList);
		}

		return iCnt+uCnt;
	}
	@Override
	public int updateToDeptChangeManagement(List<BgabGascz006Dto> list){
		return systemDao.updateToDeptChangeManagement(list);
	}
	@Override
	public int deleteToDeptChangeManagement(List<BgabGascz006Dto> list){
		return systemDao.deleteToDeptChangeManagement(list);
	}
	@Override
	public int updateBatchInfoToBatchManagement(List<BatchInfo> list){
		return systemDao.updateBatchInfoToBatchManagement(list);
	}
	@Override
	public int updateParameterToBatchManagement(List<BatchInfo> list){
		return systemDao.updateParameterToBatchManagement(list);
	}
	@Override
	public int updateBatchInfoToBatchManagementByExecute(List<BatchInfo> list){
		return systemDao.updateBatchInfoToBatchManagementByExecute(list);
	}

	@Override
	public int getSelectCountToManagerManagement(BgabGascz007Dto vo){
		return systemDao.getSelectCountToManagerManagement(vo);
	}
	@Override
	public List<BgabGascz007Dto> getSelectListToManagerManagement(BgabGascz007Dto vo){
		return systemDao.getSelectListToManagerManagement(vo);
	}
	@Override
	public int getSelectCountToBatchProcessResult(BatchProcess vo){
		return systemDao.getSelectCountToBatchProcessResult(vo);
	}
	@Override
	public List<BatchProcess> getSelectListToBatchProcessResult(BatchProcess vo){
		return systemDao.getSelectListToBatchProcessResult(vo);
	}
	@Override
	public int saveToManagerManagement(List<BgabGascz007Dto> iList, List<BgabGascz007Dto> uList){

		int iCnt = systemDao.insertToManagerManagement(iList);
		int uCnt = systemDao.updateToManagerManagement(uList);

		return iCnt+uCnt;
	}
	@Override
	public String getSelectInfoToManagerManagementAuth(String xdsmEmpno){
		return systemDao.getSelectInfoToManagerManagementAuth(xdsmEmpno);
	}

	@Override
	public int updateToManagerManagement(List<BgabGascz007Dto> list){
		return systemDao.updateToManagerManagement(list);
	}
	@Override
	public int deleteToManagerManagement(List<BgabGascz007Dto> list){
		return systemDao.deleteToManagerManagement(list);
	}

	@Override
	public List<BgabGasckcalDto> getSelectListToCalendarManagement(BgabGasckcalDto dto){

		List<BgabGasckcalDto> selectList = systemDao.getSelectListToCalendarManagement(dto);
		try{


		String [] day_name = {"", "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" };

		if(selectList.size() > 0){
			for (int i=0; i<selectList.size(); i++){
					selectList.get(i).setPlnt_adsc_ymd_h(selectList.get(i).getPlnt_adsc_ymd());
					selectList.get(i).setPlnt_adsc_ymd(StringUtil.fixNumericLength(2,Integer.toString(i+1)));
					selectList.get(i).setDow_cd(day_name[CurrentDateTime.getDayOfWeek(dto.getPlnt_adsc_ymd()+selectList.get(i).getPlnt_adsc_ymd())]);
			}
		}
		else{
			int maxDay=CurrentDateTime.getMaxday(dto.getPlnt_adsc_ymd());

			System.out.println("maxDay:::::::::::::"+maxDay);
			for (int i=0; i<maxDay; i++){
				BgabGasckcalDto calDto = new BgabGasckcalDto();
				calDto.setOdu_regn_cd(dto.getOdu_regn_cd());
				calDto.setPlnt_adsc_ymd(StringUtil.fixNumericLength(2,Integer.toString(i+1)));
				calDto.setPlnt_adsc_ymd_h(dto.getPlnt_adsc_ymd()+StringUtil.fixNumericLength(2,Integer.toString(i+1)));
				//sunday : 1, monday : 2, saturday : 7
				int intWeek = CurrentDateTime.getDayOfWeek(dto.getPlnt_adsc_ymd()+calDto.getPlnt_adsc_ymd());
				calDto.setDow_cd(day_name[CurrentDateTime.getDayOfWeek(dto.getPlnt_adsc_ymd()+calDto.getPlnt_adsc_ymd())]);
				if(intWeek==1 || intWeek==7 ){
					calDto.setOdu_dd_yn("1");
				}
				else{
					calDto.setOdu_dd_yn("0");
				}
				calDto.setIpe_eeno(dto.getIpe_eeno());
				calDto.setUpdr_eeno(dto.getUpdr_eeno());

				selectList.add(calDto);

			}


		}
		}catch(Exception e){
			e.printStackTrace();
		}

		return selectList;
	}
	@Override
	public int getSelectCountToCalendarManagement(BgabGasckcalDto vo){
		return systemDao.getSelectCountToCalendarManagement(vo);
	}


	/**
	 * Insert days of month
	 * @param dto
	 * @param batch, parameter
	 * @return
	 */

	@Override
	public int insertToCalendarManagement(List<BgabGasckcalDto> list){

		for (int i=0; i<list.size(); i++){
			//sunday : 1, monday : 2, saturday : 7
			int intWeek = CurrentDateTime.getDayOfWeek(list.get(i).getPlnt_adsc_ymd());
			list.get(i).setDow_cd(Integer.toString(intWeek));
		}

		int cnt = systemDao.insertToCalendarManagement(list);
		return cnt;
	}


	@Override
	public int updateToCalendarManagement(List<BgabGasckcalDto> list){
		return systemDao.updateToCalendarManagement(list);
	}

	@Override
	public int getSelectCountToMyApproval(CommonApproval vo){
		return systemDao.getSelectCountToMyApproval(vo);
	}
	@Override
	public CommonApproval doApproveToMyApproval(List<CommonApproval> approvalList, HttpServletRequest req) throws SessionException{

		CommonApproval appInfo = null;

		CommonApproval commonApproval = null;

		for(int i = 0 ; i < approvalList.size() ; i++){
			appInfo = new CommonApproval();
			appInfo.setIf_id(approvalList.get(i).getIf_id());					// 결재번호
			appInfo.setResult(approvalList.get(i).getResult());					// 승인(Z)/반송(R)
			appInfo.setReturn_detail(approvalList.get(i).getReturn_detail());	// 반송사유
			appInfo.setTot_level(approvalList.get(i).getTot_level());			// 전체 LEVEL
			appInfo.setLevel_index(approvalList.get(i).getLevel_index());		// 결재 INDEX
			appInfo.setTable_name(approvalList.get(i).getTable_name());			// 업무 테이블이름
			appInfo.setRdcs_eeno(approvalList.get(i).getRdcs_eeno());			// 결재자 사번
			appInfo.setRpts_dept(approvalList.get(i).getRpts_dept());			// 상신자 부서
			appInfo.setTitle_nm(approvalList.get(i).getSystem_name());			// 업무이름
			appInfo.setRpts_eeno(approvalList.get(i).getRpts_eeno());			// 상신자정보
			appInfo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
			commonApproval = ApprovalGasc.setApprovalProcess(appInfo,req);

			if(commonApproval.getResult().equals("E")){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				break;
			}
		}

		return commonApproval;
	}
	@Override
	public List<CommonApproval> getSelectListToMyApproval(CommonApproval vo){
		return systemDao.getSelectListToMyApproval(vo);
	}
	@Override
	public int updateToMyApprovalForDepute(BgabGascz008Dto vo){

		int cnt = systemDao.updateToMyApprovalForDepute(vo);

		if(cnt == 1){
			int rstCnt = systemDao.updateToMyApprovalForDeputeByApprovalInfo(vo);
		}
		else{
			cnt=0;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return cnt;
	}
	@Override
	public int updateToMyApprovalForDeputeByRestore(BgabGascz008Dto vo){

		String empno = systemDao.getSelectToMyApprovalForDepute(vo);
		int cnt = 0;
		if(empno != null){
			cnt = systemDao.updateToMyApprovalForDeputeByRestore(vo);

			if(cnt == 1){
				vo.setEmpno(vo.getEmpno_org());
				vo.setEmpno_org(empno);

				int rstCnt = systemDao.updateToMyApprovalForDeputeByApprovalInfo(vo);
			}
			else{
				cnt=0;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
		}
		else{
			cnt=0;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		return cnt;
	}
	@Override
	public BgabGascz008Dto getSelectToMyApprovalUserInfo(BgabGascz008Dto vo){
		return systemDao.getSelectToMyApprovalUserInfo(vo);
	}

	@Override
	public int getSelectCountReaderManagement(BgabGascz008Dto dto){
		return systemDao.getSelectCountReaderManagement(dto);
	}
	@Override
	public List<BgabGascz008Dto> getSelectReaderManagement(BgabGascz008Dto dto){
		return systemDao.getSelectReaderManagement(dto);
	}

	@Override
	public int updateReaderManagement(List<BgabGascz008Dto> dtoList){

		int cnt = 0;
		for(int i = 0 ; i < dtoList.size(); i++){
			cnt += systemDao.updateReaderManagement(dtoList.get(i));

			System.out.println("empno_org="+dtoList.get(i).getEmpno_org());

			//if(StringUtil.isNullToString(dtoList.get(i).getSubt_flag()).equals("Y")){
				systemDao.updateToMyApprovalForDeputeByApprovalInfo(dtoList.get(i));
				systemDao.updateToMyApprovalForDeputeByApprovalInfoDetail(dtoList.get(i));
			//}
		}
		return cnt;
	}

	@Override
	public int getSelectCountCodeManagement(BgabGascz005Dto vo){
		return systemDao.getSelectCountCodeManagement(vo);
	}

	@Override
	public List<BgabGascz005Dto> getSelectCodeManagement(BgabGascz005Dto dto){
		return systemDao.getSelectCodeManagement(dto);
	}

	@Override
	public int insertListToCodeManagement(List<BgabGascz005Dto> dtoI, List<BgabGascz005Dto> dtoU){

		int iCnt = systemDao.insertListToCodeManagement(dtoI);
		int uCnt = systemDao.updateListToCodeManagement(dtoU);

		return iCnt+uCnt;
	}
	@Override
	public int updateListToCodeManagement(List<BgabGascz005Dto> dto){
		return systemDao.updateListToCodeManagement(dto);
	}
	@Override
	public int deleteListToCodeManagement(List<BgabGascz005Dto> dto){
		return systemDao.deleteListToCodeManagement(dto);
	}
	@Override
	public int updateToBackgroundImage(BgabGascz005Dto vo){
		return systemDao.updateToBackgroundImage(vo);
	}

	@Override
	public int updateToBackgroundImageApply(BgabGascz005Dto vo){
		//다른 이미지들은 apply N으로 지정
		systemDao.updateToBackgroundImageApplyN(vo);
		//선택된 이미지는 apply를 Y로 지정
		return systemDao.updateToBackgroundImageApplyY(vo);
	}


	@Override
	public List<com.hncis.system.vo.DashBoard> getSelectChartToDashBoardEm(DashBoard code) {
		return systemDao.getSelectChartToDashBoardEm(code);
	}
	@Override
	public List<DashBoard> getSelectChartToDashBoardBt(DashBoard code) {
		return systemDao.getSelectChartToDashBoardBt(code);
	}
	@Override
	public List<DashBoard> getSelectChartToDashBoardBv(DashBoard code) {
		return systemDao.getSelectChartToDashBoardBv(code);
	}
	@Override
	public List<DashBoard> getSelectChartToDashBoardPs(DashBoard code) {
		return systemDao.getSelectChartToDashBoardPs(code);
	}

	@Override
	public int getSelectCountBackgroundImageAll(BgabGascz005Dto vo) {
		return Integer.parseInt(systemDao.getSelectCountBackgroundImageAll(vo));
	}

	@Override
	public List<BgabGascz005Dto> getSelectBackgroundImageAll(BgabGascz005Dto vo) {
		List<BgabGascz005Dto> gascz005DtosTemp = systemDao.getSelectBackgroundImageAll(vo);

		int nSize = gascz005DtosTemp.size();
		int nSizeNewList = nSize/3+1;
		List<BgabGascz005Dto> gascz005Dtos = new ArrayList<BgabGascz005Dto>(nSize/3+1);

		try {
			int j = 0;
			int k = 0;
			int l = 0;

			for(int ii = 0 ; ii < nSizeNewList ; ii++){
				gascz005Dtos.add(ii, new BgabGascz005Dto());
			}

			for(int i = 0 ; i < nSize ; i++){

				BgabGascz005Dto tempValue = gascz005DtosTemp.get(i);
				String sHname    = tempValue.getXcod_hname();
				String sCode     = tempValue.getXcod_code();
				String sAplyFlag = tempValue.getXcod_aply_flag();

				if(i % 3 == 0){
					gascz005Dtos.get(j).setName_1(sHname);
					gascz005Dtos.get(j).setCode_1(sCode);
					gascz005Dtos.get(j).setAply_flag_1(sAplyFlag);
					j++;
				}
				else if(i % 3 == 1){
					gascz005Dtos.get(k).setName_2(sHname);
					gascz005Dtos.get(k).setCode_2(sCode);
					gascz005Dtos.get(k).setAply_flag_2(sAplyFlag);
					k++;
				}else if(i % 3 == 2){
					gascz005Dtos.get(l).setName_3(sHname);
					gascz005Dtos.get(l).setCode_3(sCode);
					gascz005Dtos.get(l).setAply_flag_3(sAplyFlag);
					l++;
				}

			}//end for
		} catch (Exception e) {
			e.printStackTrace();
		}

		return gascz005Dtos;
	}

	@Override
	public int insertToBackgroundImage(BgabGascz005Dto vo, HttpServletRequest req, HttpServletResponse res) {
		String[] paramVal = new String[4];
		String[] result   = new String[4];

		paramVal[0] = "xpc_fname";
		paramVal[1] = "old_xpc_fname";
		paramVal[2] = "images/skin/";

		System.out.println("Debug01_01");

		result = FileUtil.uploadFileBackgroundImage(req, res, paramVal);

		System.out.println("Debug01_02");

		if(result != null){
			vo.setXcod_hname( result[0] );
			return systemDao.insertToBackgroundImage(vo);
		}else{
			return 0;
		}
	}

	@Override
	public int deleteToBackgroundImageApply(BgabGascz005Dto vo) {
		return systemDao.deleteToBackgroundImage(vo);
	}

	@Override
	public List<BgabGascz005Dto> getSelectSwitchToBudgetCheckList(){
		return systemDao.getSelectSwitchToBudgetCheckList();
	}

	@Override
	public int saveSwitchToBudgetCheck(List<BgabGascz005Dto> bgabGascz005Dto){
		return systemDao.saveSwitchToBudgetCheck(bgabGascz005Dto);
	}

	@Override
	public int getSelectCountCoordiManagement(BgabGascz003Dto paramDto) {
		return systemDao.getSelectCountCoordiManagement(paramDto);
	}

	@Override
	public List<BgabGascz003Dto> getSelectCoordiManagement(BgabGascz003Dto paramDto) {
		return systemDao.getSelectCoordiManagement(paramDto);
	}

	@Override
	public CommonMessage updateCoordiManagement(List<BgabGascz003Dto> list, HttpServletRequest req){

		CommonMessage message = new CommonMessage();
		boolean errYn = true;

		for(int i = 0 ; i < list.size() ; i++){

			String crdId = StringUtil.isNullToString(list.get(i).getXorg_crd_i());
			String crdId_h = StringUtil.isNullToString(list.get(i).getXorg_crd_i_h());

			String saveMode = "";

			if(crdId.length() == 8 && crdId_h.length()== 8){
				saveMode = "U";
			}else if(crdId.length()== 8 && crdId_h.length()== 0){
				saveMode = "I";
			}else if(crdId.length()== 0 && crdId_h.length()== 8){
				saveMode = "D";
			}else if(crdId.length()== 0 && crdId_h.length()== 0){
				saveMode = "N";
			}else{
				saveMode = "N";
			}

			System.out.println("=======saveMode:"+saveMode);

			try{

				if(saveMode.equals("U")){
					int crdAnt = systemDao.getSelectCountCoordiDept(list.get(i).getXorg_orga_c()+"C");

					//z008 테이블 코디레벨 존재
					if(crdAnt == 1){

						//코디 조회
						BgabGascz008Dto coordInfo = systemDao.getSelectDeptInfoApp(list.get(i).getXorg_orga_c()+"C");

						//z008 테이블 코디정보 수정
						int cnt1 = systemDao.updateCoordiInfoApp(list.get(i));

						//z003 테이블 코디정보 수정
						int cnt2 = systemDao.updateCoordiManagement(list.get(i));

						//결재테이블 코디정보수정
						BgabGascz008Dto dto = new BgabGascz008Dto();

						dto.setEmpno(list.get(i).getXorg_crd_i());
						dto.setEmpno_org(coordInfo.getEmpno_org());
						dto.setOrga_c(coordInfo.getOrga_c());

						systemDao.updateToMyApprovalForDeputeByApprovalInfo(dto);
						systemDao.updateToMyApprovalForDeputeByApprovalInfoDetail(dto);

					}
					else{//코디정보 없음

						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						message.setCode1("N");
						message.setMessage("Coordinator information does not exist, Please ask system administrator.");
						errYn = false;
						break;

					}

				}else if(saveMode.equals("I")){
					int crdAnt = systemDao.getSelectCountCoordiDept(list.get(i).getXorg_orga_c()+"C");
					//z008 테이블 코디레벨 미존재
					if(crdAnt == 0){

						//HOD 부서 조회
						BgabGascz008Dto dto = systemDao.getSelectDeptInfoApp(list.get(i).getXorg_orga_c());
						//z008 HOD 부서 UPDATE => 상위부서 ID를 코드부서로 변경
						int cnt1 = systemDao.updateCoordiInfoAppForInsert(list.get(i));
						//z008 코디부서 INSERT => 상위부서 ID를 기존 HOD부서 상위부서 ID 적용

						dto.setOrga_c(dto.getOrga_c()+"C");
						dto.setOrga_e(dto.getOrga_e()+" Coordi");
						dto.setEmpno(list.get(i).getXorg_crd_i());
						dto.setName(list.get(i).getXorg_crd_m());
						dto.setLevl_c("4");
						dto.setIpe_eeno(SessionInfo.getSess_empno(req));
						dto.setUpdr_eeno(SessionInfo.getSess_empno(req));
						dto.setEmpno_org(list.get(i).getXorg_crd_i());

						int cnt2 = systemDao.insertCoordiInfoApp(dto);

						//z003 테이블 코디정보 수정
						int cnt3 = systemDao.updateCoordiManagement(list.get(i));

					}
					else{//코디정보 이미존재

						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						message.setCode1("N");
						message.setMessage("Coordinator information already exist.");
						errYn = false;
						break;
					}

				}else if(saveMode.equals("D")){
					int crdAnt = systemDao.getSelectCountCoordiDept(list.get(i).getXorg_orga_c()+"C");
					//z008 테이블 코디레벨 존재
					if(crdAnt == 1){

						//코디 부서 조회
						BgabGascz008Dto dto = systemDao.getSelectDeptInfoApp(list.get(i).getXorg_orga_c()+"C");

						dto.setOrga_c(list.get(i).getXorg_orga_c());

						// HOD 부서 UPDATE => 상위부서 ID를 기존 코디부서 상위부서 ID로 변경
						int cnt1 = systemDao.updateCoordiInfoAppForDelete(dto);
						//z008 코디부서 DELETE
						int cnt2 = systemDao.deleteCoordiInfoApp(list.get(i));

						//z003 테이블 코디정보 수정
						int cnt3 = systemDao.updateCoordiManagement(list.get(i));

					}
					else{//코디정보 없음

						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						message.setCode1("N");
						message.setMessage("Coordinator information does not exist, Please ask system administrator.");
						errYn = false;
						break;
					}
				}

			}catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				message.setCode1("N");
				message.setMessage(HncisMessageSource.getMessage("ERROR.0000"));
				errYn = false;
				break;
			}

		}

		if(errYn){
			message.setCode1("Y");
			message.setMessage(HncisMessageSource.getMessage("SAVE.0000"));
		}


		return message;
	}


	@Override
	public int selectCountToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto){
		return systemDao.selectCountToApproveLevelManagement(bgabGascz013Dto);
	}
	@Override
	public List<BgabGascz013Dto> selectListToApproveLevelManagement(BgabGascz013Dto bgabGascz013Dto){
		return systemDao.selectListToApproveLevelManagement(bgabGascz013Dto);
	}
	@Override
	public int saveListToApproveLevelManagement(List<BgabGascz013Dto> iList, List<BgabGascz013Dto> uList){
		int iCnt = systemDao.insertListToApproveLevelManagement(iList);
		int uCnt = systemDao.updateListToApproveLevelManagement(uList);

		return iCnt + uCnt;
	}
	@Override
	public int deleteListToApproveLevelManagement(List<BgabGascz013Dto> dList){
		return systemDao.deleteListToApproveLevelManagement(dList);
	}

	/**
	 * vendor management.
	 */
	@Override
	public int getSelectCountVendorManagement(BgabGascz014Dto param){
		return systemDao.getSelectCountVendorManagement(param);
	}
	@Override
	public List<BgabGascz014Dto> getSelectVendorManagement(BgabGascz014Dto param){
		return systemDao.getSelectVendorManagement(param);
	}
	@Override
	public int insertListToVendorManagement(List<BgabGascz014Dto> dtoI, List<BgabGascz014Dto> dtoU){
		String flag = "Y";
		for(int i=0; i<dtoI.size(); i++){
			int chk = systemDao.selectToVendorManagementData(dtoI.get(i));
			if(chk > 0){
				flag = "N";
			}
		}
		for(int i=0; i<dtoU.size(); i++){
			if(!dtoU.get(i).getVendor_code().equals(dtoU.get(i).getOld_vendor_code())){
				int chk = systemDao.selectToVendorManagementData(dtoU.get(i));
				if(chk > 0){
					flag = "N";
				}
			}
		}
		int iCnt = 0, uCnt = 0;
		if("Y".equals(flag)){
			iCnt = systemDao.insertListToVendorManagement(dtoI);
			uCnt = systemDao.updateListToVendorManagement(dtoU);
		}

		return iCnt+uCnt;
	}
	@Override
	public int deleteListToVendorManagement(List<BgabGascz014Dto> dto){
		return systemDao.deleteListToVendorManagement(dto);
	}

	/**
	 * PurchaseOrder management.
	 */
	@Override
	public int getSelectCountPurchaseOrderManagement(BgabGascz015Dto param){
		return systemDao.getSelectCountPurchaseOrderManagement(param);
	}
	@Override
	public List<BgabGascz015Dto> getSelectPurchaseOrderManagement(BgabGascz015Dto param){
		return systemDao.getSelectPurchaseOrderManagement(param);
	}
	@Override
	public int insertListToPurchaseOrderManagement(List<BgabGascz015Dto> dtoI, List<BgabGascz015Dto> dtoU){

		int iCnt = systemDao.insertListToPurchaseOrderManagement(dtoI);
		int uCnt = systemDao.updateListToPurchaseOrderManagement(dtoU);

		return iCnt+uCnt;
	}
	@Override
	public int deleteListToPurchaseOrderManagement(List<BgabGascz015Dto> dto){
		return systemDao.deleteListToPurchaseOrderManagement(dto);
	}
	@Override
	public List<CommonCode> getSelectPurchaseOrderCombo(CommonCode code){
		return systemDao.getSelectPurchaseOrderCombo(code);
	}

	@Override
	public List<BgabGascz019Dto> doSearchByDateMenu(BgabGascz019Dto param){
		return systemDao.doSearchByDateMenu(param);
	}

	@Override
	public List<BgabGascz019Dto> doSearchByBrasilanMenu(BgabGascz019Dto param){
		return systemDao.doSearchByBrasilanMenu(param);
	}

	@Override
	public List<BgabGascz019Dto> doSearchByKoreanMenu(BgabGascz019Dto param){
		return systemDao.doSearchByKoreanMenu(param);
	}

	@Override
	public List<BgabGascz019Dto> doSearchByCoffee(BgabGascz019Dto param){
		return systemDao.doSearchByCoffee(param);
	}

	@Override
	public int doInsertByBrasilanMenu(List<BgabGascz019Dto> gsSaveVo, List<BgabGascz019Dto> gsModifyVo){
		int iCnt = systemDao.doInsertByBrasilanMenu(gsSaveVo);
		int uCnt = systemDao.doUpdateByBrasilanMenu(gsModifyVo);
		return iCnt+uCnt;
	}

	@Override
	public int doInsertByKoreanMenu(List<BgabGascz019Dto> gsSaveVo, List<BgabGascz019Dto> gsModifyVo){
		int iCnt = systemDao.doInsertByKoreanMenu(gsSaveVo);
		int uCnt = systemDao.doUpdateByKoreanMenu(gsModifyVo);
		return iCnt+uCnt;
	}

	@Override
	public int doInsertByCoffee(List<BgabGascz019Dto> gsSaveVo, List<BgabGascz019Dto> gsModifyVo){
		int iCnt = systemDao.doInsertByCoffee(gsSaveVo);
		int uCnt = systemDao.doUpdateByCoffee(gsModifyVo);
		return iCnt+uCnt;
	}

	@Override
	public int doDeleteByBrasilanMenu(List<BgabGascz019Dto> gsDelVo){
		int dCnt = systemDao.doDeleteByBrasilanMenu(gsDelVo);
		return dCnt;
	}

	@Override
	public int doDeleteByKoreanMenu(List<BgabGascz019Dto> gsDelVo){
		int dCnt = systemDao.doDeleteByKoreanMenu(gsDelVo);
		return dCnt;
	}

	@Override
	public int doDeleteByCoffee(List<BgabGascz019Dto> gsDelVo){
		int dCnt = systemDao.doDeleteByCoffee(gsDelVo);
		return dCnt;
	}

	@Override
	public List<CommonCode> getSelectVendorCodeCombo(CommonCode code){
		return systemDao.getSelectVendorCodeCombo(code);
	}

	/**
	 * Material management.
	 */
	@Override
	public int getSelectCountMaterialManagement(BgabGascz016Dto param){
		return systemDao.getSelectCountMaterialManagement(param);
	}
	@Override
	public List<BgabGascz016Dto> getSelectMaterialManagement(BgabGascz016Dto param){
		return systemDao.getSelectMaterialManagement(param);
	}
	@Override
	public int insertListToMaterialManagement(List<BgabGascz016Dto> dtoI, List<BgabGascz016Dto> dtoU){
		String flag = "Y";
		for(int i=0; i<dtoI.size(); i++){
			int chk = systemDao.selectToMaterialManagementData(dtoI.get(i));
			if(chk > 0){
				flag = "N";
			}
		}
		for(int i=0; i<dtoU.size(); i++){
			if(!dtoU.get(i).getMaterial_code().equals(dtoU.get(i).getOld_material_code())){
				int chk = systemDao.selectToMaterialManagementData(dtoU.get(i));
				if(chk > 0){
					flag = "N";
				}
			}
		}
		int iCnt = 0, uCnt = 0;
		if("Y".equals(flag)){
			iCnt = systemDao.insertListToMaterialManagement(dtoI);
			uCnt = systemDao.updateListToMaterialManagement(dtoU);
		}

		return iCnt+uCnt;
	}
	@Override
	public int deleteListToMaterialManagement(List<BgabGascz016Dto> dto){
		return systemDao.deleteListToMaterialManagement(dto);
	}

	@Override
	public BgabGascz002Dto getSelectUserInfoDetailPopup(BgabGascz002Dto bgabGascz002Dto){
		return systemDao.getSelectUserInfoDetailPopup(bgabGascz002Dto);
	}
	@Override
	public int selectXst30InfoListCount(BgabGascz030Dto dto){
		return systemDao.selectXst30InfoListCount(dto);
	}

	@Override
	public List<BgabGascz030Dto> selectXst30InfoList(BgabGascz030Dto dto){
		return systemDao.selectXst30InfoList(dto);
	}
	
	@Override
	public BgabGascz030Dto selectMenuInfo(BgabGascz030Dto dto){
		return systemDao.selectMenuInfo(dto);
	}

	@Override
	public CommonMessage doUpdateXst30IncomeCheck(List<BgabGascz030Dto> list, HttpServletRequest req){
		CommonMessage message = new CommonMessage();
		for(int i = 0 ; i < list.size() ; i++){

			try{
				int cnt = systemDao.updateXst30PgsStCd(list.get(i));
				if(cnt > 0){
					message.setCode1("Y");
					message.setMessage(HncisMessageSource.getMessage("INCOMECHECK.0000"));
				}
				else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					message.setCode1("N");
					message.setMessage(HncisMessageSource.getMessage("INCOMECHECK.0001"));
				}
			}catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				message.setCode1("N");
				message.setMessage(HncisMessageSource.getMessage("INCOMECHECK.0001"));
			}
		}
		return message;
	}

	@Override
	public CommonMessage doUpdateXst30Confirm(List<BgabGascz030Dto> list, HttpServletRequest req){
		CommonMessage message = new CommonMessage();

		for(int i = 0 ; i < list.size() ; i++){

			try{
				BgabGascz030Dto inputDto = list.get(i);
				inputDto.setIpe_eeno(SessionInfo.getSess_empno(req));
				inputDto.setUpdr_eeno(SessionInfo.getSess_empno(req));

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("corp_cd", inputDto.getCorp_cd());
				map.put("locale", inputDto.getLocale());

				// 인사테이블 생성
				if(this.createBgabGascz002(map)){
					//인사 테이블 생성후 생성된 인사테이블에 admin 계정 넣어줌
					BgabGascz002Dto insaDto = new BgabGascz002Dto();
					insaDto.setCorp_cd(inputDto.getCorp_cd());
					insaDto.setXusr_empno("admin");
					if(inputDto.getLocale().equals("en")){
						insaDto.setXusr_name("admin");
					}else if(inputDto.getLocale().equals("zh")){
						insaDto.setXusr_name("维护者");
					}else {
						insaDto.setXusr_name("관리자");	
					}
					insaDto.setXusr_pswd(SHA256Util.getEncrypt("admin", "admin"));
					insaDto.setXusr_retr_flag("N");
					insaDto.setXusr_sex("1");
					insaDto.setXusr_auth_knd("M");
					insaDto.setXusr_cnfm_auth("1");
					insaDto.setLocale(inputDto.getLocale());
					insaDto.setXusr_work_auth("44444444444444444444444444444444444444444444444444");
					try {
						systemDao.insertUserInfo(insaDto);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// 부서테이블 생성
				this.createBgabGascz003(map);
				// 코드테이블 생성
				this.createBgabGascz005(map);
				// 결제관련
				this.createBgabGascz007(map);
				// 결제관련
				this.createBgabGascz008(map);
				// 결제마스터테이블 생성
				this.createBgabGascz009(map);
				// 결제슬레이브테이블 생성
				this.createBgabGascz010(map);
				// 파일테이블 생성
				this.createBgabGascz011(map);
				// 픽업파일테이블 생성
				this.createBgabGascz012(map);
				// 업체별 결재관리
				this.createBgabGascz013(map);
//				// 결제사용여부 테이블 생성
//				this.createBgabGascz031(map);
				// 게시판테이블 생성
				this.createBgabGascBD(map);
				
				// 게시판테이블 생성
				this.createBgabGascCal(map);
				// 배경화면테이블 생성
				this.createBgabGascz032(map);

				if("Y".equals(inputDto.getTask01())){			//휴양소
					if(this.createBgabGascRC01(map)){
						this.createBgabGascRC02(map);
						this.createBgabGascRC03(map);
						this.createBgabGascRC04(map);
						this.createBgabGascRC05(map);
						this.createBgabGascRC06(map);
						
						systemDao.insertBgabGascrc01(map);
						systemDao.insertBgabGascrc02(map);
						systemDao.insertBgabGascrc03(map);
						systemDao.insertBgabGascrc04(map);
						systemDao.insertBgabGascrc05(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("001");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask01());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("001");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask02())){	//근무복
					if(this.createBgabGascAF01(map)){
						this.createBgabGascAF02(map);
						this.createBgabGascAF03(map);
						this.createBgabGascAF04(map);
						this.createBgabGascAF05(map);
						
						systemDao.insertBgabGascaf02(map);
						systemDao.insertBgabGascaf03(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("002");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask02());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("002");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask03())){	//선물
					if(this.createBgabGascGF01(map)){
						this.createBgabGascGF02(map);
						this.createBgabGascGF03(map);
						this.createBgabGascGF04(map);
						this.createBgabGascGF05(map);
						this.createBgabGascGFExcelTemp(map);
						
						systemDao.insertBgabGascgf01(map);
						systemDao.insertBgabGascgf03(map);
						systemDao.insertBgabGascgf04(map);
						systemDao.insertBgabGascgf05(map);
						
						HashMap<String, String> fMap = new HashMap<String, String>();
						fMap.put("corp_cd", inputDto.getCorp_cd());
						fMap.put("affr_scn_cd", "GF");
						systemDao.insertBgabGascz011(fMap);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("003");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask03());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("003");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask04())){	//도서
					if(this.createBgabGascBK01(map)){
						this.createBgabGascBK02(map);
						this.createBgabGascBK03(map);
						this.createBgabGascBK04(map);
						this.createBgabGascBKExcelTemp(map);
						
						systemDao.insertBgabGascbk01(map);
						systemDao.insertBgabGascbk03(map);
						systemDao.insertBgabGascbk04(map);
						
						HashMap<String, String> fMap = new HashMap<String, String>();
						fMap.put("corp_cd", inputDto.getCorp_cd());
						fMap.put("affr_scn_cd", "BK");
						systemDao.insertBgabGascz011(fMap);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("004");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask04());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("004");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask05())){	//교육신청
					this.createBgabGascTR01(map);

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("007");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask05());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("007");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask06())){	//명함
					if(this.createBgabGascBA01(map)){
						this.createBgabGascBA02(map);
						this.createBgabGascBA03(map);
						
						systemDao.insertBgabGascba03(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("009");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask06());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("009");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask07())){	//전산용품
					if(this.createBgabGascGS01(map)){
						this.createBgabGascGS02(map);
						this.createBgabGascGS03(map);
						this.createBgabGascGS04(map);
						
						systemDao.insertBgabGascgs02(map);
						
						HashMap<String, String> fMap = new HashMap<String, String>();
						fMap.put("corp_cd", inputDto.getCorp_cd());
						fMap.put("affr_scn_cd", "GS");
						systemDao.insertBgabGascz011(fMap);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("010");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask07());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("010");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask08())){	//사무용품
					if(this.createBgabGascOS01(map)){
						this.createBgabGascOS02(map);
						this.createBgabGascOS03(map);
						this.createBgabGascOS04(map);
						
						systemDao.insertBgabGascos02(map);
						
						HashMap<String, String> fMap = new HashMap<String, String>();
						fMap.put("corp_cd", inputDto.getCorp_cd());
						fMap.put("affr_scn_cd", "OS");
						systemDao.insertBgabGascz011(fMap);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("012");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask08());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("012");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask09())){	//출장
					if(this.createBgabGascBT01(map)){
						this.createBgabGascBT02(map);
						this.createBgabGascBT03(map);
						this.createBgabGascBT04(map);
						this.createBgabGascBT05(map);
						this.createBgabGascBT06(map);
						this.createBgabGascBT07(map);
						this.createBgabGascBT08(map);
						this.createBgabGascBT09(map);
						
						systemDao.insertBgabGascbt06(map);
						
						HashMap<String, String> fMap = new HashMap<String, String>();
						fMap.put("corp_cd", inputDto.getCorp_cd());
						fMap.put("affr_scn_cd", "BT");
						systemDao.insertBgabGascz011(fMap);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("014");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask09());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("014");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask10())){	//픽업
					if(this.createBgabGascPS01(map)){
						this.createBgabGascPS02(map);
						this.createBgabGascPS03(map);
						this.createBgabGascPS04(map);
						
						systemDao.insertBgabGascps03(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("016");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask10());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("016");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask11())){	//교통비
					if(this.createBgabGascTX01(map)){
						this.createBgabGascTX02(map);
						this.createBgabGascTX03(map);
						this.createBgabGascTX04(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("017");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask11());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("017");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask12())){	//차량신청 && 차량관리
					if(this.createBgabGascBV01(map)){
						this.createBgabGascBV02(map);
						this.createBgabGascBV03(map);
						this.createBgabGascBV04(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("018");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
//
//					ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("019");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask12());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("018");
					systemDao.updateMenuStatus(dto);
					
					dto.setTask_gubun("019");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask13())){	//주유비
					if(this.createBgabGascFC01(map)){
						this.createBgabGascFC02(map);
						
						systemDao.insertBgabGascfc02(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("020");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask13());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("020");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask14())){	//운행일지
					this.createBgabGascVL01(map);

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("021");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask14());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("021");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask15())){	//방문
					if(this.createBgabGascSE01(map)){
						this.createBgabGascSE02(map);
						this.createBgabGascSE03(map);
						this.createBgabGascSE04(map);
						this.createBgabGascSE05(map);
						this.createBgabGascSE06(map);
						this.createBgabGascSE07(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("008");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask15());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("008");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask16())){	//회의실
					if(this.createBgabGascRM01(map)){
						this.createBgabGascRM02(map);
						this.createBgabGascRM03(map);
						this.createBgabGascRM04(map);
						
						systemDao.insertBgabGascrm03(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("011");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask16());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("011");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask17())){	//증명서
					this.createBgabGascCE01(map);

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("013");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask17());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("013");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask18())){	//연차
					if(this.createBgabGascLV01(map)){
						this.createBgabGascLV02(map);
						this.createBgabGascLV03(map);
						
						systemDao.insertBgabGasclv03(map);
					}

//					BgabGascz031Dto ApprovalUseYnDto = new BgabGascz031Dto();
//					ApprovalUseYnDto.setCorp_cd(inputDto.getCorp_cd());
//					ApprovalUseYnDto.setIpe_eeno(inputDto.getIpe_eeno());
//					ApprovalUseYnDto.setUpdr_eeno(inputDto.getUpdr_eeno());
//					ApprovalUseYnDto.setAppr_use_yn("N");
//					ApprovalUseYnDto.setTask_gubun("022");
//					this.insertApprovalUseYn(ApprovalUseYnDto);
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask18());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("022");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask19())){	//통근버스
					if(this.createBgabGascSB01(map)){
						this.createBgabGascSB02(map);
						
						systemDao.insertBgabGascSb02(map);
					}
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask19());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("023");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask20())){	//경조사
					if(this.createBgabGascFJ01(map)){
						this.createBgabGascFJ02(map);
						this.createBgabGascFJ03(map);

						systemDao.insertBgabGascFj02(map);
						systemDao.insertBgabGascFj03(map);
					}
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask20());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("024");
					systemDao.updateMenuStatus(dto);
				}
				if("Y".equals(inputDto.getTask30())){	//물품지원
					if(this.createBgabGascPD01(map)){
						this.createBgabGascPD02(map);
						this.createBgabGascPD03(map);
						this.createBgabGascPD04(map);

						systemDao.insertBgabGascPd03(map);
						systemDao.insertBgabGascPd04(map);
					}
				}else{
					BgabGascz031Dto dto = new BgabGascz031Dto();
					dto.setAppr_use_yn(inputDto.getTask30());
					dto.setUpdr_eeno(inputDto.getUpdr_eeno());
					dto.setCorp_cd(inputDto.getCorp_cd());
					dto.setTask_gubun("030");
					systemDao.updateMenuStatus(dto);
				}

				int cnt = systemDao.updateXst30PgsStCd(inputDto);
				
				if("4".equals(StringUtil.isNullToString(inputDto.getBf_pgs_st_cd()))){
					systemDao.updateXst30ToApprUseFlag(inputDto);
				}
				
				if(cnt > 0){
					message.setCode1("Y");
					message.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
				}
				else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					message.setCode1("N");
					message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
				}
			}catch (Exception e) {
				e.printStackTrace();
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				message.setCode1("N");
				message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
			}
		}

		if(message.getCode1().equals("Y")){
			Locale locale = null;
			if(list.get(0).getLocale().equals("ko")){
				locale = new Locale("ko","KR");
			} else if(list.get(0).getLocale().equals("zh")){
				locale = new Locale("zh","CN");
			} else if(list.get(0).getLocale().equals("en")){
				locale = new Locale("en","US");
			} else {
				locale = new Locale("ko","KR");
			}
			
			String toEmail  = list.get(0).getReq_email();
			String fromEmail     = "hncis@hncis.co.kr";
			String text       = list.get(0).getCorp_cd()+HncisMessageSource.getMessage("MAIL.0017",locale)+" <br/><br/>";
			text+=HncisMessageSource.getMessage("MAIL.0019",locale)+"<a href='http://"+list.get(0).getCorp_cd()+".cloud-gas.com'>["+list.get(0).getCorp_cd()+".cloud-gas.com]</a><br />";
			text+=HncisMessageSource.getMessage("MAIL.0020",locale)+"<a href='http://"+list.get(0).getCorp_cd()+".cloud-gas.com:8580'>["+list.get(0).getCorp_cd()+".cloud-gas.com:8580]</a><br /><br/>";
			text+=HncisMessageSource.getMessage("MAIL.0018",locale)+"<br /><br />";
			text       += "<br/> ID : admin";
			text       += "<br/> PW : admin";
			String subject      = "GAS "+HncisMessageSource.getMessage("MAIL.0017",locale);
			SendMail oMail = new SendMail();

			boolean success = true;
			try {
				success = oMail.sendMail(toEmail, fromEmail, subject, text, 1, false);
				
				if(!success){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					message.setCode1("N");
					message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
				}else {
					String path = Constant.UPLOAD_REAL_PATH;
					/*String[] pathL = path.split("/");
					path = "";
					for(int i = 0;i<pathL.length;i++){
						if(i==pathL.length){
							path += pathL[i];
						}else{
							path += pathL[i] + "\\";
						}
					}*/
					//System.out.println("path : "+path);
					File s = new File(path+"/sample");
					File t = new File(path+"/"+list.get(0).getCorp_cd());

					FileUtil.copyFile(s,t);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		
		return message;
	}

	@Override
	public CommonMessage doUpdateXst30Reject(List<BgabGascz030Dto> list, HttpServletRequest req){
		CommonMessage message = new CommonMessage();
		for(int i = 0 ; i < list.size() ; i++){

			try{
				int cnt = systemDao.updateXst30PgsStCd(list.get(i));
				if(cnt > 0){
					message.setCode1("Y");
					message.setMessage(HncisMessageSource.getMessage("REJECT.0000"));
				}
				else{
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					message.setCode1("N");
					message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
				}
			}catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				message.setCode1("N");
				message.setMessage(HncisMessageSource.getMessage("REJECT.0001"));
			}
		}
		return message;
	}

	@Override
	public boolean createBgabGascz002(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascz002(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz002(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascz003(HashMap<String, String> map) {
		try {
			systemDao.validateBgabGascz003(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz003(map);
			return true;
		}
	}
	
	@Override
	public boolean createBgabGascz005(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascz005(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz005(map);
			systemDao.insertBgabGascz005(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascz007(HashMap<String, String> map) {
		try {
			systemDao.validateBgabGascz007(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz007(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascz008(HashMap<String, String> map) {
		try {
			systemDao.validateBgabGascz008(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz008(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascz009(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascz009(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz009(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascz010(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascz010(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz010(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascz011(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascz011(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz011(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascz012(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascz012(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz012(map);
			return true;
		}
	}
	@Override
	public boolean createBgabGascz013(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascz013(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz013(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascCal(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascCal(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascCal(map);
			return true;
		}
	}
	
	@Override
	public boolean createBgabGascz032(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascz032(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascz032(map);
			systemDao.insertBgabGascz032(map);
			return true;
		}
	}
	
	@Override
	public boolean createBgabGascRC01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRC01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRC01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascRC02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRC02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRC02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascRC03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRC03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRC03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascRC04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRC04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRC04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascRC05(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRC05(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRC05(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascRC06(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRC06(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRC06(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascAF01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascAF01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascAF01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascAF02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascAF02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascAF02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascAF03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascAF03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascAF03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascAF04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascAF04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascAF04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascAF05(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascAF05(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascAF05(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGF01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGF01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGF01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGF02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGF02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGF02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGF03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGF03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGF03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGF04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGF04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGF04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGF05(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGF05(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGF05(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGFExcelTemp(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGFExcelTemp(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGFExcelTemp(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBK01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBK01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBK01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBK02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBK02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBK02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBK03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBK03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBK03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBK04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBK04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBK04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBKExcelTemp(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBKExcelTemp(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBKExcelTemp(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascTR01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascTR01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascTR01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBA01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBA01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBA01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBA02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBA02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBA02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBA03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBA03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBA03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGS01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGS01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGS01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGS02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGS02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGS02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGS03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGS03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGS03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascGS04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascGS04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascGS04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascOS01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascOS01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascOS01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascOS02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascOS02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascOS02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascOS03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascOS03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascOS03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascOS04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascOS04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascOS04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBT01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBT01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBT01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBT02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBT02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBT02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBT03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBT03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBT03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBT04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBT04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBT04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBT05(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBT05(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBT05(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBT06(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBT06(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBT06(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBT07(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBT07(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBT07(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBT08(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBT08(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBT08(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBT09(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBT09(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBT09(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascPS01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascPS01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascPS01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascPS02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascPS02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascPS02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascPS03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascPS03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascPS03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascPS04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascPS04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascPS04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascTX01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascTX01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascTX01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascTX02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascTX02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascTX02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascTX03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascTX03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascTX03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascTX04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascTX04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascTX04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBV01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBV01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBV01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBV02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBV02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBV02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBV03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBV03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBV03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBV04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBV04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBV04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascFC01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascFC01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascFC01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascFC02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascFC02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascFC02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascVL01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascVL01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascVL01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascSE01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascSE01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascSE01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascSE02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascSE02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascSE02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascSE03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascSE03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascSE03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascSE04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascSE04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascSE04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascSE05(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascSE05(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascSE05(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascSE06(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascSE06(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascSE06(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascSE07(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascSE07(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascSE07(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascRM01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRM01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRM01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascRM02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRM02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRM02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascRM03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRM03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRM03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascRM04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascRM04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascRM04(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascCE01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascCE01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascCE01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascLV01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascLV01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascLV01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascLV02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascLV02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascLV02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascLV03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascLV03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascLV03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascBD(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBD01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascBD01(map);
			return true;
		}
	}
	
	@Override
	public boolean createBgabGascSB01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascSB01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascSB01(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascSB02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascBS02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascSB02(map);
			return true;
		}
	}
	
	@Override
	public boolean createBgabGascFJ01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascFJ01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascFJ01(map);
			return true;
		}
	}
	
	@Override
	public boolean createBgabGascFJ02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascFJ02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascFJ02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascFJ03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascFJ03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascFJ03(map);
			return true;
		}
	}	
	
	@Override
	public boolean createBgabGascPD01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascPD01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascPD01(map);
			return true;
		}
	}
	
	@Override
	public boolean createBgabGascPD02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascPD02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascPD02(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascPD03(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascPD03(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascPD03(map);
			return true;
		}
	}

	@Override
	public boolean createBgabGascPD04(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascPD04(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascPD04(map);
			return true;
		}
	}
	
	@Override
	public boolean createBgabGascCO01(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascCO01(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascCO01(map);
			return true;
		}
	}
	
	@Override
	public boolean createBgabGascCO02(HashMap<String, String> map){
		try {
			systemDao.validateBgabGascCO02(map);
			return false;
		} catch (Exception e) {
			systemDao.createBgabGascCO02(map);
			return true;
		}
	}


	@Override
	public int selectGridToDeptInfoManagementCount(BgabGascz003Dto dto) {
		return systemDao.selectGridToDeptInfoManagementCount(dto);
	}

	@Override
	public List<BgabGascz003Dto> selectGridToDeptInfoManagementList(BgabGascz003Dto dto) {
		return systemDao.selectGridToDeptInfoManagementList(dto);
	}

	@Override
	public int insertListToDeptInfoManagement(List<BgabGascz003Dto> dtoIList, List<BgabGascz003Dto> dtoUList, HttpServletRequest req) {
		
		int iCnt = 0;
		int uCnt = 0;
		
		try {
			
			iCnt = systemDao.insertListToDeptInfoManagement(dtoIList);
			uCnt = systemDao.updateListToDeptInfoManagement(dtoUList);
			
			//부서정보 저장 후 결재레벨 생성
			String corpCd = SessionInfo.getSess_corp_cd(req);
		
			BgabGascz003Dto dto = new BgabGascz003Dto();
			dto.setCorp_cd(corpCd);
			
			List<BgabGascz003Dto> list = systemDao.selectDeptInfoList(dto);
			
			List<BgabGascz008Dto> z008List = new ArrayList<BgabGascz008Dto>();
			
			for(BgabGascz003Dto z003Dto : list){
				BgabGascz008Dto bgabGascz008Dto = new BgabGascz008Dto();
				
				bgabGascz008Dto.setOrga_c(z003Dto.getXorg_orga_c());
				bgabGascz008Dto.setOrga_e(z003Dto.getXorg_orga_e());
				bgabGascz008Dto.setEmpno(z003Dto.getXorg_rsps_i());
				bgabGascz008Dto.setEmpno_org(z003Dto.getXorg_rsps_i());
				bgabGascz008Dto.setName(z003Dto.getXorg_rsps_m());
				bgabGascz008Dto.setLevl_c(z003Dto.getXorg_dept_lv());
				bgabGascz008Dto.setRank_c(z003Dto.getXorg_rsps_crank());
				bgabGascz008Dto.setRank_e(z003Dto.getXorg_rsps_mrank());
				bgabGascz008Dto.setOrga_csner(z003Dto.getXorg_orga_csner());
				bgabGascz008Dto.setCorp_cd(corpCd);
				
				z008List.add(bgabGascz008Dto);
			}
			
			systemDao.deleteAprvInfo(dto);
			systemDao.insertAprvInfo(z008List);
		
		} catch (SessionException e) {
			e.printStackTrace();
		}
		
		return iCnt+uCnt;
	}

	@Override
	public void deleteListToDeptInfoManagement(List<BgabGascz003Dto> dtoList) {
		systemDao.deleteListToDeptInfoManagement(dtoList);
	}
	
	@Override
	public void insertApprovalUseYn(BgabGascz031Dto inputDtp){
		try {
			systemDao.insertApprovalUseYn(inputDtp);
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	@Override
	public BgabGascz033Dto getSelectLogoToFile(BgabGascz033Dto bgabGascZ033Dto){
		return systemDao.selectLogoMngrToFile(bgabGascZ033Dto);
	}
	
	@Override
	public void saveLogoToFile(HttpServletRequest req, HttpServletResponse res, BgabGascz033Dto bgabGascZ033Dto){
		String msg        = "";
		String resultUrl  = "xst22_file.gas";
		
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{
			paramVal[0] = "file_name";
			paramVal[1] = "old_file_name";
			paramVal[2] = "logo";

			result = FileUtil.uploadFile(req, res, paramVal);

			if(result != null){
				if(result[0] != null){
					bgabGascZ033Dto.setOgc_fil_nm(result[0]);
					bgabGascZ033Dto.setFil_nm(result[5]);
					bgabGascZ033Dto.setFil_mgn_qty(Integer.parseInt(result[3]));
					Integer fileRs = systemDao.mergeLogoMngrToFile(bgabGascZ033Dto);
				}
				msg = result[4];

			}else{
				//resultUrl = "xbt01_file.gas";
				msg = HncisMessageSource.getMessage("FILE.0001");
			}
		}catch(Exception e){
			//resultUrl = "xbt01_file.gas";
			msg = HncisMessageSource.getMessage("FILE.0001");
			e.printStackTrace();
		}finally{
			try{
				String dispatcherYN = "Y";
				req.setAttribute("dispatcherYN", dispatcherYN);
				req.setAttribute("csrfToken", bgabGascZ033Dto.getCsrfToken());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);

				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 라이선스 구매이력 저장
	 */
	@Override
	public int insertXst35Hist(BgabGascz035Dto inDto) throws SQLException {
		return systemDao.insertXst35Hist(inDto);
	}
	
	/**
	 * 라이선스 구매이력 총건수
	 */
	@Override
	public int selectXst35ListCount(BgabGascz035Dto dto) throws SQLException {
		return systemDao.selectXst35ListCount(dto);
	}
	
	/**
	 * 라이선스 구매이력 리스트
	 */
	@Override
	public List<BgabGascz035Dto> doSearchXst35List(BgabGascz035Dto dto) throws SQLException {
		return systemDao.doSearchXst35List(dto);
	}
	


	@Override
	public void doSystemTest() throws Exception{

		logger.info("######### TEST START ###########");
		String docNo = "";
		String adminID = "10000001";
		String[] userArray = {"10000002", "10000003", "10000004", "10000005"};
		String[] pCodeArray = {"P-B-001", "P-B-002", "P-B-003", "P-B-004", "P-B-005", "P-B-006"};
		String[] sCodeArray = {"GASBZ01210010", "GASBZ01220010", "GASBZ01230010", "GASBZ01240010", "GASBZ01250010", "GASBZ01260010"};
		String[] asCodeArray = {"GASBZ01210030", "GASBZ01220030", "GASBZ01230030", "GASBZ01240030", "GASBZ01250030", "GASBZ01260030"};
		String[] rCodeArray = {"GASROLE01210030", "GASROLE01220030", "GASROLE01230030", "GASROLE01240030", "GASROLE01250030", "GASROLE01260030"};
		String[] pArray = {"RC", "UF", "GF", "BK", "TR", "FJ"};
		String message;
		String sDate = CurrentDateTime.getDate();
		
		for(int i=0;i<userArray.length; i++){
			Random randomGenerator = new Random();
			for(int j=0; j<pCodeArray.length; j++){
				String sTime = CurrentDateTime.getTime();
				int sRandom = randomGenerator.nextInt(10);
				
				docNo = sDate + sTime + sRandom + pArray[j];
				
				String processCode = pCodeArray[j]; 	
				String bizKey = docNo;	
				String statusCode = sCodeArray[j];	
				String adminCode = asCodeArray[j];
				String loginUserId = userArray[i];	
				String comment = null;
				String roleCode = rCodeArray[j]; 
				
				List<String> approveList = new ArrayList<String>();
				List<String> managerList = new ArrayList<String>();
				managerList.add(adminID);
				
				message = BpmApiUtil.sendSaveTask(processCode, bizKey, statusCode, loginUserId, roleCode, approveList, managerList );
				
				logger.info("save_return : " + message);
			}
		}
		logger.info("######### TEST END ###########");
	}
}