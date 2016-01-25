package org.m4ver1k.scheduler.service;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.m4ver1k.scheduler.core.FrequencyType;
import org.m4ver1k.scheduler.core.JobDTO;
import org.m4ver1k.scheduler.core.SimpleJob;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.stereotype.Service;

@Service
public class JobService {
	
	@Autowired
	private Scheduler scheduler;
	
	public void addJob(String name,String command,String directory,FrequencyType feqType,String feqValue) throws SchedulerException {
		
		MethodInvokingJobDetailFactoryBean jobDetailfactory = new MethodInvokingJobDetailFactoryBean();
		jobDetailfactory.setTargetObject(new SimpleJob(command,directory));
		jobDetailfactory.setTargetMethod("execute");
		jobDetailfactory.setName(name);
		jobDetailfactory.setConcurrent(false);
		try {
			jobDetailfactory.afterPropertiesSet();
		} catch (ClassNotFoundException | NoSuchMethodException e) {
			e.printStackTrace();
		}

		String cronExpression="* 0/30 * * * *";
		
		if(feqType.equals(FrequencyType.MIN)){
			cronExpression="0 0/"+feqValue+" * * * ?";
		}else if(feqType.equals(FrequencyType.HOUR)){
			cronExpression="0 0 0/"+feqValue+" * * ?";
		}else if(feqType.equals(FrequencyType.DAY)){
			cronExpression="0 0 0 * * ?";
		}else if(feqType.equals(FrequencyType.DAY_OF_MONTH)){
			cronExpression="0 0 0 "+feqValue+" * ?";
		}else if(feqType.equals(FrequencyType.DAY_OF_WEEK)){
			cronExpression="0 0 0 * * "+feqValue;
		}	
		
		Trigger trigger = newTrigger().
				withIdentity("tr-"+name,"group1")
				.startNow()
				.withSchedule(cronSchedule(cronExpression))
				.build();
		
		try {
			scheduler.scheduleJob((JobDetail)jobDetailfactory.getObject(), trigger);
		} catch (SchedulerException e) {
			System.out.println("JobService.addJob()  Job Exisit with name - "+name);
			throw e;
		}
	}
	
	public List<JobDTO> listAllJobs() throws SchedulerException{
		List<JobDTO> jobList  = new ArrayList<>();
		for (String groupName : scheduler.getJobGroupNames()) {
		     for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
						
			  String jobName = jobKey.getName();
			  String jobGroup = jobKey.getGroup();
						
			  @SuppressWarnings("unchecked")
			List<Trigger> triggers = (List<Trigger>) scheduler.getTriggersOfJob(jobKey);
			  Date nextFireTime = triggers.get(0).getNextFireTime(); 

				System.out.println("[jobName] : " + jobName + " [groupName] : "
					+ jobGroup + " - " + nextFireTime);
				
				jobList.add(new JobDTO(jobName, jobGroup, nextFireTime.toString()));

			  }

		    }
		return jobList;
	}
	
	
	
}
