package org.uengine.ui.tree.json;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.uengine.ui.tree.model.Item;
import org.uengine.ui.tree.model.ItemCollection;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;

public class FormListToJSON {

	private Collection<Item> makeChildren(Collection<Item> formDefinitionList, String parent, int level) {
		Collection<Item> childFormDefinitionList = new ArrayList<Item>();
		
		for (Item item : formDefinitionList) {
			if (item != null && item.getParent().equals(parent)) {
				childFormDefinitionList.add(item);
			}
		}
		
		Collections.sort((List<Item>) childFormDefinitionList);
		
		Collection<Item> returnChildFormDefinitionList = null;
		if (childFormDefinitionList.size() > 0) {
			returnChildFormDefinitionList = new ArrayList<Item>();
		} else {
			return null;
		}
		
		for (Item item : childFormDefinitionList) {
			if ("form".equals(item.getObj())) {
				returnChildFormDefinitionList.add(item);
			} else {
				item.setChildren(makeChildren(formDefinitionList, item.getId(), level + 1));
				if (item.getChildren() != null) {
					if (item.getChildren().size() != 0)
						returnChildFormDefinitionList.add(item);
				}
			}
		}
		
		return returnChildFormDefinitionList;
	}

	public String toJSON(Collection<Item> formDefinitionList) throws Exception {
		
		Collection<Item> formTreeList = makeChildren(formDefinitionList, "-1", 0);
		if (formTreeList == null) {
			formTreeList = new ArrayList<Item>();
		}
		ItemCollection itemCollection = new ItemCollection("name", "id", formTreeList);

        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });

		return xstream.toXML(itemCollection).replace("   ", "");
	}
}

