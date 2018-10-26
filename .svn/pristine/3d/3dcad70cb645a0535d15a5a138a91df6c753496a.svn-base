package com.hncis.cooperator.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.cooperator.vo.BgabGascco01Dto;
import com.hncis.cooperator.vo.BgabGascco02Dto;

@Transactional
public interface CooperatorManager {
	
	public String selectXcoCorpIdx(BgabGascco01Dto co01Dto);
	public int insertXcoToRequest(BgabGascco01Dto co01Dto, List<BgabGascco02Dto> co02DtoI);
	public BgabGascco01Dto selectInfoXcoToRequest(BgabGascco01Dto co01Dto);
	public List<BgabGascco02Dto> selectPicListXcoToRequest(BgabGascco02Dto co02Dto);
	public int deleteXcoToRequest(BgabGascco01Dto co01Dto);
	public int deleteXcoPicToRequest(BgabGascco02Dto co02Dto);
	public int deleteXcoPicToList(List<BgabGascco02Dto> co02ListDto);
	public List<BgabGascco02Dto> selectXcoToList(BgabGascco01Dto co01Dto);

}
