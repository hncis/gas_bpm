package com.hncis.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hncis.batch.job.vo.dto.BgabGascI001Dto;

@Component("bgabGascI001DtoRowMapper")
public class BgabGascI001DtoRowMapper implements RowMapper, FieldSetMapper<BgabGascI001Dto> {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BgabGascI001Dto bgabGascI001Dto = new BgabGascI001Dto();
		
		bgabGascI001Dto.setUsn(rs.getString("usn"));
		bgabGascI001Dto.setUsr_nm(rs.getString("usr_nm"));
		bgabGascI001Dto.setDprtmt_cd(rs.getString("dprtmt_cd"));
		bgabGascI001Dto.setDprtmt_nm(rs.getString("dprtmt_nm"));
		bgabGascI001Dto.setJob_titl_cd(rs.getString("job_titl_cd"));
		bgabGascI001Dto.setJob_titl_nm(rs.getString("job_titl_nm"));
		bgabGascI001Dto.setEmail(rs.getString("email"));
		bgabGascI001Dto.setCmpny_cd(rs.getString("cmpny_cd"));
		bgabGascI001Dto.setCmpny_nm(rs.getString("cmpny_nm"));
		//bgabGascI001Dto.setHome_phone_num(rs.getString("home_phone_num"));
		//bgabGascI001Dto.setFax_num(rs.getString("fax_num"));
		bgabGascI001Dto.setMp_num(rs.getString("mp_num"));
		//bgabGascI001Dto.setBp_num(rs.getString("bp_num"));
		bgabGascI001Dto.setWrkplc_cd(rs.getString("wrkplc_cd"));
		bgabGascI001Dto.setWrkplc_nm(rs.getString("wrkplc_nm"));
		bgabGascI001Dto.setTitle_cd(rs.getString("title_cd"));
		bgabGascI001Dto.setTitle_nm(rs.getString("title_nm"));
		//bgabGascI001Dto.setTitle_lev(rs.getString("title_lev"));
		//bgabGascI001Dto.setDpco_yn(rs.getString("dpco_yn"));
		/*
		bgabGascI001Dto.setRebusi_nm(rs.getString("rebusi_nm"));
		bgabGascI001Dto.setName_en(rs.getString("name_en"));
		bgabGascI001Dto.setDty_cd(rs.getString("dty_cd"));
		bgabGascI001Dto.setDaynnight_asrtmnt(rs.getString("daynnight_asrtmnt"));
		bgabGascI001Dto.setPstn_cd(rs.getString("pstn_cd"));
		bgabGascI001Dto.setJoin_cmpny_date(rs.getString("join_cmpny_date"));
		bgabGascI001Dto.setEmployee_group(rs.getString("employee_group"));
		bgabGascI001Dto.setEmployee_sub_group(rs.getString("employee_sub_group"));
		bgabGascI001Dto.setWork_yn(rs.getString("work_yn"));
		bgabGascI001Dto.setBotm_sctr_nm(rs.getString("botm_sctr_nm"));
		bgabGascI001Dto.setBotm_sctr_cd(rs.getString("botm_sctr_cd"));
		bgabGascI001Dto.setUp_dprtmt_cd(rs.getString("up_dprtmt_cd"));
		bgabGascI001Dto.setCnfm_auth(rs.getString("cnfm_auth"));
		bgabGascI001Dto.setGender_cd(rs.getString("gender_cd")); //add column
		*/
		bgabGascI001Dto.setWork_yn(rs.getString("work_yn"));
		bgabGascI001Dto.setUp_dprtmt_cd(rs.getString("up_dprtmt_cd"));
		bgabGascI001Dto.setCnfm_auth(rs.getString("cnfm_auth"));
		bgabGascI001Dto.setCc_cd(rs.getString("cc_cd"));
		bgabGascI001Dto.setCc_nm(rs.getString("cc_nm"));
		
		
		bgabGascI001Dto.setStreet(rs.getString("street"));
		bgabGascI001Dto.setHouse(rs.getString("house"));
		bgabGascI001Dto.setAptmt(rs.getString("aptmt"));
		bgabGascI001Dto.setCity(rs.getString("city"));
		bgabGascI001Dto.setDistrict(rs.getString("district"));
		bgabGascI001Dto.setPostal_code(rs.getString("postal_code"));
		bgabGascI001Dto.setWork_phone_no(rs.getString("work_phone_no"));
		bgabGascI001Dto.setBenefit_plan_cd(rs.getString("benefit_plan_cd"));
		bgabGascI001Dto.setWork_schedule_cd(rs.getString("work_schedule_cd"));
		bgabGascI001Dto.setBenefit_plan_nm(rs.getString("benefit_plan_nm"));
		bgabGascI001Dto.setWork_schedule_nm(rs.getString("work_schedule_nm"));
		
		return bgabGascI001Dto;
	}

	public BgabGascI001Dto mapFieldSet(FieldSet fieldSet) {
		
		if(fieldSet==null) return null;
		
		BgabGascI001Dto bgabGascI001Dto = new BgabGascI001Dto();
		return bgabGascI001Dto;
	}

}
