package com.hncis.board.manager;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;

import com.hncis.board.vo.BgabGasc01DtlDto;
import com.hncis.board.vo.BgabGasc01KeyDto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonMessage;
import com.hncis.common.vo.CommonUserInfo;
import com.hncis.system.vo.BgabGascz019Dto;

@Transactional
public interface BoardManager {
	/********************************************************************************************
	 *                                             COMMON                                       *
	 ********************************************************************************************/
	/**
	 * notice max sequence 게시물 마지막 순번 가져옴
	 * @return
	 */
	public int getSelectMaxSeqBDToBoardForNot(String corp_cd);
	
	/**
	 * board read count 게시판 읽기 횟수를 1 증가시킴
	 * @param dtlDto - 조건
	 * @return
	 */
	public Object updateReadBDToBoard(BgabGasc01KeyDto keyDto);
	
	/**
	 * notice read count 게시판 읽기 횟수를 1 증가시킴
	 * @param dtlDto - 조건
	 * @return
	 */
//	public Object updateReadBDToBoardForNot(BgabGasc01KeyDto keyDto);

	/**
	 * qna max sequence - Q&A 마지막 순번 가져옴
	 * @return
	 */
	public int getSelectMaxSeqBDToBoardForQna(String corp_cd);
	
	/**
	 * faq max sequence - FAQ 마지막 순번 가져옴
	 * @return
	 */
	public int getSelectMaxSeqBDToBoardForFaq(String corp_cd);
	
	/**
	 * In charge search - In charge 검색
	 * @param keyDto
	 * @return
	 */
	public CommonUserInfo getSelectInChargeBDToQna(BgabGasc01KeyDto keyDto);

	/**
	 * claim max sequence - Claim 마지막 순번 가져옴
	 * @return
	 */
	public int getSelectMaxSeqBDToBoardForClaim(String corp_cd);

	/**
	 * In charge search - In charge 검색
	 * @param keyDto
	 * @return
	 */
	public CommonUserInfo getSelectInChargeBDToClaim(BgabGasc01KeyDto keyDto);

