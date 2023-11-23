package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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

public class Quote implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
			Thread.sleep(5000);
			System.out.println("Quote Created ....");
			System.out.println(new Date());
			//Submission.repeatJob(arg0);
			scheduleNextJob(arg0.getScheduler(),arg0);
			
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	

	private void scheduleNextJob(Scheduler scheduler,JobExecutionContext context) throws JobExecutionException{
		try {
        	Connection conection = JDBC_Connection.JDBC_connection();
        	String query="update batchProcessTask_1 set next_job ="+"'Policy Process'"+"where job_name='"+context.getJobDetail().getKey()+"';";
            Statement st=conection.createStatement();
			int row=st.executeUpdate(query);

			try {
				JobDetail policy= JobBuilder.newJob(Policy.class).withIdentity("Policy","PolicyTrigger")
					.build();

				Trigger policyTrigger = TriggerBuilder.newTrigger().withIdentity("CrownTrigger", "PolicyTrigger")
					.build();
				
				scheduler.scheduleJob(policy,policyTrigger);
			} 
			catch (SchedulerException e) {
				throw new JobExecutionException("Error scheduling next job", e);
			}
			
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		
	}

}