<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="../../resources/plugins/jquery/jquery-2.1.1.js"></script>


<script type="text/javascript" src="../../resources/js/reportHelper.js"></script>
<link rel="stylesheet" type="text/css"  href="../../resources/css/report.css">  
<link rel="stylesheet" type="text/css"  href="../../resources/css/common.css">  
<title>报表查询</title>

</head>
<body>
	
	<!-- 过滤器 -->
	<div class="float_left">
		<div>过滤器</div>
		<div>
			<select id="rapid_view_select">
				<c:forEach items="${rapidViews}" var="rapidView">
					<c:choose>
						<%-- 默认gaphone --%>
						<c:when test="${rapidView.name == 'gaphone'}">
							<option value="${rapidView.id}" selected="selected">${rapidView.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${rapidView.id}">${rapidView.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>
	
	<!-- sprint -->
	<div class="float_left">
		<div>Sprint</div>
		<div>
			<select id="sprint_select">
				<c:forEach items="${sprints}" var="sprint">
					<option value="${sprint.id}">${sprint.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	
	<div class="clear"></div>
	<div class="float_left">
		<button id="sprint_rpt_btn">Sprint报表</button>
	</div>
	
	<div class="float_left">
		<button id="assignee_rpt_btn">Assignee报表</button>
	</div>
	
	<div class="float_left">
		<button id="daily_rpt_btn">Daily报表</button>
	</div>
	
	<div class="clear"></div>
	<div id="report"></div>
</body>
</html>