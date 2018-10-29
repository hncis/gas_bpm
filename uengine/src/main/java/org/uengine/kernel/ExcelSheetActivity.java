package org.uengine.kernel;

import org.uengine.contexts.*;
import org.uengine.util.UEngineUtil;
import org.uengine.util.ftp.Uploader;

import jxl.*; 
import jxl.write.*; 
import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @author Jinyoung Jang
 */

public class ExcelSheetActivity extends DocumentActivity{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;
	
	public String savingFileName;

		public String getSavingFileName() {
			return savingFileName;
		}
		public String getSavingFileName(ProcessInstance instance) {					
			return evaluateContent(instance, getSavingFileName()).toString();
		}
		public void setSavingFileName(String savingFileName) {
			this.savingFileName = savingFileName;
		}

	FileContext templateDocumentFile;
		public FileContext getTemplateDocumentFile() {
			return templateDocumentFile;
		}
		public void setTemplateDocumentFile(FileContext templateDocumentFile) {
			this.templateDocumentFile = templateDocumentFile;
		}

	public ExcelSheetActivity(){
		super();
		setName("excel sheet activity");
	}

	
	private String getFullFTPURL(ProcessInstance instance){
		return "ftp://"+ getFTPid() +":"+ getFTPpw() +"@"+ getUploadFTPAddress() +"/"+ getUploadFTPDirectory() +"/"+ getSavingFileName(instance);
	}

	public void executeActivity(ProcessInstance instance) throws Exception{
		if(getDocumentFile()==null)
			throw new UEngineException(getActivityLabel() + " document file is empty.");

		//document source
		String docFileURL = getDocumentFile().getPath();
		docFileURL = evaluateContent(instance, docFileURL).toString();		
		InputStream is = new URL(docFileURL).openStream();
		Workbook workbook = Workbook.getWorkbook(is);

		String tempFName = getSavingFileName(instance);
		WritableWorkbook copy = Workbook.createWorkbook(new File(tempFName), workbook); 
					
		WritableSheet sheets[] = copy.getSheets(); 
//System.out.println("	ExcelSheetActivity:getWIHAddress: start parsing file");
		
		for(int i=0; i<sheets.length; i++){
			WritableSheet sheet = sheets[i];
				
			for(int row = 0; row < sheet.getRows(); row++){
				boolean newRowInserted=false;
				
				for(int col = 0; col < sheet.getColumns(); col++){						
					WritableCell cell = sheet.getWritableCell(col,row);
					
					if (cell.getType() == CellType.LABEL) 
					{ 
						Label l = (Label) cell;				
						String expression = l.getString();
						
						if(expression.startsWith("<%=+")){
//System.out.println("expression:"+expression);
							
							if(!newRowInserted){
								newRowInserted = true;
								sheet.insertRow(row);
							}																
							//l.setString(expression);
/*
								WritableCell existingCell = sheet.getWritableCell(col,row);
								Label ecl = (Label) existingCell;
								ecl.setString(								
									EMailActivity.parseContent(expression, this, instance).toString()
								);*/
							
							jxl.format.CellFormat style = l.getCellFormat();
							WritableCell label = new Label(col,row, evaluateContent(instance, expression).toString(), style); 
							sheet.addCell(label);
							
						}else
						if(expression.indexOf("<%=") > -1 || expression.indexOf("<%=*") > -1){ //if the expression contains any value need to evaluate.
//System.out.println("expression:"+expression);
							l.setString(
								evaluateContent( instance, expression).toString()
							);
//System.out.println("evaluated:"+l.getString());
						}else
						if(expression.startsWith("<%->")){
							l.setString("");
						}else
						if(expression.startsWith("<%<-")){
							WritableCell leftCell = sheet.getWritableCell(col-1,row);
							Label lcl = (Label) leftCell;					
							lcl.setString(
								evaluateContent(instance, expression).toString()
							);
							
							l.setString("");
							}
						}																		
					}	
					
					if(newRowInserted) row++;
				}
				
			}
						
			copy.write(); 
			copy.close();
System.out.println("	ExcelSheetActivity:getWIHAddress: upload the copy: " + tempFName);
		
		Uploader uploader = new Uploader();
		uploader.connect(uploadFTPAddress, getFTPid(), getFTPpw());
		uploader.uploadFile(tempFName, getUploadFTPDirectory() + "/" + tempFName); 	
		is.close();
		workbook.close();	

		super.executeActivity(instance);
	}

