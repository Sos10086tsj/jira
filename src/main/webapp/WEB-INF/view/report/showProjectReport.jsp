<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	var ctx = '${pageContext.request.contextPath}';
</script>

<title>Jira - Sync System </title>
</head>
<body>
	<div>
		<!-- 表头部分 -->
		<div>
			<div>
				<label>总体统计</label>
			</div>
			<div>
			<table border="1">
				<tbody>
					<tr>
						<td>项目</td>
						<td>${report.project }</td>
						<td>版本</td>
						<td>${report.version }</td>
						<td colspan="2">截止日期</td>
						<td colspan="2">
							<c:if test="${not empty report.releaseDate }">
								<fmt:formatDate value="${report.releaseDate}" pattern="yyyy-MM-dd" />
							</c:if>
							<c:if test="${empty report.releaseDate }">
								待定
							</c:if>
						</td>
					</tr>
					<tr>
						<td>总数</td>
						<td>${report.total }</td>
						<td>完成数</td>
						<td>${report.completedIssues.size() }</td>
						<td>完成率</td>
						<td><fmt:formatNumber type="percent" value="${report.completionRate}" /></td>
						<td>bug数</td>
						<td>${report.bugs.size() }</td>
					</tr>
				</tbody>
			</table>
			</div>
		</div>
		
		<!-- 未完成任务 -->
		<div>
			<div>
				<label>未完成任务</label>
			</div>
			<div>
			<table border="1">
				<thead>
					<tr>
						<th>JIRA#</th>
						<th>Summary</th>
						<th>Assignee</th>
						<th>Status</th>
						<th>Due Date</th>
					</tr>
				</thead>
				<c:forEach items="${report.uncompletedIssues }" var="item">
					<tr>
						<td>${item.key }</td>
						<td>${item.summary }</td>
						<td>${item.assignee }</td>
						<td>${item.status }</td>
						<td>
							<c:if test="${not empty item.dueDate }">
								<fmt:formatDate value="${item.dueDate}" pattern="yyyy-MM-dd" />
							</c:if>
							<c:if test="${empty item.dueDate }">
								未知
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			
			<!-- bug -->
			<div>
			<div>
				<label>Bug</label>
			</div>
			<div>
			<table border="1">
				<thead>
					<tr>
						<th>JIRA#</th>
						<th>Summary</th>
						<th>Assignee</th>
						<th>Status</th>
						<th>Due Date</th>
					</tr>
				</thead>
				<c:forEach items="${report.bugs }" var="item">
					<tr>
						<td>${item.key }</td>
						<td>${item.summary }</td>
						<td>${item.assignee }</td>
						<td>${item.status }</td>
						<td>
							<c:if test="${not empty item.dueDate }">
								<fmt:formatDate value="${item.dueDate}" pattern="yyyy-MM-dd" />
							</c:if>
							<c:if test="${empty item.dueDate }">
								未知
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			
			<!-- 已完成任务 -->
			<div>
			<div>
				<label>已完成任务</label>
			</div>
			<div>
			<table border="1">
				<thead>
					<tr>
						<th>JIRA#</th>
						<th>Summary</th>
						<th>Assignee</th>
						<th>Status</th>
						<th>Due Date</th>
					</tr>
				</thead>
				<c:forEach items="${report.completedIssues }" var="item">
					<tr>
						<td>${item.key }</td>
						<td>${item.summary }</td>
						<td>${item.assignee }</td>
						<td>${item.status }</td>
						<td>
							<c:if test="${not empty item.dueDate }">
								<fmt:formatDate value="${item.dueDate}" pattern="yyyy-MM-dd" />
							</c:if>
							<c:if test="${empty item.dueDate }">
								未知
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			</div>
			
		</div>
	</div>

	<div>
		<a href="${pageContext.request.contextPath}/report">返回</a>
	</div>
</body>
</html>