package quartz_tutorial;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class SimpleJob implements Job {

	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		System.out.println("start 1");
		 boolean conditionMet = true;
		 //condition met - trying to reschedule as it did not happened last night and started running immediately after system boot up.
		 if(conditionMet) {
			 System.out.println("Condition met.. current time is "+System.currentTimeMillis());
			 	
			 
			 Trigger newTrigger = TriggerBuilder
						.newTrigger()
						.withIdentity("MasterJob", "Quartz")
						.withSchedule(CronScheduleBuilder.cronSchedule("0/2 * * * * ?")).build();;
			    Trigger oldTrigger = context.getTrigger();
			    try {
			    	Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
					scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   
			    
			    conditionMet = false;
			    System.out.println("Condition met is false now");
			    
		 }else {
			 System.out.println("Reached simple job.. current time is "+System.currentTimeMillis());
			 Trigger newTrigger = TriggerBuilder
						.newTrigger()
						.withIdentity("MasterJob", "Quartz")
						.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();;
			    Trigger oldTrigger = context.getTrigger();
			    try {
			    	Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
					scheduler.rescheduleJob(oldTrigger.getKey(), newTrigger);
				} catch (SchedulerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 }
		
		
	   
//		if(!TestCpuUtilization.exceedConditionLimit()) {
//			System.out.println("This is a quartz job!");
//		}else {
//			System.out.println("Limit Exceeded");
//		}	
		 
		
	}
	
	

	
	

}
