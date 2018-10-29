package org.uengine.ui.taglibs;

import org.uengine.ui.list.datamodel.Constants;
import org.uengine.ui.list.datamodel.DataMap;
import org.uengine.ui.list.util.HttpUtils;
import org.uengine.ui.list.util.StringUtils;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


public class SortTag extends TagSupport {
    private String columnid   = null;							
    private String columnname = null;
    
    public void setColumnid(String columnid) {
		this.columnid = columnid;
    }

    public String getColumnid() {
		return columnid;																								
    }
    
    public void setColumnname(String columnname) {
		this.columnname = columnname;
    }

    public String getColumnname() {
        return StringUtils.replace(columnname," ","&nbsp;");																					
    }
    
    public int doStartTag() throws JspException {
		StringBuffer sbuf  = new StringBuffer();
		String sortcolumn  = null;				
		String sortcond    = null;				

		ServletRequest request = pageContext.getRequest(); 
//		DataSet dataset = HttpUtil.getDataSet((javax.servlet.http.HttpServletRequest)request);
		DataMap dm = HttpUtils.getDataMap((javax.servlet.http.HttpServletRequest)request, false);
		
		if(dm != null){
		    sortcolumn  = dm.getString(Constants.SORT_COLUMN, "");
			sortcond    = dm.getString(Constants.SORT_COND, "");
			
			sbuf.append("<a href=\"javascript:headerSort('"+getColumnid()+"','"+sortcolumn+"','"+sortcond+"');\" title=\"d��\">"+getColumnname());
//			sbuf.append("<font onClick=\"javascript:headerSort('"+getColumnid()+"','"+sortcolumn+"','"+sortcond+"');\" style=\"cursor:hand;\">"+getColumnname());
			if(getColumnid().equals(sortcolumn)){
				if(sortcond.equals("ASC")){
					sbuf.append("&nbsp;<img src=\""+Constants.IMG+"/i-arrow-up-gray.gif\" border=\"0\" align=\"absmiddle\">");
				}else{
					sbuf.append("&nbsp;<img src=\""+Constants.IMG+"/i-arrow-right-red.gif\" border=\"0\" align=\"absmiddle\">");
				}
			}else{
				sbuf.append("</a>");
			}
		}else{
			sbuf.append(getColumnname());
		}
	
		JspWriter writer = pageContext.getOut();
		try {
			writer.print(sbuf.toString());
		} catch (IOException e) {
			throw new JspException("[SortTag] Failed while writing... " + e.toString());
		}
			
		return SKIP_BODY;         
	}

    public int doEndTag() throws JspException {
        return EVAL_PAGE;           
    }
}
