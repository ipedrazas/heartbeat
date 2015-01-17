package me.pedrazas.heartbeat.jobs.listeners;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapJobListener implements JobListener{
	
	private static Logger log = LoggerFactory.getLogger(BootstrapJobListener.class);

	
	public static final String LISTENER_NAME = "BootstrapJobListenerName";
	

	public String getName() {
		return LISTENER_NAME; //must return a name
	}
 
	// Run this if job is about to be executed.
	public void jobToBeExecuted(JobExecutionContext context) {
		try{
			int count = 0;
			Date d = new Date();
			JobKey key = context.getJobDetail().getKey();
			JobDetail jobDetail = context.getScheduler().getJobDetail(key);
			if(jobDetail.getJobDataMap().containsKey("count")){
				count = jobDetail.getJobDataMap().getInt("count"); 
			}
			log.info(count  + " jobToBeExecuted " + key.toString() + " - " + d.toString());
			jobDetail.getJobDataMap().put("count", ++count);
			context.getScheduler().addJob(jobDetail, true);

		}catch(Exception e){
			e.printStackTrace();
		}	
	}
 
//	private void reschedule(JobExecutionContext context) throws SchedulerException{		
//		Trigger trigger = TriggerBuilder
//				.newTrigger()
//				.withIdentity("dummyTriggerName", "group1")
//				.withSchedule(
//					CronScheduleBuilder.cronSchedule("0/3 * * * * ?"))
//				.build();
//		
//		 context.getScheduler().rescheduleJob(context.getTrigger().getKey(), trigger);
//	}
//
//	private void debug(){
//		Scheduler scheduler;
//		try {
//			scheduler = new StdSchedulerFactory().getScheduler();
//			for (String groupName : scheduler.getJobGroupNames()) {
//				 
//			     for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
//			 
//				  String jobName = jobKey.getName();
//				  String jobGroup = jobKey.getGroup();
//			 
//				  //get job's trigger
//				  List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
//				  Date nextFireTime = triggers.get(0).getNextFireTime(); 
//			 
//					System.out.println("[jobName] : " + jobName + " [groupName] : "
//						+ jobGroup + " - " + nextFireTime);
//			 
//				  }
//			 
//			    }
//		} catch (SchedulerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}	   
//	}
	
	
	//Run this after job has been executed
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		
		log.info("jobWasExecuted"); 
		String jobName = context.getJobDetail().getKey().toString();		
		log.info("Job : " + jobName + " is finished... "); 
		if (!jobException.getMessage().equals("")) {
			log.error("Exception thrown by: " + jobName
				+ " Exception: " + jobException.getMessage());
		}		
	}
	
	

	public void jobExecutionVetoed(JobExecutionContext arg0) {		
		
	}
}
