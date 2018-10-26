
package com.hncis.taxi.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.BgabGascZ011Dto;
import com.hncis.common.vo.BgabGascz002Dto;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.taxi.vo.BgabGasctx01;
import com.hncis.taxi.vo.BgabGasctx02;
import com.hncis.taxi.vo.BgabGasctx03;
import com.hncis.taxi.vo.BgabGasctx04;

@Transactional
public interface TaxiManager {

	public List<BgabGasctx03> getComboListTxToTransport(BgabGasctx03 code);
	public List<BgabGasctx03> getComboListTxFromPlace(BgabGasctx03 code);
	public List<BgabGasctx03> getComboListTxToPlace(BgabGasctx03 code);
	public BgabGasctx03 getSelectInfoTxToTaxiAmount(BgabGasctx03 dto);

	/**
	 * accept count
	 * accept search
	 * @param keyParamDto
	 * @return
	 */
	public int getSelectCountTXToAccept(BgabGasctx02 keyParamDto);

	/**
	 * request apply
	 * @param cgabGasctx01
	 * @return
	 * @throws Exception
	 */
	public Object insertInfoTXToRequest_1(BgabGasctx01 cgabGasctx01);
	public Object insertInfoTXToRequest_2(BgabGasctx01 cgabGasctx01, List<BgabGasctx04> list);

	/**
	 * request update
	 * @param cgabGasctx01
	 * @return
	 * @throws Exception
	 */
	public Object updateInfoTXToRequest(BgabGasctx01 cgabGasctx01, List<BgabGasctx04> list);

	/**
	 * request delete
	 * @param keyParamDto
	 * @return
	 */
	public Object deleteInfoTXToRequest(BgabGasctx02 keyParamDto);


	public Object deleteTxToRequestList(BgabGasctx04 keyParamDto);

	/**
	 * approval
	 * @param keyParamDto
	 * @param appInfo
	 * @param message
	 * @param req
	 * @return
	 * @throws SessionException
	 */
	public CommonMessage setApproval(BgabGasctx02 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException;

	public CommonMessage setApprovalForSpecialAuth(BgabGasctx02 keyParamDto, HttpServletRequest req);

	/**
	 * approve cancel
	 * @param keyParamDto
	 * @param appInfo
	 * @param message
	 * @param req
	 * @return
	 * @throws SessionException 
	 */
	public CommonMessage setApprovalCancel(BgabGasctx02 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException;

	/**
	 * request approve/confirm/confirm cancel
	 * @param keyParamDto
	 */
	public int updateInfoTXToApprove(BgabGasctx02 keyParamDto);

	/**
	 * request count
	 * request search
	 * @param cgabGasctx01
	 * @return
	 * @throws Exception
	 */
	public BgabGasctx01 getSelectInfoTXToRequest(BgabGasctx02 keyParamDto);
	List<BgabGasctx04> getSelectListTxToRequest(BgabGasctx04 dto);

	/**
	 * approval info search
	 * @param keyParamDto
	 * @return
	 */
	public String getSelectApprovalInfo(BgabGasctx02 keyParamDto);

	public List<BgabGasctx04> getSelectListTxToRequestApprove(BgabGasctx04 dto);

	/**
	 * submit search
	 * @param keyParamDto
	 * @return
	 */
	public BgabGasctx01 getSelectInfoTXToSubmit(BgabGasctx02 keyParamDto);

	/**
	 * List page count search
	 * @param bgabGasctx01
	 * @return int
	 */
	public int getSelectCountTXToList(BgabGasctx01 bgabGasctx01);

	/**
	 * List page search
	 * @param bgabGasctx01
	 * @return List<BgabGasctx01>
	 */
	public List<BgabGasctx01> getSelectTXToList(BgabGasctx01 bgabGasctx01);

	public List<BgabGasctx01> getSelectListTXToAccept(BgabGasctx02 keyParamDto);

	public int getSelectCountTxToPlaceManagement(BgabGasctx03 dto);
	public List<BgabGasctx03> getSelectListTxToPlaceManagement(BgabGasctx03 dto);
	public int insertTxToPlaceManagement(List<BgabGasctx03> dto);
	public int deleteTxToPlaceManagement(List<BgabGasctx03> dto);
	public CommonMessage updateInfoTxToReject(BgabGasctx02 dto, HttpServletRequest req) throws SessionException;

	public BgabGascz002Dto getSelectTaxiUserInfo(BgabGascz002Dto bgabGascz002Dto);

	public void saveTxToFile(HttpServletRequest req, HttpServletResponse res, BgabGascZ011Dto bgabGascZ011Dto);
	public List<BgabGascZ011Dto> getSelectTxToFile(BgabGascZ011Dto dto);
	public BgabGascZ011Dto getSelectTxToFileInfo(BgabGascZ011Dto dto);
	public int deleteTxToFile(List<BgabGascZ011Dto> dto);
}
