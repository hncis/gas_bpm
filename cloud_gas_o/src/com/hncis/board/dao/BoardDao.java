package com.hncis.board.dao;

import java.util.List;

import com.hncis.board.vo.BgabGasc01DtlDto;
import com.hncis.board.vo.BgabGasc01KeyDto;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonUserInfo;
import com.hncis.system.vo.BgabGascz019Dto;

// TODO: Auto-generated Javadoc
/**
 * The Interface BoardDao. - Interface Board 데이터 접근 객체
 */
public interface BoardDao {
	
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
	public String getSelectMaxSeqBDToBoardForNot(String corp_cd);
	
	/**
	 * board read count 게시판 읽기 횟수를 1 증가시킴.
	 *
	 * @param keyDto the key dto
	 * @return the object
	 */
	public Object updateReadBDToBoard(BgabGasc01KeyDto keyDto);
	
	/**
	 * qna max sequence - Q&A 마지막 순번 가져옴.
	 *
	 * @return the select max seq bd to board for qna
	 */
	public String getSelectMaxSeqBDToBoardForQna(String corp_cd);
	
	/**
	 * faq max sequence - FAQ 마지막 순번 가져옴.
	 *
	 * @return the select max seq bd to board for faq
	 */
	public String getSelectMaxSeqBDToBoardForFaq(String corp_cd);
	
	/**
	 * In charge search - In charge 검색.
	 *
	 * @param keyDto the key dto
	 * @return the select in charge bd to qna
	 */
	public CommonUserInfo getSelectInChargeBDToQna(BgabGasc01KeyDto keyDto);
	
	/**
	 * claim max sequence - Claim 마지막 순번 가져옴.
	 *
	 * @return the select max seq bd to board for claim
	 */
	public String getSelectMaxSeqBDToBoardForClaim(String corp_cd);
	
	/**
	 * In charge search - In charge 검색.
	 *
	 * @param keyDto the key dto
	 * @return the select in charge bd to claim
	 */
	public CommonUserInfo getSelectInChargeBDToClaim(BgabGasc01KeyDto keyDto);

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
	public String getSelectCountBDToNotice(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * Gets the select list bd to notice. - 공지사항 목록 조회
	 *
	 * @param keyParamDto the key param dto - 조회조건
	 * @return the select list bd to notice
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToNotice(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * notice detail search - 공지사항 내용 조회.
	 *
	 * @param keyDto - 조회조건
	 * @return the select detail bd to notice
	 */
	public BgabGasc01DtlDto getSelectDetailBDToNotice(BgabGasc01KeyDto keyDto);
	
	/**
	 * notice write - 공지사항 내용 입력.
	 *
	 * @param dtlDto - 조건
	 * @return the object
	 */
	public Object insertInfoBDToNotice(BgabGasc01DtlDto dtlDto);
	
	/**
	 * notice modify - 공지사항 수정.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateInfoBDToNotice(BgabGasc01DtlDto dtlDto);
	
	/**
	 * notice delete - 공지사항 삭제.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToNotice(BgabGasc01DtlDto dtlDto);
	
	/**
	 * take notice list for main page - 메인화면의 공지사항 조회.
	 *
	 * @param row the row
	 * @return List
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToMainNotice(String row);
	
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
	public String getSelectCountBDToQna(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * qna list search  - Q&A 목록 조회.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select list bd to qna
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToQna(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * qna detail search - Q&A 내용 조회.
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to qna
	 */
	public BgabGasc01DtlDto getSelectDetailBDToQna(BgabGasc01KeyDto keyDto);
	
	/**
	 * qna write -  Q&A 내용 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object insertInfoBDToQna(BgabGasc01DtlDto dtlDto);
	
	/**
	 * qna update - Q&A 수정.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateInfoBDToQna(BgabGasc01DtlDto dtlDto);
	
	/**
	 * qna delete - Q&A 삭제.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToQna(BgabGasc01DtlDto dtlDto);
	
	/**
	 * qna reply write - Q&A reply 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateReplyInfoBDToQnaLevel(BgabGasc01DtlDto dtlDto);
	public Object replyInfoBDToQna(BgabGasc01DtlDto dtlDto);
	
	/**
	 * ******************************************************************************************
	 * FAQ                                          *
	 * ******************************************************************************************.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select count bd to faq
	 */
	/**
	 * faq search count - Faq 내용 목록 갯수 조회
	 * @param req
	 * @param res
	 * @param dtlDto
	 * @param message
	 */
	public String getSelectCountBDToFaq(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * faq list count  - Faq 내용 목록 조회.
	 *
	 * @param keyParamDto the key param dto
	 * @return List<BgabGasc01DtlDto>
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToFaq(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * faq detail search - Faq 내용 조회.
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to faq
	 */
	public BgabGasc01DtlDto getSelectDetailBDToFaq(BgabGasc01KeyDto keyDto);
	
	/**
	 * faq insert - Faq 내용 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object insertInfoBDToFaq(BgabGasc01DtlDto dtlDto);
	
	/**
	 * faq update - Faq 내용 수정.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateInfoBDToFaq(BgabGasc01DtlDto dtlDto);
	
	/**
	 * faq delete - Faq 내용 삭제.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToFaq(BgabGasc01DtlDto dtlDto);
	
	/**
	 * ******************************************************************************************
	 * Claim                                          *
	 * ******************************************************************************************.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select count bd to claim
	 */
	/**
	 * claim total count - Claim 목록 갯수 조회
	 * claim list search
	 * @param keyParamDto
	 * @return
	 */
	public String getSelectCountBDToClaim(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * claim list search  - Claim 목록 조회.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select list bd to claim
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToClaim(BgabGasc01KeyDto keyParamDto);
	
	/**
	 * claim detail search - Claim 내용 조회.
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to claim
	 */
	public BgabGasc01DtlDto getSelectDetailBDToClaim(BgabGasc01KeyDto keyDto);
	
	/**
	 * claim write -  Claim 내용 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object insertInfoBDToClaim(BgabGasc01DtlDto dtlDto);
	
	/**
	 * claim update - Claim 수정.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateInfoBDToClaim(BgabGasc01DtlDto dtlDto);
	
	/**
	 * claim delete - Claim 삭제.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToClaim(BgabGasc01DtlDto dtlDto);
	
	/**
	 * claim reply write - Claim reply 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object replyInfoBDToClaim(BgabGasc01DtlDto dtlDto);
	
	public List<CommonCode> getSelectToJobCombo(CommonCode code);
	
	/**
	 * Meal Menu List(brasilian, korean, coffee)
	 * @param paramVo
	 * @return
	 */
	public List<BgabGascz019Dto> doSearchToBrMealMenu(BgabGascz019Dto paramVo);
	public List<BgabGascz019Dto> doSearchToKrMealMenu(BgabGascz019Dto paramVo);
	public List<BgabGascz019Dto> doSearchToCoffeeMenu(BgabGascz019Dto paramVo);
	public BgabGascz019Dto doMealToWeekCnt(String strDate);

}
