package org.uengine.ui.taglibs;

import org.apache.log4j.Logger;

import org.uengine.kernel.GlobalContext;
import org.uengine.ui.list.datamodel.Constants;
import org.uengine.ui.list.util.ListPageInfo;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * Paging Tag Library
 * 
 */
public class PagingTag extends BodyTagSupport {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(PagingTag.class);

	private int totalCount;
	private int pageCount;
	private int pageLimit;
	private int currentPage;
	private String link;
	private String locale;

	/**
	 * 전체 리스트 개수 
	 * @param i
	 */
	public void setTotalcount(int i) {
		totalCount = i;
	}	
	
	/**
	 * 화면에 보이는 리스트 개수 
	 * @param i
	 */
	public void setPagecount(int i) {
		pageCount = i;
	}		
	
	/**
	 * 표현할 페이지의 수 
	 * EX) 5  --> [1]....[5]
	 * EX) 10  --> [1]....[10]
	 * @param i
	 */
	public void setPagelimit(int i) {
		pageLimit = i;
	}
	
	/**
	 * 현재 페이지 
	 * @param i
	 */
	public void setCurrentpage(int i) {
		currentPage = i;
	}
	
	public void setLink(String s) {
		link = s;
	}
	

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
	

	public int doStartTag()	{
		return EVAL_BODY_INCLUDE;
	}
	
