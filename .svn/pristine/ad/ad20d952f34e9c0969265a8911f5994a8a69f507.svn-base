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
import com.hncis.common.vo.BgabGascz003Dto;

@Component("approvalCreate01DataSourceReader")
public class approvalCreate01DataSourceReader  extends HncisItemReader<BgabGascz003Dto>{
	
private transient Log logger = LogFactory.getLog(new BatchLogClass().getClass());
    
    @SuppressWarnings("unchecked")
    @Autowired @Qualifier("approvalCreate01JdbcItemReader")
    JdbcCursorItemReader approvalCreate01JdbcItemReader;

    @Override
    protected BgabGascz003Dto readItem() throws Exception
    {
    	BgabGascz003Dto dto = (BgabGascz003Dto)approvalCreate01JdbcItemReader.read();
    	System.out.println("######## approvalCreate01DataSourceReader readItem");

        return dto;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected AbstractItemCountingItemStreamItemReader<BgabGascz003Dto> settingItemReader()
    {        
        JobExecutionContext jobExecutionContext = super.getJobExecutionContext();
        Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
        
        System.out.println("######## approvalCreate01DataSourceReader AbstractItemCountingItemStreamItemReader");
        
        approvalCreate01JdbcItemReader.setPreparedStatementSetter(new PreparedStatementSetter(){
            public void setValues(PreparedStatement ps) throws SQLException{
            }
        });
        
        return approvalCreate01JdbcItemReader;
    }
    
    protected void setParameterToJobExecutionContext() throws Exception
    {
        JobExecutionContext jobExecutionContext = super.getJobExecutionContext();
        Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
    }

    @Override
    public void beforeStep(StepExecution stepExecution)
    {
    	System.out.println("######## approvalCreate01DataSourceReader beforeStep");
        super.beforeStep(stepExecution);
    }
    
    @Override
    public ExitStatus afterStep(StepExecution stepExecution) 
    {        
        return stepExecution.getExitStatus();
    }

}
