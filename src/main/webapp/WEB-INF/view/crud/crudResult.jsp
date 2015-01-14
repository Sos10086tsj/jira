<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Jira任务创建结果</title>
</head>
<body>
	<div>
		<table>
			<thead>
				<tr>
					<th>创建结果</th>
				</tr>
				<tr>
					<th>Key</th>
					<th>Summary</th>
					<th>Assignee</th>
					<th>Fix Version</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${issues }" var = "issue">
					<tr>
						<td>${issue.key}</td>
						<td>${issue.summary}</td>
						<td>${issue.assignee}</td>
						<td>${issue.fixVersion}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>