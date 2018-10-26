package com.hncis.batch.processor;

import java.io.File;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hncis.batch.factor.BatchLogClass;
import com.hncis.batch.factor.JobExecutionContext;
import com.hncis.batch.job.manager.JobDetailManager;
import com.hncis.batch.job.manager.JobManager;
import com.hncis.batch.job.vo.Job;
import com.hncis.batch.job.vo.impl.JobVo;
import com.hncis.common.Constant;
import com.hncis.common.util.CurrentDateTime;
import com.hncis.common.util.StringUtil;
import com.hncis.common.vo.HncisCommon;

@Service("logRemove01Processor")
public class logRemove01Processor extends StepExecutionListenerSupport implements ItemProcessor<HncisCommon, HncisCommon>{
	
	private transient Log logger = LogFactory.getLog(new BatchLogClass().getClass());

	private StepExecution stepExecution;
	
	@Autowired @Qualifier("jobExecutionContext")
    private JobExecutionContext jobExecutionContext;
	
	@Autowired	@Qualifier("jobDetailManagerImpl") 
	private JobDetailManager jobDetailManager;
	
	@Autowired	@Qualifier("jobManagerImpl") 
	private JobManager jobManager;
	
	int iptCnt = 0;						
	int uptCnt = 0;						
	
	public void beforeStep(StepExecution stepExec) {
		this.stepExecution = stepExec;
				
		Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
				
		iptCnt = 0;
		uptCnt = 0;
	}
	
