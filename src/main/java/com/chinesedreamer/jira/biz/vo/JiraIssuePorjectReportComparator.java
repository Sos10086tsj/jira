package com.chinesedreamer.jira.biz.vo;

import java.util.Comparator;

/**
 * Description: 
 * @author Paris
 * @date May 6, 20151:15:45 PM
 * @version beta
 */
public class JiraIssuePorjectReportComparator implements Comparator<JiraIssueVo>{

	@Override
	public int compare(JiraIssueVo o1, JiraIssueVo o2) {
		if (null != o1.getDueDate() && null != o2.getDueDate()) {
			return o1.getDueDate().compareTo(o2.getDueDate());
		}
		if (null != o1.getDueDate() && null == o2.getDueDate()) {
			return 0;
		}
		if (null == o1.getDueDate() && null != o2.getDueDate()) {
			return 1;
		}
		if (null == o1.getDueDate() && null == o2.getDueDate()) {
			return o1.getKey().compareTo(o2.getKey());
		}
		return 0;
	}

}
