package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import org.quartz.*;

public class Submission implements Job{
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		try {
			Thread.sleep(5000);
			System.out.println("Submission Created....");
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
        	String query="update batchProcessTask_1 set next_job ="+"'Quote Process'"+"where job_name='"+context.getJobDetail().getKey()+"';";
            Statement st=conection.createStatement();
			int row=st.executeUpdate(query);

			try {
				JobDetail quote= JobBuilder.newJob(Quote.class).withIdentity("Quote","QuoteTrigger")
					.build();

				Trigger quoteTrigger = TriggerBuilder.newTrigger().withIdentity("CrownTrigger", "QuoteTrigger")
					.build();
				
				scheduler.scheduleJob(quote,quoteTrigger);
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
	public static void repeatJob(JobExecutionContext context) throws JobExecutionException {
        try {
            // Create a new trigger for the next execution
        	Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CrownTrigger", "SubmissionTrigger")
    				.startNow().build(); 

            // Reschedule the job with the new trigger
            context.getScheduler().rescheduleJob(context.getTrigger().getKey(), trigger);
        } catch (SchedulerException e) {
            throw new JobExecutionException("Error rescheduling job", e);
        }
    }

	

}