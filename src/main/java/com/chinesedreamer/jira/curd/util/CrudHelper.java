package com.chinesedreamer.jira.curd.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinesedreamer.jira.core.Issue;
import com.chinesedreamer.jira.curd.vo.CrudVo;
import com.chinesedreamer.jira.curd.vo.IssueVo;

/**
 * Description: 
 * @author Paris
 * @date Jan 14, 201510:07:23 AM
 * @version beta
 */
public class CrudHelper {
	public static String getProject(HttpServletRequest request){
		return request.getParameter("project");
	}
	
	public static String getIssueType(HttpServletRequest request){
		return request.getParameter("issueType");
	}
	
	public static String getVersion(HttpServletRequest request){
		return request.getParameter("version");
	}
	
	public static String getIssueKeys(HttpServletRequest request){
		return request.getParameter("issueKeys");
	}
	
	public static List<CrudVo> getCrudVos(HttpServletRequest request){
		List<CrudVo> vos = new ArrayList<CrudVo>();
		String requestVos = request.getParameter("crudVos");
		JSONObject rootJsonObject = JSONObject.parseObject(requestVos);
		JSONArray rootJsonArray = rootJsonObject.getJSONArray("crudVos");
		for (Object issueVo : rootJsonArray) {
			JSONObject jo = (JSONObject) issueVo;
			CrudVo vo = new CrudVo();
			vo.setParentIssueKey(jo.getString("issueKey"));
			
			JSONArray devJsonArray = jo.getJSONArray("developers");
			String[] developers = new String[devJsonArray.size()];
			for (int i = 0; i < devJsonArray.size(); i++) {
				JSONObject devJo = (JSONObject)devJsonArray.get(i);
				developers[i] = devJo.getString("username");
			}
			vo.setDevelopers(developers);
			
			JSONArray qaJsonArray = jo.getJSONArray("qas");
			String[] qas = new String[qaJsonArray.size()];
			for (int i = 0; i < qaJsonArray.size(); i++) {
				JSONObject qaJo = (JSONObject)qaJsonArray.get(i);
				qas[i] = qaJo.getString("username");
			}
			vo.setQas(qas);
			
			vos.add(vo);
		}
		return vos;
	}
	
	public static List<IssueVo> convertIssues2Vo(List<Issue> issues){
		List<IssueVo> vos = new ArrayList<IssueVo>();
		for (Issue issue : issues) {
			IssueVo vo = new IssueVo();
			vo.setKey(issue.getKey());
			vo.setSummary(issue.getSummary());
			vo.setAssignee(issue.getAssignee().getDisplayName());
			vo.setFixVersion(issue.getFixVersions().get(0).getName());
			vos.add(vo);
		}
		return vos;
	}
}
