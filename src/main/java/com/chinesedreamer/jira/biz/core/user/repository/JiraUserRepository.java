package com.chinesedreamer.jira.biz.core.user.repository;

import com.chinesedreamer.jira.base.repository.BaseRepository;
import com.chinesedreamer.jira.biz.core.user.model.JiraUser;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:06:38 PM
 * @version beta
 */
public interface JiraUserRepository extends BaseRepository<JiraUser, Long>{
	public JiraUser findByUsername(String username);
}
