package org.uengine.ui.tree.model;

import java.util.Collection;

public class FormList {
	private int defVerId;
	private Collection<Form> forms;

	public int getDefVerId() {
		return defVerId;
	}

	public void setDefVerId(int defVerId) {
		this.defVerId = defVerId;
	}

	public Collection<Form> getForms() {
		return forms;
	}

	public void setForms(Collection<Form> forms) {
		this.forms = forms;
	}

}
