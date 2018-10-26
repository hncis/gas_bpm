package com.hncis.batch.reader;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Component;

import com.hncis.batch.factor.BatchLogClass;
import com.hncis.batch.factor.HncisItemReader;
import com.hncis.batch.factor.JobExecutionContext;
import com.hncis.batch.job.vo.dto.BgabGascI001Dto;
import com.hncis.businessVehicles.vo.BgabGascbv01Dto;

@Component("asVehiclesMail01DataSourceReader")
public class asVehiclesMail01DataSourceReader extends HncisItemReader<BgabGascbv01Dto>{
	
	private transient Log logger = LogFactory.getLog(new BatchLogClass().getClass());
	    
	    @SuppressWarnings("unchecked")
	    @Autowired @Qualifier("asVehiclesMail01JdbcItemReader")
	    JdbcCursorItemReader asVehiclesMail01JdbcItemReader;

	    @Override
	    protected BgabGascbv01Dto readItem() throws Exception
	    {
	    	BgabGascbv01Dto dto = (BgabGascbv01Dto)asVehiclesMail01JdbcItemReader.read();

	        return dto;
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    protected AbstractItemCountingItemStreamItemReader<BgabGascbv01Dto> settingItemReader()
	    {        
	        JobExecutionContext jobExecutionContext = super.getJobExecutionContext();
	        Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
	        
	        asVehiclesMail01JdbcItemReader.setPreparedStatementSetter(new PreparedStatementSetter(){
	            public void setValues(PreparedStatement ps) throws SQLException{
	            }
	        });
	        
	        return asVehiclesMail01JdbcItemReader;
	    }
	    
	    protected void setParameterToJobExecutionContext() throws Exception
	    {
	        JobExecutionContext jobExecutionContext = super.getJobExecutionContext();
	        Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
	    }

	    @Override
	    public void beforeStep(StepExecution stepExecution)
	    {
	        super.beforeStep(stepExecution);
	    }
	    
	    @Override
	    public ExitStatus afterStep(StepExecution stepExecution) 
	    {        
	        return stepExecution.getExitStatus();
	    }

	}

