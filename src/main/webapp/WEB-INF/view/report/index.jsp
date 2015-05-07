<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html xmlns="http://www.w3.org/1999/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<script src="${pageContext.request.contextPath}/static/plugins/jquery/jquery-2.1.1.js"/></script>
<script src="${pageContext.request.contextPath}/static/js/reportHelper.js"/></script>
<script>
	var ctx = '${pageContext.request.contextPath}';
</script>

<title>Jira - Report System </title>
</head>
<body>
	<div>
		<div>报表列表</div>
		<div><a href="${pageContext.request.contextPath}/report/projectReport">生成project报表</a></div>
		<div><a href="${pageContext.request.contextPath}/report/projectReportConfig">report 配置</a></div>
	</div>
</body>

</html>