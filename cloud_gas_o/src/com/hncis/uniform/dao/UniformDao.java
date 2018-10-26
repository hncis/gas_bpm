package com.hncis.uniform.dao;

import java.util.List;

import com.hncis.uniform.vo.BgabGascaf01Dto;
import com.hncis.uniform.vo.BgabGascaf02Dto;
import com.hncis.uniform.vo.BgabGascaf03Dto;


public interface UniformDao {
	public List<BgabGascaf02Dto> selectTypeofclothesComboList(String corp_cd);
	public List<BgabGascaf02Dto> selectColorComboList(String corp_cd);
	public List<BgabGascaf02Dto> selectSizeComboList(String corp_cd);
	
	public String selectUniformCycleToRequest(BgabGascaf01Dto vo);
	public int selectCountToRequest(BgabGascaf01Dto vo);
	public List<BgabGascaf01Dto> selectListToRequest(BgabGascaf01Dto vo);
	public int selectStockCheckToRequest(BgabGascaf01Dto vo);
	public int insertListToRequest(List<BgabGascaf01Dto> vo);
	
	public int selectCountToList(BgabGascaf01Dto vo);
	public List<BgabGascaf01Dto> selectListToList(BgabGascaf01Dto vo);
	public int deleteRequestToList(List<BgabGascaf01Dto> vo);
	
	public int selectCountToConfirm(BgabGascaf01Dto vo);
	public List<BgabGascaf01Dto> selectListToConfirm(BgabGascaf01Dto vo);
	public int updateUniformStatusToConfirm(BgabGascaf01Dto list);
	
	public List<BgabGascaf02Dto> selectTypeofclothesListToItemInfo(BgabGascaf02Dto vo);
	public List<BgabGascaf02Dto> selectColorListToItemInfo(BgabGascaf02Dto vo);
	public List<BgabGascaf02Dto> selectSizeListToItemInfo(BgabGascaf02Dto vo);
	public int selectMaxSeqToItemInfo(BgabGascaf02Dto vo);
	public int insertListToItemInfo(List<BgabGascaf02Dto> list);
	public int updateListToItemInfo(List<BgabGascaf02Dto> list);
	public int deleteListToItemInfo(List<BgabGascaf02Dto> list);
	
	public int selectCountToStockManagement(BgabGascaf03Dto vo);
	public List<BgabGascaf03Dto> selectListToStockManagement(BgabGascaf03Dto vo);
	public String selectMaxSeqToStockManagement(BgabGascaf03Dto vo);
	public int selectUniformTotalCountToStockManagement(BgabGascaf03Dto vo);
	public int insertListToStockManagement(BgabGascaf03Dto vo);
	public int deleteListToStockManagement(BgabGascaf03Dto vo);
	
	public int updateUniformListToReject(BgabGascaf01Dto vo);
	public int updateUniformConfirmListToRequest(BgabGascaf01Dto vo);
	public String selectUniformRemainQty(BgabGascaf01Dto vo);
	public String selectUniformClrQty(BgabGascaf01Dto vo);
	public BgabGascaf01Dto selectUniformQuantityChkUniformNm(BgabGascaf01Dto vo);
	public int deleteUniformItemToDownCode(BgabGascaf02Dto vo);
	public int deleteUniformItemToDownCodeSize(BgabGascaf02Dto vo);
	
	public int selectCountToStockManagementList(BgabGascaf03Dto vo);
	public List<BgabGascaf03Dto> selectListToStockManagementList(BgabGascaf03Dto vo);
	public List<BgabGascaf03Dto> selectListToStockDetailIn(BgabGascaf03Dto vo);
	public int selectCountToStockDetailOut(BgabGascaf03Dto vo);
	public List<BgabGascaf03Dto> selectListToStockDetailOut(BgabGascaf03Dto vo);
	public BgabGascaf03Dto selectGridStockDetailBasic(BgabGascaf03Dto vo);
	
	public int updateListToStockManagementList(List<BgabGascaf03Dto> uList);
	public int insertListToStockManagementList(List<BgabGascaf03Dto> iList);
}
