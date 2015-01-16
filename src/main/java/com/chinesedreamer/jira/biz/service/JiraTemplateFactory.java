package com.chinesedreamer.jira.biz.service;

import com.chinesedreamer.jira.core.JiraClient;

/**
 * Description: 
 * @author Paris
 * @date Jan 16, 20153:06:49 PM
 * @version beta
 */
public class JiraTemplateFactory {
	
	public static JiraCreatorService generateCreator(String templateCode, JiraClient jiraClient){
		if (templateCode.equals("yanghui-gaphone")) {
			return new JiraCreatorServiceYanghui(jiraClient);
		}else if (templateCode.equals("linjh-requirement")) {
			return new JiraCreatorServiceLinjh(jiraClient);
		}
		return null;
	}
}
