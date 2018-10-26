package com.hncis.board.manager.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hncis.board.dao.BoardDao;
import com.hncis.board.manager.BoardManager;
import com.hncis.board.vo.BgabGasc01DtlDto;
import com.hncis.board.vo.BgabGasc01KeyDto;
import com.hncis.common.application.SessionInfo;
import com.hncis.common.message.HncisMessageSource;
import com.hncis.common.util.FileUtil;
import com.hncis.common.util.SendMail;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonUserInfo;
import com.hncis.system.vo.BgabGascz019Dto;

// TODO: Auto-generated Javadoc
/**
 * The Class BoardManagerImpl.- Board 비지니스 로직 실제 수행 클래스
 */
@Service("boardManagerImpl")
public class BoardManagerImpl implements BoardManager{

	/** The board dao. - board 데이터 접근 객체 */
	@Autowired
	public BoardDao boardDao;

	/**
	 * ******************************************************************************************
	 * COMMON                                       *
	 * ******************************************************************************************.
	 *
	 * @return the select max seq bd to board for not
	 */
	/**
	 * notice max sequence 게시물 마지막 순번 가져옴
	 * @return
	 */
	public int getSelectMaxSeqBDToBoardForNot(String corp_cd){
		return Integer.parseInt(boardDao.getSelectMaxSeqBDToBoardForNot(corp_cd));
	}

	/**
	 * qna max sequence. - Q&A 마지막 순번 가져옴
	 *
	 * @return the select max seq bd to board for qna
	 */
	public int getSelectMaxSeqBDToBoardForQna(String corp_cd){
		return Integer.parseInt(boardDao.getSelectMaxSeqBDToBoardForQna(corp_cd));
	}

	/**
	 * faq max sequence. - FAQ 마지막 순번 가져옴
	 *
	 * @return the select max seq bd to board for faq
	 */
	public int getSelectMaxSeqBDToBoardForFaq(String corp_cd){
		return Integer.parseInt(boardDao.getSelectMaxSeqBDToBoardForFaq(corp_cd));
	}

	/**
	 * In charge search.  - In charge 검색
	 *
	 * @param keyDto the key dto
	 * @return the select in charge bd to qna
	 */
	public CommonUserInfo getSelectInChargeBDToQna(BgabGasc01KeyDto keyDto){
		return boardDao.getSelectInChargeBDToQna(keyDto);
	}

	/**
	 * ******************************************************************************************
	 * NOTICE                                       *
	 * ******************************************************************************************.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select count bd to notice
	 */
	/**
	 * notice total count - 공지사항 목록 갯수 조회
	 *
	 * @param keyParamDto - 조회조건
	 * @return
	 */
	public int getSelectCountBDToNotice(BgabGasc01KeyDto keyParamDto){
		return Integer.parseInt(boardDao.getSelectCountBDToNotice(keyParamDto));
	}

	/**
  	 * Gets the select list bd to notice. - 공지사항 목록 조회
  	 *
  	 * @param keyParamDto the key param dto - 조회조건
  	 * @return the select list bd to notice
  	 */
	public List<BgabGasc01DtlDto> getSelectListBDToNotice(BgabGasc01KeyDto keyParamDto){
		return boardDao.getSelectListBDToNotice(keyParamDto);
	}

	/**
	 * notice detail search. - 공지사항 내용 조회
	 *
	 * @param keyDto the key dto - 조회조건
	 * @return the select detail bd to notice
	 */
	public BgabGasc01DtlDto getSelectDetailBDToNotice(BgabGasc01KeyDto keyDto){
		return boardDao.getSelectDetailBDToNotice(keyDto);
	}

