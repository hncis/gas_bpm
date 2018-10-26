package com.hncis.board.dao.impl;

import java.util.List;

import com.hncis.board.dao.BoardDao;
import com.hncis.board.vo.BgabGasc01DtlDto;
import com.hncis.board.vo.BgabGasc01KeyDto;
import com.hncis.common.dao.CommonDao;
import com.hncis.common.vo.CommonCode;
import com.hncis.common.vo.CommonUserInfo;
import com.hncis.system.vo.BgabGascz019Dto;

// TODO: Auto-generated Javadoc
/**
 * The Class BoardDaoImplByIBatis. - Board 데이터 접근 객체
 */
public class BoardDaoImplByIBatis extends CommonDao implements BoardDao{
	
	/** The select maxseq bd to board for not. */
	private final String SELECT_MAXSEQ_BD_TO_BOARD_FOR_NOT         = "selectMaxSeqBdToBoardForNot";
	
	/** The update read bd to board for not. */
	private final String UPDATE_READ_BD_TO_BOARD		           = "updateReadBdToBoard";
	
	/** The select maxseq bd to board for qna. */
	private final String SELECT_MAXSEQ_BD_TO_BOARD_FOR_QNA         = "selectMaxSeqBdToBoardForQna";
	
	/** The select maxseq bd to board for faq. */
	private final String SELECT_MAXSEQ_BD_TO_BOARD_FOR_FAQ         = "selectMaxSeqBdToBoardForFaq";
	
	/** The select in charge bd to qna. */
	private final String SELECT_IN_CHARGE_BD_TO_QNA        = "selectInChargeBdToQna";
	
	/** The select count bd to notice. */
	private final String SELECT_COUNT_BD_TO_NOTICE         = "selectCountBdToNotice";
	
	/** The select list bd to notice. */
	private final String SELECT_LIST_BD_TO_NOTICE          = "selectListBdToNotice";
	
	/** The select detail bd to notice. */
	private final String SELECT_DETAIL_BD_TO_NOTICE        = "selectDetailBdToNotice";
	
	/** The merge info bd to notice. */
	private final String MERGE_INFO_BD_TO_NOTICE           = "mergeInfoBdToNotice";
	
	/** The delete info bd to notice. */
	private final String DELETE_INFO_BD_TO_NOTICE          = "deleteInfoBdToNotice";
	
	/** The select count bd to qna. */
	private final String SELECT_COUNT_BD_TO_QNA            = "selectCountBdToQna";
	
	/** The select list bd to qna. */
	private final String SELECT_LIST_BD_TO_QNA             = "selectListBdToQna";
	
	/** The select detail bd to qna. */
	private final String SELECT_DETAIL_BD_TO_QNA           = "selectDetailBdToQna";
	
	/** The merge info bd to qna. */
	private final String MERGE_INFO_BD_TO_QNA              = "mergeInfoBdToQna";
	
	/** The delete info bd to qna. */
	private final String DELETE_INFO_BD_TO_QNA             = "deleteInfoBdToQna";
	
	/** The reply info bd to qna. */
	private final String UPDATE_REPLY_INFO_BD_TO_QNA_LEVEL = "updateReplyInfoBDToQnaLevel";
	private final String REPLY_INFO_BD_TO_QNA              = "replyInfoBdToQna";
	
	/** The select count bd to faq. */
	private final String SELECT_COUNT_BD_TO_FAQ            = "selectCountBdToFaq";
	
	/** The select list bd to faq. */
	private final String SELECT_LIST_BD_TO_FAQ             = "selectListBdToFaq";
	
	/** The select detail bd to faq. */
	private final String SELECT_DETAIL_BD_TO_FAQ           = "selectDetailBdToFaq";
	
	/** The merge info bd to faq. */
	private final String MERGE_INFO_BD_TO_FAQ              = "mergeInfoBdToFaq";
	
	/** The delete info bd to faq. */
	private final String DELETE_INFO_BD_TO_FAQ             = "deleteInfoBdToFaq";
	
	/** The SELEC t_ lis t_ b d_ to_ mai n_ notice. */
	private final String SELECT_LIST_BD_To_MAIN_NOTICE	   ="getSelectListBDToMainNotice";
	
	/** The select maxseq bd to board for qna. */
	private final String SELECT_MAXSEQ_BD_TO_BOARD_FOR_CLAIM = "selectMaxSeqBdToBoardForClaim";
	
	/** The update read bd to board for claim. */
//	private final String UPDATE_READ_BD_TO_BOARD_FOR_CLAIM   = "updateReadBdToBoardForClaim";
	
