package com.chinesedreamer.jira.biz.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chinesedreamer.jira.biz.core.project.model.JiraProject;
import com.chinesedreamer.jira.biz.core.project.service.JiraProjectService;
import com.chinesedreamer.jira.biz.service.JiraSyncService;
import com.chinesedreamer.jira.biz.service.SchedulerJobs;
import com.chinesedreamer.jira.core.JiraException;

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
}
