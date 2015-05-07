package com.chinesedreamer.jira.biz.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chinesedreamer.jira.biz.utils.DateUtil;
import com.chinesedreamer.jira.biz.vo.ProjectReportVo;
import com.chinesedreamer.jira.biz.vo.TimeScopeReportVo;
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
	@Scheduled(cron= "0 0 0  * *  MON-FRI ")
	@Override
	public void sysncJiraIssue() throws JiraException{
		logger.info("********* start daily sync issue task.");
		List<JiraProject> jiraProjects = this.jiraProjectService.getAllProjects();
		for (JiraProject jiraProject : jiraProjects) {
			this.jiraSyncService.syncProjectIssue(jiraProject.getKey());
		}
		logger.info("********* start daily sync issue end.");
	}

	@Scheduled(cron= "0 15 8  * *  MON-FRI ")
	@Override
	public void dailyReport() throws JiraException {
		logger.info("********* daily report task start.");
		List<JiraRptVersionConfig> configs = this.jiraRptVersionConfigService.getAll();
		List<ProjectReportVo> reports = new ArrayList<ProjectReportVo>();
		for (JiraRptVersionConfig config : configs) {
			ProjectReportVo vo = jiraReportService.generateProjectReport(config.getProjectJiraId(), config.getVersionJiraId());
			reports.add(vo);
		}
		Map<String, List<ProjectReportVo>> datasource = new HashMap<String, List<ProjectReportVo>>();
		datasource.put("reports", reports);
		
		EmailRecipient recipient = new EmailRecipient();
		recipient.setTo(new String[]{"taosj@cyyun.com"});
		recipient.setCc(new String[]{"407414976@qq.com"});
		String templatePath = "project-report-email-velocity-template.vm";
		this.ipmEmailSender.sendTemplateEmail("paris1989@163.com", recipient, "Daily Project Report", templatePath, datasource);
		
		logger.info("********* daily report task end.");
	}

	@Scheduled(cron= "0 0 17  * * FRI ")
	@Override
	public void weeklyReport() throws JiraException {
		logger.info("********* weekly report task start.");
		Date date = new Date();
		TimeScopeReportVo scopReport = this.jiraReportService.generateTimeScopeReport(
				DateUtil.calculateDate(date, -5), 
				DateUtil.calculateDate(date, 0));
		
		EmailRecipient recipient = new EmailRecipient();
		recipient.setTo(new String[]{"taosj@cyyun.com"});
		recipient.setCc(new String[]{"407414976@qq.com"});
		String templatePath = "timescope-report-email-velocity-template.vm";
		this.ipmEmailSender.sendTemplateEmail("paris1989@163.com", recipient, "Weekly Project Report", templatePath, scopReport);
		
		logger.info("********* weekly report task end.");
	}
	
	@Scheduled(cron= "0 15 8  L * ? ")
	@Override
	public void monthlyReport() throws JiraException {
		logger.info("********* weekly report task start.");
		Date date = new Date();
		TimeScopeReportVo scopReport = this.jiraReportService.generateTimeScopeReport(
				DateUtil.calculateDate(date, -35), 
				DateUtil.calculateDate(date, 0));
		
		EmailRecipient recipient = new EmailRecipient();
		recipient.setTo(new String[]{"taosj@cyyun.com"});
		recipient.setCc(new String[]{"407414976@qq.com"});
		String templatePath = "timescope-report-email-velocity-template.vm";
		this.ipmEmailSender.sendTemplateEmail("paris1989@163.com", recipient, "Monthly Project Report", templatePath, scopReport);
		
		logger.info("********* weekly report task end.");
	}
	
}