	public HncisCommon process(HncisCommon dto) throws Exception {	
		
		if(dto==null) return null;
		
		String apacheFolder = "";
		String tomcatFolder = "";
		String fileName 	= "";
		String tempFileDate	= "";
		long diffCnt 		= 0;
		
		System.out.println("######################################");
		System.out.println("Value="+dto.getValue());
		
		
		if(StringUtil.getSystemArea().equals("LOCAL")){
			apacheFolder = Constant.APACHE_LOG_LOCAL_PATH;
			tomcatFolder = Constant.TOMCAT_LOG_LOCAL_PATH;
		}else if(StringUtil.getSystemArea().equals("TEST")){
			apacheFolder = Constant.APACHE_LOG_TEST_PATH;
			tomcatFolder = Constant.TOMCAT_LOG_TEST_PATH;
		}else{
			apacheFolder = Constant.APACHE_LOG_REAL_PATH;
			tomcatFolder = Constant.TOMCAT_LOG_REAL_PATH;
		}
		
		System.out.println("apacheFolder="+apacheFolder);
		System.out.println("tomcatFolder="+tomcatFolder);
		
		// Apache Log Remove
		File[] apacheFile = new File(apacheFolder).listFiles();
		System.out.println("logFile.length="+apacheFile.length);
		if(apacheFile.length > 0){
			for(int i=0; i<apacheFile.length;i++){
				System.out.println("i="+i);
				//System.out.println("filePath="+logFile[i].getPath());
				//System.out.println("fileName="+logFile[i].getName());
				
				fileName = apacheFile[i].getName();
				diffCnt = 0;
				
				if(fileName.contains("ssl_access")){
					System.out.println("fileName="+fileName);
					System.out.println("fileName.substring="+fileName.substring(11, 21));
					
					tempFileDate = fileName.substring(11, 21);
					tempFileDate = tempFileDate.replace("-", "");
					
					System.out.println("tempFileDate="+tempFileDate);
					
					diffCnt = CurrentDateTime.diffOfDate(tempFileDate, CurrentDateTime.getDate());
					System.out.println("diffCnt="+diffCnt);
					
				}else if(fileName.contains("mod_jk")){
					System.out.println("fileName="+fileName);
					System.out.println("fileName.substring="+fileName.substring(7, 17));
					
					tempFileDate = fileName.substring(7, 17);
					tempFileDate = tempFileDate.replace("-", "");
					
					System.out.println("tempFileDate="+tempFileDate);
					
					diffCnt = CurrentDateTime.diffOfDate(tempFileDate, CurrentDateTime.getDate());
					System.out.println("diffCnt="+diffCnt);
				}
				
				if(diffCnt > 30){						
					try{
						File delFile = new File(apacheFolder+"/"+fileName);
						if(delFile.exists()){
							delFile.delete();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		
		// Tomcat Log Remove
		File[] tomcatFile = new File(tomcatFolder).listFiles();
		System.out.println("logFile.length="+tomcatFile.length);
		if(tomcatFile.length > 0){
			for(int i=0; i<tomcatFile.length;i++){
				System.out.println("i="+i);
				//System.out.println("filePath="+logFile[i].getPath());
				System.out.println("fileName main="+tomcatFile[i].getName());
				
				fileName = tomcatFile[i].getName();
				diffCnt = 0;
				
				if(fileName.contains("catalina")){
					System.out.println("fileName="+fileName);
					System.out.println("fileName.substring="+fileName.substring(9, 19));
					
					tempFileDate = fileName.substring(9, 19);
					tempFileDate = tempFileDate.replace("-", "");
					
					System.out.println("tempFileDate="+tempFileDate);
					
					diffCnt = CurrentDateTime.diffOfDate(tempFileDate, CurrentDateTime.getDate());
					System.out.println("catalina diffCnt="+diffCnt);
					
				}else if(fileName.contains("hmb_app.log.")){
					System.out.println("fileName="+fileName);
					System.out.println("fileName.substring="+fileName.substring(12, 22));
					
					tempFileDate = fileName.substring(12, 22);
					tempFileDate = tempFileDate.replace("-", "");
					
					System.out.println("tempFileDate="+tempFileDate);
					
					diffCnt = CurrentDateTime.diffOfDate(tempFileDate, CurrentDateTime.getDate());
					System.out.println("hmb_app diffCnt="+diffCnt);
					
				}else if(fileName.contains("host-manager")){
					System.out.println("fileName="+fileName);
					System.out.println("fileName.substring="+fileName.substring(13, 23));
					
					tempFileDate = fileName.substring(13, 23);
					tempFileDate = tempFileDate.replace("-", "");
					
					System.out.println("tempFileDate="+tempFileDate);
					
					diffCnt = CurrentDateTime.diffOfDate(tempFileDate, CurrentDateTime.getDate());
					System.out.println("host-manager diffCnt="+diffCnt);
					
				}else if(fileName.contains("localhost.")){
					System.out.println("fileName="+fileName);
					System.out.println("fileName.substring="+fileName.substring(10, 20));
					
					tempFileDate = fileName.substring(10, 20);
					tempFileDate = tempFileDate.replace("-", "");
					
					System.out.println("tempFileDate="+tempFileDate);
					
					diffCnt = CurrentDateTime.diffOfDate(tempFileDate, CurrentDateTime.getDate());
					System.out.println("localhost diffCnt="+diffCnt);
					
				}else if(fileName.contains("localhost_access")){
					System.out.println("fileName="+fileName);
					System.out.println("fileName.substring="+fileName.substring(21, 31));
					
					tempFileDate = fileName.substring(21, 31);
					tempFileDate = tempFileDate.replace("-", "");
					
					System.out.println("tempFileDate="+tempFileDate);
					
					diffCnt = CurrentDateTime.diffOfDate(tempFileDate, CurrentDateTime.getDate());
					System.out.println("localhost_access diffCnt="+diffCnt);
					
				}else if(fileName.contains("manager.")){
					System.out.println("fileName="+fileName);
					System.out.println("fileName.substring="+fileName.substring(8, 18));
					
					tempFileDate = fileName.substring(8, 18);
					tempFileDate = tempFileDate.replace("-", "");
					
					System.out.println("tempFileDate="+tempFileDate);
					
					diffCnt = CurrentDateTime.diffOfDate(tempFileDate, CurrentDateTime.getDate());
					System.out.println("manager diffCnt="+diffCnt);
					
				}
				
				if(diffCnt > 30){						
					try{
						File delFile = new File(tomcatFolder+"/"+fileName);
						if(delFile.exists()){
							delFile.delete();
						}
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}
		
		System.out.println("######################################");
		uptCnt++;
		
		return dto;
	}
	
	@Override
	public ExitStatus afterStep(StepExecution stepExec) {
		
		Job job = new JobVo();
		job.setJobInstanceId(stepExec.getJobExecution().getJobId());
		job.setIptCnt(iptCnt);
		job.setUptCnt(uptCnt);
		
		try {
			jobManager.updateJobCnt(job);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return super.afterStep(stepExec);
	}

}
