package com.hncis.board.vo;

import com.hncis.common.vo.Common;

// TODO: Auto-generated Javadoc
/**
 * The Class BgabGasc01DtlDto.
 */
public class BgabGasc01DtlDto extends Common{

	/** The bod_indx. - 게시물 순번*/
	private int bod_indx          = 0;

	/** The bod_sano. - 게시물 작성자 사번*/
	private String bod_sano       = "";

	/** The bod_sanm. - 게시물 작성자 명*/
	private String bod_sanm       = "";

	/** The bod_ovl_nm. */
	private String bod_ovl_nm     = "";

	/** The bod_ops_nm. */
	private String bod_ops_nm     = "";

	/** The bod_email. - 게시물 작성자 이메일*/
	private String bod_email      = "";

	/** The bod_hpno1. - 게시물 작성자 전화번호1*/
	private String bod_hpno1      = "";

	/** The bod_hpno2. - 게시물 작성자 전화번호2*/
	private String bod_hpno2      = "";

	/** The bod_hpno3. - 게시물 작성자 전화번호3*/
	private String bod_hpno3      = "";

	/** The bod_jgbn. - 업무구분*/
	private String bod_jgbn       = "";

	/** The bod_jgbnnm. - 업무구분 명*/
	private String bod_jgbnnm     = "";

	/** The bod_agbn. - 지역구분*/
	private String bod_agbn       = "";

	/** The bod_qgbn. */
	private String bod_qgbn       = "";

	/** The bod_type. - 게시물 타입(N:notice, Q :Q&A)*/
	private String bod_type       = "";

	/** The bod_fdat. - 게시물 게시 시작일자*/
	private String bod_fdat       = "";

	/** The bod_ftim. - 게시물 게시 시작시간*/
	private String bod_ftim       = "";

	/** The bod_tdat. - 게시물 게시 종료일자*/
	private String bod_tdat       = "";

	/** The bod_ttim. - 게시물 게시 종료시간*/
	private String bod_ttim       = "";

	/** The bod_popyn.- 게시물 팝업 게시여부*/
	private String bod_popyn      = "";

	/** The bod_home. */
	private String bod_home       = "";

	/** The bod_title. - 게시물 제목*/
	private String bod_title      = "";

	/** The bod_content. - 게시물 내용 */
	private String bod_content    = "";

	/** The bod_pass. - 게시물 암호*/
	private String bod_pass       = "";

	/** The bod_group. - 게시물 그룹*/
	private String bod_group      = "";

	/** The bod_step. - 게시물 원래작성 글인지 답글인지 구분*/
	private String bod_step       = "";

	/** The bod_level. - 게시물 레벨*/
	private String bod_level      = "";

	/** The bod_read. - 게시물 읽은 횟수*/
	private String bod_read       = "";

	/** The bod_fname. - 게시물 첨부파일명*/
	private String bod_fname      = "";

	/** The old_bod_fname. - 게시물 예전 첨부파일명*/
	private String old_bod_fname  = "";

	/** The bod_fsize. - 게시물 첨부파일 크기*/
	private String bod_fsize      = "";

	/** The bod_down. - 게시물 첨부파일 다운횟수*/
	private String bod_down       = "";

	/** The bod_authsano. */
	private String bod_authsano   = "";

	/** The bod_solve. */
	private String bod_solve      = "";

	/** The bod_stat. */
	private String bod_stat       = "";

	/** The bod_ichage. */
	private String bod_ichage     = "";

	/** The bod_ichagenm. */
	private String bod_ichagenm   = "";

	/** The inp_ymd. - 게시물 입력일자 */
	private String inp_ymd        = "";

	/** The ipe_eeno. - 게시물 작성자 사번 */
	private String ipe_eeno       = "";

	/** The updr_ymd. - 게시물 수정일자 */
	private String updr_ymd       = "";

	/** The updr_eeno. - 게시물 수정자 사번 */
	private String updr_eeno      = "";

	/** The bod_eeno. - 게시물 작성자 사번 */
	private String bod_eeno       = "";

	/** The bod_eenm. - 게시물 작성자 명 */
	private String bod_eenm       = "";

