package com.chinesedreamer.jira.biz.core.user.logic;

import com.chinesedreamer.jira.base.logic.BaseLogic;
import com.chinesedreamer.jira.biz.core.user.model.JiraUser;

/**
 * Description: 
 * @author Paris
 * @date Apr 30, 20152:07:04 PM
 * @version beta
 */
public interface JiraUserLogic extends BaseLogic<JiraUser, Long>{
	public JiraUser findByUsername(String username);
}
