package org.m4ver1k.scheduler;

import java.util.Properties;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SchedulerConfig {

	@Bean
	public Scheduler getScheduler(){
		Scheduler scheduler = null;
		try {
			scheduler=org.quartz.impl.StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return scheduler;
	}
	
	@Bean
	public JavaMailSenderImpl getMailSender(){
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		//TODO: Update gmail details here for mails to be triggered.
		mailSender.setUsername("GMAIL-ID");
		mailSender.setPassword("GMAIL-PASS");
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable","true");
		mailSender.setJavaMailProperties(props);
		return mailSender;
		
	}
}
