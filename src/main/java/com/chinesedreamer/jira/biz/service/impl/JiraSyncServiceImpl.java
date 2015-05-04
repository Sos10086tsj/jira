package com.chinesedreamer.jira.biz.service.impl;

import java.net.URI;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Service;

import com.chinesedreamer.jira.biz.core.project.logic.JiraProjectLogic;
import com.chinesedreamer.jira.biz.core.project.model.JiraProject;
import com.chinesedreamer.jira.biz.core.user.logic.JiraUserLogic;
import com.chinesedreamer.jira.biz.core.user.model.JiraUser;
import com.chinesedreamer.jira.biz.service.JiraSyncService;
import com.chinesedreamer.jira.biz.sysconfig.logic.SysConfigLogic;
import com.chinesedreamer.jira.core.BasicCredentials;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.RestClient;
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
	
	@Resource
	private JiraUserLogic jiraUserLogic;
	@Resource
	private JiraProjectLogic jiraProjectLogic;
	@Resource
	private SysConfigLogic sysConfigLogic;
	
	private JiraClient jiraClient;
	private BasicCredentials creds;
	private DefaultHttpClient httpclient;
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
	
	private DefaultHttpClient initHttpClient() {
		if (null == this.httpclient) {
			httpclient = new DefaultHttpClient();
		}
		return httpclient;
	}

	@Override
	public void syncUser(String username) throws JiraException {
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
	}

	@Override
	public void syncProject(String projectIdOrKey) throws JiraException {
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
	}

	@Override
	public void syncPriority() throws JiraException {
		// TODO Auto-generated method stub
		this.sysConfigLogic.findByProperty("");
		String url = "";
		RestClient restClient = new RestClient(this.httpclient, this.creds, URI.create(url));
	}

	
}
