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

public class ProcessDefinitionForParticipantListToJSON {
	
	private Collection<Item> makeChildren(Collection<Item> processDefinitionList, String parent, int level) {
		Collection<Item> childProcessDefinitionList = new ArrayList<Item>();
		
		for (Item item : processDefinitionList) {
			if (item != null && item.getParent().equals(parent)) {
				childProcessDefinitionList.add(item);
			}	
		}
		
		Collections.sort((List<Item>) childProcessDefinitionList);
		
		Collection<Item> returnChildProcessDefinitionList = null;
		if (childProcessDefinitionList.size() > 0) {
			returnChildProcessDefinitionList = new ArrayList<Item>();
		} else {
			return null;
		}
		
		for (Item item : childProcessDefinitionList) {
			if ("process".equals(item.getObj())) {
				returnChildProcessDefinitionList.add(item);
			} else {
				item.setChildren(makeChildren(processDefinitionList, item.getId(), level + 1));
				if (item.getChildren() != null) {
					if (item.getChildren().size() != 0)
						returnChildProcessDefinitionList.add(item);
				}
			}
		}
		
		return returnChildProcessDefinitionList;
	}

	public String toJSON(Collection<Item> processDefinitionList) throws Exception {
		
		Collection<Item> processDefinitionTreeList = makeChildren(processDefinitionList, "-1", 0);
		if (processDefinitionTreeList == null) {
			processDefinitionTreeList = new ArrayList<Item>();
		}
		ItemCollection itemCollection = new ItemCollection("name", "id", processDefinitionTreeList);

        XStream xstream = new XStream(new JsonHierarchicalStreamDriver() {
            public HierarchicalStreamWriter createWriter(Writer writer) {
                return new JsonWriter(writer, JsonWriter.DROP_ROOT_MODE);
            }
        });

		return xstream.toXML(itemCollection).replace("   ", "");
	}
}

