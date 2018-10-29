package org.uengine.formmanager.html;

import java.util.*;

public class FormFieldStringBuffer {

	/** White Space */
	public static final String SPACE = " ";
	
	/** FormField�� ��Ʈ���� ����ִ� Buffer */
	private StringBuffer sb;

	private List exceptedKey;

	public FormFieldStringBuffer() {
		sb = new StringBuffer();
		exceptedKey = Collections.synchronizedList(new ArrayList());
	}
	
	public void addExceptKey(String key) {
		exceptedKey.add(key);
	}
	
	public void append(String value) {
		if ( ! value.equals("/>") ) {
			append(value, true);
		} else {
			append(value, false);
		}
	}

	public void append(String value, boolean appendSpace) {
		sb.append(value);
		if ( appendSpace ) {
			appendSpace();
		}
	}
	
	public void appendSpace() {
		sb.append(SPACE);
	}

	public void append(String key, String value) {
		append(key, value, true);
	}

	public void append(String key, String value, boolean appendSpace) {
		if ( ! exceptedKey.contains(key) ) {
			sb.append(key + "=\"" + value + "\"");
			if ( appendSpace ) {
				appendSpace();
			}
		}
	}
	
	public String toString() {
		return sb.toString();
	}

}
