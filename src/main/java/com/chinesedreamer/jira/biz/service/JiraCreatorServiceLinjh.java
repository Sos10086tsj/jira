package com.chinesedreamer.jira.biz.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinesedreamer.jira.core.Field;
import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.JiraClient;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.curd.vo.CrudVo;

/**
 * Description: 
 * @author Paris
 * @date Jan 16, 20153:11:15 PM
 * @version beta
 */
public class JiraCreatorServiceLinjh extends JiraCreatorService{
	
	private final static Map<String, String> devMap = new HashMap<String, String>();
	static{
		devMap.put("requirement", "【需求任务】");
		devMap.put("product", "【产品任务】");
	}

	public JiraCreatorServiceLinjh(JiraClient jiraClient) {
		super(jiraClient);
	}

	@Override
	public List<Issue> createIssues(String project, String issueType,
			String version, List<CrudVo> vos)
			throws JiraException {
		List<Issue> newIssues = new ArrayList<Issue>();

		List<String> versions = new ArrayList< String>();
		versions.add(version);
		
		for (CrudVo vo : vos) {
			//1. get parent issue
			Issue parentIssue = this.jiraClient.getIssue(vo.getParentIssueKey());
			//2. create requirement task
			if (null != vo.getRequirements() && vo.getRequirements().length != 0) {
				for (String req : vo.getRequirements()) {
					Issue newIssue = this.jiraClient.createIssue(project, issueType)
							.field(Field.SUMMARY, devMap.get("requirement") + parentIssue.getSummary())
							.field(Field.PRIORITY, Field.valueById("3"))
							.field(Field.ASSIGNEE, req)
							.field(Field.FIX_VERSIONS, versions)
							.execute();
					parentIssue.link(newIssue.getKey(), "包含");
					newIssues.add(newIssue);
				}
			}
			//3. create product task
			if (null != vo.getProducts() && vo.getProducts().length != 0) {
				for (String product : vo.getProducts()) {
					Issue newIssue = this.jiraClient.createIssue(project, issueType)
							.field(Field.SUMMARY, devMap.get("product") + parentIssue.getSummary())
							.field(Field.PRIORITY, Field.valueById("3"))
							.field(Field.ASSIGNEE, product)
							.field(Field.FIX_VERSIONS, versions)
							.execute();
					parentIssue.link(newIssue.getKey(), "包含");
					newIssues.add(newIssue);
				}
			}
		}

		return newIssues;
	}

}
