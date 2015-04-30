package com.chinesedreamer.jira.biz.service;

import com.chinesedreamer.jira.core.JiraClient;

/**
 * Description: 
 * @author Paris
 * @date Jan 16, 20153:06:49 PM
 * @version beta
 */
public class JiraOldTemplateFactory {
	
	public static JiraCreatorOldService generateCreator(String templateCode, JiraClient jiraClient){
		if (templateCode.equals("yanghui-gaphone")) {
			return new JiraCreatorOldServiceYanghui(jiraClient);
		}else if (templateCode.equals("linjh-ba")) {
			return new JiraCreatorOldServiceLinjh(jiraClient);
		}
		return null;
	}
}
