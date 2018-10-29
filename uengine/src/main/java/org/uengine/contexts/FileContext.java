package org.uengine.contexts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.metaworks.*;
import org.uengine.kernel.Activity;
import org.uengine.kernel.CommandVariableValue;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinition;
import org.uengine.kernel.ProcessInstance;
import org.uengine.kernel.ProcessVariable;
import org.uengine.kernel.UEngineException;
import org.uengine.util.UEngineUtil;
import org.uengine.util.ftp.Uploader;

/**
 * @author Jinyoung Jang
 */

public class FileContext implements java.io.Serializable, CommandVariableValue, Cloneable{
	private static final long serialVersionUID = org.uengine.kernel.GlobalContext.SERIALIZATION_UID;

	public static void metaworksCallback_changeMetadata(Type type){
		FieldDescriptor fd;
		
		EnablingDependancy enableIfFtpFile = new EnablingDependancy("FtpFile"){

			public boolean enableIf(Object dependencyFieldValue) {
				return dependencyFieldValue!=null && ((Boolean)dependencyFieldValue).booleanValue();
			}
			
		};
		
		fd = type.getFieldDescriptor("FtpUserId");		
		fd.setAttribute("dependancy", enableIfFtpFile);
		
		fd = type.getFieldDescriptor("FtpPassword");		
		fd.setAttribute("dependancy", enableIfFtpFile);
		
		fd = type.getFieldDescriptor("FtpAddress");		
		fd.setAttribute("dependancy", enableIfFtpFile);

		fd = type.getFieldDescriptor("FtpDirectory");		
		fd.setAttribute("dependancy", enableIfFtpFile);

		fd = type.getFieldDescriptor("FtpFileNamePattern");		
		fd.setAttribute("dependancy", enableIfFtpFile);

		fd = type.getFieldDescriptor("TemplateFilePath");		
		fd.setAttribute("dependancy", enableIfFtpFile);

	}
	
	String path;
		public String getPath() {
			return path;
		}
		public void setPath(String value) {
			path = value;
		}
		
	boolean isFtpFile = false;
		public boolean isFtpFile() {
			return isFtpFile;
		}
		public void setFtpFile(boolean isFtpFile) {
			this.isFtpFile = isFtpFile;
		}
		
	String ftpUserId;
		public String getFtpUserId() {
			return ftpUserId;
		}
		public void setFtpUserId(String ftpUserId) {
			this.ftpUserId = ftpUserId;
		}
		
	String ftpPassword;
		public String getFtpPassword() {
			return ftpPassword;
		}
		public void setFtpPassword(String ftpPassword) {
			this.ftpPassword = ftpPassword;
		}
		
	String ftpAddress;		
		public String getFtpAddress() {
			return ftpAddress;
		}
		public void setFtpAddress(String ftpAddress) {
			this.ftpAddress = ftpAddress;
		}
	
	String ftpDirectory;
		public String getFtpDirectory() {
			return ftpDirectory;
		}
		public void setFtpDirectory(String ftpDirectory) {
			this.ftpDirectory = ftpDirectory;
		}
	
	String ftpFileNamePattern;
		public String getFtpFileNamePattern() {
			return ftpFileNamePattern;
		}
		public void setFtpFileNamePattern(String ftpFileNamePattern) {
			this.ftpFileNamePattern = ftpFileNamePattern;
		}
	
	String templateFilePath;
		public String getTemplateFilePath() {
			return templateFilePath;
		}
		public void setTemplateFilePath(String templateFile) {
			this.templateFilePath = templateFile;
		}
		
	public File getFile(){
		if(getPath()==null) return null;
		
		return new File(getPath());
	}
	
	public File getFileForEngine()
	{
		if(getPath()==null) return null;
		
		String dir = GlobalContext.getPropertyString("filesystem.path", org.uengine.kernel.ProcessDefinitionFactory.DEFINITION_ROOT);
		String fileName = getPath().replace('/', File.separator.charAt(0));
		return new File(dir + fileName);
	}
	
	public String getFullSavingFTPURL(ProcessInstance instance) throws Exception{	
		return "ftp://"+ getFtpUserId() +":"+ getFtpPassword() +"@"+ getFtpAddress() +"/"+ getFtpDirectory() +"/"+ getSavingFileName(instance);
	}

	private String getSavingFileName(ProcessInstance instance) throws Exception{					
		return (instance!=null ? instance.getProcessDefinition().evaluateContent(instance, getFtpFileNamePattern()).toString() : getFtpFileNamePattern());
	}

