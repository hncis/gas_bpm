package org.uengine.scheduler;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.triggers.CronTriggerImpl;

public class SchedulerUtil {
	
	public static Calendar getCalendarByCronExpression(String cronExpression) throws Exception {
		SchedulerFactory sf = new StdSchedulerFactory();
		Scheduler sched = sf.getScheduler();
		
		JobKey jobKey = new JobKey("tempJobDetail", "tempGroupJobDetail");
		JobDetailImpl jd = (JobDetailImpl) sched.getJobDetail(jobKey);
		TriggerKey triggerKey = new TriggerKey("tempJobDetail", "tempGroupJobDetail");
		if (jd != null) {
			sched.unscheduleJob(triggerKey);
			sched.deleteJob(jobKey);
		}
		
		CronTriggerImpl cronTrigger = (CronTriggerImpl) sched.getTrigger(triggerKey);
		cronTrigger.setCronExpression(cronExpression);
		
		JobDetail jobDetail = sched.getJobDetail(jobKey);
		
		Date firstRunTime = sched.scheduleJob(jobDetail, cronTrigger);
		
		Calendar c = new GregorianCalendar();
		c.setTime(firstRunTime);
		c.set(Calendar.MILLISECOND, 0);
		
		sched.unscheduleJob(triggerKey);
		sched.deleteJob(jobKey);
		
		return c;
	}
}