	public boolean onComplete(ProcessInstance instance, Object payload) throws Exception{
		super.onComplete(instance, payload);

		//parse the completed xsl file and reflect the changes to the corresponding process variables.//
			
		String tempFName = getSavingFileName(instance);
		
		//template xls file
		String urlStr = evaluateContent(instance, getTemplateDocumentFile().getPath()).toString();
		InputStream tis = new URL(urlStr).openStream();
		InputStream is = new URL(getFullFTPURL(instance)).openStream();
		Workbook workbook = Workbook.getWorkbook(tis);
		Workbook resultWorkbook = Workbook.getWorkbook(is);		
		
		Sheet resultSheets[] = resultWorkbook.getSheets(); 
		Sheet sheets[] = workbook.getSheets(); 
		
		for(int i=0; i<sheets.length; i++){
			Sheet sheet = sheets[i];
			Sheet resultSheet = resultSheets[i];
			
			for(int col = 0; col < sheet.getColumns(); col++){
				for(int row = 0; row < sheet.getRows(); row++){
					try{
						Cell cell = sheet.getCell(col,row);				
		
						String expression = cell.getContents();
//System.out.println("ExcelActivity:: onComplete: expression:"+expression);
						if(expression.startsWith("<%=*")){ //if the expression contains any value need to evaluate.
							String k = expression.substring(4, expression.indexOf("%>"));
//System.out.println("ExcelActivity:: key:"+k);
							Cell resultCell = resultSheet.getCell(col, row);
							String val = resultCell.getContents();
//System.out.println("ExcelActivity:: val:"+val);
							getProcessDefinition().getProcessVariable(k).set(instance, "", val);
						}else
						if(expression.startsWith("<%->")){ //if the expression contains any value need to evaluate.
							String k = expression.substring(4, expression.indexOf("%>"));
							Cell resultCell = resultSheet.getCell(col-1, row);
							String val = resultCell.getContents();
							getProcessDefinition().getProcessVariable(k).set(instance, "", val);
						}
					}catch(Exception e){
//System.out.println("ExcelActivity:: error in setting value");
						e.printStackTrace();
					}
				}
			}
		}
		
		is.close();
		tis.close();	
		workbook.close();
		resultWorkbook.close();
		
		return true;
	}

	public Map getActivityDetails(ProcessInstance inst, String locale) throws Exception{
		Hashtable details = (Hashtable)super.getActivityDetails(inst, locale);
		
		String docURL = getFullFTPURL(inst);
		details.put("working document", "<a href='" + docURL + "'>" + docURL + "</a>");
		
		return details;
	}
	
	public ValidationContext validate(Map options) {
		ValidationContext vc = super.validate(options);
		
		FileContext docFile = getTemplateDocumentFile();
		if(docFile!=null){
			try{
//				ProcessInstance.USE_EJB = false;
				ComplexActivity.USE_JMS = false;
				ComplexActivity.USE_THREAD = false;
						
				ProcessInstance instance 
					= new DefaultProcessInstance(getProcessDefinition(), "test instance", null);
				
				evaluateContent(instance, docFile.getPath(), vc);
			}catch(Exception e){}
		}
		
		return vc;
	}


	public Map createParameter(ProcessInstance instance) throws Exception{
		Map parameters = super.createParameter(instance);
		
		parameters.put("documentFile", getFullFTPURL(instance));
		
		return parameters;
	}


	public static void main(String[] args) throws Exception{		
		ExcelSheetActivity esa = new ExcelSheetActivity();
		
		FileContext docFileCtx = new FileContext();
			docFileCtx.setPath("file:C:\\Java\\excel\\jxlrwtest2.xls");
			
		esa.setDocumentFile(docFileCtx);
				
		ProcessInstance.USE_CLASS = DefaultProcessInstance.class;
		ProcessInstance inst = ProcessInstance.create(new ProcessDefinition());
		inst.set("", "var1", "value of var1");
		inst.set("", "var2", "value of var2");
		
		try{
			esa.executeActivity(inst);
		}catch(Exception e){}
		
		esa.onComplete(inst, null);
		
		System.out.println(inst.get("", "var2"));
	}


}