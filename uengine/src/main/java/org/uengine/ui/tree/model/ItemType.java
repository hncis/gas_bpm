package org.uengine.ui.tree.model;

public enum ItemType {

	FOLDER("folder", 1), FORM("form", 2), PROCESS("process", 3), RULE("rule", 4), CLASS("class", 5);

	private String value;
	private int order;

	private ItemType(String value, int order) {
		this.value = value;
		this.order = order;
	}

	public String getValue() {
		return value;
	}

	public int getOrder() {
		return order;
	}
}