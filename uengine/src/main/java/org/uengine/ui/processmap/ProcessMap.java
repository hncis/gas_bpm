package org.uengine.ui.processmap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.uengine.processmanager.SimpleTransactionContext;
import org.uengine.ui.tree.dao.ProcessDefinitionListDAO;
import org.uengine.ui.tree.model.Item;
import org.uengine.util.dao.DefaultConnectionFactory;

public class ProcessMap {
	
	private int col;
		public int getCol() {
			return col;
		}
		public void setCol(int col) {
			this.col = col;
		}
	
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

	private StringBuffer sb = new StringBuffer("");
	
	private void makeChildrenElement(Collection<Item> processDefinitionList, int level) {
		String padding = "";
		for (int i=0; i<level; i++) {
			padding += "\t";
		}
		
		if (processDefinitionList != null) {
//			if (processDefinitionList.size() <= 3) {
//				this.col = 3;
//			} else if (processDefinitionList.size() == 4) {
//				this.col = 4;
//			} else if (processDefinitionList.size() > 4) {
//				this.col = 5;
//			}
			
			if (level == 0) {
				this.sb.append(padding + "\t" + "<ul id=\"primaryNav\" class=\"col" + this.col + "\">" + "\r\n");
			} else {
				this.sb.append(padding + "\t" + "<ul>" + "\r\n");
			}
			
			Object[] items = processDefinitionList.toArray();
			for (int i = 0; i < items.length; i++) {
				Item item = (Item) items[i];
				
				if (i == items.length - 1 && level > 1) {
					this.sb.append(padding + "\t" + "<li style=\"background: url('../images/Common/L3-bottom.gif') left top no-repeat;\">" + "\r\n");
				} else {
					this.sb.append(padding + "\t" + "<li>" + "\r\n");
				}
				
				if ("process".equals(item.getObj())) {
					this.sb.append(padding + "\t\t");
					this.sb.append("<a href=\"javascript:viewObjectType(").append(item.getId()).append(");\">" + "\r\n");
					this.sb.append("<img src=\"../images/Common/obj_icon_process.gif\" alt=\"\" width=\"18\" height=\"15\" style=\" vertical-align:middle; padding-right:5px;\" />");
					this.sb.append(item.getName() + "</a>" + "\r\n");
				} else {
					this.sb.append(padding + "\t\t" + "<a>" + item.getName() + "</a>" + "\r\n");
				}

				makeChildrenElement(item.getChildren(), level + 1);
				
				this.sb.append(padding + "\t" + "</li>" + "\r\n");
			}
			this.sb.append(padding + "\t" + "</ul>" + "\r\n");
		}
	}

	public String toHTML(String endpoint) throws Exception {
		SimpleTransactionContext st = new SimpleTransactionContext();
		
		//DataSource dataSource = DefaultConnectionFactory.create().getDataSource();
		ProcessDefinitionListDAO pdfpDAO = new ProcessDefinitionListDAO(st); 
		Collection<Item> processDefinitionList = pdfpDAO.findAllProcessDefinitionsForParticipant(endpoint);

		processDefinitionList = makeChildren(processDefinitionList, "-1", 0);
		if (processDefinitionList == null) {
			processDefinitionList = new ArrayList<Item>();
		}
		
		makeChildrenElement(processDefinitionList, 0);

		return this.sb.toString();
	}

}
