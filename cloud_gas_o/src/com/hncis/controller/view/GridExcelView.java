package com.hncis.controller.view;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

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

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.hncis.common.util.StringUtil;

public class GridExcelView extends AbstractJExcelView {
	@SuppressWarnings({"rawtypes"})
	@Override
	protected void buildExcelDocument(Map model, WritableWorkbook workbook,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		buildExcel(model, workbook ,response);
	}
	
	@SuppressWarnings("rawtypes")
	private void buildExcel(Map model, WritableWorkbook workbook, HttpServletResponse response) throws WriteException{
		Map       datas      = (Map)model.get("excelData");
		String    fileName   = (String)datas.get("fileName");
		String    jobName   = (String)datas.get("jobName");
		String[]  headers    = (String[])datas.get("header");
		String[]  headerName = (String[])datas.get("headerName");
		String[]  fomatter   = (String[])datas.get("fomatter");
		JSONArray jsonArray  = (JSONArray)datas.get("gridData");
		
		response.setHeader("Content-Disposition", "attachment; filename="+fileName+".xls");
		WritableSheet sheet = workbook.createSheet("data", 1);
		
		/**
		 * setting header
		 */
		setHeader(sheet, headers, "Y", jobName);
		
		/**
		 * setting gridData
		 */
		if(StringUtil.isNullToString(jobName).equals("BT")){
			setDataForBt(sheet, jsonArray, fomatter, headerName, 2 , "", "Y");
		}else if(StringUtil.isNullToString(jobName).indexOf("TS") > -1){
			setDataForTs(sheet, jsonArray, fomatter, headerName, 2 , "", "Y",jobName);
		}else{
			setData(sheet, jsonArray, fomatter, headerName, 2 , "", "Y");
		}
		
		
	}
	