	/** The select in charge bd to claim. */
	private final String SELECT_IN_CHARGE_BD_TO_CLAIM        = "selectInChargeBdToClaim";
	
	/** The select count bd to claim. */
	private final String SELECT_COUNT_BD_TO_CLAIM            = "selectCountBdToClaim";
	
	/** The select list bd to claim. */
	private final String SELECT_LIST_BD_TO_CLAIM             = "selectListBdToClaim";
	
	/** The select detail bd to claim. */
	private final String SELECT_DETAIL_BD_TO_CLAIM           = "selectDetailBdToClaim";
	
	/** The merge info bd to claim. */
	private final String MERGE_INFO_BD_TO_CLAIM              = "mergeInfoBdToClaim";
	
	/** The delete info bd to claim. */
	private final String DELETE_INFO_BD_TO_CLAIM             = "deleteInfoBdToClaim";
	
	/** The reply info bd to claim. */
	private final String REPLY_INFO_BD_TO_CLAIM              = "replyInfoBdToClaim";

	private final String SELECT_TO_JOB_COMBO                 = "selectToJobCombo";
	
	
	/**
	 * ******************************************************************************************
	 * COMMON                                       *
	 * ******************************************************************************************.
	 *
	 * @return the select max seq bd to board for not
	 */
	/**
	 * notice max sequence - notice 순번 가져오는 DAO
	 * @return Board 다음 순번
	 */
	public String getSelectMaxSeqBDToBoardForNot(String corp_cd){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_MAXSEQ_BD_TO_BOARD_FOR_NOT, corp_cd);
	}
	
	/**
	 * board read count - 조회 횟수 올려주는 DAO.
	 *
	 * @param keyDto the key dto
	 * @return 업데이트 횟수
	 */
	public Object updateReadBDToBoard(BgabGasc01KeyDto keyDto){
		return super.update(UPDATE_READ_BD_TO_BOARD, keyDto);
	}
	
	/**
	 * notice read count - 조회 횟수 올려주는 DAO.
	 *
	 * @param keyDto the key dto
	 * @return 업데이트 횟수
	 */
