crudGridHelperYanghuiGaphone = {

};

$(function() {
	var gloabel_assignees = eval("(" + assignees + ")");
	
	//添加按钮
	$("#add_btn").off("click").on("click", function (){
		var rowCount = $('#jira_table').getGridParam('records');
		jQuery("#jira_table").jqGrid('addRow',{
			rowID : rowCount + 1,
			initdata : {},
			position :"last", 
			useDefValues : true,  
	         useFormatter : true
		});
		//当前新增id进入可编辑状态  
        $('#jira_table').jqGrid('editRow',rowCount + 1,{  
            keys : true,
            url: 'clientArray',  
            restoreAfterError: true 
        });  
	});
	
	//删除按钮
	$("#del_btn").off("click").on("click", function (){
		var rowCount = $('#jira_table').getGridParam('records');
		for(var i = 1; i <= rowCount; i++){
			if($("#jqg_jira_table_" + i).is(":checked")){
				$('#jira_table').jqGrid('delRowData',i);
			}
		}
	});
	
	//jqGrid
	$("#jira_table").jqGrid({
		datatype: "local",
		height: 500,
		weight: 900,
		colNames: ['Jira#','开发', '测试'],
		colModel : [
								{
									name : 'issueKey',
									index : 'issueKey',
									width : 100,
									editable : true,
									edittype : 'text'
								},
								{
									name : 'developers',
									index : 'developers',
									width : 400,
									editable : true,
									edittype : 'custom',
									editoptions : {
										custom_element : function(value,
												options) {
											var devComp = "";
											var developers = gloabel_assignees.developers;
											for(i in developers){
												var developer = developers[i];
												devComp = devComp + "<input type=\"checkbox\" name=\"user\" value=\""+ developer.username +"\"/>" + developer.name;
											}
											
											var comp = "<div id=\""
													+ options.id
													+ "\" style=\"white-space:normal;\" >"
													+ devComp
													+ "</div>";
											return comp;
										},
										custom_value : function(elem,
												operation, value) {
											if (operation === 'get') {
												var id = elem.attr("id");
												var users = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														users = users + $(this).val()+ ",";
													}
												});
												users = crudHelper.formatSliptStr(users,",");
												elem.val(users);
												return elem.val();
											} else if (operation === 'set') {
												var id = elem.attr("id");
												var users = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														users = users + $(this).val()+ ",";
													}
												});
												users = crudHelper.formatSliptStr(users,",");
												elem.val(users);
											}

										}
									}
								},
								{
									name : 'qas',
									index : 'qas',
									width : 400,
									editable : true,
									edittype : 'custom',
									editoptions : {
										custom_element : function(value,
												options) {
											var qaComp = "";
											var qas = gloabel_assignees.qas;
											for(i in qas){
												var qa = qas[i];
												qaComp = qaComp + "<input type=\"checkbox\" name=\"user\" value=\""+ qa.username +"\"/>" + qa.name;
											}
											
											var comp = "<div id=\""
													+ options.id
													+ "\" style=\"white-space:normal;\" >"
													+ qaComp
													+ "</div>";
											return comp;
										},
										custom_value : function(elem,
												operation, value) {
											if (operation === 'get') {
												var id = elem.attr("id");
												var users = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														users = users + $(this).val()+ ",";
													}
												});
												users = crudHelper.formatSliptStr(users,",");
												elem.val(users);
												return elem.val();
											} else if (operation === 'set') {
												var id = elem.attr("id");
												var users = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														users = users + $(this).val()+ ",";
													}
												});
												users = crudHelper.formatSliptStr(users,",");
												elem.val(users);
											}

										}
									}
								}
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
	
	//提交按钮
	$("#create_btn").off("click").on("click", function (){
		var rowCount = $('#jira_table').getGridParam('records');
		if(rowCount){
			var project = $("#project_select").val();
			var issueType = $("#issue_type_select").val();
			var version = $("#version_select").val();
			var templateCode = $("#template_select").val();
			
			var vos = new Array();
			
			for(var i = 1; i <= rowCount; i++){
				var rowData = $("#jira_table").jqGrid('getRowData', i);
				var issueKey = rowData['issueKey'];
				var developersVal = rowData['developers'].split(",");
				var qasVal = rowData['qas'].split(",");
				
				var developers = new Array();
				for(j in developersVal){
					if(developersVal[j]){
						var developer = {
								username : developersVal[j]
						};
						developers.push(developer);
					}
				}
				
				var qas = new Array();
				for(j in qasVal){
					if(qasVal[j]){
						var qa = {
								username : qasVal[j]
						};
						qas.push(qa);
					}
				}
				
				var vo = {
						issueKey : issueKey,
						developers : developers,
						qas : qas
				};
				vos.push(vo);
			}
			
			var crudVos = {
					crudVos : vos
			};
			
			
			$.ajax({
				url : '/jiraCrud/createJiraTasks',
				type : 'post',
				async : true,
				data : {
					project : project,
					issueType : issueType,
					version : version,
					crudVos : JSON.stringify(crudVos),
					templateCode : templateCode
				},
				success : function(result){
					window.location.href = "/jiraCrud/showCreateResult?issueKeys= " + crudHelper.formatSliptStr(result,",");
				},
				failure : function(){
					alert("保存失败！");
				}
			});
		}
	});
});