package com.hncis.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hncis.batch.job.vo.dto.BgabGascI001Dto;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;

@Component("asVehiclesDtoRowMapper")
public class AsVehiclesDtoRowMapper implements RowMapper, FieldSetMapper<BgabGascbv01Dto> {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BgabGascbv01Dto bgabGascbv01Dto = new BgabGascbv01Dto();
		
		bgabGascbv01Dto.setCar_no(rs.getString("car_no"));
		bgabGascbv01Dto.setCrgr_eeno(rs.getString("crgr_eeno"));
		bgabGascbv01Dto.setFxt_ins_infm_nos(rs.getString("fxt_ins_infm_nos"));
		bgabGascbv01Dto.setAs_type(rs.getString("as_type"));
		bgabGascbv01Dto.setChss_no(rs.getString("chss_no"));
		bgabGascbv01Dto.setEeno_email(rs.getString("eeno_email"));
		
		return bgabGascbv01Dto;
	}

	public BgabGascbv01Dto mapFieldSet(FieldSet fieldSet) {
		
		if(fieldSet==null) return null;
		
		BgabGascbv01Dto bgabGascbv01Dto = new BgabGascbv01Dto();
		return bgabGascbv01Dto;
	}

}
