package com.hncis.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hncis.batch.job.vo.dto.BgabGascI002Dto;
import com.hncis.common.util.StringUtil;

@Component("bgabGascI002DtoRowMapper")
public class BgabGascI002DtoRowMapper implements RowMapper, FieldSetMapper<BgabGascI002Dto> {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BgabGascI002Dto bgabGascI002Dto = new BgabGascI002Dto();
		bgabGascI002Dto.setDept_id(rs.getString("dept_id"));
		bgabGascI002Dto.setDept_nm(rs.getString("dept_nm"));
		bgabGascI002Dto.setUpr_dept_id(StringUtil.isNullToString(rs.getString("upr_dept_id")));
		bgabGascI002Dto.setMngr_job_titl_cd(StringUtil.isNullToString(rs.getString("mngr_job_titl_cd")));
		bgabGascI002Dto.setMngr_job_titl_nm(StringUtil.isNullToString(rs.getString("mngr_job_titl_nm")));
		bgabGascI002Dto.setMngr_usn(rs.getString("mngr_usn"));
		bgabGascI002Dto.setMngr_nm(StringUtil.isNullToString(rs.getString("mngr_nm")));
		bgabGascI002Dto.setWrkplc_cd(rs.getString("wrkplc_cd"));
		bgabGascI002Dto.setWrkplc_nm(StringUtil.isNullToString(rs.getString("wrkplc_nm")));
		bgabGascI002Dto.setCorp_cd(StringUtil.isNullToString(rs.getString("corp_cd")));
		bgabGascI002Dto.setCorp_nm(StringUtil.isNullToString(rs.getString("corp_nm")));
		bgabGascI002Dto.setDept_lv(rs.getString("dept_lv"));
		bgabGascI002Dto.setDept_lv_nm(StringUtil.isNullToString(rs.getString("dept_lv_nm")));
		bgabGascI002Dto.setClos_d(rs.getString("clos_d"));
		return bgabGascI002Dto;
	}

	public BgabGascI002Dto mapFieldSet(FieldSet fieldSet) {
		
		if(fieldSet==null) return null;
		
		BgabGascI002Dto bgabGascI002Dto = new BgabGascI002Dto();
		return bgabGascI002Dto;
	}

}