	/** The eeno. - 게시물 작성자 사번 */
	private String  eeno          = "";

	/** The eenm. - 게시물 작성자 명 */
	private String  eenm          = "";

	/** The dept name. - 게시물 부서 명 */
	private String  deptName      = "";

	/** The step name. - 게시물 직급 명 */
	private String  stepName      = "";

	/** The hpno. - 게시물 작성자 전화번호 */
	private String  hpno          = "";

	/** The bod_dept code. - 게시물 부서코드 */
	private String  bod_deptCode  ;

	/** The bod_step code. - 게시물 직급코드 */
	private String  bod_stepCode  ;

	/** The bod_secret - 게시물 공개/비공개 */
	private String  bod_secret  ;

	/** The bod_secret - 게시물 공개/비공개 이름*/
	private String  bod_secretnm  ;

	/** The bod_secret - 게시물 암호 확인*/
	private Boolean  bod_passConfirm  ;

	private String csrfToken = "";


	public String getCsrfToken() {
		return csrfToken;
	}

	public void setCsrfToken(String csrfToken) {
		this.csrfToken = csrfToken;
	}

	/**
	 * Gets the bod_eeno.
	 * 게시물 작성자 사번 값 가져옴
	 * @return the bod_eeno
	 */
	public String getBod_eeno() {
		return bod_eeno;
	}

	/**
	 * Sets the bod_eeno.
	 * 게시물 작성자 사번 값 설정
	 * @param bodEeno the new bod_eeno
	 */
	public void setBod_eeno(String bodEeno) {
		bod_eeno = bodEeno;
	}

	/**
	 * Gets the bod_eenm.
	 * 게시물 작성자 명 값 가져옴
	 * @return the bod_eenm
	 */
	public String getBod_eenm() {
		return bod_eenm;
	}

	/**
	 * Sets the bod_eenm.
	 * 게시물 작성자 명 값 설정
	 * @param bodEenm the new bod_eenm
	 */
	public void setBod_eenm(String bodEenm) {
		bod_eenm = bodEenm;
	}

	/**
	 * Gets the bod_indx.
	 * 게시물 순번 값 가져옴
	 * @return the bod_indx
	 */
	public int getBod_indx() {
		return bod_indx;
	}

	/**
	 * Sets the bod_indx.
	 * 게시물 순번 값 설정
	 * @param bod_indx the new bod_indx
	 */
	public void setBod_indx(int bod_indx) {
		this.bod_indx = bod_indx;
	}

	/**
	 * Gets the bod_sano.
	 * 게시물 작성자 사번 값 가져옴
	 * @return the bod_sano
	 */
	public String getBod_sano() {
		return bod_sano;
	}

	/**
	 * Sets the bod_sano.
	 * 게시물 작성자 사번 값 설정
	 * @param bod_sano the new bod_sano
	 */
	public void setBod_sano(String bod_sano) {
		this.bod_sano = bod_sano;
	}

	/**
	 * Gets the bod_sanm.
	 * 게시물 작성자 명 값 가져옴
	 * @return the bod_sanm
	 */
	public String getBod_sanm() {
		return bod_sanm;
	}

	/**
	 * Sets the bod_sanm.
	 * 게시물 작성자 명 값 설정
	 * @param bod_sanm the new bod_sanm
	 */
	public void setBod_sanm(String bod_sanm) {
		this.bod_sanm = bod_sanm;
	}

	/**
	 * Gets the bod_ovl_nm.
	 * bod_ovl_nm 값 가져옴
	 * @return the bod_ovl_nm
	 */
	public String getBod_ovl_nm() {
		return bod_ovl_nm;
	}

	/**
	 * Sets the bod_ovl_nm.
	 * bod_ovl_nm 값 설정
	 * @param bod_ovl_nm the new bod_ovl_nm
	 */
	public void setBod_ovl_nm(String bod_ovl_nm) {
		this.bod_ovl_nm = bod_ovl_nm;
	}

	/**
	 * Gets the bod_ops_nm.
	 * bod_ops_nm 값 가져옴
	 * @return the bod_ops_nm
	 */
	public String getBod_ops_nm() {
		return bod_ops_nm;
	}

