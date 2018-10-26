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

@Component("userCreate01DataSourceReader")
public class userCreate01DataSourceReader  extends HncisItemReader<BgabGascI001Dto>{
	
private transient Log logger = LogFactory.getLog(new BatchLogClass().getClass());
    
    @SuppressWarnings("unchecked")
    @Autowired @Qualifier("userCreate01JdbcItemReader")
    JdbcCursorItemReader userCreate01JdbcItemReader;

    @Override
    protected BgabGascI001Dto readItem() throws Exception
    {
    	BgabGascI001Dto dto = (BgabGascI001Dto)userCreate01JdbcItemReader.read();

        return dto;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractItemCountingItemStreamItemReader<BgabGascI001Dto> settingItemReader()
    {        
        JobExecutionContext jobExecutionContext = super.getJobExecutionContext();
        Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
        
        userCreate01JdbcItemReader.setPreparedStatementSetter(new PreparedStatementSetter(){
            public void setValues(PreparedStatement ps) throws SQLException{
            }
        });
        
        return userCreate01JdbcItemReader;
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
