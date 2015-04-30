package com.chinesedreamer.jira.biz.jsonvo;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Description: 
 * @author Paris
 * @date Jan 16, 20153:45:41 PM
 * @version beta
 */
public class AssigneeTest {

	@Test
	public void testLoadDataSource() {
		AssigneesOld assignees = AssigneeOld.loadDataSource("linjh-ba");
		assertNotNull(assignees);
		System.out.println(assignees);
	}

}
