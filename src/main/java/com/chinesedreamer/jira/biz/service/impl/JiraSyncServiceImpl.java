package com.chinesedreamer.jira.biz.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.core.issuetype.logic.JiraIssueTypeLogic;
import com.chinesedreamer.jira.biz.core.issuetype.model.JiraIssueType;
import com.chinesedreamer.jira.biz.core.priority.logic.JiraPriorityLogic;
import com.chinesedreamer.jira.biz.core.priority.model.JiraPriority;
import com.chinesedreamer.jira.biz.core.project.logic.JiraProjectLogic;
import com.chinesedreamer.jira.biz.core.project.model.JiraProject;
import com.chinesedreamer.jira.biz.core.status.logic.JiraStatusLogic;
import com.chinesedreamer.jira.biz.core.status.model.JiraStatus;
import com.chinesedreamer.jira.biz.core.user.logic.JiraUserLogic;
import com.chinesedreamer.jira.biz.core.user.model.JiraUser;
import com.chinesedreamer.jira.biz.core.version.logic.JiraVersionLogic;
import com.chinesedreamer.jira.biz.core.version.model.JiraVersion;
import com.chinesedreamer.jira.biz.service.JiraSyncService;
import com.chinesedreamer.jira.biz.sysconfig.constant.SysConfigConstant;
import com.chinesedreamer.jira.biz.sysconfig.logic.SysConfigLogic;
import com.chinesedreamer.jira.biz.sysconfig.model.SysConfig;
import com.chinesedreamer.jira.core.BasicCredentials;
import com.chinesedreamer.jira.core.IssueType;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Priority;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.Status;
import com.chinesedreamer.jira.core.User;
import com.chinesedreamer.jira.core.Version;
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
	@Resource
	private JiraIssueTypeLogic jiraIssueTypeLogic;
	@Resource
	private JiraStatusLogic jiraStatusLogic;
	@Resource
	private JiraVersionLogic jiraVersionLogic;
	
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
		jiraUser.setActive(user.isActive());
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

	@Override
	public void syncIssueType() throws JiraException {
		logger.info("********** sync issue type begin");
		SysConfig config = this.sysConfigLogic.findByProperty(SysConfigConstant.SYNC_ISSUE_TYPE_IDS);
		if (null == config) {
			logger.info("********** missing config of issue type configuration. Key:{}",SysConfigConstant.SYNC_ISSUE_TYPE_IDS);
			return;
		}
		String[] issueTypeIds = config.getPropertyValue().split(",");
		for (String id : issueTypeIds) {
			IssueType issueType = IssueType.get(this.initJiraClient().getRestClient(), id);
			JiraIssueType jiraIssueType = this.jiraIssueTypeLogic.findByJiraId(issueType.getId());
			if (null == jiraIssueType) {
				jiraIssueType = new JiraIssueType();
			}
			jiraIssueType.setJiraId(issueType.getId());
			jiraIssueType.setSelf(issueType.getSelf());
			jiraIssueType.setName(issueType.getName());
			jiraIssueType.setDescription(issueType.getDescription());
			logger.info("********** update issue type:{}", issueType.getName());
			this.jiraIssueTypeLogic.save(jiraIssueType);
		}
		logger.info("********** sync issue type end");
	}

	@Override
	public void syncStatus() throws JiraException {
		logger.info("********** sync issue status begin");
		SysConfig config = this.sysConfigLogic.findByProperty(SysConfigConstant.SYNC_ISSUE_STATUS_IDS);
		if (null == config) {
			logger.info("********** missing config of issue status configuration. Key:{}",SysConfigConstant.SYNC_ISSUE_STATUS_IDS);
			return;
		}
		String[] issueStatusIds = config.getPropertyValue().split(",");
		for (String id : issueStatusIds) {
			Status status = Status.get(this.initJiraClient().getRestClient(), id);
			JiraStatus jiraStatus = this.jiraStatusLogic.findByJiraId(status.getId());
			if (null == jiraStatus) {
				jiraStatus = new JiraStatus();
			}
			jiraStatus.setJiraId(status.getId());
			jiraStatus.setSelf(status.getSelf());
			jiraStatus.setName(status.getName());
			jiraStatus.setDescription(status.getDescription());
			logger.info("********** update status type:{}", status.getName());
			this.jiraStatusLogic.save(jiraStatus);
		}
		logger.info("********** sync issue status end");
	}

	@Override
	public void syncProjectVersion(String projectIdOrKey) throws JiraException {
		logger.info("********** sync project version begin");
		Project project = this.initJiraClient().getProject(projectIdOrKey);
		if (null == project) {
			logger.info("********** project:{} not exists",project);
			return;
		}
		List<Version> versions = project.getVersions();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		for (Version version : versions) {
			JiraVersion jiraVersion = this.jiraVersionLogic.findByJiraId(version.getId());
			if (null == jiraVersion) {
				jiraVersion = new JiraVersion();
			}
			jiraVersion.setName(version.getName());
			jiraVersion.setDescription(version.getDescription());
			if (StringUtils.isNotEmpty(version.getReleaseDate())) {
				try {
					jiraVersion.setReleaseDate(format.parse(version.getReleaseDate()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			jiraVersion.setJiraId(version.getId());
			jiraVersion.setSelf(version.getSelf());
			jiraVersion.setProjectJiraId(project.getId());
			logger.info("********** update roject version:{}", version.getName());
			this.jiraVersionLogic.save(jiraVersion);
		}
		logger.info("********** sync project version end");
	}

	
}
