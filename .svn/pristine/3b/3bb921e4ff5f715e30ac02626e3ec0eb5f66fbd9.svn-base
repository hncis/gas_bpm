package com.hncis.shuttleBus.dao;

import java.util.List;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.shuttleBus.vo.BgabGascsb01;
import com.hncis.shuttleBus.vo.BgabGascsb02;
import com.hncis.shuttleBus.vo.BgabGascsb03;
import com.hncis.shuttleBus.vo.BgabGascsb04;

public interface ShuttleBusDao {
	public int insertShuttleBusRequstNew(BgabGascsb01 param);
	public int updateShuttleBusRequstFinalN(BgabGascsb01 param);
	public int updateShuttleBusRequstNew(BgabGascsb01 param);
	public int deleteShuttleBusRequstNew(BgabGascsb01 param);
	public int insertShuttleBusRequst(BgabGascsb01 param);
	public int updateShuttleBusRequst(BgabGascsb01 param);
	public Object updateShuttleBusRequstNewRequest(BgabGascsb01 param);
	public int updateShuttleBusRequstConfirmFinal(BgabGascsb01 param);
	public BgabGascsb01 getSelectShuttleBusNewInfo(BgabGascsb01 param);

	public int getSelectShuttleBusListCount(BgabGascsb01 param);
	public List<BgabGascsb01> getSelectShuttleBusList(BgabGascsb01 param);
	public List<BgabGascsb02> getSelectTransporteFretadoList(BgabGascsb02 param);
	public List<BgabGascsb02> getSelectPonteExistenteList(BgabGascsb02 param);
	public int insertTransporteFretadoList(BgabGascsb02 param);
	public int updateTransporteFretadoList(BgabGascsb02 param);
	public int insertPontoExistenteList(BgabGascsb02 param);
	public int updatePontoExistenteList(BgabGascsb02 param);
	public int deleteTransporteFretadoList(List<BgabGascsb02> param);
	public int deletePontoExistenteList(List<BgabGascsb02> param);
	public BgabGascsb01 getSelectShuttleBusRequestCheck(BgabGascsb01 param);
	public BgabGascsb03 getSelectShuttleBusBeforeView(BgabGascsb01 param);
	public List<BgabGascsb02> getSelectShuttleBusLineCombo(BgabGascsb02 dto);
	public List<BgabGascsb02> getSelectPonteExistentePopupList(BgabGascsb02 param);
	public int getSelectPonteExistentePopupListCount(BgabGascsb02 param);
	public BgabGascsb04 getSelectShuttleBusSapInformationView(BgabGascsb04 param);
	public List<CommonCode> getSelectWorkShiftComboList(CommonCode param);
	public int updateInfoSbToReject(BgabGascsb01 param);
	
	public int updateShuttleBusApprovalStatus(BgabGascsb01 dto);
	public BgabGascsb01 getSelectShuttleBusChangeToDocNo(BgabGascsb01 param);
	public BgabGascsb02 getSelectShuttleBusLineTime(BgabGascsb02 dto);
	
	public int insertShuttleBusToFile (BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectShuttleBusToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectShuttleBusToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public int deleteShuttleBusToFile (List<BgabGascZ011Dto> bgabGascZ011List);
	
	public List<CommonCode> getShuttleBusCombo1(CommonCode code);
	public List<CommonCode> getShuttleBusCombo2(CommonCode code);

	public BgabGascsb01 doSearchShuttleBusBeforeRequst(BgabGascsb01 param);
}
