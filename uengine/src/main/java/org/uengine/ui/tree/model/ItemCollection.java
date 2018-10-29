package org.uengine.ui.tree.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class ItemCollection implements Serializable {

	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	private String label;
	private String identifier;
	private Collection<Item> items = new ArrayList<Item>();

	public ItemCollection() {

	}

	public ItemCollection(String label, String identifier) {
		this.label = label;
		this.identifier = identifier;
	}

	public ItemCollection(String label, String identifier, Collection<Item> items) {
		this.label = label;
		this.identifier = identifier;
		this.items = items;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Collection<Item> getItems() {
		return items;
	}

	public void setItems(Collection<Item> items) {
		this.items = items;
	}

}
