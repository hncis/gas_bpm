package com.hncis.controller.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hncis.board.manager.BoardManager;
import com.hncis.board.vo.BgabGasc01DtlDto;
import com.hncis.board.vo.BgabGasc01KeyDto;
import com.hncis.common.Constant;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.base.JSONBaseArray;
import com.hncis.common.base.JSONBaseVO;
import com.hncis.common.exception.impl.HncisException;
import com.hncis.common.exception.impl.SessionException;
import com.hncis.common.manager.CommonManager;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonList;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonUserInfo;
import com.hncis.controller.AbstractController;
import com.hncis.system.vo.BgabGascz019Dto;

@Controller
public class BoardController extends AbstractController {
	@Autowired
	@Qualifier("boardManagerImpl")
	private BoardManager boardManager;

	@Autowired
	@Qualifier("commonManagerImpl")
	private CommonManager commonManager;

	/********************************************************************************************
	 *                                             NOTICE                                       *
	 ********************************************************************************************/
	/**
	 * notice list search
	 * @param req
	 * @param res
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchGridToNotice.do")
	public ModelAndView doSearchGridToNotice(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGasc01KeyDto keyParamDto = (BgabGasc01KeyDto) getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = boardManager.getSelectCountBDToNotice(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(boardManager.getSelectListBDToNotice(keyParamDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * notice detail search
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchDetailBDToNotice.do")
	public ModelAndView doSearchDetailBDToNotice(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01KeyDto keyDto = (BgabGasc01KeyDto)getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		BgabGasc01DtlDto rsDtlDto = new BgabGasc01DtlDto();
		keyDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		rsDtlDto = boardManager.getSelectDetailBDToNotice(keyDto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsDtlDto != null){
			rsDtlDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsDtlDto).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * notice write
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doWriteBDToNotice.do")
	public void doWriteBDToNotice(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, IOException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.insertInfoBDToNotice(req, res, dtlDto, message);
		}
	}

	/**
	 * notice modify
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doModifyBDToNotice.do")
	public void doModifyBDToNotice(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, IOException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.updateInfoBDToNotice(req, res, dtlDto, message);
		}
	}

	/**
	 * notice delete
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doDeleteBDToNotice.do")
	public ModelAndView doDeleteBDToNotice(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
		dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		boardManager.deleteInfoBDToNotice(dtlDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/********************************************************************************************
	 *                                             Q & A                                        *
	 ********************************************************************************************/
	/**
	 * qna list search
	 * @param req
	 * @param res
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchGridToQna.do")
	public ModelAndView doSearchGridToQna(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGasc01KeyDto keyParamDto = (BgabGasc01KeyDto) getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = boardManager.getSelectCountBDToQna(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(boardManager.getSelectListBDToQna(keyParamDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * qna detail search
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchDetailBDToQna.do")
	public ModelAndView doSearchDetailBDToQna(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01KeyDto keyDto = (BgabGasc01KeyDto)getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		BgabGasc01DtlDto rsDtlDto = new BgabGasc01DtlDto();
		keyDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		rsDtlDto = boardManager.getSelectDetailBDToQna(keyDto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsDtlDto != null){
			rsDtlDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsDtlDto).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * qna write
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doWriteBDToQna.do")
	public void doWriteBDToQna(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.insertInfoBDToQna(req, res, dtlDto, message);
		}
	}

	/**
	 * qna modify
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doModifyBDToQna.do")
	public void doModifyBDToQna(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.updateInfoBDToQna(req, res, dtlDto, message);
		}
	}

	/**
	 * qna delete
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doDeleteBDToQna.do")
	public ModelAndView doDeleteBDToQna(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
		dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		boardManager.deleteInfoBDToQna(dtlDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * qna reply write
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doReplyBDToQna.do")
	public void doReplyBDToQna(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.replyInfoBDToQna(req, res, dtlDto, message);
		}
	}

	/**
	 * In charge search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchInChargeBdToQna.do")
	public ModelAndView doSearchInChargeBdToQna(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01KeyDto keyDto = (BgabGasc01KeyDto)getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		CommonUserInfo rsUsrDto = new CommonUserInfo();
		keyDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		rsUsrDto = boardManager.getSelectInChargeBDToQna(keyDto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsUsrDto != null){
			rsUsrDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsUsrDto).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/********************************************************************************************
	 *                                             F A Q                                        *
	 ********************************************************************************************/
	/**
	 * faq list search
	 * @param req
	 * @param res
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchGridToFaq.do")
	public ModelAndView doSearchGridToFaq(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGasc01KeyDto keyParamDto = (BgabGasc01KeyDto) getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = boardManager.getSelectCountBDToFaq(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(boardManager.getSelectListBDToFaq(keyParamDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
	/**
	 * faq detail search
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchDetailBDToFaq.do")
	public ModelAndView doSearchDetailBDToFaq(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01KeyDto keyDto = (BgabGasc01KeyDto)getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		BgabGasc01DtlDto rsDtlDto = new BgabGasc01DtlDto();
		keyDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		rsDtlDto = boardManager.getSelectDetailBDToFaq(keyDto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsDtlDto != null){
			rsDtlDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsDtlDto).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * Faq write
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doWriteBDToFaq.do")
	public void doWriteBDToFaq(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.insertInfoBDToFaq(req, res, dtlDto, message);
		}
	}

	/**
	 * Faq modify
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doModifyBDToFaq.do")
	public void doModifyBDToFaq(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.updateInfoBDToFaq(req, res, dtlDto, message);
		}
	}

	/**
	 * Faq delete
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doDeleteBDToFaq.do")
	public ModelAndView doDeleteBDToFaq(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
		dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		boardManager.deleteInfoBDToFaq(dtlDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/********************************************************************************************
	 *                                           Claim                                          *
	 ********************************************************************************************/
	/**
	 * claim list search
	 * @param req
	 * @param res
	 * @param pageNumber
	 * @param pageSize
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchGridToClaim.do")
	public ModelAndView doSearchGridToClaim(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="page", required = false) String pageNumber,
			@RequestParam(value="rows", required = false) String pageSize,
			@RequestParam(value="paramJson", required=true) String paramJson) throws HncisException, SessionException{

		BgabGasc01KeyDto keyParamDto = (BgabGasc01KeyDto) getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		keyParamDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		if(StringUtil.isNullToString(pageNumber).equals("")){ pageNumber = "1"; }
		if(StringUtil.isNullToString(pageSize).equals("")){   pageSize = Constant.pageSize; }

		int currentPage = Integer.parseInt(pageNumber);
		int startRow    = (currentPage - 1)* Integer.parseInt(pageSize) +1;
		int endRow      = currentPage * Integer.parseInt(pageSize);
		int count       = boardManager.getSelectCountBDToClaim(keyParamDto);

		CommonList list = new CommonList();
		list.setPage(pageNumber);
		list.setTotal(Math.ceil((float)count / (float)Integer.parseInt(pageSize))+"");
		list.setRecords(Integer.toString(count));

		keyParamDto.setStartRow(startRow);
		keyParamDto.setEndRow(endRow);
		list.setRows(boardManager.getSelectListBDToClaim(keyParamDto));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}

	/**
	 * In charge search
	 * @param req
	 * @param res
	 * @param paramJson
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchInChargeBdToClaim.do")
	public ModelAndView doSearchInChargeBdToClaim(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01KeyDto keyDto = (BgabGasc01KeyDto)getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		CommonUserInfo rsUsrDto = new CommonUserInfo();
		keyDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		rsUsrDto = boardManager.getSelectInChargeBDToClaim(keyDto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsUsrDto != null){
			rsUsrDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsUsrDto).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * claim detail search
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchDetailBDToClaim.do")
	public ModelAndView doSearchDetailBDToClaim(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="paramJson", required=true) String paramJson)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01KeyDto keyDto = (BgabGasc01KeyDto)getJsonToBean(paramJson, BgabGasc01KeyDto.class);
		keyDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		BgabGasc01DtlDto rsDtlDto = new BgabGasc01DtlDto();
		rsDtlDto = boardManager.getSelectDetailBDToClaim(keyDto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsDtlDto != null){
			rsDtlDto.setMessage(HncisMessageSource.getMessage("SEARCH.0000"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsDtlDto).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("SEARCH.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * claim write
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doWriteBDToClaim.do")
	public void doWriteBDToClaim(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.insertInfoBDToClaim(req, res, dtlDto, message);
		}
	}

	/**
	 * claim modify
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doModifyBDToClaim.do")
	public void doModifyBDToClaim(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.updateInfoBDToClaim(req, res, dtlDto, message);
		}
	}

	/**
	 * claim delete
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doDeleteBDToClaim.do")
	public ModelAndView doDeleteBDToClaim(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
		dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		boardManager.deleteInfoBDToClaim(dtlDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("DELETE.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * claim reply write
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doReplyBDToClaim.do")
	public void doReplyBDToClaim(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		CommonMessage message = new CommonMessage();

		String contentType = req.getContentType();
		if(contentType != null && contentType.startsWith("multipart/form")){
			BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
			dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

			boardManager.replyInfoBDToClaim(req, res, dtlDto, message);
		}
	}

	/**
	 * Claim confirm password
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doConfirmPasswordToBoardForClaim.do")
	public ModelAndView doConfirmPasswordToBoardForClaim(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01KeyDto keyDto = (BgabGasc01KeyDto)getJsonToBean(bsicInfo, BgabGasc01KeyDto.class);
		BgabGasc01DtlDto dtlDto = (BgabGasc01DtlDto)getJsonToBean(bsicInfo, BgabGasc01DtlDto.class);
		keyDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		dtlDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		BgabGasc01DtlDto rsDtlDto = new BgabGasc01DtlDto();
		rsDtlDto = boardManager.getSelectDetailBDToClaim(keyDto);

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		if(rsDtlDto != null && dtlDto.getBod_pass() != null && dtlDto.getBod_pass().equals(rsDtlDto.getBod_pass())){
			rsDtlDto.setMessage(HncisMessageSource.getMessage("CONFIRM.0000"));
			rsDtlDto.setBod_passConfirm(true);
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(rsDtlDto).toString());
		}else{
			CommonMessage message = new CommonMessage();
			message.setMessage(HncisMessageSource.getMessage("CONFIRM.0001"));
			modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
			modelAndView.addObject("rstSuccess", "false");
		}
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	/**
	 * board read count
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doReadBDToBoard.do")
	public ModelAndView doReadBDToBoard(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="bsicInfo", required=true) String bsicInfo)throws HncisException, SessionException{
		ModelAndView modelAndView = null;

		BgabGasc01KeyDto keyDto = (BgabGasc01KeyDto)getJsonToBean(bsicInfo, BgabGasc01KeyDto.class);
		keyDto.setCorp_cd(SessionInfo.getSess_corp_cd(req));

		boardManager.updateReadBDToBoard(keyDto);

		CommonMessage message = new CommonMessage();
		message.setMessage(HncisMessageSource.getMessage("EDIT.0000"));

		modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(message).toString());
		modelAndView.addObject("uiType", "ajax");

		return modelAndView;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value="/hncis/board/doSearchToJobCombo.do")
	public ModelAndView doSearchToJobCombo(HttpServletRequest req, HttpServletResponse res,
		@RequestParam(value="codknd" , required=true) String codknd) throws HncisException, SessionException{

 		JSONBaseVO jso = new JSONBaseVO();
 		JSONBaseVO json = null;
		JSONBaseArray  jsonArr = null;

		String []codeStr = codknd.split(";");
		String []codePair = null;
		List<CommonCode> codeList = null;
		for( int i = 0; i < codeStr.length; i++ ){
			codePair = codeStr[i].split(":");
			if( codePair.length > 1){
				CommonCode code = new CommonCode();
				code.setCorp_cd(SessionInfo.getSess_corp_cd(req));
				code.setLocale(SessionInfo.getSess_locale(req));
				jsonArr = new  JSONBaseArray();
				code.setCodknd(codePair[1]);
				codeList = boardManager.getSelectToJobCombo(code);
				if(codePair.length > 2){
					json = new JSONBaseVO();
					if(!codePair[2].equals("Z")){
						if(codePair[2].equals("S")){
							json.put("name", HncisMessageSource.getMessage("select"));
						}else{
							json.put("name", HncisMessageSource.getMessage("total"));
						}
						json.put("value", "");

						jsonArr.add(json);
					}
				}

				for(CommonCode targetBean : codeList){
					json = new JSONBaseVO();
					json.put("key", StringUtil.isNullToStrTrm(targetBean.getKey()));
					json.put("name", StringUtil.isNullToStrTrm(targetBean.getName()));
					json.put("value", StringUtil.isNullToStrTrm(targetBean.getValue()));

					jsonArr.add(json);
				}
				jso.put(codePair[0], jsonArr);
			}else{
				break;
			}
		}

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject("uiType", "ajax");
		modelAndView.addObject(JSON_DATA_KEY, jso.toString());

		return modelAndView;
	}


	/**
	 * Meal menu
	 * @param req
	 * @param res
	 * @param bsicInfo
	 * @param travelerInfo
	 * @param scheduleInfo
	 * @return
	 * @throws HncisException
	 * @throws SessionException
	 */
	@RequestMapping(value="/hncis/board/doSearchToMealList.do")
	public ModelAndView doSearchToMealList(HttpServletRequest req, HttpServletResponse res,
			@RequestParam(value="paramJson", required=false) String paramJson) throws HncisException, SessionException{

		BgabGascz019Dto paramVo = (BgabGascz019Dto) getJsonToBean(paramJson, BgabGascz019Dto.class);
		paramVo.setCorp_cd(SessionInfo.getSess_corp_cd(req));
		String strDate = "";
		if("P".equals(paramVo.getFlag())){
			strDate = CurrentDateTime.getDate(paramVo.getRegdate(), -7);
		}else if("T".equals(paramVo.getFlag())){
			strDate = CurrentDateTime.getDate(CurrentDateTime.getDate(), 0);
		}else if("N".equals(paramVo.getFlag())){
			strDate = CurrentDateTime.getDate(paramVo.getRegdate(), +7);
		}

		BgabGascz019Dto weekBetween = boardManager.doMealToWeekCnt(strDate);
		paramVo.setK_day(weekBetween.getK_day());
		paramVo.setE_day(weekBetween.getE_day());

		CommonList list = new CommonList();
		list.setData1(strDate);
		list.setData2(CurrentDateTime.getTransferDate(weekBetween.getK_day()));
		list.setData3(CurrentDateTime.getTransferDate(weekBetween.getE_day()));
		list.setRows(boardManager.doSearchToBrMealMenu(paramVo));
		list.setRows1(boardManager.doSearchToKrMealMenu(paramVo));
		list.setRows2(boardManager.doSearchToCoffeeMenu(paramVo));

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(DATA_JSON_PAGE);
		modelAndView.addObject(JSON_DATA_KEY, JSONObject.fromObject(list).toString());

		return modelAndView;
	}
}
