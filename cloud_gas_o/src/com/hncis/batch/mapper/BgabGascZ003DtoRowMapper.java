package com.hncis.batch.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.hncis.common.vo.BgabGascz003Dto;

@Component("bgabGascZ003DtoRowMapper")
public class BgabGascZ003DtoRowMapper implements RowMapper, FieldSetMapper<BgabGascz003Dto> {
	
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		System.out.println("############# bgabGascZ003DtoRowMapper mapRow");
		BgabGascz003Dto bgabGascz003Dto = new BgabGascz003Dto();
		
		bgabGascz003Dto.setXorg_orga_c(rs.getString("xorg_orga_c"));
		bgabGascz003Dto.setXorg_orga_e(rs.getString("xorg_orga_e"));
		bgabGascz003Dto.setXorg_rsps_i(rs.getString("xorg_rsps_i"));
		bgabGascz003Dto.setXorg_rsps_m(rs.getString("xorg_rsps_m"));
		bgabGascz003Dto.setXorg_rsps_crank(rs.getString("xorg_rsps_crank"));
		bgabGascz003Dto.setXorg_rsps_mrank(rs.getString("xorg_rsps_mrank"));
		bgabGascz003Dto.setXorg_orga_csner(rs.getString("xorg_orga_csner"));
		bgabGascz003Dto.setXorg_dept_lv(rs.getString("xorg_dept_lv"));
		bgabGascz003Dto.setXorg_crd_i(rs.getString("xorg_crd_i"));
		bgabGascz003Dto.setXorg_crd_m(rs.getString("xorg_crd_m"));
		
		return bgabGascz003Dto;
	}

	public BgabGascz003Dto mapFieldSet(FieldSet fieldSet) {
		
		if(fieldSet==null) return null;
		
		BgabGascz003Dto bgabGascz003Dto = new BgabGascz003Dto();
		return bgabGascz003Dto;
	}

}
