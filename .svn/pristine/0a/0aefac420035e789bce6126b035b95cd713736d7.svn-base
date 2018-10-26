package com.hncis.businessCard.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.businessCard.vo.BgabGascba01;
import com.hncis.businessCard.vo.BgabGascba02;
import com.hncis.businessCard.vo.BgabGascba03;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;

@Transactional
public interface BusinessCardManager {
	/**
	 * request count
	 * request search
	 * @param cgabGascba01
	 * @return
	 * @throws Exception
	 */
	public BgabGascba01 getSelectInfoBCToRequest(BgabGascba02 keyParamDto);

	/**
	 * request apply
	 * @param cgabGascba01
	 * @return
	 * @throws Exception
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
	 */
	public Object updateInfoBCToRequest(BgabGascba02 keyParamDto);
	/**
	 * accept count
	 * accept search
	 * @param keyParamDto
	 * @return
	 */
	public int getSelectCountBCToAccept(BgabGascba02 keyParamDto);
	public List<BgabGascba01> getSelectListBCToAccept(BgabGascba02 keyParamDto);

	/**
	 * accept delete
	 * @param keyParamDto
	 * @return
	 */
	public Object deleteListBCToAccept(List<BgabGascba02> keyParamDto);

	/**
	 * accept accept
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
	 * approval
	 * @param keyParamDto
	 * @param appInfo
	 * @param message
	 * @param req
	 * @return
	 */
	public CommonMessage setApproval(BgabGascba02 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req) throws SessionException;

	/**
	 * approve cancel
	 * @param keyParamDto
	 * @param appInfo
	 * @param message
	 * @param req
	 * @return
	 */
	public CommonApproval setApprovalCancel(BgabGascba02 keyParamDto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);

	/**
	 * submit search
	 * @param keyParamDto
	 * @return
	 */
	public BgabGascba01 getSelectInfoBCToSubmit(BgabGascba02 keyParamDto);

	/**
	 * approval info search
	 * @param keyParamDto
	 * @return
	 */
	public String getSelectApprovalInfo(BgabGascba02 keyParamDto);
	/**
	 * Card type search
	 * @param BgabGascba03
	 * @return
	 */
	public int getSelectCountBcToCardTypeManagement(BgabGascba03 dto);
	public List<BgabGascba03> getSelectListBcToCardTypeManagement(BgabGascba03 dto);
	/**
	 * Card type insert
	 * @param BgabGascba03
	 * @return
	 */
	public int insertListBcToCardTypeManagement(List<BgabGascba03> list);
	/**
	 * Card type update
	 * @param BgabGascba03
	 * @return
	 */
	public int updateListBcToCardTypeManagement(List<BgabGascba03> list);
	/**
	 * Card type delete
	 * @param BgabGascba03
	 * @return
	 */
	public int deleteListBcToCardTypeManagement(List<BgabGascba03> list);

	public CommonMessage updateInfoBcToReject(BgabGascba02 dto, HttpServletRequest req) throws SessionException;

	/**
	 * confirm count
	 * confirm search
	 * @param keyParamDto
	 * @return
	 */
	public int getSelectCountBCToConfirm(BgabGascba02 keyParamDto);
	public List<BgabGascba01> getSelectListBCToConfirm(BgabGascba02 keyParamDto);
}
