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
import com.hncis.trafficTicket.vo.BgabGascTm01;

@Component("trafficTikcetEmail01DataSourceReader")
public class trafficTikcetEmail01DataSourceReader  extends HncisItemReader<BgabGascTm01>{
	
private transient Log logger = LogFactory.getLog(new BatchLogClass().getClass());
	
	@SuppressWarnings("unchecked")
    @Autowired @Qualifier("trafficTikcetEmail01JdbcItemReader")
    JdbcCursorItemReader trafficTikcetEmail01JdbcItemReader;

    @Override
    protected BgabGascTm01 readItem() throws Exception
    {
    	BgabGascTm01 dto = (BgabGascTm01)trafficTikcetEmail01JdbcItemReader.read();

        return dto;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractItemCountingItemStreamItemReader<BgabGascTm01> settingItemReader()
    {        
        JobExecutionContext jobExecutionContext = super.getJobExecutionContext();
        Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
        
        trafficTikcetEmail01JdbcItemReader.setPreparedStatementSetter(new PreparedStatementSetter(){
            public void setValues(PreparedStatement ps) throws SQLException{
            }
        });
        
        return trafficTikcetEmail01JdbcItemReader;
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