	public void beforeOpen(ProcessInstance instance, String variableKey) throws Exception {
		
		ProcessVariable theVariable;
		FileContext defaultValue;
		if(instance!=null){
			theVariable = instance.getProcessDefinition().getProcessVariable(variableKey);
			defaultValue = (FileContext)theVariable.getDefaultValue();
		}else
			defaultValue = this;

		//document source
		String docFileURL = (UEngineUtil.isNotEmpty(getPath()) ? getPath() : defaultValue.getTemplateFilePath());

		if(docFileURL==null)
			throw new UEngineException(" document file is empty.");
		
		ProcessDefinition definition = (instance !=null ? instance.getProcessDefinition() : new ProcessDefinition(){

			public StringBuffer evaluateContent(ProcessInstance instance, String expression) {
				return new StringBuffer("");
			}
			
		});

		docFileURL = (instance != null ? definition.evaluateContent(instance, docFileURL).toString() : docFileURL);
		
		InputStream is = new URL(docFileURL).openStream();
		Workbook workbook = Workbook.getWorkbook(is);

		String tempFName = defaultValue.getSavingFileName(instance);
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
							WritableCell label = new Label(col,row, definition.evaluateContent(instance, expression).toString(), style); 
							sheet.addCell(label);
							
						}else
						if(expression.indexOf("<%=") > -1 || expression.indexOf("<%=*") > -1){ //if the expression contains any value need to evaluate.
//System.out.println("expression:"+expression);
							l.setString(
								definition.evaluateContent( instance, expression).toString()
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
								definition.evaluateContent(instance, expression).toString()
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
		
		Uploader uploader = new Uploader();
		uploader.connect(defaultValue.getFtpAddress(), defaultValue.getFtpUserId(), defaultValue.getFtpPassword());
		uploader.uploadFile(tempFName, defaultValue.getFtpDirectory() + "/" + tempFName); 	

		setPath(defaultValue.getFullSavingFTPURL(instance));
		
		is.close();
		workbook.close();			
	}
	
	public boolean doCommand(ProcessInstance instance, String variableKey) throws Exception {
		ProcessVariable theVariable = instance.getProcessDefinition().getProcessVariable(variableKey);
		FileContext defaultValue = (FileContext)theVariable.getDefaultValue();
		
		if(defaultValue.getTemplateFilePath()!=null && defaultValue.getTemplateFilePath().endsWith(".xls")){
			//parse the completed xsl file and reflect the changes to the corresponding process variables.//
			ProcessDefinition definition = instance.getProcessDefinition();
			
			String tempFName = defaultValue.getSavingFileName(instance);
			
			//template xls file
			String urlStr = definition.evaluateContent(instance, defaultValue.getTemplateFilePath()).toString();
			InputStream tis = new URL(urlStr).openStream();
			String targetUrlStr = defaultValue.getFullSavingFTPURL(instance);
			
			try{
				InputStream is = new URL(targetUrlStr).openStream();
				
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
	//	System.out.println("ExcelActivity:: onComplete: expression:"+expression);
								if(expression.startsWith("<%=*")){ //if the expression contains any value need to evaluate.
									String k = expression.substring(4, expression.indexOf("%>"));
	//	System.out.println("ExcelActivity:: key:"+k);
									Cell resultCell = resultSheet.getCell(col, row);
									String val = resultCell.getContents();
	//	System.out.println("ExcelActivity:: val:"+val);
									definition.getProcessVariable(k).set(instance, "", val);
								}else
								if(expression.startsWith("<%->")){ //if the expression contains any value need to evaluate.
									String k = expression.substring(4, expression.indexOf("%>"));
									Cell resultCell = resultSheet.getCell(col-1, row);
									String val = resultCell.getContents();
									definition.getProcessVariable(k).set(instance, "", val);
								}
							}catch(Exception e){
	//	System.out.println("ExcelActivity:: error in setting value");
								e.printStackTrace();
							}
						}
					}
				}
				
				is.close();
				tis.close();	
				workbook.close();
				resultWorkbook.close();
			}catch(java.io.FileNotFoundException fne){
				new UEngineException("File result is not found. You might never open or edit a required document.");
			}

		}
		
		return false; //force to save the value itself as well.
	}
	
	public Object clone(){
		try{
/*			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			ObjectOutputStream ow = new ObjectOutputStream(bao);
			ow.writeObject(this);
			ByteArrayInputStream bio = new ByteArrayInputStream(bao.toByteArray());			
			ObjectInputStream oi = new ObjectInputStream(bio);
			
			return oi.readObject();			
*/
			return super.clone();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	@Override
	public String toString() {
		String returnXml = null;
		try {
			returnXml = GlobalContext.serialize(this, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnXml;
	}

	

}