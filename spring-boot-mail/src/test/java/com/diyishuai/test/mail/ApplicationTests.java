package com.diyishuai.test.mail;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeMessage;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = MailApplication.class)
public class ApplicationTests {

	@Autowired
	private JavaMailSender mailSender;

	@Test
	public void sendSimpleMail() throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("dashboard@centrixlink.com");
		message.setTo("shuai.zhu@centrixlink.com");
		message.setSubject("主题：简单邮件");
		message.setText("测试邮件内容");

		mailSender.send(message);
	}

//	@Test
//	public void sendTemplateMail() throws Exception {
//
//		MimeMessage mimeMessage = mailSender.createMimeMessage();
//
//		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
//		helper.setFrom("dyc87112@qq.com");
//		helper.setTo("dyc87112@qq.com");
//		helper.setSubject("主题：模板邮件");
//
//		Map<String, Object> model = new HashedMap();
//		model.put("username", "didi");
//		String text = FreeMarkerTemplateUtils.processTemplateIntoString("template.vm",  model);
//		helper.setText(text, true);
//
//		mailSender.send(mimeMessage);
//	}

}