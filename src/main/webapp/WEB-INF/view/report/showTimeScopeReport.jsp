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

<title>Jira - Report System </title>
</head>
<body>
	<div>
		<div><b>**********${ template.startDate} ~ ${ template.endDate}报告 **********</b></div>
		<div><br></div>
		
		<div><b>**********项目整体统计 **********</b></div>
		<div>
			<table border="1">
				<tbody>
					<c:forEach items="${ template.projects}" var="item">
						<tr>
							<td>项目</td>
							<td>${item.project }</td>
							<td>版本</td>
							<td>${item.version }</td>
							<td colspan="2">截止日期</td>
							<td colspan="2">
								<c:if test="${not empty item.releaseDate }">
									<fmt:formatDate value="${item.releaseDate}" pattern="yyyy-MM-dd" />
								</c:if>
								<c:if test="${empty item.releaseDate }">
									待定
								</c:if>
							</td>
						</tr>
						<tr>
							<td>总数</td>
							<td>${item.total }</td>
							<td>完成数</td>
							<td>${item.completedNum }</td>
							<td>完成率</td>
							<td>${item.completionRateStr}</td>
							<td>bug数</td>
							<td>${item.bugNum }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div><br></div>
		<div><b>**********用户统计**********</b></div>
		<div>
			<c:forEach items="${ template.users}" var="item">
				<div><label>${item.username }</label></div>
				<div>
					<table border="1">
						<tbody>
							<tr>
								<td>任务总数</td>
								<td>${item.totalNum }</td>
								<td>完成数</td>
								<td>${item.completedNum }</td>
								<td>完成率</td>
								<td>${item.completeRate }</td>
								<td>bug总数</td>
								<td>${item.bugNum }</td>
							</tr>
							<tr>
								<td>总耗时</td>
								<td>${item.totalTimeSpent }</td>
								<td>总预估</td>
								<td>${item.totalTimeEstimated }</td>
								<td colspan="2">总超时</td>
								<td colspan="2">${item.totalTimeout }</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div><label>明细</label></div>
				<div>
					<table border="1">
						<thead>
							<tr>
								<th>JIRA#</th>
								<th>Summary</th>
								<th>Time Estimated</th>
								<th>Time Spent</th>
								<th>Due Date</th>
								<th>Status</th>
								<th>Project</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ item.issueVos}" var="issue">
								<tr>
									<td>${issue.key }</td>
									<td>${issue.summary }</td>
									<td>${issue.timeEstimated }</td>
									<td>${issue.timeSpent }</td>
									<td>
										<c:if test="${not empty issue.dueDate }">
											<fmt:formatDate value="${issue.dueDate}" pattern="yyyy-MM-dd" />
										</c:if>
										<c:if test="${empty issue.dueDate }">
											未知
										</c:if>
									</td>
									<td>${issue.status}</td>
									<td>${issue.project}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
				<div><br></div>
			</c:forEach>
		</div>
		
	</div>

	<div>
		<a href="${pageContext.request.contextPath}/report">返回</a>
	</div>
</body>
</html>