package com.example.demo;

//JobController.java
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




//make this class as a handler for handling requests
@RestController

//defining the api using 
@RequestMapping("/api/job")
public class JobController {

 private final Scheduler scheduler;

 @Autowired
 public JobController(Scheduler scheduler) {
     this.scheduler = scheduler;
 }

 @PostMapping("/start")
 public String startJob() throws Exception {
	 scheduler.start();
	 JobListener JobLogger=new JobListener_();
		
	 scheduler.getListenerManager().addJobListener(JobLogger);
	 JobDetail submission= JobBuilder.newJob(Submission.class).withIdentity("Submission","SubmissionTrigger")
				.build();
		
		Trigger submissionTrigger = TriggerBuilder.newTrigger().withIdentity("CrownTrigger", "SubmissionTrigger")
				.startNow().build();
     // Schedule the job as needed
     // For simplicity, using a TriggerBuilder to schedule the job immediately
     scheduler.scheduleJob(submission,submissionTrigger);
     return "Job Started....";
 }
 
 @PostMapping("/stop")
 public String stopJob() throws Exception {
	scheduler.shutdown();
	return "Job execution is stoped successfully";
	 
 }
}

