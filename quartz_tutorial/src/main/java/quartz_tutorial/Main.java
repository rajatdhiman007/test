package quartz_tutorial;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Main {

	
	public static void main(String[] args) {
		JobDetail job1 = JobBuilder.newJob(SimpleJob.class)
				.withIdentity("MasterJob", "Quartz").build();

		
		//Every day night condition.
		Trigger trigger1 = TriggerBuilder
				.newTrigger()
				.withIdentity("MasterJob", "Quartz")
				.withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?")).build();
		
				//0 0/1 8-19 * * ?
		try {
			StdSchedulerFactory.getDefaultScheduler().scheduleJob(job1, trigger1);
			StdSchedulerFactory.getDefaultScheduler().start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
