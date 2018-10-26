package com.hncis.dashBoard.dao.impl;

import java.util.List;

import com.hncis.common.dao.CommonDao;
import com.hncis.dashBoard.dao.DashBoardDao;
import com.hncis.dashBoard.vo.DashBoardVo;

public class DashBoardDaoImplByIBatis extends CommonDao implements DashBoardDao{

	private final String SELECT_DB_TO_DEPT_COMBO 					= "selectDbToDeptCombo";

	private final String SELECT_DB_TO_DASH_BOARD_FOR_ET 			= "selectDbToDashBoardForEt";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_VE 			= "selectDbToDashBoardForVe";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_ML 			= "selectDbToDashBoardForMl";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_IT 			= "selectDbToDashBoardForIt";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_FP 			= "selectDbToDashBoardForFp";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_RM 			= "selectDbToDashBoardForRm";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_BC 			= "selectDbToDashBoardForBc";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_TX 			= "selectDbToDashBoardForTx";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_TW 			= "selectDbToDashBoardForTw";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_SB 			= "selectDbToDashBoardForSb";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_BT 			= "selectDbToDashBoardForBt";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_CC 			= "selectDbToDashBoardForCc";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_PS 			= "selectDbToDashBoardForPs";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_GS 			= "selectDbToDashBoardForGs";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_OS 			= "selectDbToDashBoardForOs";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_PO 			= "selectDbToDashBoardForPo";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_TT 			= "selectDbToDashBoardForTt";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_CE 			= "selectDbToDashBoardForCe";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_VL 			= "selectDbToDashBoardForVl";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_RC 			= "selectDbToDashBoardForRc";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_TR 			= "selectDbToDashBoardForTr";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_LV 			= "selectDbToDashBoardForLv";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_FC 			= "selectDbToDashBoardForFc";
	private final String SELECT_DB_TO_DASH_BOARD_FOR_FJ 			= "selectDbToDashBoardForFj";

	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDeptCombo(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DEPT_COMBO, dto);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForEt(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_ET, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForVe(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_VE, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForMl(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_ML, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForIt(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_IT, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForFp(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_FP, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForRm(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_RM, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForBc(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_BC, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForTx(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_TX, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForTw(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_TW, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForSb(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_SB, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForBt(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_BT, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForCc(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_CC, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForPs(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_PS, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForGs(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_GS, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForOs(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_OS, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForPo(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_PO, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForTt(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_TT, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForCe(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_CE, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForRc(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_RC, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForVl(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_VL, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForTr(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_TR, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForLv(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_LV, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForFc(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_FC, dto);
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<DashBoardVo> selectDbToDashBoardForFJ(DashBoardVo dto) {
		return getSqlMapClientTemplate().queryForList(SELECT_DB_TO_DASH_BOARD_FOR_FJ, dto);
	}
}
