package org.m4ver1k.scheduler.web;

import java.util.List;

import org.m4ver1k.scheduler.core.FrequencyType;
import org.m4ver1k.scheduler.core.JobDTO;
import org.m4ver1k.scheduler.service.JobService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/api/job")
public class JobEndpoint {

	@Autowired
	private JobService jobService;

	@RequestMapping(method=RequestMethod.POST)
	public String addJob(@RequestParam("name") String name,@RequestParam("cmd") String command,@RequestParam("dir") String directory,
			@RequestParam("feqType") FrequencyType feqType,@RequestParam("feqvalue") String feqValues){
		
		
			try {
				jobService.addJob(name, command, directory, feqType, feqValues);
			} catch (SchedulerException e) {
				System.out.println("JobEndpoint.addJob() - Ignoring this job and showing listing page email sent with details.");
			}
		
		return "redirect:/history.html";
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody List<JobDTO> getAllJobs() throws SchedulerException{
		return jobService.listAllJobs();
	}
}
