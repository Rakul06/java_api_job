package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

public class JobListener_ implements JobListener{

	public String getName() {
		return "JobLogger";
	}

	public void jobToBeExecuted(JobExecutionContext context) {
		try {
			Connection conection = JDBC_Connection.JDBC_connection();
			String query = "insert into batchProcessTask_1 (job_name,job_started) values "+"('"+context.getJobDetail().getKey()+"','"+"yes')";
			Statement st=conection.createStatement();
			int row=st.executeUpdate(query);
	        System.out.println("Job currently executing: " + context.getJobDetail().getKey());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void jobExecutionVetoed(JobExecutionContext context) {
        System.out.println("Job is about to be executed: " + context.getJobDetail().getKey());

	}

	public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        try {
        	Connection conection = JDBC_Connection.JDBC_connection();
        	if(jobException == null) {
                System.out.println("Job is successfully executed: " + context.getJobDetail().getKey());
                String query="update batchProcessTask_1 set job_exe_success ="+"'yes'"+"where job_name='"+context.getJobDetail().getKey()+"';";
                Statement st=conection.createStatement();
    			int row=st.executeUpdate(query);
    			System.out.println("-----------------------------------------------------------------------------------------------------------");
            }
            else {
            	try {
            		String query="update batchProcessTask_1 set job_exe_failure ="+"'yes'"+"where job_name='"+context.getJobDetail().getKey()+"';";
                    Statement st=conection.createStatement();
        			int row=st.executeUpdate(query);
        			System.out.print(row +"updated...");
    	        	System.out.println("Job Execution is failed: "+context.getJobDetail().getKey());
    	        	//If there is any exception in the job therefore the job will executed untill the job successfully executed
    				Submission.repeatJob(context);
    			} catch (JobExecutionException e) {
    				e.printStackTrace();
    	        	System.out.println("Job Execution is failed: "+context.getJobDetail().getKey());
    			}
            	
            }
        }
        catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
