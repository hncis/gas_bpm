package com.hncis.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hncis.common.vo.BgabGascZ011Dto;

@Component("bgabGascZ011DtoRowMapper")
public class BgabGascZ011DtoRowMapper implements RowMapper, FieldSetMapper<BgabGascZ011Dto> {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BgabGascZ011Dto bgabGascZ011Dto = new BgabGascZ011Dto();
		
		bgabGascZ011Dto.setDoc_no(rs.getString("doc_no"));
		bgabGascZ011Dto.setAffr_scn_cd(rs.getString("affr_scn_cd"));
		bgabGascZ011Dto.setOgc_fil_nm(rs.getString("ogc_fil_nm"));
		
		return bgabGascZ011Dto;
	}

	public BgabGascZ011Dto mapFieldSet(FieldSet fieldSet) {
		
		if(fieldSet==null) return null;
		
		BgabGascZ011Dto bgabGascZ011Dto = new BgabGascZ011Dto();
		return bgabGascZ011Dto;
	}
}
