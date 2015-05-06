package com.chinesedreamer.jira.biz.vo;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Description: 
 * @author Paris
 * @date May 6, 201511:36:32 AM
 * @version beta
 */
public @Getter @Setter class JiraIssueVo {
	private String key;
	private String summary;
	private String assignee;
	private Date dueDate;
	private String status;
}
