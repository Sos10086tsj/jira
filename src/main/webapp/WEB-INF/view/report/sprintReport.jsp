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
            	<th>解决日</th>
            	<th>按时交付</th>
            	<th>耗费时间</th>
            	<th>预估时间</th>
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
        			<td><fmt:formatDate value="${vo.resolutionDate }" pattern="yyyy-MM-dd" /></td>
        			<td>
        				<c:choose>
        					<c:when test="${vo.deliveryOnTime == true}">是</c:when>
        					<c:otherwise>否</c:otherwise>
        				</c:choose> 
        			</td>
        			<td>${vo.timeSpentStr }</td>
        			<td>${vo.timeEstimatedStr }</td>
        		</tr>
        		<c:forEach items = "${vo.subTasks}" var = "subTask" varStatus="subi">
        			<tr class="child_row_${i.index}">
        				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${subTask.key }</td>
        				<td>${subTask.summary }</td>
        				<td>${subTask.status }</td>
        				<td>${subTask.issueType }</td>
        				<td>${subTask.assignee }</td>
        				<td><fmt:formatDate value="${subTask.dueDate }" pattern="yyyy-MM-dd" /></td>
        				<td><fmt:formatDate value="${subTask.resolutionDate }" pattern="yyyy-MM-dd" /></td>
        				<td>
        					<c:choose>
        						<c:when test="${subTask.deliveryOnTime == true}">是</c:when>
        						<c:otherwise>否</c:otherwise>
        					</c:choose> 
        				</td>
        				<td>${subTask.timeSpentStr }</td>
        				<td>${subTask.timeEstimatedStr }</td>
        			</tr>
        		</c:forEach>
        		<c:forEach items = "${vo.includedTasks}" var = "includeTask" varStatus="includei">
        			<tr class="child_row_${i.index}">
        				<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${includeTask.key }</td>
        				<td>${includeTask.summary }</td>
        				<td>${includeTask.status }</td>
        				<td>${includeTask.issueType }</td>
        				<td>${includeTask.assignee }</td>
        				<td><fmt:formatDate value="${includeTask.dueDate }" pattern="yyyy-MM-dd" /></td>
        				<td><fmt:formatDate value="${includeTask.resolutionDate }" pattern="yyyy-MM-dd" /></td>
        				<td>
        					<c:choose>
        						<c:when test="${includeTask.deliveryOnTime == true}">是</c:when>
        						<c:otherwise>否</c:otherwise>
        					</c:choose> 
        				</td>
        				<td>${includeTask.timeSpentStr }</td>
        				<td>${includeTask.timeEstimatedStr }</td>
        			</tr>
        		</c:forEach>
			</c:forEach>
			
			<%-- 统计 --%>
			<tr class="total-font">
				<td colspan="4" rowspan="4" >总任务数</td>
				<td rowspan="4" >${total.totalTasks}</td>
			</tr>
			<tr class="total-font">
				<td colspan="4" >完成率</td>
				<td><fmt:formatNumber type="percent" value="${total.completedRate}" /></td>
			</tr>
			<tr class="total-font">
				<td colspan="4" >交付率</td>
				<td><fmt:formatNumber type="percent" value="${total.onTimeRate}" /></td>
			</tr>
			<tr class="total-font">
				<td colspan="4" >bug率</td>
				<td><fmt:formatNumber type="percent" value="${total.bugRate}" /></td>
			</tr>
			<tr class="total-font">
				<td colspan="3">总耗时</td>
				<td>${total.workingMinutesStr}</td>
				<td colspan="3">预计耗时</td>
				<td>${total.totalMinutesStr}</td>
				<td>耗时比</td>
				<td><fmt:formatNumber type="percent" value="${total.workingRate}" /></td>
			</tr>
			<tr class="total-font">
				<td colspan="5" rowspan="5">任务组成</td>
			</tr>
			<tr class="total-font">
				<td colspan="4" >任务</td>
				<td>${total.constitution.task}</td>
			</tr>
			<tr class="total-font">
				<td colspan="4" >子任务</td>
				<td>${total.constitution.subTask}</td>
			</tr>
			<tr class="total-font">
				<td colspan="4" >Technical task</td>
				<td>${total.constitution.tec}</td>
			</tr>
			<tr class="total-font">
				<td colspan="4" >Bug</td>
				<td>${total.constitution.bug}</td>
			</tr>
        </tbody>  
    </table>  
</div>
