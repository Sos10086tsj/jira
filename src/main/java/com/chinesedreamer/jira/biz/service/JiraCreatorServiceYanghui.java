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
 * @date Jan 16, 20153:08:08 PM
 * @version beta
 */
public class JiraCreatorServiceYanghui extends JiraCreatorService{
	
	private final static Map<String, String> devMap = new HashMap<String, String>();
	static{
		devMap.put("yanghui", "【开发任务】");
		devMap.put("laiting", "【开发任务】");
		devMap.put("xiexp", "【android】");
		devMap.put("fanyn", "【ios】");
		devMap.put("taosj", "【开发任务】");
		devMap.put("chenliang", "【开发任务】");
	}

	public JiraCreatorServiceYanghui(JiraClient jiraClient) {
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
			//2. create develop task
			if (null != vo.getDevelopers() && vo.getDevelopers().length != 0) {
				for (String dev : vo.getDevelopers()) {
					Issue newIssue = this.jiraClient.createIssue(project, issueType)
							.field(Field.SUMMARY, devMap.get(dev) + parentIssue.getSummary())
							.field(Field.PRIORITY, Field.valueById("3"))
							.field(Field.ASSIGNEE, dev)
							.field(Field.FIX_VERSIONS, versions)
							.execute();
					parentIssue.link(newIssue.getKey(), "包含");
					newIssues.add(newIssue);
				}
			}
			//3. create qa task
			if (null != vo.getQas() && vo.getQas().length != 0) {
				for (String qa : vo.getQas()) {
					Issue newIssue = this.jiraClient.createIssue(project, issueType)
							.field(Field.SUMMARY, "【测试任务】" + parentIssue.getSummary())
							.field(Field.PRIORITY, Field.valueById("3"))
							.field(Field.ASSIGNEE, qa)
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
