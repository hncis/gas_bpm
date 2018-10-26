package com.hncis.trafficTicket.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.common.vo.CommonMessage;
import com.hncis.trafficTicket.vo.BgabGascTm01;
import com.hncis.trafficTicket.vo.BgabGascTm02;

@Transactional
public interface TrafficTicketManager {
	/**
	 * management list
	 */
	public int getSelectByXtm01ListCount(BgabGascTm01 gsReqVo);
	public List<BgabGascTm01> getSelectByXtm01List(BgabGascTm01 gsReqVo);
	public int doInsertByXtm01List(BgabGascTm01 tm01Svvo);
	public int doUpdateByXtm01List(BgabGascTm01 tm01MdVo);
	public int doDeleteByXtm01List(BgabGascTm01 tm01vo);
	public CommonMessage doActionByXtm01List(BgabGascTm01 tm01vo);
	
	/**
	 * list
	 */
	public int getSelectByXtm02ListCount(BgabGascTm01 gsReqVo);
	public List<BgabGascTm01> getSelectByXtm02List(BgabGascTm01 gsReqVo);
	public List<BgabGascTm01> getSelectByXtm02ExcelList(BgabGascTm01 gsReqVo);
	
	/**
	 * view info
	 */
	public BgabGascTm01 doSearchToCarInfo(BgabGascTm01 gsParamVo);
	public BgabGascTm01 getSearchByXtm03ViewInfo(BgabGascTm01 gsParamVo);
	public int doUpdateByXtm03Accept(BgabGascTm01 gsParamVo);
	
	/**
	 * HR Report list
	 */
	public int getSelectByXtm04ListCount(BgabGascTm01 gsReqVo);
	public List<BgabGascTm01> getSelectByXtm04List(BgabGascTm01 gsReqVo);
	public int doUpdateByXtm04ListDone(BgabGascTm01 gsParamVo);
	public int doUpdateByXtm04ListDoneCancel(BgabGascTm01 gsParamVo);
	
	/**
	 * HR Report list
	 */
	public int getSelectByXtm05ListCount(BgabGascTm02 gsReqVo);
	public List<BgabGascTm02> getSelectByXtm05List(BgabGascTm02 gsReqVo);
	public int saveByXtm05List(List<BgabGascTm02> iList, List<BgabGascTm02> uList);
	public int deleteByXtm05List(List<BgabGascTm02> gsParamVo);
	
	public List<BgabGascTm02> getSelectByDescrCommboList();
	
	public BgabGascTm02 getSelectToTransitoInfo(BgabGascTm02 dto);
}
