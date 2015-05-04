package com.chinesedreamer.jira.biz.service.impl;

import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.core.priority.logic.JiraPriorityLogic;
import com.chinesedreamer.jira.biz.core.priority.model.JiraPriority;
import com.chinesedreamer.jira.biz.core.project.logic.JiraProjectLogic;
import com.chinesedreamer.jira.biz.core.project.model.JiraProject;
import com.chinesedreamer.jira.biz.core.user.logic.JiraUserLogic;
import com.chinesedreamer.jira.biz.core.user.model.JiraUser;
import com.chinesedreamer.jira.biz.service.JiraSyncService;
import com.chinesedreamer.jira.biz.sysconfig.constant.SysConfigConstant;
import com.chinesedreamer.jira.biz.sysconfig.logic.SysConfigLogic;
import com.chinesedreamer.jira.biz.sysconfig.model.SysConfig;
import com.chinesedreamer.jira.core.BasicCredentials;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Priority;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.User;
import com.chinesedreamer.jira.reader.PropertiesReader;

/**
 * Description: 
 * @author Paris
 * @date May 4, 20154:53:23 PM
 * @version beta
 */
@Service
public class JiraSyncServiceImpl implements JiraSyncService{
	private Logger logger = LoggerFactory.getLogger(JiraSyncServiceImpl.class);
	
	@Resource
	private JiraUserLogic jiraUserLogic;
	@Resource
	private JiraProjectLogic jiraProjectLogic;
	@Resource
	private SysConfigLogic sysConfigLogic;
	@Resource
	private JiraPriorityLogic jiraPriorityLogic;
	
	private JiraClient jiraClient;
	private BasicCredentials creds;
	private JiraClient initJiraClient(){
		if (null == this.jiraClient) {
			Properties prop = PropertiesReader.loadProperties();
			String url = prop.getProperty("jira.connection.url");
			
			jiraClient = new JiraClient(url,this.initBasicCredentials());
		}
		return jiraClient;
	}
	
	private BasicCredentials initBasicCredentials() {
		if (null == this.creds) {
			Properties prop = PropertiesReader.loadProperties();
			String username = prop.getProperty("jira.connection.username");
			String password = prop.getProperty("jira.connection.password");
			this.creds = new BasicCredentials(username, password);
		}
		return creds;
	}

	@Override
	public void syncUser(String username) throws JiraException {
		logger.info("********** sync user begin:{}",username);
		User user = User.get(this.initJiraClient().getRestClient(), username);
		JiraUser jiraUser = this.jiraUserLogic.findByUsername(username);
		if (null == jiraUser) {
			jiraUser = new JiraUser();
		}
		jiraUser.setUsername(username);
		jiraUser.setAcitve(user.isActive());
		jiraUser.setName(user.getName());
		jiraUser.setDisplayName(user.getDisplayName());
		jiraUser.setEmail(user.getEmail());
		jiraUser.setJiraId(user.getId());
		jiraUser.setSelf(user.getSelf());
		this.jiraUserLogic.save(jiraUser);
		logger.info("********** sync user end:{}",username);
	}

	@Override
	public void syncProject(String projectIdOrKey) throws JiraException {
		logger.info("********** sync project begin:{}",projectIdOrKey);
		Project project = Project.get(this.initJiraClient().getRestClient(), projectIdOrKey);
		JiraProject jiraProject = this.jiraProjectLogic.findByKey(project.getKey());
		if (null == jiraProject) {
			jiraProject = new JiraProject();
		}
		jiraProject.setJiraId(project.getId());
		jiraProject.setSelf(project.getSelf());
		jiraProject.setKey(project.getKey());
		jiraProject.setName(project.getName());
		jiraProject.setDescription(project.getDescription());
		this.jiraProjectLogic.save(jiraProject);
		logger.info("********** sync project end:{}",projectIdOrKey);
	}

	@Override
	public void syncPriority() throws JiraException {
		logger.info("********** sync priority begin");
		SysConfig config = this.sysConfigLogic.findByProperty(SysConfigConstant.SYNC_PRIORITY_IDS);
		if (null == config) {
			logger.info("********** missing config of priority configuration. Key:{}",SysConfigConstant.SYNC_PRIORITY_IDS);
			return;
		}
		String[] priorityIds = config.getPropertyValue().split(",");
		for (String id : priorityIds) {
			Priority priority = Priority.get(this.initJiraClient().getRestClient(), id);
			JiraPriority jiraPriority = this.jiraPriorityLogic.findByJiraId(priority.getId());
			if (null == jiraPriority) {
				jiraPriority = new JiraPriority();
			}
			jiraPriority.setJiraId(priority.getId());
			jiraPriority.setSelf(priority.getSelf());
			jiraPriority.setName(priority.getName());
			logger.info("********** update priority:{}", priority.getName());
			this.jiraPriorityLogic.save(jiraPriority);
		}
		logger.info("********** sync priority end");
	}

	
}
