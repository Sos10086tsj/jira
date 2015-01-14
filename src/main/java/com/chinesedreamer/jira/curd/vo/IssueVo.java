package com.chinesedreamer.jira.curd.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 
 * @author Paris
 * @date Jan 14, 20151:14:06 PM
 * @version beta
 */
public @Getter @Setter @ToString class IssueVo {
	private String key;
	private String summary;
	private String assignee;
	private String fixVersion;
}
