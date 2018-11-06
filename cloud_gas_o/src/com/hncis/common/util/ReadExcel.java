package com.hncis.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * Reads excel file and return list of the cell values
 * @author HG02410
 * @version 1.0
 */
public class ReadExcel {
    private transient Log logger = LogFactory.getLog(getClass());

  private String inputFile;

  public void setInputFile(String inputFile) {
    this.inputFile = inputFile;
  }
/**
 * Reads excel file and return list of the cell values
 * File type has to be "95-2003 excel workbook"
 * First row names will be keys, don't use special characters like '"\
 * @param String 
 * @return List<HashMap<String, Object>>
 * @throws IOException
 */
  public List<HashMap<String, Object>> read() throws IOException {
	  List<HashMap<String, Object>> excellCellList = new ArrayList<HashMap<String, Object>>();
	  List<String> lebels = new ArrayList<String>();
	  FileInputStream inputWorkbook = new FileInputStream(new File(inputFile));
	  Workbook w;
	  try {
		  w = Workbook.getWorkbook(inputWorkbook);
		  // Get the first sheet
		  Sheet sheet = w.getSheet(0);
		  // Loop by rows
		  if(sheet != null) {
			  for (int i = 0; i < sheet.getColumns(); i++) {
				  Cell cell = sheet.getCell(i,0);
				  lebels.add(cell.getContents().toString());		
			  }
		  }
		  for (int j = 0; j < sheet.getRows(); j++) {
			  HashMap<String, Object> excelCell = new HashMap<String, Object>();
			  for (int i = 0; i < sheet.getColumns(); i++) {
				  Cell cell = sheet.getCell(i, j);
				  excelCell.put(lebels.get(i), cell.getContents());
			  }
			  excellCellList.add(excelCell);
		  }
	  } catch (BiffException e) {
			 logger.error("messege", e);
	  }
	  return excellCellList;
  }
} 