	/**
	 * notice write - 공지사항 내용 입력
	 * @param res
	 * @param req
	 * @param dtlDto - 조건
	 * @param message
	 */
	public void insertInfoBDToNotice(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd01.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			String sess_empno 		= SessionInfo.getSess_empno(req);			//작업자 사번
			String sess_name   		= SessionInfo.getSess_name(req);			//작업자 성명
			String se   = SessionInfo.getSess_mstu_gubb(req);
			String corpCd    = SessionInfo.getSess_corp_cd(req);

			dtlDto.setBod_sano(sess_empno);
			dtlDto.setIpe_eeno(sess_empno);
			dtlDto.setUpdr_eeno(sess_empno);
			dtlDto.setCorp_cd(corpCd);

			if(!se.equals("M")){
				resultUrl = "../../index.htm";
			}else{

				int maxSeq = getSelectMaxSeqBDToBoardForNot(dtlDto.getCorp_cd());
				dtlDto.setBod_indx(maxSeq);
				if(dtlDto.getBod_fname().equals("")){
					boardDao.insertInfoBDToNotice(dtlDto);
					msg = HncisMessageSource.getMessage("SAVE.0000");
				}else{
					paramVal[0] = "bod_fname";
					paramVal[1] = "old_bod_fname";
					paramVal[2] = "board";

					result = FileUtil.uploadFile(req, res, paramVal);
					if(result != null){
						dtlDto.setBod_fname(result[0]);
						dtlDto.setBod_fsize(result[3]);
						boardDao.insertInfoBDToNotice(dtlDto);

						msg = HncisMessageSource.getMessage("SAVE.0000");
					}else{
						resultUrl = "xbd01.gas";
						msg = HncisMessageSource.getMessage("ETC.0000");
					}
				}
			}//end else


		}catch(Exception e){
			resultUrl = "xbd01.gas";
			msg = HncisMessageSource.getMessage("SAVE.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",    "write");
				req.setAttribute("message",  msg);
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * notice modify. - 공지사항 수정
	 *
	 * @param req the req
	 * @param res the res
	 * @param dtlDto the dtl dto
	 * @param message the message
	 */
	public void updateInfoBDToNotice(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd01.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{

			String sess_empno 		= SessionInfo.getSess_empno(req);			//작업자 사번
			String sess_name   		= SessionInfo.getSess_name(req);			//작업자 성명
			String se   = SessionInfo.getSess_mstu_gubb(req);
			String corpCd    = SessionInfo.getSess_corp_cd(req);

			dtlDto.setBod_sano(sess_empno);
			dtlDto.setIpe_eeno(sess_empno);
			dtlDto.setUpdr_eeno(sess_empno);
			dtlDto.setCorp_cd(corpCd);

			if(!se.equals("M")){
				resultUrl = "../../index.htm";
			}else{

				if(dtlDto.getBod_fname().equals("")){
					dtlDto.setBod_fname(dtlDto.getOld_bod_fname());
					boardDao.updateInfoBDToNotice(dtlDto);
					msg = HncisMessageSource.getMessage("EDIT.0000");
				}else{
					paramVal[0] = "bod_fname";
					paramVal[1] = "old_bod_fname";
					paramVal[2] = "board";

					result = FileUtil.uploadFile(req, res, paramVal);
					if(result != null){
						dtlDto.setBod_fname(result[0]);
						dtlDto.setBod_fsize(result[3]);
						boardDao.updateInfoBDToNotice(dtlDto);
						msg = HncisMessageSource.getMessage("EDIT.0000");
					}else{
						msg = HncisMessageSource.getMessage("ETC.0000");
					}
				}
			}

		}catch(Exception e){
			resultUrl = "xbd01.gas";
			msg = HncisMessageSource.getMessage("EDIT.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",    "modify");
				req.setAttribute("bod_indx", String.valueOf(dtlDto.getBod_indx()));
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * notice delete. - 공지사항 삭제
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToNotice(BgabGasc01DtlDto dtlDto){
		return boardDao.deleteInfoBDToNotice(dtlDto);
	}

	/**
	 * take notice list for main page. - 메인화면의 공지사항 조회
	 *
	 * @param row the row
	 * @return List
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToMainNotice(String row){
		return boardDao.getSelectListBDToMainNotice(row);
	}

	/**
	 * ******************************************************************************************
	 * QNA                                          *
	 * ******************************************************************************************.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select count bd to qna
	 */
	/**
	 * qna total count - Q&A 목록 갯수 조회
	 * qna list search
	 * @param keyParamDto
	 * @return
	 */
	public int getSelectCountBDToQna(BgabGasc01KeyDto keyParamDto){
		return Integer.parseInt(boardDao.getSelectCountBDToQna(keyParamDto));
	}

	/**
	 * qna list search  - Q&A 목록 조회
	 *
	 * @param keyParamDto
	 * @return
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToQna(BgabGasc01KeyDto keyParamDto){
		return boardDao.getSelectListBDToQna(keyParamDto);
	}

	/**
	 * qna detail search. - Q&A 내용 조회
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to qna
	 */
	public BgabGasc01DtlDto getSelectDetailBDToQna(BgabGasc01KeyDto keyDto){
		return boardDao.getSelectDetailBDToQna(keyDto);
	}

	/**
	 * qna write. -  Q&A 내용 입력
	 *
	 * @param req the req
	 * @param res the res
	 * @param dtlDto the dtl dto
	 * @param message the message
	 */
	public void insertInfoBDToQna(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd04.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{
			int maxSeq = getSelectMaxSeqBDToBoardForQna(dtlDto.getCorp_cd());
			dtlDto.setBod_indx(maxSeq);
			dtlDto.setBod_group(String.valueOf(maxSeq));
			if(dtlDto.getBod_fname().equals("")){
				boardDao.insertInfoBDToQna(dtlDto);
				msg = HncisMessageSource.getMessage("SAVE.0000");
			}else{
				paramVal[0] = "bod_fname";
				paramVal[1] = "old_bod_fname";
				paramVal[2] = "board";

				result = FileUtil.uploadFile(req, res, paramVal);
				if(result != null){
					dtlDto.setBod_fname(result[0]);
					dtlDto.setBod_fsize(result[3]);
					boardDao.insertInfoBDToQna(dtlDto);

					msg = HncisMessageSource.getMessage("SAVE.0000");
				}else{
					resultUrl = "xbd04.gas";
					msg = HncisMessageSource.getMessage("ETC.0000");
				}
			}

			sendMail("to", "from", "subject", "content");

		}catch(Exception e){
			resultUrl = "xbd04.gas";
			msg = HncisMessageSource.getMessage("SAVE.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",    "write");
				req.setAttribute("message",  msg);
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * qna update. - Q&A 수정
	 *
	 * @param req the req
	 * @param res the res
	 * @param dtlDto the dtl dto
	 * @param message the message
	 */
	public void updateInfoBDToQna(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd04.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{
			if(dtlDto.getBod_fname().equals("")){
				dtlDto.setBod_fname(dtlDto.getOld_bod_fname());
				boardDao.updateInfoBDToQna(dtlDto);
				msg = HncisMessageSource.getMessage("EDIT.0000");
			}else{
				paramVal[0] = "bod_fname";
			 	paramVal[1] = "old_bod_fname";
				paramVal[2] = "board";

				result = FileUtil.uploadFile(req, res, paramVal);
				if(result != null){
					dtlDto.setBod_fname(result[0]);
					dtlDto.setBod_fsize(result[3]);
					boardDao.updateInfoBDToQna(dtlDto);
					msg = HncisMessageSource.getMessage("EDIT.0000");
				}else{
					msg = HncisMessageSource.getMessage("ETC.0000");
				}
			}
		}catch(Exception e){
			resultUrl = "xbd04.gas";
			msg = HncisMessageSource.getMessage("EDIT.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",    "modify");
				req.setAttribute("bod_indx", String.valueOf(dtlDto.getBod_indx()));
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * qna delete. - Q&A 삭제
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToQna(BgabGasc01DtlDto dtlDto){
		return boardDao.deleteInfoBDToQna(dtlDto);
	}

	/**
	 * qna reply write. - Q&A reply 입력
	 *
	 * @param req the req
	 * @param res the res
	 * @param dtlDto the dtl dto
	 * @param message the message
	 */
	public void replyInfoBDToQna(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd04.gas";

		try{
			int maxSeq = getSelectMaxSeqBDToBoardForQna(dtlDto.getCorp_cd());
			dtlDto.setBod_indx(maxSeq);
			boardDao.updateReplyInfoBDToQnaLevel(dtlDto);
			boardDao.replyInfoBDToQna(dtlDto);
			msg = HncisMessageSource.getMessage("SAVE.0000");

			sendMail("to", "from", "subject", "content");

		}catch(Exception e){
			resultUrl = "xbd04.gas";
			msg = HncisMessageSource.getMessage("SAVE.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",   "reply");
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * ******************************************************************************************
	 * FAQ                                          *
	 * ******************************************************************************************.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select count bd to faq
	 */
	/**
	 * faq list count  - Faq 내용 목록 갯수 조회
	 * @param keyParamDto
	 * @return
	 */
	public int getSelectCountBDToFaq(BgabGasc01KeyDto keyParamDto){
		return Integer.parseInt(boardDao.getSelectCountBDToFaq(keyParamDto));
	}

	/**
	 * faq list count  - Faq 내용 목록 조회
	 * @param keyParamDto
	 * @return List<BgabGasc01DtlDto>
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToFaq(BgabGasc01KeyDto keyParamDto){
		return boardDao.getSelectListBDToFaq(keyParamDto);
	}

	/**
	 * Faq detail search. - Faq 내용 조회
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to faq
	 */
	public BgabGasc01DtlDto getSelectDetailBDToFaq(BgabGasc01KeyDto keyDto){
		return boardDao.getSelectDetailBDToFaq(keyDto);
	}

	/**
	 * Faq write. - Faq 내용 입력
	 *
	 * @param req the req
	 * @param res the res
	 * @param dtlDto the dtl dto
	 * @param message the message
	 */
	public void insertInfoBDToFaq(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd07.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{
			int maxSeq = getSelectMaxSeqBDToBoardForFaq(dtlDto.getCorp_cd());
			dtlDto.setBod_indx(maxSeq);
			dtlDto.setBod_group(String.valueOf(maxSeq));
			if(dtlDto.getBod_fname().equals("")){
				boardDao.insertInfoBDToFaq(dtlDto);
				msg = HncisMessageSource.getMessage("SAVE.0000");
			}else{
				paramVal[0] = "bod_fname";
				paramVal[1] = "old_bod_fname";
				paramVal[2] = "board";

				result = FileUtil.uploadFile(req, res, paramVal);
				if(result != null){
					dtlDto.setBod_fname(result[0]);
					dtlDto.setBod_fsize(result[3]);
					boardDao.insertInfoBDToFaq(dtlDto);

					msg = HncisMessageSource.getMessage("SAVE.0000");
				}else{
					resultUrl = "xbd07.gas";
					msg = HncisMessageSource.getMessage("ETC.0000");
				}
			}
		}catch(Exception e){
			resultUrl = "xbd07.gas";
			msg = HncisMessageSource.getMessage("SAVE.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",    "write");
				req.setAttribute("message",  msg);
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * qna update. - Faq 내용 수정
	 *
	 * @param req the req
	 * @param res the res
	 * @param dtlDto the dtl dto
	 * @param message the message
	 */
	public void updateInfoBDToFaq(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd07.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{
			if(dtlDto.getBod_fname().equals("")){
				dtlDto.setBod_fname(dtlDto.getOld_bod_fname());
				boardDao.updateInfoBDToFaq(dtlDto);
				msg = HncisMessageSource.getMessage("EDIT.0000");
			}else{
				paramVal[0] = "bod_fname";
			 	paramVal[1] = "old_bod_fname";
				paramVal[2] = "board";

				result = FileUtil.uploadFile(req, res, paramVal);
				if(result != null){
					dtlDto.setBod_fname(result[0]);
					dtlDto.setBod_fsize(result[3]);
					boardDao.updateInfoBDToFaq(dtlDto);
					msg = HncisMessageSource.getMessage("EDIT.0000");
				}else{
					msg = HncisMessageSource.getMessage("ETC.0000");
				}
			}
		}catch(Exception e){
			resultUrl = "xbd07.gas";
			msg = HncisMessageSource.getMessage("EDIT.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",    "modify");
				req.setAttribute("bod_indx", String.valueOf(dtlDto.getBod_indx()));
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * Faq delete. - Faq 내용 삭제
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToFaq(BgabGasc01DtlDto dtlDto){
		return boardDao.deleteInfoBDToFaq(dtlDto);
	}



	/**
	 * ******************************************************************************************
	 * Claim                                          *
	 * ******************************************************************************************.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select count bd to claim
	 */

	/**
	 * claim max sequence. - Claim 마지막 순번 가져옴
	 *
	 * @return the select max seq bd to board for claim
	 */
	public int getSelectMaxSeqBDToBoardForClaim(String corp_cd) {
		return Integer.parseInt(boardDao.getSelectMaxSeqBDToBoardForClaim(corp_cd));
	}

	/**
	 * In charge search.  - In charge 검색
	 *
	 * @param keyDto the key dto
	 * @return the select in charge bd to claim
	 */
	public CommonUserInfo getSelectInChargeBDToClaim(BgabGasc01KeyDto keyDto) {
		return boardDao.getSelectInChargeBDToClaim(keyDto);
	}

	/**
	 * claim total count - Claim 목록 갯수 조회
	 * claim list search
	 * @param keyParamDto
	 * @return
	 */
	public int getSelectCountBDToClaim(BgabGasc01KeyDto keyParamDto) {
		return Integer.parseInt(boardDao.getSelectCountBDToClaim(keyParamDto));
	}

	/**
	 * claim list search  - Claim 목록 조회
	 *
	 * @param keyParamDto
	 * @return
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToClaim(BgabGasc01KeyDto keyParamDto) {
		return boardDao.getSelectListBDToClaim(keyParamDto);
	}

	/**
	 * claim detail search. - Claim 내용 조회
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to claim
	 */
	public BgabGasc01DtlDto getSelectDetailBDToClaim(BgabGasc01KeyDto keyDto) {
		return boardDao.getSelectDetailBDToClaim(keyDto);
	}

	/**
	 * claim write. -  Claim 내용 입력
	 *
	 * @param req the req
	 * @param res the res
	 * @param dtlDto the dtl dto
	 * @param message the message
	 */
	public void insertInfoBDToClaim(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd10.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{
			int maxSeq = getSelectMaxSeqBDToBoardForClaim(dtlDto.getCorp_cd());
			dtlDto.setBod_indx(maxSeq);
			dtlDto.setBod_group(String.valueOf(maxSeq));
			if(dtlDto.getBod_fname().equals("")){
				boardDao.insertInfoBDToClaim(dtlDto);
				msg = HncisMessageSource.getMessage("SAVE.0000");
			}else{
				paramVal[0] = "bod_fname";
				paramVal[1] = "old_bod_fname";
				paramVal[2] = "board";

				result = FileUtil.uploadFile(req, res, paramVal);
				if(result != null){
					dtlDto.setBod_fname(result[0]);
					dtlDto.setBod_fsize(result[3]);
					boardDao.insertInfoBDToClaim(dtlDto);

					msg = HncisMessageSource.getMessage("SAVE.0000");
				}else{
					resultUrl = "xbd10.gas";
					msg = HncisMessageSource.getMessage("ETC.0000");
				}
			}

			sendMail("to", "from", "subject", "content");

		}catch(Exception e){
			resultUrl = "xbd10.gas";
			msg = HncisMessageSource.getMessage("SAVE.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",    "write");
				req.setAttribute("message",  msg);
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * claim update. - Claim 수정
	 *
	 * @param req the req
	 * @param res the res
	 * @param dtlDto the dtl dto
	 * @param message the message
	 */
	public void updateInfoBDToClaim(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd10.gas";
		String[] result   = new String[4];
		String[] paramVal = new String[4];

		try{
			if(dtlDto.getBod_fname().equals("")){
				dtlDto.setBod_fname(dtlDto.getOld_bod_fname());
				boardDao.updateInfoBDToClaim(dtlDto);
				msg = HncisMessageSource.getMessage("EDIT.0000");
			}else{
				paramVal[0] = "bod_fname";
			 	paramVal[1] = "old_bod_fname";
				paramVal[2] = "board";

				result = FileUtil.uploadFile(req, res, paramVal);
				if(result != null){
					dtlDto.setBod_fname(result[0]);
					dtlDto.setBod_fsize(result[3]);
					boardDao.updateInfoBDToClaim(dtlDto);
					msg = HncisMessageSource.getMessage("EDIT.0000");
				}else{
					msg = HncisMessageSource.getMessage("ETC.0000");
				}
			}
		}catch(Exception e){
			resultUrl = "xbd10.gas";
			msg = HncisMessageSource.getMessage("EDIT.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",    "modify");
				req.setAttribute("bod_indx", String.valueOf(dtlDto.getBod_indx()));
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * claim delete. - Claim 삭제
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToClaim(BgabGasc01DtlDto dtlDto){
		return boardDao.deleteInfoBDToClaim(dtlDto);
	}

	/**
	 * claim reply write. - Claim reply 입력
	 *
	 * @param req the req
	 * @param res the res
	 * @param dtlDto the dtl dto
	 * @param message the message
	 */
	public void replyInfoBDToClaim(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message){
		String msg        = "";
		String resultUrl  = "xbd10.gas";

		try{
			int maxSeq = getSelectMaxSeqBDToBoardForClaim(dtlDto.getCorp_cd());
			dtlDto.setBod_indx(maxSeq);
			boardDao.replyInfoBDToClaim(dtlDto);
			msg = HncisMessageSource.getMessage("SAVE.0000");

			sendMail("to", "from", "subject", "content");

		}catch(Exception e){
			resultUrl = "xbd10.gas";
			msg = HncisMessageSource.getMessage("SAVE.0001");
			e.printStackTrace();
		}finally{
			try{
				req.setAttribute("gubun",   "reply");
				req.setAttribute("csrfToken",dtlDto.getCsrfToken());
				req.setAttribute("message",  msg);
				req.getRequestDispatcher(resultUrl).forward(req, res);
				return;
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	/**
	 * board read count. - board 읽기 횟수를 1 증가시킴
	 *
	 * @param keyDto the key dto
	 * @return the object
	 */
	public Object updateReadBDToBoard(BgabGasc01KeyDto keyDto){
		return boardDao.updateReadBDToBoard(keyDto);
	}

	public void sendMail(String to, String from,  String subject, String content){
		String area = StringUtil.getSystemArea().toUpperCase();

		SendMail oMail = new SendMail();
		if(area.equals("REAL")){
			try {
				oMail.sendMail(to, from, subject, content, 1, false);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public List<CommonCode> getSelectToJobCombo(CommonCode code){
		return boardDao.getSelectToJobCombo(code);
	}

	/**
	 * Meal Menu List(brasilian, korean, coffee)
	 * @param paramVo
	 * @return
	 */
	public List<BgabGascz019Dto> doSearchToBrMealMenu(BgabGascz019Dto paramVo){
		return boardDao.doSearchToBrMealMenu(paramVo);
	}

	public List<BgabGascz019Dto> doSearchToKrMealMenu(BgabGascz019Dto paramVo){
		return boardDao.doSearchToKrMealMenu(paramVo);
	}

	public List<BgabGascz019Dto> doSearchToCoffeeMenu(BgabGascz019Dto paramVo){
		return boardDao.doSearchToCoffeeMenu(paramVo);
	}

	public BgabGascz019Dto doMealToWeekCnt(String strDate){
		return boardDao.doMealToWeekCnt(strDate);
	}
}
