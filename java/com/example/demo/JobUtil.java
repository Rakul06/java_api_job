package com.example.demo;

//JobUtil.java
import org.quartz.*;

public class JobUtil {

 public static JobDetail createJob(Class<? extends Job> jobClass, String jobName) {
     return JobBuilder.newJob(jobClass)
             .withIdentity(jobName)
             .storeDurably()
             .build();
 }

 public static Trigger createTrigger(String triggerName) {
     return TriggerBuilder.newTrigger()
             .withIdentity(triggerName)
             .startNow()
             .build();
 }
}
