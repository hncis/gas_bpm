package org.uengine.ui.taglibs.input;

import org.apache.log4j.Logger;
import org.uengine.ui.list.util.HttpUtils;
import org.uengine.util.UEngineUtil;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;
import java.io.*;
import java.util.Map;
import java.util.Map.Entry;

/**
*
* @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
* @version $Id: ForEach.java,v 1.25 2009/11/10 06:50:32 allbegray Exp $
*/
public class ForEach extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(ForEach.class);

	private boolean doWhile;
		public void setDoWhile(boolean doWhile) {
			this.doWhile = doWhile;
		}
		
	private int currentIndex =0;
	private Object[] currentVariableValue;

	private String variableName;
		public String getVariablename() {
			return variableName;
		}
		public void setVariablename(String variableName) {
			this.variableName = variableName;
		}
		
	private int viewMode;
		public int getViewMode() {
			return viewMode;
		}
		public void setViewMode(int i) {
			viewMode = i;
		}

	public int doStartTag() throws JspException {
		
		try {
			boolean isViewMode = UEngineUtil.isTrue(pageContext.getRequest().getParameter("isViewMode"));
			boolean attIsViewMode = UEngineUtil.isTrue(String.valueOf(pageContext.getRequest().getAttribute("isViewMode")));
			
			if (!attIsViewMode) {
				currentVariableValue = HttpUtils.getParameterAsObject(
						getVariablename(), 
						getVariablename(), 
						null,
						viewMode, 
						pageContext
				);
			} else {
				Map<String, String> mappedResult = (Map<String, String>) pageContext.getRequest().getAttribute("mappingResult");
				
				int count = -1;
				for (Entry<String, String> map : mappedResult.entrySet()) {
					String key = map.getKey();
					if (key.indexOf(getVariablename()) != -1 && key.indexOf("which_is_xml_value") != -1) {
						count++;
					}
				}

				if (count == -1) {
					currentVariableValue = HttpUtils.getParameterAsObject(
							getVariablename(), 
							getVariablename(), 
							null,
							viewMode, 
							pageContext
					);
				} else {
					currentVariableValue = new Object[count];	
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		currentIndex = 0;
		pageContext.setAttribute("_forEach", this);
		pageContext.setAttribute("_forEach_currentIndex", new Integer(0));
		
		return EVAL_BODY_TAG;
	}
	
	
	public int doAfterBody() {
		try {
			BodyContent body = getBodyContent();
			try {
				JspWriter out = body.getEnclosingWriter();
				
			    String existingRowHTML = body.getString();
			    String newHTML =  getHTML("name", existingRowHTML);
			    newHTML =  getHTML("id", newHTML);
				
			    out.println(newHTML);
			    
				body.clearBody();
				
			} catch(IOException ioe) {
				if (logger.isInfoEnabled()) {
					logger.info("doAfterBody() - Error in ForEachTag: " + ioe); //$NON-NLS-1$
				}
			}			

			currentIndex ++;
			
			int x = currentVariableValue.length;
			
			boolean isViewMode = UEngineUtil.isTrue(pageContext.getRequest().getParameter("isViewMode"));

			if (currentVariableValue.length > currentIndex) {
				return(EVAL_BODY_BUFFERED);
			} else {
				pageContext.setAttribute("_forEach", null);
				return(SKIP_BODY);
			}			
			
		} catch (Exception e) {
			logger.error("doAfterBody()", e); //$NON-NLS-1$
			return(SKIP_BODY);
		}
	}
	
	public int getCurrentIndex() {
		return currentIndex;
	}

	public String getHTML(String key, String source) {
		String strIndex = (currentIndex > 0) ? "[" + currentIndex + "]" : "";
		source = source.replaceAll(
				"(?i)(\\s" + key + ")=([\"|\']?)([a-zA-Z0-9_]*)(\\[?)([0-9]*\\]?)",
				"$1=$2$3" + strIndex
		);
		
		return source;
	}


}
