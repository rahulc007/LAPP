package com.svarks.lapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.svarks.lapp.order.common.OrderMarkingConstants;
import com.svarks.lapp.scheduler.service.FTPCronService;
import com.svarks.lapp.scheduler.service.SAPOrderCronJobService;

@SpringBootApplication
@ComponentScan(OrderMarkingConstants.BASE_PACKAGE_NAME)
@EntityScan(OrderMarkingConstants.ENTITY_PACKAGE)
@EnableJpaRepositories(OrderMarkingConstants.BASE_PACKAGE_NAME)
@EnableScheduling
public class LappOrderMarkingServiceApplication extends SpringBootServletInitializer   {
	
	
	
	@Autowired
	SAPOrderCronJobService sapFileUploadCron;
	
	@Autowired
	FTPCronService ftpService;

	public static void main(String[] args) {
		SpringApplication.run(LappOrderMarkingServiceApplication.class, args);
	}

	
	@Scheduled(cron = "0 0/5 * * * ?")  // executes every one minute
	//@Scheduled(cron = "0 * * * * *")  // executes every one minute
	public void run() {
		sapFileUploadCron.executeJob();
	}
	
	@Scheduled(cron = "0 0 10 * * *")  // executes every one minute
	//@Scheduled(cron = "0 * * * * *")  // executes every one minute
	public void ftpJob() {
		ftpService.executeFTPOperation();
	}
	
	@Override
  	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
       }

/*	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(env.getProperty("spring.mail.host"));
	    mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
	     
	    mailSender.setUsername(env.getProperty("spring.mail.username"));
	    mailSender.setPassword(env.getProperty("spring.mail.password"));
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    
	    // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(env.getProperty("spring.mail.username"), env.getProperty("spring.mail.password"));
            }
        };

        Session session = Session.getInstance(props, auth);
        mailSender.setSession(session);

        return mailSender;
	}*/
}
