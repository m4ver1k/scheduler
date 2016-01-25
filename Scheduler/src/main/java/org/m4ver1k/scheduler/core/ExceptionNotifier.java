package org.m4ver1k.scheduler.core;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionNotifier {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Value("${notify.to.emailid}")
	private String emailId;
	
	@AfterThrowing(value="execution(* addJob(..))",throwing="e")
	public void sentNotificationMail(Exception e){
		MimeMessage message =  this.mailSender.createMimeMessage();
		MimeMessageHelper mimeHelper;
		try {
			mimeHelper = new MimeMessageHelper(message,true);
			mimeHelper.setTo(emailId);
			mimeHelper.setFrom("m4ver1k.a@gmail.com");
			mimeHelper.setSubject("Exception Scheduling Job");
			//should be using velocity templates for more elegant email.
			mimeHelper.setText("<html><body>hi,<br/> Exception occured while running job find details below </br> "+e.getMessage()+" </body></html>",true);
			mailSender.send(message);
		} catch (MessagingException ex) {
			System.out.println("Error Sending email "+ ex.getMessage());
		}
		
	}
	
}
