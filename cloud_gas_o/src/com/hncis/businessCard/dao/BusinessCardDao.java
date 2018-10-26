package com.hncis.businessCard.dao;

import java.util.List;

import com.hncis.businessCard.vo.BgabGascba01;
import com.hncis.businessCard.vo.BgabGascba02;
import com.hncis.businessCard.vo.BgabGascba03;

public interface BusinessCardDao {
	/**
	 * request count
	 * request search
	 * @param cgabGascba01
	 * @return
	 */
	public BgabGascba01 getSelectInfoBCToRequest(BgabGascba02 keyParamDto);
	public BgabGascba01 getSelectInfoBCToDefaultRequest(BgabGascba02 keyParamDto);
	public BgabGascba01 getSelectInfoBCToBpmRequest(BgabGascba02 keyParamDto);
	
	/**
	 * request apply
	 * @param cgabGascba01
	 * @return
	 */
	public Object insertInfoBCToRequest_1(BgabGascba01 cgabGascba01);
	public Object insertInfoBCToRequest_2(BgabGascba01 cgabGascba01);
	
	/**
	 * request delete
	 * @param keyParamDto
	 * @return
	 */
	public Object deleteInfoBCToRequest(BgabGascba02 keyParamDto);
	
	/**
	 * request approve/confirm/confirm cancel
	 * @param keyParamDto
	 * @return
	 */
	public Object updateInfoBCToRequest(BgabGascba02 keyParamDto);
	
	/**
	 * accept count
	 * accept search
	 * @param keyParamDto
	 * @return
	 */
	public String getSelectCountBCToAccept(BgabGascba02 keyParamDto);
	public List<BgabGascba01> getSelectListBCToAccept(BgabGascba02 keyParamDto);
	/**
	 * accept delete
	 * @param keyParamDto
	 * @return
	 */
	public Object deleteListBCToAccept(List<BgabGascba02> keyParamDto);
	
	/**
	 * accept return
	 * @param keyParamDto
	 * @return
	 */
	public Object updateListBCToAcceptByReject(List<BgabGascba02> keyParamDto);
	
	/**
	 * accept confirm
	 * @param keyParamDto
	 * @return
	 */
	public Object updateListBCToAcceptByConfirm1(List<BgabGascba02> keyParamDto);
	
	/**
	 * accept confirmCancel
	 * @param keyParamDto
	 * @return
	 */
	public Object updateListBCToAcceptByConfirmCancel(List<BgabGascba02> keyParamDto);
	
	/**
	 * accept confirmAll
	 * @param keyParamDto
	 * @return
	 */
	public Object updateListBCToAcceptByConfirm2(List<BgabGascba02> keyParamDto);
	
	/**
	 * accept issue
	 * @param keyParamDto
	 * @return
	 */
	public Object updateListBCToAcceptByIssue(List<BgabGascba02> keyParamDto);
	
	/**
	 * submit search
	 * @param keyParamDto
	 * @return
	 */
	public BgabGascba01 getSelectInfoBCToSubmit(BgabGascba02 keyParamDto);
	
	public String getSelectApprovalInfo(BgabGascba02 keyParamDto);

	public String getSelectCountBcToCardTypeManagement(BgabGascba03 dto);

	public List<BgabGascba03> getSelectListBcToCardTypeManagement(BgabGascba03 dto);

	public int insertListBcToCardTypeManagement(List<BgabGascba03> list);

	public int updateListBcToCardTypeManagement(List<BgabGascba03> list);

	public int deleteListBcToCardTypeManagement(List<BgabGascba03> list);
	
	public int updateBusunessCardPoInfo(BgabGascba02 dto);
	
	public BgabGascba02 getSelectBusinessCardRejectSubmitPoSearch(BgabGascba02 dto);
	
	public int updateInfoBcToReject(BgabGascba02 dto);
	
	public String getSelectCountBCToConfirm(BgabGascba02 keyParamDto);
	public List<BgabGascba01> getSelectListBCToConfirm(BgabGascba02 keyParamDto);
}
