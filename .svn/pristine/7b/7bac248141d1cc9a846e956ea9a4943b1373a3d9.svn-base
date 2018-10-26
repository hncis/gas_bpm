package com.hncis.taxi.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.taxi.dao.TaxiDao;
import com.hncis.taxi.vo.BgabGasctx01;
import com.hncis.taxi.vo.BgabGasctx02;
import com.hncis.taxi.vo.BgabGasctx03;
import com.hncis.taxi.vo.BgabGasctx04;

public class TaxiDaoImplByIBatis extends CommonDao implements TaxiDao{
	
	private final String SELECT_COMBO_LIST_TX_TRANSPORT			   = "selectComboListTxTransport";
	private final String SELECT_COMBO_LIST_TX_FROM_PLACE           = "selectComboListTxFromPlace";
	private final String SELECT_COMBO_LIST_TX_TO_PLACE             = "selectComboListTxToPlace";
	private final String SELECT_INFO_TX_TO_TAXI_AMOUNT             = "selectInfoTxToTaxiAmount";
	
	private final String SELECT_INFO_TX_TO_REQUEST                 = "selectInfoTxToRequest";
	private final String SELECT_LIST_TX_TO_REQUEST 				   = "selectListTxToRequest";
	private final String SELECT_LIST_TX_TO_REQUEST_APPROVE 		   = "selectListTxToRequestApprove";
	private final String INSERT_INFO_TX_TO_REQUEST_1               = "insertInfoTxToReqeust_1";
	private final String INSERT_INFO_TX_TO_REQUEST_2               = "insertInfoTxToReqeust_2";
	private final String INSERT_TX_TO_REQUEST_LIST                 = "insertTxToRequestList";
	private final String UPDATE_TX_TO_REQUEST_LIST                 = "updateTxToRequestList";
	private final String DELETE_INFO_TX_TO_REQUEST                 = "deleteInfoTxToRequest";
	private final String UPDATE_INFO_TX_TO_REQUEST				   = "updateInfoTxToRequest";
	private final String DELETE_TX_TO_REQUEST_LIST				   = "deleteTxToRequestList";
	private final String UPDATE_INFO_TX_REQUEST_AMOUNT			   = "updateInfoTxRequestAmount";
	private final String UPDATE_INFO_TX_TO_APPROVE				   = "updateInfoTxToApprove";
	
	private final String SELECT_TX_TO_SUBMIT                       = "selectTxToSubmit";
	private final String SELECT_APPROVAL_INFO_BY_TX                = "selectApprovalInfoByTx";

	private final String SELECT_COUNT_TX_TO_LIST				   = "selectCountTxToList";
	private final String SELECT_TX_TO_LIST						   = "selectTxToList";

	private final String SELECT_COUNT_TX_TO_ACCEPT                 = "selectCountTxToAccept";
	private final String SELECT_LIST_TX_TO_ACCEPT                  = "selectListTxToAccept";
	
	private final String SELECT_COUNT_TX_TO_PLACE_MANAGEMENT 		= "selectCountTxToPlaceManagement";
	private final String SELECT_LIST_TX_TO_PLACE_MANAGEMENT 		= "selectListTxToPlaceManagement";
	private final String INSERT_TX_TO_PLACE_MANAGEMENT 				= "insertTxToPlaceManagement";
	private final String UPDATE_TX_TO_PLACE_MANAGEMENT 				= "updateTxToPlaceManagement";
	private final String DELETE_TX_TO_PLACE_MANAGEMENT 				= "deleteTxToPlaceManagement";
	private final String UPDATE_TAXI_PO_INFO						= "updateTaxiPoInfo";
	private final String SELECT_TAXI_REJECT_SUBMIT_PO_SEARCH 		= "selectTaxiRejectSubmitPoSearch";
	private final String UPDATE_INFO_TX_TO_REJECT 					= "updateInfoTxToReject";
	
