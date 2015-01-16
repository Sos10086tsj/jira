<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="../../resources/plugins/jquery/jquery-2.1.1.js"></script>

<link rel="stylesheet" type="text/css"  href="../../resources/css/common.css">  
<link rel="stylesheet" type="text/css"  href="../../resources/plugins/jpGrid/css/ui.jqgrid.css">  
<script type="text/javascript" src="../../resources/plugins/jpGrid/js/minified/jquery.jqGrid.min.js"></script>  
<script type="text/javascript" src="../../resources/plugins/jpGrid/js/i18n/grid.locale-cn.js"></script>  
<script type="text/javascript" src="../../resources/js/crudHelper.js"></script>
<title>CRUD页面</title>
</head>
<body>

	<!-- 选择项目 -->
	<div class="float_left">
		<div >项目</div>
		<div >
			<select id="project_select" name="project_select">
				<c:forEach items="${projects}" var="project">
					<c:choose>
						<%-- 默认gaphone --%>
						<c:when test="${project.key == 'GAPHONE'}">
							<option value="${project.key}" selected="selected">${project.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${project.key}" >${project.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>

	<!-- 选择问题类型 -->
	<div class="float_left">
		<div>问题</div>
		<div>
			<select id="issue_type_select" name="issue_type_select">
				<c:forEach items="${issueTypes}" var="issueType">
					<c:choose>
						<%--默认任务 --%>
						<c:when test="${issueType.name == '任务'}">
							<option value="${issueType.name}" selected="selected">${issueType.name}</option>
						</c:when>
						<c:otherwise>
							<option value="${issueType.name}">${issueType.name}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>
		</div>
	</div>

	<!-- 选择版本 -->
	<div class="float_left">
		<div>问题类型</div>
		<div>
			<select id="version_select" name="version_select">
				<c:forEach items="${versions}" var="version">
					<option value="${version.name}">${version.name}</option>
				</c:forEach>
			</select>
		</div>
	</div>

<div class="clear"></div>

	<div class="float_left">
		<table id="jira_table" cellspacing="0" cellpadding="0" border="0"></table> 
	</div>
<div class="clear"></div>
	<!-- 提交按钮 -->
	<div class="float_left">
		<button id="add_btn">添加</button>
		<button id="del_btn">删除</button>
		<button id="create_btn">提交</button>
	</div>
<div class="clear"></div>

</body>
</html>