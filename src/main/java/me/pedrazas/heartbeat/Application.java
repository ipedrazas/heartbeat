package me.pedrazas.heartbeat;

import me.pedrazas.heartbeat.jobs.Bootstrap;
import me.pedrazas.heartbeat.jobs.listeners.HelloJobListener;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application {
	
	private static Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		log.info("Starting HeartBeat Application");
		try {
			createBootstrap();
			createHeartbeat();
		} catch (SchedulerException e) {
			log.error(e.getMessage());
		}
		
	}

	private static void createBootstrap() throws SchedulerException{
		JobKey bootstrapKey = new JobKey("BootStrap", "hb1");
		JobDetail bootstrapJob = JobBuilder.newJob(Bootstrap.class)
				.withIdentity(bootstrapKey)
				.storeDurably(true)
				.usingJobData("count", 1)
				.build();
		
		Trigger bootstrapTrigger = TriggerBuilder
    			.newTrigger()
    			.withIdentity("BootStrap", "group1")
    			.withSchedule(
    			    SimpleScheduleBuilder.repeatSecondlyForTotalCount(5))
    			.build();
		Scheduler scheduler = new StdSchedulerFactory().getScheduler();
		//Listener attached to jobKey
    	scheduler.getListenerManager().addJobListener(
    		new HelloJobListener(), KeyMatcher.keyEquals(bootstrapKey)
    	);
    	scheduler.start();
    	scheduler.scheduleJob(bootstrapJob, bootstrapTrigger);
	}
	
	private static void createHeartbeat(){
		
	}
}
