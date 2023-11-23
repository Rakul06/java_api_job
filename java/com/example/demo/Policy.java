package com.example.demo;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

public class Policy implements Job{

public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
			Thread.sleep(5000);
			//System.out.println("Policy Created..."+10/0);
			System.out.println("Policy Created...");
			System.out.println(new Date());
			//Submission.repeatJob(arg0);
			try {
				arg0.getScheduler().shutdown();
			} catch (SchedulerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}


}
