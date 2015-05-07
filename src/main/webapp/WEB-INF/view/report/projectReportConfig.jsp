<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/static/plugins/jquery/jquery-2.1.1.js"/></script>
<script src="${pageContext.request.contextPath}/static/js/reportHelper.js"/></script>
<script>
	var ctx = '${pageContext.request.contextPath}';
</script>

<title>Jira - Sync System </title>
</head>
<body>
	<div>
	
		<!-- 已配置 -->
		<div>
			<table border="1">
				<thead>
					<tr>
						<th>Project</th>
						<th>Version</th>
						<th>Operation</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${configs }" var="item">
						<tr>
							<td>${item.projectJiraName}</td>
							<td>${item.versionJiraName}</td>
							<td><a href="${pageContext.request.contextPath}/reportprojectReportConfig/delete?projectJiraId=${item.projectJiraId}&versionJiraId=${item.versionJiraId}">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		
		<!-- 添加配置 -->
		<div>
			<label>项目</label>
			<select id="js_rpt_project" onclick="">
				<c:forEach items="${projects }" var="item">
					<option value="${item.jiraId}">${item.name}</option>
				</c:forEach>
			</select>
			<label>版本</label>
			<select id="js_rpt_project_version" disabled="disabled">
			</select>
			<button onclick="reportHelper.addConfig()">添加配置</button>
		</div>
		
		<div>
			<a href="${pageContext.request.contextPath}/report">返回</a>
		</div>
	</div>
	
</body>
</html>