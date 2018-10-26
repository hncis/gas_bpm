package com.hncis.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hncis.trafficTicket.vo.BgabGascTm01;

@Component("bgabGascTm01DtoRowMapper")
public class BgabGascTm01DtoRowMapper implements RowMapper, FieldSetMapper<BgabGascTm01> {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		BgabGascTm01 bgabGascTm01 = new BgabGascTm01();
		
		bgabGascTm01.setDoc_no(rs.getString("doc_no"));
		bgabGascTm01.setEeno(rs.getString("eeno"));
		bgabGascTm01.setCar_no(rs.getString("car_no"));
		bgabGascTm01.setTic_no(rs.getString("tic_no"));
		bgabGascTm01.setTic_ymd(rs.getString("tic_ymd"));
		bgabGascTm01.setTic_pint(rs.getString("tic_pint"));
		bgabGascTm01.setTic_amt(rs.getString("tic_amt"));
		bgabGascTm01.setTic_desc(rs.getString("tic_desc"));
		
		return bgabGascTm01;
	}

	public BgabGascTm01 mapFieldSet(FieldSet fieldSet) {
		
		if(fieldSet==null) return null;
		
		BgabGascTm01 bgabGascTm01 = new BgabGascTm01();
		return bgabGascTm01;
	}
}
