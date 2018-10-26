package com.hncis.cooperator.dao;

import java.util.List;

import com.hncis.cooperator.vo.BgabGascco01Dto;
import com.hncis.cooperator.vo.BgabGascco02Dto;

public interface CooperatorDao {
	
	public String selectXcoCorpIdx(BgabGascco01Dto co01Dto);
	public int mergeXcoToRequestByBasic(BgabGascco01Dto co01Dto);
	public int mergeXcoToRequestByPic(BgabGascco02Dto co02Dto);
	public BgabGascco01Dto selectInfoXcoToRequest(BgabGascco01Dto co01Dto);
	public List<BgabGascco02Dto> selectPicListXcoToRequest(BgabGascco02Dto co02Dto);
	public int deleteXcoToRequest(BgabGascco01Dto co01Dto);
	public int deleteXcoPicToRequest(BgabGascco02Dto co02Dto);
	public int deleteXcoPicToList(List<BgabGascco02Dto> co02ListDto);
	public List<BgabGascco02Dto> selectXcoToList(BgabGascco01Dto co01Dto);
}
