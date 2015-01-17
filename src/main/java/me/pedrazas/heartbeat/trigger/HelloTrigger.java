package me.pedrazas.heartbeat.trigger;

import me.pedrazas.heartbeat.jobs.Bootstrap;
import me.pedrazas.heartbeat.jobs.HelloJob;
import me.pedrazas.heartbeat.jobs.listeners.HelloJobListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

public class HelloTrigger {
	public static int count = 0;
	
	
	
	public static void main(String[] args) throws Exception {
		 
 
		JobKey bootstrapKey = new JobKey("BootStrap", "hb1");
		JobDetail bootstrapJob = JobBuilder.newJob(Bootstrap.class)
				.withIdentity(bootstrapKey)
				.storeDurably(true)
				.usingJobData("count", 1)
				.build();
		
		JobKey jobKey = new JobKey("Heartbeat", "runtime");
    	JobDetail job = JobBuilder.newJob(HelloJob.class)
		.withIdentity(jobKey)
		.storeDurably(true)
		.usingJobData("count", 1)
		.build();    	
 
    	Trigger bootstrapTrigger = TriggerBuilder
    			.newTrigger()
    			.withIdentity("BootStrap", "group1")
    			.withSchedule(
    			    SimpleScheduleBuilder.repeatSecondlyForTotalCount(5))
    			.build();
    	
    	
    	Trigger trigger = TriggerBuilder
		.newTrigger()
		.withIdentity("dummyTriggerName", "group1")
		.withSchedule(
			CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))					
		.build();
 
    	Scheduler scheduler = new StdSchedulerFactory().getScheduler();
    	
//    	//Listener attached to jobKey
//    	scheduler.getListenerManager().addJobListener(
//    		new HelloJobListener(), KeyMatcher.keyEquals(jobKey)
//    	);

    	//Listener attached to jobKey
    	scheduler.getListenerManager().addJobListener(
    		new HelloJobListener(), KeyMatcher.keyEquals(bootstrapKey)
    	);
    	scheduler.start();
    	
//    	scheduler.scheduleJob(job, trigger);
    	scheduler.scheduleJob(bootstrapJob, bootstrapTrigger);
	}
}
