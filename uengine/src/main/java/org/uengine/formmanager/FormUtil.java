package org.uengine.formmanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.uengine.formmanager.trans.Html2FormView;
import org.uengine.formmanager.trans.Html2Write;
import org.uengine.kernel.GlobalContext;
import org.uengine.kernel.ProcessDefinitionFactory;
import org.uengine.processmanager.ProcessManagerRemote;
import org.uengine.util.LocalFileSystem;
import org.uengine.util.UEngineUtil;

import au.id.jericho.lib.html.FormField;
import au.id.jericho.lib.html.FormFields;
import au.id.jericho.lib.html.OutputDocument;
import au.id.jericho.lib.html.Source;

/**
 * 
 * @author <a href="mailto:bigmahler@users.sourceforge.net">Jong-Uk Jeong</a>
 * @version $Id: FormUtil.java,v 1.10 2009/04/22 11:59:12 kmooje Exp $
 * @version $Revision: 1.10 $
 */
public class FormUtil {
	
	private static Logger logger = Logger.getLogger("bpm.web");

	public static void copyToContext(File contextDir, String defId) throws Exception {
// mHtmlPath = mHtmlPath.replace('\\', '/');
		String formName = defId;// .substring(0, defId.lastIndexOf("."));

		String htmlFormPath = formName + "_write.jsp";
		File contextHtmlFile = new File(contextDir, htmlFormPath);
		File sourceFormHtmlFile = new File(ProcessDefinitionFactory.DEFINITION_ROOT, htmlFormPath);
		
		if ( !contextHtmlFile.exists() ||
			contextHtmlFile.lastModified() != sourceFormHtmlFile.lastModified() ) 
		{
			File file = new File(ProcessDefinitionFactory.DEFINITION_ROOT);
			copyFile(file, contextDir, formName);

		} 
		
// if ( formSubmitFile.exists() ) {
// if ( !contextSubmitFile.exists() ||
// contextSubmitFile.lastModified() < formSubmitFile.lastModified() )
// {
// copySubmitFile(XONEConstants.FORM_REPOSITORY_PATH, contextDir, formName);
// }
// }
	}
	
	public static void copyFile(File formDir, File contextDir, String formName) throws Exception {
		
		File contextFormDir = new File(contextDir, formName);
		if ( !contextFormDir.getParentFile().exists() ) contextFormDir.getParentFile().mkdirs();
		
		String htmlFormPath = formName + ".form";//".html";
		String toHtmlFormPath = htmlFormPath + ".jsp";//".html";
		String writeFormPath = formName + "_write.jsp";
		String viewFormPath = formName + "_view.jsp";
		String viewFormHandlerPath = formName + "_formview.jsp";
		String printFormPath = formName + "_print.jsp";
		
		LocalFileSystem fileSystem = new LocalFileSystem();
		
		File srcFile = new File(formDir, htmlFormPath);
		File trgFile = new File(contextDir, toHtmlFormPath);		
		fileSystem.copyFile(srcFile, trgFile);		
		trgFile.setLastModified(srcFile.lastModified());
		
		if ( (new File(formDir, writeFormPath).exists()) ) {
			File srcWriteFile = new File(formDir, writeFormPath);
			File trgWriteFile = new File(contextDir, writeFormPath);
			fileSystem.copyFile(srcWriteFile, trgWriteFile);
			trgWriteFile.setLastModified(srcWriteFile.lastModified());			
		}
		if ( (new File(formDir, viewFormPath).exists()) ) {
			File srcViewFile = new File(formDir, viewFormPath);
			File trgViewFile = new File(contextDir, viewFormPath);
			fileSystem.copyFile(srcViewFile, trgViewFile);
			trgViewFile.setLastModified(srcViewFile.lastModified());
		}		
		if ( (new File(formDir, viewFormHandlerPath).exists()) ) {
			File srcViewFormHandlerPath = new File(formDir, viewFormHandlerPath);
			File trgViewFormHandlerPath = new File(contextDir, viewFormHandlerPath);
			fileSystem.copyFile(srcViewFormHandlerPath, trgViewFormHandlerPath);
			trgViewFormHandlerPath.setLastModified(srcViewFormHandlerPath.lastModified());
		}		
		if ( (new File(formDir, printFormPath).exists()) ) {
			File srcPrintForm = new File(formDir, printFormPath);
			File trgPrintForm = new File(contextDir, printFormPath);
			fileSystem.copyFile(srcPrintForm, trgPrintForm);
			trgPrintForm.setLastModified(srcPrintForm.lastModified());
		}	
		
		File formImgDir = new File(formDir, formName);
		if ( formImgDir.exists() ) {
			File contextImgDir = new File(contextDir, formName);
			if ( !contextImgDir.exists() ) contextImgDir.mkdir();
			String[] imgFileArray = formImgDir.list();
			for (int i=0; i<imgFileArray.length; i++) {
				if (logger.isInfoEnabled()) {
					logger
							.info("copyFile(File, File, String) - " + imgFileArray[i]); //$NON-NLS-1$
				}
				fileSystem.copyFile(new File(formDir, formName+"/"+imgFileArray[i]), new File(contextDir, formName+"/"+imgFileArray[i]));
			}
		}
		
	}	
	