	/**
	 * Sets the bod_ops_nm.
	 * bod_ops_nm 값 설정
	 * @param bod_ops_nm the new bod_ops_nm
	 */
	public void setBod_ops_nm(String bod_ops_nm) {
		this.bod_ops_nm = bod_ops_nm;
	}

	/**
	 * Gets the bod_email.
	 * 게시물 작성자 이메일 값 가져옴
	 * @return the bod_email
	 */
	public String getBod_email() {
		return bod_email;
	}

	/**
	 * Sets the bod_email.
	 * 게시물 작성자 이메일 값 설정
	 * @param bod_email the new bod_email
	 */
	public void setBod_email(String bod_email) {
		this.bod_email = bod_email;
	}

	/**
	 * Gets the bod_hpno1.
	 * 게시물 작성자 전화번호1 값 가져옴
	 * @return the bod_hpno1
	 */
	public String getBod_hpno1() {
		return bod_hpno1;
	}

	/**
	 * Sets the bod_hpno1.
	 * 게시물 작성자 전화번호1 값 설정
	 * @param bod_hpno1 the new bod_hpno1
	 */
	public void setBod_hpno1(String bod_hpno1) {
		this.bod_hpno1 = bod_hpno1;
	}

	/**
	 * Gets the bod_hpno2.
	 * 게시물 작성자 전화번호2 값 가져옴
	 * @return the bod_hpno2
	 */
	public String getBod_hpno2() {
		return bod_hpno2;
	}

	/**
	 * Sets the bod_hpno2.
	 * 게시물 작성자 전화번호2 값 설정
	 * @param bod_hpno2 the new bod_hpno2
	 */
	public void setBod_hpno2(String bod_hpno2) {
		this.bod_hpno2 = bod_hpno2;
	}

	/**
	 * Gets the bod_hpno3.
	 * 게시물 작성자 전화번호3 값 가져옴
	 * @return the bod_hpno3
	 */
	public String getBod_hpno3() {
		return bod_hpno3;
	}

	/**
	 * Sets the bod_hpno3.
	 * 게시물 작성자 전화번호3 값 설정
	 * @param bod_hpno3 the new bod_hpno3
	 */
	public void setBod_hpno3(String bod_hpno3) {
		this.bod_hpno3 = bod_hpno3;
	}

	/**
	 * Gets the bod_jgbn.
	 * 업무구분 값 가져옴
	 * @return the bod_jgbn
	 */
	public String getBod_jgbn() {
		return bod_jgbn;
	}

	/**
	 * Sets the bod_jgbn.
	 * 업무구분 값 설정
	 * @param bod_jgbn the new bod_jgbn
	 */
	public void setBod_jgbn(String bod_jgbn) {
		this.bod_jgbn = bod_jgbn;
	}

	/**
	 * Gets the bod_jgbnnm.
	 * 업무구분 명 값 가져옴
	 * @return the bod_jgbnnm
	 */
	public String getBod_jgbnnm() {
		return bod_jgbnnm;
	}

	/**
	 * Sets the bod_jgbnnm.
	 * 업무구분 명 값 설정
	 * @param bod_jgbnnm the new bod_jgbnnm
	 */
	public void setBod_jgbnnm(String bod_jgbnnm) {
		this.bod_jgbnnm = bod_jgbnnm;
	}

	/**
	 * Gets the bod_agbn.
	 * 지역구분 값 가져옴
	 * @return the bod_agbn
	 */
	public String getBod_agbn() {
		return bod_agbn;
	}

	/**
	 * Sets the bod_agbn.
	 * 지역구분 값 설정
	 * @param bod_agbn the new bod_agbn
	 */
	public void setBod_agbn(String bod_agbn) {
		this.bod_agbn = bod_agbn;
	}

	/**
	 * Gets the bod_qgbn.
	 * bod_qgbn 값 가져옴
	 * @return the bod_qgbn
	 */
	public String getBod_qgbn() {
		return bod_qgbn;
	}

	/**
	 * Sets the bod_qgbn.
	 * bod_qgbn 값 설정
	 * @param bod_qgbn the new bod_qgbn
	 */
	public void setBod_qgbn(String bod_qgbn) {
		this.bod_qgbn = bod_qgbn;
	}

