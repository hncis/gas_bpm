package com.hncis.fuelCost.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.fuelCost.dao.FuelCostDao;
import com.hncis.fuelCost.vo.BgabGascfc01Dto;
import com.hncis.fuelCost.vo.BgabGascfc02Dto;

public class FuelCostDaoImplByIBatis extends CommonDao implements FuelCostDao {
	private static final String uncheck = "unchecked";
	@Override
	public int insertXfc01Info(BgabGascfc01Dto dto){
		return super.insert("insertXfc01Info", dto);
	}
	@Override
	public int updateXfc01Info(BgabGascfc01Dto dto){
		return super.update("updateXfc01Info", dto);
	}
	@Override
	public int deleteXfc01Info(BgabGascfc01Dto dto){
		return super.delete("deleteXfc01Info", dto);
	}

	@Override
	public BgabGascfc01Dto selectXfc01Info(BgabGascfc01Dto dto){
		return (BgabGascfc01Dto)getSqlMapClientTemplate().queryForObject("selectXfc01Info", dto);
	}
	
	@Override
	public BgabGascfc01Dto selectXfc01InfoByIfId(BgabGascfc01Dto dto){
		return (BgabGascfc01Dto)getSqlMapClientTemplate().queryForObject("selectXfc01InfoByIfId", dto);
	}

	@Override
	public int selectXfc01InfoListCount(BgabGascfc01Dto dto){
		return (Integer)getSqlMapClientTemplate().queryForObject("selectXfc01InfoListCount", dto);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascfc01Dto> selectXfc01InfoList(BgabGascfc01Dto dto){
		return getSqlMapClientTemplate().queryForList("selectXfc01InfoList", dto);
	}

	@Override
	public int updateXfcPgsStCd(BgabGascfc01Dto dto){
		return super.update("updateXfcPgsStCd", dto);
	}

	@Override
	public int selectXfcRequestInfoListCount(BgabGascfc01Dto dto){
		return (Integer)getSqlMapClientTemplate().queryForObject("selectXfcRequestInfoListCount", dto);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascfc01Dto> selectXfcRequestInfoList(BgabGascfc01Dto dto){
		return getSqlMapClientTemplate().queryForList("selectXfcRequestInfoList", dto);
	}

	@Override
	public int selectXfc04InfoListCount(BgabGascfc01Dto dto){
		return (Integer)getSqlMapClientTemplate().queryForObject("selectXfc04InfoListCount", dto);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascfc01Dto> selectXfc04InfoList(BgabGascfc01Dto dto){
		return getSqlMapClientTemplate().queryForList("selectXfc04InfoList", dto);
	}

	@Override
	public int updateXfc05Info(BgabGascfc02Dto dto){
		return super.update("updateXfc05Info", dto);
	}

	@Override
	public int updateXfc05InfoPast(BgabGascfc02Dto dto){
		return super.update("updateXfc05InfoPast", dto);
	}

	@Override
	public int insertXfc05Info(BgabGascfc02Dto dto){
		return super.insert("insertXfc05Info", dto);
	}

	@Override
	public BgabGascfc02Dto selectXfc05Info(BgabGascfc02Dto dto){
		return (BgabGascfc02Dto)getSqlMapClientTemplate().queryForObject("selectXfc05Info", dto);
	}

	@Override
	public int selectXfc05InfoListCount(BgabGascfc02Dto dto){
		return (Integer)getSqlMapClientTemplate().queryForObject("selectXfc05InfoListCount", dto);
	}

	@Override
	@SuppressWarnings(uncheck)
	public List<BgabGascfc02Dto> selectXfc05InfoList(BgabGascfc02Dto dto){
		return getSqlMapClientTemplate().queryForList("selectXfc05InfoList", dto);
	}
	
	@Override
	public int updateXfcToReject(BgabGascfc01Dto dto){
		return update("updateXfcToReject", dto);
	}
}