package com.chinesedreamer.jira.curd.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 
 * @author Paris
 * @date Jan 14, 201510:09:46 AM
 * @version beta
 */
public @Getter @Setter @ToString class CrudVo {
	private String parentIssueKey;
	private String[] developers;
	private String[] qas;
}