	/**
	 * Gets the bod_type.
	 * 게시물 타입 값 가져옴
	 * @return the bod_type
	 */
	public String getBod_type() {
		return bod_type;
	}

	/**
	 * Sets the bod_type.
	 * 게시물 타입 값 설정
	 * @param bod_type the new bod_type
	 */
	public void setBod_type(String bod_type) {
		this.bod_type = bod_type;
	}

	/**
	 * Gets the bod_fdat.
	 * 게시물 게시 시작일자 값 가져옴
	 * @return the bod_fdat
	 */
	public String getBod_fdat() {
		return bod_fdat;
	}

	/**
	 * Sets the bod_fdat.
	 * 게시물 게시 시작일자 값 설정
	 * @param bod_fdat the new bod_fdat
	 */
	public void setBod_fdat(String bod_fdat) {
		this.bod_fdat = bod_fdat;
	}

	/**
	 * Gets the bod_ftim.
	 * 게시물 게시 시작시간 값 가져옴
	 * @return the bod_ftim
	 */
	public String getBod_ftim() {
		return bod_ftim;
	}

	/**
	 * Sets the bod_ftim.
	 * 게시물 게시 시작시간 값 설정
	 * @param bod_ftim the new bod_ftim
	 */
	public void setBod_ftim(String bod_ftim) {
		this.bod_ftim = bod_ftim;
	}

	/**
	 * Gets the bod_tdat.
	 * 게시물 게시 종료일자 값 가져옴
	 * @return the bod_tdat
	 */
	public String getBod_tdat() {
		return bod_tdat;
	}

	/**
	 * Sets the bod_tdat.
	 * 게시물 게시 종료일자 값 설정
	 * @param bod_tdat the new bod_tdat
	 */
	public void setBod_tdat(String bod_tdat) {
		this.bod_tdat = bod_tdat;
	}

	/**
	 * Gets the bod_ttim.
	 * 게시물 게시 종료시간 값 가져옴
	 * @return the bod_ttim
	 */
	public String getBod_ttim() {
		return bod_ttim;
	}

	/**
	 * Sets the bod_ttim.
	 * 게시물 게시 종료시간 값 설정
	 * @param bod_ttim the new bod_ttim
	 */
	public void setBod_ttim(String bod_ttim) {
		this.bod_ttim = bod_ttim;
	}

	/**
	 * Gets the bod_popyn.
	 * 게시물 팝업 게시여부 값 가져옴
	 * @return the bod_popyn
	 */
	public String getBod_popyn() {
		return bod_popyn;
	}

	/**
	 * Sets the bod_popyn.
	 * 게시물 팝업 게시여부 값 설정
	 * @param bod_popyn the new bod_popyn
	 */
	public void setBod_popyn(String bod_popyn) {
		this.bod_popyn = bod_popyn;
	}

	/**
	 * Gets the bod_home.
	 * bod_home 값 가져옴
	 * @return the bod_home
	 */
	public String getBod_home() {
		return bod_home;
	}

	/**
	 * Sets the bod_home.
	 * bod_home 값 설정
	 * @param bod_home the new bod_home
	 */
	public void setBod_home(String bod_home) {
		this.bod_home = bod_home;
	}

	/**
	 * Gets the bod_title.
	 * 게시물 제목 값 가져옴
	 * @return the bod_title
	 */
	public String getBod_title() {
		return bod_title;
	}

	/**
	 * Sets the bod_title.
	 * 게시물 제목 값 설정
	 * @param bod_title the new bod_title
	 */
	public void setBod_title(String bod_title) {
		this.bod_title = bod_title;
	}

	/**
	 * Gets the bod_content.
	 * 게시물 내용 값 가져옴
	 * @return the bod_content
	 */
	public String getBod_content() {
		return bod_content;
	}

	/**
	 * Sets the bod_content.
	 * 게시물 내용 값 설정
	 * @param bod_content the new bod_content
	 */
	public void setBod_content(String bod_content) {
		this.bod_content = bod_content;
	}

