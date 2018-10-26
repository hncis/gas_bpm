package com.hncis.batch.quartz;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component("autoJobLauncher")
public class AutoJobLauncher {
	
	private transient Log logger = LogFactory.getLog(this.getClass());
	private static final boolean USE_FLAG = true;

	@Autowired
	private JobLocator jobLocator;
	@Autowired
	private JobLauncher jobLauncher;

	private static final String DEFAULT_RESTART_YN = "N";
	private static final int DEFAULT_RESTART_COUNT = 1;
	private static final int DEFAULT_RESTART_TIME = 300;
	
	private static final Map<String, FlatFileItemReader[]> READER_MAP = new HashMap<String, FlatFileItemReader[]>();
	private static final Map<String, String[]> RESOURCE_MAP = new HashMap<String, String[]>();
	
	// ####################### reader ����� #######################
	// hrToPlasJob
	@Autowired @Qualifier("db2InsaFileItemReader")
	private FlatFileItemReader INSA_FILE_ITEM_READER;
	
	// familyJobBySam
//	@Autowired @Qualifier("familyFileItemReader")
//	private FlatFileItemReader FAMILY_FILE_ITEM_READER;
//	
//	// xocxintJobBySam
//	@Autowired @Qualifier("xocxintFileItemReader")
//	private FlatFileItemReader XOCXINT_FILE_ITEM_READER;
	// ###########################################################
	
	/**
	 * Job init
	 */
	private void init(String jobName, Map<String, Object> parameterMap){
		
		FlatFileItemReader[] tempReader;
		String[] tempResource;

		if(READER_MAP.size() == 0){
			Properties properties = new Properties();
			
			try {
				properties.load(new ClassPathResource("jobResource.properties").getInputStream());
			} catch (FileNotFoundException e) {
				logger.error("AutoJobLauncher FileNotFoundException : ", e);
			} catch (IOException e) {
				logger.error("AutoJobLauncher IOException : ", e);
			}

			// ####################################################################
			// # reader init
			// # resource init
			// ####################################################################
			tempReader = new FlatFileItemReader[2];
			tempResource = new String[1];
			tempResource[0] = properties.getProperty("fileItemReaderResource");
						
//			if(jobName.equals("familyJobBySam")){
//				tempReader[0] = FAMILY_FILE_ITEM_READER;
//				tempReader[0].setEncoding("utf-8");
//			}else if(jobName.equals("xocxintJobBySam")){
//				tempReader[0] = XOCXINT_FILE_ITEM_READER;
//				tempReader[0].setEncoding("utf-8");
//			}else{
//				tempReader[0] = INSA_FILE_ITEM_READER;
//				tempReader[0].setEncoding("utf-8");
//			}
			
			tempReader[0] = INSA_FILE_ITEM_READER;
			tempReader[0].setEncoding("utf-8");
			
			READER_MAP.put(jobName, tempReader);			
			RESOURCE_MAP.put(jobName, tempResource);
			
		}
		
		// ####################################################################
		// # reader, resource setting
		// ####################################################################
		if(READER_MAP.containsKey(jobName)){
			tempReader = (FlatFileItemReader[])READER_MAP.get(jobName);
			tempResource = (String[])RESOURCE_MAP.get(jobName);
			Resource resource;
			String filePath;
			String realPath;
			
			for(int i=0; i<tempResource.length; i++){
				filePath = tempResource[i]+(String)parameterMap.get("input.file.name");
				if(jobName.equals("xocxintJobBySam")){
					realPath = filePath;
				}else{
					realPath = String.format(filePath, (String)parameterMap.get("input.file.date"));
				}
				resource = new FileSystemResource(realPath);
				tempReader[i].setResource(resource);
			}
		}
	}
	
	/**
	 * Job Run
	 * @param jobName
	 * @param parameterMap
	 * @return
	 */
	public boolean run(String jobName, Map<String, Object> parameterMap){
		return run(jobName, parameterMap, DEFAULT_RESTART_YN, DEFAULT_RESTART_COUNT, DEFAULT_RESTART_TIME);
	}
	
	/**
	 * Job Run
	 * @param jobName
	 * @param parameterMap
	 * @param restartYn
	 * @param restartCount
	 * @param restartTime
	 * @return
	 */
	public boolean run(String jobName, Map<String, Object> parameterMap, String restartYn, int restartCount, int restartTime){
				
		boolean runFlag = false;
		
		if(USE_FLAG){
			init(jobName, parameterMap);

			int startLoopCount = 0;
			
			logger.info("##################################################################");
			logger.info("# AutoJobLauncher - Start job : (jobName : " + jobName + ")");
			logger.info("##################################################################");
			
			System.out.println("# AutoJobLauncher - Start job : (jobName : " + jobName + ")");
			
			while(startLoopCount <= restartCount){		
				try {
					if(startLoopCount > 0){
						try {
							logger.info("---------------- Thread Sleep (" + restartTime + ") ----------------");
							Thread.sleep(restartTime * 1000);
						} catch (InterruptedException e) {
							logger.error("InterruptedException ", e);
						}
					}
	
					startLoopCount++;
	
					if("ftpFileDownJob".equals(jobName)){
						String workDir = (String)parameterMap.get("workDir");
						String workFile = String.format((String)parameterMap.get("workFileResource"), new SimpleDateFormat((String)parameterMap.get("workFileFormat")).format(Calendar.getInstance().getTime()));
						String saveDir = (String)parameterMap.get("saveDir");
						String saveFile = String.format((String)parameterMap.get("saveFileResource"), new SimpleDateFormat((String)parameterMap.get("saveFileFormat")).format(Calendar.getInstance().getTime()));
						
						break;
					}else{
						JobParameters jobParameters = getSettingJobParameters(parameterMap);
						System.out.println("jobName=================:"+jobName);
						System.out.println("jobParameters=================:"+jobParameters);
						JobExecution jobExecution = jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
						
						System.out.println("# job(" + jobName + ") run result : " + jobExecution.getStatus());
						
						logger.info("# job(" + jobName + ") run result : " + jobExecution.getStatus());
						
						if(jobExecution.getStatus() == jobExecution.getStatus().COMPLETED){
							runFlag = true;
							break;
						}
					}
					
					if("N".equals(restartYn)){
						break;
					}
				} catch (JobExecutionAlreadyRunningException e) {
					logger.error("JobExecutionAlreadyRunningException ", e);
					break;
				} catch (JobRestartException e) {
					logger.error("JobRestartException ", e);
					break;
				} catch (JobInstanceAlreadyCompleteException e) {
					logger.error("JobInstanceAlreadyCompleteException ", e);
					break;
				} catch (NoSuchJobException e) {
					logger.error("NoSuchJobException ", e);
					break;
				} catch (Exception e) {
					logger.error("Exception ", e);
					break;
				}
			}
			
			logger.info("##################################################################");
			logger.info("# AutoJobLauncher - End job : (jobName : " + jobName + ", runFlag : " + runFlag + ")");
			logger.info("##################################################################");
		}
		
		READER_MAP.clear();
		
		return runFlag;
	}
	
	/**
	 * Job �Ķ���� ��������
	 * @param parameterMap
	 * @return JobParameters
	 */
	private JobParameters getSettingJobParameters(Map<String, Object> parameterMap){
		JobParametersBuilder builder = new JobParametersBuilder();
		
		for(Map.Entry<String, Object> entry: parameterMap.entrySet()){
			builder.addString(entry.getKey(), (String)entry.getValue());
		}
		
		return builder.toJobParameters();
	}

}
