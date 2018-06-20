package com.diyishuai.test.mail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruce
 * @since 2018/6/20
 */
@SpringBootApplication
public class MailApplication implements ApplicationRunner {

    private static Logger log = LoggerFactory.getLogger(MailApplication.class);

    @Autowired
    private EmailService emailService;


    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class,args);

    }
    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        log.info("Sending Email with Thymeleaf HTML Template Example");

        Mail mail = new Mail();
        mail.setFrom("dashboard@centrixlink.com");
        mail.setTo("350108081@qq.com");
        mail.setSubject("Sending Email with Thymeleaf HTML Template Example");

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("name", "Memorynotfound.com");
        model.put("location", "Belgium");
        model.put("signature", "http://memorynotfound.com");
        mail.setModel(model);

        emailService.sendSimpleMessage(mail);
    }

}