	/**
	 * Gets the bod_pass.
	 * 게시물 암호 값 가져옴
	 * @return the bod_pass
	 */
	public String getBod_pass() {
		return bod_pass;
	}

	/**
	 * Sets the bod_pass.
	 * 게시물 암호 값 설정
	 * @param bod_pass the new bod_pass
	 */
	public void setBod_pass(String bod_pass) {
		this.bod_pass = bod_pass;
	}

	/**
	 * Gets the bod_group.
	 * 게시물 그룹 값 가져옴
	 * @return the bod_group
	 */
	public String getBod_group() {
		return bod_group;
	}

	/**
	 * Sets the bod_group.
	 * 게시물 그룹 값 설정
	 * @param bod_group the new bod_group
	 */
	public void setBod_group(String bod_group) {
		this.bod_group = bod_group;
	}

	/**
	 * Gets the bod_step.
	 * 게시물 원래작성 글인지 답글인지 구분 값 가져옴
	 * @return the bod_step
	 */
	public String getBod_step() {
		return bod_step;
	}

	/**
	 * Sets the bod_step.
	 * 게시물 원래작성 글인지 답글인지 구분 값 설정
	 * @param bod_step the new bod_step
	 */
	public void setBod_step(String bod_step) {
		this.bod_step = bod_step;
	}

	/**
	 * Gets the bod_level.
	 * 게시물 레벨 값 가져옴
	 * @return the bod_level
	 */
	public String getBod_level() {
		return bod_level;
	}

	/**
	 * Sets the bod_level.
	 * 게시물 레벨 값 설정
	 * @param bod_level the new bod_level
	 */
	public void setBod_level(String bod_level) {
		this.bod_level = bod_level;
	}

	/**
	 * Gets the bod_read.
	 * 게시물 읽은 횟수 값 가져옴
	 * @return the bod_read
	 */
	public String getBod_read() {
		return bod_read;
	}

	/**
	 * Sets the bod_read.
	 * 게시물 읽은 횟수 값 설정
	 * @param bod_read the new bod_read
	 */
	public void setBod_read(String bod_read) {
		this.bod_read = bod_read;
	}

	/**
	 * Gets the bod_fname.
	 * 게시물 첨부파일명 값 가져옴
	 * @return the bod_fname
	 */
	public String getBod_fname() {
		return bod_fname;
	}

	/**
	 * Sets the bod_fname.
	 * 게시물 첨부파일명 값 설정
	 * @param bod_fname the new bod_fname
	 */
	public void setBod_fname(String bod_fname) {
		this.bod_fname = bod_fname;
	}

	/**
	 * Gets the old_bod_fname.
	 * 게시물 예전 첨부파일명 값 가져옴
	 * @return the old_bod_fname
	 */
	public String getOld_bod_fname() {
		return old_bod_fname;
	}

	/**
	 * Sets the old_bod_fname.
	 * 게시물 예전 첨부파일명 값 설정
	 * @param old_bod_fname the new old_bod_fname
	 */
	public void setOld_bod_fname(String old_bod_fname) {
		this.old_bod_fname = old_bod_fname;
	}

	/**
	 * Gets the bod_fsize.
	 * 게시물 첨부파일 크기 값 가져옴
	 * @return the bod_fsize
	 */
	public String getBod_fsize() {
		return bod_fsize;
	}

	/**
	 * Sets the bod_fsize.
	 * 게시물 첨부파일 크기 값 설정
	 * @param bod_fsize the new bod_fsize
	 */
	public void setBod_fsize(String bod_fsize) {
		this.bod_fsize = bod_fsize;
	}

	/**
	 * Gets the bod_down.
	 * 게시물 첨부파일 다운횟수 값 가져옴
	 * @return the bod_down
	 */
	public String getBod_down() {
		return bod_down;
	}

	/**
	 * Sets the bod_down.
	 * 게시물 첨부파일 다운횟수 값 설정
	 * @param bod_down the new bod_down
	 */
	public void setBod_down(String bod_down) {
		this.bod_down = bod_down;
	}

	/**
	 * Gets the bod_authsano.
	 * bod_authsano 값 가져옴
	 * @return the bod_authsano
	 */
	public String getBod_authsano() {
		return bod_authsano;
	}

