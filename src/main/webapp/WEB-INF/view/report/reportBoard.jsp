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
<title>报表查询</title>

<style>  
    table { border:0;border-collapse:collapse;}  
    td { font:normal 12px/17px Arial;padding:2px;width:100px;}  
    th { font:bold 12px/17px Arial;text-align:left;padding:4px;border-bottom:1px solid #333;width:100px;}  
    .parent { background:#FFF38F;cursor:pointer;}  /* 偶数行样式*/  
    .odd { background:#FFFFEE;} /* 奇数行样式*/  
    .selected { background:#FF6500;color:#fff;}  
</style>  



</head>
<body>
	
	<!-- 过滤器 -->
	<div>
		<div>过滤器</div>
		<div>
			<select id="rapid_view_select">
				<c:forEach items="${rapidViews}" var="rapidView">
					<c:choose>
						<%-- 默认gaphone --%>
						<c:when test="${rapidView.name == 'gaphone-taosj'}">
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
	<div>
		<div>Sprint</div>
		<div>
			<select id="sprint_select">
				<c:forEach items="${sprints}" var="sprint">
					<option value="${sprint.id}">${sprint.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>
	
	<div>
		<button id="rpt_btn">生成报表</button>
	</div>
	
	<div id="report"></div>
</body>
</html>