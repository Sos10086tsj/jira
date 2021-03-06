package com.chinesedreamer.jira.biz.jsonvo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 
 * @author Paris
 * @date Jan 16, 20153:49:18 PM
 * @version beta
 */
public @ToString @Getter @Setter class AssigneesOld {
	private List<AssigneeOld> developers;
	private List<AssigneeOld> qas;
	private List<AssigneeOld> bas;
	private List<AssigneeOld> products;
}
