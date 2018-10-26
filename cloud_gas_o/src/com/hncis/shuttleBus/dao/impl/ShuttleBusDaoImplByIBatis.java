package com.hncis.shuttleBus.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.CommonCode;
import com.hncis.shuttleBus.dao.ShuttleBusDao;
import com.hncis.shuttleBus.vo.BgabGascsb01;
import com.hncis.shuttleBus.vo.BgabGascsb02;
import com.hncis.shuttleBus.vo.BgabGascsb03;
import com.hncis.shuttleBus.vo.BgabGascsb04;

public class ShuttleBusDaoImplByIBatis extends CommonDao implements ShuttleBusDao{
	private final String INSERT_SHUTTLE_BUS_REQUEST_NEW  = "insertShuttleBusRequstNew";
	private final String INSERT_SHUTTLE_BUS_REQUEST_FINAL_N  = "updateShuttleBusRequstFinalN";
	private final String UPDATE_SHUTTLE_BUS_REQUEST_NEW  = "updateShuttleBusRequstNew";
	private final String DELETE_SHUTTLE_BUS_REQUEST_NEW  = "deleteShuttleBusRequstNew";
	private final String UPDATE_SHUTTLE_BUS_REQUEST_NEW_REQUEST  = "updateShuttleBusRequstNewRequest";
	private final String UPDATE_SHUTTLE_BUS_REQUEST_CONFIRM_FINAL  = "updateShuttleBusRequstConfirmFinal";
	private final String SELECT_SHUTTLE_BUS_NEW_INFO  = "selectShuttleBusNewInfo";
	private final String SELECT_SHUTTLE_BUS_LIST_COUNT = "getSelectShuttleBusListCount";
	private final String SELECT_SHUTTLE_BUS_LIST  = "getSelectShuttleBusList";
	private final String SELECT_TRANSPORTE_FRETADO_LIST  = "selectTransporteFretadoList";
	private final String SELECT_PONTO_EXISTENTE_LIST  = "selectPonteExistenteList";
	private final String INSERT_TRANSPORTE_FRETADO_LIST  = "insertTransporteFretadoList";
	private final String UPDATE_TRANSPORTE_FRETADO_LIST  = "updateTransporteFretadoList";
	private final String INSERT_PONTO_EXISTENTE_LIST  = "insertPontoExistenteList";
	private final String UPDATE_PONTO_EXISTENTE_LIST  = "updatePontoExistenteList";
	private final String DELETE_TRANSPORTE_FRETADO_LIST  = "deleteTransporteFretadoList";
	private final String DELETE_PONTO_EXISTENTE_LIST  = "deletePontoExistenteList";
	private final String SELECT_SHUTTLE_BUS_REQUEST_INFO  = "selectShuttleBusRequestCheck";
	private final String SELECT_SHUTTLE_BUS_BEFORE_VIEW  = "selectShuttleBusBeforeView";
	private final String SELECT_SHUTTLE_BUS_LINE_COMBO  = "selectShuttleBusLineCombo";
	private final String SELECT_PONTO_EXISTENTE_POPUP_LIST  = "selectPonteExistentePopupList";
	private final String SELECT_PONTO_EXISTENTE_POPUP_LIST_COUNT  = "selectPonteExistentePopupListCount";
	private final String SELECT_SHUTTLE_BUS_SAP_INFORMATION_VIEW  = "selectShuttleBusSapInformationView";
	private final String SELECT_WORK_SHIFT_COMBO_LIST = "selectWorkShiftComboList";
	
	private final String UPDATE_INFO_SB_TO_REJECT = "updateInfoSbToReject";
	private final String UPDATE_SHUTTLEBUS_APPROVAL_STATUS = "updateShuttleBusApprovalStatus";
	private final String SELECT_SHUTTLEBUS_CHANGE_TO_DOC_NO = "selectShuttleBusChangeToDocNo";
	
	private final String INSERT_SHUTTLE_BUS_TO_FILE							= "insertShuttleBusToFile";
	private final String SELECT_SHUTTLE_BUS_TO_FILE							= "selectShuttleBusToFile";
	private final String DELETE_SHUTTLE_BUS_TO_FILE							= "deleteShuttleBusToFile";

