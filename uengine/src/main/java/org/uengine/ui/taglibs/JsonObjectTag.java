package org.uengine.ui.taglibs;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonObjectTag extends SimpleTagSupport {
	
	private String var;

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public void doTag() throws JspException {
		String jsonString = getJsonString();
		boolean isArray = jsonString.startsWith("[");

		Object result = isArray ? parseJsonArray(jsonString) : parseJsonMap(jsonString);

		getJspContext().setAttribute(getVar(), result);
	}

	private Object parseJsonArray(String jsonString) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		return JSONArray.toCollection(jsonArray, ArrayList.class);
	}

	private Object parseJsonMap(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		return JSONObject.toBean(jsonObject, LinkedHashMap.class);
	}

	private String getJsonString() {
		JspFragment body = getJspBody();
		if (body == null) {
			throw new RuntimeException("Tag Body not found");
		}

		StringWriter writer = new StringWriter();
		try {
			body.invoke(writer);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return writer.toString().trim();
	}
}
