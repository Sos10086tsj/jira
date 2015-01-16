crudHelper = {
		formatSliptStr : function(str, char){
			if(char==null||char==""||str.length==0||char.length>str.length){
				return str;
			}
			if(str.substring(str.length-char.length)==char){
				return str.substring(0, str.length-char.length);
			}
			return str;
		}
};

$(function() {
	
	//jqGrid
	$("#jira_table").jqGrid({
		datatype: "local",
		height: 500,
		weight: 900,
		colNames: ['Jira#','Developers', 'Qas'],
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
											var comp = "<div id=\""
													+ options.id
													+ "\" style=\"white-space:normal;\" >"
													+ "<input type=\"checkbox\" name=\"user\" value=\"yanghui\"/>杨辉"
													+ "<input type=\"checkbox\" name=\"user\" value=\"laiting\" />赖婷" 
													+ "<input type=\"checkbox\" name=\"user\" value=\"fanyn\" />樊亚宁" 
													+ "<input type=\"checkbox\" name=\"user\" value=\"xiexp\" />谢修普" 
													+ "<input type=\"checkbox\" name=\"user\" value=\"taosj\" />陶世杰" 
													+ "</div>";
											return comp;
										},
										custom_value : function(elem,
												operation, value) {
											if (operation === 'get') {
												var id = elem.attr("id");
												var qas = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														qas = qas + $(this).val()+ ",";
													}
												});
												qas = crudHelper.formatSliptStr(qas,",");
												elem.val(qas);
												return elem.val();
											} else if (operation === 'set') {
												var id = elem.attr("id");
												var qas = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														qas = qas + $(this).val()+ ",";
													}
												});
												qas = crudHelper.formatSliptStr(qas,",");
												elem.val(qas);
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
											var comp = "<div id=\""
													+ options.id
													+ "\" style=\"white-space:normal;\" >"
													+ "<input type=\"checkbox\" name=\"user\" value=\"wanggy\"/>王光源"
													+ "<input type=\"checkbox\" name=\"user\" value=\"zhouhq\" />周豪奇</div>";
											return comp;
										},
										custom_value : function(elem,
												operation, value) {
											if (operation === 'get') {
												var id = elem.attr("id");
												var qas = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														qas = qas + $(this).val()+ ",";
													}
												});
												elem.val(qas);
												qas = crudHelper.formatSliptStr(qas,",");
												return elem.val();
											} else if (operation === 'set') {
												var id = elem.attr("id");
												var qas = "";
												$("#" + id + " input").each(function() {
													if($(this).is(":checked")){
														qas = qas + $(this).val()+ ",";
													}
												});
												qas = crudHelper.formatSliptStr(qas,",");
												elem.val(qas);
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
	
	//提交按钮
	$("#create_btn").off("click").on("click", function (){
		var rowCount = $('#jira_table').getGridParam('records');
		if(rowCount){
			var project = $("#project_select").val();
			var issueType = $("#issue_type_select").val();
			var version = $("#version_select").val();
			
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
					crudVos : JSON.stringify(crudVos)
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
	
	//项目select选中事件
	$("#project_select").change(function(){
		$.ajax({
			url : '/jiraCrud/getProjectVersions',
			type : 'post',
			async : true,
			data : {
				project : $("#project_select").find("option:selected").val()
			},
			success : function(result){				
				var comp = $("#version_select");
				comp.empty();
				var dataList = eval(result);
				if(dataList){
					for(i in dataList){
						var record = dataList[i];
						comp.append("<option value='" + record.name + "'>" + record.name + "</option>");
					}
				}
			},
			failure : function(){
				alert("获取列表失败！");
			}
		});
	});
});