package com.hncis.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hncis.common.vo.HncisCommon;

@Component("commonDtoRowMapper")
public class CommonDtoRowMapper implements RowMapper, FieldSetMapper<HncisCommon> {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		HncisCommon commonDto = new HncisCommon();
		
		commonDto.setValue(rs.getString("value"));
		
		return commonDto;
	}

	public HncisCommon mapFieldSet(FieldSet fieldSet) {
		
		if(fieldSet==null) return null;
		
		HncisCommon commonDto = new HncisCommon();
		return commonDto;
	}
}
