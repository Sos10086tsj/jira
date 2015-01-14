package com.chinesedreamer.jira.curd.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chinesedreamer.jira.biz.JiraService;
import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.core.IssueType;
import com.chinesedreamer.jira.core.JiraException;
import com.chinesedreamer.jira.core.Project;
import com.chinesedreamer.jira.core.Version;
import com.chinesedreamer.jira.curd.util.CrudHelper;
import com.chinesedreamer.jira.curd.vo.CrudVo;

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
		model.addAttribute("projects", projects);
		model.addAttribute("issueTypes", issueTypes);
		model.addAttribute("versions", versions);
		return "crud/crud";
	}
	
	@ResponseBody
	@RequestMapping(value = "createJiraTasks", method = RequestMethod.POST)
	public String createJiraTasks(Model model, HttpServletRequest request) throws JiraException{
		String project = CrudHelper.getProject(request);
		String issueType = CrudHelper.getIssueType(request);
		String version = CrudHelper.getVersion(request);
		List<CrudVo> vos = CrudHelper.getCrudVos(request);
		List<Issue> issues = this.jiraService.createIssues(project, issueType, version, vos);
		StringBuffer buffer = new StringBuffer();
		for (Issue issue : issues) {
			buffer.append(issue.getKey())
			.append(",");
		}
		return buffer.toString();
	}
	
	@RequestMapping(value = "showCreateResult",method = RequestMethod.GET)
	public String showCreateResult(Model model, HttpServletRequest request) throws JiraException{
		String issueKeysStr = CrudHelper.getIssueKeys(request);
		String[] issueKeys = issueKeysStr.split(",");
		List<Issue> issues = this.jiraService.showIssues(issueKeys);
		model.addAttribute("issues", CrudHelper.convertIssues2Vo(issues));
		return "crud/crudResult";
	}
	
	
}
