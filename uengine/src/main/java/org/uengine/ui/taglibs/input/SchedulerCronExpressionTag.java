package org.uengine.ui.taglibs.input;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.uengine.kernel.GlobalContext;

public class SchedulerCronExpressionTag extends BodyTagSupport {

	private String name;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
	private String size;
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		
	@Override
	public int doStartTag() throws JspException {
		try {
			JspWriter out = pageContext.getOut();
			
			out.print("<input type=\"hidden\" name=\"" + getName() + "\" />");
			
			out.print("<input type=\"text\" id=\"schedulerCronExpression_" + getName() + "\" size=\"" + getSize() + "\" readonly=\"readonly\"  />");
			
			out.print("<img align=absmiddle src=\""
							+ GlobalContext.WEB_CONTEXT_ROOT + "/images/Icon/btn_Scheduler.gif\" "
							+ " onclick=\"makeSchedulerCronExpression('schedulerCronExpression_"
							+ getName() + "')\"");			

		} catch (Exception ex) {
			throw new JspTagException(ex.getMessage());
		}
		return SKIP_BODY;
	}

}