	private final String UPDATE_INFO_TX_TO_APPROVE_FOR_SPECIAL_AUTH	= "updateInfoTXToApproveForSpecialAuth";
	private final String SELECT_TAXI_USER_INFO 						= "selectTaxiUserInfo";
	
	
	private final String INSERT_TX_TO_FILE					 = "insertTxToFile";
	private final String SELECT_TX_TO_FILE					 = "selectTxToFile";
	private final String DELETE_TX_TO_FILE					 = "deleteTxToFile";
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGasctx03> getComboListTxToTransport(BgabGasctx03 dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_COMBO_LIST_TX_TRANSPORT, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGasctx03> getComboListTxFromPlace(BgabGasctx03 dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_COMBO_LIST_TX_FROM_PLACE, dto);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGasctx03> getComboListTxToPlace(BgabGasctx03 dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_COMBO_LIST_TX_TO_PLACE, dto);
	}
	
	@Override
	public BgabGasctx03 getSelectInfoTxToTaxiAmount(BgabGasctx03 dto) {
		return (BgabGasctx03)getSqlMapClientTemplate().queryForObject(SELECT_INFO_TX_TO_TAXI_AMOUNT, dto);
	}
	
	/**
	 * accept count
	 * accept search
	 * @param cgabGasctx01
	 * @return
	 */
	public String getSelectCountTXToAccept(BgabGasctx02 keyParamDto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TX_TO_ACCEPT, keyParamDto);
	}
	
	/**
	 * request apply
	 * @param BgabGasctr01
	 * @return
	 */
	public Object insertInfoTXToRequest_1(BgabGasctx01 cgabGasctx01){
		return super.insert(INSERT_INFO_TX_TO_REQUEST_1, cgabGasctx01);
	}
	public Object insertInfoTXToRequest_2(BgabGasctx01 cgabGasctx01){
		return super.insert(INSERT_INFO_TX_TO_REQUEST_2, cgabGasctx01);
	}
	public Object insertTxToRequestList(List<BgabGasctx04> list){
		return super.insert(INSERT_TX_TO_REQUEST_LIST, list);
	}
	public Object insertTxToRequestRowData(BgabGasctx04 dto){
		return super.insert(INSERT_TX_TO_REQUEST_LIST, dto);
	}
	public Object updateTxToRequestRowData(BgabGasctx04 dto){
		return super.update(UPDATE_TX_TO_REQUEST_LIST, dto);
	}
	
	/**
	 * request update
	 * @param BgabGasctr01
	 * @return
	 */
	public Object updateInfoTXToRequest(BgabGasctx01 cgabGasctx01){
		return super.insert(UPDATE_INFO_TX_TO_REQUEST, cgabGasctx01);
	}
	
	/**
	 * request delete
	 * @param cgabGasctx01
	 * @return
	 */
	public Object deleteInfoTXToRequest(BgabGasctx02 keyParamDto){
		return super.delete(DELETE_INFO_TX_TO_REQUEST, keyParamDto);
	}
	
	public Object deleteTxToRequestList(BgabGasctx04 keyParamDto){
		return super.delete(DELETE_TX_TO_REQUEST_LIST, keyParamDto);
	}
	
	public Object updateInfoTxRequestAmount(BgabGasctx04 keyParamDto){
		return super.update(UPDATE_INFO_TX_REQUEST_AMOUNT, keyParamDto);
	}
	
	/**
	 * request approve/confirm/confirm cancel
	 * @param keyParamDto
	 * @return
	 */
	public int updateInfoTXToApprove(BgabGasctx02 keyParamDto){
		return super.update(UPDATE_INFO_TX_TO_APPROVE, keyParamDto);
	}
	
	/**
	 * request search
	 * @param BgabGasctr01
	 * @return
	 */
	public BgabGasctx01 getSelectInfoTXToRequest(BgabGasctx02 keyParamDto){
		return (BgabGasctx01)getSqlMapClientTemplate().queryForObject(SELECT_INFO_TX_TO_REQUEST, keyParamDto);
	}
	
	@Override
	public List<BgabGasctx04> getSelectListTxToRequest(BgabGasctx04 dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TX_TO_REQUEST, dto);
	}
	
	public String getSelectApprovalInfo(BgabGasctx02 keyParamDto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_APPROVAL_INFO_BY_TX, keyParamDto);
	}
	
	@Override
	public List<BgabGasctx04> getSelectListTxToRequestApprove(BgabGasctx04 dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TX_TO_REQUEST_APPROVE, dto);
	}
	
	/**
	 * submit search
	 * @param keyParamDto
	 * @return
	 */
	public BgabGasctx01 getSelectInfoTXToSubmit(BgabGasctx02 keyParamDto){
		return (BgabGasctx01)getSqlMapClientTemplate().queryForObject(SELECT_TX_TO_SUBMIT, keyParamDto);
	}
	
	public String getSelectCountTXToList(BgabGasctx01 bgabGasctx01){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TX_TO_LIST, bgabGasctx01);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGasctx01> getSelectTXToList(BgabGasctx01 bgabGasctx01){
		return getSqlMapClientTemplate().queryForList(SELECT_TX_TO_LIST, bgabGasctx01);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGasctx01> getSelectListTXToAccept(BgabGasctx02 keyParamDto){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TX_TO_ACCEPT, keyParamDto);
	}
	
	/*************************************************************/
	/** Taxi Place managerment page                          **/
	/*************************************************************/
	@Override
	public int getSelectCountTxToPlaceManagement(BgabGasctx03 dto) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_TX_TO_PLACE_MANAGEMENT, dto));
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BgabGasctx03> getSelectListTxToPlaceManagement(BgabGasctx03 dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_TX_TO_PLACE_MANAGEMENT, dto);
	}

	@Override
	public int insertTxToPlaceManagement(List<BgabGasctx03> dto) {
		int rtnCnt = 0;
		if("oracle".equals(StringUtil.getDbType())){
			rtnCnt = super.insert(INSERT_TX_TO_PLACE_MANAGEMENT, dto);
		}else if("mysql".equals(StringUtil.getDbType())){
			for(BgabGasctx03 vo : dto){
				if("".equals(vo.getSeq())){
					rtnCnt += super.insert(INSERT_TX_TO_PLACE_MANAGEMENT, dto);
				}else{
					rtnCnt += super.insert(UPDATE_TX_TO_PLACE_MANAGEMENT, dto);
				}
			}			
		}else if("mssql".equals(StringUtil.getDbType())){
			
		}
		return rtnCnt;
	}

	@Override
	public int deleteTxToPlaceManagement(List<BgabGasctx03> dto) {
		return super.delete(DELETE_TX_TO_PLACE_MANAGEMENT, dto);
	}
	
	public int updateTaxiPoInfo(BgabGasctx04 dto){
		return update(UPDATE_TAXI_PO_INFO, dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGasctx04> getSelectTaxiRejectSubmitPoSearch(BgabGasctx04 dto){
		return getSqlMapClientTemplate().queryForList(SELECT_TAXI_REJECT_SUBMIT_PO_SEARCH, dto);
	}
	
	public int updateInfoTxToReject(BgabGasctx02 dto){
		return update(UPDATE_INFO_TX_TO_REJECT, dto);
	}
	
	public int updateInfoTXToApproveForSpecialAuth(BgabGasctx02 keyParamDto){
		return super.update(UPDATE_INFO_TX_TO_APPROVE_FOR_SPECIAL_AUTH, keyParamDto);
	}
	public BgabGascz002Dto getSelectTaxiUserInfo(BgabGascz002Dto dto){
		return (BgabGascz002Dto)getSqlMapClientTemplate().queryForObject(SELECT_TAXI_USER_INFO, dto);
	}
	@Override
	public Integer insertTxToFile(BgabGascZ011Dto bgabGascZ011Dto) {
		return super.insert(INSERT_TX_TO_FILE, bgabGascZ011Dto);
	}
	@Override
	public List<BgabGascZ011Dto> getSelectTxToFile(BgabGascZ011Dto bgabGascZ011Dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_TX_TO_FILE, bgabGascZ011Dto);
		
	}
	@Override
	public BgabGascZ011Dto getSelectTxToFileInfo(BgabGascZ011Dto bgabGascZ011Dto) {
		return (BgabGascZ011Dto)getSqlMapClientTemplate().queryForObject(SELECT_TX_TO_FILE, bgabGascZ011Dto);
	}
	@Override
	public Integer deleteTxToFile(List<BgabGascZ011Dto> bgabGascZ011IList) {
		return super.delete(DELETE_TX_TO_FILE, bgabGascZ011IList);
	}
}