	/**
	 * Sets the bod_authsano.
	 * bod_authsano 값 설정
	 * @param bod_authsano the new bod_authsano
	 */
	public void setBod_authsano(String bod_authsano) {
		this.bod_authsano = bod_authsano;
	}

	/**
	 * Gets the bod_solve.
	 * bod_solve 값 가져옴
	 * @return the bod_solve
	 */
	public String getBod_solve() {
		return bod_solve;
	}

	/**
	 * Sets the bod_solve.
	 * bod_solve 값 설정
	 * @param bod_solve the new bod_solve
	 */
	public void setBod_solve(String bod_solve) {
		this.bod_solve = bod_solve;
	}

	/**
	 * Gets the bod_stat.
	 * bod_stat 값 가져옴
	 * @return the bod_stat
	 */
	public String getBod_stat() {
		return bod_stat;
	}

	/**
	 * Sets the bod_stat.
	 * bod_stat 값 설정
	 * @param bod_stat the new bod_stat
	 */
	public void setBod_stat(String bod_stat) {
		this.bod_stat = bod_stat;
	}

	/**
	 * Gets the bod_ichage.
	 * bod_ichage 값 가져옴
	 * @return the bod_ichage
	 */
	public String getBod_ichage() {
		return bod_ichage;
	}

	/**
	 * Sets the bod_ichage.
	 * bod_ichage 값 설정
	 * @param bod_ichage the new bod_ichage
	 */
	public void setBod_ichage(String bod_ichage) {
		this.bod_ichage = bod_ichage;
	}

	/**
	 * Gets the bod_ichagenm.
	 * bod_ichagenm 값 가져옴
	 * @return the bod_ichagenm
	 */
	public String getBod_ichagenm() {
		return bod_ichagenm;
	}

	/**
	 * Sets the bod_ichagenm.
	 * bod_ichagenm 값 설정
	 * @param bod_ichagenm the new bod_ichagenm
	 */
	public void setBod_ichagenm(String bod_ichagenm) {
		this.bod_ichagenm = bod_ichagenm;
	}

	/**
	 * Gets the inp_ymd.
	 * 게시물 입력일자 값 가져옴
	 * @return the inp_ymd
	 */
	public String getInp_ymd() {
		return inp_ymd;
	}

	/**
	 * Sets the inp_ymd.
	 * 게시물 입력일자 값 설정
	 * @param inp_ymd the new inp_ymd
	 */
	public void setInp_ymd(String inp_ymd) {
		this.inp_ymd = inp_ymd;
	}

	/**
	 * Gets the ipe_eeno.
	 * 게시물 작성자 사번 값 가져옴
	 * @return the ipe_eeno
	 */
	public String getIpe_eeno() {
		return ipe_eeno;
	}

	/**
	 * Sets the ipe_eeno.
	 * 게시물 작성자 사번 값 설정
	 * @param ipe_eeno the new ipe_eeno
	 */
	public void setIpe_eeno(String ipe_eeno) {
		this.ipe_eeno = ipe_eeno;
	}

	/**
	 * Gets the updr_ymd.
	 * 게시물 수정일자 값 가져옴
	 * @return the updr_ymd
	 */
	public String getUpdr_ymd() {
		return updr_ymd;
	}

	/**
	 * Sets the updr_ymd.
	 * 게시물 수정일자 값 설정
	 * @param updr_ymd the new updr_ymd
	 */
	public void setUpdr_ymd(String updr_ymd) {
		this.updr_ymd = updr_ymd;
	}

	/**
	 * Gets the updr_eeno.
	 * 게시물 수정자 사번 값 가져옴
	 * @return the updr_eeno
	 */
	public String getUpdr_eeno() {
		return updr_eeno;
	}

	/**
	 * Sets the updr_eeno.
	 * 게시물 수정자 사번 값 설정
	 * @param updr_eeno the new updr_eeno
	 */
	public void setUpdr_eeno(String updr_eeno) {
		this.updr_eeno = updr_eeno;
	}

	/**
	 * Gets the eeno.
	 * 게시물 작성자 사번 값 가져옴
	 * @return the eeno
	 */
	public String getEeno() {
		return eeno;
	}

