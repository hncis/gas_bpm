package com.hncis.shuttleBus.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.shuttleBus.vo.BgabGascsb01;
import com.hncis.shuttleBus.vo.BgabGascsb02;
import com.hncis.shuttleBus.vo.BgabGascsb03;
import com.hncis.shuttleBus.vo.BgabGascsb04;

@Transactional
public interface ShuttleBusManager {
	public void insertShuttleBusRequstNew(HttpServletRequest req, HttpServletResponse res,BgabGascsb01 param);
	public int saveShuttleBusRequst(BgabGascsb01 param);
	public int deleteShuttleBusRequstNew(BgabGascsb01 param);
	public CommonMessage requestShuttleBusRequstNew(BgabGascsb01 param);
	public CommonApproval requestShuttleBusNewRequstCancel(BgabGascsb01 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException;
	public BgabGascsb01 getSelectShuttleBusNewInfo(BgabGascsb01 param);
	public int getSelectShuttleBusListCount(BgabGascsb01 param);
	public List<BgabGascsb01> getSelectShuttleBusList(BgabGascsb01 param);
	public List<BgabGascsb02> getSelectTransporteFretadoList(BgabGascsb02 param);
	public List<BgabGascsb02> getSelectPonteExistenteList(BgabGascsb02 param);
	public int insertTransporteFretadoList(List<BgabGascsb02> param);
	public int insertPontoExistenteList(List<BgabGascsb02> param);
	public int deleteTransporteFretadoList(List<BgabGascsb02> param);
	public int deletePontoExistenteList(List<BgabGascsb02> param);
	public BgabGascsb01 getSelectShuttleBusRequestCheck(BgabGascsb01 param);
	public BgabGascsb03 getSelectShuttleBusBeforeView(BgabGascsb01 param);
	public Object updateShuttleBusRequstNewRequest(BgabGascsb01 param);
	public int updateShuttleBusRequstConfirmFinal(BgabGascsb01 param);
	public List<BgabGascsb02> getSelectShuttleBusLineCombo(BgabGascsb02 dto);
	public int getSelectPonteExistentePopupListCount(BgabGascsb02 param);
	public List<BgabGascsb02> getSelectPonteExistentePopupList(BgabGascsb02 param);
	public BgabGascsb04 getSelectShuttleBusSapInformationView(BgabGascsb04 param);
	public List<CommonCode> getSelectWorkShiftComboList(CommonCode param);
	public int updateInfoSbToReject(BgabGascsb01 param, HttpServletRequest req) throws SessionException;
	public BgabGascsb01 getSelectShuttleBusChangeToDocNo(BgabGascsb01 param);
	public BgabGascsb02 getSelectShuttleBusLineTime(BgabGascsb02 dto);
	public String insertShuttleBusRequstInclusion(BgabGascsb01 dto);
	public void saveShuttleBusToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectShuttleBusToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectShuttleBusToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteShuttleBusToFile(List<BgabGascZ011Dto> bgabGascZ011IList);
	
	public List<CommonCode> getShuttleBusCombo1(CommonCode code);
	public List<CommonCode> getShuttleBusCombo2(CommonCode code);
	
	public BgabGascsb01 doSearchShuttleBusBeforeRequst(BgabGascsb01 param);
}
