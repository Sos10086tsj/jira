crudResultHelper = {
};

$(function() {
	//jqGrid
	$("#jira_result_table").jqGrid({
		datatype: "local",
		height: 500,
		weight: 900,
		colNames: ['Jira#','任务', '跟进','修复版本'],
		colModel : [
								{
									name : 'key',
									index : 'key',
									width : 100
								},
								{
									name : 'summary',
									index : 'summary',
									width : 400
								},
								{
									name : 'assignee',
									index : 'assignee',
									width : 100
								},
								{
									name : 'fixVersion',
									index : 'fixVersion',
									width : 100
								}
		           ],
		caption: "创建结果"
	});
	var rowDatas = eval(data);
	for(var i=0;i<rowDatas.length;i++){  
        $("#jira_result_table").jqGrid('addRowData',i+1,rowDatas[i]);  
    } 
});