	/**
	 * Sets the eeno.
	 * 게시물 작성자 사번 값 설정
	 * @param eeno the new eeno
	 */
	public void setEeno(String eeno) {
		this.eeno = eeno;
	}

	/**
	 * Gets the eenm.
	 * 게시물 작성자 명 값 가져옴
	 * @return the eenm
	 */
	public String getEenm() {
		return eenm;
	}

	/**
	 * Sets the eenm.
	 * 게시물 작성자 명 값 설정
	 * @param eenm the new eenm
	 */
	public void setEenm(String eenm) {
		this.eenm = eenm;
	}

	/**
	 * Gets the dept name.
	 * 게시물 부서 명 값 가져옴
	 * @return the dept name
	 */
	public String getDeptName() {
		return deptName;
	}

	/**
	 * Sets the dept name.
	 * 게시물 부서 명 값 설정
	 * @param deptName the new dept name
	 */
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 * Gets the step name.
	 * 게시물 직급 명 값 가져옴
	 * @return the step name
	 */
	public String getStepName() {
		return stepName;
	}

	/**
	 * Sets the step name.
	 * 게시물 직급 명 값 설정
	 * @param stepName the new step name
	 */
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}

	/**
	 * Gets the hpno.
	 * 게시물 작성자 전화번호 값 가져옴
	 * @return the hpno
	 */
	public String getHpno() {
		return hpno;
	}

	/**
	 * Sets the hpno.
	 * 게시물 작성자 전화번호 값 설정
	 * @param hpno the new hpno
	 */
	public void setHpno(String hpno) {
		this.hpno = hpno;
	}

	/**
	 * Gets the bod_dept code.
	 * 게시물 부서코드 값 가져옴
	 * @return the bod_dept code
	 */
	public String getBod_deptCode() {
		return bod_deptCode;
	}

	/**
	 * Sets the bod_dept code.
	 * 게시물 부서코드 값 설정
	 * @param bod_deptCode the new bod_dept code
	 */
	public void setBod_deptCode(String bod_deptCode) {
		this.bod_deptCode = bod_deptCode;
	}

	/**
	 * Gets the bod_step code.
	 * 게시물 직급코드 값 가져옴
	 * @return the bod_step code
	 */
	public String getBod_stepCode() {
		return bod_stepCode;
	}

	/**
	 * Sets the bod_step code.
	 * 게시물 직급코드 값 설정
	 * @param bod_stepCode the new bod_step code
	 */
	public void setBod_stepCode(String bod_stepCode) {
		this.bod_stepCode = bod_stepCode;
	}

	/**
	 * Gets the bod_secret code.
	 * 게시물 공개/비공개 값 가져옴
	 * @return the bod_secret code
	 */
	public String getBod_secret() {
		return bod_secret;
	}

	/**
	 * Sets the bod_secret code.
	 * 게시물 공개/비공개 값 설정
	 * @param bod_secret the new bod_secret code
	 */
	public void setBod_secret(String bod_secret) {
		this.bod_secret = bod_secret;
	}

	/**
	 * Gets the bod_secretnm name.
	 * 게시물 공개/비공개 값 가져옴
	 * @return the bod_secretnm name
	 */
	public String getBod_secretnm() {
		return bod_secretnm;
	}

	/**
	 * Sets the bod_secretnm name.
	 * 게시물 공개/비공개 값 설정
	 * @param bod_secretnm the new bod_secretnm name
	 */
	public void setBod_secretnm(String bod_secretnm) {
		this.bod_secretnm = bod_secretnm;
	}

	/**
	 * Gets the bod_passConfirm value.
	 * 게시물 암호 확인 값 가져옴
	 * @return the bod_passConfirm value
	 */
	public Boolean getBod_passConfirm() {
		return bod_passConfirm;
	}

	/**
	 * Sets the bod_passConfirm value.
	 * 게시물 암호 확인 값 설정
	 * @param bod_passConfirm the new bod_passConfirm value
	 */
	public void setBod_passConfirm(Boolean bod_passConfirm) {
		this.bod_passConfirm = bod_passConfirm;
	}








}
