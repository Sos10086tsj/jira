package com.chinesedreamer.jira.curd.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.chinesedreamer.jira.biz.jsonvo.Assignee;
import com.chinesedreamer.jira.biz.jsonvo.TemplateCode;
import com.chinesedreamer.jira.biz.service.JiraService;
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
		List<Version> versions = this.jiraService.loadProjectVersions("GAPHONE");//default gaphone
		model.addAttribute("projects", projects);
		model.addAttribute("issueTypes", issueTypes);
		model.addAttribute("versions", versions);
		//templateCode
		List<TemplateCode> templateCodes = TemplateCode.loadDataSource();
		model.addAttribute("templateCodes", templateCodes);
		return "crud/crud";
	}
	
	@ResponseBody
	@RequestMapping(value = "createJiraTasks", method = RequestMethod.POST)
	public String createJiraTasks(Model model, HttpServletRequest request) throws JiraException{
		String project = CrudHelper.getProject(request);
		String issueType = CrudHelper.getIssueType(request);
		String version = CrudHelper.getVersion(request);
		List<CrudVo> vos = CrudHelper.getCrudVos(request);
		String templateCode = CrudHelper.getTemplateCode(request);
		List<Issue> issues = this.jiraService.createIssues(project, issueType, version, vos,templateCode);
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
		model.addAttribute("issues", JSON.toJSONString(CrudHelper.convertIssues2Vo(issues)));
		return "crud/crudResult";
	}
	
	@ResponseBody
	@RequestMapping(value = "getProjectVersions", method = RequestMethod.POST)
	public List<Version> getProjectVersions(Model model, HttpServletRequest request) throws JiraException{
		String project = request.getParameter("project").trim();
		List<Version> versions = this.jiraService.loadProjectVersions(project);
		return versions;
	}
	
	@RequestMapping(value = "loadGridJsp", method = RequestMethod.GET)
	public String loadGridJsp(Model model, HttpServletRequest request){
		String templateCode = request.getParameter("templateCode").trim();
		model.addAttribute("assignees", JSON.toJSONString(Assignee.loadDataSource(templateCode)));
		return "crud/crudGrid-" + templateCode;
	}
}