	public int doEndTag()	{
		StringBuffer sb = new StringBuffer();
		
		try {
			ListPageInfo listPageInfo
				= new ListPageInfo(totalCount, pageCount, pageLimit, currentPage);

			int _selectNum = listPageInfo.getAbsolutePage();
			int _pageCount = listPageInfo.getPageCount();

			//바로전 페이지
			int b =
				(_selectNum == listPageInfo.getFirstPage())
					? _selectNum
					: _selectNum - 1;
			//바로 다음 페이지
			int n =
				(_selectNum == listPageInfo.getLastPage())
					? _selectNum
					: _selectNum + 1;
			int _nPageSize = listPageInfo.getPageSetSize();	
			
			sb.append("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
				sb.append("<tr>");
				
				sb.append("<td nowrap class=\"pagenavi_txtcell\">");
				if ( _selectNum != listPageInfo.getFirstPage() ) {
					sb.append("<a href=\"JavaScript:"+link+"goPage('"+listPageInfo.getFirstPage()+"')\"  class=\"pagenavi_txtlnk\">");
					sb.append(GlobalContext.getLocalizedMessageForWeb("start", this.locale, "start"));
					sb.append("</a>");
				} else {
					sb.append(GlobalContext.getLocalizedMessageForWeb("start", this.locale, "start"));
				}
				sb.append("</td>");
				
				sb.append("<td class=\"pagenavi_imgpad\">");
				if (listPageInfo.isPrevPageSet()) {
					sb.append("<a href=\"JavaScript:"+link+"goPage('"+listPageInfo.getPrevPageSet()+"')\" title=\""+GlobalContext.getLocalizedMessageForWeb("perv", this.locale, "perv")+ " "+_nPageSize+" "+GlobalContext.getLocalizedMessageForWeb("list", this.locale, "list")+"\">");
					sb.append("<img src=\""+Constants.IMG+"/navi-first-on.gif\" border=\"0\" align=\"absmiddle\" hspace=\"1\" alt=\""+GlobalContext.getLocalizedMessageForWeb("perv", this.locale, "perv")+" " +_nPageSize+ " " + GlobalContext.getLocalizedMessageForWeb("list", this.locale, "list")+"\">");
					sb.append("</a>");
				} else {
					sb.append("<img src=\""+Constants.IMG+"/navi-first-off.gif\" border=\"0\" align=\"absmiddle\" hspace=\"1\">");
				}
				if ( _selectNum != b ) {
					sb.append("<a href=\"JavaScript:"+link+"goPage('"+b+ "')\" id=graytitle=\""+GlobalContext.getLocalizedMessageForWeb("perv_page", this.locale, "perv page")+"\">");
					sb.append("<img src=\""+Constants.IMG+"/navi-pre-on.gif\" border=\"0\" align=\"absmiddle\" hspace=\"1\"  alt=\""+GlobalContext.getLocalizedMessageForWeb("perv_page", this.locale, "perv page")+"\">");
					sb.append("</a>");
				} else {
					sb.append("<img src=\""+Constants.IMG+"/navi-pre-off.gif\" border=\"0\" align=\"absmiddle\" hspace=\"1\">");
				}
				sb.append("</td>");
			
				// 1~ 5(10) 링크
				String sTemp = null;
				for (int i = listPageInfo.getStartPage(); i <= listPageInfo.getEndPage(); i++) {
					sTemp = Integer.toString(i);
					if ( i != listPageInfo.getStartPage() ) sb.append("<td nowrap class=\"pagenavi_vline\"></td>");					
					if (_selectNum == i) {
						// 링크 없을 경우
						sb.append("<td nowrap class=\"pagenavi_cur_cell\">");
						sb.append(" " + sTemp + " ");
						sb.append("</td>");
					} else {
						// 링크 있을 경우
						sb.append("<td nowrap class=\"pagenavi_dft_cell\" onmouseover=\"this.className='pagenavi_ovr_cell'\" onmouseout=\"this.className='pagenavi_dft_cell'\" onclick=\"JavaScript:" + link + "goPage('" + sTemp + "')\">");
						sb.append(" <a href=\"JavaScript:" + link + "goPage('" + sTemp + "')\"  class=\"pagenavi_lnk\" title=\""+sTemp+" 페이지로이동\">" + sTemp + "</a> ");
						sb.append("</td>");
					}
	//				if ( i != listPageInfo.getEndPage() ) {
	//					;
	//				}
				}
				
				sb.append("<td class=\"pagenavi_imgpad\">");
				if ( _selectNum != n ) {
					sb.append("<a href=\"JavaScript:"+link+"goPage('"+n+"')\" title=\""+GlobalContext.getLocalizedMessageForWeb("next_page", this.locale, "next page")+"\">");
					sb.append("<img src=\""+Constants.IMG+"/navi-next-on.gif\" border=\"0\" align=\"absmiddle\" hspace=\"1\">");
					sb.append("</a>");
				} else {
					sb.append("<img src=\""+Constants.IMG+"/navi-next-off.gif\" border=\"0\" align=\"absmiddle\" hspace=\"1\">");
				}
				
				if (listPageInfo.isNextPageSet()) {
					sb.append("<a href=\"JavaScript:"+link+"goPage('"+listPageInfo.getNextPageSet()+"')\">");
					sb.append("<img src=\""+Constants.IMG+"/navi-end-on.gif\" border=\"0\" align=\"absmiddle\" hspace=\"1\">");
					sb.append("</a>");
				} else {
					sb.append("<img src=\""+Constants.IMG+"/navi-end-off.gif\" border=\"0\" align=\"absmiddle\" hspace=\"1\">");
				}
			
			sb.append("</td>");
			
			sb.append("<td nowrap class=\"pagenavi_txtcell\">");
			if ( _selectNum != listPageInfo.getLastPage() ) {
				sb.append("<a href=\"JavaScript:"+link+"goPage('"+listPageInfo.getLastPage()+"')\" class=\"pagenavi_txtlnk\">");
				sb.append(GlobalContext.getLocalizedMessageForWeb("end", this.locale, "end"));
				sb.append("</a>");
			} else {
				sb.append(GlobalContext.getLocalizedMessageForWeb("end", this.locale, "end"));
			}
			sb.append("</td>");
			
			sb.append("</tr>");
			sb.append("</table>");			
			
		} catch (Exception e) {}
		
		
		
		

		JspWriter out = pageContext.getOut();
	    try {
		 	out.print(sb.toString());
	   	} catch (IOException e) {
			if (logger.isInfoEnabled()) {
				logger.info("doEndTag() - " + e.toString()); //$NON-NLS-1$
			}
	   	}
	   	
	   	return EVAL_PAGE;
	}

}

