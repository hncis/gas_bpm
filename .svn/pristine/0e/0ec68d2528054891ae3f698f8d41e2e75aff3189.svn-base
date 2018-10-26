package com.hncis.uniform.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.vo.CommonMessage;
import com.hncis.uniform.vo.BgabGascaf01Dto;
import com.hncis.uniform.vo.BgabGascaf02Dto;
import com.hncis.uniform.vo.BgabGascaf03Dto;

@Transactional
public interface UniformManager {
	public List<BgabGascaf02Dto> selectTypeofclothesComboList(String corp_cd);
	public List<BgabGascaf02Dto> selectColorComboList(String corp_cd);
	public List<BgabGascaf02Dto> selectSizeComboList(String corp_cd);
	
	public int selectCountToRequest(BgabGascaf01Dto vo);
	public List<BgabGascaf01Dto> selectListToRequest(BgabGascaf01Dto vo);
	public CommonMessage insertListToRequest(List<BgabGascaf01Dto> list);
	
	public int selectCountToList(BgabGascaf01Dto vo);
	public List<BgabGascaf01Dto> selectListToList(BgabGascaf01Dto vo);
	public int deleteRequestToList(List<BgabGascaf01Dto> list);
	
	public int selectCountToConfirm(BgabGascaf01Dto vo);
	public List<BgabGascaf01Dto> selectListToConfirm(BgabGascaf01Dto vo);
	public CommonMessage updateUniformConfirmToConfirm(List<BgabGascaf01Dto> list);
	public CommonMessage updateUniformConfirmCancelToConfirm(List<BgabGascaf01Dto> list);
	
	public List<BgabGascaf02Dto> selectTypeofclothesListToItemInfo(BgabGascaf02Dto vo);
	public List<BgabGascaf02Dto> selectColorListToItemInfo(BgabGascaf02Dto vo);
	public List<BgabGascaf02Dto> selectSizeListToItemInfo(BgabGascaf02Dto vo);
	public int saveListToItemInfo(List<BgabGascaf02Dto> iList, List<BgabGascaf02Dto> uList);
	public int deleteListToItemInfo(List<BgabGascaf02Dto> list);
	
	public int selectCountToStockManagement(BgabGascaf03Dto vo);
	public List<BgabGascaf03Dto> selectListToStockManagement(BgabGascaf03Dto vo);
	public CommonMessage insertListToStockManagement(BgabGascaf03Dto vo);
	public CommonMessage deleteListToStockManagement(List<BgabGascaf03Dto> list);
	
	public CommonMessage updateUniformListToReject(List<BgabGascaf01Dto> list);
	public CommonMessage updateConfirmListToRequest(List<BgabGascaf01Dto> vo);

	public int selectCountToStockManagementList(BgabGascaf03Dto vo);
	public List<BgabGascaf03Dto> selectListToStockManagementList(BgabGascaf03Dto vo);
	public List<BgabGascaf03Dto> selectListToStockDetailIn(BgabGascaf03Dto vo);
	public int selectCountToStockDetailOut(BgabGascaf03Dto vo);
	public List<BgabGascaf03Dto> selectListToStockDetailOut(BgabGascaf03Dto vo);
	public BgabGascaf03Dto selectGridStockDetailBasic(BgabGascaf03Dto vo);
	public int insertListToStockManagementList(List<BgabGascaf03Dto> iList, List<BgabGascaf03Dto> uList);
}
