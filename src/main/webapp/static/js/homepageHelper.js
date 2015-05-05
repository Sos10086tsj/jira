$(function() {
	//创建jira button
	$("#go_2_crud").off("click").on("click", function (){
		window.location.href = "/jiraCrud";
	});
	
	//报表
	$("#go_2_rpt").off("click").on("click", function (){
		window.location.href = "/report";
	});
});