	private void setHeader(WritableSheet sheet, String[] headers, String firstUseYn, String jobName) throws WriteException {
		WritableCellFormat titleFormat = new WritableCellFormat(
			new WritableFont (WritableFont.ARIAL,                   //font type
					10,                                             //font size
					WritableFont.BOLD,                              //bold style
					false,                                          //italic
					UnderlineStyle.NO_UNDERLINE,                    //underline
					Colour.GRAY_80,                                 //font color
					ScriptStyle.NORMAL_SCRIPT));                    //script style
		titleFormat.setAlignment(Alignment.CENTRE);                 //cell align
		titleFormat.setVerticalAlignment(VerticalAlignment.CENTRE); //cell valign
		titleFormat.setBorder(Border.ALL, BorderLineStyle.THIN);    //border and borderline style
		titleFormat.setBackground(Colour.ICE_BLUE);                 //background color
		titleFormat.setWrap(true);
		
		//header
		int colIndex = 1;
		int rowIndex = 1;
		if(firstUseYn.equals("Y")){
			colIndex = 0;   
			rowIndex = rowIndex - 1;
		}
		
		if(StringUtil.isNullToString(jobName).equals("TS")){
			
			SheetSettings setting = sheet.getSettings();
			setting.setHorizontalFreeze(4);
			
			for(int i = 0; i < headers.length; i++){
				//colIndex, cell width
				sheet.setColumnView(colIndex, 15);

				if(i > 3 && i < 8){
					//titleFormat_ts.setBackground(Colour.BRIGHT_GREEN);
					//Dept ~ Company
					WritableCellFormat titleFormat_ts = new WritableCellFormat(
							new WritableFont (WritableFont.ARIAL,                   //font type
									10,                                             //font size
									WritableFont.BOLD,                              //bold style
									false,                                          //italic
									UnderlineStyle.NO_UNDERLINE,                    //underline
									Colour.GRAY_80,                                 //font color
									ScriptStyle.NORMAL_SCRIPT));                    //script style
						titleFormat_ts.setAlignment(Alignment.CENTRE);                 //cell align
						titleFormat_ts.setVerticalAlignment(VerticalAlignment.CENTRE); //cell valign
						titleFormat_ts.setBorder(Border.ALL, BorderLineStyle.THIN);    //border and borderline style
						titleFormat_ts.setBackground(Colour.PALE_BLUE);                 //background color
						titleFormat_ts.setWrap(true);
						
						
					sheet.addCell(new Label(colIndex++, rowIndex, headers[i].replaceAll("<br>", ""), titleFormat_ts));
				}else if(i > 7 && i < 9){
					//First
					// Colour.LIGHT_ORANGE 오렌지
					//titleFormat_ts.setBackground(Colour.BRIGHT_GREEN);
					
					WritableCellFormat titleFormat_ts = new WritableCellFormat(
							new WritableFont (WritableFont.ARIAL,                   //font type
									10,                                             //font size
									WritableFont.BOLD,                              //bold style
									false,                                          //italic
									UnderlineStyle.NO_UNDERLINE,                    //underline
									Colour.GRAY_80,                                 //font color
									ScriptStyle.NORMAL_SCRIPT));                    //script style
						titleFormat_ts.setAlignment(Alignment.CENTRE);                 //cell align
						titleFormat_ts.setVerticalAlignment(VerticalAlignment.CENTRE); //cell valign
						titleFormat_ts.setBorder(Border.ALL, BorderLineStyle.THIN);    //border and borderline style
						titleFormat_ts.setBackground(Colour.LIGHT_ORANGE);                 //background color
						titleFormat_ts.setWrap(true);
						
					sheet.addCell(new Label(colIndex++, rowIndex, headers[i].replaceAll("<br>", ""), titleFormat_ts));
					
				}else if(i > 8 && i < 10){
					// Last
					//titleFormat_ts.setBackground(Colour.BRIGHT_GREEN);
					
					WritableCellFormat titleFormat_ts = new WritableCellFormat(
							new WritableFont (WritableFont.ARIAL,                   //font type
									10,                                             //font size
									WritableFont.BOLD,                              //bold style
									false,                                          //italic
									UnderlineStyle.NO_UNDERLINE,                    //underline
									Colour.GRAY_80,                                 //font color
									ScriptStyle.NORMAL_SCRIPT));                    //script style
						titleFormat_ts.setAlignment(Alignment.CENTRE);                 //cell align
						titleFormat_ts.setVerticalAlignment(VerticalAlignment.CENTRE); //cell valign
						titleFormat_ts.setBorder(Border.ALL, BorderLineStyle.THIN);    //border and borderline style
						titleFormat_ts.setBackground(Colour.BRIGHT_GREEN);             //background color
						titleFormat_ts.setWrap(true);
						
						//Colour.BRIGHT_GREEN
						
					sheet.addCell(new Label(colIndex++, rowIndex, headers[i].replaceAll("<br>", ""), titleFormat_ts));
				}else if(i > 16 && i < 26){
					// pickup - individaul payment ~ remark
					//titleFormat_ts.setBackground(Colour.BRIGHT_GREEN);
					
					WritableCellFormat titleFormat_ts = new WritableCellFormat(
							new WritableFont (WritableFont.ARIAL,                   //font type
									10,                                             //font size
									WritableFont.BOLD,                              //bold style
									false,                                          //italic
									UnderlineStyle.NO_UNDERLINE,                    //underline
									Colour.GRAY_80,                                 //font color
									ScriptStyle.NORMAL_SCRIPT));                    //script style
						titleFormat_ts.setAlignment(Alignment.CENTRE);                 //cell align
						titleFormat_ts.setVerticalAlignment(VerticalAlignment.CENTRE); //cell valign
						titleFormat_ts.setBorder(Border.ALL, BorderLineStyle.THIN);    //border and borderline style
						titleFormat_ts.setBackground(Colour.LIGHT_ORANGE);                 //background color
						titleFormat_ts.setWrap(true);
						
						//Colour.BRIGHT_GREEN
						
					sheet.addCell(new Label(colIndex++, rowIndex, headers[i].replaceAll("<br>", ""), titleFormat_ts));
				}else if(i > 25 && i < 37){
					//accomodation - individaul payment ~ remark
					//titleFormat_ts.setBackground(Colour.BRIGHT_GREEN);
					
					WritableCellFormat titleFormat_ts = new WritableCellFormat(
							new WritableFont (WritableFont.ARIAL,                   //font type
									10,                                             //font size
									WritableFont.BOLD,                              //bold style
									false,                                          //italic
									UnderlineStyle.NO_UNDERLINE,                    //underline
									Colour.GRAY_80,                                 //font color
									ScriptStyle.NORMAL_SCRIPT));                    //script style
						titleFormat_ts.setAlignment(Alignment.CENTRE);                 //cell align
						titleFormat_ts.setVerticalAlignment(VerticalAlignment.CENTRE); //cell valign
						titleFormat_ts.setBorder(Border.ALL, BorderLineStyle.THIN);    //border and borderline style
						titleFormat_ts.setBackground(Colour.BRIGHT_GREEN);                 //background color
						titleFormat_ts.setWrap(true);
						
						//Colour.BRIGHT_GREEN
						
					sheet.addCell(new Label(colIndex++, rowIndex, headers[i].replaceAll("<br>", ""), titleFormat_ts));
				}else{
					//titleFormat_ts.setBackground(Colour.ICE_BLUE);
					sheet.addCell(new Label(colIndex++, rowIndex, headers[i].replaceAll("<br>", ""), titleFormat));
				}
			}
			
		}else{
			for(int i = 0; i < headers.length; i++){
				//colIndex, cell width
				sheet.setColumnView(colIndex, 15);
				sheet.addCell(new Label(colIndex++, rowIndex, headers[i].replaceAll("<br>", ""), titleFormat));
			}
		}
	}
	
