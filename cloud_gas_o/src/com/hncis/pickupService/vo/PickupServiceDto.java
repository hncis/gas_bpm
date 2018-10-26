package com.hncis.pickupService.vo;

import com.hncis.common.vo.Common;

public class PickupServiceDto extends Common{

	private String cb_key 		= "";
	private String cb_code 	= "";
	private String cb_value 	= "";
	
	public String getCb_key() {
		return cb_key;
	}
	public void setCb_key(String cb_key) {
		this.cb_key = cb_key;
	}
	public String getCb_code() {
		return cb_code;
	}
	public void setCb_code(String cb_code) {
		this.cb_code = cb_code;
	}
	public String getCb_value() {
		return cb_value;
	}
	public void setCb_value(String cb_value) {
		this.cb_value = cb_value;
	}
	

	
	
}
