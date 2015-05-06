package com.chinesedreamer.jira.email.service;

import com.chinesedreamer.jira.email.message.EmailRecipient;

public interface IpmEmailSender {
	public void sendEmail(String from, EmailRecipient recipient, String subject, String content);
	
	public void sendHtmlEmail(String from, EmailRecipient recipient, String subject, String content);
	
	public void sendCaptureEmail(String from, EmailRecipient recipient, String subject, String content, String inline,String capture);
	
	public void sendAttachEmail(String from, EmailRecipient recipient, String subject, String content, String attachPath);
	
}
