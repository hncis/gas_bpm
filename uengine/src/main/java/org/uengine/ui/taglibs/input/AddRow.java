package org.uengine.ui.taglibs.input;

import org.apache.log4j.Logger;
import org.uengine.kernel.GlobalContext;
import org.uengine.util.UEngineUtil;

import javax.servlet.jsp.*;
import javax.servlet.jsp.tagext.*;

/**
 * 
 * @author <a href="mailto:ghbpark@hanwha.co.kr">Sungsoo Park</a>
 * @version $Id: AddRow.java,v 1.10 2011/07/22 07:33:16 curonide Exp $
 */
public class AddRow extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AddRow.class);

	private int viewmode;

	private String name;
	private String value;
	private String removename;
	private String removevalue;

	public int getViewmode() {
		return viewmode;
	}

	public void setViewmode(int viewmode) {
		this.viewmode = viewmode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemovename() {
		return removename;
	}

	public void setRemovename(String removename) {
		this.removename = removename;
	}

	public String getRemovevalue() {
		return removevalue;
	}

	public void setRemovevalue(String removevalue) {
		this.removevalue = removevalue;
	}

	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();

			boolean attIsViewMode = UEngineUtil.isTrue(String.valueOf(pageContext.getRequest().getAttribute("isViewMode")));
			boolean isViewMode = UEngineUtil.isTrue(pageContext.getRequest().getParameter("isViewMode"));

			if (!attIsViewMode && !isViewMode) {
				String strHTML = "";

				if (viewmode != InputConstants.VIEW
						&& viewmode != InputConstants.PRINT) {

					String addBtnName = UEngineUtil.isNotEmpty(getName()) ? getName() : "nameBtnAddRow";
					String addBtnValue = UEngineUtil.isNotEmpty(getValue()) ? getValue() : "Add";
					String removeBtnName = UEngineUtil.isNotEmpty(getRemovename()) ? getRemovename() : "nameBtnRemoveRow";
					String removeBtnValue = UEngineUtil.isNotEmpty(getRemovevalue()) ? getRemovevalue() : "Remove";
					
					strHTML = "<input type=\"button\" onclick=\"addRow(this)\" value=\"" + addBtnValue + "\" style=\"width: 60px\" name=\"" + addBtnName + "\" />"
							+ "<input type=\"button\" onclick=\"removeRow(this)\" value=\"" + removeBtnValue + "\" style=\"width: 60px\" name=\"" + removeBtnName + "\" />";
				}

				out.print(strHTML);
			}

		} catch (Exception ex) {
			throw new JspTagException(ex.getMessage());
		}

		return SKIP_BODY;
	}

}
