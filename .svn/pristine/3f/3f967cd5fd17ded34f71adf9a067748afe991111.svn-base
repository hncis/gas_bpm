package com.hncis.trafficTicket.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.trafficTicket.dao.TrafficTicketServiceDao;
import com.hncis.trafficTicket.vo.BgabGascTm01;
import com.hncis.trafficTicket.vo.BgabGascTm02;

public class TrafficTicketServiceDaoImplByIbatis extends CommonDao implements TrafficTicketServiceDao{
	/**
	 * management list
	 */
	public int getSelectByXtm01ListCount(BgabGascTm01 gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXtm01ListCount", gsReqVo));
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascTm01> getSelectByXtm01List(BgabGascTm01 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXtm01List", gsReqVo);
	}
	
	public int doInsertByXtm01List(BgabGascTm01 tm01Svvo){
		return super.insert("insertByXtm01List", tm01Svvo);
	}
	
	public int doUpdateByXtm01List(BgabGascTm01 tm01MdVo){
		return super.update("updateByXtm01List", tm01MdVo);
	}
	
	public int doDeleteByXtm01List(BgabGascTm01 tm01DlVo){
		return super.delete("deleteByXtm01List", tm01DlVo);
	}
	
	public int doActionByXtm01List(BgabGascTm01 gsParamVo) {
		return super.update("actionByXtm01List", gsParamVo);
	}
	
	/**
	 * list
	 */
	public int getSelectByXtm02ListCount(BgabGascTm01 gsReqVo){
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXtm02ListCount", gsReqVo));
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascTm01> getSelectByXtm02List(BgabGascTm01 gsReqVo){
		return getSqlMapClientTemplate().queryForList("selectByXtm02List", gsReqVo);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascTm01> getSelectByXtm02ExcelList(BgabGascTm01 gsReqVo){
		//return getSqlMapClientTemplate().queryForList("selectByXtm02ExcelList", gsReqVo);
		return getSqlMapClientTemplate().queryForList("selectByXtm02List", gsReqVo);
	}
	
	/**
	 * view info
	 */
	public BgabGascTm01 doSearchToCarInfo(BgabGascTm01 gsParamVo){
		return (BgabGascTm01)getSqlMapClientTemplate().queryForObject("selectToCarInfo", gsParamVo);
	}
	public BgabGascTm01 getSearchByXtm03ViewInfo(BgabGascTm01 gsParamVo){
		return (BgabGascTm01)getSqlMapClientTemplate().queryForObject("selectByXtm03ViewInfo", gsParamVo);
	}

	public int doUpdateByXtm03Accept(BgabGascTm01 gsParamVo) {
		return super.update("updateByXtm03Accept", gsParamVo);
	}
	
	/**
	 * HR Report list
	 */
	public int getSelectByXtm04ListCount(BgabGascTm01 gsReqVo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXtm04ListCount", gsReqVo));
	}

	@SuppressWarnings("unchecked")
	public List<BgabGascTm01> getSelectByXtm04List(BgabGascTm01 gsReqVo) {
		return getSqlMapClientTemplate().queryForList("selectByXtm04List", gsReqVo);
	}
	
	public int doUpdateByXtm04ListDone(BgabGascTm01 gsParamVo) {
		return super.update("updateByXtm04ListDone", gsParamVo);
	}

	public int doUpdateByXtm04ListDoneCancel(BgabGascTm01 gsParamVo) {
		return super.update("updateByXtm04ListDoneCancel", gsParamVo);
	}
	
	public int updateTrafficTicketPoInfo(BgabGascTm01 gsParamVo){
		return update("updateTrafficTicketPoInfo", gsParamVo);
	}
	
	/**
	 * Code Management list
	 */
	public int getSelectByXtm05ListCount(BgabGascTm02 gsReqVo) {
		return Integer.parseInt((String)getSqlMapClientTemplate().queryForObject("selectByXtm05ListCount", gsReqVo));
	}

	@SuppressWarnings("unchecked")
	public List<BgabGascTm02> getSelectByXtm05List(BgabGascTm02 gsReqVo) {
		return getSqlMapClientTemplate().queryForList("selectByXtm05List", gsReqVo);
	}
	
	public int insertByXtm05List(List<BgabGascTm02> gsParamVo) {
		return insert("insertByXtm05List", gsParamVo);
	}

	public int updateByXtm05List(List<BgabGascTm02> gsParamVo) {
		return update("updateByXtm05List", gsParamVo);
	}
	
	public int deleteByXtm05List(List<BgabGascTm02> gsParamVo){
		return delete("deleteByXtm05List", gsParamVo);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascTm02> getSelectByDescrCommboList(){
		return getSqlMapClientTemplate().queryForList("selectByDescrCommboList");
	}
	
	public BgabGascTm02 getSelectToTransitoInfo(BgabGascTm02 dto){
		return (BgabGascTm02)getSqlMapClientTemplate().queryForObject("selectToTransitoInfo", dto);
	}
}
