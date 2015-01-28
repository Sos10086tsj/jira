<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script type="text/javascript" src="../../resources/js/reportHelper.js"></script>
<!-- html -->


<div>
	<table>  
        <thead>  
            <tr>
            	<th>经办人</th>
            	<th>任务</th>
            	<th>Bug</th>
            	<th>Bug率</th>
            	<th>耗时</th>
            	<th>预估时间</th>
            	<th>耗时率</th>
            	<th>交付率</th>
            </tr>  
        </thead>  
        <tbody>  
        	<c:forEach items = "${vos }" var = "vo">
        		<tr>
        			<td>${vo.username }</td>
        			<td>${vo.total }</td>
        			<td>${vo.bugs }</td>
        			<td><fmt:formatNumber type="percent" value="${vo.bugRate}" /></td>
        			<td>${vo.totalTimeSpentStr }</td>
        			<td>${vo.totalEstimatedStr }</td>
        			<td><fmt:formatNumber type="percent" value="${vo.timeRate}" /></td>
        			<td><fmt:formatNumber type="percent" value="${vo.onTimeRate}" /></td>
        		</tr>
        	</c:forEach>
        </tbody>  
    </table>  
</div>
