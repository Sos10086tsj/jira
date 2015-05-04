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
}
