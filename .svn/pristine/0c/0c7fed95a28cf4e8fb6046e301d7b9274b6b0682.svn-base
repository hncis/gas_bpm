package com.hncis.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hncis.common.vo.BgabGascz002Dto;

@Component("bgabGascz002DtoRowMapper")
public class BgabGascz002DtoRowMapper implements RowMapper, FieldSetMapper<BgabGascz002Dto> {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		BgabGascz002Dto insa = new BgabGascz002Dto();
		return insa;
	}

	public BgabGascz002Dto mapFieldSet(FieldSet fieldSet) {
		
		if(fieldSet==null) return null;
		
		BgabGascz002Dto insa = new BgabGascz002Dto();
		return insa;
	}

}