//	public Object updateReadBDToBoardForNot(BgabGasc01KeyDto keyDto){
//		return super.update(UPDATE_READ_BD_TO_BOARD_FOR_NOT, keyDto);
//	}
	
	/**
	 * Qna max sequence - Qna 다음 순번 가져오는 DAO.
	 *
	 * @return Qna 다음 순번
	 */
	public String getSelectMaxSeqBDToBoardForQna(String corp_cd){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_MAXSEQ_BD_TO_BOARD_FOR_QNA, corp_cd);
	}
	
	/**
	 * Faq max sequence - Faq BOARD 다음 순번 올려주는 DAO.
	 *
	 * @return the select max seq bd to board for faq
	 */
	public String getSelectMaxSeqBDToBoardForFaq(String corp_cd){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_MAXSEQ_BD_TO_BOARD_FOR_FAQ, corp_cd);
	}
	
	/**
	 * In charge search - PIC 조회.
	 *
	 * @param keyDto - pic job 구분
	 * @return the select in charge bd to claim
	 */
	public CommonUserInfo getSelectInChargeBDToQna(BgabGasc01KeyDto keyDto){
		return (CommonUserInfo)getSqlMapClientTemplate().queryForObject(SELECT_IN_CHARGE_BD_TO_QNA, keyDto);
	}
	
	/**
	 * claim max sequence - claim 다음 순번 가져오는 DAO.
	 *
	 * @return claim 다음 순번
	 */
	public String getSelectMaxSeqBDToBoardForClaim(String corp_cd){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_MAXSEQ_BD_TO_BOARD_FOR_CLAIM, corp_cd);
	}
	
	/**
	 * In charge search - PIC 조회.
	 *
	 * @param keyDto - pic job 구분
	 * @return the select in charge bd to claim
	 */
	public CommonUserInfo getSelectInChargeBDToClaim(BgabGasc01KeyDto keyDto){
		return (CommonUserInfo)getSqlMapClientTemplate().queryForObject(SELECT_IN_CHARGE_BD_TO_CLAIM, keyDto);
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
	 * notice total count- 게시판 총 갯수 구함
	 * notice list search
	 * @param keyParamDto
	 * @return
	 */
	public String getSelectCountBDToNotice(BgabGasc01KeyDto keyParamDto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BD_TO_NOTICE, keyParamDto);
	}
	
	/**
	 * notice total count- 게시판 목록 조회
	 * notice list search.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select list bd to notice
	 */
	@SuppressWarnings("unchecked")
	public List<BgabGasc01DtlDto> getSelectListBDToNotice(BgabGasc01KeyDto keyParamDto){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BD_TO_NOTICE, keyParamDto);
	}
	
	/**
	 * notice detail search - 게시판 내용 조회.
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to notice
	 */
	public BgabGasc01DtlDto getSelectDetailBDToNotice(BgabGasc01KeyDto keyDto){
		return (BgabGasc01DtlDto)getSqlMapClientTemplate().queryForObject(SELECT_DETAIL_BD_TO_NOTICE, keyDto);
	}
	
	/**
	 * notice write - 게시판 내용 INSERT.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object insertInfoBDToNotice(BgabGasc01DtlDto dtlDto){
		return super.insert(MERGE_INFO_BD_TO_NOTICE, dtlDto);
	}
	
	/**
	 * notice modify - 게시판 내용 UPDATE.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateInfoBDToNotice(BgabGasc01DtlDto dtlDto){
		return super.insert(MERGE_INFO_BD_TO_NOTICE, dtlDto);
	}
	
	/**
	 * notice delete - 게시판 내용 삭제.
	 *
	 * @param dtlDto - 게시판 id
	 * @return the object
	 */
	public Object deleteInfoBDToNotice(BgabGasc01DtlDto dtlDto){
		return super.insert(DELETE_INFO_BD_TO_NOTICE, dtlDto);
	}
	
	/**
	 * take notice list for main page - 메인 페이지의 게시판 목록 조회.
	 *
	 * @param row the row
	 * @return List
	 */
	public List<BgabGasc01DtlDto> getSelectListBDToMainNotice(String row){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BD_To_MAIN_NOTICE, row);
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
	 * qna total count - qna 갯수 구함.
	 * qna list search
	 * @param keyParamDto
	 * @return
	 */
	public String getSelectCountBDToQna(BgabGasc01KeyDto keyParamDto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BD_TO_QNA, keyParamDto);
	}
	
	/**
	 * qna total count - qna 목록 조회
	 * qna list search.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select list bd to qna
	 */
	@SuppressWarnings("unchecked")
	public List<BgabGasc01DtlDto> getSelectListBDToQna(BgabGasc01KeyDto keyParamDto){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BD_TO_QNA, keyParamDto);
	}
	
	/**
	 * qna detail search - qna 상세 내용 조회.
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to qna
	 */
	public BgabGasc01DtlDto getSelectDetailBDToQna(BgabGasc01KeyDto keyDto){
		return (BgabGasc01DtlDto)getSqlMapClientTemplate().queryForObject(SELECT_DETAIL_BD_TO_QNA, keyDto);
	}
	
	/**
	 * qna write -  Claim 내용 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object insertInfoBDToQna(BgabGasc01DtlDto dtlDto){
		return super.insert(MERGE_INFO_BD_TO_QNA, dtlDto);
	}
	
	/**
	 * qna update - Claim 수정.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateInfoBDToQna(BgabGasc01DtlDto dtlDto){
		return super.insert(MERGE_INFO_BD_TO_QNA, dtlDto);
	}
	
	/**
	 * qna delete - Claim 삭제.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToQna(BgabGasc01DtlDto dtlDto){
		return super.insert(DELETE_INFO_BD_TO_QNA, dtlDto);
	}
	
	/**
	 * qna reply write - Claim reply 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateReplyInfoBDToQnaLevel(BgabGasc01DtlDto dtlDto){
		return super.update(UPDATE_REPLY_INFO_BD_TO_QNA_LEVEL, dtlDto);
	}
	public Object replyInfoBDToQna(BgabGasc01DtlDto dtlDto){
		return super.insert(REPLY_INFO_BD_TO_QNA, dtlDto);
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
	 * faq search count - Faq 내용 목록 갯수 조회
	 * @param req
	 * @param res
	 * @param dtlDto
	 * @param message
	 */
	public String getSelectCountBDToFaq(BgabGasc01KeyDto keyParamDto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BD_TO_FAQ, keyParamDto);
	}
	
	/**
	 * faq list count  - Faq 내용 목록 조회.
	 *
	 * @param keyParamDto the key param dto
	 * @return List<BgabGasc01DtlDto>
	 */
	@SuppressWarnings("unchecked")
	public List<BgabGasc01DtlDto> getSelectListBDToFaq(BgabGasc01KeyDto keyParamDto){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BD_TO_FAQ, keyParamDto);
	}
	
	/**
	 * faq detail search - Faq 내용 조회.
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to faq
	 */
	public BgabGasc01DtlDto getSelectDetailBDToFaq(BgabGasc01KeyDto keyDto){
		return (BgabGasc01DtlDto)getSqlMapClientTemplate().queryForObject(SELECT_DETAIL_BD_TO_FAQ, keyDto);
	}
	
	/**
	 * faq insert - Faq 내용 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object insertInfoBDToFaq(BgabGasc01DtlDto dtlDto){
		return super.insert(MERGE_INFO_BD_TO_FAQ, dtlDto);
	}
	
	/**
	 * faq update - Faq 내용 수정.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateInfoBDToFaq(BgabGasc01DtlDto dtlDto){
		return super.insert(MERGE_INFO_BD_TO_FAQ, dtlDto);
	}
	
	/**
	 * faq delete - Faq 내용 삭제.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToFaq(BgabGasc01DtlDto dtlDto){
		return super.insert(DELETE_INFO_BD_TO_FAQ, dtlDto);
	}
	
	/**
	 * ******************************************************************************************
	 * CLAIM                                          *
	 * ******************************************************************************************.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select count bd to claim
	 */
	/**
	 * claim total count - claim 갯수 구함.
	 * claim list search
	 * @param keyParamDto
	 * @return
	 */
	public String getSelectCountBDToClaim(BgabGasc01KeyDto keyParamDto){
		return (String)getSqlMapClientTemplate().queryForObject(SELECT_COUNT_BD_TO_CLAIM, keyParamDto);
	}
	
	/**
	 * claim total count - claim 목록 조회
	 * claim list search.
	 *
	 * @param keyParamDto the key param dto
	 * @return the select list bd to claim
	 */
	@SuppressWarnings("unchecked")
	public List<BgabGasc01DtlDto> getSelectListBDToClaim(BgabGasc01KeyDto keyParamDto){
		return getSqlMapClientTemplate().queryForList(SELECT_LIST_BD_TO_CLAIM, keyParamDto);
	}
	
	/**
	 * claim detail search - claim 상세 내용 조회.
	 *
	 * @param keyDto the key dto
	 * @return the select detail bd to claim
	 */
	public BgabGasc01DtlDto getSelectDetailBDToClaim(BgabGasc01KeyDto keyDto){
		return (BgabGasc01DtlDto)getSqlMapClientTemplate().queryForObject(SELECT_DETAIL_BD_TO_CLAIM, keyDto);
	}
	
	/**
	 * claim write -  Claim 내용 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object insertInfoBDToClaim(BgabGasc01DtlDto dtlDto){
		return super.insert(MERGE_INFO_BD_TO_CLAIM, dtlDto);
	}
	
	/**
	 * claim update - Claim 수정.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object updateInfoBDToClaim(BgabGasc01DtlDto dtlDto){
		return super.insert(MERGE_INFO_BD_TO_CLAIM, dtlDto);
	}
	
	/**
	 * claim delete - Claim 삭제.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object deleteInfoBDToClaim(BgabGasc01DtlDto dtlDto){
		return super.insert(DELETE_INFO_BD_TO_CLAIM, dtlDto);
	}
	
	/**
	 * claim reply write - Claim reply 입력.
	 *
	 * @param dtlDto the dtl dto
	 * @return the object
	 */
	public Object replyInfoBDToClaim(BgabGasc01DtlDto dtlDto){
		return super.insert(REPLY_INFO_BD_TO_CLAIM, dtlDto);
	}

	@SuppressWarnings("unchecked")
	public List<CommonCode> getSelectToJobCombo(CommonCode code){
		return getSqlMapClientTemplate().queryForList(SELECT_TO_JOB_COMBO, code);
	}
	
	/**
	 * Meal Menu List(brasilian, korean)
	 * @param paramVo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<BgabGascz019Dto> doSearchToBrMealMenu(BgabGascz019Dto paramVo){
		return getSqlMapClientTemplate().queryForList("selectToBrMealMenu", paramVo);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascz019Dto> doSearchToKrMealMenu(BgabGascz019Dto paramVo){
		return getSqlMapClientTemplate().queryForList("selectToKrMealMenu", paramVo);
	}
	
	@SuppressWarnings("unchecked")
	public List<BgabGascz019Dto> doSearchToCoffeeMenu(BgabGascz019Dto paramVo){
		return getSqlMapClientTemplate().queryForList("selectToCoffeeMenu", paramVo);
	}
	
	public BgabGascz019Dto doMealToWeekCnt(String strDate){
		return (BgabGascz019Dto)getSqlMapClientTemplate().queryForObject("selectMealToWeekCnt", strDate);
	}
}
