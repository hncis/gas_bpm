package org.uengine.ui.list.util;

import java.net.URLEncoder;

import org.uengine.contexts.FileContext;
import org.uengine.kernel.GlobalContext;
import org.uengine.ui.taglibs.input.InputConstants;

public class FileUploadUtil {
	public static String getFileUploadTag(String tagName, FileContext fc, String fileClass, int viewMode) throws Exception {
    	StringBuffer sHtml = new StringBuffer();
    	String type = "file";
    	String value = "";
    	
    	if (fc != null) {
    		String fileName = fc.getFile().getName();
    		fileName = fileName.substring(fileName.indexOf("_")+1, fileName.length());
    		String fileContextPath = URLEncoder.encode(fc.getPath(), GlobalContext.ENCODING);
    		
    		if ("image".equalsIgnoreCase(fileClass)) {
				sHtml.append("<img src=\"")
				.append(GlobalContext.WEB_CONTEXT_ROOT)
				.append("/common/fileDownloader.jsp?filePath=")
				.append(fileContextPath)
				.append("\" /><br>");
			} else {
				sHtml.append("<a href=\"")
				.append(GlobalContext.WEB_CONTEXT_ROOT)
				.append("/common/fileDownloader.jsp?filePath=")
				.append(fileContextPath)
				.append("\" target=\"_blank\" style=\"vertical-align:middle\">")
				.append("<img src=\"")
				.append(GlobalContext.WEB_CONTEXT_ROOT)
				.append("/images/Icon/btn_file.gif\"  align=middle >")
				.append(fileName)
				.append("</a>");
			}
    		value = GlobalContext.serialize(fc, FileContext.class);
    		value = value.replaceAll("<", "&lt;");
			value = value.replaceAll(">", "&gt;");
    	}
    	
    	if (fc != null || viewMode != InputConstants.WRITE) {
    		type = "hidden";
    	}
    	
    	sHtml.append("<img id=\"_loading_file_")
    	.append(tagName)
    	.append("\" style=\"display: none\" src=\"")
    	.append(GlobalContext.WEB_CONTEXT_ROOT)
    	.append("/images/Common/i_dash_load.gif\" />")
    	.append("<input type=\"")
		.append(type)
		.append("\" onchange=\"uploadFile(this);\" name=\"")
    	.append(tagName)
    	.append("\" id=\"")
    	.append(tagName)
    	.append("\" value=\"")
    	.append(value)
    	.append("\" viewMode=\"")
    	.append(viewMode)
    	.append("\" fileClass=\"")
    	.append(fileClass)
    	.append("\" style=\"width=300px\" />");
    	
    	if (viewMode != InputConstants.VIEW && viewMode != InputConstants.PRINT) {
			if (fc != null) {
				sHtml.append("<a href=\"javascript:;;\">")
				.append("<img src=\"")
				.append(GlobalContext.WEB_CONTEXT_ROOT)
				.append("/images/Icon/12-em-cross.png\" alt=\"delete\" onclick=\"deleteFile(this)\" align=middle />")
				.append("</a>");
			}
		}
    	sHtml.append("<input type=\"hidden\" name=\"")
    	.append(tagName)
    	.append("_value_handler_class\"")
    	.append(" value=\"")
    	.append(org.uengine.ui.taglibs.input.FileUpload.class.getName())
    	.append("\" />");
    	
    	return sHtml.toString();
    }
}
