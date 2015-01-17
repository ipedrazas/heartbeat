package me.pedrazas.heartbeat.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements Job {

	
	public static final String EXECUTION_COUNT = "count";
	private static Logger log = LoggerFactory.getLogger(HelloJob.class);

	public void execute(JobExecutionContext context) throws JobExecutionException {
			System.out.println("Hello Quartz!");
	}

}
