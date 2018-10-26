package com.hncis.dashBoard.manager;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.dashBoard.vo.DashBoardVo;

@Transactional
public interface DashBoardManager {

	List<DashBoardVo> selectDbToDeptCombo(DashBoardVo dto);

	List<DashBoardVo> selectDbToDashBoard(DashBoardVo dto);

}
