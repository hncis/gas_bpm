package com.hncis.training.dao;

import java.util.List;

import com.hncis.taxi.vo.BgabGasctx01;
import com.hncis.taxi.vo.BgabGasctx02;
import com.hncis.taxi.vo.BgabGasctx04;
import com.hncis.training.vo.BgabGasctr01;

public interface TrainingDao {

	/**
	 * request save
	 * @param BgabGasctr01
	 * @return
	 */
	public Object insertInfoTRToRequest(BgabGasctr01 dto);

	/**
	 * request update
	 * @param BgabGasctr01
	 * @return
	 */
	public Object updateInfoTRToRequest(BgabGasctr01 dto);
	
	/**
	 * request delete
	 * @param BgabGasctr01
	 * @return
	 */
	public Object deleteInfoTRToRequest(BgabGasctr01 dto);
	
	/**
	 * request count
	 * request search
	 * @param BgabGasctr01
	 * @return
	 */
	public BgabGasctr01 getSelectInfoTRToRequest(BgabGasctr01 dto);
	public BgabGasctr01 getSelectInfoTRToRequestByIfId(BgabGasctr01 dto);
	public List<BgabGasctr01> getSelectListTRToRequest(BgabGasctr01 dto);
	
	/**
	 * accept count
	 * accept search
	 * @param keyParamDto
	 * @return
	 */
	public String getSelectCountTRToAccept(BgabGasctr01 dto);
	public List<BgabGasctr01> getSelectListTRToAccept(BgabGasctr01 dto);
	
	/**
	 * request
	 * @param BgabGasctr01
	 * @return
	 */
	public Object setApproveTRToRequest(BgabGasctr01 dto);
	public int setApproveCancelTRToRequest(BgabGasctr01 dto);
	
	/**
	 * confirm
	 * @param BgabGasctr01
	 * @return
	 */
	public Object setConfirmTRToRequest(BgabGasctr01 dto);
	public Object setConfirmCancelTRToRequest(BgabGasctr01 dto);
	
	public int updateInfoTrToReject(BgabGasctr01 dto);
}
