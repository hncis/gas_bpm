
package com.hncis.training.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.vo.CommonApproval;
import com.hncis.common.vo.CommonMessage;
import com.hncis.taxi.vo.BgabGasctx01;
import com.hncis.taxi.vo.BgabGasctx02;
import com.hncis.taxi.vo.BgabGasctx04;
import com.hncis.training.vo.BgabGasctr01;

@Transactional
public interface TrainingManager {

	/**
	 * request apply
	 * @param bgabGasctr01
	 * @return
	 * @throws Exception
	 */
	public Object insertInfoTRToRequest(BgabGasctr01 dto);
	
	/**
	 * request update
	 * @param bgabGasctr01
	 * @return
	 * @throws Exception
	 */
	public Object updateInfoTRToRequest(BgabGasctr01 dto);
	
	/**
	 * request delete
	 * @param bgabGasctr01
	 * @return
	 */
	public Object deleteInfoTRToRequest(BgabGasctr01 dto);
	
	/**
	 * request count
	 * request search
	 * @param bgabGasctr01
	 * @return
	 * @throws Exception
	 */
	public BgabGasctr01 getSelectInfoTRToRequest(BgabGasctr01 dto);
	public BgabGasctr01 getSelectInfoTRToRequestByIfId(BgabGasctr01 dto);
	List<BgabGasctr01> getSelectListTRToRequest(BgabGasctr01 dto);
	
	/**
	 * accept count
	 * accept search
	 * @param keyParamDto
	 * @return
	 */
	public int getSelectCountTRToAccept(BgabGasctr01 dto);
	List<BgabGasctr01> getSelectListTRToAccept(BgabGasctr01 dto);
	
	/**
	 * request
	 * @param bgabGasctr01
	 * @return
	 * @throws Exception
	 */
	public CommonMessage setApproveTRToRequest(BgabGasctr01 dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	public CommonMessage setApproveCancelTRToRequest(BgabGasctr01 dto, CommonApproval appInfo, CommonMessage message, HttpServletRequest req);
	
	/**
	 * confirm
	 * @param bgabGasctr01
	 * @return
	 * @throws Exception
	 */
	public Object setConfirmTRToRequest(BgabGasctr01 dto);
	public Object setConfirmCancelTRToRequest(BgabGasctr01 dto);
	public CommonMessage updateInfoTrToReject(BgabGasctr01 dto);
	public CommonMessage updateConfirmTRToRequest(BgabGasctr01 dto);
}
