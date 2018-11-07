package com.hncis.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReadExcelPoi {
    private transient Log logger = LogFactory.getLog(getClass());

	private String inputFile;

	public void setInputFile(String inputFile) {
		this.inputFile = inputFile;
	}

	@SuppressWarnings("finally")
	public List<Map<String, String>> getExcelXlsData(String[] excelKeyArr)throws Exception{
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		Map<String, String>       result     = new HashMap<String, String>();

		try{
			//파일을 읽기위해 엑셀파일을 가져온다
			FileInputStream fis   = new FileInputStream(inputFile);
			HSSFWorkbook workbook = new HSSFWorkbook(fis);
			int rowindex    = 0;
			int columnindex = 0;
			//시트 수 (첫번째에만 존재하므로 0을 준다)
			//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
			HSSFSheet sheet = workbook.getSheetAt(0);
			//행의 수
			int rows=sheet.getPhysicalNumberOfRows();
			for(rowindex = 1; rowindex < rows; rowindex++){
				//행을 읽는다
				HSSFRow row = sheet.getRow(rowindex);
				if(row != null){
					result = new HashMap<String, String>();
					for(columnindex = 0; columnindex < excelKeyArr.length; columnindex++){
						//셀값을 읽는다
						HSSFCell cell = row.getCell(columnindex);
						//셀이 빈값일경우를 위한 널체크
						if(cell == null){
							continue;
						}else{
							//타입별로 내용 읽기

							switch (cell.getCellType()){
							case HSSFCell.CELL_TYPE_FORMULA:
								result.put(excelKeyArr[columnindex], cell.getCellFormula());
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:
								result.put(excelKeyArr[columnindex], (int)cell.getNumericCellValue()+"");
								break;
							case HSSFCell.CELL_TYPE_STRING:
								result.put(excelKeyArr[columnindex], cell.getStringCellValue()+"");
								break;
							case HSSFCell.CELL_TYPE_BLANK:
								result.put(excelKeyArr[columnindex], cell.getBooleanCellValue()+"");
								break;
							case HSSFCell.CELL_TYPE_ERROR:
								result.put(excelKeyArr[columnindex], cell.getErrorCellValue()+"");
								break;
							}
//							System.out.println("========== "+ excelKeyArr[columnindex] + " :: "+cell.getCellType() + "::" + result.get(excelKeyArr[columnindex]));
						}
					}

					resultList.add(result);
				}
			}
			
		}catch(Exception e){
			logger.error("messege", e);
			throw e;
		}
		/*finally{
			return resultList;
		}*/
		return resultList;
	}

	public List<Map<String, String>> getExcelXlsxData(String[] excelKeyArr) throws Exception{
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		Map<String, String>       result     = new HashMap<String, String>();

		File excelFile = new File(inputFile);
		//파일을 읽기위해 엑셀파일을 가져온다
		FileInputStream fis   = null;
		XSSFWorkbook workbook = null;
		try{
			fis   = new FileInputStream(excelFile);
			workbook = new XSSFWorkbook(fis);
			XSSFSheet    sheet    = workbook.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();
			for (int rowindex = 1; rowindex < rows; rowindex++) {
				// 행을읽는다
				XSSFRow row = sheet.getRow(rowindex);
				if (row != null) {
					result = new HashMap<String, String>();
					for ( int colIndex = 0; colIndex < excelKeyArr.length; colIndex++ ){
						XSSFCell cell = row.getCell(colIndex);
						// 셀이 빈값일경우를 위한 널체크
						if (cell == null) {
							continue;
						} else {
							// 타입별로 내용 읽기
							switch (cell.getCellType()) {
							case XSSFCell.CELL_TYPE_FORMULA:
								result.put(excelKeyArr[colIndex], cell.getStringCellValue());
								break;
							case XSSFCell.CELL_TYPE_NUMERIC:
								result.put(excelKeyArr[colIndex], (int)cell.getNumericCellValue()+"");
								break;
							case XSSFCell.CELL_TYPE_STRING:
							case XSSFCell.CELL_TYPE_BLANK:
								result.put(excelKeyArr[colIndex], cell.getStringCellValue());
								break;
							case XSSFCell.CELL_TYPE_ERROR:
								result.put(excelKeyArr[colIndex], cell.getErrorCellValue()+"");
								break;
							}
						}
					}
					resultList.add(result);
				}
			}
			fis.close();
		}catch(IOException e){
			logger.error("messege", e);
		}catch(Exception e){
			logger.error("messege", e);
		}finally{
			if(fis != null){fis.close();}
		}

		return resultList;
	}

}