	/********************************************************************************************
	 *                                             NOTICE                                       *
	 ********************************************************************************************/
	/**
	 * notice total count - 공지사항 목록 갯수 조회
	 * 
	 * @param keyParamDto - 조회조건
	 * @return
	 */
	public int getSelectCountBDToNotice(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * Gets the select list bd to notice. - 공지사항 목록 조회
	 *
	 * @param keyParamDto the key param dto - 조회조건
	 * @return the select list bd to notice
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToNotice(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * notice detail search - 공지사항 내용 조회
	 * @param keyDto - 조회조건
	 * @return
	 */
	public BgabGasc01DtlDto getSelectDetailBDToNotice(BgabGasc01KeyDto keyDto);
	
	/**
	 * notice write - 공지사항 내용 입력
	 * @param res 
	 * @param req 
	 * @param dtlDto - 조건
	 * @param message 
	 */
	public void insertInfoBDToNotice(HttpServletRequest req,
			HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message);
	
	/**
	 * notice modify - 공지사항 수정
	 * @param dtlDto
	 */
	public void updateInfoBDToNotice(HttpServletRequest req,
			HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message);
	
	/**
	 * notice delete - 공지사항 삭제
	 * @param dtlDto
	 */
	public Object deleteInfoBDToNotice(BgabGasc01DtlDto dtlDto);
	
	/**
	 * take notice list for main page - 메인화면의 공지사항 조회
	 * @param String
	 * @return List
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToMainNotice(String row);
	
	/********************************************************************************************
	 *                                             QNA                                          *
	 ********************************************************************************************/
	/**
	 * qna total count - Q&A 목록 갯수 조회
	 * qna list search
	 * @param keyParamDto
	 * @return
	 */
	public int getSelectCountBDToQna(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * qna list search  - Q&A 목록 조회
	 * 
	 * @param keyParamDto
	 * @return
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToQna(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * qna detail search - Q&A 내용 조회
	 * @param keyDto
	 * @return
	 */
	public BgabGasc01DtlDto getSelectDetailBDToQna(BgabGasc01KeyDto keyDto);
	
	/**
	 * qna write -  Q&A 내용 입력
	 * @param res 
	 * @param req 
	 * @param dtlDto
	 * @param message 
	 * @return
	 */
	public void insertInfoBDToQna(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message);
	
	/**
	 * qna update - Q&A 수정
	 * @param res 
	 * @param req 
	 * @param dtlDto
	 * @param message 
	 * @return
	 */
	public void updateInfoBDToQna(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message);
	
	/**
	 * qna delete - Q&A 삭제
	 * @param dtlDto
	 * @return
	 */
	public Object deleteInfoBDToQna(BgabGasc01DtlDto dtlDto);
	
	/**
	 * qna reply write - Q&A reply 입력
	 * @param req
	 * @param res
	 * @param dtlDto
	 * @param message
	 */
	public void replyInfoBDToQna(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message);
	
	/********************************************************************************************
	 *                                             FAQ                                          *
	 ********************************************************************************************/
	
	/**
	 * faq search count - Faq 내용 목록 갯수 조회
	 * @param req
	 * @param res
	 * @param dtlDto
	 * @param message
	 */
	public int getSelectCountBDToFaq(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * faq list count  - Faq 내용 목록 조회
	 * @param keyParamDto
	 * @return List<BgabGasc01DtlDto>
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToFaq(BgabGasc01KeyDto keyParamDto);
	/**
	 * faq detail search - Faq 내용 조회
	 * @param req
	 * @param res
	 * @param dtlDto
	 * @param message
	 */
	public BgabGasc01DtlDto getSelectDetailBDToFaq(BgabGasc01KeyDto keyDto);
	/**
	 * faq insert - Faq 내용 입력
	 * @param req
	 * @param res
	 * @param dtlDto
	 * @param message
	 */
	public void insertInfoBDToFaq(HttpServletRequest req,HttpServletResponse res, BgabGasc01DtlDto dtlDto,CommonMessage message);
	/**
	 * faq update - Faq 내용 수정
	 * @param req
	 * @param res
	 * @param dtlDto
	 * @param message
	 */
	public void updateInfoBDToFaq(HttpServletRequest req,HttpServletResponse res, BgabGasc01DtlDto dtlDto,CommonMessage message);
	/**
	 * faq delete - Faq 내용 삭제
	 * @param req
	 * @param res
	 * @param dtlDto
	 * @param message
	 */
	public Object deleteInfoBDToFaq(BgabGasc01DtlDto dtlDto);
	
	/********************************************************************************************
	 *                                           Claim                                          *
	 ********************************************************************************************/
	/**
	 * claim total count - Claim 목록 갯수 조회
	 * claim list search
	 * @param keyParamDto
	 * @return
	 */
	public int getSelectCountBDToClaim(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * claim list search  - Claim 목록 조회
	 * 
	 * @param keyParamDto
	 * @return
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToClaim(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * claim detail search - Claim 내용 조회
	 * @param keyDto
	 * @return
	 */
	public BgabGasc01DtlDto getSelectDetailBDToClaim(BgabGasc01KeyDto keyDto);
	
	/**
	 * claim write -  Claim 내용 입력
	 * @param res 
	 * @param req 
	 * @param dtlDto
	 * @param message 
	 * @return
	 */
	public void insertInfoBDToClaim(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message);
	
	/**
	 * claim update - Claim 수정
	 * @param res 
	 * @param req 
	 * @param dtlDto
	 * @param message 
	 * @return
	 */
	public void updateInfoBDToClaim(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message);
	
	/**
	 * claim delete - Claim 삭제
	 * @param dtlDto
	 * @return
	 */
	public Object deleteInfoBDToClaim(BgabGasc01DtlDto dtlDto);
	
	/**
	 * claim reply write - Claim reply 입력
	 * @param req
	 * @param res
	 * @param dtlDto
	 * @param message
	 */
	public void replyInfoBDToClaim(HttpServletRequest req, HttpServletResponse res, BgabGasc01DtlDto dtlDto, CommonMessage message);

	public List<CommonCode> getSelectToJobCombo(CommonCode code);
	
	/**
	 * Meal Menu List(brasilian, korean)
	 * @param paramVo
	 * @return
	 */
	public List<BgabGascz019Dto> doSearchToBrMealMenu(BgabGascz019Dto paramVo);
	public List<BgabGascz019Dto> doSearchToKrMealMenu(BgabGascz019Dto paramVo);
	public List<BgabGascz019Dto> doSearchToCoffeeMenu(BgabGascz019Dto paramVo);
	public BgabGascz019Dto doMealToWeekCnt(String strDate);
}
