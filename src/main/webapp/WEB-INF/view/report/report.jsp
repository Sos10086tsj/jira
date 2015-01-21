<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="../../resources/js/reportHelper.js"></script>
<!-- html -->


<div>
	<table>  
        <thead>  
            <tr>
            	<th>Jira#</th>
            	<th style="width:200px; !important">Summary</th>
            	<th>状态</th>
            	<th>类型</th>
            	<th>经办人</th>
            	<th>到期日</th>
            </tr>  
        </thead>  
        <tbody>  
        	<c:forEach items = "${vos }" var = "vo" varStatus="i">
        		<tr class="parent" id="row_${i.index}">
        			<td>${vo.key }</td>
        			<td>${vo.summary }</td>
        			<td>${vo.status }</td>
        			<td>${vo.issueType }</td>
        			<td>${vo.assignee }</td>
        			<td><fmt:formatDate value="${vo.dueDate }" pattern="yyyy-MM-dd" /></td>
        		</tr>
        		<c:forEach items = "${vo.subTasks}" var = "subTask" varStatus="subi">
        			<tr class="child_row_${i.index}">
        				<td>子任务${subTask.key }</td>
        				<td>${subTask.summary }</td>
        				<td>${subTask.status }</td>
        				<td>${subTask.issueType }</td>
        				<td>${subTask.assignee }</td>
        				<td><fmt:formatDate value="${subTask.dueDate }" pattern="yyyy-MM-dd" /></td>
        			</tr>
        		</c:forEach>
        		<c:forEach items = "${vo.includedTasks}" var = "includeTask" varStatus="includei">
        			<tr class="child_row_${i.index}">
        				<td>包含${includeTask.key }</td>
        				<td>${includeTask.summary }</td>
        				<td>${includeTask.status }</td>
        				<td>${includeTask.issueType }</td>
        				<td>${includeTask.assignee }</td>
        				<td><fmt:formatDate value="${includeTask.dueDate }" pattern="yyyy-MM-dd" /></td>
        			</tr>
        		</c:forEach>
			</c:forEach>
        </tbody>  
    </table>  
	
</div>