	private final String SELECT_SHUTTLE_BUS_COMBO1							= "selectShuttleBusCombo1";
	private final String SELECT_SHUTTLE_BUS_COMBO2							= "selectShuttleBusCombo2";
	
	private final String INSERT_SHUTTLE_BUS_REQUEST = "insertShuttleBusRequst";
	private final String UPDATE_SHUTTLE_BUS_REQUEST = "updateShuttleBusRequst";
	
	private final String SELECT_SHUTTLE_BUS_BEFORE_REQUEST_INFO = "selectShuttleBusBeforeRequestInfo";
	
	public int insertShuttleBusRequstNew(BgabGascsb01 param){
		return insert(INSERT_SHUTTLE_BUS_REQUEST_NEW, param);
	}
	public int updateShuttleBusRequstFinalN(BgabGascsb01 param){
		return insert(INSERT_SHUTTLE_BUS_REQUEST_FINAL_N, param);
	}

	public int updateShuttleBusRequstNew(BgabGascsb01 param){
		return update(UPDATE_SHUTTLE_BUS_REQUEST_NEW, param);
	}
	
	public int deleteShuttleBusRequstNew(BgabGascsb01 param){
		return delete(DELETE_SHUTTLE_BUS_REQUEST_NEW, param);
	}
	
	public Object updateShuttleBusRequstNewRequest(BgabGascsb01 param){
		return super.update(UPDATE_SHUTTLE_BUS_REQUEST_NEW_REQUEST, param);
	}

	public int updateShuttleBusRequstConfirmFinal(BgabGascsb01 param){
		return super.update(UPDATE_SHUTTLE_BUS_REQUEST_CONFIRM_FINAL, param);
	}
	
	public BgabGascsb01 getSelectShuttleBusNewInfo(BgabGascsb01 param){
		return (BgabGascsb01)getSqlMapClientTemplate().queryForObject(SELECT_SHUTTLE_BUS_NEW_INFO, param);
	}
	
