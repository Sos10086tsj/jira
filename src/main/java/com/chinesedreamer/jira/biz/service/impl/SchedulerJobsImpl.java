package com.chinesedreamer.jira.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chinesedreamer.jira.biz.core.project.model.JiraProject;
import com.chinesedreamer.jira.biz.core.project.service.JiraProjectService;
import com.chinesedreamer.jira.biz.rptconfig.model.JiraRptVersionConfig;
import com.chinesedreamer.jira.biz.rptconfig.service.JiraRptVersionConfigService;
import com.chinesedreamer.jira.biz.service.JiraReportService;
import com.chinesedreamer.jira.biz.service.JiraSyncService;
import com.chinesedreamer.jira.biz.service.SchedulerJobs;
import com.chinesedreamer.jira.biz.vo.ProjectReportVo;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.email.message.EmailRecipient;
import com.chinesedreamer.jira.email.service.IpmEmailSender;

/**
 * Description: 
 * @author Paris
 * @date May 6, 201510:53:30 AM
 * @version beta
 */
@Component
public class SchedulerJobsImpl implements SchedulerJobs{
	private Logger logger = LoggerFactory.getLogger(SchedulerJobsImpl.class);
	@Resource
	private JiraSyncService jiraSyncService;
	@Resource
	private JiraProjectService jiraProjectService;
	@Resource
	private JiraReportService jiraReportService;
	@Resource
	private JiraRptVersionConfigService jiraRptVersionConfigService;
	@Resource
	private IpmEmailSender ipmEmailSender;
	
	//@Scheduled(cron= "0 0 0  * * ? ")
	@Scheduled(cron= "0 0 0  * * ? ")
	@Override
	public void sysncJiraIssue() throws JiraException{
		logger.info("********* start daily sync issue task.");
		List<JiraProject> jiraProjects = this.jiraProjectService.getAllProjects();
		for (JiraProject jiraProject : jiraProjects) {
			this.jiraSyncService.syncProjectIssue(jiraProject.getKey());
		}
		logger.info("********* start daily sync issue end.");
	}

	@Scheduled(cron= "0 15 8  * * ? ")
	@Override
	public void dailyReport() throws JiraException {
		logger.info("********* daily report task start.");
		List<JiraRptVersionConfig> configs = this.jiraRptVersionConfigService.getAll();
		for (JiraRptVersionConfig config : configs) {
			ProjectReportVo vo = jiraReportService.generateProjectReport(config.getProjectJiraId(), config.getVersionJiraId());
			EmailRecipient recipient = new EmailRecipient();
			recipient.setTo(new String[]{"taosj@cyyun.com"});
			recipient.setCc(new String[]{"407414976@qq.com"});
			String templatePath = "project-report-email-velocity-template.vm";
			this.ipmEmailSender.sendTemplateEmail("paris1989@163.com", recipient, "Test Project", templatePath, vo);
		}
		logger.info("********* daily report task end.");
	}
}
