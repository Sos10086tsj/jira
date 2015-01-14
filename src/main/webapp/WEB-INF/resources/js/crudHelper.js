crudHelper = {
};

$(function() {
	/*提交创建请求*/
	$("#create_btn").off("click").on("click",function (){
		var project = $("#project_select").val();
		var issueType = $("#issue_type_select").val();
		var version = $("#version_select").val();

		var issues = new Array();
		
		//loop
		var parentIssue = $("#jira_key").val();
		var developers = new Array();
		if ( $("#task_type_dev").is( ":checked" ) ){
			$("input[name='developer']").each(function(){
				var developer = {
						username : $(this).val()
				};
				developers.push(developer);
			});
		}
		var qas = new Array();
		if ( $("#task_type_test").is( ":checked" ) ){
			$("input[name='qa']").each(function(){
				var qa = {
						username : $(this).val()
				};
				qas.push(qa);
			});
		}
		
		var issue = {
				issueKey : parentIssue,
				developers : developers,
				qas : qas
		};
		issues.push(issue);
		
		var curdVos = {
				crudVos : issues
		};
		
		$.ajax({
			url : '/jiraCrud/createJiraTasks',
			type : 'post',
			async : false,
			data : {
				project : project,
				issueType : issueType,
				version : version,
				curdVos : JSON.stringify(curdVos)
			},
			success : function(result){
				//alert("success!");
				window.location.href = "/jiraCrud/showCreateResult?issueKeys=" + result;
			},
			failure : function(){
				alert("failure!");
			}
		});
	});
	
	
	
	//jqGrid
	$("#jira_table").jqGrid({
		datatype: "local",
		height: 250,
		weight: 500,
		colNames: ['Jira#','Developers', 'Qas'],
		colModel : [
		            	//{name:'id',index:'id', width:60, sorttype:"int"},
		            	{name:'issueKey',index:'issueKey', width:100, editable:true, edittype:'text'},
		            	{name:'developers',index:'developers', width:200, editable:true, edittype:'select',formatter:'select', editoptions:{value:"yanghui:杨辉;taosj:陶世杰"}},
		            	//{name:'qas',index:'qas', width:200, editable:true, edittype:'select',formatter:'select', editoptions:{value:"wanggy:王光源"}}
		            	{name:'qas',index:'qas', width:200, editable:true, edittype:'custom', editoptions:{custom_element: function(value, options){
		            		
		            			}, 
		            			custom_value: function(elem, operation, value){
		            				$(elem).val('123');
		            				if(operation === 'get') {
		            					   return $(elem).val();
		            					  } else if(operation === 'set') {
		            					   $(elem).val(value);
		            					   }
		            			}
		            			}}
		           ],
		multiselect: true,
		caption: "创建Jira任务",
		ondblClickRow: function(id){
			$('#jira_table').jqGrid('editRow', id, {
				keys : true,
				url : 'clientArray',
				restoreAfterError : true
			});
		}
	});
	
	//添加按钮
	$("#add_btn").off("click").on("click", function (){
		jQuery("#jira_table").jqGrid('addRow',{
			rowID : "new_row",
			initdata : {},
			position :"last", 
			useDefValues : true,  
	         useFormatter : true
		});
		//当前新增id进入可编辑状态  
        $('#jira_table').jqGrid('editRow','new_row',{  
            keys : true,
            url: 'clientArray',  
            restoreAfterError: true 
        });  
	});
});