	public int getSelectShuttleBusListCount(BgabGascsb01 param){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_SHUTTLE_BUS_LIST_COUNT, param));
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascsb01> getSelectShuttleBusList(BgabGascsb01 param){
		return getSqlMapClientTemplate().queryForList(SELECT_SHUTTLE_BUS_LIST, param);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascsb02> getSelectTransporteFretadoList(BgabGascsb02 param){
		return getSqlMapClientTemplate().queryForList(SELECT_TRANSPORTE_FRETADO_LIST, param);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascsb02> getSelectPonteExistenteList(BgabGascsb02 param){
		return getSqlMapClientTemplate().queryForList(SELECT_PONTO_EXISTENTE_LIST, param);
	}
	
	public int insertTransporteFretadoList(BgabGascsb02 param){
		return insert(INSERT_TRANSPORTE_FRETADO_LIST, param);
	}
	
	public int updateTransporteFretadoList(BgabGascsb02 param){
		return update(UPDATE_TRANSPORTE_FRETADO_LIST, param);
	}
	
	public int insertPontoExistenteList(BgabGascsb02 param){
		return insert(INSERT_PONTO_EXISTENTE_LIST, param);
	}
	
	public int updatePontoExistenteList(BgabGascsb02 param){
		return update(UPDATE_PONTO_EXISTENTE_LIST, param);
	}
	
	public int deleteTransporteFretadoList(List<BgabGascsb02> param){
		return delete(DELETE_TRANSPORTE_FRETADO_LIST, param);
	}
	
	public int deletePontoExistenteList(List<BgabGascsb02> param){
		return delete(DELETE_PONTO_EXISTENTE_LIST, param);
	}
	
	public BgabGascsb01 getSelectShuttleBusRequestCheck(BgabGascsb01 param){
		return (BgabGascsb01)getSqlMapClientTemplate().queryForObject(SELECT_SHUTTLE_BUS_REQUEST_INFO, param);
	}
	
	public BgabGascsb03 getSelectShuttleBusBeforeView(BgabGascsb01 param){
		return (BgabGascsb03)getSqlMapClientTemplate().queryForObject(SELECT_SHUTTLE_BUS_BEFORE_VIEW, param);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascsb02> getSelectShuttleBusLineCombo(BgabGascsb02 dto){
		return getSqlMapClientTemplate().queryForList(SELECT_SHUTTLE_BUS_LINE_COMBO, dto);
	}
	
	@SuppressWarnings("unchecked")
	public int getSelectPonteExistentePopupListCount(BgabGascsb02 param){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_PONTO_EXISTENTE_POPUP_LIST_COUNT, param));
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascsb02> getSelectPonteExistentePopupList(BgabGascsb02 param){
		return getSqlMapClientTemplate().queryForList(SELECT_PONTO_EXISTENTE_POPUP_LIST, param);
	}
	
	public BgabGascsb04 getSelectShuttleBusSapInformationView(BgabGascsb04 param){
		return (BgabGascsb04)getSqlMapClientTemplate().queryForObject(SELECT_SHUTTLE_BUS_SAP_INFORMATION_VIEW, param);
	}
	
	public List<CommonCode> getSelectWorkShiftComboList(CommonCode param){
		return getSqlMapClientTemplate().queryForList(SELECT_WORK_SHIFT_COMBO_LIST, param);
	}
	public int updateInfoSbToReject(BgabGascsb01 param){
		return update(UPDATE_INFO_SB_TO_REJECT, param);
	}
	public int updateShuttleBusApprovalStatus(BgabGascsb01 dto){
		return update(UPDATE_SHUTTLEBUS_APPROVAL_STATUS, dto);
	}
	
	public BgabGascsb01 getSelectShuttleBusChangeToDocNo(BgabGascsb01 param){
		return (BgabGascsb01)getSqlMapClientTemplate().queryForObject(SELECT_SHUTTLEBUS_CHANGE_TO_DOC_NO, param);
	}
	public BgabGascsb02 getSelectShuttleBusLineTime(BgabGascsb02 dto){
		return (BgabGascsb02)getSqlMapClientTemplate().queryForObject("selectShuttleBusLineTime", dto);
	}
	
	public int insertShuttleBusToFile (BgabGascZ011Dto bgabGascZ011Dto){
		return super.insert(INSERT_SHUTTLE_BUS_TO_FILE, bgabGascZ011Dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascZ011Dto> getSelectShuttleBusToFile(BgabGascZ011Dto bgabGascZ011Dto){
		return getSqlMapClientTemplate().queryForList(SELECT_SHUTTLE_BUS_TO_FILE, bgabGascZ011Dto);
	}
	
	public BgabGascZ011Dto getSelectShuttleBusToFileInfo(BgabGascZ011Dto bgabGascZ011Dto){
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_SHUTTLE_BUS_TO_FILE, bgabGascZ011Dto);
	}
	
	public int deleteShuttleBusToFile (List<BgabGascZ011Dto> bgabGascZ011List){
		return super.delete(DELETE_SHUTTLE_BUS_TO_FILE, bgabGascZ011List);
	}
	
	@SuppressWarnings("unchecked")
	public List<CommonCode> getShuttleBusCombo1(CommonCode code){
		return getSqlMapClientTemplate().queryForList(SELECT_SHUTTLE_BUS_COMBO1, code);
	}

	@SuppressWarnings("unchecked")
	public List<CommonCode> getShuttleBusCombo2(CommonCode code){
		return getSqlMapClientTemplate().queryForList(SELECT_SHUTTLE_BUS_COMBO2, code);
	}
	
	public int insertShuttleBusRequst(BgabGascsb01 param){
		return insert(INSERT_SHUTTLE_BUS_REQUEST, param);
	}

	public int updateShuttleBusRequst(BgabGascsb01 param){
		return update(UPDATE_SHUTTLE_BUS_REQUEST, param);
	}
	
	public BgabGascsb01 doSearchShuttleBusBeforeRequst(BgabGascsb01 param){
		return (BgabGascsb01)getSqlMapClientTemplate().queryForObject(SELECT_SHUTTLE_BUS_BEFORE_REQUEST_INFO, param);		
	}
}

