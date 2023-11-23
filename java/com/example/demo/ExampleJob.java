package com.example.demo;

//ExampleJob.java
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class ExampleJob implements Job {
 @Override
 public void execute(JobExecutionContext context) throws JobExecutionException {
     System.out.println("Executing the job!");
     // Your job logic goes here
 }
}
