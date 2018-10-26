package com.hncis.controller.dashBoard;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.StringUtil;
import com.hncis.controller.AbstractController;
import com.hncis.dashBoard.manager.DashBoardManager;
import com.hncis.dashBoard.vo.DashBoardVo;

@Controller
public class DashBoardController extends AbstractController{

	@Autowired
    @Qualifier("dashBoardManagerImpl")
	private DashBoardManager dashBoardManager;

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/dashBoard/doSearchDbToDeptCombo.do")
	public ModelAndView doSearchDbToDeptCombo(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="corp_cd", required=true) String corp_cd) throws Exception{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		List<DashBoardVo> codeList = null;

		DashBoardVo dto = new DashBoardVo();
		dto.setCorp_cd(corp_cd);

		codeList = dashBoardManager.selectDbToDeptCombo(dto);
		jsonArr = new JSONBaseArray();

		json = new JSONBaseVO();
		json.put("name", HncisMessageSource.getMessage("total"));
		json.put("value", "");
		jsonArr.add(json);

		for(DashBoardVo targetBean : codeList){

			json = new JSONBaseVO();
			json.put("value", StringUtil.isNullToStrTrm(targetBean.getDept_code()));
			json.put("name", StringUtil.isNullToStrTrm(targetBean.getDept_name()));
			jsonArr.add(json);
		}
		jso.put("DEPT_LIST", jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

	/**
	 * Do search chart to dash board.
	 *
	 * @param req the req
	 * @param res the res
	 * @param key_year the key_year
	 * @return the model and view
	 * @throws Exception the exception
	 */

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/dashBoard/doSearchDbToDashBoard.do")
	public ModelAndView doSearchDbToDashBoard(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException{
		DashBoardVo dto = (DashBoardVo) getJsonToBean(paramJson, DashBoardVo.class);

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		List<DashBoardVo>codeList1 = dashBoardManager.selectDbToDashBoard(dto);

		for(DashBoardVo targetBean : codeList1){

			json = new JSONBaseVO();
			jsonArr = new JSONBaseArray();

			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM01()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM02()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM03()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM04()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM05()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM06()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM07()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM08()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM09()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM10()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM11()));
			jsonArr.add(StringUtil.isNullToStrTrm(targetBean.getM12()));
			jso.put("CHART", jsonArr);
		}



		jsonArr = new JSONBaseArray();

		jsonArr.add(HncisMessageSource.getMessage("SEARCH.0000"));

		jso.put("MSG",jsonArr);

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}

}
