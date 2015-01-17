package me.pedrazas.heartbeat.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class Bootstrap implements Job{
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("Boostraping instance");
	}
}
