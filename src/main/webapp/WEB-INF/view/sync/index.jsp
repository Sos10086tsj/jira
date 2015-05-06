<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script src="${pageContext.request.contextPath}/static/plugins/jquery/jquery-2.1.1.js"/></script>
<script src="${pageContext.request.contextPath}/static/js/syncHelper.js"/></script>
<script>
	var ctx = '${pageContext.request.contextPath}';
</script>

<title>Jira - Sync System </title>
</head>
<body>
	<div>
		<div>同步列表</div>
		<div><a href="${pageContext.request.contextPath}/sync/user">同步用户</a></div>
		<div><a href="${pageContext.request.contextPath}/sync/project">同步项目</a></div>
		<div><button onclick="syncHelper.syncPriority()">同步优先级</button></div>
		<div><button onclick="syncHelper.syncIssueType()">同步问题类型</button></div>
		<div><button onclick="syncHelper.syncIssueStatus()">同步问题状态</button></div>
		<div><a href="${pageContext.request.contextPath}/sync/project/version">同步项目版本</a></div>
	</div>
</body>

</html>