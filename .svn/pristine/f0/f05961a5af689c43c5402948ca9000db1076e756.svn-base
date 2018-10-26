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
import com.hncis.common.vo.HncisCommon;

@Component("logRemove01DataSourceReader")
public class logRemove01DataSourceReader  extends HncisItemReader<HncisCommon>{
	
private transient Log logger = LogFactory.getLog(new BatchLogClass().getClass());
    
    @SuppressWarnings("unchecked")
    @Autowired @Qualifier("logRemove01JdbcItemReader")
    JdbcCursorItemReader logRemove01JdbcItemReader;

    @Override
    protected HncisCommon readItem() throws Exception
    {
    	HncisCommon dto = (HncisCommon)logRemove01JdbcItemReader.read();
        return dto;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractItemCountingItemStreamItemReader<HncisCommon> settingItemReader()
    {        
        JobExecutionContext jobExecutionContext = super.getJobExecutionContext();
        Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
        
        logRemove01JdbcItemReader.setPreparedStatementSetter(new PreparedStatementSetter(){
            public void setValues(PreparedStatement ps) throws SQLException{
            }
        });
        return logRemove01JdbcItemReader;
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
