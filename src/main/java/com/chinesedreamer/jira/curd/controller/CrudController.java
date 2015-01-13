package com.chinesedreamer.jira.curd.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chinesedreamer.jira.biz.JiraService;
import com.chinesedreamer.jira.core.IssueType;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.Version;

/**
 * Description: 
 * @author Paris
 * @date Jan 13, 20153:27:32 PM
 * @version beta
 */
@Controller
@RequestMapping(value = "jiraCrud")
public class CrudController {
	
	@Resource
	private JiraService jiraService;
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String loadCrud(Model model) throws JiraException {
		List<Project> projects = this.jiraService.loadProjects();
		List<IssueType> issueTypes = this.jiraService.loadIssueTypes();
		List<Version> versions = this.jiraService.loadProjectVersions("GAPHONE");
		Map<String, String> roles = this.jiraService.loadProjectRoles("GAPHONE");
		for (Project project : projects) {
			System.out.println("project|" + project.getKey());
		}
		for (IssueType issueType : issueTypes) {
			System.out.println("issueType|" + issueType.getId()+ "| " + issueType.getName());
		}
		for (Version version : versions) {
			System.out.println("version|" + version.getId() + "| " + version.getName());
		}
		for (String key : roles.keySet()) {
			System.out.println("role|" + key + "| " + roles.get(key));
		}
		//model.addAttribute("dto", dto);
		return "crud";
	}
}
