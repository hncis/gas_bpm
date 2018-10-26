package com.hncis.controller.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.SheetSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.hncis.common.util.StringUtil;

public class GridSapExcelView extends AbstractJExcelView {
	@SuppressWarnings({"rawtypes"})
	@Override
	protected void buildExcelDocument(Map model, WritableWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		buildExcel(model, workbook ,response);
	}
	
	@SuppressWarnings("rawtypes")
	private void buildExcel(Map model, WritableWorkbook workbook, HttpServletResponse response) throws WriteException{
		Map       datas      	= (Map)model.get("excelData");
		String    fileName   	= (String)datas.get("fileName");
		String    headerLength  = (String)datas.get("headerLength");
		JSONArray jsonArray  = (JSONArray)datas.get("gridData");
		
		//String[]  headers    = (String[])datas.get("header");
		//String[]  headerName = (String[])datas.get("headerName");
		//String[]  fomatter   = (String[])datas.get("fomatter");
		
		
		System.out.println("fileName="+fileName);
		System.out.println("headerLength="+headerLength);
		System.out.println("jsonArray="+jsonArray.size());
		
		response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
		WritableSheet sheet = workbook.createSheet("data", 1);
		
		String[] headers = {"AAA", "BBB"};
		
		//setHeader(sheet, headers);
		setData(sheet, jsonArray, headerLength, 1, "String");
	}
	
	private void setData(WritableSheet sheet, JSONArray jsonArray, String headerLength,
			int startRowIndex, String formatData) throws WriteException {
		WritableCellFormat cellFormat = new WritableCellFormat();
		//cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellFormat.setShrinkToFit(false);
		NumberFormat numberFormat = new NumberFormat("###,##0");
		NumberFormat numberNoFormat = new NumberFormat("#0");
		WritableCellFormat cellNumberFormat = new WritableCellFormat(numberFormat);
		WritableCellFormat cellNumberNoFormat = new WritableCellFormat(numberNoFormat);
		cellNumberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		cellNumberNoFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
		
		int arraySize = jsonArray.size();
		String format = formatData;
		for(int i = 0; i < arraySize; i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			int colIndex = 0;
			
			for(int j = 0; j < Integer.parseInt(headerLength); j++) {
				setDataByAddCell(sheet, obj, "header"+StringUtil.lpad(Integer.toString(j+1), 2, "0"), colIndex, startRowIndex+i, format, cellFormat, cellNumberFormat, cellNumberNoFormat);
				colIndex++;
			}
		}
	}
	
	private void setDataByAddCell(WritableSheet sheet, JSONObject obj, String dataKey, int colIndex, int rowIndex, String format,
			WritableCellFormat cellFormat, WritableCellFormat cellNumberFormat, WritableCellFormat cellNumberNoFormat)
			throws RowsExceededException, WriteException
	{
		sheet.addCell(new Label(colIndex, rowIndex, obj.getString(dataKey.trim()) , cellFormat));
	}
}