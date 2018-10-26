package com.hncis.common.util;

import java.util.Map;
import net.sf.jxls.transformer.XLSTransformer;


public class ExcelTemplat {
	/**
	 * 
	 * @param templateFilePath 템플릿 경로
	 * @param destFilePath   생성할파일경로
	 * @param map 생성할파일의 데이타
	 * @throws Exception
	 */
	public static void createXlsFile(String templateFilePath, String destFilePath, Map<String, Object> map) throws Exception{
               
        XLSTransformer transformer = new XLSTransformer();
        transformer.transformXLS(templateFilePath, map, destFilePath);
     }
}
