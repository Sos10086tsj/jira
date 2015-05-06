package com.chinesedreamer.jira.email.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.chinesedreamer.jira.email.message.EmailRecipient;
import com.chinesedreamer.jira.email.util.IpmEmailConfig;

@Service
public class IpmEmailSenderImpl implements IpmEmailSender{
	private static Logger logger = LoggerFactory.getLogger(IpmEmailSenderImpl.class);
	@Autowired
	private VelocityEngine velocityEngine;

	@Override
	public void sendEmail(String from, EmailRecipient recipient,
			String subject, String content) {
		logger.info("begin sendEmail...");
		Properties configProp = new Properties();
		try {
			configProp = IpmEmailConfig.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(configProp.getProperty("system.email.server.host"));
		sender.setUsername(configProp.getProperty("system.email.to.username"));
		sender.setPassword(configProp.getProperty("system.email.to.password"));
			
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(from);
		mailMessage.setTo(recipient.getTo());
		mailMessage.setCc(recipient.getCc());
		mailMessage.setBcc(recipient.getBcc());
		mailMessage.setSubject(subject);
		mailMessage.setText(content);
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.timeout", configProp.getProperty("system.email.timeout"));
		
		sender.setJavaMailProperties(prop);
		
		sender.send(mailMessage);
		
		logger.info("sendEmail success! Sent date: {}, subject:{}", new Date(), subject);
	}

	@Override
	public void sendHtmlEmail(String from, EmailRecipient recipient,
			String subject, String content) {
		logger.info("begin sendHtmlEmail...");
		Properties configProp = new Properties();
		try {
			configProp = IpmEmailConfig.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(configProp.getProperty("system.email.server.host"));
		sender.setUsername(configProp.getProperty("system.email.to.username"));
		sender.setPassword(configProp.getProperty("system.email.to.password"));
			
		MimeMessage mailMessage = sender.createMimeMessage();		
		MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
		try {
			messageHelper.setTo(recipient.getTo());
			messageHelper.setFrom(from);
			messageHelper.setCc(recipient.getCc());
			messageHelper.setBcc(recipient.getBcc());
			messageHelper.setSubject(subject);
			messageHelper.setText(content, true);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.timeout", configProp.getProperty("system.email.timeout"));
		
		sender.setJavaMailProperties(prop);
		
		sender.send(mailMessage);
		
		logger.info("sendHtmlEmail success! Sent date: {}, subject:{}", new Date(), subject);
	}

	@Override
	public void sendCaptureEmail(String from, EmailRecipient recipient,
			String subject, String content, String inline,String capture) {
		logger.info("begin sendCaptureEmail...");
		
		
		Properties configProp = new Properties();
		try {
			configProp = IpmEmailConfig.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(configProp.getProperty("system.email.server.host"));
		sender.setUsername(configProp.getProperty("system.email.to.username"));
		sender.setPassword(configProp.getProperty("system.email.to.password"));
			
		MimeMessage mailMessage = sender.createMimeMessage();		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true);
			messageHelper.setTo(recipient.getTo());
			messageHelper.setFrom(from);
			messageHelper.setCc(recipient.getCc());
			messageHelper.setBcc(recipient.getBcc());
			messageHelper.setSubject(subject);
			messageHelper.setText(content, true);

			FileSystemResource img = new FileSystemResource(new File(capture));
			messageHelper.addInline(inline, img);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.timeout", configProp.getProperty("system.email.timeout"));
		
		sender.setJavaMailProperties(prop);
		
		sender.send(mailMessage);
		
		logger.info("sendCaptureEmail success! Sent date: {}, subject:{}", new Date(), subject);
	}

	@Override
	public void sendAttachEmail(String from, EmailRecipient recipient,
			String subject, String content, String attachPath) {
		logger.info("begin sendAttachEmail...");
		
		
		Properties configProp = new Properties();
		try {
			configProp = IpmEmailConfig.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(configProp.getProperty("system.email.server.host"));
		sender.setUsername(configProp.getProperty("system.email.to.username"));
		sender.setPassword(configProp.getProperty("system.email.to.password"));
			
		MimeMessage mailMessage = sender.createMimeMessage();		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
			messageHelper.setTo(recipient.getTo());
			messageHelper.setFrom(from);
			messageHelper.setCc(recipient.getCc());
			messageHelper.setBcc(recipient.getBcc());
			messageHelper.setSubject(subject);
			messageHelper.setText(content, true);

			FileSystemResource attac = new FileSystemResource(new File(attachPath));
			int position = attachPath.lastIndexOf("/");
			messageHelper.addAttachment(attachPath.substring(position), attac);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.timeout", configProp.getProperty("system.email.timeout"));
		
		sender.setJavaMailProperties(prop);
		
		sender.send(mailMessage);
		
		logger.info("sendAttachEmail success! Sent date: {}, subject:{}", new Date(), subject);
	}

	@Override
	public void sendTemplateEmail(String from, EmailRecipient recipient,
			String subject, String templatePath, Object obj) {
		logger.info("begin sendTemplateEmail...");
		Properties configProp = new Properties();
		try {
			configProp = IpmEmailConfig.loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(configProp.getProperty("system.email.server.host"));
		sender.setUsername(configProp.getProperty("system.email.to.username"));
		sender.setPassword(configProp.getProperty("system.email.to.password"));
			
		MimeMessage mailMessage = sender.createMimeMessage();		
		try {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
			messageHelper.setTo(recipient.getTo());
			messageHelper.setFrom(from);
			messageHelper.setCc(recipient.getCc());
			messageHelper.setBcc(recipient.getBcc());
			messageHelper.setSubject(subject);
			Map<String,Object> model = new HashMap<String,Object>();
			model.put("template", obj);
			String text = VelocityEngineUtils.mergeTemplateIntoString(this.velocityEngine, templatePath, "UTF-8", model);
			messageHelper.setText(text, true);

		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.timeout", configProp.getProperty("system.email.timeout"));
		
		sender.setJavaMailProperties(prop);
		
		sender.send(mailMessage);
		logger.info("sendTemplateEmail success! Sent date: {}, subject:{}", new Date(), subject);
	}
}
