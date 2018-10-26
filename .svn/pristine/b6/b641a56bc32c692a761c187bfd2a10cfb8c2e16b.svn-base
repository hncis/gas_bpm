package com.hncis.batch.factor;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

// TODO: Auto-generated Javadoc
/**
 * The Class HncisItemReader. - item을 읽는 클래스
 *
 * @param <T> the generic type
 */
public abstract class HncisItemReader<T>  extends StepExecutionListenerSupport{
	
	/** The is execution context open. - 변수 - execution context 가 로드여부 */
	private boolean isExecutionContextOpen;
    
    /** The item reader. - item 을 읽는 클래스*/
    private AbstractItemCountingItemStreamItemReader<T> itemReader;
    
    /** The job execution context. - 잡 실행 목록*/
    @Autowired @Qualifier("jobExecutionContext")
    private JobExecutionContext jobExecutionContext;
    
    /**
     * item을 읽음.
     *
     * @return the t
     * @throws Exception the exception
     */
    public final T read() throws Exception
    {
        if(!isExecutionContextOpen)
        {
            this.setParameterToJobExecutionContext();
            this.setItemReader();
            this.openExcutionContext();
        }
        
        T item = this.readItem();
        
        if(item == null)
            this.closeExcutionContext();
        
        return item;
    }
    
    /**
     * 파일을 읽기 전에 파라미터 설정을 함.
     *
     * @param stepExecution the step execution
     * @return the t
     */
    @Override
    public void beforeStep(StepExecution stepExecution) 
    {
        if(stepExecution.getJobExecution().getStepExecutions().size() == 1)
        {
            jobExecutionContext.reset();
            Map<String,JobParameter> jobParameterMap = stepExecution.getJobParameters().getParameters();
            Set<String> jobParameterSet = jobParameterMap.keySet();
            Iterator<String> keyIterator = jobParameterSet.iterator();
            
            Map<String,Object> contextMap = jobExecutionContext.getParameterMap();
            while(keyIterator.hasNext())
            {
                String key = keyIterator.next();
                contextMap.put(key, jobParameterMap.get(key).getValue());
            }
        }
    }
    
    /**
     * 파라미터 설정.
     *
     * @throws Exception the exception
     */
    protected void setParameterToJobExecutionContext()throws Exception{}
    
    /**
     * 파라미터 가져옴.
     *
     * @return the job execution context
     */
    public JobExecutionContext getJobExecutionContext(){ return this.jobExecutionContext; }
    
    /**
     * Setter - item reader.
     */
    private void setItemReader()
    {
        this.itemReader = this.settingItemReader();
    }
    
    /**
     * Open excution context.
     */
    private void openExcutionContext()
    {
        ExecutionContext executionContext = new ExecutionContext();
        if(this.itemReader != null)
            this.itemReader.open(executionContext);
        this.isExecutionContextOpen = true;
    }
    
    /**
     * item reader 설정.
     *
     * @return the abstract item counting item stream item reader
     */
    protected abstract AbstractItemCountingItemStreamItemReader<T> settingItemReader();

    /**
     * Read item.
     *
     * @return the t
     * @throws Exception the exception
     */
    protected abstract T readItem() throws Exception;
    
    /**
     * READ 후 종료작업을 하는 메서드.
     */
    private void closeExcutionContext()
    {
        if(this.itemReader != null)
            this.itemReader.close();
        
        this.isExecutionContextOpen = false;
    }
}