	public static void copySubmitFile(File formDir, File contextDir, String formName) throws Exception {
		File contextFormDir = new File(contextDir, formName);
		if ( !contextFormDir.getParentFile().exists() ) contextFormDir.getParentFile().mkdirs();
		
		String submitFormPath = formName + "_submit.jsp";
		
		LocalFileSystem fileSystem = new LocalFileSystem();
		
		if ( (new File(formDir, submitFormPath).exists()) ) {
			fileSystem.copyFile(new File(formDir, submitFormPath), new File(contextDir, submitFormPath));
		}	
	}	
	
	private static String deleteTag(String src, String key) throws Exception {
		String retHtml = ""; 

		int beg = 0;
		int end = 0;
		int keysize = key.length();
		boolean bcontinue = true;

		if (key.equals("<form")) {
			end = src.indexOf(key);

// System.out.println("end:" + String.valueOf(end));
			retHtml = src.substring(beg, end);

			beg = src.indexOf(">", end) + 1;
			retHtml = " " + retHtml + " " + src.substring(beg);

		} else if (key.equals("</form>")) {
			end = src.indexOf(key);
			retHtml = src.substring(beg, end);
			retHtml = " " + retHtml + src.substring(end + 7);
		}

		return retHtml;
	}
	
	
	public static void replaceMacro(String HTML_PATH) throws Exception {
		File htmlFile = new File(HTML_PATH);
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(htmlFile), "UTF-8"));
		StringBuffer daoHeader = new StringBuffer();
		StringBuffer contents = new StringBuffer();
		String line="";
		
		while ( (line=in.readLine()) != null  ) {
			if (line.indexOf("<form") > -1) {
				line = deleteTag(line, "<form");
			}
			if (line.indexOf("</form>") > -1) {
				line = deleteTag(line, "</form>");
			}			
			System.out.println(line);
			contents.append(line);
			contents.append("\r\n");
		}
		UEngineUtil.saveContents(htmlFile.getAbsolutePath(), daoHeader.toString()+contents.toString());
		
	// MhtmlEncoder gen = new MhtmlEncoder();
	// gen.setHtmlFile(HTML_PATH);
	// gen.setMhtmlFile(MHTML_PATH);
	// gen.generate();
	}
	
	public static String deployFormDefinition(
			ProcessManagerRemote pm, 
			String definition,
			String version,
			String definitionName,
			String definitionAlias ,
			String savingFolder,
			String description,
			String objectType ,
			String mimeContents  ,
			String belongingDefinitionId
	) throws Exception{
		
		try{
			
			Source source=new Source(new String(definition));
			source.setLogWriter(new OutputStreamWriter(System.err)); // send
																		// log
																		// messages
																		// to
																		// stderr
			FormFields formFields=source.findFormFields();
			System.out.println("The document contains "+formFields.size()+" form fields:\n");
			
			StringBuffer sb = new StringBuffer();
			for (Iterator i=formFields.iterator(); i.hasNext();) {
				FormField formField=(FormField)i.next();
				System.out.println(formField.getName());
				System.out.println(formField.getFormControl().getFormControlType().toString());
				System.out.println(formField.getDebugInfo());
				
				// sb.append("<" + "%=" + formField.getName() + "%" + ">");
				formFields.addValue(formField.getName(),sb.toString());
				sb.setLength(0);
			}
			
			
			OutputDocument outputDocument=new OutputDocument(source);
			outputDocument.replace(formFields);
			
			//System.out.println("html : " + outputDocument.toString());
			
			// *.form save
			String defVerId = pm.addProcessDefinition(definitionName, Integer.parseInt(version), description, false, definition, savingFolder, belongingDefinitionId, definitionAlias, objectType);
			String DEFINITION_ROOT = GlobalContext.getPropertyString(
					"server.definition.path",
					"./uengine/definition/"
				);
			
			String defverid = defVerId.substring(0, defVerId.lastIndexOf("@"));
			String [] defVerIdArr = defVerId.split("@");
			// String defverid = defVerIdArr[0];
			String processDefinitionVersionID = defVerIdArr[1];


			// *.html save
			String HTML_PATH = DEFINITION_ROOT + defverid +".html";
			OutputStreamWriter bw = null;
			try{
				bw = new OutputStreamWriter(new FileOutputStream(HTML_PATH), "UTF-8");
				bw.write(outputDocument.toString());
				bw.close();
			}catch(Exception e){
				throw e;
			}finally{
				if(bw!=null)
					try{bw.close();}catch(Exception e){};
			}
			
			// *.mhtml save
			String MHTML_PATH = DEFINITION_ROOT + defverid +".mhtml";
			OutputStreamWriter MhtmlWriter = null;
			
			/*
			 * try{ MhtmlWriter = new OutputStreamWriter(new
			 * FileOutputStream(MHTML_PATH), "UTF-8");
			 * MhtmlWriter.write(mimeContents); MhtmlWriter.close();
			 * }catch(Exception e){ throw e; }finally{ if(MhtmlWriter!=null)
			 * try{MhtmlWriter.close();}catch(Exception e){}; }
			 */
			
			int index = HTML_PATH.lastIndexOf(File.separatorChar);
			String IMAGE_PATH = HTML_PATH.substring(index+1, HTML_PATH.length());
			replaceMacro(HTML_PATH);
			File htmlFile = new File(HTML_PATH);
			Html2Write html2write = new Html2Write();
			html2write.transformation(htmlFile, new BigDecimal(defverid));
			
			
			Html2FormView html2formview = new Html2FormView();
			html2formview.transformation(htmlFile, new BigDecimal(defverid));
			
			return defVerId;
		}catch(Exception e){
			pm.cancelChanges();
			e.printStackTrace();
			throw e;
		
		}
		
	}
}
	