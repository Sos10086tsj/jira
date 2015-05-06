<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="${pageContext.request.contextPath}/static/plugins/jquery/jquery-2.1.1.js"/></script>
<script src="${pageContext.request.contextPath}/static/js/syncHelper.js"/></script>
<script>
	var ctx = '${pageContext.request.contextPath}';
</script>

<title>Jira - Sync System </title>
</head>
<body>
	<div>
		<div>
			<select id="js_sync_project_version">
				<c:forEach items="${projects }" var="item">
					<option value="${item.jiraId}">${item.name}</option>
				</c:forEach>
			</select>
			<button onclick="syncHelper.syncProjectIssue()">同步</button>
		</div>
		<div>
			<a href="${pageContext.request.contextPath}/sync">返回</a>
		</div>
	</div>
</body>
</html>