	private void setData(WritableSheet sheet, JSONArray jsonArray, String[] fomatter, String[] headerName,
			int startRowIndex, String formatData, String firstUseYn ) throws WriteException {
		WritableCellFormat cellFormat = new WritableCellFormat();
		cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
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
			int colIndex = 1;
			if(firstUseYn.equals("Y")){
				colIndex = 0;
				if(i==0) startRowIndex = startRowIndex-1;
			}
			
			for(String key : headerName){
				String type = fomatter[colIndex];
				setDataByAddCell(sheet, obj, key, type, colIndex++, startRowIndex+i, format, cellFormat, cellNumberFormat, cellNumberNoFormat);
			}
		}
	}
	private void setDataForBt(WritableSheet sheet, JSONArray jsonArray, String[] fomatter, String[] headerName,
			int startRowIndex, String formatData, String firstUseYn ) throws WriteException {
		
		int arraySize = jsonArray.size();
		String format = formatData;
		for(int i = 0; i < arraySize; i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			int colIndex = 1;
			if(firstUseYn.equals("Y")){
				colIndex = 0;
				if(i==0) startRowIndex = startRowIndex-1;
			}
			
			for(String key : headerName){
				
				WritableCellFormat cellFormat = new WritableCellFormat();
				cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				cellFormat.setShrinkToFit(false);
				NumberFormat numberFormat = new NumberFormat("###,##0");
				NumberFormat numberNoFormat = new NumberFormat("#0");
				WritableCellFormat cellNumberFormat = new WritableCellFormat(numberFormat);
				WritableCellFormat cellNumberNoFormat = new WritableCellFormat(numberNoFormat);
				cellNumberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				cellNumberNoFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				
				String type = fomatter[colIndex];
				/*
				if(!StringUtil.isNullToString(obj.getString("mepp_eur")).equals("")){
					cellFormat.setBackground(Colour.GRAY_25);
				}
				*/
				
				setDataByAddCell(sheet, obj, key, type, colIndex++, startRowIndex+i, format, cellFormat, cellNumberFormat, cellNumberNoFormat);
			}
		}
	}
	private void setDataForTs(WritableSheet sheet, JSONArray jsonArray, String[] fomatter, String[] headerName,
			int startRowIndex, String formatData, String firstUseYn, String jobName) throws WriteException {
		//setDataForTs(sheet, jsonArray, fomatter, headerName, 2 , "", "Y",jobName)
		int arraySize = jsonArray.size();
		String format = formatData;
		for(int i = 0; i < arraySize; i++) {
			JSONObject obj = jsonArray.getJSONObject(i);
			int colIndex = 1;
			if(firstUseYn.equals("Y")){
				colIndex = 0;
				if(i==0) startRowIndex = startRowIndex-1;
			}
			
			for(String key : headerName){
				
				WritableCellFormat cellFormat = new WritableCellFormat();
				cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				cellFormat.setShrinkToFit(false);
				NumberFormat numberFormat = new NumberFormat("###,##0");
				NumberFormat numberNoFormat = new NumberFormat("#0");
				WritableCellFormat cellNumberFormat = new WritableCellFormat(numberFormat);
				WritableCellFormat cellNumberNoFormat = new WritableCellFormat(numberNoFormat);
				cellNumberFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				cellNumberNoFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
				
				String type = fomatter[colIndex];
				
				//Key : req_no , data : obj
				
				if("pc_individual_payment".equals(key.trim()) || "htl_individual_payment".equals(key.trim())){
					
					String sValue = obj.getString(key.trim());
					
					if("0".equals(sValue)){
						obj.put(key.trim(), "N");
					}else{
						obj.put(key.trim(), "Y");
					}
				}	
				
				
				if(jobName.equals("TS_P")){
					if(StringUtil.isNullToString(obj.getString("pc_pgs_st_cd")).equals("3")){
						cellFormat.setBackground(Colour.GRAY_25);
					}
				}else if(jobName.equals("TS_I")){
					if(StringUtil.isNullToString(obj.getString("ic_pgs_st_cd")).equals("3")){
						cellFormat.setBackground(Colour.GRAY_25);
					}
				}else if(jobName.equals("TS_M")){
					if(StringUtil.isNullToString(obj.getString("hp_pgs_st_cd")).equals("3")){
						cellFormat.setBackground(Colour.GRAY_25);
					}
				}else if(jobName.equals("TS_C")){
					if(StringUtil.isNullToString(obj.getString("car_pgs_st_cd")).equals("3")){
						cellFormat.setBackground(Colour.GRAY_25);
					}
				}
				
				
				setDataByAddCell(sheet, obj, key, type, colIndex++, startRowIndex+i, format, cellFormat, cellNumberFormat, cellNumberNoFormat);
			}
		}
	}
	private void setDataByAddCell(WritableSheet sheet, JSONObject obj, String dataKey, String type, int colIndex, int rowIndex, String format,
			WritableCellFormat cellFormat, WritableCellFormat cellNumberFormat, WritableCellFormat cellNumberNoFormat)
			throws RowsExceededException, WriteException
	{
		if(type.trim().equals("string")){
			sheet.addCell(new Label(colIndex, rowIndex, obj.getString(dataKey.trim()) , cellFormat));
		}else if(type.trim().equals("integer")){
			long amount = 0l;
			try{ amount = obj.getLong(dataKey.trim()); }catch(Exception ex){ amount = 0l;}
			
			if(format.equals("noformat")){
				sheet.addCell(new Number(colIndex, rowIndex, amount, cellNumberNoFormat));
			}else{
				sheet.addCell(new Number(colIndex, rowIndex, amount, cellNumberFormat));
			}
		}else{
			sheet.addCell(new Label(colIndex, rowIndex, obj.getString(dataKey.trim()) , cellFormat));
		}
	}
}