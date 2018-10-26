package com.hncis.taxi.dao;

import java.util.List;

import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.taxi.vo.BgabGasctx01;
import com.hncis.taxi.vo.BgabGasctx02;
import com.hncis.taxi.vo.BgabGasctx03;
import com.hncis.taxi.vo.BgabGasctx04;

public interface TaxiDao {

	public List<BgabGasctx03> getComboListTxToTransport(BgabGasctx03 dto);
	public List<BgabGasctx03> getComboListTxFromPlace(BgabGasctx03 dto);
	public List<BgabGasctx03> getComboListTxToPlace(BgabGasctx03 dto);
	public BgabGasctx03 getSelectInfoTxToTaxiAmount(BgabGasctx03 dto);
	
	/**
	 * accept count
	 * accept search
	 * @param keyParamDto
	 * @return
	 */
	public String getSelectCountTXToAccept(BgabGasctx02 keyParamDto);
	
	/**
	 * request apply
	 * @param cgabGasctx01
	 * @return
	 */
	public Object insertInfoTXToRequest_1(BgabGasctx01 cgabGasctx01);
	public Object insertInfoTXToRequest_2(BgabGasctx01 cgabGasctx01);
	public Object insertTxToRequestList(List<BgabGasctx04> list);
	public Object insertTxToRequestRowData(BgabGasctx04 dto);
	public Object updateTxToRequestRowData(BgabGasctx04 dto);

	/**
	 * request update
	 * @param cgabGasctx01
	 * @return
	 */
	public Object updateInfoTXToRequest(BgabGasctx01 cgabGasctx01);
	
	/**
	 * request delete
	 * @param keyParamDto
	 * @return
	 */
	public Object deleteInfoTXToRequest(BgabGasctx02 keyParamDto);
	public Object deleteTxToRequestList(BgabGasctx04 keyParamDto);
	public Object updateInfoTxRequestAmount(BgabGasctx04 keyParamDto);
	
	/**
	 * request approve/confirm/confirm cancel
	 * @param keyParamDto
	 * @return
	 */
	public int updateInfoTXToApprove(BgabGasctx02 keyParamDto);
	
	/**
	 * request count
	 * request search
	 * @param cgabGasctx01
	 * @return
	 */
	public BgabGasctx01 getSelectInfoTXToRequest(BgabGasctx02 keyParamDto);
	public List<BgabGasctx04> getSelectListTxToRequest(BgabGasctx04 dto);
	
	public String getSelectApprovalInfo(BgabGasctx02 keyParamDto);
	public List<BgabGasctx04> getSelectListTxToRequestApprove(BgabGasctx04 dto);
	
	/**
	 * submit search
	 * @param keyParamDto
	 * @return
	 */
	public BgabGasctx01 getSelectInfoTXToSubmit(BgabGasctx02 keyParamDto);
	
	public String getSelectCountTXToList(BgabGasctx01 bgabGasctx01);
	
	public List<BgabGasctx01> getSelectTXToList(BgabGasctx01 bgabGasctx01);
	
	public List<BgabGasctx01> getSelectListTXToAccept(BgabGasctx02 keyParamDto);
	
	
	public int getSelectCountTxToPlaceManagement(BgabGasctx03 dto);
	public List<BgabGasctx03> getSelectListTxToPlaceManagement(BgabGasctx03 dto);
	public int insertTxToPlaceManagement(List<BgabGasctx03> dto);
	public int deleteTxToPlaceManagement(List<BgabGasctx03> dto);
	
	public int updateTaxiPoInfo(BgabGasctx04 dto);
	
	public List<BgabGasctx04> getSelectTaxiRejectSubmitPoSearch(BgabGasctx04 dto);
	public int updateInfoTxToReject(BgabGasctx02 dto);
	
	public int updateInfoTXToApproveForSpecialAuth(BgabGasctx02 keyParamDto);
	
	public BgabGascz002Dto getSelectTaxiUserInfo(BgabGascz002Dto dto);
	
	public Integer insertTxToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectTxToFile(BgabGascZ011Dto bgabGascZ011Dto);
	public BgabGascZ011Dto getSelectTxToFileInfo(BgabGascZ011Dto bgabGascZ011Dto);
	public Integer deleteTxToFile(List<BgabGascZ011Dto> bgabGascZ011IList);
}
