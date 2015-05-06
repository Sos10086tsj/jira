package com.chinesedreamer.jira.biz.service;

import com.chinesedreamer.jira.core.JiraException;

/**
 * Description: 从服务器同步
 * @author Paris
 * @date May 4, 20154:45:43 PM
 * @version beta
 */
public interface JiraSyncService {

	/**
	 * 同步用户信息
	 * @param username
	 * @throws JiraException
	 */
	public void syncUser(String username) throws JiraException;
	
	/**
	 * 同步项目信息
	 * @param projectIdOrKey
	 * @throws JiraException
	 */
	public void syncProject(String projectIdOrKey) throws JiraException;
	
	/**
	 * 同步优先级
	 * @throws JiraException
	 */
	public void syncPriority() throws JiraException;
	
	/**
	 * 同步问题类型
	 * @throws JiraException
	 */
	public void syncIssueType() throws JiraException;
	
	/**
	 * 同步问题状态
	 * @throws JiraException
	 */
	public void syncStatus() throws JiraException;
	
	
	/**
	 * 同步项目版本
	 * @param projectIdOrKey
	 * @throws JiraException
	 */
	public void syncProjectVersion(String projectIdOrKey) throws JiraException;
	
	/**
	 * 同步项目issue列表
	 * @param projectIdOrKey
	 * @throws JiraException
	 */
	public void syncProjectIssue(String projectIdOrKey) throws JiraException;
}
