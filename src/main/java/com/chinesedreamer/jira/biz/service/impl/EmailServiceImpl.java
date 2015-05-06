package com.chinesedreamer.jira.biz.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.service.EmailService;
import com.chinesedreamer.jira.email.message.EmailRecipient;
import com.chinesedreamer.jira.email.service.IpmEmailSender;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20152:43:58 PM
 * @version beta
 */
@Service
public class EmailServiceImpl implements EmailService{
	
	@Resource
	private IpmEmailSender sender;

	@Override
	public void sendEmail() {
		EmailRecipient recipient = new EmailRecipient();
		recipient.setTo(new String[]{"taosj@cyyun.com"});
		recipient.setCc(new String[]{"407414976@qq.com"});
		this.sender.sendHtmlEmail("paris1989@163.com", recipient, "Test spring email", "<html><head></head><body><h1>This is a test email of spring html mail sender!</h1></body></html>");
	}

}
